package server;/*
 * Created by jakkra on 2015-02-12.
 */


public class CommandFactory {
    public static Command buildCommand(String clientMsg) {
        String patientId = "";
        if (clientMsg.startsWith("Delete")) {
            //TODO
            return new DeleteCommand(patientId);
        } else if (clientMsg.startsWith("Add")) {
            return new AddCommand(patientId, "something", "Nurse name or id something");
        } else if (clientMsg.startsWith("Read")) {
            return new ReadCommand(patientId);
        } else if (clientMsg.startsWith("Modify")) {
            return new ModifyCommand(patientId, "Something");
        }
        //TODO
        return null;
    }
}
