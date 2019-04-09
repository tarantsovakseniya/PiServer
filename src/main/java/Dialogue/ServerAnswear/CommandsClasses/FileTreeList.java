package Dialogue.ServerAnswear.CommandsClasses;

import Dialogue.Root;
import Dialogue.ServerAnswear.CommandsWork;

import java.io.*;

public class FileTreeList implements CommandsWork {

    @Override
    public void execute(Root root, DataOutputStream dataOutputStream) {
        try {
            File file = new File(root.getPATH());
            StringBuilder stringBuilder = new StringBuilder();
            File[] tree = file.listFiles();
            stringBuilder.append(file.getPath()+"\n");
            buildTree(tree, "|-", stringBuilder);
            stringBuilder.append(file.getPath() + "$");
            dataOutputStream.writeUTF(stringBuilder.toString());
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buildTree(File[] list, String m, StringBuilder stringBuilder) {
        for (File f : list) {
            if (f.isFile()) {
                stringBuilder.append(m);
                stringBuilder.append(f.getName() + "\n");
            } else {
                stringBuilder.append(m);
                stringBuilder.append(f.getName() + "\n");
                String t = "| " + m;
                buildTree(f.listFiles(), t, stringBuilder);
            }
        }
    }
}
