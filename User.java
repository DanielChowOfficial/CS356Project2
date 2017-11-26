import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User extends UserComposite{
	
	private HashMap<String,User> followers;
	private HashMap<String,User> following;
	private List<String> newsFeed;
	private boolean inGroup;
	private boolean allowsChildren;
	public User(String id){
		followers = new HashMap<String,User>();
		following = new HashMap<String,User>();
 		followers.put(id, this);
  		following.put(id, this);
		this.id = id;
		inGroup = false;
		newsFeed = new ArrayList<String>();
		allowsChildren = false;
		creationTime = System.currentTimeMillis();
		lastUpdate = System.currentTimeMillis();
		
		
	}
	public List<String> getNewsFeed(){
		return newsFeed;
	}
	public HashMap<String,User> getFollowers(){
		return followers;
	}
	protected void tweet(String twt){
		Observer nfo = NewsFeedObserver.getInstance();
		nfo.update(this, twt);
		lastUpdate = System.currentTimeMillis();
		FollowEngine fe = FollowEngine.getInstance();
		fe.setLast(this.id);
		System.out.println("id set" + this.id);

		
	}
	public void setInGroup(boolean b){
		inGroup = b;
	}
	protected void printFollowing(){
		  
		for (String key : following.keySet()) {
		    System.out.println(key);
		}
	}
	
	
	protected void printFeed(){
		
	
		for(int i = 0; i < newsFeed.size(); i++){
			System.out.println(newsFeed.get(i));

		}
	}
	protected void updateNewsFeed(String tweet){
		newsFeed.add(tweet);
	}
	@Override
	public void addFollower(User followerUser){
		if(followers.containsKey(followerUser.getID())){
			
		}
		else{
			followers.put(followerUser.getID(), followerUser);
		}
	}
	@Override
	public void follow(User followingUser){
		if(following.containsKey(followingUser.getID())){

		}
		else{
			following.put(followingUser.getID(), followingUser);
		}
	}
	@Override
	public String getID() {
		return id;
	}
	
	@Override
	public boolean getAllowsChildren() {
		
		return allowsChildren;
	}
	public HashMap<String, User> getFollowing() {
		// TODO Auto-generated method stub
		return following;
	}
	


}
