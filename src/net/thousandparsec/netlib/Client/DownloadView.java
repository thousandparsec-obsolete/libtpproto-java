/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.thousandparsec.netlib.Client;
import javax.microedition.lcdui.*;
import java.util.Vector;

/**
 * The Object download interface for thousand parsec.
 * @author Brendan
 */
public class DownloadView extends Form implements CommandListener{
    private static DownloadView instance;
    Display display;
    static Vector messageList;
    /**
     * Constructs a new download interface
     * @param title the title of the interface
     * @param d the midlet's display object
     */
    public DownloadView(String title, Display d){
        super(title);
        instance = this;
        display = d;
        Command exitCommand = new Command("EXIT", Command.EXIT, 1);
        Command loginCommand = new Command("NEXT", Command.OK, 1);
        Gauge g = new Gauge("Progress",false,10,5);
        addCommand(exitCommand);
        addCommand(loginCommand);
        append(g);
        setCommandListener(this);
        messageList = new Vector();
        messageList.addElement("AA");
        messageList.addElement("AB");
        messageList.addElement("AC");
        messageList.addElement("AD");
    }
    /**
     * handles specific soft-button commands that occur.
     */
    public void commandAction(Command command, Displayable displayable) {
        String label = command.getLabel();
        if(label.equals("NEXT")){
            display.setCurrent(new StarMapView("Map", display));
            
        }
        
    }

    public static DownloadView getInstance() {
        return instance;
    }
    /**
     * Returns the messages that were downloaded from the server
     * @return messageList - the vector containing a list of the messages downloaded from the server.
     */
    public static Vector getMessages(){
        return messageList;
    }
}
