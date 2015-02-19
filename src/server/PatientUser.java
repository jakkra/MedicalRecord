package server;/*
 * Created by jakkra on 2015-02-12.
 */

public class PatientUser extends User {
    public PatientUser(String name, String department) {
        super(name, department, User.PATIENT);
    }
}
