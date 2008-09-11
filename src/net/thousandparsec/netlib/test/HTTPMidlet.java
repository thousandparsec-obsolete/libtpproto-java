/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.thousandparsec.netlib.test;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.io.Connector;
import javax.microedition.io.*;

/**
 * @author Brendan
 */
public class HTTPMidlet extends MIDlet {
    public void startApp() {
        try{
            Connection hc = Connector.open("http://www.wirelessdevnet.com");
            System.out.println("Connection Opened");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }
}
