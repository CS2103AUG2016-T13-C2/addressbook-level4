package seedu.lifekeeper.model.activity;

import seedu.lifekeeper.commons.exceptions.IllegalValueException;
import seedu.lifekeeper.model.activity.event.EndTime;
import seedu.lifekeeper.model.activity.event.Event;
import seedu.lifekeeper.model.activity.event.StartTime;
import seedu.lifekeeper.model.activity.task.DueDate;
import seedu.lifekeeper.model.activity.task.Priority;
import seedu.lifekeeper.model.activity.task.Task;
import seedu.lifekeeper.model.tag.Tag;
import seedu.lifekeeper.model.tag.UniqueTagList;
import seedu.lifekeeper.model.tag.UniqueTagList.DuplicateTagException;
//@@author A0125680H
public class ActivityManager {
    private static final String NULL_ENTRY = "";
    
    public static Activity editUnaffectedParams(Activity oldTask, Activity newParams, String type) {
        Activity newActivity = null;
        String oldTaskType = oldTask.getClass().getSimpleName().toLowerCase();
        String newParamsType = newParams.getClass().getSimpleName().toLowerCase();
        try {
            switch (oldTaskType) {
            case "activity":
                if (newParamsType.equals("task")) { //change from activity to task
                    newActivity = new Task(
                            updateTaskName(oldTask, newParams, type),
                            updateDueDate(oldTask, newParams, type),
                            updatePriority(oldTask, newParams, type),
                            updateReminder(oldTask, newParams, type),
                            updateTags(oldTask, newParams, type)
                            );
                } else if (newParamsType.equals("event")) { //change from activity to event
                    newActivity = new Event(
                            updateTaskName(oldTask, newParams, type),
                            updateStartTime(oldTask, newParams, type),
                            updateEndTime(oldTask, newParams, type),
                            updateReminder(oldTask, newParams, type),
                            updateTags(oldTask, newParams, type)
                            );
                } else { //remain as activity
                    newActivity = new Activity(
                            updateTaskName(oldTask, newParams, type),
                            updateReminder(oldTask, newParams, type),
                            updateTags(oldTask, newParams, type)
                            );
                }
                
                newActivity.setCompletionStatus(oldTask.getCompletionStatus());
                break;
            case "task":
            	 if (newParamsType.equals("task")) {
            	 newActivity = new Task(
                         updateTaskName(oldTask, newParams, type),
                         updateDueDate((Task) oldTask, newParams, type),
                         updatePriority((Task) oldTask, newParams, type),
                         updateReminder(oldTask, newParams, type),
                         updateTags(oldTask, newParams, type)
                         );
            	 } else if (newParamsType.equals("activity") && type.equals("edit")) {
            	     newActivity = new Task(
            	             updateTaskName(oldTask, newParams, type),
            	             new DueDate(((Task) oldTask).getDueDate().getCalendarValue()),
            	             new Priority(((Task) oldTask).getPriority().toString()),
                             updateReminder(oldTask, newParams, type),
                             updateTags(oldTask, newParams, type)
            	             );
            	 } else if(type.equals("undo")) {
					newActivity = new Activity(
							updateTaskName(oldTask, newParams, type),
							updateReminder(oldTask, newParams, type),
							updateTags(oldTask, newParams, type)
					);
            		 
            	 }
            	
            	newActivity.setCompletionStatus(oldTask.getCompletionStatus());
                break;
            case "event":
                if (newParamsType.equals("event")) {
                    newActivity = new Event(
                            updateTaskName(oldTask, newParams, type),
                            updateStartTime((Event) oldTask, newParams, type),
                            updateEndTime((Event) oldTask, newParams, type),
                            updateReminder(oldTask, newParams, type),
                            updateTags(oldTask, newParams, type)
                            );
                    } else if (newParamsType.equals("activity") && type.equals("edit")) {
                        newActivity = new Event(
                                updateTaskName(oldTask, newParams, type),
                                new StartTime(((Event) oldTask).getStartTime().getCalendarValue(), ((Event) oldTask).getStartTime().recurring, ((Event) oldTask).getStartTime().RecurringMessage),
                                new EndTime(((Event) oldTask).getEndTime().getCalendarValue(), ((Event) oldTask).getStartTime().recurring, ((Event) oldTask).getStartTime().RecurringMessage),
                                updateReminder(oldTask, newParams, type),
                                updateTags(oldTask, newParams, type)
                                );
                    }
            	if (newParamsType.equals("activity") && (type.equals("undo")) ) {
                    newActivity = new Activity(
                            updateTaskName(oldTask, newParams, type),
                            updateReminder(oldTask, newParams, type),
                            updateTags(oldTask, newParams, type)
                                 ); 
            	}
                break;
            default:
                assert false : "Invalid class type";
            }
        } catch (IllegalValueException ive) {
            assert false : "There should not be any illegal value at this point";
        }
        
        return newActivity;
    }

    public static void marksTask(Activity task, boolean isComplete) {
		task.setCompletionStatus(isComplete);
	}

    //@@author A0125680H
	private static Name updateTaskName(Activity oldTask, Activity newParams, String type) throws IllegalValueException {
        Name newTaskName;

        if (newParams.getName().toString().equals(NULL_ENTRY)&& type.equals("edit")) {
            newTaskName = new Name(oldTask.getName().toString());
        } else {
            newTaskName = new Name(newParams.getName().toString());
        }

        return newTaskName;
    }

