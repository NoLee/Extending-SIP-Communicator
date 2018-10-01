package gov.nist.sip.proxy.extensions;


import java.io.Console;
import java.sql.*;
import java.text.ParseException;

import javax.sip.address.Address;
import javax.sip.address.AddressFactory;
import javax.sip.address.SipURI;
import javax.sip.header.FromHeader;
import javax.sip.header.HeaderFactory;
import javax.sip.header.ToHeader;
import javax.sip.message.MessageFactory;
import javax.sip.message.Request;
import gov.nist.sip.proxy.Proxy;



public class ProxyDecorator extends SipListenerDec {

    Proxy proxy;
    Blocker blocker;
    ConnectToDB database;
    Forwarder forwarder;
    BillingDecideStrategy billing;

    private long startTime;

    public ProxyDecorator(Proxy myproxy) {
        super(myproxy);
        this.database = new ConnectToDB();
        this.blocker = new Blocker(database);
        this.forwarder = new Forwarder(database);
        this.billing = new BillingDecideStrategy(database);
    }

    public void beginCallTimeCount(Request request){
        startTime = System.nanoTime();
        String caller = getCallerName(request);
        String callee = getCalleeName(request);
        billing.setUsers(caller, callee);
    }

    public void endCallTimeCount(){
        long duration = (System.nanoTime() - startTime) / 1000000;
        if (duration > 1200) { duration = duration - 1200; }
        System.out.println("Call time = " + duration + " ms");
        BillingContext cont = billing.decide();

        // Print cost.
        float cost = cont.executeStrategy(duration);

        updateCost(billing.getCaller(),cost);
    }

    public String getUserNamefromHeader(String header)
    {
        String[] parts = header.split("@");
        String[] parts2 = parts[0].split("<");
        String[] parts3 = parts2[1].split(":");
        return parts3[1];
    }

    public String getCallerName(Request request){
        String head = request.getHeader(FromHeader.NAME).toString();
        String callerName = getUserNamefromHeader(head);
        return callerName;
    }

    public String getCalleeName(Request request){
        String head = request.getHeader(ToHeader.NAME).toString();
        String calleeName = getUserNamefromHeader(head);
        return calleeName;
    }

    public boolean checkBlocking(Request request)	{
        String caller = getCallerName(request);
        String callee = getCalleeName(request);
        boolean result = blocker.checkBlock(caller, callee);
        return result;
    }

    public String findForwardTarget (Request request){
        String caller = getCallerName(request);
        String callee = getCalleeName(request);
        String forwardee = forwarder.getTarget(caller,callee);

        return forwardee;
    }

    public boolean checkloops (Request request){
        boolean result = false;
        String check = findForwardTarget(request);
        if ( (check.equals("loop_detected")) || (check.equals("error")) ) {
            result =true;
        }
        return result;
    }

    public Request forwardRequest(Request request, AddressFactory adfactory, HeaderFactory headfactory){
        String target = findForwardTarget(request);
        SipURI test = (SipURI) request.getRequestURI();
        try {
            test.setUser(target);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Address newAddress = adfactory.createAddress(test) ;
        ToHeader toHeader;
        try {
            toHeader = headfactory.createToHeader(newAddress, null);
            Request newRequest = (Request) request.clone();
            newRequest.setHeader(toHeader);
            return newRequest;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return request;
        }

    }

    public void updateCost(String user, float cost){
        Connection conn = database.start();
        PreparedStatement stmt = null;
        String sql = "SELECT total_cost FROM billing WHERE user=?";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, user);
            ResultSet rs = stmt.executeQuery();
            if (rs.first()){
                cost = cost + rs.getFloat("total_cost");
            }
            stmt.close();
            sql = "UPDATE billing SET total_cost=? WHERE user=?";
            stmt = conn.prepareStatement(sql);
            stmt.setFloat(1, cost);
            stmt.setString(2, user);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
        database.close(conn);
    }
}
