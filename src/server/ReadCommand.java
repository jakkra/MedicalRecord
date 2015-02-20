package server;/*
 * Created by jakkra on 2015-02-12.
 */

public class ReadCommand extends Command {

    private String id;

    public ReadCommand(String id){

        super(id);

        this.id = id;

    }
    @Override
    public String doCommand(Database db) {
        return db.get(id).toString();
    }
}
