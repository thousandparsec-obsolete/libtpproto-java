/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.thousandparsec.netlib.Client;
import org.j4me.ui.*;
import org.j4me.ui.components.*;
import java.io.*;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author Brendan
 */
public class Login extends Dialog {
    /**
     * The location of the image
     * most likely to break - it needs to be set to the exact position inside the jar
     */
    private static final String IMAGE_LOCATION = "/net/thousandparsec/netlib/Client/logo.png";
    /**
     * A Username Field
     */
    private TextBox username;
    /**
     * Password field
     */
    private TextBox password;
    /**
     * Server URL Field
     */
    private TextBox serverLoc;
    /**
     * Thousand Parsec Logo
     */
    private Picture picture = new Picture();
    public Login(){
        setTitle("Login to Thousand Parsec");
        setMenuText("Exit","Login");
        /**
         * Insert the Picture
         */
        try
	{
            // Center the picture.
            picture.setHorizontalAlignment( Graphics.HCENTER );
			
            // The image location within the Jar.
            picture.setImage( IMAGE_LOCATION );
	
            // Add the picture to this screen.
            append( picture );
	}
	catch (IOException e)
	{
            // Show an error message instead of the picture.
            Label error = new Label( "Error:  Could not find image " + IMAGE_LOCATION );
            append( error );
            //e.printStackTrace();
	}
        
        /**
         * Insert Username/PW and server URL boxes
         */
        username = new TextBox();
        username.setLabel("Username");
        append(username);
        
        password = new TextBox();
        password.setLabel("Password");
        password.setPassword(true);
        append(password);
        
        serverLoc = new TextBox();
        serverLoc.setLabel("Server URL");
        append(serverLoc);
    }
    /**
     * The Back Button
     *
     */
    public void declineNotify(){
        System.out.println("Decline Pressed");
        TPClientMIDP.exit();
        super.declineNotify();
    }
    public void acceptNotify(){
        //do some connect stuff then move on
        Downloader dl = new Downloader();
        dl.show();
    }
}
