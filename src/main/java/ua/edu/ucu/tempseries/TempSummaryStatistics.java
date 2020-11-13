package ua.edu.ucu.tempseries;

public final class TempSummaryStatistics {
    public final double avgTemp;
    public final double devTemp;
    public final double minTemp;
    public final double maxTemp;

    public TempSummaryStatistics(TemperatureSeriesAnalysis seriesAnalysis) {
        avgTemp = seriesAnalysis.average();
        devTemp = seriesAnalysis.deviation();
        minTemp = seriesAnalysis.min();
        maxTemp = seriesAnalysis.max();
    }

    @Override
    public String toString() {
        return "TempSummaryStatistics(avgTemp=" + avgTemp + ", devTemp="
                + devTemp + ", minTemp=" + minTemp + ", maxTemp=" + maxTemp
                + ")";
    }
}
