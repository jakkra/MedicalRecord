package server;/*
 * Created by jakkra on 2015-02-12.
 */


public class Doctor extends User {
    public Doctor(String name, String department) {
        super(name, department, User.DOCTOR);
    }
}
