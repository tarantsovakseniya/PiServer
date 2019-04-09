package Dialogue.ServerAnswear;

import Dialogue.ServerAnswear.CommandsClasses.*;

public class CommandsFactory {

    public CommandsFactory() {
    }

    public CommandsWork getCommand(Commands command) {
        CommandsWork toReturn = null;
        switch (command) {
            case FILELIST:
                toReturn = new FileList();
                break;
            case TREE:
                toReturn = new FileTreeList();
                break;
            case MOVETO:
                toReturn = new MoveTo();
                break;
            case BACKTO:
                toReturn = new MoveBack();
                break;
            case DOWNLOAD:
                toReturn = new FileDownload();
                break;
            case UPLOAD:
                toReturn = new FileUpload();
                break;
            case DELETE:
                toReturn = new FileDelete();
                break;
        }
        return toReturn;
    }
}
