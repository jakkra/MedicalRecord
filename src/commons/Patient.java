package commons;/*
 * Created by jakkra on 2015-02-12.
 */


public class Patient {

    private final String name;
    private final String department;
    private final String doctor;
    private final String nurse;
    private final String information;
    private String patientId;

    public Patient( String name, String department, String doctor, String nurse, String information) {

        this.name = name;
        this.department = Department;
        this.doctor = doctor;
        this.nurse = nurse;
        this.information = information;
    }


    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getNurse() {
        return nurse;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getInformation() {
        return information;
    }


    /**
     * Build a Patient from a String.
     * Format accepted:
     *
     * name;department;doctor;nurse;information
     * @param s String to be used for building the Patient
     * @return
     */
    public static Patient ParsePatient(String s) {
      
        String[] data = s.split(";");
        return new Patient(data[0], data[1], data[2], data[3], data[4]);

    }

    public String toString() {
        return name + ";" + department + ";" + doctor + ";" + nurse + ";" + information;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
}
