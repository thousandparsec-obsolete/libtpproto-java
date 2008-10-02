/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thousandparsec.netlib.Client;
import net.thousandparsec.netlib.Client.GameObjects.Planet;
import javax.microedition.lcdui.*;
import java.util.Vector;
/**
 * A custom canvas used to visually display the thousand parsec universe.
 * @author Brendan
 */
public class StarMapView extends Canvas implements CommandListener{
    Display display;
    Vector list;
    /*
     * The viewport values x1,y1,x2,y2
     */
    int x0 = 0;
    int y0 = 0;
    int x1 = getWidth();
    int y1 = getHeight();
    /*
     * The offset from our original position
     */
    int xOffset=0;
    int yOffset=0;
    /*
     * The Zoom Level: -5 is max zoom out +5 is max zoom in
     */
    int zoomLevel=0;
    
    int zoomX1=x1;
    int zoomY1=y1;
    
    //some constants to work the zoom level off.
    final int viewPort_x1=x1;
    final int viewPort_y1=y1;
    

    public StarMapView(String title, Display d){
        setTitle(title);
        setCommandListener(this);
        setFullScreenMode(true);
        display = d;
        /*
         * Create Menu Commands
         */
        createMenuCommands();

        /*
         * Goes to the object information screen
         */

        list = new FakeObjects().getList();
        
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
        drawPlanets(g);


    }
    /**
     * Draws the planets on to the starmap.
     * @param g
     */
    protected void drawPlanets(Graphics g){
        //White Planets!
        g.setColor(255,255,255);
        System.out.println("Drawing Planets - Viewport Conditions: x0:" +(x0-xOffset) + " x1: " +(x1-xOffset) + " y0: " + (y0-yOffset) + " y1: " + (y1-yOffset) + " xoffset: " + xOffset + " yoffset: " + yOffset);
        for(int i = 0; i < list.size(); i++){
            Planet p = (Planet)list.elementAt(i);
            if(p.getPos().getX()/10 > x0-xOffset && p.getPos().getX()/10 < x1-xOffset){
                if(p.getPos().getY()/10 > y0-yOffset && p.getPos().getY()/10 < y1-yOffset){
                    
                    int x = (int)p.getPos().getX()/10;
                    int y = (int)p.getPos().getY()/10;
                    /*
                     * Zoomed Out
                     */
                    if(zoomLevel <0){
                        
                        
                        
                        g.fillArc((x +xOffset)/(-1 * zoomLevel),(y + yOffset)/(-1 * zoomLevel), 5, 5, 360, 360);
                        g.drawString("("+x+","+y+")", (x +xOffset)/(-1 * zoomLevel), (y + yOffset)/(-1 * zoomLevel), Graphics.TOP|Graphics.LEFT);
                    }
                    /*
                     * Zoomed In
                     */
                    else if(zoomLevel > 0){
                        
                        
                        g.drawString("("+x+","+y+")", (x +xOffset)* zoomLevel, (y + yOffset)*zoomLevel, Graphics.TOP|Graphics.LEFT);
                        g.fillArc((x +xOffset)* zoomLevel,(y + yOffset)*zoomLevel, 5, 5, 360, 360);
                    }
                    /*
                     * No Zoom
                     */
                    else {
                         g.drawString("("+x+","+y+")", (x +xOffset), (y + yOffset), Graphics.TOP|Graphics.LEFT);
                        g.fillArc(x +xOffset,y + yOffset, 5, 5, 360, 360);
                    }
                }
            }
        }
        //g.fillArc(100,100,5,5,45,360);
    }
    /**
     * This method draws the ships on to the starmap
     * @param g
     */
    protected void drawShips(Graphics g){
        
    }
    /**
     * This method draws the square around the currently selected object.
     * @param g the Graphics object from paint()
     */
    protected void drawHighlighter(Graphics g){
        
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
    /**
     * Scrolls Down
     * 
     */
    private void scrollDown(){
        //if(y1 < 1000){
            //y0+=50;
            //y1+=50;
            yOffset -=50;
            repaint();
        //}
    }
    /**
     * Scrolls Up
     */
    private void scrollUp(){
        //if(y0 > 0){
                //y0-=50;
                //y1-=50;
                yOffset +=50;
                repaint();
        //}
    }
    /**
     * Scrolls Left
     */
    private void scrollLeft(){
        //if(x0 > 0){
            //x0-=50;
            //x1-=50;
            xOffset +=50;
            repaint();            
        //}
    }
    /**
     * Scrolls Right
     */
    private void scrollRight(){
        //if(x1 < 1000){
            //x0+=50;
            //x1+=50;
            xOffset -=50;
            repaint();
        //}
    }
    /**
     * Zooms the Map out; the space between objects becomes smaller
     * by half.
     * This method has to account for 2 things.
     * if the zoomlevel is 0, it needs to jump to -1 and then zoom out
     *  otherwise, the zoom button will seemingly do nothing (the same thing) for
     *  0 and -1
     * if the zoom level is more than 0
     *  multiply x1 and y1 by the zoomlevel
     * if the zoom level is less than 0, check if the zoom level is still more than -5
     *  if this is all true, then it multiplies x1 and y1 by -1(zoomLevel) to make it positive.
     */
    private void zoomOut(){
        if(zoomLevel > -4){
            zoomLevel--;
            //x1=viewPort_x1 *2;
            //y1=viewPort_y1 * 2;
            x1*=2;
            y1*=2;
            repaint();
        }
        System.out.println("Zoom Level: " + zoomLevel);
    }
    /**
     * Zooms the Map in; the space between things becomes bigger.
     * This method has to account for three things
     * if the zoomLevel is 0, it needs to jump to 1 and then zoom out, otherwise the zoom button
     *  will seemingly do nothing (the same thing) for 0 and 1.
     * if the zoom level is less than 0
     *  divide x1 and y1 by -1(zoomLevel)
     * if the zoom level is more than 0 and less than 5
     *  divide x1 and y1 by zoomLevel
     */
    private void zoomIn(){
        if(zoomLevel < 4){
            zoomLevel++;
            //x1=viewPort_x1 / 2;
            //y1=viewPort_y1 / 2;
            x1/=2;
            y1/=2;
            repaint();
        }
        System.out.println("Zoom Level: " + zoomLevel);
        
    }

    /**
     * Key is pressed, something happens.
     * Design of this:
     * KEY_NUM2 Scrolls Up
     * KEY_NUM4 Scrolls Left
     * KEY_NUM6 Scrolls Right
     * KEY_NUM8 Scrolls Down
     * 
     * KEY_NUM1 Zooms In
     * KEY_NUM7 Zooms Out
     * @param keycode the code of the key pressed.
     */
    protected void keyPressed(int keycode){
        if(keycode == KEY_NUM6){
            scrollRight();
        }
        if(keycode == KEY_NUM4){
            scrollLeft();
            
        }
        if(keycode == KEY_NUM8){
            scrollDown();
            
        }
        if(keycode == KEY_NUM2){
            scrollUp();
            
        }
        if(keycode== KEY_NUM1){
            zoomIn();
        }
        if(keycode==KEY_NUM7){
            zoomOut();
        }
        
    }
    /**
     * Creates the menu commands accessed by the soft menu buttons
     */
    private void createMenuCommands(){
        Command exitCommand = new Command("EXIT", Command.EXIT,1);
        addCommand(exitCommand);
        
        Command selectCommand = new Command("Select", Command.OK,1);
        addCommand(selectCommand);
        
        Command messagesCommand = new Command("Messages", Command.OK, 2);
        addCommand(messagesCommand);
    }
}
