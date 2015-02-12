package server;

import java.io.*;
import java.net.*;
import java.security.KeyStore;
import java.util.List;
import javax.net.*;
import javax.net.ssl.*;
import javax.security.cert.X509Certificate;

public class Server implements Runnable {
    private ServerSocket serverSocket = null;
    private boolean running;

    public Server() {
        running = true;
    }

    public void run() {
        System.out.println("\nserver.Server Started\n");
        System.out.println("Enter port");
        int port = Integer.parseInt(System.console().readLine());
        String type = "TLS";
        try {
            ServerSocketFactory ssf = getServerSocketFactory(type);
            ServerSocket ss = ssf.createServerSocket(port);
            ((SSLServerSocket) ss).setNeedClientAuth(true); // enables client.client authentication
        } catch (IOException e) {
            System.out.println("Unable to start server.Server: " + e.getMessage());
            e.printStackTrace();
        }

        while (running) {
            try {
                SSLSocket socket = (SSLSocket) serverSocket.accept();
                SSLSession session = socket.getSession();
                X509Certificate cert = (X509Certificate) session.getPeerCertificateChain()[0];
                String subject = cert.getSubjectDN().getName();
                System.out.println("client.client connected");
                System.out.println("client.client name (cert subject DN field): " + subject);

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
                String keystorePassword = new String(System.console().readPassword());
                System.out.println("Enter server Truststore password");
                String truststorePassword = new String(System.console().readPassword());
                
                ks.load(new FileInputStream("server/serverkeystore"), keystorePassword.toCharArray()); 
                ts.load(new FileInputStream("server/servertruststore"), truststorePassword.toCharArray()); 
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
