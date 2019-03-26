package handler.command.control;

import java.io.IOException;

public class RightForwardCommand implements ControlCommand {

    private static final String RIGHT = "Right";

    @Override
    public String getCommand() {
        return RIGHT;
    }

    @Override
    public void execute(int percentage) {
        try{
            StopCommand stopCommand = new StopCommand();
            stopCommand.execute(StopCommand.BOTH_STOP);

            Runtime runtime = Runtime.getRuntime();
            String command = String.format("python ./commands/forward1.py %d",
                    percentage);
            runtime.exec(command);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
