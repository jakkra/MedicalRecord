package server;/*
 * Created by jakkra on 2015-02-12.
 */

public class AddCommand extends Command {
    private String patientId;
    private String something;
    private String nurse;

    public AddCommand(String patientId, String something, String nurse) {
        super(patientId);
        this.patientId = patientId;
        this.something = something;
        this.nurse = nurse;
    }

    @Override
    public String doCommand(Database db) {
        return null;
    }
}
