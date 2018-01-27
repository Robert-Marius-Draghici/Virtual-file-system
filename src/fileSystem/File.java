package fileSystem;

public class File extends FileSystemEntity {
	
	private String content;

	public File(String name, User owner, String permission) {
		super(name, owner, permission);
		// TODO Auto-generated constructor stub
		this.type = "f";
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public void accept(Visitor v) {
		// TODO Auto-generated method stub
		v.visit(this);
	}

}