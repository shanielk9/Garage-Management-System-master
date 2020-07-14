package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Garage 
{
    private final Map<String, Vehicle> r_GarageVehicles;
    private final Map<String, Client> r_VehicleClients;

    public Garage()
    {
        r_GarageVehicles = new HashMap<String, Vehicle>();
        r_VehicleClients = new HashMap<String, Client>();
    }
    
	public static <T> T as(Object o, Class<T> tClass) 
	{
	     return tClass.isInstance(o) ? (T) o : null;
	}

	public Vehicle GetVehicle(String i_PlateNumber)
	{
		Vehicle result = r_GarageVehicles.get(i_PlateNumber);
		
		return result;
	}
	
    public void AddVehicle(String i_PlateNumber, Vehicle i_Vehicle, String i_ClientName, String i_ClientPhoneNumber)
    {
        Client vehicleClient = new Client(i_ClientName, i_ClientPhoneNumber);
        if (r_GarageVehicles.get(i_PlateNumber) == null)
        {
            r_GarageVehicles.put(i_PlateNumber, i_Vehicle);
            r_VehicleClients.put(i_PlateNumber, vehicleClient);
        }
        else
        {
        	return;
        }
    }

    public String GetClientVehicleDetails(String i_PlateNumber)
    {
        Vehicle vehicle;
        Client client;
        String result, engineDetails, wheelsDetails, modelDetails;

        if (!IsVehicleExists(i_PlateNumber))
        {
           return null;
        }

        vehicle = r_GarageVehicles.get(i_PlateNumber);
        client = r_VehicleClients.get(i_PlateNumber);
        engineDetails = vehicle.GetEngineDetails();
        wheelsDetails = vehicle.GetWheelsDetails();
        modelDetails = vehicle.getModel();
        result = String.format("Plate number: %s" + System.lineSeparator() + System.lineSeparator() + "Model: %s." + System.lineSeparator() + System.lineSeparator() + 
        		"Client name: %s " + System.lineSeparator() + System.lineSeparator() + "Client Phone Number: %s" + System.lineSeparator() + System.lineSeparator() + "Status: %s." + System.lineSeparator() + System.lineSeparator() +
        		"Wheel details:" + System.lineSeparator() + "%s" + System.lineSeparator() + System.lineSeparator() + "Engine details: %s", i_PlateNumber,
        		modelDetails,client.getName(),client.getPhoneNumber(),client.getVehicleStatus(),wheelsDetails,engineDetails);

        return result;
    }
    
    public String GetVehicleDetails(String i_PlateNumber)
    {
        Vehicle vehicle;
        Client client;
        String result, engineDetails, wheelsDetails, modelDetails;

        if (!IsVehicleExists(i_PlateNumber))
        {
           return null;
        }

        vehicle = r_GarageVehicles.get(i_PlateNumber);
        client = r_VehicleClients.get(i_PlateNumber);
        engineDetails = vehicle.GetEngineDetails();
        wheelsDetails = vehicle.GetWheelsDetails();
        modelDetails = vehicle.getModel();
        result = String.format("Plate number: %s" + System.lineSeparator() + 
        						"Model: %s." + System.lineSeparator() + 
        						"Client name: %s " + System.lineSeparator() + 
        						"Client Phone Number: %s" + System.lineSeparator() + 
        						"%s" + System.lineSeparator() + 
        						"Status: %s." + System.lineSeparator() +
        						"Wheel details:" + System.lineSeparator() + "%s" + System.lineSeparator() + 
        						"Engine details: %s", 
        						i_PlateNumber, 
        						modelDetails,
        						client.getName(),
        						client.getPhoneNumber(),
        						vehicle.detailsToString(),
        						client.getVehicleStatus(),
        						wheelsDetails,
        						engineDetails);

        return result;
    }

    public List<String> GetVehiclesDetailsByStatus(String i_Status, boolean i_AllVehicles)
    {
        List<String> details = new ArrayList<String>();

        for(Map.Entry<String, Client> entry : r_VehicleClients.entrySet())
        {
            if (i_AllVehicles || i_Status == ((Client) entry).getVehicleStatus())
            {
                details.add(entry.getKey());
            }
        }

        return details;
    }

    public void ChangeStatus(String i_PlateNumber, String i_NewStatus)
    {
        boolean check = r_GarageVehicles.containsKey(i_PlateNumber) == true ? true : false;
        if (check)
        {
            r_VehicleClients.get(i_PlateNumber).setVehicleStatus(i_NewStatus);
        }
       
    }

    public void InflateVehicleWheelsToMax(String i_PlateNumber)
    {
        r_GarageVehicles.get(i_PlateNumber).InflateToMax();
    }

    public boolean RefuelVehicle(String i_PlateNumber, float i_FuelToAdd)
    {
    	boolean result = false;
    	if(r_GarageVehicles.get(i_PlateNumber).AddEnergy(i_FuelToAdd))
    	{
    		r_GarageVehicles.get(i_PlateNumber).UpdateEnergy();
    		result = true;
    	}
    	
    	return result;
    }

    public boolean ChargeVehicle(String i_PlateNumber, float i_BatteryTimeToAdd)
    {
        i_BatteryTimeToAdd = ((int)((i_BatteryTimeToAdd / 60) * 10)) / 10.0f; 
        
        boolean result = false;
        
        if( r_GarageVehicles.get(i_PlateNumber).AddEnergy(i_BatteryTimeToAdd))
        {
        	r_GarageVehicles.get(i_PlateNumber).UpdateEnergy();
        	result = true;
        }
       
        return result;
    }

    public Boolean IsVehicleExists(String i_PlateNumber)
    {
        return r_GarageVehicles.containsKey(i_PlateNumber);
    }

	public Map<String, Vehicle> GetVehicleMap() {
		return r_GarageVehicles;
	}
	
	

 }
