package server;/*
 * Created by jakkra on 2015-02-12.
 */


public class CommandFactory {
    public static Command buildCommand(String clientMsg) {
        if(clientMsg.startsWith("Delete")){
            //TODO
            return new DeleteCommand();
        }
        //TODO
        return null;
    }
}
