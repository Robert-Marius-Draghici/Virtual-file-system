package fileSystem;

public class PrintVisitor implements Visitor {

	private Integer indentLevel;

	public PrintVisitor() {
		// TODO Auto-generated constructor stub
		this.indentLevel = 0;
	}

	@Override
	public void visit(File file) {
		// TODO Auto-generated method stub
		for (int i = 0; i < indentLevel; i++)
			System.out.print("\t");

		System.out.println(file.getName() + " " + file.getType()
				+ file.getPermission() + " " + file.getOwner().getName());
	}

	@Override
	public void visit(Directory directory) {
		// TODO Auto-generated method stub

		for (int i = 0; i < indentLevel; i++)
			System.out.print("\t");

		System.out.println(directory.getName() + " " + directory.getType()
				+ directory.getPermission() + " "
				+ directory.getOwner().getName());

		for (FileSystemEntity entity : directory.getChildEntities()) {
			indentLevel++;
			entity.accept(this);
			indentLevel--;
		}

	}

}
