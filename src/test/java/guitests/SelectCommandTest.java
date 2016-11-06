package guitests;

import org.junit.Test;

import seedu.lifekeeper.model.activity.ReadOnlyActivity;

import static org.junit.Assert.assertEquals;

public class SelectCommandTest extends AddressBookGuiTest {


    @Test
    public void selectPerson_nonEmptyList() {

        assertSelectionInvalid(10); //invalid index
        assertNoPersonSelected();

        assertSelectionSuccess(1); //first activity in the list
        int personCount = td.getTypicalActivities().length;
        assertSelectionSuccess(personCount); //last activity in the list
        int middleIndex = personCount / 2;
        assertSelectionSuccess(middleIndex); //a activity in the middle of the list

        assertSelectionInvalid(personCount + 1); //invalid index
        assertPersonSelected(middleIndex); //assert previous selection remains

        /* Testing other invalid indexes such as -1 should be done when testing the SelectCommand */
    }

    @Test
    public void selectPerson_emptyList(){
        commandBox.runCommand("clear");
        assertListSize(0);
        assertSelectionInvalid(1); //invalid index
    }

    private void assertSelectionInvalid(int index) {
        commandBox.runCommand("select " + index);
        assertResultMessage("The task index provided is invalid");
    }

    private void assertSelectionSuccess(int index) {
        commandBox.runCommand("select " + index);
        assertResultMessage("Selected Task: "+index);
        assertPersonSelected(index);
    }

    private void assertPersonSelected(int index) {
        assertEquals(activityListPanel.getSelectedPersons().size(), 1);
        ReadOnlyActivity selectedPerson = activityListPanel.getSelectedPersons().get(0);
        assertEquals(activityListPanel.getPerson(index-1), selectedPerson);
        //TODO: confirm the correct page is loaded in the Browser Panel
    }

    private void assertNoPersonSelected() {
        assertEquals(activityListPanel.getSelectedPersons().size(), 0);
    }

}
