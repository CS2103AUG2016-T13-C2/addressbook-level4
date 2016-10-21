package seedu.address.testutil;

import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.activity.event.Event;
import seedu.address.model.activity.event.ReadOnlyEvent;
import seedu.address.model.activity.task.ReadOnlyTask;
import seedu.address.model.activity.task.Task;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Name;
import seedu.address.model.activity.ReadOnlyActivity;
import seedu.address.model.activity.Reminder;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UniqueTagList.DuplicateTagException;

/**
 * A mutable Activity object. For testing only.
 */
public class TestActivity implements ReadOnlyActivity {

    protected Name name;
    protected Reminder reminder;
    protected boolean isCompleted;
    protected UniqueTagList tags;
    
    /**
     * Every field must be present and not null.
     */
/*    public TestActivity(Name name, Reminder reminder, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, reminder, tags);
        this.name = name;
        this.reminder = reminder;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
*/
    public TestActivity() {
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

    public void setReminder(Reminder address) {
        this.reminder = address;
    }
    
	@Override
	public boolean getCompletionStatus() {
		return isCompleted;
	}

    @Override
    public UniqueTagList getTags() {
        return tags;
    }
    
	@Override
	public String toStringCompletionStatus() {
        if(isCompleted) {
            return "Completed";
        } 
            return ""; 
	}

	@Override
	public boolean passedDueDate() {
		return false;
	}   
    
    //TestActivity specific commands
    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("add " + this.getName().fullName + " ");
        
        if (getReminder().value != null && !getReminder().value.equals("")) {
        sb.append("a/" + this.getReminder().value + " ");
        }
        
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }


}
