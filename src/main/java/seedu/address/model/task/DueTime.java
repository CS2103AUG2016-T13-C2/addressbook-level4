package seedu.address.model.task;


import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.TimeValidation;

public class DueTime {
    public static final String MESSAGE_DUEDATE_CONSTRAINTS = "Task's DueDate should only contain valid date";
    public static final String MESSAGE_DUEDATE_INVALID = "due time has passed";
    public final String value;

    /**
     * Validates given Due Date.
     *
     * @throws IllegalValueException
     *             if given due date string is invalid.
     */
    public DueTime(String time) throws IllegalValueException {
        assert time != null;
        if (!isValidDueDate(time)) {
            throw new IllegalValueException(MESSAGE_DUEDATE_CONSTRAINTS);
        }
        if (time =="")
            time = TimeValidation.TimeFormatToday();
        this.value = time;
    }

    /**
     * Returns true if a given string is a valid task reminder.
     */
    public static boolean isValidDueDate(String test) {
        if ((TimeValidation.validate(test)) || (test == ""))
            return true;
        else
            return false;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DueDate // instanceof handles nulls
                        && this.value.equals(((DueDate) other).value)); // state
                                                                        // check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
