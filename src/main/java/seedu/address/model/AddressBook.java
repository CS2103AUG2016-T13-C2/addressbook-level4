package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.ReadOnlyActivity;
import seedu.address.model.activity.ActivityManager;
import seedu.address.model.activity.UniqueActivityList;
import seedu.address.model.activity.UniqueActivityList.DuplicateTaskException;
import seedu.address.model.activity.UniqueActivityList.TaskNotFoundException;
import seedu.address.model.activity.UpcomingReminders;
import seedu.address.model.activity.task.Task;
import seedu.address.model.activity.event.Event;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.storage.XmlAdaptedTag;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the address-book level Duplicates are not allowed (by
 * .equals comparison)
 */
public class AddressBook implements ReadOnlyLifeKeeper {

    private final UniqueActivityList activities;
    private final UniqueTagList tags;
    private final UpcomingReminders nextReminders;

    {
        activities = new UniqueActivityList();
        tags = new UniqueTagList();
        nextReminders = new UpcomingReminders();
    }

    public AddressBook() {
    }

    /**
     * Persons and Tags are copied into this addressbook
     */
    public AddressBook(ReadOnlyLifeKeeper toBeCopied) {
        this(toBeCopied.getUniquePersonList(), toBeCopied.getUniqueTagList());
    }

    /**
     * Persons and Tags are copied into this addressbook
     */
    public AddressBook(UniqueActivityList persons, UniqueTagList tags) {
        resetData(persons.getInternalList(), tags.getInternalList());
        nextReminders.initialize(persons.getInternalList());
    }

    public static ReadOnlyLifeKeeper getEmptyAddressBook() {
        return new AddressBook();
    }

    //// list overwrite operations

    public ObservableList<Activity> getAllEntries() {
        // activities.getInternalList().sorted(new Comparator<Activity>(){
        // public int compare (Activity a1, Activity a2){
        // return
        // a1.getClass().getSimpleName().compareTo(a2.getClass().getSimpleName());
        // }
        // });
        return activities.getInternalList().sorted(new Comparator<Activity>() {
            public int compare(Activity a1, Activity a2) {
                if (a1.getClass().getSimpleName().equalsIgnoreCase("activity"))
                    return Integer.MAX_VALUE;
                if (a2.getClass().getSimpleName().equalsIgnoreCase("activity"))
                    return Integer.MIN_VALUE;
                
                return a1.getTimeForComparator().compareTo(a2.getTimeForComparator());
                
                /*
                 * if (a1.getClass().getSimpleName().equalsIgnoreCase("task")
                        && a2.getClass().getSimpleName().equalsIgnoreCase("task"))
                    {if (((Task) a1).getDueDate().value == null)
                        return Integer.MAX_VALUE;
                    else if (((Task) a2).getDueDate().value == null)
                        return Integer.MIN_VALUE;
                return ((Task) a1).getDueDate().value.compareTo(((Task) a2).getDueDate().value);}
                if (a1.getClass().getSimpleName().equalsIgnoreCase("event")
                        && a2.getClass().getSimpleName().equalsIgnoreCase("event"))
                    return ((Event) a1).getStartTime().value.compareTo(((Event) a2).getStartTime().value);
                if (a1.getClass().getSimpleName().equalsIgnoreCase("event")
                        && a2.getClass().getSimpleName().equalsIgnoreCase("task"))
                    return ((Event) a1).getStartTime().value.compareTo(((Task) a2).getDueDate().value);
                if (a1.getClass().getSimpleName().equalsIgnoreCase("task")
                        && a2.getClass().getSimpleName().equalsIgnoreCase("event"))
                    return ((Event) a2).getStartTime().value.compareTo(((Task) a1).getDueDate().value);                
                return a1.getName().toString().compareTo(a2.getName().toString());
                */
            }

        });

    }

    public ObservableList<Tag> getTag() {
        return tags.getInternalList();
    }

    public void setPersons(List<Activity> persons) {
        this.activities.getInternalList().setAll(persons);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    public void resetData(Collection<? extends ReadOnlyActivity> newPersons, Collection<Tag> newTags) {
        List<Activity> activities = newPersons.stream().map(Activity::create).collect(Collectors.toList());
        setPersons(activities);
        setTags(newTags);
        nextReminders.initialize(activities);
    }

    public void resetData(ReadOnlyLifeKeeper newData) {
        resetData(newData.getPersonList(), newData.getTagList());
    }

    //// person-level operations

    /**
     * Adds a person to the address book. Also checks the new person's tags and
     * updates {@link #tags} with any new tags found, and updates the Tag
     * objects in the person to point to those in {@link #tags}.
     *
     * @throws UniqueActivityList.DuplicateTaskException
     *             if an equivalent person already exists.
     */
    public void addPerson(Activity p) throws UniqueActivityList.DuplicateTaskException {
        syncTagsWithMasterList(p);
        activities.addTo(p);
        nextReminders.addReminder(p);
    }

    public void addPerson(int index, Activity activity) throws UniqueActivityList.DuplicateTaskException {
        syncTagsWithMasterList(activity);
        activities.addAt(index, activity);
        nextReminders.addReminder(activity);
    }

    /**
     * Ensures that every tag in this person: - exists in the master list
     * {@link #tags} - points to a Tag object in the master list
     */
    private void syncTagsWithMasterList(Activity person) {
        final UniqueTagList personTags = person.getTags();
        tags.mergeFrom(personTags);

        // Create map with values = tag object references in the master list
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        for (Tag tag : tags) {
            masterTagObjects.put(tag, tag);
        }

        // Rebuild the list of person tags using references from the master list
        final Set<Tag> commonTagReferences = new HashSet<>();
        for (Tag tag : personTags) {
            commonTagReferences.add(masterTagObjects.get(tag));
        }
        person.setTags(new UniqueTagList(commonTagReferences));
    }

    public boolean removePerson(ReadOnlyActivity key) throws UniqueActivityList.TaskNotFoundException {
        if (activities.remove(key)) {
            nextReminders.removeReminder(key);
            return true;
        } else {
            throw new UniqueActivityList.TaskNotFoundException();
        }
    }

    // @@author A0125680H
    public Activity editTask(Activity task, Activity newParams, String type)
            throws TaskNotFoundException, DuplicateTaskException {
        if (activities.contains(task)) {
            Activity newTask = ActivityManager.editUnaffectedParams(task, newParams, type);
            activities.edit(task, newTask);
            nextReminders.removeReminder(task);
            nextReminders.addReminder(newTask);

            return newTask;
        } else {
            throw new UniqueActivityList.TaskNotFoundException();
        }
    }

    public void markTask(Activity task, boolean isComplete) throws TaskNotFoundException {
        if (activities.contains(task)) {
            activities.mark(task, isComplete);
        } else {
            throw new UniqueActivityList.TaskNotFoundException();
        }
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return activities.getInternalList().size() + " persons, " + tags.getInternalList().size() + " tags";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyActivity> getPersonList() {
        return Collections.unmodifiableList(activities.getInternalList());
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }

    @Override
    public UniqueActivityList getUniquePersonList() {
        return this.activities;
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                        && this.activities.equals(((AddressBook) other).activities)
                        && this.tags.equals(((AddressBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(activities, tags);
    }

}
