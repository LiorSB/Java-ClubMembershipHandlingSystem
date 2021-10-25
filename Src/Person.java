import javax.swing.*;
import java.awt.*;

/**
 * This class extends from {@link ClubAbstractEntity}.
 * <br> This Class sets info fields for a person with GUI elements which then 
 * sends them to {@link ClubAbstractEntity} through 
 * {@link ClubAbstractEntity#addToCenter}.
 * This class implements the abrstract methods: {@link ClubAbstractEntity#match},
 * {@link ClubAbstractEntity#validateData}, {@link ClubAbstractEntity#commit},
 * {@link ClubAbstractEntity#rollBack}, {@link ClubAbstractEntity#isEmpty} and
 * {@link ClubAbstractEntity#getID}.
 * @author Lior Sabri, Ben Biton
 */
public class Person extends ClubAbstractEntity
{
	private String id;
	private String name;
	private String surname;
	private String tel;      
	
	private JTextField idTextField;
	private JTextField nameTextField;
	private JTextField surnameTextField;
	private JTextField telTextField;  
	
	private JLabel[] asteriskLabel; 
	
	/**
	 * Person Empty Constructor - invokes arguments constructor with empty strings. 
	 */
	public Person()
	{
		this("", "", "", "");
	}
	
	/**
	 * Person Arguments Constructor - creates an info {@link javax.swing.JPanel}
	 * with {@link java.awt.FlowLayout} for each info field and sends them to 
	 * {@link ClubAbstractEntity#addToCenter}.
	 * <br> Initializes all {@link java.lang.String}s, {@link javax.swing.JTextField}s
	 * and {@link javax.swing.JLabel}s and add to them a tooltip with the method
	 * {@link javax.swing.JComponent#setToolTipText}.
	 * @param id persons ID.
	 * @param name persons name.
	 * @param surname persons surname.
	 * @param tel persons phone number.
	 */
	public Person(String id, String name, String surname, String tel)
	{
		JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); 
		JPanel surnamePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel telPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		idTextField = new JTextField(30);
		nameTextField = new JTextField(30);
		surnameTextField = new JTextField(30);
		telTextField = new JTextField(30);
		
		idTextField.setText(id);
		nameTextField.setText(name);
		surnameTextField.setText(surname);
		telTextField.setText(tel);
		
		this.id = idTextField.getText(); 
		this.name = idTextField.getText();
		this.surname = idTextField.getText();
		this.tel = idTextField.getText();   
		
		JLabel idLabel = new JLabel("ID");
		JLabel nameLabel = new JLabel("Name");
		JLabel surnameLabel = new JLabel("Surname");
		JLabel telLabel = new JLabel("Tel");
		
		idLabel.setToolTipText("e.g: 0-2423535|1, 2-5554445|3");
		nameLabel.setToolTipText("e.g: Mark, Bo");
		surnameLabel.setToolTipText("e.g: Avrahami-O'Mally, Sabri");
		telLabel.setToolTipText("e.g: +(972)50-6663210, +(44)206-8208167, +(1)4-9520205");
		
		asteriskLabel = new JLabel[4];
		JPanel[] asteriskPanel = new JPanel[4];
		
		for (int i = 0; i < asteriskLabel.length; i++)
		{
			asteriskLabel[i] = new JLabel("*");	
			asteriskLabel[i].setForeground(Color.RED);
			asteriskLabel[i].setVisible(false);
			
			// Reason for the JPanel is because in Java it isn't possible to hide
			// and when you set visibility to false the JLabel will collapse and
			// create a less desirable GUI.
			asteriskPanel[i] = new JPanel();
			asteriskPanel[i].setPreferredSize(new Dimension(15, 25));
			asteriskPanel[i].add(asteriskLabel[i]);
		}
		
		idPanel.add(idLabel);
		idPanel.add(idTextField);
		idPanel.add(asteriskPanel[0]);
		
		namePanel.add(nameLabel);
		namePanel.add(nameTextField);
		namePanel.add(asteriskPanel[1]);
		
