package client;

import javax.net.ssl.*;
import javax.security.cert.X509Certificate;
import java.io.*;
import java.security.KeyStore;

/*
 * This example shows how to set up a key manager to perform client.client
 * authentication.
 *
 * This program assumes that the client.client is not inside a firewall.
 * The application can be modified to connect to a server outside
 * the firewall by following SSLSocketClientWithTunneling.java.
 */
public class client {

    public static void main(String[] args) throws Exception {
        String host = null;
        String passwordInfo = null;
        String type = null;
        String name = null;
//        String passwordInfo = "password";
        int port = 4446;
        for (int i = 0; i < args.length; i++) {
            System.out.println("args[" + i + "] = " + args[i]);
        }
        if (args.length < 4) {
            System.out.println("USAGE: java client.client <host> <keystore_password> <user_type> <name>");
            System.exit(-1);
        }
        try { /* get input parameters */
            host = args[0];
            passwordInfo = args[1];
            type = args[2];
            name = args[3];
        } catch (IllegalArgumentException e) {
            System.out.println("USAGE: java client.client <host> <keystore_password> <user_type> <name>");
            System.exit(-1);
        }


        try { /* set up a key manager for client.client authentication */
            SSLSocketFactory factory = null;
            try {
                char[] password = passwordInfo.toCharArray();
                KeyStore ks = KeyStore.getInstance("JKS");
                KeyStore ts = KeyStore.getInstance("JKS");
                KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
                TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
                SSLContext ctx = SSLContext.getInstance("TLS");
                //TODO input from user instead of coded here
                ks.load(new FileInputStream("Client_certificates/" + type + "/" + name + "/" + "keystore"), password);  // keystore password (storepass)
                ts.load(new FileInputStream("Client_certificates/" + type + "/" + name + "/" + "truststore"), password); // truststore password (storepass);
                kmf.init(ks, password); // user password (keypass)
                tmf.init(ts); // keystore can be used as truststore here
                ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
                factory = ctx.getSocketFactory();

            } catch (Exception e) {
                throw new IOException(e);
            }

            SSLSocket socket = (SSLSocket) factory.createSocket(host, port);
            String[] enabled = socket.getEnabledCipherSuites();
            for (int i = 0; i < enabled.length; i++) {
                System.out.println("Enabled " + enabled[i]);
            }
            String[] supported = socket.getSupportedCipherSuites();
            for (int i = 0; i < supported.length; i++) {
                System.out.println("Supported " + supported[i]);

            }
            System.out.println("\nsocket before handshake:\n" + socket + "\n");

            /*
             * send http request
             *
             * See SSLSocketClient.java for more information about why
             * there is a forced handshake here when using PrintWriters.
             */
            socket.startHandshake();

            SSLSession session = socket.getSession();
            System.out.println("Cipher suite used: " + session.getCipherSuite());
            X509Certificate cert = (X509Certificate) session.getPeerCertificateChain()[0];
            String subject = cert.getSubjectDN().getName();
            System.out.println("certificate name (subject DN field) on certificate received from server:\n" + subject + "\n");
            System.out.println("socket after handshake:\n" + socket + "\n");
            System.out.println("secure connection established\n\n");

            BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg;
            for (; ; ) {
                System.out.print(">");
                msg = read.readLine();
                if (msg.equalsIgnoreCase("quit")) {
                    break;
                }
                System.out.print("sending '" + msg + "' to server...");
                out.println(msg);
                out.flush();
                System.out.println("Send");
                String line;
                System.out.println("Received from server: ");
                line = in.readLine();
                System.out.println(line);

            }
            in.close();
            out.close();
            read.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
