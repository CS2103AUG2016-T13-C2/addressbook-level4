package seedu.lifekeeper.storage;

import seedu.lifekeeper.commons.events.model.LifekeeperChangedEvent;
import seedu.lifekeeper.commons.events.model.LoadLifekeeperEvent;
import seedu.lifekeeper.commons.events.storage.DataSavingExceptionEvent;
import seedu.lifekeeper.commons.exceptions.DataConversionException;
import seedu.lifekeeper.model.ReadOnlyLifeKeeper;
import seedu.lifekeeper.model.UserPrefs;

import java.io.IOException;
import java.util.Optional;

/**
 * API of the Storage component
 */
public interface Storage extends LifekeeperStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getAddressBookFilePath();
    
    void setAddressBookFilePath(String addressBookFilePath);

    @Override
    Optional<ReadOnlyLifeKeeper> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyLifeKeeper lifeKeeper) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(LifekeeperChangedEvent abce);
    
    void handleLoadLifekeeperEvent(LoadLifekeeperEvent llke) throws DataConversionException;
}
