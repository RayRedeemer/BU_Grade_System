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
     * return range lower bound
     * @return
     */
    public double getRangeLowerBound();

    /**
     * return range upper bound
     * @return
     */
    public double getRangeUpperBound();
}
