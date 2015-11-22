package core.network;

import ConnectionManagement.Connection;
import MessageObject.Message;
import MessageObject.PayloadObject.Payload;
import SharedConstants.ApplicationCsts;
import SharedConstants.CoreCsts;
import com.sun.corba.se.spi.activation.Server;
import core.AbstractApplication;
import core.ServerCore;
import sun.nio.ch.Net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.BlockingQueue;

/**
 * created by fabian on 13.11.15
 */

public class ServerDispatcherThread implements Runnable {

    // private ServerKeepAliveThread keepAliveThread;
    private static List<Session> morgueQueue = new ArrayList<>();
    private Thread serverDispatcher;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private HashMap<UUID, NetworkInfo> sessionTable;
    private BlockingQueue<Message> sendingQueue;

    public ServerDispatcherThread(ServerSocket socket, HashMap sTable, ServerSenderThread senderThread) {

        serverSocket = socket;
        sessionTable = sTable;
        sendingQueue = senderThread.getSendingQueue();

        serverDispatcher = new Thread(this);
        serverDispatcher.start();
    }

    @Override
    public void run() {

        while (ServerNetwork.isRunning()) {

            try {
                clientSocket = serverSocket.accept();

                InetAddress ip = clientSocket.getInetAddress();
                int port = clientSocket.getPort();

                ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());

                while (!morgueQueue.isEmpty()) {
                    sessionTable.remove(morgueQueue.remove(0).getUUID());
                }

                if (input.readObject() instanceof Message) {
                    Message receivedMessage = (Message) input.readObject();
                    UUID uuid = receivedMessage.getUuid();

                    // check the sessionTable
                    if (!sessionTable.containsKey(uuid)) {
                        NetworkInfo clientInfo = new NetworkInfo(ip, port, System.currentTimeMillis());
                        sessionTable.put(uuid, clientInfo);
                    } else {
                        // update lastSeen
                        sessionTable.get(uuid).lastSeen = System.currentTimeMillis();
                    }

                    // (TODO: first check if it is a FilePickerRequest) is handled by ServerCore

                    // put message on mainQueue from Core
                    ServerCore.mainQueue.put(receivedMessage);

                } else if (input.readObject() instanceof Connection) {
                    Connection connection = (Connection) input.readObject();

                    if (connection.getConnection() == Connection.Connect.CONNECT) {
                        // I would only do that if it is a connectRequest
                        UUID uuid = new UUID(1, 1);
                        uuid = uuid.randomUUID();

                        NetworkInfo clientInfo = new NetworkInfo(ip, port, System.currentTimeMillis());
                        sessionTable.put(uuid, clientInfo);

                        sendingQueue.add(new Message(uuid, ServerCore.getState().getServerState(), ServerCore.getState().getApplicationState()));
                    } else {
                        // TODO: remove from sessionTable -> include UUID in diconnection?
                    }

                }

            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Session> getmorgueQueue() {
        return morgueQueue;
    }
}