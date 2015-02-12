package server;/*
 * Created by jakkra on 2015-02-12.
 */

public class AddCommand extends Command {
    private final String id;
    private final String something;

    public AddCommand(String id, String something) {
        super(1);
        this.id = id;
        this.something = something;
    }

    @Override
    public String doCommand(Database db) {
        return null;
    }
}
