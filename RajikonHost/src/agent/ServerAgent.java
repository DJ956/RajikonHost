package agent;

import channel.CameraChannel;
import channel.ControlChannel;
import com.github.sarxos.webcam.WebcamException;
import controller.MotorControlManager;
import handler.CameraHandler;

import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerAgent implements IServerAgent{
    private static final Dimension DIMENSION = new Dimension(320, 240);

    private ExecutorService worker = Executors.newFixedThreadPool(2);

    private ControlChannel controlChannel;
    private CameraChannel cameraChannel;

    @Override
    public void start(int controlPort, int cameraPort) {
        controlChannel = new ControlChannel(controlPort, new MotorControlManager());

        worker.execute(new Runnable() {
            @Override
            public void run() {
                controlChannel.start();
            }
        });

        try {
            cameraChannel = new CameraChannel(DIMENSION, new CameraHandler(), cameraPort);
            worker.execute(new Runnable() {
                @Override
                public void run() {
                    cameraChannel.start();
                }
            });
        }catch (WebcamException w){
            System.out.println(w.getMessage());
        }
    }

    @Override
    public void stop() {
        controlChannel.stop();
        if(cameraChannel != null) {
            cameraChannel.stop();
        }
        worker.shutdown();
    }
}
