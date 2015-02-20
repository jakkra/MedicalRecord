package server;/*
 * Created by jakkra on 2015-02-12.
 */


public abstract class Command {
    protected String requesterId = "-1";

    public Command(String requesterId) {
        this.requesterId = requesterId;
    }

    public abstract String doCommand(Database db);

    public String getRequestedId(){
        return requesterId;
    }

    public void setId(String reqId){
      this.requesterId = reqId;
    }


}
