package seedu.address.testutil;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private TaskName taskName;
    private Reminder reminder;
    private Priority priority;
    private DueDate dueDate;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setTaskName(TaskName name) {
        this.taskName = name;
    }

    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setDueDate(DueDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public TaskName getName() {
        return taskName;
    }

    @Override
    public DueDate getDueDate() {
        return dueDate;
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
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        sb.append("d/" + this.getDueDate().value + " ");
        sb.append("p/" + this.getPriority().value + " ");
        sb.append("r/" + this.getReminder().value + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
