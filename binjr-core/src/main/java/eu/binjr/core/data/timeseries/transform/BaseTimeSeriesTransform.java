/*
 *    Copyright 2016-2020 Frederic Thevenet
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
 */

package eu.binjr.core.data.timeseries.transform;

import eu.binjr.common.logging.Logger;
import eu.binjr.common.logging.Profiler;
import javafx.scene.chart.XYChart;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * The base class for time series transformation functions.
 *
 * @author Frederic Thevenet
 */
public abstract class BaseTimeSeriesTransform<T> implements TimeSeriesTransform<T> {
    private static final Logger logger = Logger.create(BaseTimeSeriesTransform.class);
    private final String name;
    private volatile boolean enabled = true;

    /**
     * Base constructor for {@link BaseTimeSeriesTransform} instances.
     *
     * @param name the name of the transform function
     */
    public BaseTimeSeriesTransform(String name) {
        this.name = name;
    }

    /**
     * The actual transform implementation
     *
     * @param data The data on which the transform should be applied.
     * @return the actual transform implementation
     */

    protected abstract List<XYChart.Data<ZonedDateTime, T>> apply(List<XYChart.Data<ZonedDateTime, T>> data);

    @Override
    public List<XYChart.Data<ZonedDateTime, T>> transform(List<XYChart.Data<ZonedDateTime, T>> data) {
        if (isEnabled()) {
            try (Profiler ignored = Profiler.start("Applying transform " + getName(), logger::perf)) {
                return apply(data);
            }
        } else {
            logger.debug(() -> "Transform " + getName() + " is disabled.");
        }
        return data;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public TimeSeriesTransform<T> getNextPassTransform() {
        return new NoOpTransform<T>();
    }
}
