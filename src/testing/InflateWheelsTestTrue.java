package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Model.Wheel;

class InflateWheelsTestTrue {

	@Test
	void test() {
		Wheel w = new Wheel(30);
		boolean output = w.Inflate(20);
		assertEquals(true, output);
	}

}
