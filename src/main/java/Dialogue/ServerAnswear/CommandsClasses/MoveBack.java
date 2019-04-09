package Dialogue.ServerAnswear.CommandsClasses;

import Dialogue.Root;
import Dialogue.ServerAnswear.CommandsWork;

import java.io.*;

public class MoveBack implements CommandsWork {
    @Override
    public void execute(Root root, DataOutputStream dataOutputStream) {
        try {
            File file = new File(root.getPATH());
            root.setPATH(file.getParent());
            dataOutputStream.writeUTF(root.getPATH() + root.getTail());
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
