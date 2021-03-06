/*
 *    Copyright 2020 Frederic Thevenet
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

package eu.binjr.core.data.timeseries;

import java.util.HashMap;
import java.util.Map;

public class LogEvent {
    private final String message;
    private final Map<String, FacetEntry> facets = new HashMap<>();

    public LogEvent(String message, FacetEntry... categories) {
        this.message = message;
        if (categories != null) {
            for (var category : categories) {
                this.facets.put(category.getFacetName(), category);
            }
        }
    }

    public String getMessage() {
        return message;
    }

    public Map<String, FacetEntry> getFacets() {
        return facets;
    }
}
