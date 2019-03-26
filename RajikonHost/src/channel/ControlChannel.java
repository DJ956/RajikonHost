package channel;

import handler.IControlHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ControlChannel implements Channel {

    private final int PORT;

    private ServerSocket serverSocket = null;
    private IControlHandler handler = null;

    public ControlChannel(int port, IControlHandler handler){
        PORT = port;
        this.handler = handler;
    }

    public void start() {
        try{
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server start:" + serverSocket.getLocalSocketAddress());

            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                handler.onConnected(socket.getRemoteSocketAddress());
                ControlTask controlTask = new ControlTask(socket);
                controlTask.run();

                //コントロールちゃんねるを終了させるためループを止める
                if(controlTask.endSession){
                    break;
                }
            }
        }catch (IOException e){
            handler.onException(e);
        }finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                handler.onException(e);
            }
        }
    }

    public void stop(){
        if(serverSocket != null){
            try {
                serverSocket.close();
            }catch (IOException e){
                handler.onException(e);
            }
        }
    }

    private class ControlTask {
        private static final String END_SESSION = "END_SESSION";
        private static final String END_CHANNEL = "END_CHANNEL";
        private volatile boolean endSession = false;
        private Socket socket = null;

        private ControlTask(Socket socket){
            this.socket = socket;
        }

        private void run(){
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))){
                while (true) {
                    String line = reader.readLine();
                    handler.onReceiveMessage(line);

                    if(line == null){
                        endSession = true;
                        socket.close();
                        handler.onDisconnected();
                        break;
                    }

                    //コントロールチャンネル終了
                    if (line.equals(END_CHANNEL)) {
                        endSession = true;
                        socket.close();
                        handler.onDisconnected();
                        //セッション終了
                    }else if(line.equals(END_SESSION)){
                        socket.close();
                        handler.onDisconnected();
                        break;
                    }
                }
            }catch (IOException e){
                handler.onException(e);
            }finally {
                if(socket != null) {
                    try {
                        socket.close();
                        handler.onDisconnected();
                    } catch (IOException e1) {
                        handler.onException(e1);
                    }
                }
            }
        }
    }
}
