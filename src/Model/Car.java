package Model;

public class Car extends Vehicle
{
    private static final int k_NumberOfWheels = 4;
    private static final float k_MaxAirPressure = 31;
    private static final float k_MaxFuel = 55;
    private static final float k_MaxBetteryTime = 1.8f;

    public Car(String i_PlateNumber)
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
            ((Fuel)m_Engine).setFuelType("Octan96");
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