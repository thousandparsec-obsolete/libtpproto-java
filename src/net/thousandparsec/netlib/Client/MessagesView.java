package net.thousandparsec.netlib.Client;
import javax.microedition.lcdui.*;
import java.util.Vector;
/**
 * Message Interface for TPClient-midp, this version is an implementation
 * that does not make use of the j4me library.
 * @author Brendan 
 */
public class MessagesView extends Form implements CommandListener{
    /*
     * The display
     */
    Display display;
    /*
     * Soft Button Commands
     */
    Command backCommand;
    Command nextCommand;
    Command previousCommand;
    Command deleteCommand;
    /*
     * The previous device screen
     */
    StarMapView previousScreen;
    /*
     * A list of messages from the system
     */
    Vector messageList;
    /*
     * The current displayed message
     */
    String currentMessage;
    /*
     * An index value of this current message
     */
    int currentMessageIndex;
    /*
     * An immutable default message
     */
    final String default_message ="There are no messages to display.";
    /**
     * Creates a messages user interface for tpclient-midp
     * @param title the title of the interface
     * @param d The display object that changes are displayed on
     * @param prev the previous devicescreen
     */
    public MessagesView(String title, Display d, StarMapView prev){
        super(title);
        display = d;
        setCommandListener(this);
        previousScreen = prev;
        
        backCommand = new Command("Back", Command.BACK, 1);
        addCommand(backCommand);
        nextCommand = new Command("Next", Command.OK, 1);
        addCommand(nextCommand);
        previousCommand = new Command("Previous", Command.OK,2);
        addCommand(previousCommand);
        deleteCommand = new Command("Delete", Command.OK, 3);
        addCommand(deleteCommand);
        
        
        
        messageList = DownloadView.getInstance().getMessages();
        messageList.addElement("Message1");
        messageList.addElement("Message2");
        messageList.addElement("Message3");
        messageList.addElement("Message4");
        messageList.addElement("Message5");
        messageList.addElement("Message6");
        if(messageList.size() > 0){
            currentMessage=(String) messageList.elementAt(0);
        }
        else{
            currentMessage=default_message;
        }
        append(currentMessage);
        /*
         * Store the message index so that it is easy to find later
         * as all new messages will replace this current message
         */
        for(int i = 0; i < size(); i++){
            if(((StringItem)get(i)).getText().equals(currentMessage)){
                currentMessageIndex = i;
                break;
            }
        }
        
    }
    /**
     * Controls what each command actually does
     */
    public void commandAction(Command command, Displayable displayable) {
        String label = command.getLabel();
        if(label.equals("Back")){
            display.setCurrent(previousScreen);
        }
        if(label.equals("Next")){
            nextMessage();
        }
        if(label.equals("Previous")){
            previousMessage();
        }
        if(label.equals("Delete")){
            deleteCurrentMessage();
        }        
    }
    /**
     * Add messages to the list of messages
     * @param message the message to be added
     */
    public void addMessage(String message){
        messageList.addElement(message);
    }
    /**
     * displays the next message
     */
    public void nextMessage(){
        //not the last message in the vector?
        if(messageList.indexOf(currentMessage)< messageList.size()-1){
            String newMessage = (String) messageList.elementAt(messageList.indexOf(currentMessage)+1);
            set(currentMessageIndex, new StringItem(null, newMessage));
            currentMessage = newMessage;
        }
    }
    /**
     * displays the previous message.
     */
    public void previousMessage(){
        if(messageList.indexOf(currentMessage)>0){
            String newMessage = (String) messageList.elementAt(messageList.indexOf(currentMessage)-1);
            set(currentMessageIndex, new StringItem(null, newMessage));
            currentMessage = newMessage;
        }
    }
    /**
     * Deletes the current displayed message.
     */
    public void deleteCurrentMessage(){
        int index = messageList.indexOf(currentMessage);
        if(index ==-1){
         return;
        }
        messageList.removeElementAt(index);
        if(messageList.size()==0){
            set(currentMessageIndex, new StringItem(null, default_message));
            currentMessage=default_message;
            
        }
        else{
            String newMessage = (String) messageList.elementAt(index);
            set(currentMessageIndex, new StringItem(null, newMessage));
            currentMessage=newMessage;

        }
        
         
    }

    
}
