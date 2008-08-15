/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.thousandparsec.netlib.Client;
import org.j4me.ui.*;

import org.j4me.ui.components.*;
import javax.microedition.lcdui.Graphics;
import java.io.IOException;
/**
 *
 * @author Brendan
 */
public class Orders extends Dialog{
    private static final String IMAGE_LOCATION = "/net/thousandparsec/netlib/Client/placeholder.png";
    private Picture picture = new Picture();
    public Orders(){
        setTitle("Orders");
        
        /*
         * PlaceHolder Image
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
    }
}
