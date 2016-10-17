package seedu.address.model.task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateUtil;
import seedu.address.commons.util.DateValidation;

/**
 * Represents a Task's reminder in the Lifekeeper. Guarantees: immutable; is
 * valid as declared in {@link #isValidReminder(String)}
 */
public class Reminder extends DateTime {

    public static final String MESSAGE_REMINDER_CONSTRAINTS = "Task reminder can only be in date format";
    public static final String MESSAGE_REMINDER_INVALID = "reminder time has passed";

    public Reminder(Calendar date) {
        super(date);
    }
    
    /**
     * Validates given reminder.
     *
     * @throws IllegalValueException
     *             if given reminder string is invalid.
     */
    public Reminder(String date) throws IllegalValueException {
        super(date);

        if (!isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_REMINDER_CONSTRAINTS);
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
                throw new IllegalValueException(MESSAGE_REMINDER_INVALID);
            }

            this.value.setTime(taskDate);
        }
    }
    
    /**
     * Outputs Reminder Date as a string, according to a specific format. Uses a standard format when format is not specified.
     * 
     * Prerequisite: Format is specified correctly. (Most likely will use a standard format for testing)
     */

    public String outputReminderDateAsString (String format) {
    	SimpleDateFormat formatter = new SimpleDateFormat();
    	return formatter.format(this.getCalendarValue().getTime());
    }
    
    public String outputReminderDateAsString () {
    	SimpleDateFormat formatter = new SimpleDateFormat("EEE, MM d, yyyy HH:mm");
    	return formatter.format(this.getCalendarValue().getTime());
    }
}