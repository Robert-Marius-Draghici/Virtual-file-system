package fileSystem;

import java.util.LinkedHashMap;

public class FileSystemErrors {

	private static FileSystemErrors INSTANCE = null;
	private LinkedHashMap<Integer, String> errorMap;

	private FileSystemErrors() {
		this.errorMap = new LinkedHashMap<Integer, String>();
		errorMap.put(1, "Is a directory");
		errorMap.put(2, "No such directory");
		errorMap.put(3, "Not a directory");
		errorMap.put(4, "No rights to read");
		errorMap.put(5, "No rights to write");
		errorMap.put(6, "No rights to execute");
		errorMap.put(7, "File already exists");
		errorMap.put(8, "User does not exist");
		errorMap.put(9, "User already exists");
		errorMap.put(10, "No rights to change user status");
		errorMap.put(11, "No such file");
		errorMap.put(12, "No such file or directory");
		errorMap.put(13, "Cannot delete parent or current directory");
		errorMap.put(14, "Non empty directory");
	}

	public static FileSystemErrors getInstance() {
		if (INSTANCE == null)
			INSTANCE = new FileSystemErrors();
		return INSTANCE;
	}

	public String getErrorMessage(String commandName, Integer errorNumber) {
		return "-" + errorNumber + ": " + commandName + ": "
				+ errorMap.get(errorNumber);

	}
}
