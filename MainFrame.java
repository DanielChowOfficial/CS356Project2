import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class MainFrame extends JFrame{
	private JTree tree;
	private Group root;
	private boolean allowsChildren;
	private User selectedUser;
	private DefaultMutableTreeNode jTreeRoot;
	private DefaultMutableTreeNode selectedNode;
	private JScrollPane treePane;
	private HashMap<String, UserComposite> all;
	private HashMap<String, DefaultMutableTreeNode> allNodes;
	private JButton showUserTotalButton;
	private JButton showGroupTotalButton;
	private JButton showTotalMessagesButton;
	private JButton checkIDButton;
	private JButton showPositivePercentageButton;
	private JButton addUserButton;
	private JButton addGroupButton;
	private JButton last;
	private JButton openUserViewButton;
	private JTextField userID;
	private JTextField groupID;
	private FollowEngine fe;
	private Group selectedGroup;
	private static HashMap<String, UserFrame> open;
	private int groupCount;
	public MainFrame(){
		groupCount = 1;
		open = new HashMap<String, UserFrame>();
		allNodes = new HashMap<String, DefaultMutableTreeNode>();  
		all = new HashMap<String, UserComposite>();
		fe = FollowEngine.getInstance();
		root = new Group("Root");
		all.put(root.getID(), root);
		fe.addUser(root);

		selectedGroup = root;
		
		jTreeRoot = new DefaultMutableTreeNode("Root");
		allNodes.put("Root", jTreeRoot);
		selectedNode = jTreeRoot;




	}

	public void initComponents(){
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);


		tree = new JTree(jTreeRoot);
		treePane = new JScrollPane(tree);
		
		treePane.setBounds(10, 10, 260, 800);
		tree.setFont(new Font("TimesRoman", Font.PLAIN, 25));
		Container buttons = new Container();



		//buttons and text fields
		userID = new JTextField();
		userID.setText("enter user id here");
		userID.setBounds(300, 20, 260, 50);
		userID.setFont(new Font("TimesRoman", Font.PLAIN, 25));
		groupID = new JTextField();
		groupID.setFont(new Font("TimesRoman", Font.PLAIN, 25));
		groupID.setText("enter group id here");
		groupID.setBounds(600, 20, 260, 50);


		addUserButton = new JButton("Add User");
		addUserButton.setFont(new Font("TimesRoman", Font.PLAIN, 25));
		addUserButton.setBounds(300, 100, 260, 70);

		addGroupButton = new JButton("Add Group");
		addGroupButton.setFont(new Font("TimesRoman", Font.PLAIN, 25));
		addGroupButton.setBounds(600, 100, 260, 70);

		openUserViewButton = new JButton("Open User View");
		openUserViewButton.setFont(new Font("TimesRoman", Font.PLAIN, 25));
		openUserViewButton.setBounds(300, 180, 560, 70);
		showUserTotalButton = new JButton("Show User Total");
		showUserTotalButton.setFont(new Font("TimesRoman", Font.PLAIN, 25));
		showUserTotalButton.setBounds(300, 260, 260, 70);

		showGroupTotalButton = new JButton("Show Group Total");
		showGroupTotalButton.setBounds(600, 260, 260, 70);
		showGroupTotalButton.setFont(new Font("TimesRoman", Font.PLAIN, 25));

		showTotalMessagesButton = new JButton("Show Total Messages");
		showTotalMessagesButton.setFont(new Font("TimesRoman", Font.PLAIN, 25));
		showTotalMessagesButton.setBounds(300, 340, 260, 70);

		showPositivePercentageButton = new JButton("Show Positive Percentage");
		showPositivePercentageButton.setBounds(600, 340, 260, 70);
		showPositivePercentageButton.setFont(new Font("TimesRoman", Font.PLAIN, 25));

		checkIDButton = new JButton("Check for Invalid ID");
		checkIDButton.setFont(new Font("TimesRoman", Font.PLAIN, 25));
		checkIDButton.setBounds(300, 420, 260, 70);
	
		last = new JButton("Get Last Updated");
		last.setFont(new Font("TimesRoman", Font.PLAIN, 25));
		last.setBounds(300, 520, 260, 70);
		
		
		buttons.add(treePane);
		buttons.add(last);
		buttons.add(checkIDButton);
		buttons.add(openUserViewButton);
		buttons.add(userID);
		buttons.add(groupID);
		buttons.add(addUserButton);
		buttons.add(addGroupButton);
		buttons.add(showUserTotalButton);
		buttons.add(showGroupTotalButton);
		buttons.add(showTotalMessagesButton);
		buttons.add(showPositivePercentageButton);

		add(buttons);
		initListeners();

	}

	public void initListeners(){
		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				treeEntryClicked(me);
			}
		});


		checkIDButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				System.out.println("checkIDButton clicked");
				JOptionPane.showMessageDialog(null, "There are no invalid ID's");


			}
		});
		
		
		last.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				System.out.println("last clicked");
				FollowEngine fe = FollowEngine.getInstance();
				JOptionPane.showMessageDialog(null, "The last updated user is : " + fe.getLast());


			}
		});
		

		addUserButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				System.out.println(allowsChildren);
				System.out.println("addUserButton clicked");
				String id = userID.getText();
				if(fe.getUsers().containsKey(id)){
					JOptionPane.showMessageDialog(null, "Error: " + id + " already exists");
				}
				else{

					System.out.println(root.getChildren().size());
					if(allowsChildren){
						User temp = new User(id);
						System.out.println(root.getChildren().size());
						fe.addUser(temp);
						all.put(id, temp);
						DefaultMutableTreeNode user = null;
						DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
						user = new DefaultMutableTreeNode(temp.getID());
						selectedNode.add(user);
						allNodes.put(temp.getID(), user);
						user.setParent(selectedNode);
						model.reload(selectedNode);

					}
					else{
						JOptionPane.showMessageDialog(null, "Group not selected");
					}
				}
			}
		});
		addGroupButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				System.out.println("addGroupButton clicked");
				String id = groupID.getText();
				if(all.containsKey(id)){
					JOptionPane.showMessageDialog(null, "Error: " + id + " already exists");
				}
				else{
					groupCount++;
					Group temp = new Group(id);

					System.out.println(root.getChildren().size());
					if(allowsChildren){
						fe.getUsers().put(id, temp);
						all.put(id, temp);
						DefaultMutableTreeNode group = null;
						DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
						group = new DefaultMutableTreeNode(id);
						group.setAllowsChildren(true);
						selectedNode.add(group);
						allNodes.put(temp.getID(), group);
						group.setParent(selectedNode);
						model.reload(selectedNode);
						treePane.repaint();
					}
					else{
						JOptionPane.showMessageDialog(null, "Group not selected");
					}
				}
			}
		});
		
		openUserViewButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {

				if(!allowsChildren){
					System.out.println("openUserViewButton clicked");
					UserFrame uf = new UserFrame(selectedUser);
					uf.initComponents();
					open.put(selectedUser.getID(),uf);
				}
				else{
					JOptionPane.showMessageDialog(null, "User not selected");
				}




			}
		});
		showUserTotalButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				System.out.println("showUserTotalButton clicked");
				JOptionPane.showMessageDialog(null, "There are " + FollowEngine.getInstance().getCount() + " users.");
			}
		});
		showGroupTotalButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				System.out.println("showGroupTotalButton clicked");
				JOptionPane.showMessageDialog(null, "There are " + groupCount + " groups.");
			}
		});
		showTotalMessagesButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				System.out.println("showTotalMessagesButton clicked");
				JOptionPane.showMessageDialog(null, "There are " + NewsFeedObserver.getInstance().getmessageCounter() + " messages.");
			}
		});
		showPositivePercentageButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				System.out.println("showPositivePercentageButton clicked");
				JOptionPane.showMessageDialog(null, "There are 0 positie tweets");
			}
		});



	}
	public static HashMap<String, UserFrame> getUserFrames(){
		return open;
	}
	public void treeEntryClicked(MouseEvent me){
		TreePath tp = tree.getPathForLocation(me.getX(), me.getY());
		try{
			System.out.println(tp.getLastPathComponent().toString());

			UserComposite t = fe.getUsers().get(tp.getLastPathComponent().toString());
			allowsChildren = fe.getUsers().get(tp.getLastPathComponent().toString()).getAllowsChildren();
			System.out.println(allowsChildren);

			if(!allowsChildren){
				User u = (User)fe.getUsers().get(tp.getLastPathComponent().toString());
				System.out.println("children selected");
				selectedUser = u;
			}

			selectedNode = allNodes.get(tp.getLastPathComponent().toString());

		}
		catch(Exception e){
			e.printStackTrace();

		}
	}
public void createSubTree(UserComposite uC, DefaultMutableTreeNode sN){
		DefaultMutableTreeNode group = null;
		DefaultMutableTreeNode user = null;
		for (String key : uC.getChildren().keySet()) {
			if(uC.getChildren().get(key).getAllowsChildren() == false){
				DefaultTreeModel model = (DefaultTreeModel) tree.getModel();



				user = new DefaultMutableTreeNode(uC.getChildren().get(key).getID());
				sN.add(user);
				model.reload(sN);
			}
			else{
				DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
				group = new DefaultMutableTreeNode(uC.getChildren().get(key).getID());
				sN.add(group);
				model.reload(sN);
				createSubTree(uC.getChildren().get(key), group);
				

			}
		}
		return;
	}


public void refreshTree(){
		selectedNode = jTreeRoot;
		Group cur = root;
		createSubTree(cur, selectedNode);
	}



	public static void main(String args[]) {

		UserComposite root = new Group("root");




		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				MainFrame mainFrame = new MainFrame();

				mainFrame.setSize(1000, 1000);
				mainFrame.setLocationRelativeTo(null);
				mainFrame.initComponents();
				mainFrame.setVisible(true);


			}
		});
	}

}
