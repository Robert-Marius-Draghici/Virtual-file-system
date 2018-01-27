package fileSystem;

import commandPackage.Command;
import commandPackage.CommandFactory;

public class Permission {

	private static String rootDirectoryPermissions = new String("rwxr-x");
	private static String implicitPermissions = new String("rwx---");

	public static String getImplicitPermissions() {
		return implicitPermissions;
	}

	public static void setImplicitPermissions(String implicitPermissions) {
		Permission.implicitPermissions = implicitPermissions;
	}

	public static String getRootDirectoryPermissions() {
		return rootDirectoryPermissions;
	}

	public static void setRootDirectoryPermissions(String rootDirectoryPermissions) {
		Permission.rootDirectoryPermissions = rootDirectoryPermissions;
	}

	public static boolean checkPermissions(FileSystemEntity entity, String toCheck) {
		boolean areValid = false;
		FileSystemTerminal terminalInstance = FileSystemTerminal.getInstance();
		User activeUser = terminalInstance.getActiveUser();
		String ownerPermissions = entity.getPermission().substring(0, 3);
		String othersPermissions = entity.getPermission().substring(3);
		
		if (activeUser.getName().equals("root"))
			areValid = true;
		else {
			if (entity.getOwner().equals(activeUser)) {
				if (ownerPermissions.contains(toCheck))
					areValid = true;
			} else if (othersPermissions.contains(toCheck))
				areValid = true;
		}

		return areValid;
	}

	public static String setPermissions(Integer newPermissionCode) {
		/*
		 * We calculate the binary representation of the permission code.
		 */
		String ownerPermission = String
				.format("%3s", Integer.toBinaryString(newPermissionCode / 10)).replace(" ", "0");
		String othersPermission = String
				.format("%3s", Integer.toBinaryString(newPermissionCode % 10)).replace(" ", "0");

		/*
		 * We replace the binary digits with the corresponding permissions.
		 */
		char[] permissions = { 'r', 'w', 'x' };
		char[] ownerArray = ownerPermission.toCharArray();
		char[] othersArray = othersPermission.toCharArray();

		for (int i = 0; i < ownerPermission.length(); i++)
			if (ownerArray[i] == '1')
				ownerArray[i] = permissions[i];
			else
				ownerArray[i] = '-';

		for (int i = 0; i < othersPermission.length(); i++)
			if (othersArray[i] == '1')
				othersArray[i] = permissions[i];
			else
				othersArray[i] = '-';

		return new String(ownerArray).concat(new String(othersArray));
	}

}
