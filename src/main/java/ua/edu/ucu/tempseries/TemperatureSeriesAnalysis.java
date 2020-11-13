package ua.edu.ucu.tempseries;

import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {
    static final double MINTEMP = -273.15;  // The minimal possible temperature
    static final double DELTA = 0.0000001;
    private double[] tempSeries;
    private int length;

    public TemperatureSeriesAnalysis() {
        tempSeries = new double[] {};
        length = 0;
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        checkIfValidTemperatures(temperatureSeries);
        this.tempSeries = temperatureSeries.clone();  // Clone the passed array
        // so the changes made to it won't affect the cloned one
        length = temperatureSeries.length;  // We make an assumption that the
        // passed array contain only elements, that we want to be temperatures
    }

    private void checkIfValidTemperatures(double[] temperatureSeries) {
        for (int i = 0; i < temperatureSeries.length; i++) {
            if (temperatureSeries[i] < MINTEMP) {
                throw new InputMismatchException("Temperature can't be less "
                        + "than -273.15");
            }
        }
    }

    private double findSum() {
        double sum = 0;
        for (int i = 0; i < length; i++) {
            sum += tempSeries[i];
        }
        return sum;
    }

    private int getLength() {
        return length;
    }

    private void checkIfNotEmpty() {
        if (getLength() == 0) {
            throw new IllegalArgumentException("No temperatures");
        }
    }

    public double average() {
        checkIfNotEmpty();
        return findSum() / getLength();
    }

    public double deviation() {
        // There no need to check if the array is empty, because we've already
        // done it in average()
        double mean = average();
        double sum = 0;
        for (int i = 0; i < length; i++) {
            double diff = tempSeries[i] - mean;
            sum += diff*diff;
        }
        return Math.sqrt(sum / getLength());
    }

    public double min() {
        checkIfNotEmpty();
        double minValue = tempSeries[0];
        for (int i = 0; i < length; i++) {
            if (tempSeries[i] < minValue) {
                minValue = tempSeries[i];
            }
        }
        return minValue;
    }

    public double max() {
        checkIfNotEmpty();
        double maxValue = tempSeries[0];
        for (int i = 0; i < length; i++) {
            if (tempSeries[i] > maxValue) {
                maxValue = tempSeries[i];
            }
        }
        return maxValue;
    }

    public double findTempClosestToZero() {
        return findTempClosestToValue(0.0);
    }

    public double findTempClosestToValue(double tempValue) {
        checkIfNotEmpty();
        double closestTemp = tempSeries[0];
        double closestDiff = Math.abs(tempValue - closestTemp);
        for (int i = 0; i < length; i++) {
            double diff = Math.abs(tempValue - tempSeries[i]);
            if (diff < closestDiff) {
                closestTemp = tempSeries[i];
                closestDiff = diff;
            } else if (Math.abs(diff - closestDiff) < DELTA
                    && tempSeries[i] > closestTemp) {
                closestTemp = tempSeries[i];
            }
        }
        return closestTemp;
    }

    private int findNumTempsLessThen(double tempValue) {
        int num = 0;
        for (int i = 0; i < length; i++) {
            if (tempSeries[i] < tempValue) {
                num++;
            }
        }
        return num;
    }

    private int findNumTempsGreaterThen(double tempValue) {
        return getLength() - findNumTempsLessThen(tempValue);
    }

    public double[] findTempsLessThen(double tempValue) {
        checkIfNotEmpty();
        double[] lesserTemps = new double[findNumTempsLessThen(tempValue)];
        int j = 0;
        for (int i = 0; i < length; i++) {
            if (tempSeries[i] < tempValue) {
                lesserTemps[j++] = tempSeries[i];
            }
        }
        return lesserTemps;
    }

    public double[] findTempsGreaterThen(double tempValue) {
        checkIfNotEmpty();
        double[] greaterTemps = new double[findNumTempsGreaterThen(tempValue)];
        int j = 0;
        for (int i = 0; i < length; i++) {
            if (tempSeries[i] >= tempValue) {
                greaterTemps[j++] = tempSeries[i];
            }
        }
        return greaterTemps;
    }

    public TempSummaryStatistics summaryStatistics() {
        // Again, there's no need to check if the array is empty because it
        // will be done in average()
        return new TempSummaryStatistics(this);
    }

    public double addTemps(double... temps) {
        checkIfValidTemperatures(temps);
        if (tempSeries.length - getLength() <= temps.length) {
            double[] newTemps = new double[2*(getLength() + temps.length)];
            System.arraycopy(tempSeries, 0, newTemps, 0,
                    getLength());
            tempSeries = newTemps;
        }
        System.arraycopy(temps, 0, tempSeries, getLength(), temps.length);
        length += temps.length;
        return findSum();
    }
}
