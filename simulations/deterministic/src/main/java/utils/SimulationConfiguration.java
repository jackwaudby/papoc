package utils;

import org.apache.commons.configuration.Configuration;

import java.io.InputStream;
import java.util.Properties;

/**
 * Reads in configuration .properties file
 */
public class SimulationConfiguration {

    private static final SimulationConfiguration instance = new SimulationConfiguration();

    private SimulationConfiguration(){

    }

    public static SimulationConfiguration getInstance() {
        return instance;
    }

    private static Properties simulationConfiguration = new Properties();

    static {
        try {
            InputStream in = Configuration.class.getResourceAsStream("/simulation.properties");
            simulationConfiguration.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Get average transaction arrival rate
     * @return double, average transaction arrival rate
     */
    Integer getTPS() {
        return (Integer.parseInt(simulationConfiguration.getProperty("tps")));
    }

   public void setTPS(String tps) {
        simulationConfiguration.setProperty("tps",tps);
    }


    double getAverageNetworkDelay() {
        return Double.parseDouble(simulationConfiguration.getProperty("av.network.delay"));
    }

    public int getDatabaseSize() {
        return Integer.parseInt(simulationConfiguration.getProperty("database.size"));
    }

    public int getTxnLimit(){
        return Integer.parseInt(simulationConfiguration.getProperty("txn.limit"));
    }

    long getSeedValue() {
        return Long.parseLong(simulationConfiguration.getProperty("seed.value"));
    }

    boolean isSeedSet() {
        return Boolean.parseBoolean(simulationConfiguration.getProperty("fix.seed"));
    }

    public boolean saveResults() {
        return Boolean.parseBoolean(simulationConfiguration.getProperty("save.results"));
    }

    int getLength() {
     return Integer.parseInt(simulationConfiguration.getProperty("length"));
    }

    double getStartValue() {
        return Double.parseDouble(simulationConfiguration.getProperty("start.value"));
    }

    double getRatio(){
        return Double.parseDouble(simulationConfiguration.getProperty("common.ratio"));
    }

}
