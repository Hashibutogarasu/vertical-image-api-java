package git.hashibutogarasu.verticalimageapi;

import org.kohsuke.args4j.Option;

public class AppArgs {
    @Option(name = "-p", aliases = {"--port" }, metaVar = "port", required = true, usage = "port")
    public static int port = 3001;

    public void execute() {
    }
}
