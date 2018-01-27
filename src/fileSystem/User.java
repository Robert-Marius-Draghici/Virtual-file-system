package fileSystem;

import java.util.ArrayList;

public class User {

	private String name;
	private Directory homeDirectory;
	private ArrayList<FileSystemEntity> ownedEntities;

	public User(String name) {
		this.name = name;
		this.ownedEntities = new ArrayList<FileSystemEntity>();
	}

	public User(String name, Directory homeDirectory) {
		this.name = name;
		this.homeDirectory = homeDirectory;
		this.ownedEntities = new ArrayList<FileSystemEntity>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Directory getHomeDirectory() {
		return homeDirectory;
	}

	public void setHomeDirectory(Directory homeDirectory) {
		this.homeDirectory = homeDirectory;
	}

	public ArrayList<FileSystemEntity> getOwnedEntities() {
		return ownedEntities;
	}

	public void setOwnedEntities(ArrayList<FileSystemEntity> ownedEntities) {
		this.ownedEntities = ownedEntities;
	}
}
