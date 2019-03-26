package handler;

public class CameraHandler implements ICameraHandler {
    @Override
    public void onConnected() {
        System.out.println("Connected camera channel");
    }

    @Override
    public void onDisconnected() {
        System.out.println("Disconnected camera channel");
    }

    @Override
    public void onException(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onSendImage(byte[] imgData) {
        //System.out.println("Send image data:" + imgData.length);
        imgData = null;
    }
}
