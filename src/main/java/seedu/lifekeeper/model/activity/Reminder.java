package seedu.lifekeeper.model.activity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import seedu.lifekeeper.commons.exceptions.IllegalValueException;
import seedu.lifekeeper.commons.util.DateUtil;

/**
 * Represents a Task's reminder in the Lifekeeper. Guarantees: immutable; is
 * valid as declared in {@link #isValidReminder(String)}
 */
// @@author A0131813R
public class Reminder extends DateTime {

    public static final String MESSAGE_REMINDER_CONSTRAINTS = "Task reminder can only be in date format";
    public static final String MESSAGE_REMINDER_INVALID = "reminder time has passed";
    public String RecurringMessage;

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
        String[] recur;
        if (date != "") {
            if (date.contains("every")) {
                this.recurring = true;
                RecurringMessage = date;
                recur = date.split(" ", 2);
                if (recur.length == 1)
                    throw new IllegalValueException(MESSAGE_REMINDER_CONSTRAINTS);
                date = recur[1];
            }
            setDate(date);
        }
    }

    public void setDate(String date) throws IllegalValueException {
        String[] recur = date.split(" ", 2);
        String recurfreq = recur[0];
        if (!isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_REMINDER_CONSTRAINTS);
        }
        if (recur.length != 1) {

            if (recurfreq.contains("day")) {
                date = "today " + recur[1];
            }

            if (!date.equals("")) {
                Date taskDate = DateUtil.convertFixedDate(date);
                if (!isValidDate(date)) {
                    throw new IllegalValueException(MESSAGE_REMINDER_CONSTRAINTS);
                }
                if (taskDate == null) {
                    assert false : "Date should not be null";
                } /*
                   * else if (DateUtil.hasPassed(taskDate)) { throw new
                   * IllegalValueException(MESSAGE_REMINDER_INVALID);
                   */

                this.value.setTime(taskDate);
                this.value.set(Calendar.MILLISECOND, 0);
                this.value.set(Calendar.SECOND, 0);
            }

            while (recurring && this.value.before(Calendar.getInstance())) {
                if (recurfreq.contains("year"))
                    this.value.add(Calendar.YEAR, 1);
                if (recurfreq.contains("month"))
                    this.value.add(Calendar.MONTH, 1);
                else if (recurfreq.contains("mon") || recurfreq.contains("tue") || recurfreq.contains("wed")
                        || recurfreq.contains("thu") || recurfreq.contains("fri") || recurfreq.contains("sat")
                        || recurfreq.contains("sun"))
                    this.value.add(Calendar.DAY_OF_WEEK, 7);
                if (recurfreq.contains("day"))
                    this.value.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
    }
    
    /**
     * Advances the reminder by a week (7 days) if it is recurring.
     */
    public void resetTime() {
        if (this.recurring) {
            this.value.add(Calendar.DAY_OF_MONTH, 7);
        }
    }

    public String forDisplay() {
        if (this.value == null) {
            return "Reminder:\t-";
        } else {
            if (!recurring) {
                return "Reminder:\t".concat(this.toString());
            }
            else {
                SimpleDateFormat sdfRecurr = new SimpleDateFormat("EEEE, h:mm aa");
                return "Reminder:\t".concat("Every " + sdfRecurr.format(this.value.getTime()));
            }
        }
    }

}