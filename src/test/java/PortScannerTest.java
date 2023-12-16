import org.junit.jupiter.api.Test;
import tools.FreePortScanner;

import java.io.IOException;

class PortScannerTest {
    private static final int nbrPortMax = 1000; // Max is 65535, number of available ports
    private static final String ip = "127.0.0.1";
    FreePortScanner freePortScanner = new FreePortScanner();

    @Test
    void when_Run_then_lunchPortScan() throws IOException {
        freePortScanner.runPortScan(ip, nbrPortMax);
    }
}