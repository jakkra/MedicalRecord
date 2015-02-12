package server;/*
 * Created by jakkra on 2015-02-12.
 */

import javax.security.cert.X509Certificate;

public class UserFactory {
    
    public static User buildUser(X509Certificate certificate) {
       // if(certificate.something)
        return new Doctor(certificate);
    }
}
