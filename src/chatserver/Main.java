package chatserver;

import java.io.IOException;

public class Main extends CommandInterpreter {

    public static void main(String args[]) throws IOException {

        Server server = new Server();
        server.serve();

    }
}

