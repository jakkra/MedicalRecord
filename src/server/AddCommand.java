package server;/*
 * Created by jakkra on 2015-02-12.
 */

import commons.Patient;

public class AddCommand extends Command {

    public AddCommand(Patient patient) {
        super(patient.getName());
    }

    @Override
    public String doCommand(Database db) {
        return null;
    }
}
