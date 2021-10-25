// File: NightClubMgmtApp.java
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * This abstract class extends from {@link javax.swing.JFrame}.
 * The class provides a user interface which will be able to communicate in a 
 * user friendly manner.
 * <br> This Class holds an {@link java.util.ArrayList}&lt;{@link ClubAbstractEntity}&gt;
 * which provides a window with {@link javax.swing.JTextField} so the user can write
 * the clubbers info.
 * <br> This class holds the inner class {@link ButtonsHandler} which listens
 * to the {@link javax.swing.JButton} searchButton, showListButton, clubberButton
 * and deleteButton.
 * <br> The {@link javax.swing.JRadioButton} personRadioButton, soldierRadioButton
 * and studentRadioButton are set to a button group which provide the user a way
 * to choose what type of clubber they would like to create.
 * <br> The class has an instance called overlayPanel which is a 
 * {@link ImagePanel} that is set to a {@link java.awt.GridLayout}.
 * <br> There is an instance called self which holds the 'this' of the class
 * which will be helpful to window placements.
 * @author Lior Sabri, Ben Biton
 */
public class NightClubMgmtApp extends JFrame
{
	// Night-Club Regular Customer Repository.
	private ArrayList<ClubAbstractEntity> clubbers;
	
	// JButtons which provide the same functionality as their name.
	private JButton searchButton;
	private JButton showListButton;
	private JButton clubberButton; 
	private JButton deleteButton;
	
	// JRadioButtons which help choose the type of the clubber.
	private JRadioButton personRadioButton;
	private JRadioButton soldierRadioButton;
	private JRadioButton studentRadioButton;
	
	// Main JPanel that holds other GUI elements.
	private ImagePanel overlayPanel;
	
	// Holds the 'this' of the class.
	private JFrame self;
	
	// It's a good practice to save a reference to events/listeners that are in
	// the memory to prevent memory leak.
	private ButtonsHandler btnHandler;
	
	/**
	 * NightClubMgmtApp Constructor - initializes the {@link javax.swing.JFrame}
	 * and places it in the center of the screen with 
	 * {@link java.awt.Window#setLocationRelativeTo} with sending null, when null
	 * is sent location will be set to the center of the screen.
	 * <br> Initializes {@link javax.swing.JRadioButton} into a 
	 * {@link javax.swing.ButtonGroup} with the method {@link #createButtonPanel}.
	 * <br> Initializes {@link javax.swing.JButton} and {@link ButtonsHandler}
	 * with the method {@link #createButtonPanel}.
	 * <br> Initializes the {@link java.util.ArrayList}&lt;{@link ClubAbstractEntity}&gt;
	 * and read from file with method {@link #loadClubbersDBFromFile} and when 
	 * closing the program there is a {@link java.awt.event.WindowListener} which
	 * Overrides the method {@link java.awt.event.WindowAdapter#windowClosing}
	 * which when invoked it will write the clubbers data to a file with the 
	 * method {@link #writeClubbersDBtoFile}.
	 */
	public NightClubMgmtApp()
	{
		super("Night Club Management App");
		
		self = this;
		
		// Create an image icon and get the background image.
		overlayPanel = new ImagePanel(new ImageIcon(getClass().
			getResource("background.jpg")).getImage());
		
		Color skinColor = new Color(170, 105, 100);
		Color greyBlue = new Color(125, 145, 180);
		
		// Create a LineBorder and TitleBorder for a nice looking menu.
		LineBorder bkLineBorder = new LineBorder(greyBlue, 1);
		TitledBorder bkTitledBorder = new TitledBorder(bkLineBorder, "B.K",
			TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION,
			new Font(Font.SERIF, Font.BOLD + Font.ITALIC, 40), greyBlue);
		
		overlayPanel.setBorder(bkTitledBorder);
		add(overlayPanel);
		
		// Add an underline to the JLabel and set the font.
		JLabel radioTitleLabel = new JLabel("<HTML><U>Clubber Type:</U></HTML>", 
											SwingConstants.CENTER);
		radioTitleLabel.setFont(new Font(Font.SERIF, Font.BOLD + Font.ITALIC, 20));
		radioTitleLabel.setForeground(skinColor);
		overlayPanel.add(radioTitleLabel);
		
		createRadioButtonPanel();
		
		// Add an underline to the JLabel and set the font.
		JLabel buttonsTitleLabel = new JLabel("<HTML><U>Clubbers Controls:</U></HTML>", 
											  SwingConstants.CENTER);
		buttonsTitleLabel.setFont(new Font(Font.SERIF, Font.BOLD + Font.ITALIC, 30));
		buttonsTitleLabel.setForeground(skinColor);
		overlayPanel.add(buttonsTitleLabel);
		
		createButtonPanel();
		
		// Get size of screen and set width and hieght to make the program
		// appear in a fitting size to the screen.
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = screenSize.height;
		int width = screenSize.width;
		
		// Set size of JFrame and location to the center of the screen.
		setSize(width/4, height/4);
		setLocationRelativeTo(null);
		setVisible(true); 
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		clubbers = new ArrayList<>();
		loadClubbersDBFromFile();
		
		addWindowListener(new WindowAdapter()
        {
        	/**
        	 * Overriden method which invokes the method 
        	 * {@link #writeClubbersDBtoFile} while closing the window.
        	 * @param event Holds the current event.
        	 */
            @Override
            public void windowClosing(WindowEvent event)
            {
            	writeClubbersDBtoFile();
                System.exit(0);
            }
        });
	}

