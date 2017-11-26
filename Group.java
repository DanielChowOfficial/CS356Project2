import java.util.HashMap;

public class Group extends UserComposite{
	protected HashMap<String, UserComposite> children;
	boolean allowsChildren = true;
	public Group(String id){
		 children = new HashMap<String, UserComposite>();
		 creationTime = System.currentTimeMillis();
		this.id = id;
		
	}
	@Override
	public void add(String id, UserComposite child) {
		
		children.put(id, child);
		
	}
	
	
	public HashMap<String, UserComposite> getChildren(){
		return children;
	}
	@Override
	public boolean getAllowsChildren() {
		
		return allowsChildren;
	}


}
