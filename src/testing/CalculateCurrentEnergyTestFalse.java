package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Model.Fuel;

class CalculateCurrentEnergyTestFalse {

	@Test
	void test() {
		Fuel test = new Fuel();
		float output = test.CalculateCurrentEnergy(40, 20);
		assertEquals(200, output);
	}

}
