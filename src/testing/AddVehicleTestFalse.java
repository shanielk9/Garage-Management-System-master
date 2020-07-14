package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Model.Car;
import Model.Garage;

class AddVehicleTestFalse {

	@Test
	void test()
	{
		Car c = new Car("1234");
		Garage g = new Garage();
		
		g.AddVehicle("1234", c, "Shain", "11");
		
		int output = g.GetVehicleMap().size();
		
		assertEquals(1, output);
	}
}


