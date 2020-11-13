package ua.edu.ucu.tempseries;

import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {
    static final double MIN_TEMP = -273.15;  // The minimal possible temperature
    private double[] tempSeries;
    private int length;

    public static void main(String[] args) {
        TemperatureSeriesAnalysis a = new TemperatureSeriesAnalysis(new double[] {1, 1, 1, 1});
        System.out.println(a.summaryStatistics());
    }

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
        for (double temp : temperatureSeries) {
            if (temp < MIN_TEMP) {
                throw new InputMismatchException("Temperature can't be less " +
                        "than -273.15");
            }
        }
    }

    private double findSum() {
        double sum = 0;
        for (double temp : tempSeries) {
            sum += temp;
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
        checkIfNotEmpty();
        double mean = average();
        double sum = 0;
        for (double temp : tempSeries) {
            double diff = temp - mean;
            sum += diff*diff;
        }
        return Math.sqrt(sum / getLength());
    }

    public double min() {
        checkIfNotEmpty();
        double minValue = tempSeries[0];
        for (double temp : tempSeries) {
            if (temp < minValue) {
                minValue = temp;
            }
        }
        return minValue;
    }

    public double max() {
        checkIfNotEmpty();
        double maxValue = tempSeries[0];
        for (double temp : tempSeries) {
            if (temp > maxValue) {
                maxValue = temp;
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
        double closestDiff = Math.abs(closestTemp - tempValue);
        for (double temp : tempSeries) {
            double diff = Math.abs(tempValue - temp);
            if (diff < closestDiff) {
                closestTemp = temp;
                closestDiff = diff;
            } else if (diff == closestDiff && temp > closestTemp) {
                closestTemp = temp;
            }
        }
        return closestTemp;
    }

    private int findNumTempsLessThen(double tempValue) {
        int num = 0;
        for (double temp : tempSeries) {
            if (temp < tempValue) {
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
        int i = 0;
        for (double temp : tempSeries) {
            if (temp < tempValue) {
                lesserTemps[i++] = temp;
            }
        }
        return lesserTemps;
    }

    public double[] findTempsGreaterThen(double tempValue) {
        checkIfNotEmpty();
        double[] greaterTemps = new double[findNumTempsGreaterThen(tempValue)];
        int i = 0;
        for (double temp : tempSeries) {
            if (temp >= tempValue) {
                greaterTemps[i++] = temp;
            }
        }
        return greaterTemps;
    }

    public TempSummaryStatistics summaryStatistics() {
        checkIfNotEmpty();
        return new TempSummaryStatistics(this);
    }

    public int addTemps(double... temps) {
        checkIfValidTemperatures(tempSeries);
        if (tempSeries.length - getLength() <= temps.length) {
            double[] newTemps = new double[2*(getLength() + temps.length)];
            System.arraycopy(tempSeries, 0, newTemps, 0,
                    getLength());
            tempSeries = newTemps;
        }
        System.arraycopy(temps, 0, tempSeries, getLength(), temps.length);
        length += temps.length;
        return 0;
    }
}
