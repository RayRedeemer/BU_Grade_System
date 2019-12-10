package backend;

/**
 * Interface used for classes to compute some basic statistics.
 */
public interface Statisticsable {
    /**
     * return average
     * @return
     */
    public double getMean();

    /**
     * return median
     * @return
     */
    public double getMedian();

    /**
     * return range lower bound
     * @return
     */
    public double getMinimum();

    /**
     * return range upper bound
     * @return
     */
    public double getMaximum();

    public double getRange();
}
