package seedu.address.model.activity.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateUtil;
import seedu.address.commons.util.DateValidation;
import seedu.address.model.activity.DateTime;

/**
 * Represents a Task's DueDate in the Lifekeeper. Guarantees: immutable; is
 * valid as declared in {@link #isValidDueDate(String)}
 */
public class DueDate extends DateTime {

    public static final String MESSAGE_DUEDATE_CONSTRAINTS = "Task's DueDate should only contain valid date";
    public static final String MESSAGE_DUEDATE_INVALID = "Deadline is over";

    public DueDate(Calendar date) {
        super(date);
    }
    
    /**
     * Validates given Due Date.
     *
     * @throws IllegalValueException
     *             if given due date string is invalid.
     */
    public DueDate(String date) throws IllegalValueException {
        super(date);
        if (!isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_DUEDATE_CONSTRAINTS);
        }
        
        if (date != "") {

            Date taskDate = DATE_PARSER.DueDateConvert(date);

            if (taskDate == null) {
                assert false : "Date should not be null";
            } else if (DateUtil.hasPassed(taskDate)) {
                throw new IllegalValueException(MESSAGE_DUEDATE_INVALID);
            }
            this.value.setTime(taskDate);
            this.value.set(Calendar.MILLISECOND, 0);
            this.value.set(Calendar.SECOND, 0);
        }
    }
    
    public String forDisplay() {
        if (this.value == null) {
            return "Due:\t\t\t-";
        } else {
            return "Due:\t\t\t".concat(this.toString());
        }
    }
}
