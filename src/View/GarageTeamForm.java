package View;

import java.awt.Component;
import java.awt.EventQueue;
import Controller.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import javax.swing.JSlider;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class GarageTeamForm extends JFrame {

	private enum CurrentState
	{
		None,
		AddVehicle,
		UpdateVehicle,
		MakeAction,
		PrintByStatus,
		ViewDetails,
	};
	
	private JPanel contentPane;
	private Mangment controller = new Mangment();
	private CurrentState currentState = CurrentState.None;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GarageTeamForm frame = new GarageTeamForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static <T> T as(Object o, Class<T> tClass) 
	{
	     return tClass.isInstance(o) ? (T) o : null;
	}
	
	private boolean checkIfFilled(JPanel panel) 
	{
		Component[] c = panel.getComponents();
		for(Component com : c) 
		{
			if(com instanceof JTextField) 
			{
				if(((JTextField) com).getText().isEmpty() || ((JTextField) com).getText().equals("Type Here"))
				{
					return false;
				}
			}
			else if(com instanceof JComboBox<?>)
			{
				if(((JComboBox<?>) com).getSelectedItem().equals("Select"))
				{
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean isVehicleExists(String i_PlateNumber) 
	{
		try
		{
			FileReader reader = new FileReader("Vehicles.json");
			BufferedReader buffer = new BufferedReader(reader);
			JSONParser parser = new JSONParser();
			String currentJSONString  = "";

			while((currentJSONString = buffer.readLine()) != null ) 
			{
				JSONObject vehicle = (JSONObject)parser.parse(currentJSONString);
				if(vehicle.get("Plate Number").equals(i_PlateNumber))
			    {
					return true;
			    }
			}
		}
		catch (FileNotFoundException e1) 
		{
            e1.printStackTrace();
        }
		catch (IOException e1) 
		{
            e1.printStackTrace();
        }
		catch (org.json.simple.parser.ParseException e1)
		{
			e1.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Create the frame.
	 */
	public GarageTeamForm() 
	{
		setResizable(false);
		setBackground(SystemColor.activeCaption);
		setIconImage(Toolkit.getDefaultToolkit().getImage(GarageTeamForm.class.getResource("/resources/Car-Repair-Blue-2-icon.png")));
		//////////////////////////////////////////////////////////////////////
		/////////////Components Initialization///////////////////////////////
		////////////////////////////////////////////////////////////////////
		setTitle("Garage Management System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 952, 627);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		JPanel panelFillForm = new JPanel();
		panelFillForm.setBounds(25, 185, 892, 384);
		JButton btnPrintByStatus = new JButton("Print By Vehicle Status");
		btnPrintByStatus.setHorizontalAlignment(SwingConstants.LEFT);
		btnPrintByStatus.setIcon(new ImageIcon(GarageTeamForm.class.getResource("/resources/Actions-view-list-details-icon.png")));
		btnPrintByStatus.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		btnPrintByStatus.setBounds(216, 127, 231, 47);
		JButton btnUpdateVehicle = new JButton("Update Vehicle Status");
		btnUpdateVehicle.setHorizontalAlignment(SwingConstants.LEFT);
		btnUpdateVehicle.setIcon(new ImageIcon(GarageTeamForm.class.getResource("/resources/edit-file-icon.png")));
		btnUpdateVehicle.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		btnUpdateVehicle.setBounds(339, 69, 231, 47);
		JButton btnViewFullDetails = new JButton("View Full Vehicle Details");
		btnViewFullDetails.setHorizontalAlignment(SwingConstants.LEFT);
		btnViewFullDetails.setIcon(new ImageIcon(GarageTeamForm.class.getResource("/resources/Apps-preferences-contact-list-icon.png")));
		btnViewFullDetails.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		btnViewFullDetails.setBounds(464, 127, 231, 47);
		JLabel lblVehicleManagement = new JLabel("Garage Management System Menu");
		lblVehicleManagement.setHorizontalAlignment(SwingConstants.CENTER);
		lblVehicleManagement.setBounds(142, 0, 620, 66);
		lblVehicleManagement.setFont(new Font("Arial Black", Font.BOLD, 29));	
		JButton btnAddVehicle = new JButton("Add Vehicle");
		btnAddVehicle.setHorizontalAlignment(SwingConstants.LEFT);
		btnAddVehicle.setIcon(new ImageIcon(GarageTeamForm.class.getResource("/resources/car-add-icon.png")));
		btnAddVehicle.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		btnAddVehicle.setBounds(168, 69, 161, 47);
		JLabel lblModel = new JLabel("Model:");
		lblModel.setBounds(65, 95, 61, 22);
		lblModel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		JLabel lblPlateNumber = new JLabel("Plate Number:");
		lblPlateNumber.setBounds(65, 25, 131, 22);
		lblPlateNumber.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		JLabel lblType = new JLabel("Type:");
		lblType.setBounds(65, 60, 50, 22);
		lblType.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		JComboBox<String> comboBoxVehicleType = new JComboBox<String>();
		comboBoxVehicleType.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		comboBoxVehicleType.setBounds(258, 60, 149, 20);
		comboBoxVehicleType.setModel(new DefaultComboBoxModel(new String[] {"Select", "Car", "Motorcycle", "Truck"}));
		JFormattedTextField textPlateNumber = new JFormattedTextField();
		textPlateNumber.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		textPlateNumber.setBounds(258, 25, 149, 20);
		textPlateNumber.setText("Type here");
		JTextField textFieldModel = new JTextField();
		textFieldModel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		textFieldModel.setBounds(258, 95, 149, 20);
		textFieldModel.setText("Type here");
		textFieldModel.setColumns(10);
		JFormattedTextField textEngineCap = new JFormattedTextField();
		textEngineCap.setText("Type here");
		textEngineCap.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		textEngineCap.setBounds(257, 199, 150, 20);
		JLabel lblNumberOfWheels = new JLabel("Number of wheels:");
		lblNumberOfWheels.setBounds(65, 235, 172, 22);
		lblNumberOfWheels.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		JComboBox<String> comboBoxNumOfWheels = new JComboBox<String>();
		comboBoxNumOfWheels.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		comboBoxNumOfWheels.setBounds(258, 235, 149, 20);
		comboBoxNumOfWheels.setModel(new DefaultComboBoxModel<String>(new String[] {"Select", "2", "4", "12"}));
		JLabel lblEngineType = new JLabel("Engine Type:");
		lblEngineType.setBounds(65, 165, 117, 22);
		lblEngineType.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		JComboBox<String> comboBoxEngineType = new JComboBox<String>();
		
		comboBoxEngineType.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		comboBoxEngineType.setBounds(258, 165, 149, 20);
		comboBoxEngineType.setModel(new DefaultComboBoxModel<String>(new String[] {"Select", "Fuel", "Electric"}));
		JLabel lblFuelType = new JLabel("Fuel Type:");
		lblFuelType.setBounds(530, 165, 107, 22);
		lblFuelType.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		JComboBox<String> comboBoxFuelType = new JComboBox<String>();
		comboBoxFuelType.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		comboBoxFuelType.setBounds(647, 165, 127, 20);
		comboBoxFuelType.setModel(new DefaultComboBoxModel<String>(new String[] {"Select","Electricity", "Octan 95", "Octan 96", "Soler"}));
		JLabel lblWheelsManufacturer = new JLabel("Wheels' Manufacturer:");
		lblWheelsManufacturer.setBounds(65, 270, 209, 22);
		lblWheelsManufacturer.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		JFormattedTextField textWheelManufacturer = new JFormattedTextField();
		textWheelManufacturer.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		textWheelManufacturer.setBounds(257, 270, 150, 20);
		textWheelManufacturer.setText("Type here");
		JLabel lblColor = new JLabel("Color:");
		lblColor.setBounds(528, 95, 71, 22);
		lblColor.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		JComboBox<String> comboBoxColor = new JComboBox<String>();
		comboBoxColor.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		comboBoxColor.setBounds(647, 95, 127, 20);
		comboBoxColor.setModel(new DefaultComboBoxModel<String>(new String[] {"Select", "Red", "Green", "Blue", "White", "Black", "Yellow", "Violet", "Pink", "Gold", "Silver"}));
		JLabel lblNumberOfDoors = new JLabel("Number Of Doors:");
		lblNumberOfDoors.setBounds(65, 340, 164, 22);
		lblNumberOfDoors.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		JComboBox<String> comboBoxNumOfDoors = new JComboBox<String>();
		comboBoxNumOfDoors.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		comboBoxNumOfDoors.setBounds(258, 340, 149, 20);
		comboBoxNumOfDoors.setModel(new DefaultComboBoxModel<String>(new String[] {"Select","0", "2", "3", "4", "5"}));
		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setBounds(528, 130, 85, 22);
		lblStatus.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		JComboBox<String> comboBoxVehicleStatus = new JComboBox<String>();
		comboBoxVehicleStatus.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		comboBoxVehicleStatus.setBounds(647, 130, 127, 20);
		comboBoxVehicleStatus.setModel(new DefaultComboBoxModel<String>(new String[] {"Select", "In Progress", "Fixed", "Paid"}));
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(587, 291, 122, 49);
		btnSubmit.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
		JLabel lblClientName = new JLabel("Client Name:");
		lblClientName.setBounds(528, 200, 122, 22);
		lblClientName.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		JFormattedTextField textClientName = new JFormattedTextField();
		textClientName.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		textClientName.setBounds(647, 200, 127, 20);
		textClientName.setText("Type here");
		JLabel lblClientPhone = new JLabel("Client Phone:");
		lblClientPhone.setBounds(528, 235, 131, 22);
		lblClientPhone.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		JFormattedTextField textClientPhoneNumber = new JFormattedTextField();
		textClientPhoneNumber.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		textClientPhoneNumber.setBounds(647, 235, 127, 20);
		textClientPhoneNumber.setText("Type here");
		JLabel lblEngineCapacity = new JLabel("Engine Capacity:");
		lblEngineCapacity.setBounds(65, 200, 201, 22);
		lblEngineCapacity.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		JLabel lblWheelsAirPressure = new JLabel("Wheels' Air Pressure:");
		lblWheelsAirPressure.setBounds(65, 305, 200, 22);
		lblWheelsAirPressure.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		JLabel lblLicenseType = new JLabel("License Type:");
		lblLicenseType.setBounds(65, 130, 200, 22);
		lblLicenseType.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
		JComboBox comboBoxLicenseType = new JComboBox();
		comboBoxLicenseType.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		comboBoxLicenseType.setBounds(258, 130, 149, 20);
		comboBoxLicenseType.setModel(new DefaultComboBoxModel(new String[] {"Select", "A", "B", "C"}));
		JFormattedTextField textWheelAirPressure = new JFormattedTextField();
		textWheelAirPressure.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		textWheelAirPressure.setBounds(258, 305, 149, 20);
		textWheelAirPressure.setText("Type here");
		JButton btnOK= new JButton("OK");
		btnOK.setBounds(417, 17, 55, 35);
		btnOK.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		JButton btnMakeAction = new JButton("Make Action");
		btnMakeAction.setHorizontalAlignment(SwingConstants.LEFT);
		btnMakeAction.setIcon(new ImageIcon(GarageTeamForm.class.getResource("/resources/Actions-configure-icon.png")));
		btnMakeAction.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		btnMakeAction.setBounds(580, 69, 161, 47);
		
		///////////////////////////////////////////////////////////
		///////////Events/////////////////////////////////////////
		/////////////////////////////////////////////////////////
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) 
			{
				panelFillForm.setVisible(false);
				contentPane.setFocusable(true);
				controller.intializeJSONArray();
				controller.initializeGarageDictionaries();
			}
		});
		///////////////////////////////////////////////////////////
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) 
			{
				contentPane.setFocusable(true);
				panelFillForm.setFocusable(false);
			}
			@Override
			public void mouseExited(MouseEvent e)
			{
				contentPane.setFocusable(false);
				panelFillForm.setFocusable(true);
			}
		});
		///////////////////////////////////////////////////////////
		textPlateNumber.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) 
			{
				if((isVehicleExists(textPlateNumber.getText())) && (currentState.equals(CurrentState.AddVehicle))) 
				{
					JOptionPane.showMessageDialog(panelFillForm, "The vehicle already exists." + System.lineSeparator() + "Changing status of vehicle number:" + textPlateNumber.getText() + " to In Progress.");
					controller.changeVehicleStatus(textPlateNumber.getText(),"In Progress");
					textPlateNumber.setText("");
				}
			}
		});
		
		textEngineCap.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				textEngineCap.setText("");
			}
		});
		
		
		textWheelAirPressure.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				textWheelAirPressure.setText("");
			}
		});
		
		textPlateNumber.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				textPlateNumber.setText("");
			}
		});
		
		textFieldModel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				textFieldModel.setText("");
			}
		});
		
		textWheelManufacturer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				textWheelManufacturer.setText("");
			}
		});
		
		textClientName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				textClientName.setText("");
			}
		});
		
		textClientName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) 
			{
				for(char c : textClientName.getText().toCharArray())
				{
					if(Character.isDigit(c)) 
					{
						JOptionPane.showMessageDialog(panelFillForm, "Client name can't include digits!");
						textClientName.setText("");
						break;
					}
				}
			}
		});
		
		textClientPhoneNumber.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) 
			{
				for(char c : textClientPhoneNumber.getText().toCharArray())
				{
					if(Character.isLetter(c)) 
					{
						JOptionPane.showMessageDialog(panelFillForm, "Client phone number can't include letters!");
						textClientPhoneNumber.setText("");
						break;
					}
				}
			}
		});

		
		textWheelAirPressure.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) 
			{
				for(char c : textWheelAirPressure.getText().toCharArray())
				{
					if(Character.isLetter(c)) 
					{
						JOptionPane.showMessageDialog(panelFillForm, "Wheels Air Pressure can't include letters!");
						textWheelAirPressure.setText("");
						break;
					}
				}
			}
		});
		
		textClientPhoneNumber.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				textClientPhoneNumber.setText("");
			}
		});
		
		comboBoxEngineType.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (comboBoxEngineType.getSelectedItem().equals("Electric"))
				{
						comboBoxFuelType.setSelectedItem("Electricity");
						textEngineCap.setText("Enter in hours");
				}
				if (comboBoxEngineType.getSelectedItem().equals("Fuel"))
				{
					textEngineCap.setText("Enter in liters");
					if (comboBoxVehicleType.getSelectedItem().equals("Motorcycle"))
					{
						comboBoxFuelType.setSelectedItem("Octan 95");
					}
					if (comboBoxVehicleType.getSelectedItem().equals("Car"))
					{
						comboBoxFuelType.setSelectedItem("Octan 96");
					}
					
				}
				comboBoxFuelType.setEnabled(false);
				
			}
		});
		
		comboBoxVehicleType.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (comboBoxVehicleType.getSelectedItem().equals("Motorcycle"))
				{
						comboBoxLicenseType.setSelectedItem("A");
						comboBoxNumOfWheels.setSelectedItem("2");
						comboBoxNumOfDoors.setSelectedItem("0");
						comboBoxNumOfWheels.setEnabled(false);
						comboBoxLicenseType.setEnabled(false);
						comboBoxNumOfDoors.setEnabled(false);
						
				}
				if (comboBoxVehicleType.getSelectedItem().equals("Car"))
				{
					comboBoxLicenseType.setSelectedItem("B");
					comboBoxNumOfWheels.setSelectedItem("4");
					comboBoxNumOfWheels.setEnabled(false);
					comboBoxLicenseType.setEnabled(false);
					comboBoxNumOfDoors.setEnabled(true);
				}
				
				comboBoxEngineType.setSelectedIndex(0);
				comboBoxEngineType.setEnabled(true);
				comboBoxFuelType.setSelectedIndex(0);
				comboBoxFuelType.setEnabled(true);
				
				if (comboBoxVehicleType.getSelectedItem().equals("Truck"))
				{
					comboBoxLicenseType.setSelectedItem("C");
					comboBoxNumOfWheels.setSelectedItem("12");
					comboBoxEngineType.setSelectedItem("Fuel");
					comboBoxFuelType.setSelectedItem("Soler");
					comboBoxFuelType.setEnabled(false);
					comboBoxEngineType.setEnabled(false);
					comboBoxNumOfWheels.setEnabled(false);
					comboBoxLicenseType.setEnabled(false);
					comboBoxNumOfDoors.setEnabled(true);
				}	
				
			}
				
			
		});
		
		btnOK.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if((currentState.equals(CurrentState.UpdateVehicle)) || (currentState.equals(CurrentState.MakeAction))|| (currentState.equals(CurrentState.ViewDetails)) )
				{
					boolean vehicleFound = false;
					try
					{
						FileReader reader = new FileReader("Vehicles.json");
						BufferedReader buffer = new BufferedReader(reader);
						JSONParser parser = new JSONParser();
						String currentJSONString  = "";
	
						while((currentJSONString = buffer.readLine()) != null ) 
						{
							JSONObject vehicle = (JSONObject)parser.parse(currentJSONString);
							if(vehicle.get("Plate Number").equals(textPlateNumber.getText()))
						    {
								textPlateNumber.setText(vehicle.get("Plate Number").toString());
								textWheelManufacturer.setText(vehicle.get("Wheel Manufacturer").toString());
								textFieldModel.setText(vehicle.get("Model").toString());
								textEngineCap.setText(vehicle.get("Engine Capacity").toString());
								textWheelAirPressure.setText(vehicle.get("Wheel Air Pressure").toString());
								comboBoxColor.setSelectedItem(vehicle.get("Color"));
								comboBoxEngineType.setSelectedItem(vehicle.get("Engine Type"));
								comboBoxFuelType.setSelectedItem(vehicle.get("Fuel Type"));
								comboBoxNumOfDoors.setSelectedItem(vehicle.get("NumOfDoors"));
								comboBoxNumOfWheels.setSelectedItem(vehicle.get("NumOfWheels"));
								comboBoxVehicleType.setSelectedItem(vehicle.get("Type"));
								comboBoxLicenseType.setSelectedItem(vehicle.get("License Type"));
								vehicleFound = true;
						    	break;
						    }
						}
						reader = new FileReader("Clients.json");
						buffer = new BufferedReader(reader);
						currentJSONString  = "";
	
						while((currentJSONString = buffer.readLine()) != null ) 
						{
							JSONObject client = (JSONObject)parser.parse(currentJSONString);
							if(client.get("Vehicle#").equals(textPlateNumber.getText())) 
							{
								textClientName.setText(client.get("Name").toString());
								textClientPhoneNumber.setText(client.get("Phone").toString());
								comboBoxVehicleStatus.setSelectedItem(client.get("Status")); 
								break;
							}
						}
						buffer.close();
					}
					catch (FileNotFoundException e1) 
					{
			            e1.printStackTrace();
			        }
					catch (IOException e1) 
					{
			            e1.printStackTrace();
			        }
					catch (org.json.simple.parser.ParseException e1)
					{
						e1.printStackTrace();
					}
					comboBoxVehicleStatus.setEnabled(true);
					if(vehicleFound)
					{
						if(currentState.equals(currentState.ViewDetails))
						{
							ClientForm clientF = new ClientForm(controller.getVehicleFullDetails(textPlateNumber.getText()), "Full Vehicle Details");
							clientF.setTitle("Full Vehicle Details");
							clientF.setVisible(true);
						}
						
						if(currentState.equals(CurrentState.UpdateVehicle))
						{
							JOptionPane.showMessageDialog(panelFillForm, "Details are shown." + System.lineSeparator()+ "Status can be updated.");
						}
						
						if(currentState.equals(CurrentState.MakeAction))
						{
							Object[] possibilities = {"Select","Inflate air pressure to maximum", "Add energy to vehicle"};
							String s = (String)JOptionPane.showInputDialog(
						                    contentPane,
						                    "Please select Action:\n"
									                    ,
									                    "Make Action",
									                    JOptionPane.PLAIN_MESSAGE,
									                    null,
									                    possibilities,
									                    "Select");
									
							if (s.equals("Inflate air pressure to maximum"))
							{
								controller.inflateWheelsToMax((String)textPlateNumber.getText());
								controller.changeVehicleAirPressure((String)textPlateNumber.getText());
								JOptionPane.showMessageDialog(panelFillForm, "Wheels Air pressure fully filled!");
								
							}
							
							else if (s.equals("Add energy to vehicle"))
							{
								String energyType = controller.getEnergyType((String)textPlateNumber.getText());
								
								String FuelOrElectric = "";
								if(energyType.equals("Fuel")) 
								{
									FuelOrElectric = "in liters";
								}
								else
								{
									FuelOrElectric = "in minutes";
								}
								
								String s2 = (String)JOptionPane.showInputDialog(
							                    panelFillForm,
							                    "Please enter amount of energy to add " + FuelOrElectric
										                    ,
										                    "Add Energy",
										                    JOptionPane.PLAIN_MESSAGE,
										                    null,
										                    null,
										                    "Type here");
	
								if (energyType.equals("Fuel"))
								{
									
									if(controller.refuelVehicle((String)textPlateNumber.getText(),s2))
									{
										JOptionPane.showMessageDialog(panelFillForm, "Energy succesfully filled!");
										controller.changeVehicleEnergy((String)textPlateNumber.getText());
										
									}
									else
									{
										JOptionPane.showMessageDialog(panelFillForm, "Max energy can't be exceeded");
									}
									
								}
								
								if (energyType.equals("Electric"))
								{
									
									if(controller.chargeVehicle((String)textPlateNumber.getText(),s2))
									{
										JOptionPane.showMessageDialog(panelFillForm, "Energy succesfully filled!");
										controller.changeVehicleEnergy((String)textPlateNumber.getText());
									}
									else
									{
										JOptionPane.showMessageDialog(panelFillForm, "Max energy can't be exceeded");
									}
									
								}
								
								
							}
						}
					}
					else
					{
						JOptionPane.showMessageDialog(panelFillForm, "Vehicle not found!" + System.lineSeparator() + "Please make sure the plate number is correct.");
					}
				}
			}
		});
		
		
		
		btnMakeAction.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				panelFillForm.setVisible(true);
				currentState = CurrentState.MakeAction;
				panelFillForm.setVisible(true);
				btnSubmit.setVisible(false);
				textClientName.setVisible(false);
				textClientPhoneNumber.setVisible(false);
				textWheelManufacturer.setVisible(false);
				textFieldModel.setVisible(false);
				textEngineCap.setVisible(false);
				textWheelAirPressure.setVisible(false);
				comboBoxColor.setVisible(false);
				comboBoxEngineType.setVisible(false);
				comboBoxFuelType.setVisible(false);
				comboBoxNumOfDoors.setVisible(false);
				comboBoxNumOfWheels.setVisible(false);
				comboBoxVehicleStatus.setVisible(false);
				comboBoxVehicleType.setVisible(false);
				comboBoxLicenseType.setVisible(false);
				btnOK.setVisible(true);
				lblClientName.setVisible(false);
				lblClientPhone.setVisible(false);
				lblColor.setVisible(false);
				lblEngineCapacity.setVisible(false);
				lblEngineType.setVisible(false);
				lblFuelType.setVisible(false);
				lblLicenseType.setVisible(false);
				lblModel.setVisible(false);
				lblNumberOfDoors.setVisible(false);
				lblNumberOfWheels.setVisible(false);
				lblType.setVisible(false);
				lblWheelsAirPressure.setVisible(false);
				lblWheelsManufacturer.setVisible(false);
				lblStatus.setVisible(false);
			}	
		});
		
		btnAddVehicle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				panelFillForm.setVisible(true);
				currentState = CurrentState.AddVehicle;
				panelFillForm.setVisible(true);
				btnSubmit.setVisible(true);
				textClientName.setEnabled(true);
				textClientPhoneNumber.setEnabled(true);
				textWheelManufacturer.setEnabled(true);
				textFieldModel.setEnabled(true);
				textEngineCap.setEnabled(true);
				textWheelAirPressure.setEnabled(true);
				comboBoxColor.setEnabled(true);
				comboBoxEngineType.setEnabled(true);
				comboBoxFuelType.setEnabled(true);
				comboBoxNumOfDoors.setEnabled(true);
				comboBoxNumOfWheels.setEnabled(true);
				comboBoxVehicleStatus.setEnabled(true);
				comboBoxVehicleType.setEnabled(true);
				comboBoxLicenseType.setEnabled(true);
				textClientName.setVisible(true);
				textClientPhoneNumber.setVisible(true);
				textWheelManufacturer.setVisible(true);
				textFieldModel.setVisible(true);
				textEngineCap.setVisible(true);
				textWheelAirPressure.setVisible(true);
				comboBoxColor.setVisible(true);
				comboBoxEngineType.setVisible(true);
				comboBoxFuelType.setVisible(true);
				comboBoxNumOfDoors.setVisible(true);
				comboBoxNumOfWheels.setVisible(true);
				comboBoxVehicleStatus.setVisible(true);
				comboBoxVehicleType.setVisible(true);
				comboBoxLicenseType.setVisible(true);
				btnOK.setVisible(false);
				lblClientName.setVisible(true);
				lblClientPhone.setVisible(true);
				lblColor.setVisible(true);
				lblEngineCapacity.setVisible(true);
				lblEngineType.setVisible(true);
				lblFuelType.setVisible(true);
				lblLicenseType.setVisible(true);
				lblModel.setVisible(true);
				lblNumberOfDoors.setVisible(true);
				lblNumberOfWheels.setVisible(true);
				lblType.setVisible(true);
				lblWheelsAirPressure.setVisible(true);
				lblWheelsManufacturer.setVisible(true);
				lblStatus.setVisible(true);
			}
		});
		
		btnUpdateVehicle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				panelFillForm.setVisible(true);
				currentState = CurrentState.UpdateVehicle;
				btnSubmit.setVisible(true);
				panelFillForm.setVisible(true);
				textClientName.setEnabled(false);
				textClientPhoneNumber.setEnabled(false);
				textWheelManufacturer.setEnabled(false);
				textFieldModel.setEnabled(false);
				textEngineCap.setEnabled(false);
				textWheelAirPressure.setEnabled(false);
				comboBoxColor.setEnabled(false);
				comboBoxEngineType.setEnabled(false);
				comboBoxFuelType.setEnabled(false);
				comboBoxNumOfDoors.setEnabled(false);
				comboBoxNumOfWheels.setEnabled(false);
				comboBoxVehicleStatus.setEnabled(false);
				comboBoxVehicleType.setEnabled(false);
				comboBoxLicenseType.setEnabled(false);
				textClientName.setVisible(true);
				textClientPhoneNumber.setVisible(true);
				textWheelManufacturer.setVisible(true);
				textFieldModel.setVisible(true);
				textEngineCap.setVisible(true);
				textWheelAirPressure.setVisible(true);
				comboBoxColor.setVisible(true);
				comboBoxEngineType.setVisible(true);
				comboBoxFuelType.setVisible(true);
				comboBoxNumOfDoors.setVisible(true);
				comboBoxNumOfWheels.setVisible(true);
				comboBoxVehicleStatus.setVisible(true);
				comboBoxVehicleType.setVisible(true);
				comboBoxLicenseType.setVisible(true);
				btnOK.setVisible(true);
				lblClientName.setVisible(true);
				lblClientPhone.setVisible(true);
				lblColor.setVisible(true);
				lblEngineCapacity.setVisible(true);
				lblEngineType.setVisible(true);
				lblFuelType.setVisible(true);
				lblLicenseType.setVisible(true);
				lblModel.setVisible(true);
				lblNumberOfDoors.setVisible(true);
				lblNumberOfWheels.setVisible(true);
				lblType.setVisible(true);
				lblWheelsAirPressure.setVisible(true);
				lblWheelsManufacturer.setVisible(true);
				lblStatus.setVisible(true);
			}
		});
		
		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				if(currentState == CurrentState.AddVehicle)
				{	
					if(checkIfFilled(panelFillForm))
					{
						String resultAirPressure =controller.CheckAirPressureValidation((String)comboBoxVehicleType.getSelectedItem(),(String)textWheelAirPressure.getText());
						String resultEnergy = controller.CheckEnergyValidation((String)comboBoxVehicleType.getSelectedItem(),(String)comboBoxEngineType.getSelectedItem(),textEngineCap.getText());
						
						String minOrLtr = "";
						if (((String)comboBoxEngineType.getSelectedItem()).equals("Fuel"))
						{
							minOrLtr = "liters";
						}
						
						else
						{
							minOrLtr = "hours";
						}
						
						if(!resultAirPressure.equals("") || !resultEnergy.equals(""))
						{
							if(!resultAirPressure.equals(""))
							{
								JOptionPane.showMessageDialog(panelFillForm, "Air Pressure too high!" + System.lineSeparator() +"The max is:" + resultAirPressure);
							}
							
							if(!resultEnergy.equals(""))
							{
								JOptionPane.showMessageDialog(panelFillForm, "Engine Capacity too high!" + System.lineSeparator() +"The max is:" + resultEnergy+ " " + minOrLtr);
							}
							
							
						}
						else
						{
							JSONObject vehicle = new JSONObject();
							JSONObject client = new JSONObject();
							
							////////////////////////////////////////////////////////
							////////Creating Vehicle JSON//////////////////////////
							//////////////////////////////////////////////////////
							vehicle.put("Plate Number", textPlateNumber.getText());
							vehicle.put("Type", comboBoxVehicleType.getSelectedItem());
							vehicle.put("License Type", comboBoxLicenseType.getSelectedItem());
							vehicle.put("Model", textFieldModel.getText());
							vehicle.put("Engine Type", comboBoxEngineType.getSelectedItem());
							vehicle.put("Engine Capacity", textEngineCap.getText());
							vehicle.put("Fuel Type", comboBoxFuelType.getSelectedItem());
							vehicle.put("NumOfWheels", comboBoxNumOfWheels.getSelectedItem());
							vehicle.put("Wheel Manufacturer", textWheelManufacturer.getText());
							vehicle.put("Wheel Air Pressure", textWheelAirPressure.getText());
							vehicle.put("Color", comboBoxColor.getSelectedItem());
							vehicle.put("NumOfDoors", comboBoxNumOfDoors.getSelectedItem());
							controller.AddVehicleToFile(vehicle); //Write to file
							////////////////////////////////////////////////////////
							////////Creating Client JSON//////////////////////////
							//////////////////////////////////////////////////////
							client.put("Name", textClientName.getText());
							client.put("Phone", textClientPhoneNumber.getText());
							client.put("Status", comboBoxVehicleStatus.getSelectedItem());
							client.put("Vehicle#", textPlateNumber.getText());
							controller.AddClientToFile(client); //Write to file
							////////Creating Vehicle Object///////////////////////
							//////////////////////////////////////////////////////
							controller.addVehicle(vehicle, client);
							//////////////////////////////////////////////////////
							textPlateNumber.setText("");
							textClientName.setText("");
							textClientPhoneNumber.setText("");
							textWheelManufacturer.setText("");
							textFieldModel.setText("");
							textWheelAirPressure.setText("");
							textEngineCap.setText("");
							comboBoxColor.setSelectedIndex(0);
							comboBoxEngineType.setSelectedIndex(0);
							comboBoxEngineType.setSelectedIndex(0);
							comboBoxFuelType.setSelectedIndex(0);
							comboBoxNumOfDoors.setSelectedIndex(0);
							comboBoxNumOfWheels.setSelectedIndex(0);
							comboBoxVehicleStatus.setSelectedIndex(0);
							comboBoxVehicleType.setSelectedIndex(0);
							JOptionPane.showMessageDialog(panelFillForm, "Vehicle added succesfully!");
						}
					}
					else
					{
						JOptionPane.showMessageDialog(panelFillForm, "Form must be fully filled!");
					}
				}
				else if(currentState == CurrentState.UpdateVehicle) 
				{
					controller.changeVehicleStatus(textPlateNumber.getText(),(String)comboBoxVehicleStatus.getSelectedItem());
					JOptionPane.showMessageDialog(panelFillForm, "Status of Vehicle number: " + textPlateNumber.getText() + " updated succesfully!");
					textPlateNumber.setText("");
				}
			}
		});
		
		btnPrintByStatus.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				Object[] possibilities = {"Select","In Progress", "Fixed", "Paid"};
				String s = (String)JOptionPane.showInputDialog(
				                    contentPane,
				                    "Please select status:\n"
				                    ,
				                    "Print By Vehicle Status",
				                    JOptionPane.PLAIN_MESSAGE,
				                    null,
				                    possibilities,
				                    "Select");
				
				Report reportForm = new Report(controller.GenerateStringReport(s));
				reportForm.setVisible(true);
			}
		});
		
		btnViewFullDetails.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				panelFillForm.setVisible(true);
				currentState = CurrentState.ViewDetails;
				panelFillForm.setVisible(true);
				textClientName.setVisible(false);
				textClientPhoneNumber.setVisible(false);
				textWheelManufacturer.setVisible(false);
				textFieldModel.setVisible(false);
				textEngineCap.setVisible(false);
				textWheelAirPressure.setVisible(false);
				comboBoxColor.setVisible(false);
				comboBoxEngineType.setVisible(false);
				comboBoxFuelType.setVisible(false);
				comboBoxNumOfDoors.setVisible(false);
				comboBoxNumOfWheels.setVisible(false);
				comboBoxVehicleStatus.setVisible(false);
				comboBoxVehicleType.setVisible(false);
				comboBoxLicenseType.setVisible(false);
				btnOK.setVisible(true);
				btnSubmit.setVisible(false);
				lblClientName.setVisible(false);
				lblClientPhone.setVisible(false);
				lblColor.setVisible(false);
				lblEngineCapacity.setVisible(false);
				lblEngineType.setVisible(false);
				lblFuelType.setVisible(false);
				lblLicenseType.setVisible(false);
				lblModel.setVisible(false);
				lblNumberOfDoors.setVisible(false);
				lblNumberOfWheels.setVisible(false);
				lblType.setVisible(false);
				lblWheelsAirPressure.setVisible(false);
				lblWheelsManufacturer.setVisible(false);
				lblStatus.setVisible(false);
				
				
			}
		});
		
		
		contentPane.setLayout(null);
		
		////////////////////////////////////////////////////////////////////////
		////////////////Panel and ContentPane Layout///////////////////////////
		//////////////////////////////////////////////////////////////////////
		panelFillForm.setBorder(null);
		panelFillForm.setLayout(null);
		panelFillForm.add(lblStatus);
		panelFillForm.add(lblType);
		panelFillForm.add(lblPlateNumber);
		panelFillForm.add(lblEngineType);
		panelFillForm.add(lblModel);
		panelFillForm.add(lblNumberOfWheels);
		panelFillForm.add(comboBoxNumOfWheels);
		panelFillForm.add(comboBoxVehicleType);
		panelFillForm.add(comboBoxEngineType);
		panelFillForm.add(textFieldModel);
		panelFillForm.add(textPlateNumber);
		panelFillForm.add(lblClientName);
		panelFillForm.add(lblClientPhone);
		panelFillForm.add(lblFuelType);
		panelFillForm.add(comboBoxFuelType);
		panelFillForm.add(textClientPhoneNumber);
		panelFillForm.add(textClientName);
		panelFillForm.add(btnSubmit);
		panelFillForm.add(lblWheelsManufacturer);
		panelFillForm.add(lblColor);
		panelFillForm.add(lblNumberOfDoors);
		panelFillForm.add(comboBoxVehicleStatus);
		panelFillForm.add(comboBoxNumOfDoors);
		panelFillForm.add(comboBoxColor);
		panelFillForm.add(textWheelManufacturer);
		panelFillForm.add(lblEngineCapacity);
		panelFillForm.add(lblWheelsAirPressure);
		panelFillForm.add(lblLicenseType);
		panelFillForm.add(comboBoxLicenseType);
		panelFillForm.add(textWheelAirPressure);
		panelFillForm.add(btnOK);
		panelFillForm.add(textEngineCap);
		contentPane.add(panelFillForm);
		contentPane.add(lblVehicleManagement);
		contentPane.add(btnAddVehicle);
		contentPane.add(btnUpdateVehicle);
		contentPane.add(btnPrintByStatus);
		contentPane.add(btnViewFullDetails);
		contentPane.add(btnMakeAction);
	}
}
