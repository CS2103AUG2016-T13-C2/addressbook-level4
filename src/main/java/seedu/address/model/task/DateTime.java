package seedu.address.model.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateUtil;

public abstract class DateTime {
    public final Calendar value;
    protected static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("EEE, MMM d, yyyy h:mm a");
    protected static final DateUtil DATE_PARSER = new DateUtil();
    
    public DateTime(String date) throws IllegalValueException {
        assert date != null;
        this.value = Calendar.getInstance();
    }
    
    /**
     * Returns true if a given string is a valid task reminder.
     */
    protected static boolean isValidDate(String test) {
        if (DATE_PARSER.validate(test) || test == "")
            return true;
        else
            return false;
    }

    @Override
    public String toString() {
        if (this.value == null) {
            return "";
        } else {
            return DATE_FORMAT.format(value.getTime());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                        && this.value.equals(((DateTime) other).value)); // state
                                                                        // check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
