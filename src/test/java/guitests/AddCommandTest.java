package guitests;

import guitests.guihandles.ActivityCardHandle;
import org.junit.Test;
import seedu.address.logic.commands.AddCommand;
import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestActivity;
import seedu.address.testutil.TestEvent;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends AddressBookGuiTest {

    @Test
    public void add() {

    	//section 1: test for adding basic activities, tasks and events.
    	
    	//add an activity
        TestActivity[] currentList = td.getTypicalActivities();
        TestActivity activityToAdd = td.findHoon;

        assertAddSuccess(activityToAdd.getAddCommand(),activityToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, activityToAdd);

        //add a task
        activityToAdd = td.findIda;
        assertAddSuccess(activityToAdd.getAddCommand(),activityToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, activityToAdd);
        assertTrue(activityListPanel.isListMatching(currentList));
 
        //add an event
        activityToAdd = td.findJodie;
        assertAddSuccess(activityToAdd.getAddCommand(),activityToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, activityToAdd);

        //add an event without specifying end time
        activityToAdd = td.findKenny;
        assertAddSuccess(((TestEvent) activityToAdd).getAddCommandWithNoEndTime(),activityToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, activityToAdd);

        //add a recurring event
        activityToAdd = td.findMoney;
        assertAddSuccess(activityToAdd.getAddCommand(),activityToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, activityToAdd);

        
        //section 2: test for adding duplicates of activities, tasks and events/
        
        //add duplicate activity
        commandBox.runCommand(td.findHoon.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(activityListPanel.isListMatching(currentList));

        //add duplicate task
        commandBox.runCommand(td.findIda.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);

        //add duplicate event
        commandBox.runCommand(td.findJodie.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(activityListPanel.isListMatching(currentList));

        //section 3:
 
        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.findAlice.getAddCommand(),td.findAlice);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        
        //testing for date formatting
        commandBox.runCommand("");
        
    }

    private void assertAddSuccess(String command, TestActivity activityToAdd, TestActivity... currentList) {
    	commandBox.runCommand(command);
        //confirm the new card contains the right data
        ActivityCardHandle addedCard = activityListPanel.navigateToActivity(activityToAdd.getName().fullName);
        assertMatching(activityToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestActivity[] expectedList = TestUtil.addPersonsToList(currentList, activityToAdd);
        assertTrue(activityListPanel.isListMatching(expectedList));
    }

}
