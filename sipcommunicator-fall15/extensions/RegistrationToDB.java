/**
 * <p>Description: JAIN-SIP Audio/Video phone application Extensions</p>
 * <p>National Technical University of Athens (http://www.ntua.gr) </p>
 * <p>School of Electrical and Computer Engineering (http://www.ece.ntua.gr))</p>
 * <p>Course: Software Engineering</p>
 * @author Ntallas Yannis (03111418), Pentarakis Manolis (03111048),
 *         Tsitseklis Konstantinos (03111409), Chatzikyriakos Giorgos (03111)
 * @version 1.2
 *
 */
 
package net.java.sip.communicator.extensions;

import java.sql.*;

import net.java.sip.communicator.sip.security.SecurityAuthority;
import net.java.sip.communicator.sip.security.UserCredentials;

public class RegistrationToDB {

    ConnectToDB database = new ConnectToDB();


    public UserCredentials obtainCreds (SecurityAuthority securityAuthority,String realm, UserCredentials defaultCredentials)
    {
        UserCredentials initialCredentials = new UserCredentials();
        boolean reg_flag = false;
        while(!reg_flag){
            initialCredentials = securityAuthority.obtainCredentials(realm,defaultCredentials);
            reg_flag = this.checkCreds(initialCredentials.getUserName(),String.valueOf(initialCredentials.getPassword()));
            if (!reg_flag){
                InvalidCredUI invui = new InvalidCredUI();
                invui.waitForOK();
            }
        }
        return initialCredentials;
    }

    private boolean checkCreds(String name, String password){
        Connection conn =  database.start();
        if (name.equals("") || (password.equals(""))){
            return false;
        }

        String sql = "SELECT * FROM user_info WHERE username=? ";
        PreparedStatement stmt;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.first()) {
                sql = "SELECT * FROM user_info WHERE username=? AND password=SHA1(?) ";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, name);
                stmt.setString(2, password);
                rs = stmt.executeQuery();
                if (rs.first()) {
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                CredentialsUI cre= new CredentialsUI(name,password);
                cre.waitForOK();
            }
            stmt.close();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return true;
    }

}