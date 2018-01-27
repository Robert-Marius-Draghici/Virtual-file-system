package commandPackage;

import fileSystem.Directory;
import fileSystem.FileSystemEntity;
import fileSystem.FileSystemErrors;
import fileSystem.FileSystemTerminal;
import fileSystem.Permission;

public class Rm extends FileCommand {

	public Rm(String commandName, String commandArguments) {
		super(commandName, commandArguments);
	}

	@Override
	public void executeCommand() {
		FileSystemTerminal terminalInstance = FileSystemTerminal.getInstance();
		FileSystemErrors errorInstance = FileSystemErrors.getInstance();
		String recursive = null;
		String path = null;
		
		if(commandArguments.endsWith("/") && !commandArguments.equals("-r /"))
			commandArguments = commandArguments.substring(0,commandArguments.length() - 1);

		if (commandArguments.contains("-r ")) {
			int spacePosition = commandArguments.indexOf(" ");
			recursive = commandArguments.substring(0, spacePosition);
			path = commandArguments.substring(spacePosition + 1);			
		} else {
			path = commandArguments;
		}

		if (recursive != null && (path.equals(".") || path.equals("..") || path.equals("/"))) {
			System.out.println(errorInstance.getErrorMessage(getCommand(), 13));
			return;
		}

		String entityName = processArguments(path);

		if (entityName == null)
			return;

		boolean found = false;
		FileSystemEntity toRemove = null;

		for (FileSystemEntity childEntity : ((Directory) targetEntity).getChildEntities()) {
			if (childEntity.getName().equals(entityName)) {
				switch (childEntity.getType()) {
					case "f":
						found = true;
						toRemove = childEntity;
						break;

					case "d":
						if (recursive == null) {
							System.out.println(errorInstance.getErrorMessage(getCommand(), 1));
							return;
						} else
							found = true;
						toRemove = childEntity;
						break;

					default:
						break;
				}
			}
		}

		if (recursive == null && !found) {
			System.out.println(errorInstance.getErrorMessage(getCommand(), 11));
			return;
		}

		if (recursive != null && found) {

			FileSystemEntity auxiliary = terminalInstance.getCurrentDirectory();
			while (!auxiliary.equals(terminalInstance.getRootDirectory())) {
				if (toRemove.equals(auxiliary)) {
					System.out.println(errorInstance.getErrorMessage(getCommand(), 13));
					return;
				}
				auxiliary = auxiliary.getParent();
			}
		}

		if (recursive != null && !found) {
			System.out.println(errorInstance.getErrorMessage(getCommand(), 12));
			return;
		}

		if (!Permission.checkPermissions(targetEntity, "w")) {
			System.out.println(errorInstance.getErrorMessage(getCommand(), 5));
			return;
		}

		((Directory) targetEntity).getChildEntities().remove(toRemove);
		toRemove.getOwner().getOwnedEntities().remove(toRemove);
		toRemove.setParent(null);
		toRemove = null;

	}

}
