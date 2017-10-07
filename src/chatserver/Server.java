package chatserver;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public void serve() {

        // Used to register the CI as a observer to both of these lists
        ChatHistory history = ChatHistory.getInstance();
        UserNameList users = UserNameList.getInstance();

        try {
            ServerSocket ss = new ServerSocket(1337, 2);

            while (true) {
                System.out.println("I have socket " + ss.getLocalPort());
                Socket s = ss.accept();
                System.out.println("Accepted new connection");
                CommandInterpreter ci = new CommandInterpreter(s.getInputStream(), new PrintStream(s.getOutputStream(), true));
                history.register(ci);
                users.register(ci);

                Thread t = new Thread(ci);
                t.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
