package handler.command.control;

public interface ControlCommand {
    String getCommand();
    void execute(int percentage);
}
