package Model;

public class Truck extends Vehicle
{
	private static final int k_NumberOfWheels = 12;
	private static final int k_MaxAirPressure = 26;
	private static final int k_MaxFuel = 110;
	private static final int k_NoneBattery = 0;

    public Truck(String i_PlateNumber) 
    {
    	super(i_PlateNumber, k_NumberOfWheels);
    }

    public void CreateEngineAndWheels(String i_EnergyType)
    {
        TypeOfEngine(i_EnergyType);
        InitializeWheels(k_MaxAirPressure);
        m_Engine.UpdateMax(k_MaxFuel);
        ((Fuel)m_Engine).setFuelType("Soler");
    }

    public void UpdateEnergy()
    {
        UpdateCurrentEnergy(k_MaxFuel, k_NoneBattery);
    }
}
