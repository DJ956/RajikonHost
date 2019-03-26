package agent;

public class MainServer {

    private static final int PORT = 6665;
    private static final int CAMERA_PORT = 6666;

    public static void main(String[] args) throws Exception {
        ServerAgent serverAgent = new ServerAgent();
        serverAgent.start(PORT, CAMERA_PORT);
    }

}
