package handler;

import handler.command.control.*;

import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

public class ControlHandler implements IControlHandler {

    private static final String FORWARD = "Forward";
    private static final String BACK = "Back";
    private static final String STOP = "Stop";
    private static final String LEFT = "Left";
    private static final String RIGHT = "Right";

    private boolean isForward = false;

    private Map<String, ControlCommand> commandMap;

    public ControlHandler(){
        commandMap = new HashMap<>();
        commandMap.put(BACK, new BackCommand());
        commandMap.put(FORWARD, new ForwardCommand());
        commandMap.put(STOP, new StopCommand());
        commandMap.put(LEFT, new LeftForwardCommand());
        commandMap.put(RIGHT, new RightForwardCommand());
    }

    @Override
    public void onConnected(SocketAddress remoteSocketAddress) {
        System.out.println("Connected:" + remoteSocketAddress);
    }

    @Override
    public void onDisconnected() {
        System.out.println("Disconnected");
    }

    @Override
    public void onException(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onReceiveMessage(String message) {
        if(message != null && message.contains(":")){
            String[] args = message.split(":");
            if(args.length > 0){
                String command = args[0];
                int percentage = Integer.parseInt(args[1]);
                driveModeChange(command, percentage);
            }
        }
    }

    private void driveModeChange(String commandName, int percentage){
        switch (commandName)
        {
            case FORWARD:
            {
                if(!isForward){
                    ControlCommand stopCommand = commandMap.get(STOP);
                    stopCommand.execute(0);
                }
                ControlCommand forwardCommand = commandMap.get(FORWARD);
                forwardCommand.execute(percentage);
                break;
            }
            case BACK:
            {
                if(isForward){
                    ControlCommand stopCommand = commandMap.get(STOP);
                    stopCommand.execute(StopCommand.BOTH_STOP);
                }
                ControlCommand backCommand = commandMap.get(BACK);
                backCommand.execute(percentage);
                break;
            }
            case STOP:
            {
                ControlCommand stopCommand = commandMap.get(STOP);
                stopCommand.execute(StopCommand.BOTH_STOP);
                break;
            }
            case LEFT:
            {
                ControlCommand stopCommand = commandMap.get(STOP);
                stopCommand.execute(StopCommand.RIGHT_STOP);

                ControlCommand leftCommand = commandMap.get(LEFT);
                leftCommand.execute(percentage);
                break;
            }
            case RIGHT:
            {
                ControlCommand stopCommand = commandMap.get(STOP);
                stopCommand.execute(StopCommand.LEFT_STOP);

                ControlCommand rightCommand = commandMap.get(RIGHT);
                rightCommand.execute(percentage);
                break;
            }
            default:
            {
                break;
            }
        }
    }
}
