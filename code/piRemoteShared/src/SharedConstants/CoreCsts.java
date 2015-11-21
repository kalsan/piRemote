package SharedConstants;

/**
 * Created by sandro on 10.11.15.
 * Contains all constants used by ServerCore and ClientCore
 */
public class CoreCsts {
    public enum ServerState{
        // Constants that indicate what application is currently running, use NONE if no application is running
        NONE,
        SERVER_DOWN, // Message put into Client MainQueue by Client Dispatcher when the server times out
        TRAFFIC_LIGHT
    }
}
