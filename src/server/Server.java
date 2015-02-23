package server;

import javax.net.ServerSocketFactory;
import javax.net.ssl.*;
import javax.security.cert.X509Certificate;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.security.KeyStore;

public class Server implements Runnable {

    private ServerSocket serverSocket = null;
    private Database db;

    private boolean running;

    public Server() {
        running = true;
        Database.initiate();
        try {
            this.db = Database.getInstance();
            this.db.openConnection();

        } catch (Exception e) {

            e.printStackTrace();
        }

    }


    public void run() {
        System.out.println("\nserver.Server Started\n");
        System.out.println("Enter port");
        //int port = Integer.parseInt(System.console().readLine());
        int port = 4446;
        String type = "TLS";
        try {
            ServerSocketFactory ssf = getServerSocketFactory(type);
            serverSocket = ssf.createServerSocket(port);

            ((SSLServerSocket) serverSocket).setNeedClientAuth(true); // enables client.client authentication
        } catch (IOException e) {
            System.out.println("Unable to start server.Server: " + e.getMessage());
            e.printStackTrace();
        }

        while (running) {
            try {
                SSLSocket socket = (SSLSocket) serverSocket.accept();
                System.out.println("Supported " + socket.getSupportedCipherSuites()[0]);
                String[] enabled = socket.getEnabledCipherSuites();
                for (int i = 0; i <enabled.length; i++) {
                    System.out.println("Enabled" + enabled[i]);
                }
                String[] supported = socket.getSupportedCipherSuites();
                for (int i = 0; i <supported.length; i++) {
                    System.out.println("Supported " + supported[i]);

                }

                SSLSession session = socket.getSession();
                X509Certificate cert = (X509Certificate) session.getPeerCertificateChain()[0];
                String subject = cert.getSubjectDN().getName();
                System.out.println("client.client connected");
                System.out.println("client.client getName: (cert subject DN field): " + subject);

                ServerConnection serverConnection = new ServerConnection(socket, cert);
                new Thread(serverConnection).start();

            } catch (IOException e) {
                System.out.println("Server died " + e.getMessage());
                e.printStackTrace();
                return;
            }
        }
    }

    private static ServerSocketFactory getServerSocketFactory(String type) {
        if (type.equals("TLS")) {
            SSLServerSocketFactory ssf = null;
            try { // set up key manager to perform server authentication
                SSLContext ctx = SSLContext.getInstance("TLS");
                KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
                TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
                KeyStore ks = KeyStore.getInstance("JKS");
                KeyStore ts = KeyStore.getInstance("JKS");

                System.out.println("Enter server Keystore password");
                //String keystorePassword = new String(System.console().readPassword());
                String keystorePassword = "password";

                System.out.println("Enter server Truststore password");
                //String truststorePassword = new String(System.console().readPassword());
                String truststorePassword = "password";
                ks.load(new FileInputStream("serverkeystore"), keystorePassword.toCharArray());
                ts.load(new FileInputStream("servertruststore"), truststorePassword.toCharArray());
                kmf.init(ks, keystorePassword.toCharArray());
                tmf.init(ts);
                ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
                ssf = ctx.getServerSocketFactory();
                return ssf;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return ServerSocketFactory.getDefault();
        }
        return null;
    }

    public static void main(String args[]) {
        new Thread(new Server()).start();
    }
}
