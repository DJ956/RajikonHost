package handler.command.control;

import java.io.IOException;

public class RightBackCommand implements ControlCommand {

    public static final String RIGHT_BACK = "Right_Back";

    @Override
    public String getCommand() {
        return RIGHT_BACK;
    }

    @Override
    public void execute(int percentage) {
        try{
            StopCommand stopCommand = new StopCommand();
            stopCommand.execute(StopCommand.BOTH_STOP);

            Runtime runtime = Runtime.getRuntime();
            String command = String.format("python ./commands/back1.py %d",
                    percentage);
            runtime.exec(command);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
