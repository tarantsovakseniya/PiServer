import Dialogue.PIServer;

public class Main {

    public static void main(String[] args) {
        PIServer piServer = new PIServer(8777);
        piServer.setStop(()->{
            piServer.setAlive(false);
            piServer.interrupt();
            System.exit(0);
        });
        piServer.start();
    }
}
