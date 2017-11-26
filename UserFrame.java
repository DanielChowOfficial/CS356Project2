import java.awt.Container;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class UserFrame extends JFrame{

	private FollowEngine fe;
	private JScrollPane newsFeedScrollPane;
	private JScrollPane followingScrollPane;
	
	private JList<String> newsFeed;
	private DefaultListModel<String> newsFeedListModel;
	private JList<String> following;
	private DefaultListModel<String> followingModel;
	User user;
	private JTextField userID;
	private JTextField tweetMessage;
	private JLabel creationT;
	private JLabel updateT;
	private JButton followButton;
	private JButton tweetButton;

	public UserFrame(User selectedUser){
		user = selectedUser;
		fe = FollowEngine.getInstance();
	}
	public void buildFollowingModel(){
		followingModel  = new DefaultListModel<>();
		followingModel.addElement(user.getID() + "'s followings");
		Set<String> followingUsers = ((User)fe.getUsers().get(user.getID())).getFollowing().keySet();
		if(followingUsers.size() != 0){
			for(String s: followingUsers){
				
				followingModel.addElement(s);
				followingModel.removeElement(user.getID());
			}

		}
		else{
			System.out.println("Following 0");
		}
		
		following = new JList<String>(followingModel);
		
		
		
		
	}
	
	
	
	public void buildNewsFeedListModel(){
		newsFeedListModel  = new DefaultListModel<>();
		System.out.println(user.getID());
		newsFeedListModel.addElement(user.getID() + "'s News Feed");
		ArrayList<String> news = (ArrayList<String>) ((User)fe.getUsers().get(user.getID())).getNewsFeed();
		if(news.size() != 0){
			for(String s: news){
				newsFeedListModel.addElement(s);
			}

		}
		else{
			System.out.println("Following 0");
		}
		
		newsFeed = new JList<String>(newsFeedListModel);
		
		
		
	}



	public void initComponents(){

		setSize(700, 700);
		buildNewsFeedListModel();
		newsFeedScrollPane = new JScrollPane(newsFeed);
		buildFollowingModel();
		setLocationRelativeTo(null);
	
		newsFeedScrollPane.setBounds(10, 10, 260, 300);
		followingScrollPane = new JScrollPane(following);
		followingScrollPane.setBounds(300, 70, 260, 300);
		Container buttons = new Container();
		buttons.add(followingScrollPane);
		buttons.add(newsFeedScrollPane);

		userID = new JTextField();
		
		userID.setText("user to follow");
		userID.setBounds(450, 10, 100, 20);
		userID.setFont(new Font("TimesRoman", Font.PLAIN, 12));
		creationT = new JLabel();
		creationT.setText("user created at " + user.getCreationTime());
		creationT.setBounds(10, 600, 600, 20);
		creationT.setFont(new Font("TimesRoman", Font.PLAIN, 12));
		updateT = new JLabel();
		updateT.setText("last updated at " + user.getLasteUpdatedTime());
		updateT.setBounds(10, 550, 600, 20);
		updateT.setFont(new Font("TimesRoman", Font.PLAIN, 12));
		tweetMessage = new JTextField();
		tweetMessage.setFont(new Font("TimesRoman", Font.PLAIN, 12));
		tweetMessage.setText("String to tweet");
		tweetMessage.setBounds(300, 10, 100, 20);
		tweetButton = new JButton();
		tweetButton.setText("Tweet");
		tweetButton.setBounds(300, 40, 100, 20);

		followButton = new JButton();
		followButton.setText("follow");
		followButton.setBounds(450, 40, 100, 20);
		buttons.add(userID);
		buttons.add(tweetMessage);
		buttons.add(tweetButton);
		buttons.add(followButton);
		buttons.add(creationT);
		add(buttons);
		initListeners();
		setVisible(true);

	}
	public void updateNewsFeed(String s){
		DefaultListModel model = (DefaultListModel) newsFeed.getModel();
		model.addElement(s);
		newsFeed = new JList(model);	
		
	}
	public void initListeners(){

		tweetButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				System.out.println("tweetButton clicked");
				FollowEngine fe = FollowEngine.getInstance();
				fe.setLast(user.id);
				System.out.println("id set" + user.id);
				NewsFeedObserver.getInstance().update(user, tweetMessage.getText());
				for (String key : user.getFollowers().keySet()) {
					if(MainFrame.getUserFrames().containsKey(key)){
						MainFrame.getUserFrames().get(key).updateNewsFeed(tweetMessage.getText());
					}
					
					
					
				}
				
				
				
			}
		});
		
		followButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				System.out.println("followButton clicked");
				
				
				String id = userID.getText();
				if(FollowEngine.getInstance().getUsers().containsKey(id)){
					FollowEngine.getInstance().follow((User)FollowEngine.getInstance().getUsers().get(user.getID()), (User)FollowEngine.getInstance().getUsers().get(id));
				
					DefaultListModel model = (DefaultListModel) following.getModel();
					model.addElement(userID.getText());
					following = new JList(model);	
				
				}
				else{
					JOptionPane.showMessageDialog(null, "User does not exist");
				}
			}
		});

		
		
		
	}




}
