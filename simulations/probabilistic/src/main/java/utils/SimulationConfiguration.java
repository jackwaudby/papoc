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

    public boolean simulateTime() {
        return Boolean.parseBoolean(simulationConfiguration.getProperty("sim.time"));
    }

    public void setSimulateTime(String s) {
        simulationConfiguration.setProperty("sim.time", s);
    }

    /**
     * Get simulation period
     * @return int, simulation period
     */
    public double getSimulationPeriod() {
        return Double.parseDouble(simulationConfiguration.getProperty("sim.period"));
    }

    public void setSimulationPeriod(String s) {
        simulationConfiguration.setProperty("sim.period", s);
    }

    /**
     * Get average transaction arrival rate
     * @return double, average transaction arrival rate
     */
    public double getAverageTransactionArrivalRate() {
        return (1 / Double.parseDouble(simulationConfiguration.getProperty("tps")));
    }

    /**
     * Get average transaction arrival rate
     * @return double, average transaction arrival rate
     */
     Integer getTPS() {
        return (Integer.parseInt(simulationConfiguration.getProperty("tps")));
    }

    public void setTPS(String s) {
        simulationConfiguration.setProperty("tps", s);
    }

    /**
     * Get average message delay
     * @return double, average message delay
     */
    double getAverageNetworkDelay() {
        return Double.parseDouble(simulationConfiguration.getProperty("av.network.delay"));
    }

    public void setAverageNetworkDelay(String s) {
        simulationConfiguration.setProperty("av.network.delay", s);
    }

    public int getDatabaseSize() {
        return Integer.parseInt(simulationConfiguration.getProperty("database.size"));
    }

    public void setDatabaseSize(String s) {
        simulationConfiguration.setProperty("database.size", s);
    }

    double getAverageArbiterServiceTime() {
        return Double.parseDouble(simulationConfiguration.getProperty("arbiter.service"));
    }

    public void setAverageArbiterServiceTime(String s) {
        simulationConfiguration.setProperty("arbiter.service", s);
    }

    public int getTxnLimit(){
        return Integer.parseInt(simulationConfiguration.getProperty("txn.limit"));
    }

    public void setTxnLimit(String s) {
        simulationConfiguration.setProperty("txn.limit", s);
    }

    long getSeedValue() {
        return Long.parseLong(simulationConfiguration.getProperty("seed.value"));
    }

    public void setSeedValue(String s) {
        simulationConfiguration.setProperty("seed.value", s);
    }

    boolean isSeedSet() {
        return Boolean.parseBoolean(simulationConfiguration.getProperty("fix.seed"));
    }

    public void setSeed(String s) {
        simulationConfiguration.setProperty("fix.seed", s);
    }

    int getLength() {
     return Integer.parseInt(simulationConfiguration.getProperty("length"));
    }

    public void setLength(String s) {
        simulationConfiguration.setProperty("length", s);
    }

    double getStartValue() {
        return Double.parseDouble(simulationConfiguration.getProperty("start.value"));
    }

    public void setStartValue(String s) {
        simulationConfiguration.setProperty("start.value", s);
    }

    double getRatio(){
        return Double.parseDouble(simulationConfiguration.getProperty("common.ratio"));
    }

    public void setCommonRatio(String s) {
        simulationConfiguration.setProperty("common.ratio", s);
    }


    public int getScaleFactor(){
        return Integer.parseInt(simulationConfiguration.getProperty("scale.factor"));
    }

    public void setScaleFactor(String s) {
        simulationConfiguration.setProperty("scale.factor", s);
    }

    public boolean getUseArbiter() {
        return Boolean.parseBoolean(simulationConfiguration.getProperty("use.arbiter"));
    }


}
