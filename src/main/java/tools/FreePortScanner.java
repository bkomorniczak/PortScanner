package tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class FreePortScanner {
    private static final int POOL_SIZE = 10;
    private static final int TIME_OUT = 200;
    private static final Logger log = LoggerFactory.getLogger(FreePortScanner.class);

    public void runPortScan(String ip, int numberOfPortsToScan) {
        ConcurrentLinkedQueue<Object> openPorts = new ConcurrentLinkedQueue<>();
        ExecutorService executorService = Executors.newFixedThreadPool(POOL_SIZE);
        AtomicInteger port = new AtomicInteger(0);
        while (port.get() < numberOfPortsToScan) {
            final int currentPort = port.getAndIncrement();
            executorService.submit(() -> {
                try {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(ip, currentPort), TIME_OUT);
                    socket.close();
                    openPorts.add(currentPort);
                    log.debug((String.format("%s ,port open: %s", ip, currentPort)));
                } catch (IOException e) {
                    //log.info(String.format("Caught exception: %s", e.getMessage()));
                }
            });
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        List<Object> openPortList = new ArrayList<>();

        while (!openPorts.isEmpty()) {
            openPortList.add(openPorts.poll());
        }

//        openPortList.forEach(System.out::println);
    }
}