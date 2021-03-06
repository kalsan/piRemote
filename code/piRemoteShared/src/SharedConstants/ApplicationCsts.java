package SharedConstants;

import java.io.Serializable;

/**
 * Created by sandro on 10.11.15.
 * Contains all constants used by applications, grouped by application
 * Must provide at least XApplicationState where X is the name of the application
 */
public class ApplicationCsts implements Serializable {

    public interface ApplicationState{}

    // TrafficLightApplication
    public enum TrafficLightApplicationState implements ApplicationState{
        TRAFFIC_RED("Red"),
        TRAFFIC_ORANGE("Orange"),
        TRAFFIC_GREEN("Green");

        private final String stateRepresentation;
        TrafficLightApplicationState(String s) {
            this.stateRepresentation = s;
        }

        @Override
        public String toString() {
            return stateRepresentation;
        }
    }
    public static final int TRAFFIC_GO_GREEN = 0;
    public static final int TRAFFIC_GO_ORANGE = 1;
    public static final int TRAFFIC_GO_RED = 2;
    public static final int TRAFFIC_PICK_FILE = 3;


    // VideoApplication
    public enum VideoApplicationState implements ApplicationState{
        VIDEO_STOPPED,
        VIDEO_PLAYING,
        VIDEO_PAUSED
    }
    public static final int VIDEO_PLAY = 0;
    public static final int VIDEO_PAUSE = 1;
    public static final int VIDEO_STOP = 2;
    public static final int VIDEO_JUMP_BACK = 3;
    public static final int VIDEO_JUMP_FORWARD = 4;
    public static final int VIDEO_SPEED_SLOWER = 5;
    public static final int VIDEO_SPEED_FASTER = 6;
    public static final int VIDEO_VOLUME_INCREASE = 7;
    public static final int VIDEO_VOLUME_DECREASE = 8;
    public static final int VIDEO_PICK_FILE = 9;
    public static final int VIDEO_LEAP_BACK = 10;
    public static final int VIDEO_LEAP_FORWARD = 11;
    public static final int VIDEO_TOGGLE_SUBTITLES = 12;


    // MusicPlayerApplication
    public enum MusicApplicationState implements ApplicationState {
        MUSIC_STOPPED,
        MUSIC_PLAYING,
        MUSIC_PAUSED,
        MUSIC_INVALID
    }
    public static final int MUSIC_PLAY = 0;
    public static final int MUSIC_PAUSE = 1;
    public static final int MUSIC_STOP = 2;
    public static final int MUSIC_STATUS = 3;
    public static final int MUSIC_GET_CURRENT = 4;
    public static final int MUSIC_NEXT = 10;
    public static final int MUSIC_PREV = 11;
    public static final int MUSIC_VOLUME_UP = 20;
    public static final int MUSIC_VOLUME_DOWN = 21;
    public static final int MUSIC_LOOP = 30;
    public static final int MUSIC_SINGLE = 31;
    public static final int MUSIC_NOLOOPING = 32;
    public static final int MUSIC_SHUFFLE_ON = 33;
    public static final int MUSIC_SHUFFLE_OFF = 34;
    public static final int MUSIC_PICK_FILE = 100;
    public static final int MUSIC_GET_PLAYLIST = 101;

    public static final String MUSIC_PREFIX_SONG = "SONG: ";
    public static final String MUSIC_PREFIX_EXTRA = "XTRA: ";
    public static final String MUSIC_PREFIX_FILESELECTION = "FILE: ";
    public static final String MUSIC_PREFIX_PLAYLIST = "LIST: ";
    public static final String MUSIC_PREFIX_STATUS = "STATUS: ";
    public static final String MUSIC_PREFIX_VOLUME = "VABS: ";


    // RadioPiApplication
    public enum RadioPiApplicationState implements ApplicationState{
        RADIO_STOP("Stop"),
        RADIO_INIT("Initialize"),
        RADIO_PLAY("Play");

        private final String stateRepresentation;
        RadioPiApplicationState(String s) {
            this.stateRepresentation = s;
        }

        @Override
        public String toString() {
            return stateRepresentation;
        }
    }
    public static final int RADIO_GO_PLAY = 0;
    public static final int RADIO_GO_INIT = 1;
    public static final int RADIO_GO_STOP = 2;
    public static final int RADIO_PICK_FILE = 3;


    // ImageApplication
    public enum ImageApplicationState implements ApplicationState{
        IMAGE_DISPLAYED,
        IMAGE_NOT_DISPLAYED
    }
    public static final int IMAGE_PICK_FILE = 0;
    public static final int IMAGE_SHOW = 1;
    public static final int IMAGE_HIDE = 2;
    public static final int IMAGE_PREV = 3;
    public static final int IMAGE_NEXT = 4;

    // ShutdownApplication
    public enum ShutdownApplicationState implements ApplicationState{
        SHUTDOWN_APPLICATION_STATE;
    }
    public static final int SHUTODNW_BUTTON_PRESSED = 0;
}
