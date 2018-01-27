package commandPackage;

import fileSystem.Directory;
import fileSystem.FileSystemErrors;
import fileSystem.FileSystemTerminal;

public abstract class FileCommand extends Command {

	public FileCommand(String commandName, String commandArguments) {
		super(commandName, commandArguments);
	}
	
	protected String processArguments(String commandArguments) {
		FileSystemTerminal terminalInstance = FileSystemTerminal.getInstance();
		FileSystemErrors errorInstance = FileSystemErrors.getInstance();
		
		int lastDelimiter = commandArguments.lastIndexOf('/');
		boolean shortPath = false;		
		String pathToCheck = null;
		String entityName = null;		

		if (!commandArguments.contains("/")) {
			targetEntity = terminalInstance.getCurrentDirectory();
			entityName = commandArguments;
			shortPath = true;
		}

		if (!shortPath) {
			pathToCheck = commandArguments.substring(0, lastDelimiter);
			entityName = commandArguments.substring(lastDelimiter + 1);
		}

		if (pathToCheck != null && pathToCheck.isEmpty())
			pathToCheck = "/";

		if (!shortPath && !Directory.checkPath(pathToCheck, this)) {
			return null;
		}
		
		if(targetEntity.getType().equals("f")) {
			System.out.println(errorInstance.getErrorMessage(getCommand(), 3));
			return null;
		}
		
		return entityName;
	}

}
