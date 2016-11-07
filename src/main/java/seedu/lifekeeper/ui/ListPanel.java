package seedu.lifekeeper.ui;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.lifekeeper.model.activity.ReadOnlyActivity;
// @@author A0125284H
/**
 * Base class for all List Panels.
 */

public abstract class ListPanel extends UiPart {

	private VBox panel;
	protected AnchorPane placeHolderPane;
	
    @FXML
    protected ListView<ReadOnlyActivity> activityListView;

	public ListPanel() {
			
	}

	@Override
	public void setNode(Node node) {
		panel = (VBox) node;
	}


    public void setPlaceholder(AnchorPane placeholder) {
        //Do nothing by default.
    }

    protected void configure(ObservableList<ReadOnlyActivity> personList) {
        setConnections(personList);
        addToPlaceholder();
    }
    
    protected void setConnections(ObservableList<ReadOnlyActivity> personList) {
        activityListView.setItems(personList);
        activityListView.setCellFactory(listView -> new ActivityListViewCell());
    }
    
    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }
    
    public void scrollTo(int index) {
        Platform.runLater(() -> {
            activityListView.scrollTo(index);
            activityListView.getSelectionModel().clearAndSelect(index);
        });
    }
    
    public void refresh(){
        ObservableList<ReadOnlyActivity> items = activityListView.<ReadOnlyActivity>getItems();
        setConnections(items);
    }
    
	public abstract String getFxmlPath();

}
