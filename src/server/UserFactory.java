package server;/*
 * Created by jakkra on 2015-02-12.
 */

import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.security.cert.X509Certificate;

public class UserFactory {


    /**
     * CN Field should have the name of the person who connect
     * O Field should contain the department of the person who connect
     * OU Field should contain the type of the person connecting (Doctor/Agency/Nurse/PatientUser)
     *
     * @param certificate
     * @return
     * @throws InvalidNameException
     */
    public static User buildUser(X509Certificate certificate) throws InvalidNameException {
        String userType = "";
        String userName = "";
        String department = "";

        String certData = certificate.getSubjectDN().getName();
        LdapName ldapName = new LdapName(certData);
        for (Rdn rdn : ldapName.getRdns()) {
            if (rdn.getType().equalsIgnoreCase("CN")) {
                userName = rdn.getValue().toString();
            }
            if (rdn.getType().equalsIgnoreCase("OU")) {
                userType = rdn.getValue().toString();
            }
            if (rdn.getType().equalsIgnoreCase("O")) {
                department = rdn.getValue().toString();
            }

        }

        if (userType.equals("Doctor")) {
            return new Doctor(userName, department);
        } else if (userType.equals("Agency")) {
            return new Agency(userName, department);
        } else if (userType.equals("Nurse")) {
            return new Nurse(userName, department);
        } else if (userType.equals("PatientUser")) {
            return new PatientUser(userName, department);
        }
        System.err.println("Should never be called");
        return null;
    }
}
