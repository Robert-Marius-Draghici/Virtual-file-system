import fileSystem.FileSystemTerminal;

public class Test {

	public static void main(String[] args) {
		if(args.length > 0)
			FileSystemTerminal.getInstance().terminalOS(args[0]);
		else
			FileSystemTerminal.getInstance().terminalOS(null);
	}
}
