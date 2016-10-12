package seedu.address.logic.commands;

public class ChangeDirectoryCommand extends Command {

    public static final String MESSAGE_SUCCESS = "Storage directory changed to: %1$s";
    public static final String MESSAGE_DUPLICATE_DIRECTORY = "This directory already exists in the Lifekeeper";
	
	public ChangeDirectoryCommand() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public CommandResult execute(String newDirectory) {
		// TODO Auto-generated method stub
		return null;
	}
}


