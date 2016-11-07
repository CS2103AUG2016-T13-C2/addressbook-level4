package seedu.lifekeeper.model.activity;

import seedu.lifekeeper.commons.util.CollectionUtil;
import seedu.lifekeeper.model.activity.event.Event;
import seedu.lifekeeper.model.activity.event.ReadOnlyEvent;
import seedu.lifekeeper.model.activity.task.ReadOnlyTask;
import seedu.lifekeeper.model.activity.task.Task;
import seedu.lifekeeper.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents an Activity in the Lifekeeper.
 * Guarantees: details are present and not null, field values are validated.
 */
//@@author A0131813R
public class Activity implements ReadOnlyActivity {

    protected Name name;
    protected Reminder reminder;
    protected boolean isCompleted;
    protected UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Activity(Name name, Reminder reminder, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, reminder, tags);
        this.name = name;
        this.reminder = reminder;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Activity(ReadOnlyActivity source) {
        this(source.getName(), source.getReminder(), source.getTags());
        this.isCompleted =  source.getCompletionStatus();
    }

    @Override
    public Name getName() {
        return name;
    }
    
    public void setName(Name name) {
        this.name = name;
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
    
    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }
    
    //@@author A0125680H
    public boolean hasReminderPassed() {
        return reminder.isBeforeNow();
    }

    /**
     * Replaces this activity's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
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
                    && this.isSameStateAs((ReadOnlyActivity) other));
        }
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, reminder, tags);
    }

    @Override
    public String toString() {
        return getAsText();
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
	public boolean hasPassedDueDate() {
		return false;
	}
    
    public static Activity create (ReadOnlyActivity act) {
		
    	String actType = act.getClass().getSimpleName().toLowerCase();
    	
    			switch (actType) {
                
    			case "activity":
                    return new Activity(act);
                case "task":
                	 return new Task((ReadOnlyTask) act);
                case "event":
                	return new Event((ReadOnlyEvent) act);
    }
				return null;
    	
    }

    public boolean getisOver() {
        return false;
    }

}