		surnamePanel.add(surnameLabel);
		surnamePanel.add(surnameTextField);
		surnamePanel.add(asteriskPanel[2]);
		
		telPanel.add(telLabel);
		telPanel.add(telTextField);
		telPanel.add(asteriskPanel[3]);
		
		addToCenter(idPanel);
		addToCenter(namePanel);
		addToCenter(surnamePanel);
		addToCenter(telPanel);
		
		setTitle("Person Clubber's Data");
		setSize(450, 220);
	}
	
	/**
	 * This method returns the {@link Person}s ID.
	 * @return id {@link java.lang.String}
	 */
	public String getID()
	{
		return id;
	}
	                                                                                          
	/**
	 * This method checks whether the info fields are empty with
	 * {@link java.lang.Object#equals}.
	 * @return true or false whether an info field is empty.
	 */
	public boolean isEmpty()
	{
		// Comparing an empty String is an efficient way to both check whether 
		// the String is empty or null, since equals() will check its arguement and
		// return false incase it is null.
		String emptyString = "";
		
		return emptyString.equals(id) || emptyString.equals(name) ||
			emptyString.equals(surname) || emptyString.equals(tel); 
	}
	
	/**
	 * This method checks whether the key sent is equal with 
	 * {@link java.lang.Object#equals} to id.
	 * @param key may hold an id String.
	 * @return true or false whether id is equal to the key.
	 */
	public boolean match(String key)
	{
		return id.equals(key);
	}
	
	/**
	 * This method validates whether the info fields matches regular expressions 
	 * with {@link java.lang.String#matches} if so it will set the asteriskLabels  
	 * visibility to true, otherwise to false. 
	 * @return true or false whether all info fields match thier regular expressions.
	 */
	protected boolean validateData()
	{
		if (!idTextField.getText().matches("\\d-\\d{7}\\|[1-9]"))
		{
			asteriskLabel[0].setVisible(true);
			return false;
		}
		else
		{
			asteriskLabel[0].setVisible(false);
		}
		
		if (!nameTextField.getText().matches("[A-Z][a-z]+"))
		{
			asteriskLabel[1].setVisible(true);
			return false;
		}
		else
		{
			asteriskLabel[1].setVisible(false);
		}
		
		if (!surnameTextField.getText().matches("([A-Z][a-z]*['-]?)+"))
		{
			asteriskLabel[2].setVisible(true);
			return false;
		}
		else
		{
			asteriskLabel[2].setVisible(false);
		}
		
		if (!telTextField.getText().matches("\\+\\([1-9]\\d{0,2}\\)[1-9]\\d{0,2}-[1-9]\\d{6}"))
		{
			asteriskLabel[3].setVisible(true);
			return false;
		}
		else
		{
			asteriskLabel[3].setVisible(false);
		}
		
		return true;
	}
	
	/**
	 * This method uses {@link javax.swing.JTextField#getText} to recieve the text 
	 * in the {@link javax.swing.JTextField}s and save them in the info Strings.
	 */
	protected void commit()
	{
		id = idTextField.getText();
		name = nameTextField.getText();
		surname = surnameTextField.getText();
		tel = telTextField.getText();    		
	}
	
	/**
	 * This method uses {@link javax.swing.JTextField#setText} to insert the info 
	 * String into the {@link javax.swing.JTextField}s and set the asteriskLabels
	 * visibility to false.
	 */
	protected void rollBack()
	{
		idTextField.setText(id);
		nameTextField.setText(name);
		surnameTextField.setText(surname);
		telTextField.setText(tel);

		for (JLabel asterisk : asteriskLabel)
		{
			asterisk.setVisible(false);
		}
	}
	
	/**
	 * Overriden method to implement {@link Person}'s {@link java.lang.Object#toString}.
	 */
	@Override
	public String toString()
	{
		return String.format("ID: %s\nName: %s\nSurname: %s\nTel: %s\n",
			id, name, surname, tel);
	}
}