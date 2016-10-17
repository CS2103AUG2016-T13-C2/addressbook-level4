package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask finishIE3101Revision, finishCS2103Project, planTaiwanTrip, findDaniel, findElle, findFiona, findGeorge, findHoon, findIda, floatingTaskTestCase, tomorrowTaskTestCase;

    public TypicalTestTasks() {
        try {
            finishIE3101Revision =  new TaskBuilder().withTaskName("Complete IE3101 Revision").withReminder("20-11-2016 14:00").withPriority("1").withDueDate("20-12-2016 14:00").build();
            finishCS2103Project = new TaskBuilder().withTaskName("Finish CS2103 Project").withReminder("31-12-2016 14:00").withPriority("3").withDueDate("31-12-2016 14:00").build();
            planTaiwanTrip = new TaskBuilder().withTaskName("Plan Taiwan Trip").withDueDate("31-12-2016 14:00").withPriority("0").withReminder("20-11-2016 14:00").withTags("tedious").build();
            findDaniel = new TaskBuilder().withTaskName("Find Daniel").withDueDate("31-12-2016 19:00").withPriority("1").withReminder("31-12-2016 18:00").withTags("tedious").build();
            findElle = new TaskBuilder().withTaskName("Find Elle").withDueDate("31-12-2016 14:00").withPriority("2").withReminder("20-12-2016 14:00").withTags("tedious").build();
            findFiona = new TaskBuilder().withTaskName("Find Fiona").withDueDate("31-12-2016 14:00").withPriority("3").withReminder("28-11-2016 14:00").withTags("tedious").build();
            findGeorge = new TaskBuilder().withTaskName("Find George").withDueDate("31-12-2016 14:00").withPriority("0").withReminder("20-11-2016 14:00").withTags("tedious").build();

            //Input Test Cases
            findHoon = new TaskBuilder().withTaskName("Find Hoon").withDueDate("31-12-2018 14:00").withPriority("2").withReminder("20-11-2017 14:00").build();
            findIda = new TaskBuilder().withTaskName("Find Ida").withDueDate("31-12-2019 14:00").withPriority("1").withReminder("20-11-2018 14:00").build();
            floatingTaskTestCase = new TaskBuilder().withTaskName("floatingTaskTestCase").withPriority("1").build();
         //   tomorrowTaskTestCase = new TaskBuilder(). withTaskName("tomorrowTaskTestCase").withDueDate("tomorrow 14:00").withPriority("1").withReminder("Tomorrow 13:00").build();
            
            //invalid test cases
            //invalidDueDateTestCase = new TaskBuilder().withTaskName("invalidDueDateTestCase").withReminder("20-11-2016").withPriority("1").withDueDate("20-40-2016").build();
            //invalidReminderTestCase = new TaskBuilder().withTaskName("invalidReminderTestCase").withReminder("55-11-2016").withPriority("1").withDueDate("20-12-2016").build();
            //invalidPriorityTestCase = new TaskBuilder().withTaskName("invalidPriorityTestCase").withReminder("20-11-2016").withPriority("4").withDueDate("20-12-2016").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(AddressBook ab) {

        try {
            ab.addTask(new Task(finishIE3101Revision));
            ab.addTask(new Task(finishCS2103Project));
            ab.addTask(new Task(planTaiwanTrip));
            ab.addTask(new Task(findDaniel));
            ab.addTask(new Task(findElle));
            ab.addTask(new Task(findFiona));
            ab.addTask(new Task(findGeorge));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{finishIE3101Revision, finishCS2103Project, planTaiwanTrip, findDaniel, findElle, findFiona, findGeorge};
    }

    public AddressBook getTypicalAddressBook(){
        AddressBook ab = new AddressBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
