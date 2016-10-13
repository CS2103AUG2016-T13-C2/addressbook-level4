package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.*;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    public TaskBuilder withTaskName(String name) throws IllegalValueException {
        this.task.setName(new TaskName(name));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withReminder(String address) throws IllegalValueException {
        this.task.setAddress(new Reminder(address));
        return this;
    }

    public TaskBuilder withDueDate(String phone) throws IllegalValueException {
        this.task.setPhone(new DueDate(phone));
        return this;
    }

    public TaskBuilder withPriority(String email) throws IllegalValueException {
        this.task.setEmail(new Priority(email));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
