package Model;

public class Motorcycle extends Vehicle
{
	private static final int k_NumberOfWheels = 2;
    private static final float k_MaxAirPressure = 33;
    private static final float k_MaxFuel = 8;
    private static final float k_MaxBetteryTime = 1.4f;

    public Motorcycle(String i_PlateNumber) 
    {
    	super(i_PlateNumber, k_NumberOfWheels);
    }

    public void CreateEngineAndWheels(String i_EnergyType)
    {
        TypeOfEngine(i_EnergyType);
        InitializeWheels(k_MaxAirPressure);
        if (m_Engine instanceof Fuel)
        {
            m_Engine.UpdateMax(k_MaxFuel);
            ((Fuel)m_Engine).setFuelType("Octan95");
        }
        else
        {
            m_Engine.UpdateMax(k_MaxBetteryTime);
        }
    }

   public void UpdateEnergy()
    {
	   UpdateCurrentEnergy(k_MaxFuel, k_MaxBetteryTime);
    }
}
