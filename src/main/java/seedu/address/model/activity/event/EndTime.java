package seedu.address.model.activity.event;

import java.util.Calendar;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateUtil;
import seedu.address.commons.util.DateValidation;
import seedu.address.model.activity.DateTime;

public class EndTime extends DateTime {
    public static final String MESSAGE_DUEDATE_CONSTRAINTS = "Task's end time should only contain valid date";
    public static final String MESSAGE_DUEDATE_INVALID = "end time has passed";

    
    /**
     * Validates given Due Date.
     *
     * @throws IllegalValueException
     *             if given due date string is invalid.
     */
    public EndTime(String date) throws IllegalValueException {
        super(date);

        if (date != "") {
            if (date.contains("today")) { // allow user to key in "today"
                                          // instead of today's date
                
                date = DateValidation.DateTimeToday(date);
            } else if (date.contains("tomorrow")) { // allow user to key in
                                                    // "tomorrow" instead of
                                                    // tomorrow's/ date
                date = DateValidation.DateTimeTomorrow(date);
            }

            Date taskDate = DATE_PARSER.parseDate(date);

            if (taskDate == null) {
                assert false : "Date should not be null";
            } else if (DateUtil.hasPassed(taskDate)) {
                throw new IllegalValueException(MESSAGE_DUEDATE_INVALID);
            }
            if (!isValidDate(date)) {
                throw new IllegalValueException(MESSAGE_DUEDATE_CONSTRAINTS);
            }
            this.value.setTime(taskDate);
            this.value.set(Calendar.MILLISECOND, 0);
            this.value.set(Calendar.SECOND, 0);
        }
        if (!isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_DUEDATE_CONSTRAINTS);
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
