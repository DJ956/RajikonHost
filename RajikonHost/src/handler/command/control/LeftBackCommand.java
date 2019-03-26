package handler.command.control;

import java.io.IOException;

public class LeftBackCommand implements ControlCommand {

    public static final String LEFT_BACK = "Left_Back";

    @Override
    public String getCommand() {
        return LEFT_BACK;
    }

    @Override
    public void execute(int percentage) {
        try{
            StopCommand stopCommand = new StopCommand();
            stopCommand.execute(StopCommand.BOTH_STOP);

            Runtime runtime = Runtime.getRuntime();
            String command = String.format("python ./commands/back.py %d",
                    percentage);
            runtime.exec(command);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
