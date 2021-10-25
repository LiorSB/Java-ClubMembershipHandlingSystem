import javax.swing.*;
import java.awt.*;

/**
 * This class extends from {@link Person}.
 * <br> This Class sets info fields for a person with GUI elements which then 
 * sends them to {@link ClubAbstractEntity} through 
 * {@link ClubAbstractEntity#addToCenter}.
 * This class overrides the methods: {@link Person#match},
 * {@link Person#validateData}, {@link Person#commit},
 * {@link Person#rollBack} and {@link Person#isEmpty}.
 * @author Lior Sabri, Ben Biton
 */
public class Student extends Person
{
	private String studentID;
	private JTextField studentIDTextField;
	private JLabel asteriskLabel;
	
	/**
	 * Student Empty Constructor - invokes arguments constructor with empty strings. 
	 */
	public Student()
	{
		this("", "", "", "", "");
	}
	
	/**
	 * Student Arguments Constructor - creates an info {@link javax.swing.JPanel}
	 * with {@link java.awt.FlowLayout} for info field and sends them it 
	 * {@link ClubAbstractEntity#addToCenter}.
	 * <br> Initializes {@link java.lang.String}, {@link javax.swing.JTextField}
	 * and {@link javax.swing.JLabel}s and add to them a tooltip with the method
	 * {@link javax.swing.JComponent#setToolTipText}.
	 * @param id student ID.
	 * @param name student name.
	 * @param surname student surname.
	 * @param tel student phone number.
	 * @param studentID student ID.
	 */
	public Student(String id, String name, String surname, String tel,
		String studentID)
	{
		super(id, name, surname, tel);
		
		studentIDTextField = new JTextField(30);
		
		studentIDTextField.setText(studentID);
		this.studentID = studentIDTextField.getText();
		
		JLabel studentIDLabel = new JLabel("Student ID");
		studentIDLabel.setToolTipText("e.g: SCE12345, RUP56098, HIT14606");
		
		asteriskLabel = new JLabel("*");
		asteriskLabel.setForeground(Color.RED);
		asteriskLabel.setVisible(false);
		
		JPanel asteriskPanel = new JPanel();
		asteriskPanel.add(asteriskLabel);
		asteriskPanel.setPreferredSize(new Dimension(15, 25));
		
		JPanel studentIDPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		studentIDPanel.add(studentIDLabel);
		studentIDPanel.add(studentIDTextField);
		studentIDPanel.add(asteriskPanel);                               
		
		addToCenter(studentIDPanel);
		
		setTitle("Student Clubber's Data");
		setSize(450, 250);
	}
	
	/**
	 * This method checks whether the studentID is empty with
	 * {@link java.lang.Object#equals} or the Super method {@link Person#isEmpty}.
	 * @return true or false whether an info field is empty.
	 */
	@Override
	public boolean isEmpty()
	{
		return super.isEmpty() || studentID.equals("");        
	}
	
	/**
	 * This method checks whether the key sent is equal with 
	 * {@link java.lang.Object#equals} to studentID from the 4th letter
	 * (including) or to the Super method {@link Person#match}.
	 * @param key may hold an id or numbers of studentID String.
	 * @return true or false whether id, studentID or both are equal to the key.
	 */
	@Override
	public boolean match(String key)
	{
		return super.match(key) || key.regionMatches(0, studentID, 3, 5);
	}
	
	/**
	 * This method validates whether the info field matches regular expressions 
	 * with {@link java.lang.String#matches} if so it will set the asteriskLabel 
	 * visibility to true, otherwise to false.
	 * <br> It also invokes the Super method {@link Person#validateData}.
	 * @return true or false whether all info fields match thier regular expressions.
	 */
	@Override
	protected boolean validateData()
	{
		if (!super.validateData())
		{
			return false;
		}
		
		if (!studentIDTextField.getText().matches("[A-Z]{3}[1-9]\\d{4}"))
		{
			asteriskLabel.setVisible(true);
			return false;
		}
		else
		{
			asteriskLabel.setVisible(false);
		}
		
		return true;
	}
	
	/**
	 * This method uses {@link javax.swing.JTextField#getText} to recieve the text 
	 * in the {@link javax.swing.JTextField} and save them in the info String.
	 * <br> It also invokes the Super method {@link Person#commit}.
	 */
	@Override
	protected void commit()
	{
		super.commit();
		studentID = studentIDTextField.getText();
	}
	
	/**
	 * This method uses {@link javax.swing.JTextField#setText} to insert the info 
	 * String into the {@link javax.swing.JTextField} and set the asteriskLabel
	 * visibility to false.
	 * <br> It also invokes the Super method {@link Person#rollBack}.
	 */
	@Override
	protected void rollBack()
	{
		super.rollBack();
		
		studentIDTextField.setText(studentID);
		asteriskLabel.setVisible(false);
	}
	
	/**
	 * Overriden method to implement {@link Student} {@link java.lang.Object#toString}.
	 * <br> It also invokes the Super method {@link Person#toString}. 
	 */
	@Override
	public String toString()
	{
		return super.toString() + String.format("Student ID: %s\n", studentID);
	}
}