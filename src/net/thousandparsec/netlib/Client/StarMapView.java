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
    /*
     * LCD Display Object
     */
    Display display;
    
    /*
     * A List of Space Objects
     */
    Vector list;
    
    /*
     * The viewport values x0,y0,x1,y1
     * 
     * Y1 the starmap ends at 15 pixels off of the whole screen height, so that
     * a menu bar can be drawn.
     */
    int x0 = 0;
    int y0 = 0;
    int x1 = getWidth();
    int y1 = getHeight()-15;
    
    /*
     * The offset from our original position
     */
    int xOffset=0;
    int yOffset=0;
    
    /*
     * The Zoom Level: -5 is max zoom out +5 is max zoom in
     */
    int zoomLevel=0;
    
    /*
     * Toggles the map scrolling
     */
    boolean scroll = true;
    
    /*
     * A Reference to the currently selected object
     */
    net.thousandparsec.netlib.tp03.Object current_object;
    
    /*
     * The Previous Object, for object navigation
     */
    net.thousandparsec.netlib.tp03.Object previous_object;
    /*
     * The Previous Key pressed for selection
     */
    int prev;
    

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
        current_object = (net.thousandparsec.netlib.tp03.Object)list.elementAt(0);
        
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
        paintBottomBar(g);
        //drawHighlighter(g);


    }
    /**
     * Draws the planets on to the starmap.
     * @param g the graphics object
     */
    protected void drawPlanets(Graphics g){
        //White Planets!
        //g.setColor(255,255,255);
        //System.out.println("Drawing Planets - Viewport Conditions: x0:" +(x0-xOffset) + " x1: " +(x1-xOffset) + " y0: " + (y0-yOffset) + " y1: " + (y1-yOffset) + " xoffset: " + xOffset + " yoffset: " + yOffset);
        for(int i = 0; i < list.size(); i++){
            Planet p = (Planet)list.elementAt(i);
            if(p.equals(current_object)){
                g.setColor(56,255,0);
            }
            else{
                g.setColor(255,255,255);
            }
            if(p.getPos().getX()/10 > x0-xOffset && p.getPos().getX()/10 < x1-xOffset){
                if(p.getPos().getY()/10 > y0-yOffset && p.getPos().getY()/10 < y1-yOffset){
                    int x = (int)p.getPos().getX()/10;
                    int y = (int)p.getPos().getY()/10;
                    /*
                     * Zoomed Out
                     */
                    if(zoomLevel <0){
                        g.fillArc((x +xOffset)/(-1 * zoomLevel),(y + yOffset)/(-1 * zoomLevel), 5, 5, 360, 360);
                        //g.drawString("("+x+","+y+")", (x +xOffset)/(-1 * zoomLevel), (y + yOffset)/(-1 * zoomLevel), Graphics.TOP|Graphics.LEFT);
                        g.drawString(p.getName(), (x +xOffset)/(-1 * zoomLevel), (y + yOffset)/(-1 * zoomLevel), Graphics.TOP|Graphics.LEFT);
                    }
                    /*
                     * Zoomed In
                     */
                    else if(zoomLevel > 0){
                        //g.drawString("("+x+","+y+")", (x +xOffset)* zoomLevel, (y + yOffset)*zoomLevel, Graphics.TOP|Graphics.LEFT);
                        g.drawString(p.getName(), (x +xOffset)* zoomLevel, (y + yOffset)*zoomLevel, Graphics.TOP|Graphics.LEFT);
                        g.fillArc((x +xOffset)* zoomLevel,(y + yOffset)*zoomLevel, 5, 5, 360, 360);
                    }
                    /*
                     * No Zoom
                     */
                    else {
                        //g.drawString("("+x+","+y+")", (x +xOffset), (y + yOffset), Graphics.TOP|Graphics.LEFT);
                        g.drawString(p.getName(), (x +xOffset), (y + yOffset), Graphics.TOP|Graphics.LEFT);
                        g.fillArc(x +xOffset,y + yOffset, 5, 5, 360, 360);
                    }
                }
            }
        }
        //g.fillArc(100,100,5,5,45,360);
    }
    /*
     * A bar at the bottom of the screen, contains the menus and the current selected Object's name.
     */
    protected void paintBottomBar(Graphics g){
        //White Bottom Bar
        g.setColor(255,255,255);
        g.fillRect(0, getHeight()-15, getWidth(), getHeight());
        //Black is back! (For the text)
        g.setColor(0,0,0);
        g.drawString(current_object.getName(),getWidth()/2,getHeight()-1,Graphics.BOTTOM|Graphics.HCENTER);
        //On the Motorola V3x, the menu is bound to the left soft button, but on other phones, such as a Nokia, the menu button may be the right one.
        //I have no solution for this yet.
        //g.drawString("Menu",0, getHeight()-1, Graphics.BOTTOM|Graphics.LEFT);
     
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
        g.setColor(56, 255, 0);
        int pos_x = (int)current_object.getPos().getX()/10;
        int pos_y = (int)current_object.getPos().getY()/10;
        g.drawRect(pos_x-5,pos_y-5,pos_x+10,pos_y +10);
        
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
        yOffset -=50;
        repaint();
        
    }
    /**
     * Scrolls Up
     */
    private void scrollUp(){
        yOffset +=50;
        repaint();

    }
    /**
     * Scrolls Left
     */
    private void scrollLeft(){
        xOffset +=50;
        repaint();            

    }
    /**
     * Scrolls Right
     */
    private void scrollRight(){
        xOffset -=50;
        repaint();
        
    }
    /**
     * Zooms the Map out; the space between objects becomes smaller by half.
     * Has to check for one thing; if the zoom level is greater than -4, it zooms out.
     */
    private void zoomOut(){
        if(zoomLevel > -4){
            zoomLevel--;
            x1*=2;
            y1*=2;
            repaint();
        }
        //System.out.println("Zoom Level: " + zoomLevel);
    }
    /**
     * Zooms the Map in; the space between things becomes bigger.
     * Has to check for one thing. If the zoom level is less than 4
     */
    private void zoomIn(){
        if(zoomLevel < 4){
            zoomLevel++;
            x1/=2;
            y1/=2;
            repaint();
        }
        //System.out.println("Zoom Level: " + zoomLevel);
        
    }
    
    private void selectUp(){
        net.thousandparsec.netlib.tp03.Object temp;
        /*
         * The closest object found so far
         */
        net.thousandparsec.netlib.tp03.Object closest_object = null;
        /*
         * The distance from the current object
         */
        long distance=-1;
        /*
         * First check if we're just going backwards
         */  
        if(prev == KEY_NUM8 && previous_object !=null){
            temp = previous_object;
            previous_object = current_object;
            current_object = temp;

        }
        /* Check if the Y value is <= the current object's Y value.
         * 
         * If true; find the distance between the objects
         */
        else{
            for(int i = 0; i < list.size(); i++){
                if((temp = (net.thousandparsec.netlib.tp03.Object)list.elementAt(i)).getPos().getY() <= current_object.getPos().getY() && !temp.equals(current_object)){
                    //if the object isn't just going backwards
                    if(!temp.equals(previous_object)){
                        long tempX = temp.getPos().getX() - current_object.getPos().getX();
                        long tempY = temp.getPos().getY() - current_object.getPos().getY();
                        int pyDist =(int)Math.sqrt((tempX*tempX)+(tempY*tempY));
                        if(distance==-1){
                            distance = pyDist;
                            closest_object = temp;
                        }
                        else{
                            if(pyDist < distance){
                                closest_object = temp;
                                distance=pyDist;
                            }
                        }
                    }
                }
            }
            //if closest object is null, no object is found - just stay as we are.
            if(closest_object != null){
                previous_object = current_object;
                current_object = closest_object;
            }

        }
        prev = KEY_NUM2;
        repaint();            
    }
    private void selectDown(){
        net.thousandparsec.netlib.tp03.Object temp;
        /*
         * The closest object found so far
         */
        net.thousandparsec.netlib.tp03.Object closest_object = null;
        /*
         * The distance from the current object
         */
        long distance=-1;
        /*
         * First check if we're just going backwards
         */  
        if(prev == KEY_NUM2 && previous_object !=null){
            temp = previous_object;
            previous_object = current_object;
            current_object = temp;
        }
        else{
            for(int i = 0; i < list.size(); i++){
                if((temp = (net.thousandparsec.netlib.tp03.Object)list.elementAt(i)).getPos().getY() >= current_object.getPos().getY() && !temp.equals(current_object)){
                    //if the object isn't just going backwards
                    if(!temp.equals(previous_object)){
                        long tempX = temp.getPos().getX() - current_object.getPos().getX();
                        long tempY = temp.getPos().getY() - current_object.getPos().getY();
                        int pyDist =(int)Math.sqrt((tempX*tempX)+(tempY*tempY));
                        if(distance==-1){
                            distance = pyDist;
                            closest_object = temp;
                        }
                        else{
                            if(pyDist < distance){
                                closest_object = temp;
                                distance=pyDist;
                            }
                        }
                    }
                }
            }
            //if closest object is null, no object is found - just stay as we are.
            if(closest_object != null){
                previous_object = current_object;
                current_object = closest_object;
            }
        }
        prev = KEY_NUM8;
        repaint();
    }    
    private void selectLeft(){
        net.thousandparsec.netlib.tp03.Object temp;
        /*
         * The closest object found so far
         */
        net.thousandparsec.netlib.tp03.Object closest_object = null;
        /*
         * The distance from the current object
         */
        long distance=-1;
        /*
         * First check if we're just going backwards
         */  
        if(prev == KEY_NUM6 && previous_object !=null){
            temp = previous_object;
            previous_object = current_object;
            current_object = temp;

        }
        else{
            for(int i = 0; i < list.size(); i++){
                if((temp = (net.thousandparsec.netlib.tp03.Object)list.elementAt(i)).getPos().getX() <= current_object.getPos().getX() && !temp.equals(current_object)){
                    if(!temp.equals(previous_object)){
                        long tempX = temp.getPos().getX() - current_object.getPos().getX();
                        long tempY = temp.getPos().getY() - current_object.getPos().getY();
                        int pyDist =(int)Math.sqrt((tempX*tempX)+(tempY*tempY));
                        if(distance==-1){
                            distance = pyDist;
                            closest_object = temp;
                        }
                        else{
                            if(pyDist < distance){
                                closest_object = temp;
                                distance=pyDist;
                            }
                        }
                    }
                }
            }
            //if closest object is null, no object is found - just stay as we are.
            if(closest_object != null){
                previous_object = current_object;
                current_object = closest_object;
            }
        }

        prev = KEY_NUM4;
        repaint();
    
    }
    private void selectRight(){
        net.thousandparsec.netlib.tp03.Object temp;
        /*
         * The closest object found so far
         */
        net.thousandparsec.netlib.tp03.Object closest_object = null;
        /*
         * The distance from the current object
         */
        long distance=-1;
        /*
         * First check if we're just going backwards
         */  
        if(prev == KEY_NUM4 && previous_object !=null){
            temp = previous_object;
            previous_object = current_object;
            current_object = temp;

        }
        else{
            for(int i = 0; i < list.size(); i++){
                if((temp = (net.thousandparsec.netlib.tp03.Object)list.elementAt(i)).getPos().getX() >= current_object.getPos().getX() && !temp.equals(current_object)){
                    if(!temp.equals(previous_object)){
                        long tempX = temp.getPos().getX() - current_object.getPos().getX();
                        long tempY = temp.getPos().getY() - current_object.getPos().getY();
                        int pyDist =(int)Math.sqrt((tempX*tempX)+(tempY*tempY));
                        if(distance==-1){
                            distance = pyDist;
                            closest_object = temp;
                        }
                        else{
                            if(pyDist < distance){
                                closest_object = temp;
                                distance=pyDist;
                            }
                        }
                    }
                }
            }
            //if closest object is null, no object is found - just stay as we are.
            if(closest_object != null){
                previous_object = current_object;
                current_object = closest_object;
            }
        }

        prev = KEY_NUM6;
        repaint();
    }
    /**
     * Key is pressed, something happens.
     * Design of this:
     * A key is pressed that toggles between scrolling the map and navigating by
     * each object in the game. This
     * KEY_NUM2 Scrolls Up
     * KEY_NUM4 Scrolls Left
     * KEY_NUM6 Scrolls Right
     * KEY_NUM8 Scrolls Down
     * 
     * KEY_NUM1 Zooms In
     * KEY_NUM7 Zooms Out
     * Key_NUM0 toggles scrolling mode on/off
     * @param keycode the code of the key pressed.
     */
    protected void keyPressed(int keycode){
        if(keycode== KEY_NUM1){
            zoomIn();
        }
        if(keycode==KEY_NUM7){
            zoomOut();
        }
        if(keycode == KEY_NUM0){
            if(scroll)
                scroll = false;
            else
                scroll = true;
            
        }
        if(scroll){
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

        }
        else{
            if(keycode==KEY_NUM6){
                selectRight();
            }
            if(keycode==KEY_NUM4){
                selectLeft();
            }
            if(keycode==KEY_NUM8){
                selectDown();
            }
            if(keycode==KEY_NUM2){
                selectUp();
            }
        }
    }
    /**
     * Creates the menu commands accessed by the soft menu buttons
     */
    private void createMenuCommands(){
        Command exitCommand = new Command("Exit", Command.EXIT,4);
        addCommand(exitCommand);
        
        Command selectCommand = new Command("Select", Command.OK,1);
        addCommand(selectCommand);
        
        Command messagesCommand = new Command("Messages", Command.OK, 2);
        addCommand(messagesCommand);
    }
}
