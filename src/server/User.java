package server;

import commons.Patient;

import javax.security.cert.X509Certificate;

abstract class User {
    public static int AGENCY = 1337;
    public static int DOCTOR = 3;
    public static int NURSE = 2;
    public static int PATIENT = 1;

    private Database db = null;

    protected String department;
    protected int type; //AGENCY/DOCTOR...
    protected int nameOfPersonRequesting;


    public User(X509Certificate cert) {
        try {
            db = Database.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Parse cert to extract department, name and type (patient/nurse/doctor/agency)
    }

    public String execute(Command command) {
        if (canExecuteCommand(command, db.get(command.getRequestedId()))) {
            try {
                return command.doCommand(db);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return "ACCESS DENIED";
        }
        return "";
    }

    private boolean canExecuteCommand(Command command, Patient requestedPatient) {
        if (type == AGENCY) {
            return true;
        }
        if (command instanceof ReadCommand && type == PATIENT && requestedPatient.getName().equals(nameOfPersonRequesting)) { // Multiple patients on same department???????
            return true;
        }
        if (command instanceof ReadCommand && type == NURSE) {
            if (requestedPatient.getDepartment().equals(department)) {
                return true;
            } else if (requestedPatient.getNurse().equals(nameOfPersonRequesting)) { // What if two nurses has the same name???????
                return true;
            }

        } else if (command instanceof ModifyCommand && type == NURSE) {
            if (requestedPatient.getNurse().equals(nameOfPersonRequesting)) { // What if two nurses has the same name???????
                return true;
            }
        } else if (command instanceof ReadCommand && type == DOCTOR) {
            if (requestedPatient.getDepartment().equals(department)) {
                return true;
            } else if (requestedPatient.getDoctor().equals(nameOfPersonRequesting)) { // What if two nurses has the same name???????
                return true;
            }

        } else if (command instanceof ModifyCommand && type == DOCTOR) {
            if (requestedPatient.getDoctor().equals(nameOfPersonRequesting)) { // What if two nurses has the same name???????
                return true;
            }
        } else if (command instanceof AddCommand && type == DOCTOR) {
            if (requestedPatient.getDoctor().equals(nameOfPersonRequesting)) {
                return true;
            }
        }
        return false;
    }


}