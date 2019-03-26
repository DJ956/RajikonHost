package handler.command.control;

import java.io.IOException;

public class LeftForwardCommand implements ControlCommand {

    private static final String LEFT = "Left";

    @Override
    public String getCommand() {
        return LEFT;
    }

    @Override
    public void execute(int percentage) {
        try{
            StopCommand stopCommand = new StopCommand();
            stopCommand.execute(StopCommand.BOTH_STOP);

            Runtime runtime = Runtime.getRuntime();
            String command = String.format("python ./commands/forward.py %d",
                    percentage);
            runtime.exec(command);

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
