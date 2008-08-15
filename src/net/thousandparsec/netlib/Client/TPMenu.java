/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.thousandparsec.netlib.Client;

import org.j4me.ui.Dialog;
import org.j4me.ui.Menu;
import org.j4me.examples.ui.components.ProgressBarExample;
import org.j4me.ui.*;
import org.j4me.ui.components.*;
import java.io.*;
import javax.microedition.lcdui.Graphics;
/**
 * The main User Interface Menu for Thousand Parsec Mobile
 * @author Brendan
 */
public class TPMenu extends Menu {
    /**
     * The device screen from which the menu was called.
     */
    private final DeviceScreen calledFrom;
    public TPMenu(DeviceScreen calledFrom){
        this.calledFrom = calledFrom;
        setTitle("Main Menu");
        setMenuText("Back", "Ok");
        
        ObjectInformation objectInfo= new ObjectInformation(this);
        appendMenuOption(objectInfo);
        
        Messages messages = new Messages(this);
        appendMenuOption(messages);
        
        
 		// Attach an exit option.
	appendMenuOption(
            new MenuItem(){
                public String getText (){
                    return "Exit";
                }
                public void onSelection (){
                    //UI extends midlet . this . notifyDestroyed();
                    //TPMenu.this.notifyDestroyed();
                    TPClientMIDP.exit();
                }
            } 
        );
		
		// Show the menu.       
    }
    public void declineNotify(){
        calledFrom.show();
    }
    /*public void acceptNotify(){
        
    }*/
    
}
