/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.thousandparsec.netlib.test;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

import java.io.IOException;
import net.thousandparsec.util.URI;
import net.thousandparsec.util.URISyntaxException;
import net.thousandparsec.netlib.Connection;
import net.thousandparsec.netlib.DefaultConnectionListener;
import net.thousandparsec.netlib.Frame;
import net.thousandparsec.netlib.TPException;
import net.thousandparsec.netlib.tp03.Fail;
import net.thousandparsec.netlib.tp03.GetObjectsByID;
import net.thousandparsec.netlib.tp03.Object;
import net.thousandparsec.netlib.tp03.ObjectParams;
import net.thousandparsec.netlib.tp03.Okay;
import net.thousandparsec.netlib.tp03.Ping;
import net.thousandparsec.netlib.tp03.TP03Decoder;
import net.thousandparsec.netlib.tp03.TP03Visitor;
import net.thousandparsec.netlib.tp03.GetWithID.IdsType;
import net.thousandparsec.netlib.tp03.ObjectParams.Universe;
import net.thousandparsec.netlib.Visitor;
import java.io.EOFException;

/**
 * @author Brendan
 */
public class TestMidlet extends MIDlet {
    //Connection conn;
    public void startApp() {

    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }
    
    /*public TestMidlet(Connection conn){
       this.conn=conn;
       
    }*/
    public TestMidlet(){
        Connection tc=null;
        try{
            	TP03Decoder decoder=new TP03Decoder();
                System.out.println("TP03Decoder instantiated");
                System.out.println("decoder.makeConnection");
		Connection conn=decoder.makeConnection(
                      new URI("tp://brendan:a@192.168.0.180"), true, new TP03Visitor(false));
                      //new URI("tp://guest:guest@demo1.thousandparsec.net"),true, new TP03Visitor(false));
                      //new URI("tp://guest:guest@llnz.dyndns.org:6924/llnz-dev2"),true, new TP03Visitor(false));
                System.out.println("Connection object made");
                conn.addConnectionListener(new DefaultConnectionListener());
                new FrameReceiver(conn).sendAndReceive();
                tc=conn;
        }
        catch(Exception e){
            e.printStackTrace();
        }
       /* try
		{
			tc.sendFrame(new Ping());

			GetObjectsByID getObj=new GetObjectsByID();
			//zero for top-level object is a typical magic number
			//getObj.getIds().add(new IdsType(0));
                        tc.sendFrame(getObj);
                        System.out.println("GetObj Reached in main");
		}
        catch(Exception e){
            e.printStackTrace();
        }*/
    }
    class FrameReceiver extends TP03Visitor {
        Connection conn = null;
        public FrameReceiver(Connection c){
            conn = c;
        }
        public void sendAndReceive() throws IOException, TPException{
            conn.receiveAllFramesAsync(this);
            try{
                System.out.println("Now in sendAndReceive");
                //conn.receiveAllFrames(this);
                System.out.println("All frames received");
                System.out.println("Sending ping");
                conn.sendFrame(new Ping());
                System.out.println("Getting Objects by id");
                GetObjectsByID getObj = new GetObjectsByID();
                getObj.getIds().addElement(new IdsType(0));
                getObj.getIds().addElement(new IdsType(1));
                System.out.println("getObj made; sending getObj Frame");
                conn.sendFrame(getObj);
                //conn.receiveAllFramesAsync(this);
                System.out.println("FrameReceiver: getObj passed");
                System.out.println("---GetObject.toString()---\n" + getObj.toString());
            }
            catch(EOFException eof){
                eof.printStackTrace();
            }
            catch(IOException ioe){
                ioe.printStackTrace();
            }

            catch(Exception e){
                System.out.println("Generic Exception catch - message is: " + e.getMessage());                
            }
            finally{
                try{
                    conn.close();
                }
                catch(IOException ioe){
                    System.out.println("IOException in connection.close - reads: " + ioe.getMessage());
                }
            }
            
        }
    }

}
