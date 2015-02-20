package server;/*
 * Created by jakkra on 2015-02-12.
 */

import commons.Patient;

public class ModifyCommand extends Command {

    String[] data;
    /**
     * Updates the user in the database
     * @param patient user to be modified
     */
    public ModifyCommand(String s) {

       data = s.split(";");

        super(data[0]);

    }

    @Override
    public String doCommand(Database db) {

        return db.modify(reqId, data[1], data[2]);
    }

}
