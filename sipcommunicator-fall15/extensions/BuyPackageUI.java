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

import javafx.scene.control.RadioButton;
import net.java.sip.communicator.common.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BuyPackageUI extends JFrame {

    ConnectToDB database;

    private boolean opt1 = false;
    private boolean opt2 = false;

    public static void main(String[] args) {
        new BuyPackageUI();
    }



    private void updateCost(String user, float cost, Connection conn) {
        PreparedStatement stmt = null;
        String sql = "SELECT total_cost FROM billing WHERE user=?";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, user);
            ResultSet rs = stmt.executeQuery();
            if (rs.first()) {
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
    }

    public BuyPackageUI() {

        JFrame window = new JFrame("Package purchase.");


        JPanel mainframe = new JPanel();
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setBounds(0, 0, 300, 200);

        JLabel msg = new JLabel("Option:");
        mainframe.add(msg);
        window.getContentPane().add(mainframe);

        JRadioButton p1 = new JRadioButton("Friend Package", false);
        JRadioButton p2 = new JRadioButton("Premium", false);

        p1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    opt1 = true;
                    opt2 = false;
                    p2.setSelected(false);
                }
                else if(e.getStateChange() == ItemEvent.DESELECTED){
                    opt1 = false;
                }
            }
        });
        p2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    opt1 = false;
                    opt2 = true;
                    p1.setSelected(false);
                }
                else if(e.getStateChange() == ItemEvent.DESELECTED){
                    opt2 = false;
                }
            }
        });



        mainframe.add(p1);
        mainframe.add(p2);

        JButton jb = new JButton();
        jb.setText("Buy!");
        jb.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainframe.add(jb);

        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                database = new ConnectToDB();
                String username = Utils.getProperty("net.java.sip.communicator.sip.USER_NAME");
                Connection conn = database.start();
                PreparedStatement stmt;

                if (opt1) {
                    try {
                        String sql = "SELECT user FROM friends_package WHERE user=? ";
                        stmt = conn.prepareStatement(sql);
                        stmt.setString(1, username);
                        ResultSet rs = stmt.executeQuery();

                        if(!rs.first()){
                            sql = "INSERT INTO friends_package VALUES (?)";
                            stmt = conn.prepareStatement(sql);
                            stmt.setString(1, username);
                            stmt.executeUpdate();

                            updateCost(username, 2.0f, conn);

                        }

                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                } else if (opt2) {
                    try {
                        String sql = "SELECT user FROM premium WHERE user=? ";
                        stmt = conn.prepareStatement(sql);
                        stmt.setString(1, username);
                        ResultSet rs = stmt.executeQuery();

                        if(!rs.first()){
                            sql = "INSERT INTO premium VALUES (?)";
                            stmt = conn.prepareStatement(sql);
                            stmt.setString(1, username);
                            stmt.executeUpdate();

                            updateCost(username, 15.0f, conn);
                        }

                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                }

                database.close(conn);
                // Close window.
                window.dispose();
            }
        });

        window.pack();
        window.setVisible(true);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width / 2 - window.getSize().width / 2, dim.height / 2 - window.getSize().height / 2);


    }

}