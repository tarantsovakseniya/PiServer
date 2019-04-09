package Dialogue;

import Dialogue.ClientsRequest.ClientRequest;

public class Root {
    private final String root;
    private String PATH;
    private final String tail;
    private String fromClient;

    public Root(String root) {
        this.root = root;
        tail = "$";
        PATH = root;
    }

    public void setPATH(String PATH) {
        if (PATH.startsWith(root)) {
            this.PATH = PATH;
        } else this.PATH = root;
    }

    public String getFromClient() {
        return fromClient;
    }

    public void setFromClient(String fromClient) {
        this.fromClient = fromClient;
    }

    public String getRoot() {
        return root;
    }

    public String getPATH() {
        return PATH;
    }

    public String getTail() {
        return tail;
    }

    public void checkRootFromData(ClientRequest.Request request, String data) {
        setFromClient(request.getP().matcher(data).replaceFirst(""));
        if (getFromClient().startsWith(root)) setFromClient(getFromClient());
        if (getFromClient().equals("home")) setFromClient(getRoot());
        else setFromClient(getPATH() + "\\" + getFromClient());
    }
}