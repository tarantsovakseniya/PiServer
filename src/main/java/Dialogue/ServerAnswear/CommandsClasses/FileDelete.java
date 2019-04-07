package Dialogue.ServerAnswear.CommandsClasses;

import Dialogue.Root;
import Dialogue.ServerAnswear.CommandsWork;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

public class FileDelete implements CommandsWork {
    @Override
    public void execute(Root root, DataOutputStream dataOutputStream) {
        try {
            File file = new File(root.getFromClient());
            if (file.exists()) file.delete();
            dataOutputStream.writeUTF(root.getPATH() + root.getTail());
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}