/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thousandparsec.netlib.Client;
import javax.microedition.lcdui.*;
/**
 * A custom canvas used to visually display the thousand parsec universe.
 * @author Brendan
 */
public class StarMapView extends Canvas implements CommandListener{
    Display display;
    public StarMapView(String title, Display d){
        setTitle(title);
        setCommandListener(this);
        //setFullScreenMode(true);
        display = d;
        
        Command exitCommand = new Command("EXIT", Command.EXIT,1);
        /*
         * Goes to the object information screen
         */
        Command selectCommand = new Command("Select", Command.OK,1);
        Command messagesCommand = new Command("Messages", Command.OK, 2);
        addCommand(exitCommand);
        addCommand(selectCommand);
        addCommand(messagesCommand);
        repaint();
    }
    /**
     * Handles the custom drawing of this canvas.
     * This object first should paint the screen black, then draw at specific x,y
     * values, graphical representations of objects in the thousand parsec universe
     * @param g the Graphics Object
     */
    protected void paint(Graphics g){
        g.setColor(0,0,0);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
    /**
     * handles specific soft-button commands that occur.
     */ 
    public void commandAction(Command command, Displayable displayable) {
        String label = command.getLabel();
        if(label.equals("Select")){
            //display.setCurrent(new ObjectInformationScreen("Object Information", display, this));
        }
        if(label.equals("Messages")){
            display.setCurrent(new MessagesView("System Messages", display, this));
        }
        if(label.equals("Exit")){
            
        }
        
    }
}
