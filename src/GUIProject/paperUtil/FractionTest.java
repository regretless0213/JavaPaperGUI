package GUIProject.paperUtil;

import static org.junit.Assert.*;

import org.junit.Test;

public class FractionTest {

	Fraction f1 = new Fraction(9, 4);
	Fraction f2 = new Fraction(16, 4);

	@Test
	public void testdown0() {
		new Fraction(0, 0);
	}

	@Test
	public void testGcd() {
		assertEquals("4", f2.gcd(f2).toString());
	}

	@Test
	public void testToString() {
		assertEquals("2 1/4", f1.toString());
	}

	@Test
	public void testAddFraction() {
		assertEquals(new Fraction(25, 4).toString(), f1.add(f2).toString());
	}

	@Test
	public void testMinusFraction() {
		assertEquals(new Fraction(-7, 4).toString(), f1.minus(f2).toString());
	}

	@Test
	public void testMultiplyFraction() {
		assertEquals(new Fraction(144, 16).toString(), f1.multiply(f2).toString());
	}

	@Test
	public void testDivideFraction() {
		assertEquals(new Fraction(9, 16).toString(), f1.divide(f2).toString());
	}

	@Test
	public void testChangeSign() {
		assertEquals(new Fraction(-9, 4).toString(), f1.changeSign().toString());
	}

	@Test
	public void testGetRandiomInt() {
		Fraction a = Fraction.getRandiom(7);
		System.out.println(a.toString());
	}

	@Test
	public void testGetRandiomIntBoolean() {
		Fraction a = Fraction.getRandiom(7, true);
		Fraction b = Fraction.getRandiom(7, true);
		System.out.println("true = " + a.toString() + "\nfalse = " + b.toString());
	}

	
}
