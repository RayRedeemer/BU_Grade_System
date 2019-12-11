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

    /**
     * return range
     * @return
     */
    public double getRange();

    /**
     * set mean
     * @param m
     */
    public void setMean(double m);

    /**
     * set median
     * @param m
     */
    public void setMedian(double m);

    /**
     * set minimum
     * @param m
     */
    public void setMinimum(double m);

    /**
     * set maximum
     * @param m
     */
    public void setMaximum(double m);

    /**
     * set range
     * @param r
     */
    public void setRange(double r);
}
