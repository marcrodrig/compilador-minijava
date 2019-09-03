package test.java;

import main.Principal;

import org.junit.jupiter.api.Test;

class SintacticoTest {

	@Test
	void test() {
		String[] args = {"src/test/resources/TestRapido.txt"};
		Principal.main(args);
	}

}
