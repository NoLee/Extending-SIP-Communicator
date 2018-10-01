package gov.nist.sip.proxy.extensions;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class Forwarder {
    private ConnectToDB database;

    public Forwarder(ConnectToDB database) {
        this.database = database;
    }

    public String getTarget(String caller, String callee){
        String target;
        PreparedStatement stmt = null;
        boolean query_flag = true;
        Set<String> seen = new HashSet<String>();
        seen.add(caller);
        seen.add(callee);
        try {
            Connection conn= database.start();
            while (query_flag){
                String sql = "SELECT toUser FROM forwards WHERE fromUser=?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, callee);
                ResultSet rs = stmt.executeQuery();
                if (rs.first()) {
                    callee = rs.getString("toUser");
                    if (seen.contains(callee)){
                        break;
                    }
                    seen.add(callee);
                }
                else{
                    query_flag=false;
                }

            }
            stmt.close();

            database.close(conn);
            if (query_flag){
                // loop detected
                return("loop_detected");
            }
            else return(callee);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return ("exception\n");
        }

    }

}
