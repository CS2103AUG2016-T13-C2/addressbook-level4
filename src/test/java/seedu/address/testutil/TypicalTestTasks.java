package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask finishIE3101Revision, finishCS2103Project, planTaiwanTrip, daniel, elle, fiona, george, hoon, ida, floatingTaskTestCase;

    public TypicalTestTasks() {
        try {
            finishIE3101Revision =  new TaskBuilder().withTaskName("Complete IE3101 Revision").withReminder("20-11-2016 14:00")
                    .withPriority("1").withDueDate("20-12-2016 14:00")
                    .withTags("tedious").build();
            finishCS2103Project = new TaskBuilder().withTaskName("Finish CS2103 Project").withReminder("31-11-2016 14:00")
                    .withPriority("3").withDueDate("31-12-2016 14:00")
                    .withTags("difficult", "urgent").build();
            planTaiwanTrip = new TaskBuilder().withTaskName("Plan for Taiwan Trip").withDueDate("31-12-2016").withPriority("0").withReminder("20-11-2016").withTags("tedious").build();
            daniel = new TaskBuilder().withTaskName("Daniel Meier").withDueDate("31-11-2016").withPriority("1").withReminder("20-11-2017").withTags("tedious").build();
            elle = new TaskBuilder().withTaskName("Elle Meyer").withDueDate("31-11-2016").withPriority("2").withReminder("20-12-2016").withTags("tedious").build();
            fiona = new TaskBuilder().withTaskName("Fiona Kunz").withDueDate("31-11-2016").withPriority("3").withReminder("28-11-2016").withTags("tedious").build();
            george = new TaskBuilder().withTaskName("George Best").withDueDate("31-11-2016").withPriority("0").withReminder("20-11-2016").withTags("tedious").build();

            //Manually added
            hoon = new TaskBuilder().withTaskName("Hoon Meier").withDueDate("31-11-2018").withPriority("2").withReminder("20-11-2017").build();
            ida = new TaskBuilder().withTaskName("Ida Mueller").withDueDate("31-11-2019").withPriority("1").withReminder("20-11-2018").build();
            floatingTaskTestCase = new TaskBuilder().withTaskName("floatingTaskTestCase").withPriority("1").build();
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
            ab.addTask(new Task(daniel));
            ab.addTask(new Task(elle));
            ab.addTask(new Task(fiona));
            ab.addTask(new Task(george));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{finishIE3101Revision, finishCS2103Project, planTaiwanTrip, daniel, elle, fiona, george};
    }

    public AddressBook getTypicalAddressBook(){
        AddressBook ab = new AddressBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
