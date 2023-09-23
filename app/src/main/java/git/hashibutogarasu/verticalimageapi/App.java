package git.hashibutogarasu.verticalimageapi;

import com.sun.net.httpserver.HttpServer;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.io.IOException;
import java.net.InetSocketAddress;

public class App {
    public static void main(String[] args) throws IOException{
        AppArgs main = new AppArgs();
        CmdLineParser parser = new CmdLineParser(main);
        try{
            parser.parseArgument(args);
        }
        catch (CmdLineException e) {
            System.out.println("usage:");
            parser.printSingleLineUsage(System.out);
            System.out.println();
            parser.printUsage(System.out);
            return;
        }

        int port = AppArgs.port;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new VerticalAPIServer());
        System.out.println("MyServer wakes up: port=" + port);
        server.start();
    }
}
