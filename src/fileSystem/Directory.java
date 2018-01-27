package fileSystem;

import java.util.ArrayList; 
import java.util.StringTokenizer;

import commandPackage.Command;
import commandPackage.CommandFactory;

public class Directory extends FileSystemEntity {

	private ArrayList<FileSystemEntity> childEntities;

	public Directory(String name, User owner, String permission) {
		super(name, owner, permission);
		childEntities = new ArrayList<FileSystemEntity>();
		this.type = "d";
	}

	public ArrayList<FileSystemEntity> getChildEntities() {
		return childEntities;
	}

	public void setChildEntities(ArrayList<FileSystemEntity> childEntities) {
		this.childEntities = childEntities;
	}

	public static boolean checkPath(String path, Command command) {
		ArrayList<String> pathEntities = new ArrayList<String>();
		FileSystemTerminal terminalInstance = FileSystemTerminal.getInstance();
		boolean isAbsolute = false;
		boolean isValid = false;
		/*
		 * If the path starts with /, then the path is absolute.
		 */
		if ("/".equals(path.substring(0, 1))) {
			pathEntities.add("/");
			isAbsolute = true;
		}

		StringTokenizer st = new StringTokenizer(path, "/");
		/*
		 * We create an array that contains all the entities of the path.
		 */
		while (st.hasMoreTokens()) {
			pathEntities.add(st.nextToken());
		}

		FileSystemVisitor v = new FileSystemVisitor(pathEntities, command.getCommand());

		if (isAbsolute)
			v.visit(terminalInstance.getRootDirectory());
		else
			v.visit(terminalInstance.getCurrentDirectory());

		if (!v.isTargetReached() && !v.isNoExecute()) {
			if (command.getCommand().contains("ls") )
				System.out.println(
						FileSystemErrors.getInstance().getErrorMessage(command.getCommand(), 12));
			else
				System.out.println(
						FileSystemErrors.getInstance().getErrorMessage(command.getCommand(), 2));
		} else if (v.isTargetReached()) {
			isValid = true;
			command.setTargetEntity(v.getTargetEntity());
		}

		return isValid;
	}

	@Override
	public void accept(Visitor v) {
		// TODO Auto-generated method stub
		v.visit(this);
	}

	public static void main(String[] args) {
		Command command = CommandFactory.getInstance().createCommand("adduser ana");
		command.executeCommand();
		Directory.checkPath("/ana", command);
	}

}
