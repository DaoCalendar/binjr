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

package eu.binjr.core.data.adapters;

import eu.binjr.core.data.timeseries.LogEvent;
import eu.binjr.core.data.workspace.LogWorksheet;
import eu.binjr.core.data.workspace.Worksheet;
import javafx.scene.paint.Color;

public class LogFilesBinding extends SourceBinding<LogEvent> {

    private transient boolean indexed = false;

    public LogFilesBinding() {
        super();
    }

    public LogFilesBinding(String label, String legend, String path, String treeHierarchy, DataAdapter<LogEvent> adapter) {
        super(label, legend, null, path, treeHierarchy, adapter);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public Class<? extends Worksheet<LogEvent>> getWorksheetClass() {
        return LogWorksheet.class;
    }

    public boolean isIndexed() {
        return indexed;
    }

    public void setIndexed(boolean indexed) {
        this.indexed = indexed;
    }

    public static class Builder extends SourceBinding.Builder<LogEvent, LogFilesBinding, Builder> {

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        protected LogFilesBinding construct(String label, String legend, Color color, String path, String treeHierarchy, DataAdapter<LogEvent> adapter) {
            return new LogFilesBinding(label, legend, path, treeHierarchy, adapter);
        }
    }

}
