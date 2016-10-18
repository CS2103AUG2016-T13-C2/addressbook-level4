package seedu.address.model.activity.event;

import java.util.Calendar;

import seedu.address.model.activity.DateTime;

public class StartTime extends DateTime{
    
    public static final String MESSAGE_DUEDATE_CONSTRAINTS = "Task's DueDate should only contain valid date";
    public static final String MESSAGE_DUEDATE_INVALID = "reminder time has passed";

	public StartTime(Calendar date) {
		super(date);
	}
	
	

}
