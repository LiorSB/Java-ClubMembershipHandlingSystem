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
public class Soldier extends Person
{
	private String personalNumber;
	private JTextField personalNumberTextField;
	private JLabel asteriskLabel;
	
	/**
	 * Soldier Empty Constructor - invokes arguments constructor with empty strings. 
	 */
	public Soldier()
	{
		this("", "", "", "", "");
	}
	
	/**
	 * Soldier Arguments Constructor - creates an info {@link javax.swing.JPanel}
	 * with {@link java.awt.FlowLayout} for info field and sends them it 
	 * {@link ClubAbstractEntity#addToCenter}.
	 * <br> Initializes {@link java.lang.String}, {@link javax.swing.JTextField}
	 * and {@link javax.swing.JLabel}s and add to them a tooltip with the method
	 * {@link javax.swing.JComponent#setToolTipText}.
	 * @param id soldiers ID.
	 * @param name soldiers name.
	 * @param surname soldiers surname.
	 * @param tel soldiers phone number.
	 * @param personalNumber soldiers personal number.
	 */
	public Soldier(String id, String name, String surname, String tel,
		String personalNumber)
	{
		super(id, name, surname, tel);
		
		personalNumberTextField = new JTextField(30);
		
		personalNumberTextField.setText(personalNumber);
		this.personalNumber = personalNumberTextField.getText();
		
		JLabel personalNumberLabel = new JLabel("Personal No.");
		personalNumberLabel.setToolTipText("e.g: R/4684109, O/5044109, C/4684109");
		
		asteriskLabel = new JLabel("*");
		asteriskLabel.setForeground(Color.RED);
		asteriskLabel.setVisible(false);
		
		JPanel asteriskPanel = new JPanel();
		asteriskPanel.setPreferredSize(new Dimension(15, 25));
		asteriskPanel.add(asteriskLabel);
		
		JPanel personalNumberPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		personalNumberPanel.add(personalNumberLabel);
		personalNumberPanel.add(personalNumberTextField);
		personalNumberPanel.add(asteriskPanel);
		
		addToCenter(personalNumberPanel);
		
		setTitle("Soldier Clubber's Data");
		setSize(450, 250);
	}
	
	/**
	 * This method checks whether the personalNumber is empty with
	 * {@link java.lang.Object#equals} or the Super method {@link Person#isEmpty}.
	 * @return true or false whether an info field is empty.
	 */
	@Override
	public boolean isEmpty()
	{
		return super.isEmpty() || "".equals(personalNumber); 
	}
	
	/**
	 * This method checks whether the key sent is equal with 
	 * {@link java.lang.Object#equals} to personalNumber or to the Super method
	 * {@link Person#match}.
	 * @param key may hold an id or personalNumber String.
	 * @return true or false whether id, personalNumber or both are equal to the key.
	 */
	@Override
	public boolean match(String key)
	{
		return super.match(key) || personalNumber.equals(key);
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
		
		if (!personalNumberTextField.getText().matches("[ROC]/[1-9]\\d{6}"))
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
		personalNumber = personalNumberTextField.getText();
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
		
		personalNumberTextField.setText(personalNumber);
		asteriskLabel.setVisible(false);
	}
	
	/**
	 * Overriden method to implement {@link Soldier} {@link java.lang.Object#toString}.
	 * <br> It also invokes the Super method {@link Person#toString}. 
	 */
	@Override
	public String toString()
	{
		return super.toString() + String.format("Personal Number: %s\n", personalNumber);
	}
}