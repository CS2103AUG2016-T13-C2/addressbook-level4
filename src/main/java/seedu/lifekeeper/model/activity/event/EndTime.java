package seedu.lifekeeper.model.activity.event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import seedu.lifekeeper.commons.exceptions.IllegalValueException;
import seedu.lifekeeper.commons.util.DateUtil;
import seedu.lifekeeper.model.activity.DateTime;

//@@author A0131813R
public class EndTime extends DateTime {

    public static final String MESSAGE_ENDTIME_CONSTRAINTS = "Event's end time should only contain valid date";
    public static final String MESSAGE_ENDTIME_INVALID = "Event has already ended";
    public static final String MESSAGE_ENDTIME_NOTVALID = "Event end time is before start time";
    public String RecurringMessage;

    public EndTime(Calendar date) {
        super(date);
    }

    /**
     * Validates given Start Time 
     *
     * @throws IllegalValueException
     *             if given Start time string is invalid.
     */
    public EndTime(StartTime starttime, String date) throws IllegalValueException {
        super(Calendar.getInstance());
        SimpleDateFormat start = new SimpleDateFormat("d-MM-yyyy HH:mm");
        String startstring = start.format(starttime.value.getTime());
        Date startdate;
        try {
            startdate = start.parse(startstring);
        } catch (ParseException e) {
            throw new IllegalValueException("Start Time Invalid");
        }
        formEndTimeDateString(starttime, date, startdate, startstring);
    }
    
    
    /**
     * Validates given Start Time and 
     *
     * @throws IllegalValueException
     *             if given Start time string is invalid.
     */
    private void formEndTimeDateString(StartTime starttime, String date, Date startdate, String startstring) throws IllegalValueException{
        if (starttime.recurring) {
            if (date.equals("")) {
                checkRecurring(starttime, date, startdate);
            } else {
                recurringEndTime(starttime, startdate, date);
            }
        } else if (date.equals("")) {
            this.value = DateUtil.EndDateTime(startdate);
        } else if (date.split(" ").length == 1) {
            String[] startt = startstring.split(" ");
            date = startt[0] + " " + date;
            this.value = DateUtil.setDate(date);
        } else {
            if (!DateUtil.isValidDate(date)) {
                throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
            }
            this.value = DateUtil.setDate(date);
        }
        checkForBeforeStartTime(starttime, date);
    }

    private void checkForBeforeStartTime(StartTime starttime, String date) throws IllegalValueException {
        if (this.value.before(starttime.value)) {
            while (this.value.before(starttime.value)) {
                if ((date.contains("mon") || date.contains("tue") || date.contains("wed") || date.contains("thu")
                        || date.contains("fri") || date.contains("sat") || date.contains("sun")))
                    this.value.add(Calendar.DAY_OF_WEEK, 7);
                else if ((date.contains("day")) && recurring)
                    this.value.add(Calendar.DAY_OF_MONTH, 1);
                throw new IllegalValueException(MESSAGE_ENDTIME_NOTVALID);
            }
        }
    }

    public EndTime(Calendar date, boolean isRecurring, String recurringMessage) {
        super(date);
        this.recurring = isRecurring;
        this.RecurringMessage = recurringMessage;
    }

    /**
     * HandleRecurringTask if the endtime is not entered, set to default
     *
     * @throws IllegalValueException
     *             if given Start time string is invalid.
     */
    private void checkRecurring(StartTime starttime, String date, Date startdate) throws IllegalValueException {
        recurring = true;
        String recu[] = starttime.RecurringMessage.split(" ");
        Calendar startcal = starttime.value;
        startcal.add(Calendar.HOUR_OF_DAY, 1);
        SimpleDateFormat format1 = new SimpleDateFormat("EEE HHmm");
        RecurringMessage = recu[0] + " " + format1.format(startcal.getTime());
        this.value = DateUtil.EndDateTime(startdate);
    }

    /**
     * HandleRecurringTask if the endtime is enter, handle different variations
     * of endtime
     *
     * @throws IllegalValueException
     *             if given Start time string is invalid.
     */
    private void recurringEndTime(StartTime starttime, Date startdate, String date) throws IllegalValueException {
        this.recurring = true;
        String[] recur;
        recur = date.split(" ");
        String[] recurstart;
        recurstart = starttime.RecurringMessage.split(" ");
        String[] recurendtime;
        if (recur.length == 1) {
            RecurringMessage = recurstart[0] + " " + recurstart[1] + " " + recur[0];
            date = recurstart[1] + " " + recur[0];
        } else if (date.contains("every")) {
            RecurringMessage = date;
            recurendtime = date.split(" ", 2);
            if (recurendtime.length == 1)
                throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
            date = recurendtime[1];
        } else if (recur.length == 2) {
            RecurringMessage = "every " + date;
        } else
            throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
        if (!DateUtil.isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
        }
        this.value = DateUtil.setDate(date);

        Calendar cal = Calendar.getInstance();
        cal.setTime(startdate);
    }

    public EndTime(String date) throws IllegalValueException {
        super(date);
        String[] recur;
        if (date != "") {
            if (date.contains("every")) {
                this.recurring = true;
                RecurringMessage = date;
                recur = date.split(" ", 2);
                if (recur.length == 1)
                    throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
                date = recur[1];
            }
            if (!DateUtil.isValidDate(date)) {
                throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
            }
            this.value = DateUtil.setDate(date);
        }
    }
}
