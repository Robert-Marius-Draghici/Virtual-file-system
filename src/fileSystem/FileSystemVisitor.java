package fileSystem;

import java.util.ArrayList;

public class FileSystemVisitor implements Visitor {

	private FileSystemErrors errorInstance = FileSystemErrors.getInstance();
	private int index;
	private ArrayList<String> pathEntities;
	private String target;
	private String command;
	private boolean targetReached;
	private FileSystemEntity targetEntity;
	private boolean noExecute = false;

	public FileSystemVisitor(ArrayList<String> pathEntities, String command) {
		this.pathEntities = pathEntities;
		this.index = 0;
		this.target = pathEntities.get(pathEntities.size() - 1);
		this.command = command;
		this.targetReached = false;
		this.targetEntity = null;
	}

	public boolean isNoExecute() {
		return noExecute;
	}

	public boolean isTargetReached() {
		return targetReached;
	}

	public void setTargetReached(boolean targetReached) {
		this.targetReached = targetReached;
	}

	public FileSystemEntity getTargetEntity() {
		return targetEntity;
	}

	public void setTargetEntity(FileSystemEntity targetEntity) {
		this.targetEntity = targetEntity;
	}

	@Override
	public void visit(File file) {
		if (file.getName().equals(target)) {
			this.targetReached = true;
			this.targetEntity = file;
		} else {
			System.out.println(errorInstance.getErrorMessage(command, 3));
		}

	}

	private void auxiliaryCheck(Directory directory) {
		/*
		 * If we reach a leaf directory, and the next element in the path is
		 * .(current directory) or .. (parent directory) we have to return to
		 * the parent entities.
		 */
		if (pathEntities.get(index).equals(".")) {
			index++;
			directory.accept(this);
			index--;
		} else if (pathEntities.get(index).equals("..")) {
			index++;
			if (Permission.checkPermissions(directory.getParent(), "x"))
				directory.getParent().accept(this);
			else {
				System.out.println(errorInstance.getErrorMessage(command, 6));
				noExecute = true;
				return;
			}
			index--;
		}
	}

	@Override
	public void visit(Directory directory) {
		/*
		 * The index indicates the current file system entity in the path from
		 * source to target. If the path is absolute, then the index 0 indicates
		 * the current directory(root) and it must be incremented in order to
		 * indicate the next entity.
		 */
		if (directory.getName().equals("/") && index == 0 && pathEntities.get(0).equals("/"))
			index++;

		/*
		 * If the index is equal to the size of the path, then we have examined
		 * all the entities on that path.
		 */
		if (index == pathEntities.size()) {
			targetReached = true;
			this.targetEntity = directory;
		} else if (directory.getChildEntities().size() == 0) {
			auxiliaryCheck(directory);
		} else {
			/*
			 * If the current directory is not an empty one, then we search the
			 * next element from the path in its child entities.
			 */
			auxiliaryCheck(directory);
			
			if(noExecute)
				return;
			
			for (FileSystemEntity entity : directory.getChildEntities()) {
				if (entity.getName().equals(pathEntities.get(index))) {
					index++;
					if (entity.getType().equals("d")) {
						if (Permission.checkPermissions(entity, "x")) {
							entity.accept(this);

						} else {
							System.out.println(errorInstance.getErrorMessage(command, 6));
							noExecute = true;
							return;
						}

					} else
						entity.accept(this);
					index--;

				}
			}

		}
	}

}
