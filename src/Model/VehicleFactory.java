package Model;

public class VehicleFactory
{
    public static Vehicle CreateVehicle(String i_PlateNumber, String i_VehicleType)
    {
        Vehicle newVehicle = null;

        if (i_VehicleType.equals("Motorcycle"))
        {
            newVehicle = new Motorcycle(i_PlateNumber);
        }
        else if (i_VehicleType.equals("Car"))
        {
            newVehicle = new Car(i_PlateNumber);
        }
        else if (i_VehicleType.equals("Truck"))
        {
            newVehicle = new Truck(i_PlateNumber);
        }

        return newVehicle;
    }
}
