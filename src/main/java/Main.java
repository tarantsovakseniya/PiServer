import Dialogue.PIServer;
import Dialogue.Root;

public class Main {

    public static void main(String[] args) {

        Root root = new Root(args[0]);
        System.out.println(args[0]);
        PIServer piServer = new PIServer(36007);
        piServer.setRoot(root);
        piServer.setStop(()->{
            piServer.setAlive(false);
            piServer.interrupt();
            System.exit(0);
        });
        piServer.start();
    }
}
