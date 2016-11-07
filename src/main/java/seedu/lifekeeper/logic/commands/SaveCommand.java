package seedu.lifekeeper.logic.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

import seedu.lifekeeper.commons.core.EventsCenter;
import seedu.lifekeeper.commons.events.ui.SaveFileChooserEvent;
import seedu.lifekeeper.commons.util.FileUtil;
import seedu.lifekeeper.storage.XmlAddressBookStorage;

//@@author A0125680H
/**
 * Saves the Lifekeeper data to the file directory specified by the user.
 */
public class SaveCommand extends Command {
    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Allows the save file location to be specified.\n"
            + "File name cannot consist of any of the following characters: '<', '>', ':', '\"', '|', '?', '*'\n"
            + "Example: " + COMMAND_WORD + " folder" + System.getProperty("file.separator") + "file.xml";

    public static final String SAVE_MESSAGE = "New save location: %1$s";
    
    private final String savePath;

    public SaveCommand(String filePath) {
        filePath.replace("/", System.getProperty("file.separator"));
        filePath.replace("\\", System.getProperty("file.separator"));
        this.savePath = filePath;
    }

    @Override
    public CommandResult execute() {
        File saveFile = new File(savePath);
        if (FileUtil.isValidFilePath(savePath)) {
            String filePath = saveFile.getAbsolutePath();
            
            if (!filePath.endsWith(".xml")) {
                filePath += ".xml";
            }
            
            EventsCenter.getInstance().post(new SaveFileChooserEvent(filePath));
            return new CommandResult(String.format(SAVE_MESSAGE, XmlAddressBookStorage.getFilePathForSaveCommand()));
        } else {
            EventsCenter.getInstance().post(new SaveFileChooserEvent(""));
            return new CommandResult(String.format(SAVE_MESSAGE, XmlAddressBookStorage.getFilePathForSaveCommand()));
        }
    }
}
