/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.thousandparsec.netlib.Client;
import javax.microedition.lcdui.*;

/**
 * The Object download interface for thousand parsec.
 * @author Brendan
 */
public class DownloadView extends Form implements CommandListener{
    Display display;
    /**
     * Constructs a new download interface
     * @param title the title of the interface
     * @param d the midlet's display object
     */
    public DownloadView(String title, Display d){
        super(title);
        display = d;
        Command exitCommand = new Command("EXIT", Command.EXIT, 1);
        Command loginCommand = new Command("NEXT", Command.OK, 1);
        Gauge g = new Gauge("Progress",false,10,5);
        addCommand(exitCommand);
        addCommand(loginCommand);
        append(g);
        setCommandListener(this);
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
}
