package handler.command.control;

import java.io.IOException;

public class ForwardCommand implements ControlCommand {

    public static final String FORWARD = "ForwardCommand";

    @Override
    public String getCommand() {
        return FORWARD;
    }

    @Override
    public void execute(int percentage) {
        try{
            Runtime runtime = Runtime.getRuntime();
            String command = String.format("python ./commands/forward.py %d & python ./commands/forward1.py %d",
                    percentage, percentage);
            runtime.exec(command);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
