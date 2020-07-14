package Controller;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JTable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import Model.*;

public class Mangment 
{
    private static final int k_CarMaxAirPressure = 31;
    private static final int k_MotorcycleMaxAirPressure = 33;
    private static final int k_TruckMaxAirPressure = 26;
    private static final float k_TruckEngineCapacity = 110;
    private static final float k_FuelCarEngineCapacity = 55;
    private static final float k_FuelMotorcycleEngineCapacity = 8;
    private static final float k_CarMaxBattery = 1.8f;
    private static final float k_MotorcycleMaxBattery = 1.4f;
    private Garage r_Garage = new Garage();
    private JSONArray jsonsToFile = new JSONArray();
    private JSONArray cJsonsToFile = new JSONArray();
    
    
    public final void addVehicle(JSONObject i_Vehicle, JSONObject i_Client)
    {
        String plateNumber = (String)i_Vehicle.get("Plate Number");
        String clientPhoneNumber = (String)i_Client.get("Phone");
        String clientName = (String)i_Client.get("Name");
        VehicleDetails details;
        Vehicle newVehicle;
   
        details = new VehicleDetails();
        newVehicle = this.createNewVehicle(plateNumber,(String)i_Vehicle.get("Type"),(String)i_Vehicle.get("Engine Type"));
        this.fillDetails(newVehicle, details,i_Vehicle, i_Client);
        newVehicle.InsertDetails(details);
        this.r_Garage.AddVehicle(plateNumber, newVehicle, clientName, clientPhoneNumber);
    }
        
    private final Vehicle createNewVehicle(String i_PlateNumber, String i_Type ,String i_EngineType) 
    {
        Vehicle newVehicle;
        newVehicle = VehicleFactory.CreateVehicle(i_PlateNumber, i_Type);
        newVehicle.CreateEngineAndWheels(i_EngineType);
        return newVehicle;
    }
    
    private final void fillDetails(Vehicle i_Vehicle, VehicleDetails i_Details,JSONObject i_JSVehicle, JSONObject i_JSClient) 
    {
        this.fillBasicInformation(i_Vehicle,i_JSVehicle);
        i_Details.setCarColor((String)i_JSVehicle.get("Color"));
        i_Details.setNumberOfDoors(Integer.parseInt((String)i_JSVehicle.get("NumOfDoors")));
        i_Details.setLicenseType((String)i_JSVehicle.get("License Type"));
    }
        
    private final void fillBasicInformation(Vehicle i_Vehicle,JSONObject i_JSVehicle) {
        boolean validEnergy = false;
        boolean validAirPressure = false;
        float currentAirPressure;
        String manufacturerName;
        i_Vehicle.setModel((String)i_JSVehicle.get("Model"));
        while (!validEnergy) {
            {
            	i_Vehicle.AddEnergy(Float.parseFloat((String)i_JSVehicle.get("Engine Capacity")));
                validEnergy = true;
            }
            
        }
        
        while (!validAirPressure) 
        {
            	currentAirPressure = Float.parseFloat((String)i_JSVehicle.get("Wheel Air Pressure"));
            	manufacturerName = (String)i_JSVehicle.get("Wheel Manufacturer");
                i_Vehicle.InitializeWheelsInfo(manufacturerName, currentAirPressure);
                validAirPressure = true;
       }
        
    }
    
    public final void changeVehicleStatus(String i_PlateNumber,String i_StatusToChange) 
    {    
    	for(Object o : cJsonsToFile) 
    	{
    		if(((JSONObject)o).get("Vehicle#").equals(i_PlateNumber)) 
    		{
    			((JSONObject)o).replace("Status", i_StatusToChange);
    		}
    	}
    	UpdateClientsFile();
    }
    
    public final void changeVehicleAirPressure(String i_PlateNumber) 
    {    
    	Vehicle v = r_Garage.GetVehicle(i_PlateNumber);
    	float MaxAirPressure = v.getWheel().getCurrentAirPressure();
    	String MaxAirPressureString = Float.toString(MaxAirPressure);
    	
    	for(Object o : jsonsToFile) 
    	{
    		if(((JSONObject)o).get("Plate Number").equals(i_PlateNumber)) 
    		{
    			((JSONObject)o).replace("Wheel Air Pressure", MaxAirPressureString);
    		}
    	}
    	UpdateVehiclesFile();
    }
    
    public final void changeVehicleEnergy(String i_PlateNumber) 
    {    
    	Vehicle v = r_Garage.GetVehicle(i_PlateNumber);
    	float EnergyCap = v.getCurrentEnergy();
    	String EnergyCapString = Float.toString(EnergyCap);
    	
    	for(Object o : jsonsToFile) 
    	{
    		if(((JSONObject)o).get("Plate Number").equals(i_PlateNumber)) 
    		{
    			((JSONObject)o).replace("Engine Capacity", EnergyCapString);
    		}
    	}
    	UpdateVehiclesFile();
    }
    
