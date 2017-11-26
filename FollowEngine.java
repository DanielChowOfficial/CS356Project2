import java.util.HashMap;
import java.util.Map;

public class FollowEngine {
	private static FollowEngine instance;
	Map<String, UserComposite> users;
	private int count;
	private String last = null;
	private int groupCount;
	private FollowEngine(){
		count = 0;
		groupCount = 0;
		users = new HashMap<String, UserComposite>();
	}
	
	public static FollowEngine getInstance(){
		if(instance == null){
			instance = new FollowEngine();
		}
		return instance;
	}
	public void setLast(String last){
		this.last = last;
	}
	public String getLast(){
		return last;
	}
	public void addUser(UserComposite temp){
		if(temp.getAllowsChildren() == false){
			count++;
		}
		else{
			groupCount++;
		}
		users.put(temp.getID(), temp);
	}
	public int getGroupCount(){
		return groupCount;
	}
	public int getCount(){
		return count;
	}
	public void follow(User id, User follower){
		users.get(id.getID()).follow(follower);
		System.out.println(id.getID() + " has followed " + follower.getID());
		users.get(follower.getID()).addFollower(id);
		
	}
	public Map<String, UserComposite> getUsers(){
		return users;
	}

}
