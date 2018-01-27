package commandPackage;

import fileSystem.Directory; 
import fileSystem.File;
import fileSystem.FileSystemEntity;
import fileSystem.FileSystemErrors;
import fileSystem.Permission;

public class Cat extends FileCommand {

	public Cat(String commandName, String commandArguments) {
		super(commandName, commandArguments);
	}

	@Override
	public void executeCommand() {
		FileSystemErrors errorInstance = FileSystemErrors.getInstance();		

		if (commandArguments.endsWith("/") && !commandArguments.equals("/"))
			commandArguments = commandArguments.substring(0, commandArguments.length() - 1);

		String entityName = processArguments(commandArguments);

		if (entityName == null)
			return;
		
		boolean found = false;
		File toRead = null;

		for (FileSystemEntity childEntity : ((Directory) targetEntity).getChildEntities()) {
			if (childEntity.getName().equals(entityName)) {
				switch (childEntity.getType()) {
					case "f":
						toRead = (File) childEntity;
						found = true;
						break;

					case "d":
						System.out.println(errorInstance.getErrorMessage(getCommand(), 1));
						return;
					default:
						break;
				}
			}
		}

		if (!found) {
			System.out.println(errorInstance.getErrorMessage(getCommand(), 11));
			return;
		}

		if (!Permission.checkPermissions(toRead, "r")) {
			System.out.println(errorInstance.getErrorMessage(getCommand(), 4));
			return;
		}

		System.out.println(((File) toRead).getContent());
	}

}
