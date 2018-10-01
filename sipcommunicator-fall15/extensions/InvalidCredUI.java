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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by yandall on 21/3/2016.
 */
public class InvalidCredUI extends JFrame {

    private boolean flag=true;
    public static void main(String[] args) { new InvalidCredUI();  }

    public void waitForOK(){
        //wait to close the window
        while(flag){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    public InvalidCredUI(){

        JFrame window = new JFrame("Invalid Credentials!");


        JPanel mainframe=new JPanel();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0,0,300,200);

        JLabel msg = new JLabel("Username or password is incorrect.");
        msg.setForeground(Color.red);
        mainframe.add(msg);
        window.getContentPane().add(mainframe);

        JButton jb=new JButton();
        jb.setText("OK");
        jb.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainframe.add(jb);

        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close window.
                window.dispose();
                flag=false;
            }
        });

        window.pack();
        window.setVisible(true);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width / 2 - window.getSize().width / 2, dim.height / 2 - window.getSize().height / 2);


    }

}