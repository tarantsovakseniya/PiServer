package Dialogue.ServerAnswear.CommandsClasses;

import Dialogue.Root;
import Dialogue.ServerAnswear.CommandsWork;

import java.io.*;

public class FileUpload implements CommandsWork {
    @Override
    public void execute(Root root, DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.writeUTF("File was uploaded." + "\n"+root.getPATH()+root.getTail());
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
