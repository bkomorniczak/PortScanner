package tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class OccupiedPortScanner {

    private static final Logger log = LoggerFactory.getLogger(OccupiedPortScanner.class);

    public String findPortOccupiers(String port, String text) {
        String output = "";
        try {
            Process process = getProcess(port, text);
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                output = getOutput(process.getInputStream());
                log.info("Netstat Output:\n{}", output);
            } else {
                log.error("Error executing netstat. Exit code: {}", exitCode);
            }
        } catch (Exception e) {
            log.error("Exception occurred: {}", e.getMessage(), e);
        }

        return output;
    }

    private static Process getProcess(String port, String text) throws IOException {
        String cmd = "";
        if ("".equals(port)) {
            cmd = "bash -c \"netstat | grep " + text + "\"";
        } else if ("".equals(text)) {
            cmd = "bash -c \"netstat | grep :" + port + "\"";
        } else {
            cmd = "bash -c \"netstat\"";
        }

        List<String> cmdParts = Arrays.asList("bash", "-c", cmd);
        ProcessBuilder processBuilder = new ProcessBuilder(cmdParts);

        return processBuilder.start();
    }

    private static String getOutput(InputStream inputStream) {
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        } catch (IOException e) {
            log.error("Error reading output stream: {}", e.getMessage(), e);
        }
        return output.toString();
    }
}