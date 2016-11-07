package seedu.lifekeeper.ui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.lifekeeper.commons.core.Config;
import seedu.lifekeeper.commons.core.EventsCenter;
import seedu.lifekeeper.commons.core.GuiSettings;
import seedu.lifekeeper.commons.events.model.LoadLifekeeperEvent;
import seedu.lifekeeper.commons.events.model.SaveEvent;
import seedu.lifekeeper.commons.events.ui.ExitAppRequestEvent;
import seedu.lifekeeper.logic.Logic;
import seedu.lifekeeper.model.UserPrefs;
import seedu.lifekeeper.storage.XmlAddressBookStorage;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart {

    private static final String ICON = "/images/lifekeeper.png";
    private static final String FXML = "MainWindow.fxml";
    public static final int MIN_HEIGHT = 600;
    public static final int MIN_WIDTH = 450;

    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private ActivityListPanel activityListPanel;
    private OverdueTaskListPanel overdueListPanel;
    private ResultDisplay resultDisplay;
    private StatusBarFooter statusBarFooter;
    private CommandBox commandBox;
    private Config config;
    private UserPrefs userPrefs;

    // Handles to elements of this Ui container
    private VBox rootLayout;
    private Scene scene;

    private String addressBookName;

    @FXML
    private AnchorPane browserPlaceholder;

    @FXML
    private AnchorPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private AnchorPane activityListPanelPlaceholder;

    @FXML
    private AnchorPane resultDisplayPlaceholder;

    @FXML
    private AnchorPane statusbarPlaceholder;
        
    @FXML
    private AnchorPane overdueListDisplayPlaceHolder;

    public MainWindow() {
        super();
    }

    @Override
    public void setNode(Node node) {
        rootLayout = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    public static MainWindow load(Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {

        MainWindow mainWindow = UiPartLoader.loadUiPart(primaryStage, new MainWindow());
        mainWindow.configure(config.getAppTitle(), config.getLifekeeperName(), config, prefs, logic);
        return mainWindow;
    }

    private void configure(String appTitle, String addressBookName, Config config, UserPrefs prefs,
                           Logic logic) {

        //Set dependencies
        this.logic = logic;
        this.addressBookName = addressBookName;
        this.config = config;
        this.userPrefs = prefs;

        //Configure the UI
        setTitle(appTitle);
        setIcon(ICON);
        setWindowMinSize();
        setWindowDefaultSize(prefs);
        scene = new Scene(rootLayout);
        primaryStage.setScene(scene);

        setAccelerators();
    }

    private void setAccelerators() {
        helpMenuItem.setAccelerator(KeyCombination.valueOf("F1"));
    }

    void fillInnerParts() {
    	//fill main activities display panel
        activityListPanel = ActivityListPanel.load(primaryStage, getActivityListPlaceholder(), logic.getFilteredActivityList());

        //fill dash board UI panel.
        overdueListPanel = OverdueTaskListPanel.load(primaryStage, getOverdueListPlaceholder(), logic.getFilteredOverdueTaskList());    
        
        //fill other components of UI.
        resultDisplay = ResultDisplay.load(primaryStage, getResultDisplayPlaceholder());
        statusBarFooter = StatusBarFooter.load(primaryStage, getStatusbarPlaceholder(), userPrefs.getDataFilePath());
        commandBox = CommandBox.load(primaryStage, getCommandBoxPlaceholder(), resultDisplay, logic);
    }

    private AnchorPane getCommandBoxPlaceholder() {
        return commandBoxPlaceholder;
    }

    private AnchorPane getStatusbarPlaceholder() {
        return statusbarPlaceholder;
    }

    private AnchorPane getResultDisplayPlaceholder() {
        return resultDisplayPlaceholder;
    }

    public AnchorPane getActivityListPlaceholder() {
        return activityListPanelPlaceholder;
    }
    
    //@@author A0125284H
    public AnchorPane getOverdueListPlaceholder() {
    	return overdueListDisplayPlaceHolder;
    }

    public void hide() {
        primaryStage.hide();
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    /**
     * Sets the default size based on user preferences.
     */
    protected void setWindowDefaultSize(UserPrefs prefs) {
        primaryStage.setHeight(prefs.getGuiSettings().getWindowHeight());
        primaryStage.setWidth(prefs.getGuiSettings().getWindowWidth());
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
            primaryStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            primaryStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }

    private void setWindowMinSize() {
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);
    }

    /**
     * Returns the current size and the position of the main Window.
     */
    public GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
    }

    @FXML
    public void handleHelp() {
        HelpWindow helpWindow = HelpWindow.load(primaryStage);
        helpWindow.show();
    }

    public void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }
    
    //@@author A0125680H
    @FXML
    public void handleSaveLoc() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(userPrefs.getDataFilePath()));
        fileChooser.setFileFilter(new FileNameExtensionFilter("XML File", "xml"));
        fileChooser.setAcceptAllFileFilterUsed(false);
        int result = fileChooser.showSaveDialog(null);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            
            if (!selectedFile.getAbsolutePath().endsWith(".xml")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".xml");
            }
            
            setSaveLocation(selectedFile.getAbsolutePath());
        }
    }
    
    //@@author A0125680H
    public void handleOpen() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(userPrefs.getDataFilePath()));
        fileChooser.setFileFilter(new FileNameExtensionFilter("XML File", "xml"));
        fileChooser.setAcceptAllFileFilterUsed(false);
        int result = fileChooser.showOpenDialog(null);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            
            if (!selectedFile.getAbsolutePath().endsWith(".xml")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".xml");
            }
            
            openFile(selectedFile);
        }
    }
    
    //@@author A0125680H
    public void setSaveLocation(String filePath) {
        resultDisplay.postMessage("New save location: " + filePath);
        changeFileLocation(filePath);
        
        EventsCenter.getInstance().post(new SaveEvent());
    }
    
    //@@author A0125680H
    public void openFile(File selectedFile) {
        if (!selectedFile.exists()) {
            resultDisplay.postMessage("The specified file doesn't exist");
        } else {
            EventsCenter.getInstance().post(new LoadLifekeeperEvent(selectedFile, logic));
            resultDisplay.postMessage("Loaded data from file: " + selectedFile.getAbsolutePath());
        }
    }
    
    //@@author A0125680H
    public void changeFileLocation(String filePath) {
        XmlAddressBookStorage.setAddressBookFilePath(filePath);
        userPrefs.setDataFilePath(filePath);
        statusBarFooter.setSaveLocation(filePath);
    }

    public ActivityListPanel getActivityListPanel() {
        return this.activityListPanel;
    }
    
    public void refresh() {
        activityListPanel.refresh();
        
    }

}
