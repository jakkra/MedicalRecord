package server;/*
 * Created by jakkra on 2015-02-12.
 */

public class ModifyCommand extends Command {
    private final String id;
    private final String something;

    public ModifyCommand(String id, String something) {
        super(id);
        this.id = id;
        this.something = something;
    }

    @Override
    public String doCommand(Database db) {
        return null;
    }
}
