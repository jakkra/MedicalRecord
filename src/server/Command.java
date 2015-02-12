package server;/*
 * Created by jakkra on 2015-02-12.
 */


public abstract class Command {
    protected int securityLevel;

    public Command(int securityLevel) {
        this.securityLevel = securityLevel;
    }

    public abstract String doCommand(Database db);

    public int getSecurityLevel() {
        return securityLevel;

    }
}
