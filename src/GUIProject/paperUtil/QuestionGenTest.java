package GUIProject.paperUtil;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import GUIProject.paperUtil.QuestionGen.SupportedOperation;


public class QuestionGenTest {

	QuestionGen qg = new QuestionGen();
	ArrayList<Fraction> flt = new ArrayList<Fraction>();
	SupportedOperation[] sot = { SupportedOperation.ADD, SupportedOperation.MINUS, SupportedOperation.MULTIPLY,
			SupportedOperation.DIVIDE };

	@Test
	public void testGetanswer() {

		flt.add(new Fraction(1, 2));
		flt.add(new Fraction(1, 3));
		flt.add(new Fraction(1, 4));
		flt.add(new Fraction(1, 3));
		flt.add(new Fraction(1, 2));

		Fraction r = qg.getanswer(flt, sot);
		assertEquals("2/3", r.toString());

	}

	@Test
	public void testGenerateSimpleQuestion() {
		qg.generateSimpleQuestion();
	}

	@Test
	public void testGenerateCommonQuestion() {
		qg.generateCommonQuestion();
	}

	@Test
	public void testGenerateMediumQuestion() {
		qg.generateMediumQuestion();
	}

	@Test
	public void testGenerateComplexQuestion() {
		qg.generateComplexQuestion();
	}


}