    public final void inflateWheelsToMax(String i_PlateNumber) 
    {
        this.r_Garage.InflateVehicleWheelsToMax(i_PlateNumber);  
    }
    
    public String getEnergyType(String i_PlateNumber) 
    {
    	Vehicle vehicle = r_Garage.GetVehicle(i_PlateNumber);
    	EnergySource engine = vehicle.getEngine();
    	
    	if(engine instanceof Fuel)
    	{
    		return "Fuel";
    	}
    	else 
    	{
    		return "Electric";
    	}
    }
    
    public final boolean refuelVehicle(String i_PlateNumber, String i_FuelToAdd) 
    {
        float fuelToAdd = Float.parseFloat(i_FuelToAdd);
        
        return this.r_Garage.RefuelVehicle(i_PlateNumber, fuelToAdd);
        
        
    }
    
    public final boolean chargeVehicle(String i_PlateNumber, String i_BatteryToAdd) {
        
        float timeToAdd = Float.parseFloat(i_BatteryToAdd);
        return this.r_Garage.ChargeVehicle(i_PlateNumber, timeToAdd);
    }
    
    public final String getClientFullDetails(String i_PlateNumber) 
    {
        return this.r_Garage.GetClientVehicleDetails(i_PlateNumber);
    }
    
    public final String getVehicleFullDetails(String i_PlateNumber) 
    {
        return this.r_Garage.GetVehicleDetails(i_PlateNumber);
    }
    
    private void UpdateClientsFile() 
    {
    	try (FileWriter file = new FileWriter("Clients.json")) 
    	{
    		BufferedWriter bw = new BufferedWriter(file);
    		for(Object j : cJsonsToFile) 
    		{
    			bw.write(((JSONObject)j).toJSONString());
    			bw.newLine();
    		}
    		bw.close();
        }
    	catch (IOException e) 
    	{
            e.printStackTrace();
        }
    }
    
    private void UpdateVehiclesFile() 
    {
    	try (FileWriter file = new FileWriter("Vehicles.json")) 
    	{
    		BufferedWriter bw = new BufferedWriter(file);
    		for(Object j : jsonsToFile) 
    		{
    			bw.write(((JSONObject)j).toJSONString());
    			bw.newLine();
    		}
    		bw.close();
        }
    	catch (IOException e) 
    	{
            e.printStackTrace();
        }
    }
    
