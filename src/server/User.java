package server;

import javax.security.cert.X509Certificate;

abstract class User {
    public static int AGENCY = 1337;
    public static int DOCTOR = 3;
    public static int NURSE = 2;
    public static int PATIENT = 1;

    protected String department;
    protected int type; //AGENCY/DOCTOR...

    public User(X509Certificate cert) {
        //Parse cert to extract department and patient/nurse/doctor/agency
    }

    public String execute(Command command) {
        if (canExecute(command)) {
            try {
                return command.doCommand(Database.getInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return "ACCESS DENIED";
        }
        return "";
    }

    private boolean canExecute(Command command) {
        if (command instanceof DeleteCommand) {
            //TODO more
            return true;
        } else {
            return false;
        }

    }
}