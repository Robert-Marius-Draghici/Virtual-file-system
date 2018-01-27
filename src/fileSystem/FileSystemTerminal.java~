package fileSystem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import commandPackage.Command;
import commandPackage.CommandFactory;

public class FileSystemTerminal {

	private static FileSystemTerminal INSTANCE = null;

	private User root;
	private Directory rootDirectory;
	private User activeUser;
	private ArrayList<User> registeredUsers;
	private Directory currentDirectory;

	private FileSystemTerminal() {
		root = new User("root");
		rootDirectory = new Directory("/", root, Permission.getRootDirectoryPermissions());
		rootDirectory.setParent(rootDirectory);
		root.setHomeDirectory(rootDirectory);
		activeUser = root;
		registeredUsers = new ArrayList<>();
		registeredUsers.add(root);
		currentDirectory = rootDirectory;
	}

	public static FileSystemTerminal getInstance() {
		if (INSTANCE == null)
			INSTANCE = new FileSystemTerminal();
		return INSTANCE;
	}

	public User getActiveUser() {
		return activeUser;
	}

	public void setActiveUser(User activeUser) {
		this.activeUser = activeUser;
	}

	public ArrayList<User> getRegisteredUsers() {
		return registeredUsers;
	}

	public Directory getRootDirectory() {
		return rootDirectory;
	}

	public Directory getCurrentDirectory() {
		return currentDirectory;
	}

	public void setCurrentDirectory(Directory currentDirectory) {
		this.currentDirectory = currentDirectory;
	}

	public void terminalOS(String fileName) {
		BufferedReader bufferedReader = null;
		try {
			if(fileName != null) {
				FileReader fileReader = new FileReader(fileName);
				bufferedReader = new BufferedReader(fileReader);

				String line;
				Command command;
				while ((line = bufferedReader.readLine()) != null) {
					command = CommandFactory.getInstance().createCommand(line);
					command.executeCommand();
				}
			} else {
				Scanner scanner = new Scanner(System.in);
				Command command;
				String line;
				while (!(line = scanner.nextLine()).equals("exit")) {
					command = CommandFactory.getInstance().createCommand(line);
					command.executeCommand();
				}
			}
			PrintVisitor printVisitor = new PrintVisitor();
			rootDirectory.accept(printVisitor);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