	/**
	 * This method Initializes the {@link javax.swing.JButton}, sets their color,
	 * sets a hand cursor when hovering the buttons with the method   
	 * {@link java.awt.Component#setCursor}, sets a tooltip with the method
	 * {@link javax.swing.JComponent#setToolTipText} and adds an 
	 * {@link java.awt.event.ActionListener} to {@link ButtonsHandler}. 
	 * <br> A {@link javax.swing.JPanel} that is set to a {@link java.awt.GridLayout}
	 * will set its opaque to false for better design with the method 
	 * {@link javax.swing.JComponent#setOpaque}.
	 */
	private void createButtonPanel()
	{
		JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 5, 5));
		buttonPanel.setOpaque(false);
		
		btnHandler = new ButtonsHandler();
		
		searchButton = new JButton("Search");
		showListButton = new JButton("Show List");
		clubberButton = new JButton("New Clubber");
		deleteButton = new JButton("Delete Clubber");  
		
		Color darkCyan = new Color(125, 145, 180);
		
		searchButton.setBackground(darkCyan);
		showListButton.setBackground(darkCyan);
		clubberButton.setBackground(darkCyan);
		deleteButton.setBackground(darkCyan);
		
		searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		showListButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		clubberButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		deleteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		searchButton.setToolTipText(
			"Search for a clubber using an ID, personal number or student ID");
		showListButton.setToolTipText("Show all saved clubbers details");
		clubberButton.setToolTipText("Choose clubber type with radio buttons");
		deleteButton.setToolTipText(
			"Delete a clubber using an ID, personal number or student ID");
		
		searchButton.addActionListener(btnHandler);
		showListButton.addActionListener(btnHandler);
		clubberButton.addActionListener(btnHandler);
		deleteButton.addActionListener(btnHandler);
		
		buttonPanel.add(searchButton);
		buttonPanel.add(showListButton);
		buttonPanel.add(clubberButton);
		buttonPanel.add(deleteButton);
		
		overlayPanel.add(buttonPanel);
	}
	
	/**
	 * This method Initializes the {@link javax.swing.JRadioButton}, sets a 
	 * hand cursor when hovering the JRadioButtons with the method   
	 * {@link java.awt.Component#setCursor} and adds them to a 
	 * {@link javax.swing.ButtonGroup}
	 * <br> A {@link javax.swing.JPanel} will set its opaque to false for better
	 * design with the method 
	 * {@link javax.swing.JComponent#setOpaque}.
	 */
	private void createRadioButtonPanel()
	{
		JPanel radioButtonPanel = new JPanel();
		radioButtonPanel.setOpaque(false);
		
		personRadioButton = new JRadioButton("Person", true);
		soldierRadioButton = new JRadioButton("Soldier", false);
		studentRadioButton = new JRadioButton("Student", false);
		
		Color greyBlue = new Color(70, 100, 125);
		
		personRadioButton.setForeground(greyBlue);
		soldierRadioButton.setForeground(greyBlue);
		studentRadioButton.setForeground(greyBlue);
		
		personRadioButton.setOpaque(false);
		soldierRadioButton.setOpaque(false);
		studentRadioButton.setOpaque(false);
		
		personRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		soldierRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		studentRadioButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		radioButtonPanel.add(personRadioButton);
		radioButtonPanel.add(soldierRadioButton);
		radioButtonPanel.add(studentRadioButton);
		
		ButtonGroup radioGroup = new ButtonGroup();
		
		radioGroup.add(personRadioButton);
		radioGroup.add(soldierRadioButton);
		radioGroup.add(studentRadioButton);
		
		overlayPanel.add(radioButtonPanel);
	}
	
	/**
     * The method loads the clubbers data from a file. If no file is found a 
     * message will appear
     * <br> See FileOutputStream class: {@link java.io.FileInputStream}.
     * <br> See ObjectOutputStream class: {@link java.io.ObjectInputStream}.
     * <br> See writeObject method: {@link java.io.ObjectInputStream#readObject}.
     */
	private void loadClubbersDBFromFile()
	{  
		FileInputStream fis = null; 
		ObjectInputStream ois = null;
   	   
		try 
		{
			// Open file.
			fis = new FileInputStream("BKCustomers.dat");
			ois = new ObjectInputStream(fis);
			// Read object from a file.
			// SuppressWarnings doesn't work because of a bug, therefore it isn't 
			// possible to ignore the warning. The warning happens because we read 
			// an object from a file which obviously can't be known beforehand and 
			// then try to cast it to an ArrayList<ClubAbstractEntity>, even if we 
			// check with 'instanceof' the warning will presist, so the only option 
			// we have is to leave it as is.
			//@SuppressWarnings("unchecked")
			clubbers = (ArrayList<ClubAbstractEntity>)ois.readObject();
		} 
		catch (FileNotFoundException e) 
		{
			JOptionPane.showMessageDialog(this, 
				"BKCustomers.dat will be created after closing the program.", 
				"File Not Found", JOptionPane.INFORMATION_MESSAGE);
		} 
		catch (IOException e)
		{
		} 
		catch (ClassNotFoundException e)
	  	{
	  	}
	  	finally // Close inputstreams if they exist.
	  	{
	  		try
	  		{
	  			if (ois != null)
	  			{
	  				ois.close();
	   	   		}
	   	   
	   	   		if (fis != null)
	   	   		{
	   	   			fis.close();
	   	   		}
	   	   	}
	   	   	catch (IOException e)
	   	  	{
		  	}
		}
	}
	
	/**
	 * The method saves the current clubbers into file.
	 * <br> See FileOutputStream class: {@link java.io.FileOutputStream}.
	 * <br> See ObjectOutputStream class: {@link java.io.ObjectOutputStream}.
	 * <br> See writeObject method: {@link java.io.ObjectOutputStream#writeObject}.
	 */
	private void writeClubbersDBtoFile()
	{
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
   	   
		try
		{
			// Create a new file.
			fos = new FileOutputStream("BKCustomers.dat"); 
			oos = new ObjectOutputStream(fos);
			// Write object to a file
			oos.writeObject(clubbers);   
			oos.flush();
		}
		catch (FileNotFoundException e) 
		{
		} 
		catch (IOException e)
		{
		} 
		finally // Close outputstreams if they exist
		{
			try
	   	   	{
	   	   		if (oos != null)
	   	   		{
	   	   			oos.close();
	   	   		}
	   	   
	   	   		if (fos != null)
	   	   		{
	   	   			fos.close();
	   	   		}
	   	   }
	   	   catch (IOException e)
	   	   {
		   } 
		}
	}
	
	/**
	 * This clas extends from {@link javax.swing.JPanel}.
	 * <br> This class is a useful way to set a background image to a JPanel.
	 */
	private class ImagePanel extends JPanel
	{
		private Image image = null;
		
		private int iWidth2;
		private int iHeight2;
		
		/**
		 * ImagePanel Constructor recieves a {@link java.awt.Image} initializes
		 * its class instance variables with image height and length.
		 * @param image holds an image to set as background.
		 */
		public ImagePanel(Image image)
		{
			setLayout(new GridLayout(0, 1));
			
			this.image = image;
			iWidth2 = image.getWidth(this)/2;
			iHeight2 = image.getHeight(this)/2;
		}
		
		/**
		 * The method will draw the image to the JPanel using the method
		 * {@link java.awt.Graphics#drawImage}.
		 * @param g The graphic panel.
		 */
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			
			if (image != null)
			{
				int x = this.getParent().getWidth()/2 - iWidth2;
				int y = this.getParent().getHeight()/2 - iHeight2;
				
				g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
			}
		}
	}
	
	/**
    * This Class implemets {@link java.awt.event.ActionListener}.
    * <br> ButtonsHandler listens to the {@link javax.swing.JButton}: 
    * searchButton, showListButton, clubberButton and deleteButton.
    */
	private class ButtonsHandler implements ActionListener
	{
		/**
		 * This method is invoked when searchButton, showListButton,
		 * clubberButton or deleteButton are pressed.
		 * <br> The buttons will invoke the following methods:
		 * <br> searchButton - {@link #searchClubber}.
		 * <br> showListButton - {@link #showClubbers}.
		 * <br> clubberButton - {@link #createClubber}.
		 * <br> deleteButton - {@link #deleteClubber}.
		 * @param event Holds the current event.
		 */
		public void actionPerformed(ActionEvent event)
		{
			Object source = event.getSource();
			
			if (source == searchButton)
			{
				String userInput = JOptionPane.showInputDialog(self, 
					"Please Enter The Clubber's Key:", "Seach Key",
					JOptionPane.QUESTION_MESSAGE);
		
				searchClubber(userInput);
				return;
			}
			
			if (source == showListButton)
			{
				showClubbers();
				return;
			}
			
			if (source == clubberButton)
			{
				createClubber();
				return;
			}
			
			if (source == deleteButton)
			{
				String userInput = JOptionPane.showInputDialog(self, 
					"Please Enter The Clubber's Key:", "Delete Key",
					JOptionPane.QUESTION_MESSAGE); 
				
				
				deleteClubber(userInput);
				return;
			}
		}
		
		/**
		 * This method recieves a key and runs through they arraylist to find 
		 * a match with the method {@link ClubAbstractEntity#match}, if found
		 * the entity will set its visibility to true and allow customization
		 * and prevent use of the main JFrame until the current frame is closed.
		 * @param key holds data to find in the arraylist.
		 */
		private void searchClubber(String key)
		{
			if (key == null)
			{
				return;
			}
				
			for (ClubAbstractEntity clubber : clubbers)
			{
				if (clubber.match(key))
				{
					// Disable use of main frame.
					self.setEnabled(false);
					
					// Set the clubber window to appear in the middle
					// of the frame.
					clubber.setLocationRelativeTo(self);
					clubber.setAlwaysOnTop(true);
					clubber.setVisible(true);
					
					clubber.addWindowListener(new WindowAdapter()
					{
						/**
        	             * Overriden method which will prevent access to the main
        	             * frame till the current window is closed.
        	             * @param event Holds the current event.
        	             */
						@Override
						public void windowDeactivated(WindowEvent event)
						{
							// As long as the window is visible don't proceed
							if (clubber.isVisible())
							{
								return;
							}
							
							// Enable use of main frame.
							self.setEnabled(true);
							
							// Stop listening to this window after it isn't visible
							clubber.removeWindowListener(this);
						}
					});
						
					return;
				}
			}
			
			JOptionPane.showMessageDialog(self,
				String.format("Clubber with key %s does not exist", key),
				"Key Not Found", JOptionPane.INFORMATION_MESSAGE);
		}
		
		/**
		 * This method will show all the clubbers in a {@link javax.swing.JTextArea}
		 * and add a {@link javax.swing.JScrollPane} to it.
		 */
		private void showClubbers()
		{
			String clubbersDetails = String.format("Number of Clubbers: %d\n\n",
					clubbers.size()); 
				
			// Add all clubbers into one string
			for (ClubAbstractEntity clubber : clubbers)
			{
				clubbersDetails += clubber + "\n";
			}
			
			JTextArea textArea = new JTextArea(clubbersDetails);
			JScrollPane scrollPane = new JScrollPane(textArea); 
			
			textArea.setLineWrap(true);  
			textArea.setWrapStyleWord(true); 
			scrollPane.setPreferredSize(new Dimension(0, 350));
			
			JOptionPane.showMessageDialog(self, scrollPane, "Clubbers Details",
				JOptionPane.INFORMATION_MESSAGE);
		}
		
		/**
		 * This method enables the creation of the objects {@link Person},
		 * {@link Soldier} and {@link Student} through their empty constructors
		 * they will be added to the arraylist only after the window visibility
		 * will be set to false with help of a {@link java.awt.event.WindowListener}
		 * and the ID doesn't already exist in the arraylist.
		 */
		private void createClubber()
		{
			// Prevent use of main frame.
			self.setEnabled(false);
			
			ClubAbstractEntity newClubber;
			
			if (personRadioButton.isSelected())
			{
				newClubber = new Person();
			} 
			else if (soldierRadioButton.isSelected())
			{
				newClubber = new Soldier();
			}
			else
			{
				newClubber = new Student();
			}
			
			// Set the new clubber window to appear in the middle
			// of the frame
			newClubber.setLocationRelativeTo(self);
			
			newClubber.addWindowListener(new WindowAdapter()
			{
				/**
				 * Overriden method which will prevent access to the main
				 * frame till the current window has been closed.
				 * Upon closing the window the method will then check if the new
				 * clubber is valid with the methods: {@link ClubAbstractEntity#match}
				 * and {@link ClubAbstractEntity#getID}
				 * @param event Holds the current event.
				 */
				@Override
				public void windowDeactivated(WindowEvent event)
				{
					// As long as the window is visible don't continue
					if (newClubber.isVisible())
					{
						return;
					}
					
					// Enable use of main frame.
					self.setEnabled(true);
					// Stop listening to this window after it isn't visible
					newClubber.removeWindowListener(this);
					
					// Prevent addition of empty clubbers.
					// This happens when the program is force closed.
					if (newClubber.isEmpty())
					{
						return;
					}
					
					// Check if there is a clubber with the same ID
					for (ClubAbstractEntity clubber : clubbers)
					{
						if (clubber.match(newClubber.getID()))
						{
							JOptionPane.showMessageDialog(self,
								"ID already exists", "Clubber Addition Error",
								JOptionPane.ERROR_MESSAGE);
							
							return; 
						}
					}
	
					clubbers.add(newClubber);
					self.toFront();
				}
			});
		}
		
		/**
		 * This method gives the user an option to delete a clubber with a key
		 * which holds data.
		 * @param key holds data to find in the arraylist.
		 */
		private void deleteClubber(String key)
		{
			if (key == null)
			{
				return;
			}
			
			for (ClubAbstractEntity clubber : clubbers)
			{
				if (clubber.match(key))
				{
					int inputYesNo = JOptionPane.showConfirmDialog(self,
						  String.format("Are you sure you want to delete:\n%s",clubber),
						  "Delete Warning", JOptionPane.YES_NO_OPTION,
						  JOptionPane.WARNING_MESSAGE);
					  
					if (inputYesNo == JOptionPane.YES_OPTION)
					{
						clubbers.remove(clubber);
						JOptionPane.showMessageDialog(self,
							"Clubber succesfuly deleted!",
							"Clubber Deleted", JOptionPane.INFORMATION_MESSAGE);
					}
					
					return;
				}
			}
			
			JOptionPane.showMessageDialog(self,
				String.format("Clubber with key %s does not exist", key),
				"Key Not Found", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	/**
	 * static main method.
	 * Simply invokes {@link #NightClubMgmtApp} constructor.
	 * @param args command line arguments
	 */
	public static void main(String[] args)
	{
		NightClubMgmtApp application = new NightClubMgmtApp();
	}
} // End of class NightClubMgmtApp