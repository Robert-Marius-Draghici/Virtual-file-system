package commandPackage;

import fileSystem.FileSystemErrors;
import fileSystem.FileSystemTerminal;
import fileSystem.User;

public class Chuser extends Command {

	public Chuser(String commandName, String commandArguments) {
		super(commandName, commandArguments);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void executeCommand() {
		// TODO Auto-generated method stub
		FileSystemTerminal terminalInstance = FileSystemTerminal.getInstance();
		FileSystemErrors errorInstance = FileSystemErrors.getInstance();

		String username = commandArguments;
		User toChange = null;
		Boolean existsUser = false;
		
		for (User user : terminalInstance.getRegisteredUsers())
			if (user.getName().equals(username)) {
				existsUser = true;
				toChange = user;
			}

		if (!existsUser) {
			System.out.println(errorInstance.getErrorMessage(getCommand(), 8));
			return;
		}
		
		terminalInstance.setActiveUser(toChange);
		terminalInstance.setCurrentDirectory(toChange.getHomeDirectory());
	}

}
