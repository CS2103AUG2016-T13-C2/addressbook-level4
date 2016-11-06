package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seedu.address.model.activity.ReadOnlyActivity;
import seedu.address.model.activity.event.ReadOnlyEvent;
import seedu.address.model.activity.task.ReadOnlyTask;
import seedu.address.testutil.ImageUtil;

/**
 * Provides a handle to a person card in the person list panel.
 */
//@@author A0125097A
public class ActivityCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String REMINDER_FIELD_ID = "#reminder";
    private static final String DUEDATE_FIELD_ID = "#line1";
    private static final String PRIORITY_FIELD_ID = "#priorityIcon";
    private static final String STARTENDTIME_FIELD_ID = "#line1";

    

    private Node node;

    public ActivityCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }
    
    protected Image getImageFromImageView(String fieldId) {
        return getImageFromImageView(fieldId, node);
    }

    public String getFullName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getReminder() {
        return getTextFromLabel(REMINDER_FIELD_ID);
    }
    
    public String getDueDate() {
    	return getTextFromLabel(DUEDATE_FIELD_ID);
    }
    
    public Image getPriority() {
    	return getImageFromImageView(PRIORITY_FIELD_ID);
    }
    
    public String getStartEndTime() {
    	return getTextFromLabel(STARTENDTIME_FIELD_ID);
    }
    

  //@@author A0131813R
    public boolean isSameActivity(ReadOnlyActivity person){

    	boolean isSameName = getFullName().equals(person.getName().fullName);
    	boolean isSameReminder = getReminder().equals(person.getReminder().forDisplay());
    	return (isSameName && isSameReminder);
    }
    
    public boolean isSameTask(ReadOnlyTask person){
    	boolean isSameName = getFullName().equals(person.getName().fullName);
    	boolean isSameReminder = getReminder().equals(person.getReminder().forDisplay());
    	boolean isSameDueDate = getDueDate().toString().equals(person.getDueDate().forDisplay());
    	boolean isSamePriority = ImageUtil.compareImages(getPriority() ,person.getPriority().getPriorityIcon());
    	
    	return (isSameName && isSameReminder && isSameDueDate && isSamePriority);
    }
    
	public boolean isSameEvent(ReadOnlyEvent person){
    	boolean isSameName = getFullName().equals(person.getName().fullName);
    	boolean isSameReminder = getReminder().equals(person.getReminder().forDisplay());
    	boolean isSameStartEndTime = getStartEndTime().equals(person.displayTiming());


    	return (isSameName && isSameReminder && isSameStartEndTime);
    }
  //@@author 
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ActivityCardHandle) {
            ActivityCardHandle handle = (ActivityCardHandle) obj;
            return getFullName().equals(handle.getFullName())
                    && getReminder().equals(handle.getReminder()); //TODO: compare the rest
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullName() + " " + getReminder();
    }
}
