package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.DueDate;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Reminder;
import seedu.address.model.task.TaskName;
import seedu.address.testutil.TestPerson;
import seedu.address.testutil.TestUtil;


public class EditCommandTest extends AddressBookGuiTest {

    @Test
    /**
     * Assumptions: there is at least one task already in TypicalTestPersons.
     */
    public void edit() {
        TestPerson[] currentList = td.getTypicalPersons();
        
    	//edit one person selected
        int targetIndex = 1;
        assertEditSuccess(targetIndex, currentList);
    	
        //invalid index
        commandBox.runCommand("edit " + currentList.length + 1);
        assertResultMessage("The person index provided is invalid");
        
    	//invalid command
        commandBox.runCommand("edits Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
  
    }
    
    
    /**
     * Runs the edit command to edit the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of task (before edits).
     */
	
	private void assertEditSuccess(int targetIndexOneIndexed, final TestPerson[] currentList) {
        TestPerson taskToEdit = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        
        //i am still unsure how to initialise the tags.
        TestPerson expectedResult = TestUtil.editPersonAttributes(taskToEdit, new TaskName("CS2103 T7A1 "), new Reminder("05-01-2016"), new Priority("1"), new DueDate("06-10-2016"));
        
        //i am thinking that commandBox is a virtual CLI that takes in sample user input and outputs results in a test environment
        commandBox.runCommand("edit " + targetIndexOneIndexed);
        //not sure of what is the exact input format for edit.
        commandBox.runCommand("CS2103 T7A1 d/06-10-2016 p/1 r/05-01-2016 t/CS t/groupwork");

        //confirm if both tasks are exactly the same
        assertEquals(currentList[targetIndexOneIndexed-1], expectedResult);

        //confirm the result message is correct

    }
}
