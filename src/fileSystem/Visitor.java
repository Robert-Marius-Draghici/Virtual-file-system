package fileSystem;

public interface Visitor {
	public void visit(File file);
	public void visit(Directory directory);
}
