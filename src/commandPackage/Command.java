package commandPackage;

import fileSystem.FileSystemEntity;

public abstract class Command implements ICommand {

	protected String commandName;
	protected String commandArguments;
	protected FileSystemEntity targetEntity;

	public void setTargetEntity(FileSystemEntity targetEntity) {
		this.targetEntity = targetEntity;
	}

	public Command(String commandName, String commandArguments) {
		super();
		this.commandName = commandName;
		this.commandArguments = commandArguments;
	}
	
	public String getCommand() {
		return commandName + " " + commandArguments;
	}

}
