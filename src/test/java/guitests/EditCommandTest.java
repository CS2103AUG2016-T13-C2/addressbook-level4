package guitests;

import static org.junit.Assert.assertTrue;

import java.awt.List;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Reminder;
import seedu.address.model.activity.event.Event;
import seedu.address.model.activity.event.ReadOnlyEvent;
import seedu.address.model.activity.task.ReadOnlyTask;
import seedu.address.model.activity.task.Task;
import seedu.address.testutil.*;

//@@author A0125097A
public class EditCommandTest extends AddressBookGuiTest {

    @Test
    public void edit_activityParameters(){
        TestActivity[] currentList = td.getTypicalActivities();
        int index = 1;
        assertEditActivityResult(index,currentList);
    }
    
    @Test
    public void edit_taskParameters(){
        TestActivity[] currentList = td.getTypicalActivities();
        int index = 3;
        assertEditTaskResult(index,currentList);
    }
    
    @Test
    public void edit_activityWithTaskParameters(){
        TestActivity[] currentList = td.getTypicalActivities();
        int index = 1;
        assertEditTaskResult(index,currentList);
    }


	@Test
    public void edit_eventParameters(){
        TestActivity[] currentList = td.getTypicalActivities();
        int index = 5;
        assertEditEventResult(index,currentList);        
    }
	
    @Test
    public void edit_activityWithEventParameters(){
        TestActivity[] currentList = td.getTypicalActivities();
        int index = 1;
        assertEditEventResult(index,currentList);
    }
    


	private void assertEditActivityResult(int index,TestActivity... currentList){
    	assertEditResult(index,"activity",currentList);
    }
    
	private void assertEditTaskResult(int index, TestActivity[] currentList) {
		assertEditResult(index,"task",currentList);	
	}
    
    private void assertEditEventResult(int index, TestActivity[] currentList) {
    	assertEditResult(index,"event",currentList);
	}
    
    	private void assertEditResult(int index,String type, TestActivity... currentList){
    	String newName = "Totally New Name";
    	String newReminder = "1-01-2020 1111";
    	String newDuedate = "10-10-2020 1010";
    	String newPriority = "3";
    	String newStartTime = "2-02-2020 1212";
    	String newEndTime = "10-10-2020 1010";
        String editCommand = "edit " + index;
    	
        TestActivity activityBeforeEdit;
			activityBeforeEdit = produceNewActivityObject(currentList[index-1]);

    	TestActivity activityAfterEdit = null;

        editCommand += " n/" + newName ;
        switch (type) {
        case "task":
        {
				activityAfterEdit = new TestTask(currentList[index-1]);
        ((TestTask) activityAfterEdit).setDueDate(newDuedate);
        ((TestTask) activityAfterEdit).setPriority(newPriority);
        editCommand += " d/" + newDuedate  + " p/" + newPriority;
        }
        break;
        
        case "event":
        {
				activityAfterEdit = new TestEvent(currentList[index-1]);
        ((TestEvent) activityAfterEdit).setStartTime(newStartTime);
        ((TestEvent) activityAfterEdit).setEndTime(newEndTime);
        editCommand += " s/" + newStartTime  + " e/" + newEndTime;
        } 
        break;
        
        case "activity":
        	activityAfterEdit = new TestActivity(currentList[index-1]);
        }
        
        activityAfterEdit.setName(newName);
        activityAfterEdit.setReminder(newReminder);
        editCommand += " r/" + newReminder;
        commandBox.runCommand(editCommand);
        
        assertResultMessage(String.format("Edited Task from: %1$s\nto: %2$s",
        		activityBeforeEdit.getAsText(),activityAfterEdit.getAsText()));
        
        assertTrue(activityListPanel.isListMatching(currentList));
 
    }
    	
    	
        private TestActivity produceNewActivityObject(TestActivity original){
            String type = original.getClass().getSimpleName().toLowerCase();
            
            switch(type){
            case "testactivity":
            	return new TestActivity(original);
            case "testtask":
            	return new TestTask((TestTask) original);
            default: //case "event":
            	return new TestEvent((TestEvent) original);	
            }
        }
        
}
