package ua.edu.ucu.tempseries;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
//import org.junit.Ignore;

import java.util.InputMismatchException;

public class TemperatureSeriesAnalysisTest {
    final static double delta = 0.00001;
    private double[] oneElementArray;
    private double[] emptyArray;
    private double[] temperatureSeries;
    private double[] invalidArray;
    private double[] mirrorArray;
    private double[] temperatureSeries2;

    @Before
    public void init() {
        oneElementArray = new double[] {-1.0};
        emptyArray = new double[] {};
        temperatureSeries = new double[] {3.0, -5.0, 1.0, 5.0};
        invalidArray = new double[] {3.0, -5.0, 1.0, 5.0, -300.0};
        mirrorArray = new double[] {3.0, -3.0, -5.0, 1.0, -1.0, 5.0};
        temperatureSeries2 = new double[] {3.0, 4.0, 7.0, 12.0};
    }

    @Test
    public void testAverageWithOneElementArray() {
        // setup input data and expected result
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(oneElementArray);
        double expResult = -1.0;

        // call tested method
        double actualResult = seriesAnalysis.average();

        // compare expected result with actual result
        assertEquals(expResult, actualResult, 0.00001);
    }

//    @Ignore
    @Test(expected = IllegalArgumentException.class)
    public void testAverageWithEmptyArray() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(emptyArray);

        // expect exception here
        seriesAnalysis.average();
    }

//    @Ignore
    @Test
    public void testAverage() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = 1.0;

        double actualResult = seriesAnalysis.average();
        
        assertEquals(expResult, actualResult, 0.00001);        
    }

    @Test(expected = InputMismatchException.class)
    public void testInitWithInvalidTemps() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(invalidArray);
    }

    @Test
    public void testAverageWithMutation() {
        TemperatureSeriesAnalysis seriesAnalisys = new TemperatureSeriesAnalysis(temperatureSeries);
        temperatureSeries[0] = 10;

        double expResult = 1.0;

        double actualResult = seriesAnalisys.average();

        assertEquals(expResult, actualResult, delta);
    }

    @Test
    public void testDeviation() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = 3.7416573867739;

        double actualResult = seriesAnalysis.deviation();

        assertEquals(expResult, actualResult, delta);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeviationWithEmptyArray() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis();

        seriesAnalysis.deviation();
    }

    @Test
    public void testMin() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = -5.0;

        double actualResult = seriesAnalysis.min();

        assertEquals(expResult, actualResult, delta);
    }

    @Test
    public void testMax() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = 5.0;

        double actualResult = seriesAnalysis.max();

        assertEquals(expResult, actualResult, delta);
    }

    @Test
    public void testFindTempClosestToZero() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = 1.0;

        double actualResult = seriesAnalysis.findTempClosestToZero();

        assertEquals(expResult, actualResult, delta);
    }

    @Test
    public void testFindTempClosestPosToZero() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(mirrorArray);
        double expResult = 1.0;

        double actualResult = seriesAnalysis.findTempClosestToZero();

        assertEquals(expResult, actualResult, delta);
    }

    @Test
    public void testFindTempClosestToValue() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = 5.0;

        double actualResult = seriesAnalysis.findTempClosestToValue(4);

        assertEquals(expResult, actualResult, delta);
    }

    @Test
    public void testFindTempsLessThen() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double[] expResult = {3.0, -5.0, 1.0};

        double[] actualResult = seriesAnalysis.findTempsLessThen(4);

        assertArrayEquals(actualResult, expResult, delta);
    }

    @Test
    public void testFindTempsGreaterThen() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double[] expResult = {5.0};

        double[] actualResult = seriesAnalysis.findTempsGreaterThen(4);

        assertArrayEquals(actualResult, expResult, delta);
    }

    @Test
    public void testSummaryStatistics() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        String expResult = "TempSummaryStatistics(avgTemp=1.0, devTemp=3.7416573867739413, minTemp=-5.0, maxTemp=5.0)";

        String actualResult = seriesAnalysis.summaryStatistics().toString();

        assertEquals(expResult, actualResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSummaryStatisticsWithEmptyArray() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(emptyArray);
        seriesAnalysis.summaryStatistics();
    }

    @Test
    public void testAddTemps() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = 3.75;
        double expSum = 30.0;

        double actualSum = seriesAnalysis.addTemps(temperatureSeries2);
        double actualResult = seriesAnalysis.average();
        assertEquals(expResult, actualResult, delta);
        assertEquals(expSum, actualSum, delta);
    }

    @Test
    public void testAddSameTemps() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = 1;
        double expSum = 8.0;

        double actualSum = seriesAnalysis.addTemps(temperatureSeries);
        double actualResult = seriesAnalysis.average();
        assertEquals(expResult, actualResult, delta);
        assertEquals(expSum, actualSum, delta);
    }

    @Test(expected = InputMismatchException.class)
    public void testAddInvalidTemps() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        seriesAnalysis.addTemps(invalidArray);
    }
}
