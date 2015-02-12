package server;/*
 * Created by jakkra on 2015-02-12.
 */

public class Database {
    private static Database db;

    public static void  initiate() {
        db = new Database();
    }
    
    public static Database getInstance() throws Exception {
        if(db != null){
            return db;
        } else {
            throw new Exception("Mist initiate Database first");
        }
    }

    public void delete(String patientId) {
        //TODO
    }


    //Either a SQL database or just a text file
}
