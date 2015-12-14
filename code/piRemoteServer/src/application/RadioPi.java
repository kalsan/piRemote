package application;

import SharedConstants.ApplicationCsts;
import core.AbstractApplication;
import SharedConstants.ApplicationCsts.RadioPiApplicationState;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by FR4NK-W on 12.12.15.
 * Client side of RadioPi2473 application
 */
public class RadioPi extends AbstractApplication {
    Process p;
    String soundFile = "./radiopi2473/music/star_wars.wav";

    @Override
    public void onApplicationStart() {
        System.out.println("Radio Pi: Starting up.");
        changeApplicationState(RadioPiApplicationState.RADIO_INIT);
    }

    @Override
    public void onApplicationStop() {
        System.out.println("Radio Pi: Will now stop.");
    }

    @Override
    public void onApplicationStateChange(ApplicationCsts.ApplicationState newState) {
        String str;
        if(newState.equals(RadioPiApplicationState.RADIO_PLAY)) {
            String executable = "pifm";
            String frequency = "96.9"; // use 96.9 or gge
            if (soundFile!=null) {
                try {
                    System.out.println("Executing:" + "sudo ./radiopi2473/" + executable + " " + soundFile + " " + frequency);
                    p = Runtime.getRuntime().exec("sudo ./radiopi2473/" + executable + " " + soundFile + " " + frequency);
                    System.out.println("RadioPi: Broadcasting");
                } catch (IOException e) {
                    System.out.println("RadioPi: Broadcast failed");
                    e.printStackTrace();
                }
            }
            str = "Play";
        } else if(newState.equals(RadioPiApplicationState.RADIO_INIT)) {
            str = "Init";
        } else if(newState.equals(RadioPiApplicationState.RADIO_STOP)) {
            if (p!=null) {
                p.destroy();
            }
            str = "Stop";
        } else {
            throw new RuntimeException("RadioPiApplication read unknown state!" + newState.toString());
        }
        System.out.println("RadioPi: New state is: "+str);
    }

    @Override
    public void onFilePicked(File file, UUID senderUUID) {
        System.out.println("RadioPi: Picked file: "+file.getName()+". Requesting close.");
        sendString(file.getName(), senderUUID);
        closeFilePicker(senderUUID);
    }

    @Override
    public void onReceiveInt(int cst, UUID senderUUID) {
        if(cst == ApplicationCsts.RADIO_PICK_FILE){
            System.out.println("RadioPi: Initializing file pick.");
            pickFile("/home/pi/piremote/radiopi2473",senderUUID);
            return;
        }

        ApplicationCsts.ApplicationState newState;
        switch (cst){
            case ApplicationCsts.RADIO_GO_PLAY:
                newState = RadioPiApplicationState.RADIO_PLAY;
                break;
            case ApplicationCsts.RADIO_GO_INIT:
                newState = RadioPiApplicationState.RADIO_INIT;
                break;
            case ApplicationCsts.RADIO_GO_STOP:
                newState = RadioPiApplicationState.RADIO_STOP;
                break;
            default:
                return;
        }
        changeApplicationState(newState);
    }

    @Override
    public void onReceiveDouble(double d, UUID senderUUID) {

    }

    @Override
    public void onReceiveString(String str, UUID senderUUID) {
        soundFile = str;
    }
}
