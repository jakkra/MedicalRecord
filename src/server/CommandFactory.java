package server;
/*
 * Created by jakkra on 2015-02-12.
 */

import commons.Patient;

public class CommandFactory {


    /**
     * Converts an String to a command, to be executed by a User.
     * Format of the String can be one of the following:
     * <p/>
     * Add: Add:Patient.toString()
     * Remove: Remove:patientID
     * Modify: Modify:patientID;field to edit;new data for that field
     * Read: Read:patientID
     *
     * @param clientMsg String which the command will be based on
     * @return Command which can be executed by a User
     */
    public static Command buildCommand(String clientMsg) {
        String[] input = clientMsg.split(":");
        String command = input[0];
        Command c = null;
        if (command.equals("Delete")) {
            String patientId = input[1];
            c = new DeleteCommand(patientId);
        } else if (command.equals("Add")) {
            Patient p = Patient.ParsePatient(input[1]);
            c = new AddCommand(p);
        } else if (command.equals("Read")) {
            String patientId = input[1];
            c = new ReadCommand(patientId);
        } else if (command.equals("Modify")) {
            c = new ModifyCommand(input[1]);
        }
        if (c != null) {
            Logger.log("CommandFactory", "Received command is " + c.getClass().getSimpleName() + " from message: " + clientMsg);
        } else {
            System.err.println("Command null WTF should not be called!??!??1!");
            Logger.log("CommandFactory", "Unknown Command not supported: " + clientMsg);
        }
        return c;
    }

}
