import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

/**
 * This abstract class extends from {@link javax.swing.JFrame} and implements
 * {@link java.io.Serializable}.
 * <br> This Class builds the basic GUI arragment of each window.
 * <br> Other classes that inherit from this class may add {@link java.awt.Component}s
 * to the centerPanel which is set to {@link java.awt.GridLayout}.
 * <br> This class holds the inner class {@link ButtonsHandler} which listens
 * to the {@link javax.swing.JButton} okButton and cancelButton.
 * <br> Currently the class that inherits from this class is: {@link Person}
 * @author Lior Sabri, Ben Biton
 */
public abstract class ClubAbstractEntity extends JFrame implements Serializable
{
	private JButton okButton;
	private JButton cancelButton;
	
	private JPanel centerPanel;
	
	// It's a good practice to save a reference to events/listeners that are in
	// the memory to prevent memory leak.
	private ButtonsHandler handler; 
	
	/**
	 * ClubAbstractEntity Constructor - initializes JButtons, JPanels and 
	 * {@link ButtonsHandler}.
	 */
	public ClubAbstractEntity()
	{
		// Setting the GridLayout with (0, 1) will make it add a new row when needed.
		centerPanel = new JPanel(new GridLayout(0, 1));
		JPanel buttonPanel = new JPanel();
		
		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");
		
		cancelButton.setEnabled(false);
		
		handler = new ButtonsHandler();
		
		okButton.addActionListener(handler);
		cancelButton.addActionListener(handler);
		
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		
		add(centerPanel);
		add(buttonPanel, BorderLayout.SOUTH);          
		
		setVisible(true); 
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	
	/**
	 * This method recieves a {@link java.awt.Component} and adds it to the CenterPanel
	 * @param guiComponent holds a component sent by son classes.
	 */
	protected void addToCenter(Component guiComponent)
	{
		centerPanel.add(guiComponent);
	}
	
	/**
     * Abstract method that implements a copmarison with a given key.
     * @param key String which will be compared.
     * @return true or false whether the key has found a match.
     */
	public abstract boolean match(String key);
	
	/**
     * Abstract method which invokes whenever okay button is pressed and 
     * implements data validation.
     * @return true or false whether the data is valid.
     */
	protected abstract boolean validateData();
	
	/**
     * Abstract method which invokes whenever okayButton is pressed if 
     * {@link #validateData} returns true.
     */
	protected abstract void commit();
	
	/**
     * Abstract method which invokes whenever cancelButton is pressed.
     */
	protected abstract void rollBack();
	
	/**
     * Abstract method which helps know if fields of inherited class are initialized.
     * @return true or false whether the data is empty.
     */
	public abstract boolean isEmpty();
	
	/**
	 * Abstract method which returns inherited class ID.
	 * <br> This method is only needed in {@link Person}, although if it will be 
	 * placed there then when used in {@link NightClubMgmtApp} {@link ClubAbstractEntity}
	 * will need casting to {@link Person}.
	 * @return recieve an ID.
	 */
	public abstract String getID();
	
	/**
    * This Class implemets {@link java.awt.event.ActionListener} and 
    * {@link java.io.Serializable}.
    * <br> ButtonsHandler handles okButton and cancelButton.
    */
	private class ButtonsHandler implements ActionListener ,Serializable
	{
		/**
		 * This method is invoked when okButton and cancelButton are pressed.
		 * <br> okButton - first checks {@link #validateData} if it returns true
		 * it will proceed to {@link #commit}, enable cancel button with 
		 * {@link java.awt.Component#setEnabled} and hide the {@link javax.swing.JFrame}.
		 * <br> cancelButton - invokes {@link #rollBack} and then sets 
		 * {@link javax.swing.JFrame} visibility to false.
		 * @param event Holds the current event.
		 */
		public void actionPerformed(ActionEvent event)
		{
			Object source = event.getSource();

			if (source == okButton)
			{
				if (validateData())
				{
					commit();
					cancelButton.setEnabled(true);
					setVisible(false);
				}
				
				return;
			}
			
			if (source == cancelButton)
			{
				rollBack();
				setVisible(false);
			}
		}
	}
}