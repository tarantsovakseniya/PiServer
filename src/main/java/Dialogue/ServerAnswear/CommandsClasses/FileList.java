package Dialogue.ServerAnswear.CommandsClasses;

import Dialogue.Root;
import Dialogue.ServerAnswear.CommandsWork;

import java.io.*;

public class FileList implements CommandsWork {

    @Override
    public void execute(Root root, DataOutputStream dataOutputStream) {
        try {
            File file = new File(root.getPATH());
            String[] fileNames = file.list();
            StringBuilder stringBuilder = new StringBuilder();
            for (String s : fileNames) {
                stringBuilder.append(s + "\n");
            }
            stringBuilder.append(file.getPath() + "$");
            System.out.println(stringBuilder.toString());
            dataOutputStream.writeUTF(stringBuilder.toString());
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
