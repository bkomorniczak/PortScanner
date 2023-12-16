package tools;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class IpFinder {
    private static final Logger log = LoggerFactory.getLogger(IpFinder.class);

    public String retrieveIpAddress() {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("google.com", 80));
            return socket.getLocalAddress().getHostAddress();
        } catch (IOException e) {
            log.debug(String.format("Caught exception: %s", e.getMessage()));
        }
        return null;
    }
}
