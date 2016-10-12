package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask finishIE3101Revision, finishCS2103Project, planTaiwanTrip, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            finishIE3101Revision =  new TaskBuilder().withTaskName("Complete IE3101 Revision").withReminder("20-11-2016")
                    .withPriority("1").withDueDate("20-12-2016")
                    .withTags("tedious").build();
            finishCS2103Project = new TaskBuilder().withTaskName("Finish CS2103 Project").withReminder("31-11-2016")
                    .withPriority("3").withDueDate("31-12-2016")
                    .withTags("difficult", "urgent").build();
            planTaiwanTrip = new TaskBuilder().withTaskName("Plan for Taiwan Trip").withDueDate("31-11-2016").withPriority("0").withReminder("20-11-2016").withTags("tedious").build();
            daniel = new TaskBuilder().withTaskName("Daniel Meier").withDueDate("31-11-2016").withPriority("1").withReminder("20-11-2017").withTags("tedious").build();
            elle = new TaskBuilder().withTaskName("Elle Meyer").withDueDate("31-11-2016").withPriority("2").withReminder("20-12-2016").withTags("tedious").build();
            fiona = new TaskBuilder().withTaskName("Fiona Kunz").withDueDate("31-11-2016").withPriority("3").withReminder("28-11-2016").withTags("tedious").build();
            george = new TaskBuilder().withTaskName("George Best").withDueDate("31-11-2016").withPriority("0").withReminder("20-11-2016").withTags("tedious").build();

            //Manually added
            hoon = new TaskBuilder().withTaskName("Hoon Meier").withDueDate("8482424").withPriority("stefan@mail.com").withReminder("little india").build();
            ida = new TaskBuilder().withTaskName("Ida Mueller").withDueDate("8482131").withPriority("hans@google.com").withReminder("chicago ave").build();
            //wrongDueDateTestCase = new TaskBuilder().withTaskName("wrongDueDateTestCase").withReminder("20-11-2016").withPriority("1").withDueDate("20-40-2016").withPriority("0").withReminder("20-11-2016").build();
            //wrongReminderTestCase = new TaskBuilder().withTaskName("wrongDueDateTestCase").withReminder("55-11-2016").withPriority("1").withDueDate("20-12-2016").withPriority("0").withReminder("20-11-2016").build();
            //wrongPriorityTestCase = new TaskBuilder().withTaskName("wrongDueDateTestCase").withReminder("55-11-2016").withPriority("1").withDueDate("20-12-2016").withPriority("0").withReminder("20-11-2016").build();
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
