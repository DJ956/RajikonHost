package handler.command.control;

import java.io.IOException;

public class StopCommand implements ControlCommand {

    public static final String STOP = "Stop";
    public static final int LEFT_STOP = 0;
    public static final int RIGHT_STOP = 1;
    public static final int BOTH_STOP = 2;

    @Override
    public String getCommand() {
        return STOP;
    }

    @Override
    public void execute(int stopMode) {
        if(stopMode < 0 || stopMode > 2){
            stopMode = 2;
        }
        try{
            Runtime runtime = Runtime.getRuntime();
            String command = String.format("python ./commands/stop.py %d",
                    stopMode);
            runtime.exec(command);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
