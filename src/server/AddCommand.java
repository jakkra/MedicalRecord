package server;/*
 * Created by jakkra on 2015-02-12.
 */

import commons.Patient;

public class AddCommand extends Command {

  private Patient patient;


    public AddCommand(Patient patient) {

        super("0");

        this.patient = patient;
        
    }

    @Override
    public String doCommand(Database db) {

        String id = db.add(patient);

        setId(id);

        return id;
    }
}
