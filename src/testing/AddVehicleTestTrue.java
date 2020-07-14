package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import Model.*;

class AddVehicleTestTrue {

	@Test
	void test() {
		Car c = new Car("1234");
		Garage g = new Garage();
		
		g.AddVehicle("1234", c, "Shain", "11");
		
		int output = g.GetVehicleMap().size();
		
		assertEquals(0, output);
	}

}
