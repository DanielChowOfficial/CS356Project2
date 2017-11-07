import java.util.Iterator;

public class Tester {

	public static void main(String[] args) {
		UserComposite root = new Group("root");
		FollowEngine fe = FollowEngine.getInstance();
		User dan = new User("daniel");
		User bob = new User("bob");
		User aly = new User("aly");
		root.add("daniel", dan);
		root.add("bob", bob);
		root.add("aly", aly);
		fe.addUser(dan);
		fe.addUser(bob);
		fe.addUser(aly);
		fe.follow(dan, bob);
		fe.follow(dan, aly);
		bob.tweet("tweet from bob");
		aly.tweet("tweet from aly");
		bob.tweet("another tweet form bob");
		dan.tweet("tweet form dan");
		dan.printFeed();
		
		
	
		


		
	}

}
