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

public class ConnectToDB {


    public Connection start() {
        // JDBC driver name and database URL
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        final String DB_URL = "jdbc:mysql://localhost/sipdb";

        //  Database credentials
        final String USER = "root";
        final String PASS = "";
        Connection conn = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected to database...");
            return conn;
        }
        catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
            return conn;
        }
        catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
            return conn;
        }
        finally
        {
            System.out.println("Goodbye!");

        }
    }

    public void close(Connection conn){
        try {
            if (conn!=null){
                conn.close();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
