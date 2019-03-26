package channel;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamException;
import com.github.sarxos.webcam.WebcamUtils;
import encoder.Encoder;
import handler.ICameraHandler;

import java.awt.*;
import java.io.IOException;
import java.net.*;

public class CameraChannel implements Channel {
    private static final String FORMAT = "JPG";

    private InetSocketAddress remote;

    private Webcam webcam;
    private DatagramSocket datagramSocket;
    private ICameraHandler handler;

    public CameraChannel(Dimension dimension, ICameraHandler handler, int port) throws WebcamException {
        this.handler = handler;

        remote = new InetSocketAddress("192.168.0.255", port);
        System.out.println("Camera server setup:" + remote.toString());

        webcam = Webcam.getDefault();
        if (webcam == null) {
            throw new WebcamException("Webcam not found");
        } else {
            webcam.setViewSize(dimension);
        }
    }

    @Override
    public void start() {
        webcam.open();
        try {
            datagramSocket = new DatagramSocket();
            while (webcam.isOpen()) {
                byte[] imgData = Encoder.encode(webcam.getImage());

                if(imgData == null){
                    imgData = WebcamUtils.getImageBytes(webcam, FORMAT);
                }

                if(imgData != null) {
                    DatagramPacket packet = new DatagramPacket(imgData, 0, imgData.length, remote);
                    datagramSocket.send(packet);
                    handler.onSendImage(imgData);
                }
            }
        }catch (IOException e){
            handler.onException(e);
        }
    }

    @Override
    public void stop() {
        webcam.close();
        if(datagramSocket != null){
            datagramSocket.disconnect();
            datagramSocket.close();
            handler.onDisconnected();
        }
    }
}
