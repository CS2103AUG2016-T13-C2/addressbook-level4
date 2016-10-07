package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateValidation;

/**
 * Represents a Task's DueDate in the Lifekeeper.
 * Guarantees: immutable; is valid as declared in {@link #isValidDueDate(String)}
 */
public class DueDate {

    public static final String MESSAGE_DUEDATE_CONSTRAINTS = "Task's DueDate should only contain valid date";

    public final String value;

    /**
     * Validates given Due Date.
     *
     * @throws IllegalValueException if given due date string is invalid.
     */
    public DueDate(String date) throws IllegalValueException {
        assert date != null;
        date = date.trim();
        if (!isValidDueDate(date)) {
            throw new IllegalValueException(MESSAGE_DUEDATE_CONSTRAINTS);
        }
        this.value = date;
    }

    /**
     * Returns true if a given string is a valid person phone number.
     */
    public static boolean isValidDueDate(String test) {
        if((DateValidation.validate(test)) || (test== "today") || (test == "tomorrow"))
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
                && this.value.equals(((DueDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
