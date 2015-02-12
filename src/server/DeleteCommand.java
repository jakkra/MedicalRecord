package server;/*
 * Created by jakkra on 2015-02-12.
 */

public class DeleteCommand extends Command {

    private String patientId;

    public DeleteCommand(String patientId) {
        super(patientId);
        this.patientId = patientId;
    }

    @Override
    public String doCommand(Database db) {
        db.delete(patientId);
        return null;
    }
}
