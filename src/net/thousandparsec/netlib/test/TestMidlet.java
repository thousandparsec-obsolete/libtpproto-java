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
                //conn.addConnectionListener(new DefaultConnectionListener());
                //tc=conn;
                //SequentialConnection sconn = new SimpleSequentialConnection(conn);
                PipelinedConnection pconn = new PipelinedConnection(conn);
                new FrameReceiver(pconn).sendAndReceive();
                
                
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
                        //tc.sendFrame(getObj);
                        System.out.println("GetObj Reached in main");
		}
        catch(Exception e){
            e.printStackTrace();
        }*/
    }
    class FrameReceiver extends TP03Visitor {
        PipelinedConnection conn = null;
        public FrameReceiver(PipelinedConnection c){
            conn = c;
        }
        public void sendAndReceive() throws IOException, TPException{
            conn.getConnection().receiveAllFramesAsync(this);
            try{
                SequentialConnection sq = conn.createPipeline();
                //conn.receiveAllFramesAsync(this);
                
                System.out.println("All frames received");
                              
                System.out.println("----Boards User Can See-------");
                GetBoardIDs gbid = new GetBoardIDs();
                gbid.setAmount(-1);
                Vector v = ((IDSequence) sq.sendFrame(gbid, IDSequence.class)).getModtimes();

                GetBoards gb = new GetBoards();
                //Gets the board
                System.out.println("SETTING UP GET BOARD");
                //gb.getIds().addElement(new IdsType(((IDSequence.ModtimesType)v.elementAt(0)).getId()));
                
                gb.getIds().addElement(new IdsType(0));
                //gb.getIds().addElement(new IdsType(-1));
                System.out.println("GB SETUP: " + gb.toString());
                System.out.println("SENDING GET BOARD");
                Board b = (Board) sq.sendFrame(gb, Board.class);
                System.out.println("Board Successfully received - printing the contents");
                System.out.println(b.toString());
                System.out.println("---Now for the Message---");
                GetMessage gm = new GetMessage();
                gm.getSlots().addElement(new GetWithIDSlot.SlotsType(0));
                
                
                gm.getSlots().addElement(new GetWithIDSlot.SlotsType(1));
                gm.getSlots().addElement(new GetWithIDSlot.SlotsType(2));
                //gm.getSlots().addElement(new GetWithIDSlot.SlotsType(-1));
                
                Message m = (Message )sq.sendFrame(gm, Message.class);
                System.out.println("Message m is: " + m.getSubject());

            }
            /*catch(EOFException eof){
                eof.printStackTrace();
            }
            catch(IOException ioe){
                ioe.printStackTrace();
            }*/

            catch(Exception e){
                System.out.println("Generic Exception catch - message is: " + e.getMessage()); 
                e.printStackTrace();
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
