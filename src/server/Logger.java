package server;/*
 * Created by krantz on 15-02-19.
 */

import java.io.*;

public class Logger {

    public Logger() {
        File yourFile = new File("log.txt");
        if (!yourFile.exists()) {
            try {
                yourFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public static void log(String tag, String message) {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("log.txt", true)));
            out.println(tag + " : " + message);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
