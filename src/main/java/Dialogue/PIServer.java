package Dialogue;

import java.io.*;
import java.net.*;

import Dialogue.ServerAnswear.*;
import Dialogue.ClientsRequest.*;
import Dialogue.ServerAnswear.CommandsClasses.Stop;

public class PIServer extends Thread {

    private ServerSocket serverSocket;
    private Root root;
    private boolean alive = true;
    private Stop stop;
    private CommandsFactory commandsFactory;

    public PIServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setStop(Stop stop) {
        this.stop = stop;
    }

    public Stop getStop() {
        return stop;
    }

    public void setRoot(Root root) {
        this.root = root;
    }

    @Override
    public void run() {
        while (alive) {
            System.out.println("Connection was started...");
            try {
                final Socket socket = serverSocket.accept();
                commandsFactory = new CommandsFactory();
                System.out.println("Waiting for client...");
                Thread thread = new Thread(() -> {
                    System.out.println("Client connected...");
                    try (DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                         DataInputStream dataInputStream = new DataInputStream(socket.getInputStream())) {
                        dataOutputStream.writeUTF(root.getRoot() + root.getTail());
                        dataOutputStream.flush();
                        while (true) {
                            try {
                                String data = "";
                                data = dataInputStream.readUTF();
                                getData(data, dataOutputStream, dataInputStream,() -> socket.close());
                            } catch (SocketException exception) {
                                exception.getMessage();
                            }
                        }
                    } catch (IOException e) {
                        try {socket.close();}
                        catch (IOException ex) {ex.printStackTrace();}
                        e.printStackTrace();
                    }
                });
                thread.setDaemon(true);
                thread.start();
            } catch (SocketException e){
                e.getCause();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void getData(String data, DataOutputStream dataOutputStream, DataInputStream dataInputStream,Stop stopClient) {
        try {
            if (data == null) return;
            ClientRequest.Request request = RequestSearch.getRequest(data);
            if (request.equals(ClientRequest.Request.CD)
                    || request.equals(ClientRequest.Request.DWN)
                    || request.equals(ClientRequest.Request.UPLOAD)
                    || request.equals(ClientRequest.Request.DEL)) {
                root.checkRootFromData(request, data);
            }

            switch (request) {
                case LL:
                    CommandsWork fileList = commandsFactory.getCommand(Commands.FILELIST);
                    fileList.execute(root, dataOutputStream);
                    break;
                case TREE:
                    CommandsWork tree = commandsFactory.getCommand(Commands.TREE);
                    tree.execute(root, dataOutputStream);
                    break;
                case CD:
                    CommandsWork moveTo = commandsFactory.getCommand(Commands.MOVETO);
                    moveTo.execute(root, dataOutputStream);
                    break;
                case BACK:
                    CommandsWork moveBack = commandsFactory.getCommand(Commands.BACKTO);
                    moveBack.execute(root, dataOutputStream);
                    break;
                case DWN:
                    CommandsWork download = commandsFactory.getCommand(Commands.DOWNLOAD);
                    download.execute(root, dataOutputStream);
                    break;
                case UPLOAD:
                    int size = dataInputStream.readInt();
                    byte[] bytes = new byte[size];
                    dataInputStream.read(bytes, 0, bytes.length);
                    File file = new File(root.getFromClient());
                    if (file.getUsableSpace() >= size) {
                        file.createNewFile();
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        fileOutputStream.write(bytes, 0, bytes.length);
                        fileOutputStream.close();
                        CommandsWork upload = commandsFactory.getCommand(Commands.UPLOAD);
                        upload.execute(root, dataOutputStream);
                    } else {
                        dataOutputStream.writeUTF("No empty space. File was not uploaded.");
                        dataOutputStream.flush();
                    }
                    break;
                case DEL:
                    CommandsWork delete = commandsFactory.getCommand(Commands.DELETE);
                    delete.execute(root, dataOutputStream);
                    break;
                case STOP:
                    stopClient.stop();
                    System.out.println("Client disconnected.");
                    break;
                case FINISH:
                    getStop().stop();
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
