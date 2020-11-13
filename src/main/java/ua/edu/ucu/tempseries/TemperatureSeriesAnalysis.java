package ua.edu.ucu.tempseries;

import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {
    private double[] temperatureSeries;
    private int length;
    static final double minTemp = -273.15;  // The minimal possible temperature

    public TemperatureSeriesAnalysis() {
        temperatureSeries = new double[] {};
        length = 0;
    }

    private void checkIfValidTemperatures(double[] temperatureSeries) {
        for (double temp : temperatureSeries)
            if (temp < minTemp)
                throw new InputMismatchException();
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        checkIfValidTemperatures(temperatureSeries);
        this.temperatureSeries = temperatureSeries.clone();  // Clone the passed array so the changes
                                                             // made to it won't affect the cloned one
        length = temperatureSeries.length;  // We make an assumption that the passed array contain
                                            // only elements, that we want to be temperatures
    }

    private double findSum() {
        double sum = 0;
        for (double temp : temperatureSeries)
            sum += temp;
        return sum;
    }

    private int getLength() {
        return length;
    }

    private void checkIfNotEmpty() {
        if (getLength() == 0)
            throw new IllegalArgumentException();
    }

    public double average() {
        checkIfNotEmpty();
        return findSum() / getLength();
    }

    public double deviation() {
        checkIfNotEmpty();
        double mean = average();
        double sum = 0;
        for (double temp : temperatureSeries)
            sum += Math.pow(temp - mean, 2);
        return Math.sqrt(sum / getLength());
    }

    public double min() {
        checkIfNotEmpty();
        double minValue = temperatureSeries[0];
        for (double temp : temperatureSeries)
            if (temp < minValue)
                minValue = temp;
        return minValue;
    }

    public double max() {
        checkIfNotEmpty();
        double maxValue = temperatureSeries[0];
        for (double temp : temperatureSeries)
            if (temp > maxValue)
                maxValue = temp;
        return maxValue;
    }

    public double findTempClosestToZero() {
        return findTempClosestToValue(0.0);
    }

    public double findTempClosestToValue(double tempValue) {
        checkIfNotEmpty();
        double closestTemp = temperatureSeries[0];
        double closestDiff = Math.abs(closestTemp - tempValue);
        for (double temp : temperatureSeries) {
            double diff = Math.abs(tempValue - temp);
            if (diff < closestDiff) {
                closestTemp = temp;
                closestDiff = diff;
            } else if (diff == closestDiff && temp > closestTemp)
                closestTemp = temp;
        }
        return closestTemp;
    }

    private int findNumTempsLessThen(double tempValue) {
        int num = 0;
        for (double temp : temperatureSeries)
            if (temp < tempValue)
                num++;
        return num;
    }

    private int findNumTempsGreaterThen(double tempValue) {
        return getLength() - findNumTempsLessThen(tempValue);
    }

    public double[] findTempsLessThen(double tempValue) {
        checkIfNotEmpty();
        double[] lesserTemps = new double[findNumTempsLessThen(tempValue)];
        int i = 0;
        for (double temp : temperatureSeries)
            if (temp < tempValue)
                lesserTemps[i++] = temp;
        return lesserTemps;
    }

    public double[] findTempsGreaterThen(double tempValue) {
        checkIfNotEmpty();
        double[] greaterTemps = new double[findNumTempsGreaterThen(tempValue)];
        int i = 0;
        for (double temp : temperatureSeries)
            if (temp >= tempValue)
                greaterTemps[i++] = temp;
        return greaterTemps;
    }

    public TempSummaryStatistics summaryStatistics() {
        checkIfNotEmpty();
        return new TempSummaryStatistics(this);
    }

    public int addTemps(double... temps) {
        checkIfValidTemperatures(temperatureSeries);
        if (temperatureSeries.length - getLength() <= temps.length) {
            double[] newTemps = new double[2*(getLength() + temps.length)];
            System.arraycopy(temperatureSeries, 0, newTemps, 0, getLength());
            temperatureSeries = newTemps;
        }
        System.arraycopy(temps, 0, temperatureSeries, getLength(), temps.length);
        length += temps.length;
        return 0;
    }
}
