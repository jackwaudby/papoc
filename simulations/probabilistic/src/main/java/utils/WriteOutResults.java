package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class write the system metrics to a CSV file called results.csv.
 */
public class WriteOutResults {

    public static void writeOut(String[] string) {

        StringBuilder headerStringBuilder = new StringBuilder();
        for(String header : string){
            headerStringBuilder.append(header).append(",");
        }

        String headerString = headerStringBuilder.toString();
        if( headerString.length() > 0 ) // remove trailing comma
            headerString = headerString.substring(0, headerString.length() - 1);


        BufferedWriter outputStream = null;
        FileWriter fileWriter;
        int delta = (int) SimulationConfiguration.getInstance().getDeltaInMS();
        int tps = SimulationConfiguration.getInstance().getTPS();
        try {
            fileWriter = new FileWriter("./delta_" + delta + "_" + tps + ".csv",true);
            outputStream = new BufferedWriter(fileWriter);
            outputStream.append(headerString);
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
