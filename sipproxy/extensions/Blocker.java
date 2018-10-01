package gov.nist.sip.proxy.extensions;

import java.sql.*;

public class Blocker {
    private ConnectToDB database;;

    public Blocker(ConnectToDB database) {
        this.database=database;
    }

    public boolean checkBlock(String aCaller, String aCallee){
        boolean result=false;
        PreparedStatement stmt = null;
        String sql = "SELECT * FROM blocks WHERE blocker=? AND blocked=?";
        try {
            Connection conn= database.start();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, aCallee); // check if callee blocks the caller
            stmt.setString(2, aCaller);
            ResultSet rs = stmt.executeQuery();
            if (rs.first()) {
                result=true;
            }
            stmt.close();
            database.close(conn);
            return result;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
    }

}
