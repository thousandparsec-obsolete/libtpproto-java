/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.thousandparsec.netlib.Client;

import org.j4me.ui.*;

import org.j4me.ui.components.*;
import java.util.Vector;
/**
 *
 * @author Brendan
 */
public class Messages extends Dialog {
    /**
     * The previous screen
     */
    private final DeviceScreen previous;
    
    
    /*
     * A list of strings of messages
     */
    Vector messageList; 
    /*
     * Current message
     */
    String currentMessage;
    String firstMessage="This is a Test Message\n You can use the left and right buttons on your keypad to navigate through the messages!";
    String testStringOne = "This is the First Test String";
    String testStringTwo = "This is the Second Message";
    String testStringThree = "This is the Third Message";
    String defaultMessage = "No messages to display.";
    Label message;
    
    
    public Messages(DeviceScreen previous){
        this.previous = previous;
        setTitle("Messages");
        setMenuText("Back", "Delete Message");
        messageList = new Vector();
        messageList.addElement(firstMessage);
        messageList.addElement(testStringOne);
        messageList.addElement(testStringTwo);
        messageList.addElement(testStringThree);
        currentMessage=(String) messageList.elementAt(0);
        message = new Label(currentMessage);
        append(message);

    }
    
    public void declineNotify(){
        //back button
        previous.show();
    }
    public void acceptNotify(){
        //delete the currentMessage
        removeCurrentMessage();
    }
    protected void keyPressed (int key)
    {
        //find out which key was pressed
        updateMessage(key);
        //continue processing the event
        super.keyPressed(key);
    }
    protected void keyRepeated(int key){
        updateMessage(key);
        // Continue processing the key event.
        super.keyRepeated( key );
    }
    /*
     * Allows the Messages to be Navigatable
     * 
     */
    protected void updateMessage(int key){
        if ( key == LEFT){
            if(messageList.indexOf(currentMessage)>0){
                currentMessage = (String) messageList.elementAt(messageList.indexOf(currentMessage)-1);
                
                message.setLabel(currentMessage);
                message.repaint();
                
            }
        }
        if (key == RIGHT){
            if(messageList.indexOf(currentMessage)< messageList.size()-1){
                currentMessage = (String) messageList.elementAt(messageList.indexOf(currentMessage)+1);
                
                message.setLabel(currentMessage);
                message.repaint();
            }
            
        }
        
	
    }
    public void AddMessage(String message){
        messageList.addElement(message);
    }
    public void removeCurrentMessage(){
        
        int index = messageList.indexOf(currentMessage);
        if(index ==-1){
         return;
        }
        messageList.removeElementAt(index);
        if(messageList.size()==0){
            message.setLabel(defaultMessage);
            message.repaint();
        }
        else{
            //if(index == messageList.size()-1){
                currentMessage = (String) messageList.elementAt(index);
                message.setLabel(currentMessage);
                message.repaint();
            //}
            //currentMessage = (String) messageList.elementAt(message)
        }
        
    }

}
