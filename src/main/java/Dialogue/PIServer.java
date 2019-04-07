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

    @Override
    public void run() {
        while (alive) {
            System.out.println("Connection was started...");
            try {
                final Socket socket = serverSocket.accept();
                root = new Root();
                commandsFactory = new CommandsFactory();
                Thread thread = new Thread(() -> {
                    try (DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                         DataInputStream dataInputStream = new DataInputStream(socket.getInputStream())) {
                        dataOutputStream.writeUTF(root.getRoot() + root.getTail());
                        dataOutputStream.flush();
                        while (true) {
                            try {
                                String data = "";
                                data = dataInputStream.readUTF();
                                System.out.println("Data " + data);
                                getData(data, dataOutputStream, () -> {
                                    dataOutputStream.close();
                                    dataInputStream.close();
                                    socket.close();
                                });
                                if (data.equals("end") || data.equals("stop")) {
                                    break;
                                }
                            } catch (SocketException exception) {
                                exception.getMessage();
                            }
                        }
                    } catch (IOException e) {
                        try {
                            socket.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        e.printStackTrace();
                    }
//                    while (true) {
//                        try {
//                            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
//                            dataOutputStream.writeUTF(root.getRoot() + root.getTail());
//                            dataOutputStream.flush();
//                            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
//                            while (true) {
//                                String data = "";
//                                data = dataInputStream.readUTF();
//                                System.out.println("Data " + data);
//                                getData(data, dataOutputStream, () -> {
//                                    dataInputStream.close();
//                                    dataOutputStream.close();
//                                    socket.close();
//                                });
//                                if (data.equals("end") || data.equals("stop")) {
//                                    break;
//                                }
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
                });
                thread.setDaemon(true);
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Connection completed.");
        }
        System.out.println("Finish");
    }

    private void getData(String data, DataOutputStream dataOutputStream, Stop stop) throws IOException {
        if (data == null) return;
        ClientRequest.Request request = RequestSearch.getRequest(data);
        if (request == null) return;

        if (request.equals(ClientRequest.Request.CD)
                || request.equals(ClientRequest.Request.DWN)
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
                System.out.println("CD");
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
                CommandsWork upload = commandsFactory.getCommand(Commands.SAVE);
                upload.execute(root, dataOutputStream);
                break;
            case DEL:
                CommandsWork delete = commandsFactory.getCommand(Commands.DELETE);
                delete.execute(root, dataOutputStream);
                break;
            case STOP:
                stop.stop();
                break;
        }
    }
}
