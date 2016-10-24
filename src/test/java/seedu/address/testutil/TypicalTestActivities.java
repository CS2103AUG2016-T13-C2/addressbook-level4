package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.UniqueTaskList;
import seedu.address.model.activity.task.*;

/**
 *
 */
public class TypicalTestActivities {

    public static TestActivity findAlice, findHoon;
    public static TestTask findBenson, findCarl, findDaniel, findIda; 
    public static TestEvent findElle, findFiona, findGeorge, findJodie;

    public TypicalTestActivities() {
        try {
        	//to test loading saved activity, tasks and events
            findAlice =  new ActivityBuilder().withName("find Alice").withReminder("30-12-2017 1200").withTags("bringgift").build();
            findBenson = new TaskBuilder().withName("find Benson").withDueDate("31-12-2017 1400").withTags("bringgift").build();
            findCarl = new TaskBuilder().withName("find Carl").withDueDate("31-12-2017 1200").build();
            findDaniel = new TaskBuilder().withName("find Daniel").withDueDate("31-12-2017 1200").withReminder("30-12-2017 1200").withTags("bringgifts").build();
            findElle = new EventBuilder().withName("find Elle").withStartTime("31-12-2017 1200").withReminder("30-12-2017 1200").build();
            findFiona = new EventBuilder().withName("find Fiona").withStartTime("31-12-2017 1200").withEndTime("31-12-2017 1800").withReminder("30-12-2017 1200").build();
            findGeorge = new EventBuilder().withName("find George").withStartTime("31-12-2017 1200").withEndTime("31-12-2017 1800").withTags("bringgifts").build();

            //To test adding of activity, task and event.
            findHoon = new ActivityBuilder().withName("find Hoon").withReminder("30-12-2017 1200").withTags("bringgift").build();
            findIda = new TaskBuilder().withName("find Ida").withDueDate("31-12-2017 1200").withReminder("30-12-2017 1200").withTags("bringgifts").build();
            findJodie = new EventBuilder().withName("find Jodie").withStartTime("31-12-2017 1200").withEndTime("31-12-2017 1800").withReminder("30-12-2017 1200").withTags("bringgifts").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(AddressBook ab) {

        try {
            ab.addPerson(new Activity(findAlice));
            ab.addPerson(new Activity(findBenson));
            ab.addPerson(new Activity(findCarl));
            ab.addPerson(new Activity(findDaniel));
            ab.addPerson(new Activity(findElle));
            ab.addPerson(new Activity(findFiona));
            ab.addPerson(new Activity(findGeorge));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestActivity[] getTypicalPersons() {
        return new TestActivity[]{findAlice, findBenson, findCarl, findDaniel, findElle, findFiona, findGeorge};
    }

    public AddressBook getTypicalAddressBook(){
        AddressBook ab = new AddressBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}