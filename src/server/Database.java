package server;
/*
 * Created by jakkra on 2015-02-12.
 */

import commons.Patient;

import java.sql.*;
import java.util.ArrayList;


public class Database {

    private static Database db;

    private Connection conn;


    public static void initiate() {
        db = new Database();
    }

    public static Database getInstance() throws Exception {
        if (db != null) {
            return db;
        } else {
            throw new Exception("Must initiate Database first");
        }
    }

    /**
     * Open a connection to the database, using the specified user name and
     * password.
     *
     * @return true if the connection succeeded, false if the supplied user name
     * and password were not recognized. Returns false also if the JDBC
     * driver isn't found.
     */
    public boolean openConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://puccini.cs.lth.se/" + "db24", "db24", "spat_Thrill7edit");

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return isConnected();
    }

    /**
     * Close the connection to the database.
     */
    public void closeConnection() {

        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
        }
        conn = null;
    }

    public boolean isConnected() {
        return conn != null;
    }

    /**
     * @param patientId id of patient to remove
     * @return true if patient was removed (in Database) false otherwise
     * @throws SQLException
     */
    public String delete(String patientId) {


        PreparedStatement ps;
        try {
            ps = conn.prepareStatement("DELETE FROM patients WHERE patientId = ?;");

            ps.setString(1, patientId);

            if (ps.executeUpdate() == 1) {
                return "Delete successful";
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return "Delete not successful";
    }

    public String add(Patient patient) {
        System.out.println(patient.toString());
        PreparedStatement ps;

        try {

            conn.setAutoCommit(false);


            ps = conn
                    .prepareStatement("INSERT INTO patients (name, department, nurse, doctor, information)  VALUES (?, ?, ?, ?, ?);");

            ps.setString(1, patient.getName());
            ps.setString(2, patient.getDepartment());
            ps.setString(3, patient.getNurse());
            ps.setString(4, patient.getDoctor());
            ps.setString(5, patient.getInformation());

            ps.execute();

            ps = conn
                    .prepareStatement("SELECT LAST_INSERT_ID() as patientId;");

            ResultSet rs = ps.executeQuery();

            conn.setAutoCommit(true);

            if (rs.next()) {

                return String.valueOf(rs.getInt("patientId"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
                conn.setAutoCommit(true);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return "Fel i servern";

    }

    /**
     * @param patientId id of patient to get from database
     * @return Patient object containing fields from the database
     */
    public Patient get(String patientId) {


        PreparedStatement ps;

        try {

            ps = conn.prepareStatement("SELECT name, department, nurse, doctor, information FROM patients WHERE patientId = ?;");

            ps.setString(1, patientId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return
                        new Patient(

                                rs.getString("name"),
                                rs.getString("department"),
                                rs.getString("nurse"),
                                rs.getString("doctor"),
                                rs.getString("information")

                        );

            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;

    }

    public String getAll() {

        ArrayList<Patient> list = new ArrayList<Patient>();


        PreparedStatement ps;

        try {

            ps = conn.prepareStatement("Select name, department, nurse, doctor, information FROM patients;");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                list.add(new Patient(
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getString("nurse"),
                        rs.getString("doctor"),
                        rs.getString("information")

                ));

            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return list.toString();
    }


    public String modify(String patientId, String category, String newValue) {
        PreparedStatement ps = null;

        try {


            if (category.equals("name")) {

                ps = conn.prepareStatement("UPDATE patients SET name = ? WHERE patientId = ?;");

                ps.setString(1, newValue);
                ps.setString(2, patientId);


            } else if (category.equals("department")) {

                ps = conn.prepareStatement("UPDATE patients SET department = ? WHERE patientId = ?;");

                ps.setString(1, newValue);
                ps.setString(2, patientId);


            } else if (category.equals("nurse")) {

                ps = conn.prepareStatement("UPDATE patients SET nurse = ? WHERE patientId = ?;");

                ps.setString(1, newValue);
                ps.setString(2, patientId);


            } else if (category.equals("doctor")) {

                ps = conn.prepareStatement("UPDATE patients SET doctor = ? WHERE patientId = ?;");

                ps.setString(1, newValue);
                ps.setString(2, patientId);


            } else if (category.equals("information")) {

                ps = conn.prepareStatement("UPDATE patients SET information = ? WHERE patientId = ?;");

                ps.setString(1, newValue);
                ps.setString(2, patientId);


            }


            if (ps.executeUpdate() == 1) {
                return "Patient journal changed";
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "Patient journal not changed";

    }

}
