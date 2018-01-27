package commandPackage;

import fileSystem.Directory; 
import fileSystem.FileSystemEntity;
import fileSystem.FileSystemErrors;
import fileSystem.Permission;

public class Chmod extends FileCommand {

	public Chmod(String commandName, String commandArguments) {
		super(commandName, commandArguments);
	}

	@Override
	public void executeCommand() {
		FileSystemErrors errorInstance = FileSystemErrors.getInstance();

		if (commandArguments.endsWith("/") && !commandArguments.equals("/"))
			commandArguments = commandArguments.substring(0, commandArguments.length() - 1);

		int spacePosition = commandArguments.indexOf(" ");
		int permissionCode = Integer.parseInt(commandArguments.substring(0, spacePosition));
		String path = commandArguments.substring(spacePosition + 1);
		String entityName = processArguments(path);

		if (entityName == null)
			return;

		boolean found = false;
		FileSystemEntity toChangePermission = null;

		for (FileSystemEntity childEntity : ((Directory) targetEntity).getChildEntities()) {
			if (childEntity.getName().equals(entityName)) {
				found = true;
				toChangePermission = childEntity;
			}
		}

		if (!found) {
			System.out.println(FileSystemErrors.getInstance().getErrorMessage(getCommand(), 12));
			return;
		}

		if (!Permission.checkPermissions(toChangePermission, "w")) {
			System.out.println(errorInstance.getErrorMessage(getCommand(), 5));
			return;
		}

		toChangePermission.setPermission(Permission.setPermissions(permissionCode));

	}

}
