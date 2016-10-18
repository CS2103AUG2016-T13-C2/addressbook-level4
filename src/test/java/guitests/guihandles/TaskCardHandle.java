package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getTaskName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    /**
     * 
     * @return String that represents Reminder, formatted as Day, Month DD, YYYY hh:mm
     */
    public String getReminder() {
        return getTextFromLabel(ADDRESS_FIELD_ID);
    }
    /**
     * 
     * @return String that represents DueDate, formatted as Day, Month DD, YYYY hh:mm
     */
    public String getDueDate() {
        return getTextFromLabel(PHONE_FIELD_ID);
    }
    /**
     * 
     * @return Priority in String Format
     */
    public String getPriority() {
        return getTextFromLabel(EMAIL_FIELD_ID);
    }

    public boolean isSamePerson(ReadOnlyTask task){    	
        return getTaskName().equals(task.getName().fullName) && getDueDate().equals(task.getDueDate().outputDueDateAsString())
                && getPriority().equals(task.getPriority().value) && getReminder().equals(task.getReminder().outputReminderDateAsString());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getTaskName().equals(handle.getTaskName())
                    && getReminder().equals(handle.getReminder()); //TODO: compare the rest
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getTaskName() + " " + getReminder();
    }
}
