package commandPackage;

public class CommandFactory {

	private static CommandFactory INSTANCE = null;

	private CommandFactory() {
	}

	public static CommandFactory getInstance() {
		if (INSTANCE == null)
			INSTANCE = new CommandFactory();
		return INSTANCE;
	}

	public Command createCommand(String line) {
		Integer spacePosition = line.indexOf(" ");
		String commandName = line.substring(0, spacePosition);
		String commandArguments = line.substring(spacePosition + 1);
		switch (commandName) {
			case "adduser":
				return new Adduser(commandName, commandArguments);
			case "deluser":
				return new Deluser(commandName, commandArguments);
			case "chuser":
				return new Chuser(commandName, commandArguments);
			case "cd":
				return new Cd(commandName, commandArguments);
			case "mkdir":
				return new Mkdir(commandName, commandArguments);
			case "ls":
				return new Ls(commandName, commandArguments);
			case "chmod":
				return new Chmod(commandName, commandArguments);
			case "touch":
				return new Touch(commandName, commandArguments);
			case "rm":
				return new Rm(commandName, commandArguments);
			case "rmdir":
				return new Rmdir(commandName, commandArguments);
			case "writetofile":
				return new Writetofile(commandName, commandArguments);
			case "cat":
				return new Cat(commandName, commandArguments);
			default:
				throw new IllegalArgumentException("Ati introdus o comanda gresita!");
		}
	}

}
