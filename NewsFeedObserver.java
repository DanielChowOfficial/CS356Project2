import java.util.HashMap;

public class NewsFeedObserver implements Observer {
	private static NewsFeedObserver instance;
	private int messageCounter;
	
	private NewsFeedObserver(){
		messageCounter = 0;
	}
	
	public static NewsFeedObserver getInstance(){
		if(instance == null){
			instance = new NewsFeedObserver();
		}
		return instance;
	}
	public int getmessageCounter(){
		return messageCounter;
	}
	
	
	@Override
	public void update(User user, String tweet) {
		messageCounter++;
		for (String key : user.getFollowers().keySet()) {
			
			user.getFollowers().get(key).updateNewsFeed(tweet);
					   	
		}
		

	}

	

}
