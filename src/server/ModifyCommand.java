package server;/*
 * Created by jakkra on 2015-02-12.
 */

import commons.Patient;

public class ModifyCommand extends Command {


    private Patient patient;

    /**
     * Updates the user in the database
     * @param patient user to be modified
     */
    public ModifyCommand(Patient patient) {
        super(patient.getName());
        this.patient = patient;
    }

    @Override
    public String doCommand(Database db) {
        db.modifyPatient(patient);
        return null;
    }
}
