/*
 *    Copyright 2017-2018 Frederic Thevenet
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

package eu.binjr.core.data.exceptions;

/**
 * Signals that an error happened while trying to initialize a DataAdapter.
 *
 * @author Frederic Thevenet
 */
public class CannotInitializeWorksheetException extends Exception {
    /**
     * Creates a new instance of the {@link CannotInitializeWorksheetException} class.
     */
    public CannotInitializeWorksheetException() {
        super();
    }

    /**
     * Creates a new instance of the {@link CannotInitializeWorksheetException} class with the provided message.
     *
     * @param message the message of the exception.
     */
    public CannotInitializeWorksheetException(String message) {
        super(message);
    }

    /**
     * Creates a new instance of the {@link CannotInitializeWorksheetException} class with the provided message and cause {@link Throwable}
     *
     * @param message the message of the exception.
     * @param cause   the cause for the exception.
     */
    public CannotInitializeWorksheetException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new instance of the {@link CannotInitializeWorksheetException} class with the provided cause {@link Throwable}
     *
     * @param cause the cause for the exception.
     */
    public CannotInitializeWorksheetException(Throwable cause) {
        super(cause);
    }
}
