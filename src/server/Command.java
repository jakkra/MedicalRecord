package server;/*
 * Created by jakkra on 2015-02-12.
 */


public abstract class Command {
    protected String reqId;

    public Command(String reqId) {
        this.reqId = reqId;
    }

    public abstract String doCommand(Database db);

    public String getRequestedId() {
        return reqId;
    }
}
