/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.thousandparsec.netlib.Client;
import org.j4me.ui.*;
import org.j4me.ui.components.*;
/**
 *
 * @author Brendan
 */
public class Downloader extends Dialog {
    private ProgressBar progressBar = new ProgressBar();
    public Downloader(){
        setTitle("Universe Download");
        setMenuText(null,"Continue");
        progressBar.setMaxValue( 12 );
	progressBar.setLabel( "1 of " + progressBar.getMaxValue() );
        ProgressBar spinner = new ProgressBar();
        
        append(progressBar);
        append(spinner);
        Label label= new Label("Downloading: Objects");
        append(label);
    }
    
    public void acceptNotify(){
        StarmapUI ui = new StarmapUI();
        ui.show();
    }
}
