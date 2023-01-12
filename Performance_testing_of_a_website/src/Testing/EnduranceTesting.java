package Testing;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class EnduranceTesting {
  public static void main(String[] args) {
    // URL of the website to test
    String url = "https://studenti.uni-pr.edu/";

    // Number of iterations to perform
    int iterations = 1000;

    // Time to wait between iterations (in seconds)
    int waitTime = 1;

    // Perform the endurance test
    for (int i = 0; i < iterations; i++) {
      try {
        // Open a connection to the website
        URL website = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) website.openConnection();

        // Set the request method to GET
        connection.setRequestMethod("GET");

        // Connect to the website
        connection.connect();

        // Get the response code
        int responseCode = connection.getResponseCode();

        // Print the response code
        System.out.println("Iteration " + (i+1) + ": Response code = " + responseCode);

        // Close the connection
        connection.disconnect();

        // Wait for the specified amount of time before the next iteration
        TimeUnit.SECONDS.sleep(waitTime);
      } catch (IOException | InterruptedException e) {
        // Print the error message
        System.out.println("Error: " + e.getMessage());
      }
    }
  }
}


