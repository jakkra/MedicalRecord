package server;

import javax.naming.InvalidNameException;
import javax.net.ssl.SSLSocket;
import javax.security.cert.X509Certificate;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;


public class ServerConnection implements Runnable {

    private final SSLSocket socket;
    private final X509Certificate certificate;
    private User user;
    private PrintWriter out;
    private BufferedReader in;


    public ServerConnection(SSLSocket socket, X509Certificate certificate) {
        this.socket = socket;
        this.certificate = certificate;
        out = null;
        in = null;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            user = UserFactory.buildUser(certificate);
        } catch (InvalidNameException e) {
            System.err.println("Error building User");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String clientMsg;
        try {
            while ((clientMsg = in.readLine()) != null) {
                System.out.println("received '" + clientMsg + "' from client.client");

                Command command = CommandFactory.buildCommand(clientMsg);
                String response = user.execute(command);
                out.println(response);
                out.flush();
            }
        } catch (IOException e) {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            e.printStackTrace();
        }

    }
}
