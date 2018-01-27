package commandPackage;

import fileSystem.Directory;
import fileSystem.File;
import fileSystem.FileSystemEntity;
import fileSystem.FileSystemErrors;
import fileSystem.FileSystemTerminal;
import fileSystem.Permission;
import fileSystem.User;

public class Touch extends FileCommand{

	public Touch(String commandName, String commandArguments) {
		super(commandName, commandArguments);
	}

	@Override
	public void executeCommand() {
		FileSystemTerminal terminalInstance = FileSystemTerminal.getInstance();
		FileSystemErrors errorInstance = FileSystemErrors.getInstance();
		
		if(commandArguments.endsWith("/") && !commandArguments.equals("/"))
			commandArguments = commandArguments.substring(0,commandArguments.length() - 1);
		
		String entityName = processArguments(commandArguments);

		if (entityName == null)
			return;
		
		if (!Permission.checkPermissions(targetEntity, "w")) {
			System.out.println(errorInstance.getErrorMessage(getCommand(), 5));
			return;
		}
		
		boolean existsAlready = false;
		
		for (FileSystemEntity childEntity : ((Directory) targetEntity).getChildEntities()) {
			if (childEntity.getName().equals(entityName)) {
				switch (childEntity.getType()) {
					case "f":
						System.out.println(errorInstance.getErrorMessage(getCommand(), 7));
						existsAlready = true;
						break;

					case "d":
						System.out.println(errorInstance.getErrorMessage(getCommand(), 1));
						existsAlready = true;
						break;

					default:
						break;
				}
			}
		}		

		if (existsAlready)
			return;

		User activeUser = terminalInstance.getActiveUser();
		File newFile = new File(entityName,activeUser,Permission.getImplicitPermissions());
		newFile.setParent((Directory) targetEntity);
		((Directory) targetEntity).getChildEntities().add(newFile);
		activeUser.getOwnedEntities().add(newFile);		
	}
}
