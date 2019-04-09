package Dialogue.ServerAnswear.CommandsClasses;

import Dialogue.Root;
import Dialogue.ServerAnswear.CommandsWork;

import java.io.*;

public class FileDownload implements CommandsWork {
    @Override
    public void execute(Root root, DataOutputStream dataOutputStream) {
        try {
            File file = new File(root.getFromClient());
            System.out.println(root.getFromClient());
            if (file.exists()) {
                System.out.println("file exist "+ file.getName());
                System.out.println((int)file.length());
                dataOutputStream.writeInt((int)file.length());
                byte[] bytes = new byte[(int) file.length()];
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file.getPath()));
                bis.read(bytes, 0, bytes.length);
                dataOutputStream.write(bytes, 0, bytes.length);
                dataOutputStream.writeUTF("File was downloaded." + "\n"+ root.getPATH()+root.getTail());
                bis.close();
                dataOutputStream.flush();
            }
        } catch (IOException e) {
            try {
                dataOutputStream.writeUTF("File is already in use. Please, try again later." +
                        "\n" + root.getPATH() + root.getTail());
                dataOutputStream.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}