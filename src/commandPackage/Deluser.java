package commandPackage;

import fileSystem.FileSystemEntity;
import fileSystem.FileSystemErrors;
import fileSystem.FileSystemTerminal;
import fileSystem.User;

public class Deluser extends Command {

	public Deluser(String commandName, String commandArguments) {
		super(commandName, commandArguments);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void executeCommand() {
		// TODO Auto-generated method stub
		FileSystemTerminal terminalInstance = FileSystemTerminal.getInstance();
		FileSystemErrors errorInstance = FileSystemErrors.getInstance();

		String username = commandArguments;
		User toBeDeleted = null;
		String activeUserName = terminalInstance.getActiveUser().getName();
		Boolean existsUser = false;

		/*
		 * Only root can delete existing users.
		 */
		if (!activeUserName.equals("root")) {
			System.out.println(errorInstance.getErrorMessage(getCommand(), 10));
			return;
		}

		for (User user : terminalInstance.getRegisteredUsers())
			if (user.getName().equals(username)) {
				existsUser = true;
				toBeDeleted = user;
			}

		if (!existsUser) {
			System.out.println(errorInstance.getErrorMessage(getCommand(), 8));
			return;
		}

		/*
		 * The entities owned by the user that will be deleted must change
		 * their owner. The new owner will be the first user added by root.
		 */
		for (FileSystemEntity entity : toBeDeleted.getOwnedEntities()) {
			entity.setOwner(terminalInstance.getRegisteredUsers().get(1));
		}
		
		terminalInstance.getRegisteredUsers().remove(toBeDeleted);
		toBeDeleted = null;
	}

}
