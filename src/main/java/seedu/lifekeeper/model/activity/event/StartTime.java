package seedu.lifekeeper.model.activity.event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import seedu.lifekeeper.commons.exceptions.IllegalValueException;
import seedu.lifekeeper.commons.util.DateUtil;
import seedu.lifekeeper.model.activity.DateTime;

//@@author A0131813R
public class StartTime extends DateTime {

    public static final String MESSAGE_STARTTIME_CONSTRAINTS = "Event's start time should only contain valid date";
    public static final String MESSAGE_STARTTIME_INVALID = "Event has already started";
    public String RecurringMessage;

    public StartTime(Calendar date) {
        super(date);
    }

    /**
     * Validates given Start Time.
     *
     * @throws IllegalValueException
     *             if given Start time string is invalid.
     */
    public StartTime(String date) throws IllegalValueException {
        super(date);
        String[] recur;
        if (date != "") {
            if (date.contains("every")) {
                this.recurring = true;
                RecurringMessage = date;
                recur = date.split(" ", 2);
                if (recur.length == 1)
                    throw new IllegalValueException(MESSAGE_STARTTIME_CONSTRAINTS);
                date = recur[1];
            }
            this.value= DateUtil.setDate(date);
            if(recurring && this.value.before(Calendar.getInstance()))
                this.value.add(Calendar.DAY_OF_MONTH, 7);
        }

    }

    public void setDate(String date) throws IllegalValueException {
        String[] recur = date.split(" ", 2);
        String recurfreq = recur[0];
        if (recur.length == 1)
            throw new IllegalValueException(MESSAGE_STARTTIME_CONSTRAINTS);
        if (recurfreq.equals("day")) {
            date = "today " + recur[1];
        }
        if (!date.equals("")) {
            Date taskDate = DateUtil.convertDate(date);
            if (!DateUtil.isValidDate(date)) {
                throw new IllegalValueException(MESSAGE_STARTTIME_CONSTRAINTS);
            }
            if (taskDate == null) {
                assert false : "Date should not be null";
            } /*
               * else if (DateUtil.hasPassed(taskDate)) { throw new
               * IllegalValueException(MESSAGE_STARTTIME_INVALID); }
               */

            this.value.setTime(taskDate);
            this.value.set(Calendar.MILLISECOND, 0);
            this.value.set(Calendar.SECOND, 0);
        }
    }
    
    public StartTime(Calendar date, boolean isRecurring, String recurringMessage) {
        super(date);
        this.recurring = isRecurring;
        this.RecurringMessage = recurringMessage;
    }

    public String forDisplay() {
        if (this.value == null) {
            return "Start:\t\t-";
        } else {
            if(!recurring) {
                return "Start:\t\t".concat(this.toString());
            }
            else {
                SimpleDateFormat sdfRecurr = new SimpleDateFormat("EEEE, h:mm aa");
                return "Every ".concat(sdfRecurr.format(this.value.getTime()));
            }
        }
    }
}
