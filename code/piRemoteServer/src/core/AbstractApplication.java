package Core;

import MessageObject.Message;
import MessageObject.PayloadObject.ApplicationStateChange;
import MessageObject.PayloadObject.DoubleMessage;
import MessageObject.PayloadObject.IntMessage;
import MessageObject.PayloadObject.StringMessage;
import SharedConstants.ApplicationCsts;

import java.io.File;

/**
 * Created by sandro on 11.11.15.
 * Parent class for all applications' server part
 */
public abstract class AbstractApplication {

    protected static ApplicationCsts.ApplicationState applicationState;

    public ApplicationCsts.ApplicationState getApplicationState(){
        return applicationState;
    }

    public void processMessage(Message msg){
        if(!checkApplicationState(msg)){
            // Application State mismatch! Send ss to whoever sent this to us
            ServerCore.sendMessage(ServerCore.makeMessage(msg.getUuid()));
            return;
        }

        // Application State is consistent. Look at the payload
        if(msg.hasPayload()){
            if(msg.getPayload() instanceof ApplicationStateChange){
                ApplicationCsts.ApplicationState newApplicationState = ((ApplicationStateChange) msg.getPayload()).newApplicationState;
                if(!newApplicationState.equals(applicationState)){
                    onApplicationStateChange(newApplicationState);
                    applicationState = newApplicationState;
                }// Otherwise we are already in the right application state, do nothing
            }else if(msg.getPayload() instanceof IntMessage){
                onReceiveInt(((IntMessage) msg.getPayload()).i);
            }else if(msg.getPayload() instanceof DoubleMessage){
                onReceiveDouble(((DoubleMessage) msg.getPayload()).d);
            }else if(msg.getPayload() instanceof StringMessage){
                onReceiveString(((StringMessage) msg.getPayload()).str);
            }else{
                System.out.println("WARNING: Unknown Payload type: "+msg.getPayload().getClass().toString());
            }
        }// otherwise it was just an ss.
    }

    protected boolean checkApplicationState(Message msg) {
        return msg.getApplicationState().equals(applicationState);
    }

    public abstract void onApplicationStart(ApplicationCsts.ApplicationState initialState);   // Called right after creation of the application
    public abstract void onApplicationStop();                                                 // Called right before the destruction of the application
    public abstract void onApplicationStateChange(ApplicationCsts.ApplicationState newState); // Called right BEFORE application switches to another state
    public abstract void onFilePicked(File file);                                             // Called when the FilePicker on the client sent a file pick message
    public abstract void onReceiveInt(int i);                                                 // Called when an int    message arrived
    public abstract void onReceiveDouble(double d);                                           // Called when a  double message arrived
    public abstract void onReceiveString(String str);                                         // Called when a  string message arrived
}