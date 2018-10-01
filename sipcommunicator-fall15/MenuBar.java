/* ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2000 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Apache" and "Apache Software Foundation" must
 *    not be used to endorse or promote products derived from this
 *    software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    nor may "Apache" appear in their name, without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 * Portions of this software are based upon public domain software
 * originally written at the National Center for Supercomputing Applications,
 * University of Illinois, Urbana-Champaign.
 */
package net.java.sip.communicator.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JMenuItem;
import net.java.sip.communicator.common.Utils;
import java.awt.event.KeyEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import net.java.sip.communicator.extensions.*;
import net.java.sip.communicator.sip.security.UserCredentials;

/**
 * <p>Title: SIP COMMUNICATOR</p>
 * <p>Description:JAIN-SIP Audio/Video phone application</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Organisation: LSIIT laboratory (http://lsiit.u-strasbg.fr) </p>
 * <p>Network Research Team (http://www-r2.u-strasbg.fr))</p>
 * <p>Louis Pasteur University - Strasbourg - France</p>
 * @author Emil Ivov (http://www.emcho.com)
 * @version 1.1
 *
 */
class MenuBar
        extends JMenuBar
{
    //// TODO: 20/3/2016
    JMenu callMenu = new JMenu("Call");
    JMenu editMenu = new JMenu("Edit");
    JMenu settingsMenu = new JMenu("Settings");
    JMenu helpMenu = new JMenu("Help");
    JMenuItem blockUser = new JMenuItem("Blocking");
    JMenuItem forwardCall = new JMenuItem("Call Forwarding");
    JMenuItem billingInfo = new JMenuItem("Billing Info");
    JMenuItem friends = new JMenuItem("Friends");
    JMenuItem buyPackage = new JMenuItem("Buy Package");


    Action exitAction;
    private ConfigFrame configFrame;

    MenuBar()
    {
        callMenu.setMnemonic('C');
        editMenu.setMnemonic('E');
        settingsMenu.setMnemonic('S');
        helpMenu.setMnemonic('H');
        add(callMenu);
        add(editMenu);
        add(settingsMenu);
        add(helpMenu);
        add(billingInfo);
        add(friends);
        add(buyPackage)
;
        // TODO: 20/3/2016
        editMenu.add(blockUser);
        blockUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BlockUI b = new BlockUI();
            }
        });
        editMenu.add(forwardCall);
        forwardCall.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ForwardUI f  = new ForwardUI();
            }
        });
        editMenu.add(billingInfo);
        billingInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BillingUI bl  = new BillingUI();
            }
        });
        editMenu.add(friends);
        friends.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FriendUI fr = new FriendUI();
            }
        });

        editMenu.add(buyPackage);
        buyPackage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BuyPackageUI bp = new BuyPackageUI();
            }
        });

    }

    public void setConfigFrame(ConfigFrame configFrame)
    {
        this.configFrame = configFrame;
    }

    void addConfigCallAction(Action action)
    {
        JMenuItem config = new JMenuItem(action);
        config.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0));

        settingsMenu.add(config);
    }

    void addCallAction(Action action)
    {
        addCallAction(action, -1);
    }

    void addCallAction(Action action, int accelerator)
    {
        JMenuItem voiceMail = new JMenuItem(action);
        if(accelerator != -1)
            voiceMail.setAccelerator(KeyStroke.getKeyStroke(accelerator, 0));
        callMenu.add(voiceMail);
    }

    void addExitCallAction(Action action)
    {
        callMenu.addSeparator();

        JMenuItem menuItem = new JMenuItem(action);
        menuItem.setMnemonic('X');

        callMenu.add(menuItem);
    }

    void addConfigMediaAction(Action action)
    {
        JMenuItem mItem = new JMenuItem(action);
        mItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, KeyEvent.CTRL_MASK | KeyEvent.ALT_MASK));
        settingsMenu.add(mItem);
    }

    void addSetupWizardAction(Action action)
    {
        JMenuItem mItem = new JMenuItem(action);
        mItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_MASK | KeyEvent.ALT_MASK));
        mItem.setMnemonic('S');
        settingsMenu.addSeparator();
        settingsMenu.add(mItem);
    }

    /**
     * Creates a JMenuItem using the specified <code>action</code> sets A as
     * its mnemonic character and adds it to the settings menu.
     * @param action the about action
     */
    public void addAbout(Action action)
    {
        JMenuItem mItem = new JMenuItem(action);
        mItem.setMnemonic('A');
        helpMenu.addSeparator();
        helpMenu.add(mItem);
    }

/*
    void addShowTracesAction(Action action)
    {
        JMenuItem traces = new JMenuItem(action);
        traces.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
        settingsMenu.add(traces);
    }
*/
}
