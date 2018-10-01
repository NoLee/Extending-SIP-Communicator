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

import net.java.sip.communicator.common.Utils;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.*;

public class CredentialsUI extends JFrame {
    private ConnectToDB database;

  /*  public static void main(String[] args) {
        new CredentialsUI("1", "2");

    }
*/
    public CredentialsUI() {
        database = new ConnectToDB();

        //Create frame
        JFrame frame = new JFrame("Block User");
        frame.setVisible(true);
        //frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

        //Create Panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        frame.add(panel);

        //Name field
        JLabel lb0 = new JLabel("Username");
        panel.add(lb0);
        JTextField nameTxtFld = new JTextField();
        nameTxtFld.setPreferredSize(new Dimension(200, 25));
        panel.add(nameTxtFld);

        //Password field
        JLabel lb4 = new JLabel("Password");
        panel.add(lb4);
        JPasswordField pswdFld = new JPasswordField();
        pswdFld.setPreferredSize(new Dimension(200, 25));
        panel.add(pswdFld);

        //Email field
        JLabel lb1 = new JLabel("E-mail");
        panel.add(lb1);
        JTextField emailTxtFld = new JTextField();
        emailTxtFld.setPreferredSize(new Dimension(200, 25));
        panel.add(emailTxtFld);

        //Address field
        JLabel lb2 = new JLabel("Address");
        panel.add(lb2);
        JTextField addrTxtFld = new JTextField();
        addrTxtFld.setPreferredSize(new Dimension(200, 25));
        panel.add(addrTxtFld);

        //Phone field
        JLabel lb3 = new JLabel("Phone");
        panel.add(lb3);
        JTextField phnTxtFld = new JTextField();
        phnTxtFld.setPreferredSize(new Dimension(200, 25));
        panel.add(phnTxtFld);

        //Sign-up button
        JButton okbtn = new JButton("Sign Up");

        okbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // get text field data
                String username = nameTxtFld.getText();
                String password = String.valueOf(pswdFld.getPassword());
                String email = emailTxtFld.getText();
                String address = addrTxtFld.getText();
                String phone = phnTxtFld.getText();

                // Start connection
                Connection conn = database.start();
                PreparedStatement stmt;

                // Insert user to DB.
                try {
                    // Create query.
                    String sql = "INSERT INTO user_info VALUES (?,SHA1(?),?,?,?)";
                    stmt = conn.prepareStatement(sql);
                    // Set values.
                    stmt.setString(1, username);
                    stmt.setString(2, password);
                    stmt.setString(3, address);
                    stmt.setString(4, email);
                    stmt.setString(5, phone);
                    // Execute.
                    stmt.executeUpdate();
                    stmt.close();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                // Create billing.
                try{
                    // Create query.
                    String sql = "INSERT INTO billing VALUES (?,?)";
                    stmt = conn.prepareStatement(sql);
                    // Set Values.
                    stmt.setString(1, username);
                    stmt.setFloat(2, 0.0f);
                    // Execute.
                    stmt.executeUpdate();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                database.close(conn);
                frame.dispose();
            }
        });

        panel.add(okbtn);
        frame.pack();
    }
}