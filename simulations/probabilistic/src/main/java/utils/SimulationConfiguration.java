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

    public Integer getTPS() {
        return (Integer.parseInt(simulationConfiguration.getProperty("tps")));
    }

    public void setTPS(long TPS) {
        simulationConfiguration.setProperty("tps", String.valueOf(TPS));
    }

    double getAverageNetworkDelay() {
        return Double.parseDouble(simulationConfiguration.getProperty("av.network.delay"));
    }

    public int getDatabaseSize() {
        return Integer.parseInt(simulationConfiguration.getProperty("database.size"));
    }

    long getSeedValue() {
        return Long.parseLong(simulationConfiguration.getProperty("seed.value"));
    }

    boolean isSeedSet() {
        return Boolean.parseBoolean(simulationConfiguration.getProperty("fix.seed"));
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

    double getDeltaInMS(){
        return Double.parseDouble(simulationConfiguration.getProperty("delta"));
    }

    public double getDelta(){
        return Double.parseDouble(simulationConfiguration.getProperty("delta"))/1000;
    }

    public void setDelta(double delta){
        simulationConfiguration.setProperty("delta",String.valueOf(delta));
    }

    public int getTxnLimit(){
        return Integer.parseInt(simulationConfiguration.getProperty("txn.limit"));
    }


    public boolean saveResults() {
        return Boolean.parseBoolean(simulationConfiguration.getProperty("save.results"));
    }

}
