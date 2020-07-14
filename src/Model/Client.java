package Model;

public class Client 
{

     private String m_Name;
     private String m_ClientPhoneNumber;
     private String m_VehicleStatus = "InProcess"; 


     public Client(String i_Name, String i_PhoneNumber)
     {
         m_Name = i_Name;
         m_ClientPhoneNumber = i_PhoneNumber;
     }

     public String getName()
     {
          return m_Name;
     }
     
     public String getVehicleStatus() 
     {
    	 return m_VehicleStatus;
     }
     
     public void setVehicleStatus(String m_VehicleStatus) {
		this.m_VehicleStatus = m_VehicleStatus;
	}

	public String getPhoneNumber() 
     {
    	 return m_ClientPhoneNumber;
     }
}
