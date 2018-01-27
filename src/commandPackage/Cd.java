package commandPackage;

import fileSystem.Directory;
import fileSystem.FileSystemErrors;
import fileSystem.FileSystemTerminal;

public class Cd extends Command {

	public Cd(String commandName, String commandArguments) {
		super(commandName, commandArguments);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void executeCommand() {
		// TODO Auto-generated method stub
		FileSystemTerminal terminalInstance = FileSystemTerminal.getInstance();
		FileSystemErrors errorInstance = FileSystemErrors.getInstance();

		if (commandArguments.endsWith("/") && !commandArguments.equals("/"))
			commandArguments = commandArguments.substring(0, commandArguments.length() - 1);

		if (!Directory.checkPath(commandArguments, this)) {
			return;
		}

		if (targetEntity.getType().equals("f"))
			System.out.println(errorInstance.getErrorMessage(getCommand(), 3));
		else
			terminalInstance.setCurrentDirectory((Directory) targetEntity);
	}

}
