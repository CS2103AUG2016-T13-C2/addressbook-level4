package seedu.address.model.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateUtil;
import seedu.address.commons.util.DateValidation;

/**
 * Represents a Task's DueDate in the Lifekeeper. Guarantees: immutable; is
 * valid as declared in {@link #isValidDueDate(String)}
 */
public class DueDate extends DateTime {

    public static final String MESSAGE_DUEDATE_CONSTRAINTS = "Task's DueDate should only contain valid date";
    public static final String MESSAGE_DUEDATE_INVALID = "reminder time has passed";

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
            if (date.contains("today")) { // allow user to key in "today"
                                          // instead of today's date
                this.value.setTime(Calendar.getInstance().getTime());
            } else if (date.contains("tomorrow")) { // allow user to key in
                                                    // "tomorrow" instead of
                                                    // tomorrow's/ date
                this.value.setTime(Calendar.getInstance().getTime());
                value.add(Calendar.DAY_OF_MONTH, 1);
            }

            Date taskDate = DATE_PARSER.parseDate(date);

            if (taskDate == null) {
                assert false : "Date should not be null";
            } else if (DateUtil.hasPassed(taskDate)) {
                throw new IllegalValueException(MESSAGE_DUEDATE_INVALID);
            }

            this.value.setTime(taskDate);
        }
    }
    
    /**
     * Outputs Due Date as a string, according to a specific format. Uses a default format Day, Month DD, YYYY hh:mm when format is not specified.
     * 
     * Prerequisite: Format is specified correctly. (Most likely will use a standard format for testing)
     */
    
    public String outputDueDateAsString (String format) {
    	SimpleDateFormat formatter = new SimpleDateFormat(format);
    	return formatter.format(this.getCalendarValue().getTime());
    }
    
    public String outputDueDateAsString () {
    	SimpleDateFormat formatter = new SimpleDateFormat("EEE, MM d, yyyy HH:mm");
    	return formatter.format(this.getCalendarValue().getTime());
    }
}
