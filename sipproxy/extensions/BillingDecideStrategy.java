package gov.nist.sip.proxy.extensions;

import java.sql.*;

public class BillingDecideStrategy {

    private String caller, callee;
    private ConnectToDB database;

    public void setUsers(String caller, String callee) {
        this.caller = caller;
        this.callee = callee;
    }

    public String getCaller() {
        return this.caller;
    }

    public BillingDecideStrategy(ConnectToDB database) {
        this.database = database;

    }

    public BillingContext decide() {
        BillingContext context;
        if (premium()) {
            context = new BillingContext(new BillingPremium());
        } else if (friends()) {
            context = new BillingContext(new BillingFriends());
        } else {
            //standard billing
            context = new BillingContext(new BillingStandard());
        }
        return context;
    }

    public boolean premium() {

        Connection conn = database.start();
        PreparedStatement stmt = null;
        String sql = "SELECT * FROM premium WHERE user=?";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, caller);
            ResultSet rs = stmt.executeQuery();
            if (rs.first()) {
                return true;
            }
            stmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        database.close(conn);
        return false;
    }

    public boolean friends() {
        Connection conn = database.start();
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT user FROM friends_package WHERE user=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, caller);
            ResultSet rs = stmt.executeQuery();

            if (rs.first()) {

                sql = "SELECT * FROM friends WHERE friend1=? AND friend2=?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, caller);
                stmt.setString(2, callee);
                rs = stmt.executeQuery();

                if (rs.first()) {
                    return true;
                }
            }
            stmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        database.close(conn);
        return false;
    }

}

