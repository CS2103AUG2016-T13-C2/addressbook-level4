package seedu.address.model.activity.task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateUtil;
import seedu.address.commons.util.DateValidation;
import seedu.address.model.activity.DateTime;

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


        if (date != "") {
            if (date.contains("today")) { // allow user to key in "today"
                                          // instead of today's date
                date = DateValidation.ReminderTimeToday(date);
            } else if (date.contains("tomorrow")) { // allow user to key in
                                                    // "tomorrow" instead of
                                                    // tomorrow's/ date
                date = DateValidation.DateTimeTomorrow(date);
            }

            Date taskDate = DATE_PARSER.parseDate(date);

            if (taskDate == null) {
                assert false : "Date should not be null";
            } else if (DateUtil.hasPassed(taskDate)) {
                throw new IllegalValueException(MESSAGE_REMINDER_INVALID);
            }
            System.out.println(date);
            System.out.println(taskDate);
            if (!isValidDate(date)) {
                throw new IllegalValueException(MESSAGE_REMINDER_CONSTRAINTS);
            }
            this.value.setTime(taskDate);
            this.value.set(Calendar.MILLISECOND, 0);
            this.value.set(Calendar.SECOND, 0);
        }


    }
    
    public String forDisplay() {
        if (this.value == null) {
            return "Reminder:\t-";
        } else {
            return "Reminder:\t".concat(this.toString());
        }
    }
}