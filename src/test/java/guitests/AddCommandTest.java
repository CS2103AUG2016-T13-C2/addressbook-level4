package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;
import seedu.address.logic.commands.AddCommand;
import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends AddressBookGuiTest {

    @Test
    public void add() {
        //add one person
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.findHoon;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
/*
        //add another person
        taskToAdd = td.findIda;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate person
        commandBox.runCommand(td.findHoon.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.findAli);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        
        //add a floating task
        commandBox.runCommand("add floatingTaskTestCase");
        assertAddSuccess(td.floatingTaskTestCase);
        */
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToPerson(taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        System.out.println("before" + currentList.length);
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        System.out.println("after" + expectedList.length);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    /*
     * private void assertAddFailure(TestTask)
     */
    
}
