package handler.command.control;

import java.io.IOException;

public class BackCommand implements ControlCommand {

    public static final String BACK = "Back";

    @Override
    public String getCommand() {
        return BACK;
    }

    @Override
    public void execute(int percentage){
        try{
            System.out.println(percentage);
            Runtime runtime = Runtime.getRuntime();
            String command = String.format("python ./commands/back.py %d & python ./commands/back1.py %d",
                    percentage, percentage);
            runtime.exec(command);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
