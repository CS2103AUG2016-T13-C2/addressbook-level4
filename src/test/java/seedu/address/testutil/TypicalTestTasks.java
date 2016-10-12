package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice =  new TaskBuilder().withTaskName("Alice Pauline").withReminder("123, Jurong West Ave 6, #08-111")
                    .withPriority("alice@gmail.com").withDueDate("85355255")
                    .withTags("friends").build();
            benson = new TaskBuilder().withTaskName("Benson Meier").withReminder("311, Clementi Ave 2, #02-25")
                    .withPriority("johnd@gmail.com").withDueDate("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withTaskName("Carl Kurz").withDueDate("95352563").withPriority("heinz@yahoo.com").withReminder("wall street").build();
            daniel = new TaskBuilder().withTaskName("Daniel Meier").withDueDate("87652533").withPriority("cornelia@google.com").withReminder("10th street").build();
            elle = new TaskBuilder().withTaskName("Elle Meyer").withDueDate("9482224").withPriority("werner@gmail.com").withReminder("michegan ave").build();
            fiona = new TaskBuilder().withTaskName("Fiona Kunz").withDueDate("9482427").withPriority("lydia@gmail.com").withReminder("little tokyo").build();
            george = new TaskBuilder().withTaskName("George Best").withDueDate("9482442").withPriority("anna@google.com").withReminder("4th street").build();

            //Manually added
            hoon = new TaskBuilder().withTaskName("Hoon Meier").withDueDate("8482424").withPriority("stefan@mail.com").withReminder("little india").build();
            ida = new TaskBuilder().withTaskName("Ida Mueller").withDueDate("8482131").withPriority("hans@google.com").withReminder("chicago ave").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(AddressBook ab) {

        try {
            ab.addTask(new Task(alice));
            ab.addTask(new Task(benson));
            ab.addTask(new Task(carl));
            ab.addTask(new Task(daniel));
            ab.addTask(new Task(elle));
            ab.addTask(new Task(fiona));
            ab.addTask(new Task(george));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public AddressBook getTypicalAddressBook(){
        AddressBook ab = new AddressBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
