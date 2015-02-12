package server;/*
 * Created by jakkra on 2015-02-12.
 */

import commons.Patient;

public class Database {
    private static Database db;

    public static void initiate() {
        db = new Database();
    }

    public static Database getInstance() throws Exception {
        if (db != null) {
            return db;
        } else {
            throw new Exception("Mist initiate Database first");
        }
    }

    /**
     * @param patientId id of patient to remove
     * @return true if patient was removed (in Database) false otherwise
     */
    public boolean delete(String patientId) {
        return true;
    }

    public Patient get(String id) {
        return null;
    }


    //Either a SQL database or just a text file
}
