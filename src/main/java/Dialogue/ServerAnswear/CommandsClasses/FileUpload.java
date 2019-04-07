package Dialogue.ServerAnswear.CommandsClasses;

import Dialogue.Root;
import Dialogue.ServerAnswear.CommandsWork;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUpload implements CommandsWork {

    int size;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    //ask about available free space
    //проверка названия файла
    @Override
    public void execute(Root root, DataOutputStream dataOutputStream) {
        try {
            File file = new File(root.getFromClient());
            if (file.exists()) {
                file = new File(root.getFromClient() + 1);
            }
            file.createNewFile();

            dataOutputStream.writeUTF("size");
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void size(int size){
        FileOutputStream

    }
}
