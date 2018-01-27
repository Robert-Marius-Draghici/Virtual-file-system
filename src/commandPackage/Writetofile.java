package commandPackage;

import fileSystem.Directory; 
import fileSystem.File;
import fileSystem.FileSystemEntity;
import fileSystem.FileSystemErrors;
import fileSystem.Permission;

public class Writetofile extends FileCommand {

	public Writetofile(String commandName, String commandArguments) {
		super(commandName, commandArguments);
	}

	@Override
	public void executeCommand() {
		FileSystemErrors errorInstance = FileSystemErrors.getInstance();		

		if(commandArguments.endsWith("/") && !commandArguments.equals("/"))
			commandArguments = commandArguments.substring(0,commandArguments.length() - 1);

		int spacePosition = commandArguments.indexOf(" ");
		String path = commandArguments.substring(0, spacePosition);
		String content = commandArguments.substring(spacePosition + 1);
		String entityName = processArguments(path);

		if (entityName == null)
			return;
		
		File toWrite = null;
		boolean found = false;

		for (FileSystemEntity childEntity : ((Directory) targetEntity).getChildEntities()) {
			if (childEntity.getName().equals(entityName)) {
				switch (childEntity.getType()) {
					case "f":
						toWrite = (File) childEntity;
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

		if (!Permission.checkPermissions(toWrite, "w")) {
			System.out.println(errorInstance.getErrorMessage(getCommand(), 5));
			return;
		}

		((File) toWrite).setContent(content);
	}

}
