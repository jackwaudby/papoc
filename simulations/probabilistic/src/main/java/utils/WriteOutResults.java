package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class write the system metrics to a CSV file called results.csv.
 */
public class WriteOutResults {

    public static void writeOutResults() {

        String[] headers = {"tps","arrivals", "aborts","collisions","arbitration","commits","sentToArbiter"};

        StringBuilder headerStringBuilder = new StringBuilder();
        for(String header : headers){
            headerStringBuilder.append(header).append(",");
        }
        String headerString = headerStringBuilder.toString();
        if( headerString.length() > 0 ) // remove trailing comma
            headerString = headerString.substring(0, headerString.length() - 1);

        String results = SystemMetrics.getInstance().toString();

        BufferedWriter outputStream = null;
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter("results.csv");
            outputStream = new BufferedWriter(fileWriter);
            outputStream.append(headerString);
            outputStream.append("\n");
            outputStream.append(results);
            outputStream.append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
