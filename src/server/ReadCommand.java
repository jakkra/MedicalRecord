package server;/*
 * Created by jakkra on 2015-02-12.
 */

public class ReadCommand extends Command {


    public ReadCommand(String patientId){

        super(patientId);

    }
    @Override
    public String doCommand(Database db) {
        return db.get(requesterId).toString();
    }
}
