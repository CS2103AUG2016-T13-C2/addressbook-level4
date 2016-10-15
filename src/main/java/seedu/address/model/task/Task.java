package seedu.address.model.task;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a Task in the Lifekeeper.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private TaskName name;
    private DueDate duedate;
    private Priority priority;
    private Reminder reminder;
    private DueTime duetime;
    private ReminderTime remindertime;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(TaskName name, DueDate dueDate, DueTime duetime, Priority priority, Reminder reminder, ReminderTime remindertime, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, dueDate, priority, reminder, tags);
        this.name = name;
        this.duedate = dueDate;
        this.duetime = duetime;
        this.priority = priority;
        this.reminder = reminder;
        this.remindertime = remindertime;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getDueDate(), source.getDueTime(), source.getPriority(), source.getReminder(), source.getReminderTime(), source.getTags());
    }

    @Override
    public TaskName getName() {
        return name;
    }

    @Override
    public DueDate getDueDate() {
        return duedate;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public Reminder getReminder() {
        return reminder;
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
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, duedate, duetime, priority, reminder, remindertime, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    @Override
    public DueTime getDueTime() {
        return duetime;
    }

    @Override
    public ReminderTime getReminderTime() {
        return remindertime;
    }

}
