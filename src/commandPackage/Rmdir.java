package commandPackage;

import fileSystem.Directory;
import fileSystem.FileSystemEntity;
import fileSystem.FileSystemErrors;
import fileSystem.FileSystemTerminal;
import fileSystem.Permission;

public class Rmdir extends FileCommand {

	public Rmdir(String commandName, String commandArguments) {
		super(commandName, commandArguments);
	}

	@Override
	public void executeCommand() {
		FileSystemTerminal terminalInstance = FileSystemTerminal.getInstance();
		FileSystemErrors errorInstance = FileSystemErrors.getInstance();
		String copy = commandArguments;
		if (commandArguments.endsWith("/") && !commandArguments.equals("/"))
			copy = commandArguments.substring(0, commandArguments.length() - 1);

		if (copy.equals(".") || copy.equals("..")) {
			System.out.println(errorInstance.getErrorMessage(getCommand(), 13));
			return;
		}

		if (copy.equals("/..")) {
			System.out.println(errorInstance.getErrorMessage(getCommand(), 13));
			return;
		}

		String entityName = processArguments(copy);

		if (entityName == null)
			return;

		boolean found = false;
		Directory toRemove = null;

		for (FileSystemEntity childEntity : ((Directory) targetEntity).getChildEntities()) {
			if (childEntity.getName().equals(entityName)) {
				switch (childEntity.getType()) {
					case "f":
						System.out.println(errorInstance.getErrorMessage(getCommand(), 3));
						return;

					case "d":
						found = true;
						toRemove = (Directory) childEntity;
						break;

					default:
						break;
				}
			}
		}

		if (found) {
			FileSystemEntity auxiliary = terminalInstance.getCurrentDirectory();
			while (!auxiliary.equals(terminalInstance.getRootDirectory())) {
				if (toRemove.equals(auxiliary)) {
					System.out.println(errorInstance.getErrorMessage(getCommand(), 13));
					return;
				}
				auxiliary = auxiliary.getParent();
			}
		}

		if (found && toRemove.getChildEntities().size() != 0) {
			System.out.println(errorInstance.getErrorMessage(getCommand(), 14));
			return;
		}

		if (found && !Permission.checkPermissions(targetEntity, "w")) {
			System.out.println(errorInstance.getErrorMessage(getCommand(), 5));
			return;
		}

		if (!found) {
			System.out.println(errorInstance.getErrorMessage(getCommand(), 2));
			return;
		}

		((Directory) targetEntity).getChildEntities().remove(toRemove);
		toRemove.getOwner().getOwnedEntities().remove(toRemove);
		toRemove.setParent(null);
		toRemove = null;
	}

}
