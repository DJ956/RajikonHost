package handler;


public interface ICameraHandler {
    void onConnected();
    void onDisconnected();
    void onException(Throwable throwable);
    void onSendImage(byte[] imgData);
}
