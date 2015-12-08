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
    public static final int VIDEO_SLOWER = 5;
    public static final int VIDEO_FASTER = 6;
    public static final int VIDEO_VOLUME_INCREASE = 7;
    public static final int VIDEO_VOLUME_DECREASE = 8;
    public static final int VIDEO_PICK_FILE = 9;
}
