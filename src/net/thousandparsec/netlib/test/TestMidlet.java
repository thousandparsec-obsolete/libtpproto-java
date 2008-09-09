/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.thousandparsec.netlib.test;

import java.util.Vector;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

import java.io.IOException;
import net.thousandparsec.util.URI;
import net.thousandparsec.netlib.Connection;
import net.thousandparsec.netlib.DefaultConnectionListener;
import net.thousandparsec.netlib.TPException;
import net.thousandparsec.netlib.tp03.GetObjectsByID;
import net.thousandparsec.netlib.tp03.Ping;
import net.thousandparsec.netlib.tp03.TP03Decoder;
import net.thousandparsec.netlib.tp03.TP03Visitor;
import net.thousandparsec.netlib.tp03.GetWithID.IdsType;
import net.thousandparsec.netlib.tp03.ObjectIDs;
import net.thousandparsec.netlib.tp03.*;
import net.thousandparsec.netlib.*;


/**
 * @author Brendan Roberts
 * Based upon Krzysztof Sobolewski's TestConnect client.
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
                //tc=conn;
                SequentialConnection sconn = new SimpleSequentialConnection(conn);
                new FrameReceiver(sconn).sendAndReceive();
                
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
        SequentialConnection conn = null;
        public FrameReceiver(SequentialConnection c){
            conn = c;
        }
        public void sendAndReceive() throws IOException, TPException{
            conn.getConnection().receiveAllFramesAsync(this);
            try{
                //asd
                //conn.receiveAllFramesAsync(this);
                System.out.println("All frames received");
                System.out.println("Sending ping");
                //conn.sendFrame(new Ping());
                //System.out.println("Getting Objects by id");
                //GetObjectsByID getObj = new GetObjectsByID();
                GetObjectIDs getObj = new GetObjectIDs();
                //start of frame from tp03 protocol
                getObj.setKey(-1);
                getObj.setStart(0);
                //-1 for all
                getObj.setAmount(-1);
                //getObj.getIds().addElement(new IdsType(0));
                //getObj.getIds().addElement(new IdsType(1));
                System.out.println("---Start of Object IDs---");
                //Vector v = conn.s
                //Frame f = conn.sendFrame(getObj, IDSequence.class);
                Vector t = ((IDSequence) conn.sendFrame(getObj, IDSequence.class)).getModtimes();
                System.out.println("SIZE OF VECTOR:" + t.size());
                GetObjectsByID gobjid = new GetObjectsByID();
                for(int i = 0; i < t.size(); i++){
                    gobjid.getIds().addElement(new IdsType(((IDSequence.ModtimesType)t.elementAt(i)).getId()));
                }
                System.out.println("GOBJID SIZE IS: " + gobjid.getIds().size());
                conn.getConnection().sendFrame(gobjid);
                
                
                
                System.out.println("Sending singular object at id 0: " );
                GetObjectsByID goobj = new GetObjectsByID();
                goobj.getIds().addElement(new IdsType(0));
                conn.getConnection().sendFrame(goobj);
                
                
                
                
                
                
                
                
                
                
                
                //System.out.println("Object at 2");
                //System.out.println(getObj.getIds().elementAt(2).toString());
                //GetObjectIDs
                ////System.out.println("object ids: sending frame");
                //ObjectIDs gobjid = new ObjectIDs();
                //conn.sendFrame(gobjid);
                
                //conn.receiveAllFrames(this);
                //System.out.println("objectids passed");
                //System.out.println("---ObjectIDs.tostring()---\n" + gobjid.toString());
                //System.out.println("getObj made; sending getObj Frame");
                //conn.sendFrame(getObj);
                //conn.receiveAllFramesAsync(this);
                //System.out.println("FrameReceiver: getObj passed");
                //System.out.println("---GetObject.toString()---\n" + getObj.toString());
            }
            /*catch(EOFException eof){
                eof.printStackTrace();
            }
            catch(IOException ioe){
                ioe.printStackTrace();
            }*/

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
