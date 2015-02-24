package server;

import commons.Patient;

abstract class User {

    public static int AGENCY = 1337;
    public static int DOCTOR = 3;
    public static int NURSE = 2;
    public static int PATIENT = 1;

    private Database db = null;

    protected String department;
    protected final int type; //AGENCY/DOCTOR...
    protected final String nameOfPersonRequesting;


    public User(String name, String department, int type) {
        this.nameOfPersonRequesting = name;
        this.department = department;
        this.type = type;
        try {
            db = Database.getInstance();
            System.out.println(db);
        } catch (Exception e) {
            System.out.println("error no db");
            e.printStackTrace();
        }
    }

    public String execute(Command command) {
        db.get(command.getRequestedId());
        if (canExecuteCommand(command, db.get(command.getRequestedId()))) {
            try {
                return command.doCommand(db);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("DENIED!!!");
            return "ACCESS DENIED";
        }
        return "Something went wrong";
    }

    //TODO type can be replaced by this instanceOf DOCTOR and so on...
    private boolean canExecuteCommand(Command command, Patient requestedPatient) {
        boolean approved = false;
        if (type == AGENCY) {
            approved = true;
        }
        if (command instanceof ReadCommand && type == PATIENT && requestedPatient.getName().equals(nameOfPersonRequesting)) { //TODO broken, need to be id instead
            approved = true;
        }
        if (command instanceof ReadCommand && type == NURSE) {
            if (requestedPatient.getDepartment().equals(department)) {
                approved = true;
            } else if (requestedPatient.getNurse().equals(nameOfPersonRequesting)) {
                approved = true;
            }

        } else if (command instanceof ModifyCommand && type == NURSE) {
            if (requestedPatient.getNurse().equals(nameOfPersonRequesting)) {
                approved = true;
            }
        } else if (command instanceof ReadCommand && type == DOCTOR) {
            if (requestedPatient.getDepartment().equals(department)) {
                approved = true;
            } else if (requestedPatient.getDoctor().equals(nameOfPersonRequesting)) {
                approved = true;
            }

        } else if (command instanceof ModifyCommand && type == DOCTOR) {
            if (requestedPatient.getDoctor().equals(nameOfPersonRequesting)) {
                approved = true;
            }
        } else if (command instanceof AddCommand && type == DOCTOR) {
            approved = true;

        }
        if (approved) {
            Logger.log(getClass().getSimpleName(), "Access Granted");
            return true;
        } else {
            Logger.log(getClass().getSimpleName(), "Access Denied");
            return false;
        }
    }


}
