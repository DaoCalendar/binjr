/*
 *    Copyright 2017 Frederic Thevenet
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package eu.fthevenet.binjr.data.timeseries;

import javafx.scene.chart.XYChart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.OptionalDouble;

/**
 * This class provides a full implementation of a {@link TimeSeriesProcessor} of {@link Double} values.
 *
 * @author Frederic Thevenet
 */
public class DoubleTimeSeriesProcessor extends TimeSeriesProcessor<Double> {
    private static final Logger logger = LogManager.getLogger(DoubleTimeSeriesProcessor.class);

    /**
     * Initializes a new instance of the {@link DoubleTimeSeriesProcessor} class with the provided binding.
     *
     */
    public DoubleTimeSeriesProcessor() {
        super();
    }

    @Override
    public Double getMinValue() {
        OptionalDouble res = this.data.stream().mapToDouble(XYChart.Data::getYValue).min();
        return res.isPresent() ? res.getAsDouble() : Double.NaN;
    }

    @Override
    public Double getAverageValue() {
        OptionalDouble res = this.data.stream().mapToDouble(XYChart.Data::getYValue).average();
        return res.isPresent() ? res.getAsDouble() : Double.NaN;
    }

    @Override
    public Double getMaxValue() {
        OptionalDouble res = this.data.stream().mapToDouble(XYChart.Data::getYValue).max();
        return res.isPresent() ? res.getAsDouble() : Double.NaN;
    }
}
