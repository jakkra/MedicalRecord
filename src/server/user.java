package server;

import util.Patient;

abstract class User{

    private String securityLevel;

    public abstract Patient read(int patientNbr);



}
