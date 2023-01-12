package Testing;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StressTesting {

    private static final int NUM_THREADS = 100;
    private static final int NUM_REQUESTS_PER_THREAD = 200;
    private static final String TARGET_URL = "https://studenti.uni-pr.edu/";

    public static void main(String[] args) {
    	// Create an executor service to manage the threads
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        for (int i = 0; i < NUM_THREADS; i++) {
            Runnable worker = new RequestThread(NUM_REQUESTS_PER_THREAD, TARGET_URL);
            executor.execute(worker);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("Finished all threads");
    }
}

//Klasa qe implementon interface runnable perdoret per me kriju threda
class RequestThread implements Runnable {
    private final int numRequests;
    private final String targetUrl;
    long startTime = System.currentTimeMillis();
    RequestThread(int numRequests, String targetUrl) {
        this.numRequests = numRequests;
        this.targetUrl = targetUrl;
    }

    //contains the code that will be executed by the thread.
    @Override
    public void run() {
        for (int i = 0; i < numRequests; i++) {
            try {
                URL url = new URL(targetUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET"); 
                connection.setConnectTimeout(500);
                connection.setReadTimeout(500);
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode != 200) {
                    System.out.println("Received non-200 response: " + responseCode);
                }
                if(responseCode == 200) {
                	System.out.println("Response 200 , responseTime = " + (System.currentTimeMillis()-startTime));
                }
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    
                }
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}






