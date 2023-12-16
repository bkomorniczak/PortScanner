import tools.IpFinder;
import tools.FreePortScanner;
import tools.OccupiedPortScanner;

public class PortScannerApp {

    public static final String X = "************************";

    public static void main(String[] args) {
        IpFinder finder = new IpFinder();
        FreePortScanner freePortScanner = new FreePortScanner();
        OccupiedPortScanner occupiedPortScanner = new OccupiedPortScanner();
        String ip = finder.retrieveIpAddress();
        System.out.println(X);
        System.out.println("SHOWING FREE PORTS");
        System.out.println(X);
        freePortScanner.runPortScan(ip,64000);
        System.out.println(X);
        System.out.println("SHOWING OCCUPIED PORTS");
        System.out.println(X);
        String occupiers = occupiedPortScanner.findPortOccupiers("", "localhost");
        System.out.println(occupiers);

    }
}
