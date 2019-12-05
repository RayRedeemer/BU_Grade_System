package backend;

/**
 * Interface used for classes to compute some basic statistics.
 */
public interface Statisticsable {
    /**
     * return average
     * @return
     */
    public double getAvg();

    /**
     * return median
     * @return
     */
    public double getMedian();

    /**
     * return range
     * @return
     */
    public double getRange();

    /**
     * return standard deviation
     * @return
     */
    public double getStdDev();
}
