package handler;

import java.net.SocketAddress;

public interface IControlHandler {
    void onConnected(SocketAddress remoteSocketAddress);
    void onDisconnected();
    void onException(Throwable throwable);
    void onReceiveMessage(String message);
}
