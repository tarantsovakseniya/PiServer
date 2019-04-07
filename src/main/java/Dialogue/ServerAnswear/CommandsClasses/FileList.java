package Dialogue.ServerAnswear.CommandsClasses;

import Dialogue.Root;
import Dialogue.ServerAnswear.CommandsWork;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

public class FileList implements CommandsWork {

    @Override
    public void execute(Root root, DataOutputStream dataOutputStream) {
        System.out.println("fileList");
        try {
            File file = new File(root.getPATH());
            System.out.println("create");
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
