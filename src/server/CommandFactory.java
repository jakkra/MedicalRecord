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
        if (command.equals("Delete")) {
            String patientName = input[1];
            return new DeleteCommand(patientName);
        } else if (command.equals("Add")) {
            Patient p = Patient.ParsePatient(input[1]);
            return new AddCommand(p);
        } else if (command.equals("Read")) {
            String patientId = input[1];
            return new ReadCommand(patientId);
        } else if (command.equals("Modify")) {
            return new ModifyCommand(input[1]);
        }
        //TODO
        return null;
    }

}
