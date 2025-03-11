import java.util.concurrent.*;
import java.io.*;

public class HealthCheck {
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final String PHONE_NUMBER = "+123123123";

    public static void main(String[] args) {
        System.out.println("Health Check Monitor started. Alerts will occur every 2 hours. Follow the given instructions");
        scheduler.scheduleAtFixedRate(HealthCheck::performCheck, 0, 2, TimeUnit.HOURS);

        // Keep program running indefinitely
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void performCheck() {
        System.out.println("\nALERT: Are you okay? Respond with 'im okay' or 'im sick' within one minute.");

        ExecutorService inputExecutor = Executors.newSingleThreadExecutor();
        try {
            Future<String> future = inputExecutor.submit(() ->
                    new BufferedReader(new InputStreamReader(System.in)).readLine()
            );

            String response = future.get(1, TimeUnit.MINUTES);
            validateResponse(response);
        } catch (TimeoutException e) {
            emergencyProcedure("No response received");
        } catch (Exception e) {
            emergencyProcedure("Error: " + e.getMessage());
        } finally {
            inputExecutor.shutdownNow();
        }
    }

    private static void validateResponse(String response) {
        if (response != null) {
            String cleanResponse = response.trim().toLowerCase();
            if (cleanResponse.equals("im okay") || cleanResponse.equals("im sick")) {
                System.out.println("Thank you for responding. Next check in 2 hours. Contact me immediately if you're feeling unwell.");
                return;
            }
        }
        emergencyProcedure("Invalid response received");
    }

    private static void emergencyProcedure(String reason) {
        System.out.println(reason + "! Initiating emergency call...");
        try {
            String script = String.format(
                    "tell application \"Messages\"\n" +
                            "    send \"EMERGENCY: User failed health check!\" to buddy \"%s\" of service \"SMS\"\n" +
                            "end tell", PHONE_NUMBER);

            Process p = Runtime.getRuntime().exec(new String[] {"osascript", "-e", script});
            p.waitFor();
            System.out.println("Emergency message sent to " + PHONE_NUMBER);
        } catch (Exception e) {
            System.err.println("Failed to send emergency message: " + e.getMessage());
        }
    }
}
