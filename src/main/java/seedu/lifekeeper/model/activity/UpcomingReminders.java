package seedu.lifekeeper.model.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.PriorityQueue;
import java.util.logging.Logger;

import seedu.lifekeeper.commons.core.LogsCenter;
import seedu.lifekeeper.model.activity.Activity;


//@@author A0125680H
/**
 * Keeps a queue of upcoming events. Events that are ongoing or over will not be
 * added to the queue. 
 * The queue is ordered in such a way that the earliest incoming reminder is at the front of the queue. 
 */
public class UpcomingReminders {

    private static final int DEFAULT_SIZE = 50;

    private static PriorityQueue<ReadOnlyActivity> reminderQueue;
    
    private static final Logger logger = LogsCenter.getLogger(LogsCenter.class);

    {
        reminderQueue = new PriorityQueue<>(DEFAULT_SIZE, new Comparator<ReadOnlyActivity>() {
            
            public int compare(ReadOnlyActivity first, ReadOnlyActivity second) {
                return first.getReminder().compareTo(second.getReminder());
            }
        });
    }

    public UpcomingReminders() {}
    
    /**
     * Empties the current event queue and adds the specified activities into the queue. 
     * @param activities to be added into the queue
     */
    public void initialize(Collection<Activity> activities) {
        empty();
        
        for (ReadOnlyActivity activity : activities) {
            if (activity.getReminder().getCalendarValue() != null && !activity.hasReminderPassed()) {
                logger.info("Activity added to reminder queue: " + activity.toString());
                reminderQueue.add(activity);
            }
        }
    }

    /**
     * Adds an activity to the reminder queue. Activities without reminder or a
     * passed reminder will not be added.
     * 
     * @return true if the activity is added to the queue.
     */
    public boolean addReminder(ReadOnlyActivity newActivity) {
        if (newActivity.getReminder().getCalendarValue() != null && !newActivity.hasReminderPassed()) {
            return reminderQueue.add(newActivity);
        } else {
            return false;
        }
    }

    /**
     * Removes the activity from the reminder queue, if it exists.
     * 
     * @return true if the reminder is successfully removed.
     */
    public boolean removeReminder(ReadOnlyActivity activity) {
        return reminderQueue.remove(activity);
    }

    /**
     * Dequeues and returns a list of events having reminders closest to the current time.
     * 
     * @return a list of the events with start time closest to current time.
     */
    public static ArrayList<ReadOnlyActivity> popNextReminders() {
        ArrayList<ReadOnlyActivity> nextRemindedActivities = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 10);
        Date offset10Secs = cal.getTime(); //The current time is offset by 10 seconds to account for lag.

        while (!reminderQueue.isEmpty()
                && reminderQueue.peek().getReminder().getCalendarValue().getTime().before(offset10Secs)) {
            ReadOnlyActivity toBeReminded = reminderQueue.poll();
            logger.info("Activity removed from reminder queue: " + toBeReminded.toString());
            nextRemindedActivities.add(toBeReminded);
            toBeReminded.getReminder().resetTime();
        }
        
        return nextRemindedActivities;
    }
    
    /**
     * Clears the reminder queue.
     */
    public static void empty() {
        while (!reminderQueue.isEmpty()) {
            reminderQueue.poll();
        }
    }
}
