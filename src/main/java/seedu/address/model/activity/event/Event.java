package seedu.address.model.activity.event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Name;
import seedu.address.model.activity.ReadOnlyActivity;
import seedu.address.model.activity.Reminder;
import seedu.address.model.tag.UniqueTagList;
//@@author A0131813R
public class Event extends Activity implements ReadOnlyEvent{

    private StartTime startTime;
    private EndTime endTime;
    
    public Event(Name name, StartTime start, EndTime end, Reminder reminder, UniqueTagList tags) {
        super(name, reminder, tags);
        
//        assert !CollectionUtil.isAnyNull(start, end);
        this.startTime = start;
        this.endTime = end;
    }
    
    /**
     * Copy constructor.
     */
    public Event(ReadOnlyEvent source) {
        this(source.getName(), source.getStartTime(), source.getEndTime(), source.getReminder(), source.getTags());
        this.isCompleted =  source.getCompletionStatus();
    }
    
    @Override
    public StartTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(StartTime starttime) {
        this.startTime= starttime;
    }
    
    @Override
    public EndTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(EndTime endtime) {
        this.endTime= endtime;
    }
    
    /**
     * Checks if this event is currently ongoing.
     * @return true if the current time is between the start and end time.
     */
    @Override
    public boolean isOngoing() {
        return startTime.isBeforeNow() && endTime.isAfterNow();
    }

    /**
     * Checks if this event is over.
     * @return true if the current time is after the end time.
     */
    @Override
    public boolean isOver() {
        return endTime.isBeforeNow();
    }
    
    @Override
    public boolean getisOver() {
        return isOver();
    }

    @Override
    public String toStringCompletionStatus() {
        if(this.isOver()) {
            return "Event\nOver";
        } else if (this.isOngoing()) {
            return "Event\nOngoing";
        } else {
            return "";
        }
    }
    
    @Override
    public String displayTiming() {
        if (isStartAndEndOnSameDate()) {
            SimpleDateFormat timeOnly = new SimpleDateFormat("h:mm aa");
            return "From " + startTime.toString() + " to " + timeOnly.format(endTime.getCalendarValue().getTime());
        } else {
            return "From " + startTime.toString() + " to " + endTime.toString();
        }
    }
    
    private boolean isStartAndEndOnSameDate() {
        return startTime.getCalendarValue().get(Calendar.YEAR) == endTime.getCalendarValue().get(Calendar.YEAR)
                && startTime.getCalendarValue().get(Calendar.DAY_OF_YEAR) == endTime.getCalendarValue().get(Calendar.DAY_OF_YEAR);
    }
    
    @Override
    public boolean equals(Object other) {
        if (this == null || other == null) {
            return !(this == null ^ other == null);
        } else if (this.getClass() != other.getClass()) {
            return false;
        } else {
            return other == this // short circuit if same object
                    || (other instanceof ReadOnlyActivity // instanceof handles nulls
                    && ((Event) other).getName().equals(this.getName()) // state checks here onwards
                    && ((Event) other).getStartTime().equals(this.getStartTime())
                    && ((Event) other).getEndTime().equals(this.getEndTime())
                    && ((Event) other).getReminder().equals(this.getReminder()));
        }
    }
    
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, startTime, endTime, reminder, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }
    
}
