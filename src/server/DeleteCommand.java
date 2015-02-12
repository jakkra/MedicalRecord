package server;/*
 * Created by jakkra on 2015-02-12.
 */

public class DeleteCommand extends Command {

    public DeleteCommand() {
        super(5);
    }

    @Override
    public String doCommand(Database db) {
        db.delete();
        return null;
    }
}
