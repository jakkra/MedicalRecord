package server;/*
 * Created by jakkra on 2015-02-12.
 */


public class Nurse extends User {
    public Nurse(String name, String department) {
        super(name, department, User.NURSE);
    }
}