	//@@author A0125680H
    private static DueDate updateDueDate(Activity oldTask, Activity newParams, String type) throws IllegalValueException {
        DueDate newDueDate;
        
        if (!newParams.getClass().getSimpleName().equalsIgnoreCase("task")) {
            return new DueDate(((Task) oldTask).getDueDate().getCalendarValue());
        }

        if (((Task) newParams).getDueDate().toString().equals(NULL_ENTRY)&& type.equals("edit")) {
            if (oldTask.getClass().getSimpleName().equalsIgnoreCase("activity")) {
                newDueDate = new DueDate("");
            } else {
                newDueDate = new DueDate(((Task) oldTask).getDueDate().getCalendarValue());
            }
        } else {
            newDueDate = new DueDate(((Task) newParams).getDueDate().getCalendarValue());
        }

        return newDueDate;
    }

    //@@author A0125680H
    private static Priority updatePriority(Activity oldTask, Activity newParams, String type) throws IllegalValueException {
        Priority newPriority;
        
        if (!newParams.getClass().getSimpleName().equalsIgnoreCase("task")) {
            return new Priority(((Task) oldTask).getPriority().toString());
        }

        if (((Task) newParams).getPriority().toString().equals(NULL_ENTRY)&& type.equals("edit")) {
            if (oldTask.getClass().getSimpleName().equalsIgnoreCase("activity")) {
                newPriority = new Priority("0");
            } else {
                newPriority = new Priority(((Task) oldTask).getPriority().toString());
            }
        } else {
            newPriority = new Priority(((Task) newParams).getPriority().toString());
        }

        return newPriority;
    }

    //@@author A0125680H
    private static Reminder updateReminder(Activity oldTask, Activity newParams, String type) throws IllegalValueException {
        Reminder newReminder;

        if (newParams.getReminder().toString().equals(NULL_ENTRY)&& type.equals("edit")) {
            newReminder = new Reminder(oldTask.getReminder().getCalendarValue());
            newReminder.recurring=oldTask.getReminder().recurring;
            newReminder.RecurringMessage = oldTask.getReminder().RecurringMessage;            
        } else {
            newReminder = new Reminder(newParams.getReminder().getCalendarValue());
            newReminder.recurring=newParams.getReminder().recurring;
            newReminder.RecurringMessage = newParams.getReminder().RecurringMessage;
        }

        return newReminder;
    }
    
    //Handle wrong time format in event instead of here?
    //@@author A0125680H
    private static StartTime updateStartTime(Activity oldTask, Activity newParams, String type) throws IllegalValueException {
        StartTime newStartTime;
        
        if (!newParams.getClass().getSimpleName().equalsIgnoreCase("event")) {
            return new StartTime(((Event) oldTask).getStartTime().getCalendarValue());
        }

        if (((Event) newParams).getStartTime().toString().equals(NULL_ENTRY)&& type.equals("edit")) {
            if (oldTask.getClass().getSimpleName().equalsIgnoreCase("activity")) {
                newStartTime = new StartTime("");
            } else {
                newStartTime = new StartTime(((Event) oldTask).getStartTime().getCalendarValue());
                newStartTime.recurring = ((Event) oldTask).getStartTime().recurring;
                newStartTime.RecurringMessage = ((Event) oldTask).getStartTime().RecurringMessage;

            }
        } else {
            newStartTime = new StartTime(((Event) newParams).getStartTime().getCalendarValue());
            newStartTime.recurring = ((Event) newParams).getStartTime().recurring;
            newStartTime.RecurringMessage = ((Event) newParams).getStartTime().RecurringMessage;
        }

        return newStartTime;
    }

    //@@author A0125680H
    private static EndTime updateEndTime(Activity oldTask, Activity newParams, String type) throws IllegalValueException {
        EndTime newEndTime;
        
        if (!newParams.getClass().getSimpleName().equalsIgnoreCase("event")) {
            return new EndTime(((Event) oldTask).getEndTime().getCalendarValue());
        }

        if (((Event) newParams).getEndTime().toString().equals(NULL_ENTRY)&& type.equals("edit")) {
            if (oldTask.getClass().getSimpleName().equalsIgnoreCase("activity")) {
                newEndTime = new EndTime(((Event) oldTask).getStartTime(), ""); //what to put as starttime?
            } else {
                newEndTime = new EndTime(((Event) oldTask).getEndTime().getCalendarValue());
                newEndTime.recurring = ((Event) oldTask).getEndTime().recurring;
                newEndTime.RecurringMessage = ((Event) oldTask).getEndTime().RecurringMessage;
            }
        } else {
            newEndTime = new EndTime(((Event) newParams).getEndTime().getCalendarValue());
            newEndTime.recurring = ((Event) newParams).getEndTime().recurring;
            newEndTime.RecurringMessage = ((Event) newParams).getEndTime().RecurringMessage;
        }

        return newEndTime;
    }
   
    //@@author A0125680H
    private static UniqueTagList updateTags(Activity oldTask, Activity newParams, String type) {
        UniqueTagList newTags = new UniqueTagList();
        if (type.equals("edit")) {
            newTags = new UniqueTagList(oldTask.getTags());
        }
        
        for (Tag toAdd : newParams.getTags()) {
            if (type.equals("edit")) {
                try {
                    newTags.add(toAdd);
                } catch (DuplicateTagException e) {
                    continue;
                }
            } else {
                try {
                    newTags.add(toAdd);
                } catch (DuplicateTagException e) {
                    continue;
                }
            }
        }

        return newTags;
    }



}
