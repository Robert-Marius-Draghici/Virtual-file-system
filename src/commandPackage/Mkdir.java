package commandPackage;

import fileSystem.Directory; 
import fileSystem.FileSystemEntity;
import fileSystem.FileSystemErrors;
import fileSystem.FileSystemTerminal;
import fileSystem.Permission;
import fileSystem.User;

public class Mkdir extends FileCommand {

	private static int attempts = 0;

	public Mkdir(String commandName, String commandArguments) {
		super(commandName, commandArguments);
	}

	@Override
	public void executeCommand() {
		FileSystemTerminal terminalInstance = FileSystemTerminal.getInstance();
		FileSystemErrors errorInstance = FileSystemErrors.getInstance();
		
		if (commandArguments.equals("/") && attempts == 0) {
			attempts++;
			return;
		} else if (commandArguments.equals("/")) {
			System.out.println(errorInstance.getErrorMessage(getCommand(), 1));
			return;
		}

		if(commandArguments.endsWith("/"))
			commandArguments = commandArguments.substring(0,commandArguments.length() - 1);
		
		String entityName = processArguments(commandArguments);

		if (entityName == null)
			return;
		
		boolean existsAlready = false;
		
		for (FileSystemEntity childEntity : ((Directory) targetEntity).getChildEntities()) {
			if (childEntity.getName().equals(entityName)) {
				switch (childEntity.getType()) {
					case "f":
						System.out.println(errorInstance.getErrorMessage(getCommand(), 3));
						existsAlready = true;
						break;

					case "d":
						System.out.println(errorInstance.getErrorMessage(getCommand(), 1));
						existsAlready = true;
						break;
				}
			}
		}

		if (!Permission.checkPermissions(targetEntity, "w")) {
			System.out.println(errorInstance.getErrorMessage(getCommand(), 5));
			return;
		}

		if (existsAlready)
			return;

		User activeUser = terminalInstance.getActiveUser();
		Directory newDirectory = new Directory(entityName, activeUser,
				Permission.getImplicitPermissions());
		newDirectory.setParent((Directory) targetEntity);
		((Directory) targetEntity).getChildEntities().add(newDirectory);
		activeUser.getOwnedEntities().add(newDirectory);

	}
}
