package seedu.address.model.activity.event;

import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Name;
import seedu.address.model.activity.ReadOnlyEvent;
import seedu.address.model.activity.task.Reminder;
import seedu.address.model.activity.task.Task;
import seedu.address.model.tag.UniqueTagList;

public class Event extends Task implements ReadOnlyEvent {

    private Reminder reminder;
    private StartTime starttime;
    private EndTime endtime;
    private boolean isCompleted;


    /**
     * Every field must be present and not null.
     */
    public Event(Name name, StartTime starttime, EndTime endtime, Reminder reminder, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, starttime, endtime, reminder, tags);
        this.name = name;
        this.starttime = starttime;
        this.endtime = endtime;
        this.reminder = reminder;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.isCompleted = false;
    }

    /**
     * Copy constructor.
     */
    public Event(ReadOnlyEvent source) {
        this(source.getName(), source.getStartTime(), source.getEndTime(), source.getReminder(), source.getTags());
    }

    @Override
    public Name getName() {
        return name;
    }
    
    public void setName(Name name) {
        this.name = name;
    }

    @Override
    public StartTime getStartTime() {
        return starttime;
    }
    
    public void setStartTime(StartTime starttime) {
        this.starttime = starttime;
    }
    
    @Override
    public EndTime getEndTime() {
        return endtime;
    }
    
    public void setEndTime(EndTime endtime) {
        this.endtime = endtime;
    }

    @Override
    public Reminder getReminder() {
        return reminder;
    }
    
    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
    }

    @Override
    public boolean getCompletionStatus() {
        return isCompleted;
    }
    
    public void setCompletionStatus(boolean isComplete) {
        this.isCompleted = isComplete;
    }
    
    @Override
    public String toStringCompletionStatus() {
        if(isCompleted) {
            return "Completed";
        } 
        
            return "";  
    }
    
    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this person's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyEvent // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyEvent) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, starttime, endtime, reminder, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
