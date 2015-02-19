package server;/*
 * Created by jakkra on 2015-02-12.
 */


public class Agency extends User {
    public Agency(String name, String department) {
        super(name, department, User.AGENCY);
    }
}
