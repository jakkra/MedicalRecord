package server;/*
 * Created by jakkra on 2015-02-12.
 */

public class DeleteCommand extends Command {
    public DeleteCommand(String patientId) {
        super(patientId);
    }

    @Override
    public String doCommand(Database db) {
        return db.delete(requesterId);

    }
}
