package server;/*
 * Created by jakkra on 2015-02-12.
 */


public class ModifyCommand extends Command {

    String[] data;
    /**
     * Updates the user in the database
     * @param s formatted userID;field to update;value
     */
    public ModifyCommand(String s) {
        super(s.split(";")[0]);
        this.data = s.split(";");
    }

    @Override
    public String doCommand(Database db) {
        return db.modify(requesterId, data[1], data[2]);
    }

}
