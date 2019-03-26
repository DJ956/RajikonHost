package controller;

import handler.IControlHandler;
import java.net.SocketAddress;

public class MotorControlManager implements IControlHandler {

    private static final String FORWARD = "Forward";
    private static final String BACK = "Back";
    private static final String STOP = "Stop";
    private static final String LEFT = "Left";
    private static final String RIGHT = "Right";

    private final Motor leftMotor;
    private final Motor rightMotor;

    public MotorControlManager(){
        leftMotor = new LeftMotor();
        rightMotor = new RightMotor();
    }

    @Override
    public void onConnected(SocketAddress remoteSocketAddress) {
        System.out.println("Control Channel Connected:" + remoteSocketAddress);
    }

    @Override
    public void onDisconnected() {
        System.out.println("Control Channel Disconnected");
    }

    @Override
    public void onException(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onReceiveMessage(String message) {
        System.out.println("Received message:" + message);
        if(message != null && message.contains(":")){
            String[] args = message.split(":");
            if(args.length > 0){
                String command = args[0];
                int percentage = Integer.parseInt(args[1]);
                driveModeChange(command, percentage);
            }
        }
    }

    private void driveModeChange(String commandName, int percentage) {
        switch (commandName) {
            case FORWARD: {
                leftMotor.forward(percentage);
                rightMotor.forward(percentage);
                break;
            }
            case BACK: {
                leftMotor.back(percentage);
                rightMotor.back(percentage);
                break;
            }
            case STOP: {
                leftMotor.stop();
                rightMotor.stop();
                break;
            }
            case LEFT: {
                leftMotor.forward(percentage);
                rightMotor.stop();
                break;
            }
            case RIGHT: {
                leftMotor.stop();
                rightMotor.forward(percentage);
                break;
            }
            default: {
                break;
            }
        }
    }
}
