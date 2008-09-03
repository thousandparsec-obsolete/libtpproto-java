/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.thousandparsec.netlib.Client;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
/**
 * The TPClient-MIDP midlet, this is the entry point of the mobile application.
 * @author Brendan
 */
public class TPClientMIDP extends MIDlet{
        /**
         * The one and only instance of this class.
         */
        private static TPClientMIDP instance;

        private Display display;        
        /**
         * Constructs the midlet.  This is called before <code>startApp</code>.
         */
        public TPClientMIDP ()
        {
                instance = this;
        }

        /**
         * Called when the application is minimized.  For example when their
         * is an incoming call that is accepted or when the phone's hangup key
         * is pressed.
         * 
         * @see javax.microedition.midlet.MIDlet#pauseApp()
         */
        protected void pauseApp ()
        {
        }

        /**
         * Gets the display object from the midlet to allow ease-of-navigation
         * through TPClient-midp.
         * @return
         */
        public Display getDisplay()
        {
            return Display.getDisplay(this); 
        }
        /**
         * Called when the application starts.  Shows the first screen.
         * 
         * @see javax.microedition.midlet.MIDlet#startApp()
         */


        protected void startApp () throws MIDletStateChangeException
        {
                LoginView lv = new LoginView("Login",getDisplay());
                getDisplay().setCurrent(lv);
        }

        /**
         * Called when the application is exiting.
         * 
         * @see javax.microedition.midlet.MIDlet#destroyApp(boolean)
         */
        protected void destroyApp (boolean arg0) throws MIDletStateChangeException
        {
                // Add cleanup code here.
                
                // Exit the application.
                notifyDestroyed();
        }
        
        /**
         * Programmatically exits the application.
         */
        public static void exit ()
        {
                try
                {
                        instance.destroyApp( true );
                }
                catch (MIDletStateChangeException e)
                {
                        // Ignore.
                }
        }
}
