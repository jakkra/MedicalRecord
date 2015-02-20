package server;/*
 * Created by jakkra on 2015-02-12.
 */

import commons.Patient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


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
   * @param userName
   *            The user name.
   * @param password
   *            The user's password.
   * @return true if the connection succeeded, false if the supplied user name
   *         and password were not recognized. Returns false also if the JDBC
   *         driver isn't found.
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

    if( ps.execute() ){

      return "Delete succesfull";
    }

    } catch (SQLException e) {

      e.printStackTrace();
    }

    return "Delete not succesfull";


    public boolean delete(String patientName) {
        return true;
    }

    public String add(String name,String department,String nurse, String doctor){

      PreparedStatement ps;

    try {

      ps = conn
        .prepareStatement("INSERT INTO patients VALUES ( ?, ?, ?);");

    ps.setString(1, name);
    ps.setString(2, department);
    ps.setString(3, nurse);
    ps.setString(3, doctor);

    if( ps.execute()){

      return "Add complete";
    }


    } catch (SQLException e) {

      e.printStackTrace();
    }
    return "Add not complete";    }
    /**
     * @param id id of patient to get from database
     * @return Patient object containing fields from the database
     */
    public Patient get(String patientId){


      PreparedStatement ps;
    try {

      ps = conn.prepareStatement("Select department, nurse, doctor FROM patients WHERE patientId = ?;");

      ps.setString(1, patientId);

      ResultSet rs = ps.executeQuery();

      if(rs.next()){

      return
          new Patient(patientId,
          rs.getString("department"),
          rs.getString("nurse"),
          rs.getString("doctor"));

      }

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return null;

    }

  public String getAll(){

      ArrayList<Patient> list = new ArrayList<Patient>();


      PreparedStatement ps;
    try {

      ps = conn.prepareStatement("Select * FROM patients;");

      ResultSet rs = ps.executeQuery();

      while(rs.next()){

       list.add(new Patient(
          rs.getString("patientId"),
          rs.getString("department"),
          rs.getString("nurse"),
          rs.getString("doctor")
          ));

      }

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }


      return list.toString();

    public Patient get(String patientName) {
        return null;
    }

    public String movify(String info1,String info2,String info3){


      PreparedStatement ps;
    try {
      ps = conn
          .prepareStatement("UPDATE patients SET ? = ? WHERE patientId = ?");


    ps.setString(1, info1);
    ps.setString(2, info2);
    ps.setString(3, info3);



    if( ps.execute() ){

      return "Patient journal changed";
    }

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return "Patient journal not changed";
    }
    public boolean modifyPatient(Patient patient) {
        return true;

    }

}
