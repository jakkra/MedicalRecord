package server;/*
 * Created by jakkra on 2015-02-12.
 */

import javax.security.cert.X509Certificate;

public class Agency extends User {
    public Agency(X509Certificate cert) {
        super(cert);
    }
}
