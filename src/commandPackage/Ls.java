package commandPackage;

import fileSystem.Directory; 
import fileSystem.FileSystemEntity;
import fileSystem.FileSystemErrors;
import fileSystem.Permission;

public class Ls extends Command {

	public Ls(String commandName, String commandArguments) {
		super(commandName, commandArguments);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void executeCommand() {
		// TODO Auto-generated method stub
		FileSystemErrors errorInstance = FileSystemErrors.getInstance();
		
		if(commandArguments.endsWith("/") && !commandArguments.equals("/"))
			commandArguments = commandArguments.substring(0,commandArguments.length() - 1);

		if (!Directory.checkPath(commandArguments, this)) {
			return;
		}

		if (!Permission.checkPermissions(targetEntity, "r")) {
			System.out.println(errorInstance.getErrorMessage(getCommand(), 4));
			return;
		}

		switch (targetEntity.getType()) {
			case "f":
				System.out.println(targetEntity.getName() + " " + targetEntity.getType()
						+ targetEntity.getPermission() + " " + targetEntity.getOwner().getName());
				break;

			case "d":
				for (FileSystemEntity entity : ((Directory) targetEntity).getChildEntities())
					System.out.println(entity.getName() + " " + entity.getType()
							+ entity.getPermission() + " " + entity.getOwner().getName());
				break;
			default:
				break;
		}

	}

}