    public void intializeJSONArray()
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
				jsonsToFile.add(vehicle);
			}
			
			reader = new FileReader("Clients.json");
			buffer = new BufferedReader(reader);
			while((currentJSONString = buffer.readLine()) != null ) 
			{
				JSONObject client = (JSONObject)parser.parse(currentJSONString);
				cJsonsToFile.add(client);
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
    }
    
    public void AddVehicleToFile(JSONObject i_Vehicle)
    {
    	jsonsToFile.add(i_Vehicle);

    	try (FileWriter file = new FileWriter("Vehicles.json")) 
    	{
    		BufferedWriter bw = new BufferedWriter(file);
    		for(Object j : jsonsToFile) 
    		{
    			bw.write(((JSONObject)j).toJSONString());
    			bw.newLine();
    		}
    		bw.close();
        }
    	catch (IOException e) 
    	{
            e.printStackTrace();
        }
    }
    
    public void AddClientToFile(JSONObject i_Client)
    {
    	cJsonsToFile.add(i_Client);

    	try (FileWriter file = new FileWriter("Clients.json")) 
    	{
    		BufferedWriter bw = new BufferedWriter(file);
    		for(Object j : cJsonsToFile) 
    		{
    			bw.write(((JSONObject)j).toJSONString());
    			bw.newLine();
    		}
    		bw.close();
        }
    	catch (IOException e) 
    	{
            e.printStackTrace();
        }
    }
    
    public String CheckAirPressureValidation(String i_VehicleType, String i_AirPressureToAdd)
    {
    	String isValid="";
    	int airPressure = Integer.parseInt(i_AirPressureToAdd);
    	if((i_VehicleType.equals("Car"))&&(airPressure > k_CarMaxAirPressure))
    	{
    		isValid=Integer.toString(k_CarMaxAirPressure);
    	}
    	if((i_VehicleType.equals("Motorcycle"))&&(airPressure > k_MotorcycleMaxAirPressure))
    	{
    		isValid=Integer.toString(k_MotorcycleMaxAirPressure);
    	}
    	if((i_VehicleType.equals("Truck"))&&(airPressure > k_TruckMaxAirPressure))
    	{
    		isValid=Integer.toString(k_TruckMaxAirPressure);
    	}
    	
    	return isValid;
    }
    
    public String CheckEnergyValidation(String i_VehicleType, String i_EngineType, String i_EnergyToAdd)
    {
    	float energyToAdd = Float.parseFloat(i_EnergyToAdd);
    	String isValid= "";
    	
    	if(i_VehicleType.equals("Truck") && energyToAdd > k_TruckEngineCapacity)
    	{
    		isValid = Float.toString(k_TruckEngineCapacity);
    	}
    	
    	if(i_VehicleType.equals("Car"))
    	{
    		if(i_EngineType.equals("Fuel") && energyToAdd > k_FuelCarEngineCapacity)
    		{
    			isValid = Float.toString(k_FuelCarEngineCapacity);
    		}
    		
    		if(i_EngineType.equals("Electric") && energyToAdd > k_CarMaxBattery)
    		{
    			isValid = Float.toString(k_CarMaxBattery);
    		}
    		
    	}
    	
    	if(i_VehicleType.equals("Motorcycle"))
    	{
    		if(i_EngineType.equals("Fuel") && energyToAdd > k_FuelMotorcycleEngineCapacity)
    		{
    			isValid = Float.toString(k_FuelMotorcycleEngineCapacity);
    		}
    		
    		if(i_EngineType.equals("Electric") && energyToAdd > k_MotorcycleMaxBattery)
    		{
    			isValid = Float.toString( k_MotorcycleMaxBattery);
    		}
    		
    	}
    	
    	return isValid;
    }
    
    public JTable GenerateStringReport(String i_Status) 
    {
    	ArrayList<String> relevantPN = new ArrayList<String>();    	
    	String[] columnNames = {"index",
    			"Plate number",
    			"Engine type",
    			"Vehicle type",
    			"Color",
    			"Model",
    			"Engine Capacity",
    			"Wheel Manufacturer",
    			"Wheel Air Pressure",
    			"License Type",
    			"Num Of Wheels",
    			"Num Of Doors",
    			"Fuel Type"};
    	
    	int i = 0;
    	for(Object o : cJsonsToFile) 
    	{
    		if(((JSONObject)o).get("Status").equals(i_Status))
    		{
    			i++;
    			relevantPN.add((String)((JSONObject)o).get("Vehicle#"));
    		}
    	}
    	
    	String[][] rows = new String[i+1][13];
		
		int j = 0;
		
		for(String s: columnNames)
		{
			rows[0][j] = s;
			j++;
		}
		
		int k=0;
		j=1;
    	for(Object o : jsonsToFile) 
    	{
    		if(!relevantPN.isEmpty())
    		{
    			if(relevantPN.get(k).equals((String)((JSONObject)o).get("Plate Number"))) 
	    		{
    				rows[j][0] = Integer.toString(k+1);
    				rows[j][1] = (String)((JSONObject)o).get("Plate Number");
    				rows[j][2] = (String)((JSONObject)o).get("Engine Type");
    				rows[j][3] =(String)((JSONObject)o).get("Type");
    				rows[j][4] =(String)((JSONObject)o).get("Color");
    				rows[j][5] =(String)((JSONObject)o).get("Model");
    				rows[j][6] =(String)((JSONObject)o).get("Engine Capacity");
    				rows[j][7] =(String)((JSONObject)o).get("Wheel Manufacturer");
    				rows[j][8] =(String)((JSONObject)o).get("Wheel Air Pressure");
    				rows[j][9] =(String)((JSONObject)o).get("License Type");
    				rows[j][10] =(String)((JSONObject)o).get("NumOfWheels");
    				rows[j][11] =(String)((JSONObject)o).get("NumOfDoors");
    				rows[j][12] =(String)((JSONObject)o).get("Fuel Type");
    				
    				j++;	
    				k++;
	    			if(j >= (relevantPN.size()+1)) 
	    			{
	    				break;
	    			}
	    		}
    		}
    	}
    	
    	JTable table = new JTable(rows,columnNames);
    	return table;
    }
    
    public void initializeGarageDictionaries() 
	{
		try 
    	{	
	    	FileReader vReader = new FileReader("Vehicles.json");
			BufferedReader vBuffer = new BufferedReader(vReader);
			FileReader cReader = new FileReader("Clients.json");
			BufferedReader cBuffer = new BufferedReader(cReader);
			JSONParser parser = new JSONParser();
			String currentVehicleJSONString  = "";
			String currentClientJSONString  = "";
	
			while((currentVehicleJSONString = vBuffer.readLine()) != null && ((currentClientJSONString = cBuffer.readLine()) != null )) 
			{
				JSONObject vehicle = (JSONObject)parser.parse(currentVehicleJSONString);
				JSONObject client = (JSONObject)parser.parse(currentClientJSONString);
				addVehicle(vehicle, client);	
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
	}
}
