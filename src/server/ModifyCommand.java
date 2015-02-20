package server;/*
 * Created by jakkra on 2015-02-12.
 */


public class ModifyCommand extends Command {

    String[] data;
    /**
     * Updates the user in the database
     * @param patient user to be modified
     */
    public ModifyCommand(String s) {
<<<<<<< HEAD

      super("0");

      data = s.split(";");
      setId(data[0]);

=======
        super(s.split(";")[0]);
        this.data = s.split(";");
>>>>>>> FETCH_HEAD
    }

    @Override
    public String doCommand(Database db) {
        return db.modify(reqId, data[1], data[2]);
    }

}
