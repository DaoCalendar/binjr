package eu.fthevenet.binjr.commons.controls;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * A date and time picker control that works with {@link java.time.ZonedDateTime}
 *
 * @author Frederic Thevenet
 */
public class ZonedDateTimePicker extends DatePicker {
    private static final Logger logger = LogManager.getLogger(ZonedDateTimePicker.class);
    private final DateTimeFormatter formatter;
    private ObjectProperty<ZonedDateTime> dateTimeValue = new SimpleObjectProperty<>(ZonedDateTime.now());

    /**
     * Initializes a new instance of the {@link ZonedDateTimePicker} class with the system's default timezone
     */
    public ZonedDateTimePicker() {
        this(ZoneId.systemDefault());
    }

    /**
     * Initializes a new instance of the {@link ZonedDateTimePicker} class with the provided timezone
     *
     * @param currentZoneId the timezone id to use in the control
     */
    public ZonedDateTimePicker(ZoneId currentZoneId) {
        getStyleClass().add("datetime-picker");
        this.formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.MEDIUM).withZone(currentZoneId);
        setConverter(new StringConverter<LocalDate>() {
            public String toString(LocalDate object) {
                ZonedDateTime value = getDateTimeValue();
                return (value != null) ? value.format(formatter) : "";
            }

            public LocalDate fromString(String stringValue) {

                if (stringValue == null || stringValue.isEmpty()) {
                    dateTimeValue.set(null);
                    return null;
                }
                try {
                    dateTimeValue.set(ZonedDateTime.parse(stringValue, formatter));
                } catch (Exception ex) {
                    logger.debug("Error parsing date", ex);
                    throw ex;
                }
                return dateTimeValue.get().toLocalDate();
            }

        });

        valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (dateTimeValue.get() == null) {
                    dateTimeValue.set(ZonedDateTime.of(newValue, LocalTime.now(), currentZoneId));
                }
                else {
                    LocalTime time = dateTimeValue.get().toLocalTime();
                    dateTimeValue.set(ZonedDateTime.of(newValue, time, currentZoneId));
                }
            }
        });

        dateTimeValue.addListener((observable, oldValue, newValue) -> {
            setValue(null);
            setValue(newValue == null ? null : newValue.toLocalDate());

        });

        getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                getEditor().commitValue();
            }
        });
    }

    //region [Properties]

    /**
     * Gets the selected date/time value of the control.
     *
     * @return the selected date/time value of the control.
     */
    public ZonedDateTime getDateTimeValue() {
        return dateTimeValue.get();
    }

    /**
     * Sets the selected date/time value of the control.
     *
     * @param dateTimeValue the selected date/time value of the control.
     */
    public void setDateTimeValue(ZonedDateTime dateTimeValue) {
        this.dateTimeValue.set(dateTimeValue);
    }

    /**
     * Returns the value property for the selected date/time
     *
     * @return the value property for the selected date/time
     */
    public ObjectProperty<ZonedDateTime> dateTimeValueProperty() {
        return dateTimeValue;
    }
//endregion
}