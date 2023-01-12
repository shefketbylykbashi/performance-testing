package Testing;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class LoadTester {
     
    // Number of concurrent threads to use for the load test
    private static final int NUM_THREADS = 100;

    // Target URL for the load test
    private static final String TARGET_URL = "https://studenti.uni-pr.edu/";

    public static void main(String[] args) throws InterruptedException, IOException {
        // Create an executor service to manage the threads
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        // Create a HTTP client to send requests
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // Create a timer to measure the duration of the load test
        long startTime = System.currentTimeMillis();

        // Submit a task for each thread to send a request
        for (int i = 0; i < NUM_THREADS; i++) {
            executor.submit(() -> {
                try {
                    // Send a GET request to the target URL
                    HttpGet request = new HttpGet(TARGET_URL);
                    CloseableHttpResponse response = httpClient.execute(request);

                    // Record the response time for this request
                    long responseTime = System.currentTimeMillis() - startTime;
                    System.out.println("Response time: " + responseTime + "ms");

                    // Close the response
                    response.close();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        // Shut down the executor service and wait for all tasks to complete
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);

        // Close the HTTP client
        httpClient.close();
    }
}

