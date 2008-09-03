/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.thousandparsec.netlib.Client;
import javax.microedition.lcdui.*;
import java.io.IOException;

/**
 *
 * @author Brendan
 */
public class LoginView extends Form implements CommandListener{
    /*
     * Thousand parsec logo string location
     */
    private final String IMAGE_LOCATION = "/net/thousandparsec/netlib/Client/logo_t.png";
    /*
     * The image object that the logo will be created upon
     */
    Image logo;
    /*
     * Display Object passed from the midlet
     */
    Display display;
    /*
     * The username field
     */
    TextField loginField;
    /*
     * The password field
     */
    TextField passwordField;
    /*
     * The server url field
     */
    TextField urlField;
    /**
     * Constructs a new login interface
     * @param title the title of the interface
     * @param d the midlet's display object
     */
    public LoginView(String title, Display d){
        super(title);
        display = d;
        //Create the image
        try{
            logo = logo.createImage(IMAGE_LOCATION);
            
            append(logo);
        }
        catch(IOException e){
            append("Cannot find Image at '" + IMAGE_LOCATION + "'");
        }
        
        loginField = new TextField("Username","w:"+getWidth()+" h"+getHeight(), 20, TextField.ANY);
        append(loginField);
        
        passwordField = new TextField("Password",null,20,TextField.PASSWORD);
        append(passwordField);
        
        urlField = new TextField("Server URL", null, 50, TextField.ANY);
        append(urlField);
        Command exitCommand = new Command("EXIT", Command.EXIT, 1);
        Command loginCommand = new Command("LOGIN", Command.OK, 1);
        addCommand(loginCommand);
        addCommand(exitCommand);
        setCommandListener(this);
    }
    /**
     * Controls what each command actually does
     */
    public void commandAction(Command command, Displayable displayable) {
        String label = command.getLabel();
        if(label.equals("LOGIN")){
            display.setCurrent(new DownloadView("Downloading Data...", display));
        }
        if(label.equals("EXIT")){
            //do later
        }
    }
}