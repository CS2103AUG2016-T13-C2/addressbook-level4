package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UniqueTagList.DuplicateTagException;

public class TaskManager {
    private static final String NULL_ENTRY = "";
    
    public TaskManager() {
    }

    public static Task mapUnaffectedParams(ReadOnlyTask oldTask, Task newParams, String type) {
        Task newTask = null;
        try {
            newTask = new Task(
                    updateTaskName(oldTask, newParams, type),
                    updateDueDate(oldTask, newParams, type),
                    updateDueTime(oldTask, newParams, type),
                    updatePriority(oldTask, newParams, type),
                    updateReminder(oldTask, newParams, type),
                    updateReminderTime(oldTask, newParams, type),
                    updateTags(oldTask, newParams)
                    );
        } catch (IllegalValueException ive) {
            assert false : "There should not be any illegal value at this point";
        }
        
        return newTask;
    }

    private static TaskName updateTaskName(ReadOnlyTask oldTask, Task newParams, String type) throws IllegalValueException {
        TaskName newTaskName;

        if (newParams.getName().toString().equals(NULL_ENTRY)&& type == "edit") {
            newTaskName = new TaskName(oldTask.getName().toString());
        } else {
            newTaskName = new TaskName(newParams.getName().toString());
        }

        return newTaskName;
    }

    private static DueDate updateDueDate(ReadOnlyTask oldTask, Task newParams, String type) throws IllegalValueException {
        DueDate newDueDate;

        if (newParams.getDueDate().toString().equals(NULL_ENTRY)&& type == "edit") {
            newDueDate = new DueDate(oldTask.getDueDate().toString());
        } else {
            newDueDate = new DueDate(newParams.getDueDate().toString());
        }

        return newDueDate;
    }
    
    private static DueTime updateDueTime(ReadOnlyTask oldTask, Task newParams, String type) throws IllegalValueException {
        DueTime newDueTime;

        if (newParams.getDueTime().toString().equals(NULL_ENTRY)&& type == "edit") {
            newDueTime = new DueTime(oldTask.getDueTime().toString());
        } else {
            newDueTime = new DueTime(newParams.getDueTime().toString());
        }

        return newDueTime;
    }

    private static Priority updatePriority(ReadOnlyTask oldTask, Task newParams, String type) throws IllegalValueException {
        Priority newPriority;

        if (newParams.getPriority().toString().equals(NULL_ENTRY)&& type == "edit") {
            newPriority = new Priority(oldTask.getPriority().toString());
        } else {
            newPriority = new Priority(newParams.getPriority().toString());
        }

        return newPriority;
    }

    private static Reminder updateReminder(ReadOnlyTask oldTask, Task newParams, String type) throws IllegalValueException {
        Reminder newReminder;

        if (newParams.getReminder().toString().equals(NULL_ENTRY)&& type == "edit") {
            newReminder = new Reminder(oldTask.getReminder().toString());
        } else {
            newReminder = new Reminder(newParams.getReminder().toString());
        }

        return newReminder;
    }
    
    private static ReminderTime updateReminderTime(ReadOnlyTask oldTask, Task newParams, String type) throws IllegalValueException {
        ReminderTime newReminderTime;

        if (newParams.getReminderTime().toString().equals(NULL_ENTRY)&& type == "edit") {
            newReminderTime = new ReminderTime(oldTask.getReminderTime().toString());
        } else {
            newReminderTime = new ReminderTime(newParams.getReminderTime().toString());
        }

        return newReminderTime;
    }

    private static UniqueTagList updateTags(ReadOnlyTask oldTask, Task newParams) {
        UniqueTagList newTags = new UniqueTagList(oldTask.getTags());

        for (Tag toAdd : newParams.getTags()) {
            try {
                newTags.add(toAdd);
            } catch (DuplicateTagException e) {
                continue;
            }
        }

        return newTags;
    }
}
