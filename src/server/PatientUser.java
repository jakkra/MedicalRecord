package server;/*
 * Created by jakkra on 2015-02-12.
 */

import javax.security.cert.X509Certificate;

public class PatientUser extends User {
    
    public PatientUser(X509Certificate cert) {
        super(cert);
    }

    public String getName() {
        return null;
    }

    public String getDepartment() {
        return null;
    }

    public String getNurse() {
        return null;
    }

    public String getDoctor() {
        return null;
    }
}
