package seedu.address.model.task;

import java.text.ParseException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateValidation;

/**
 * Represents a Task's DueDate in the Lifekeeper. Guarantees: immutable; is
 * valid as declared in {@link #isValidDueDate(String)}
 */
public class DueDate {

    public static final String MESSAGE_DUEDATE_CONSTRAINTS = "Task's DueDate should only contain valid date";
    public static final String MESSAGE_DUEDATE_INVALID = "reminder time has passed";
    public final String value;

    /**
     * Validates given Due Date.
     *
     * @throws IllegalValueException
     *             if given due date string is invalid.
     */
    public DueDate(String date) throws IllegalValueException {
        assert date != null;
        String time;
        String[] parts;
        if(date!=""){
        try {
            if (date.contains("today")) {
                parts = date.split(" ");
                time = parts[1];
                date = DateValidation.TodayDate();
                date = date + " " + time;
            } // allow user to key in today instead of today's date
            else if (date.contains("tomorrow")) {
                parts = date.split(" ");
                time = parts[1];
                date = DateValidation.TodayDate();
                date = date + " " + time;
                date = DateValidation.TomorrowDate();
            } // allow user to key in "tomorrow" instead of tomorrow's date
            if (!isValidDueDate(date)) {
                throw new IllegalValueException(MESSAGE_DUEDATE_CONSTRAINTS);
            }
            if (!DateValidation.aftertoday(date)) // check if the time is future
                                                  // time
                throw new IllegalValueException(MESSAGE_DUEDATE_INVALID);
        } catch (ParseException pe) {
            throw new IllegalValueException(MESSAGE_DUEDATE_INVALID);
        }}

        this.value = date;
    }

    /**
     * Returns true if a given string is a valid task reminder.
     */
    public static boolean isValidDueDate(String test) {
        if ((DateValidation.validate(test))|| (test ==""))
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