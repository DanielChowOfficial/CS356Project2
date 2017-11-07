import java.util.HashMap;

import javax.swing.tree.DefaultMutableTreeNode;

public abstract class UserComposite extends DefaultMutableTreeNode {
	protected HashMap<String, UserComposite> children;
	protected String id;
	private Group parent;
	public HashMap<String, UserComposite> getChildren(){
		return children;
	}
	public String getID() {
		// TODO Auto-generated method stub
		return id;
	}
	public void setID(String id) {
		this.id = id;
	}
	
	
	public void setParent(Group g) {
		parent = g;
		
	}
	public Group getParent() {
		return parent;
	}

	public void follow(User follower) {
		// TODO Auto-generated method stub
		
	}

	public void addFollower(User id2) {
		// TODO Auto-generated method stub
		
	}
	public DefaultMutableTreeNode getTree(){
		return new DefaultMutableTreeNode(this);
	}
	public void add(String id, UserComposite child) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
