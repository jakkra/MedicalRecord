package server;/*
 * Created by jakkra on 2015-02-12.
 */

import javax.security.cert.X509Certificate;

public class PatientUser extends User {
    
    public PatientUser(X509Certificate cert) {
        super(cert);
    }


}
