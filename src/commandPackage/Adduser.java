package commandPackage;

import fileSystem.Directory;
import fileSystem.FileSystemErrors;
import fileSystem.FileSystemTerminal;
import fileSystem.Permission;
import fileSystem.User;

public class Adduser extends Command {

	public Adduser(String commandName, String commandArguments) {
		super(commandName, commandArguments);
	}

	@Override
	public void executeCommand() {
		FileSystemTerminal terminalInstance = FileSystemTerminal.getInstance();
		FileSystemErrors errorInstance = FileSystemErrors.getInstance();

		String username = commandArguments;
		String activeUserName = terminalInstance.getActiveUser().getName();

		/*
		 * Only root can add new users.
		 */
		if (!activeUserName.equals("root")) {
			System.out.println(errorInstance.getErrorMessage(getCommand(), 10));
			return;
		}

		/*
		 * We cannot add an existing user.
		 */
		for (User user : terminalInstance.getRegisteredUsers())
			if (user.getName().equals(username)) {
				System.out.println(errorInstance.getErrorMessage(getCommand(), 9));
				return;
			}

		User newUser = new User(username);
		Directory homeDirectory = new Directory(username, newUser,
				Permission.getImplicitPermissions());
		homeDirectory.setParent(terminalInstance.getRootDirectory());
		newUser.setHomeDirectory(homeDirectory);
		newUser.getOwnedEntities().add(homeDirectory);
		terminalInstance.getRegisteredUsers().add(newUser);
		terminalInstance.getRootDirectory().getChildEntities().add(homeDirectory);
	}

}
