package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Model.*;

class CalculateCurrentEnergyTestTrue {

	@Test
	void test() {
		Fuel test = new Fuel();
		float output = test.CalculateCurrentEnergy(20, 40);
		assertEquals(200, output);
	}

}
