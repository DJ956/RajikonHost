package agent;

public interface IServerAgent {
    void start(int controlPort, int cameraPort);
    void stop();
}
