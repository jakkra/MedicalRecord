package server;/*
 * Created by jakkra on 2015-02-12.
 */

import commons.Patient;

public class AddCommand extends Command {

  private Patient patient;


    public AddCommand(Patient patient) {
        super(patient.getName());

        this.patient = patient;
    }

    @Override
    public String doCommand(Database db) {
      
        return db.add(patient);
    }
}
