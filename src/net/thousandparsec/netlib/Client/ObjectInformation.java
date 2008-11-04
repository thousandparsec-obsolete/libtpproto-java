/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.thousandparsec.netlib.Client;



import java.io.IOException;
import javax.microedition.lcdui.*;
import net.thousandparsec.netlib.Client.GameObjects.Planet;
import net.thousandparsec.netlib.Client.GameObjects.Fleet;


/**
 *
 * @author Brendan
 */
public class ObjectInformation extends Form implements CommandListener{
    net.thousandparsec.netlib.tp03.Object object;
    /*
     * Soft Button Commands
     */
    Command backCommand;
    Command ordersCommand;
    
    Display display;
    StarMapView previousScreen;
    
    public ObjectInformation(net.thousandparsec.netlib.tp03.Object o, Display d, StarMapView prev){
        super("viewing " + o.getName());
        object = o;
        display = d;
        previousScreen = prev;
        setCommandListener(this);
        /*
         * Set the Commands
         */
        backCommand = new Command("Back", Command.EXIT,1);
        addCommand(backCommand);
        ordersCommand = new Command("View Orders", Command.OK, 1);
        addCommand(ordersCommand);

        
        switch(o.getOtype()){
            case 3:
                Planet p = (Planet) o;
                append("Type: Planet\n");
                append("Location:\n");
                append("x: " + p.getPos().getX());
                append("y: " + p.getPos().getY()+"\n");
                append("Resources:\n");
                
                for(int i = 0; i < p.getResources().size(); i++){
                    
                    append("Type: " + ((Planet.PlanetResource)p.getResources().elementAt(i)).getResourceID()
                            +" Units Available: " + ((Planet.PlanetResource)p.getResources().elementAt(i)).getMaximumMinableUnits()
                        +"\n");
       
                }
                break;
            case 4:
                Fleet f = (Fleet) o;
                append("Type: Fleet\n");
                append("Location:\n");
                append("x: " + f.getPos().getX());
                append("y: " + f.getPos().getY()+"\n");
                
                for(int i = 0; i < f.getShips().size(); i++){
                    append("Ship Type: " + ((Fleet.Ship)f.getShips().elementAt(i)).getShipType()
                            + " Number of Ships: " + ((Fleet.Ship)f.getShips().elementAt(i)).getNumberofShips()
                        
                        +"\n");
                }
                append("Damage:" + f.getDamage());
                break;
                
                
        }
            
        
    }
    
    public void commandAction(Command command, Displayable displayable){
        String label = command.getLabel();
        if(label.equals("Back")){
            display.setCurrent(previousScreen);
        }
        if(label.equals("View Orders")){
            
        }
        
    }
}
