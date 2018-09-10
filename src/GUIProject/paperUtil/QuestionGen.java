package GUIProject.paperUtil;

import java.util.ArrayList;
import java.util.Stack;
import java.util.TreeMap;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;;


public class QuestionGen {

	public String answer;

	Stack<SupportedOperation> oplist = new Stack<SupportedOperation>();
	Stack<Fraction> numlist = new Stack<Fraction>();
	ArrayList<Integer[]> bclist;
	TreeMap<Integer, Integer> frequency;
	TreeMap<Integer, Integer> direction;

	private void getBcPrint(int numOfOperand) {
		bclist = new ArrayList<Integer[]>();
		if (numOfOperand > 2) {
			int bcnum = (int) (Math.random() * (numOfOperand - 2));
			for (int n = 0; n < bcnum; n++) {
				Integer[] bracket = new Integer[2];
				bracket[0] = (int) (Math.random() * (numOfOperand - 2));
				bracket[1] = (int) (Math.random() * (numOfOperand - 2 - bracket[0]) + bracket[0]);
				if (bracket[0] == bracket[1]) {
					bracket[1]++;
				}
				boolean canput = true;
				for (int i = 0; i < bclist.size(); i++) {
					Integer[] tmp = bclist.get(i);
					if (bracket[0] < tmp[0] & bracket[1] >= tmp[0] & bracket[1] < tmp[1]) {
						canput = false;
						break;
					} else if (bracket[1] > tmp[1] & bracket[0] > tmp[0] & bracket[0] <= tmp[1]) {
						canput = false;
						break;
					} else if (bracket[0] == tmp[0] & bracket[1] == tmp[1]) {

					}
				}
				if (canput) {
					bclist.add(bracket);
				}
			}

		}
		frequency = new TreeMap<Integer, Integer>();
		direction = new TreeMap<Integer, Integer>();
		for (int i = 0; i < bclist.size(); i++) {
			Integer[] tmp = bclist.get(i);
			if (frequency.containsKey(tmp[0])) {
				frequency.put(tmp[0], frequency.get(tmp[0]) + 1);
			} else {
				frequency.put(tmp[0], 1);
				direction.put(tmp[0], 0);
			}
			if (frequency.containsKey(tmp[1])) {
				frequency.put(tmp[1], frequency.get(tmp[1]) + 1);
			} else {
				frequency.put(tmp[1], 1);
				direction.put(tmp[1], 1);
			}
		}
	}

	public Fraction getanswer(ArrayList<Fraction> frlist, SupportedOperation[] so) {

		numlist.push(frlist.get(0));
		for (int n = 0; n < so.length; n++) {
			switch (so[n]) {
			case ADD:
				oplist.push(so[n]);
				numlist.push(frlist.get(n + 1));
				break;
			case MINUS:
				oplist.push(SupportedOperation.ADD);
				numlist.push(frlist.get(n + 1).changeSign());
				break;
			case MULTIPLY: {
				Fraction r = numlist.pop().multiply(frlist.get(n + 1));
				numlist.push(r);
			}
				break;
			case DIVIDE: {
				Fraction r = numlist.pop().divide(frlist.get(n + 1));
				numlist.push(r);
			}
				break;
			default:
				System.out.println("不支持的运算");
				break;
			}
		}

		while (!oplist.isEmpty()) {
			Fraction answer = numlist.pop();
			switch (oplist.pop()) {
			case ADD: {
				answer = answer.add(numlist.pop());
				numlist.push(answer);
			}
				break;
//			case MINUS: {
//				answer = answer.minus(numlist.pop());
//				numlist.push(answer);
//			}
//				break;
			default:
				System.out.println("不支持的运算");
				break;
			}

		}

		return numlist.pop();
	}

	public enum SupportedOperation {
		ADD, MINUS, MULTIPLY, DIVIDE/* , SQUARE, CUBE, RADICAL */, ALL;
		public String toString() {
			String op = "";
			switch (this) {
			case ADD:
				op = "+";
				break;
			case MINUS:
				op = "-";
				break;
			case MULTIPLY:
				op = "*";
				break;
			case DIVIDE:
				op = "/";
				break;
			case ALL:
				op = "#";
			}
			return op;
		}
	}

	public String generateSimpleQuestion() {
		SupportedOperation[] operation = { SupportedOperation.ADD, SupportedOperation.MINUS };
		return generateQuestion(2, 0, 20, operation, false, false);
	}

	public String generateCommonQuestion() {
		SupportedOperation[] operation = { SupportedOperation.ADD, SupportedOperation.MINUS,
				SupportedOperation.MULTIPLY, SupportedOperation.DIVIDE };
		return generateQuestion(4, 0, 20, operation, false, true);
	}

	public String generateMediumQuestion() {
		SupportedOperation[] operation = { SupportedOperation.ADD, SupportedOperation.MINUS,
				SupportedOperation.MULTIPLY, SupportedOperation.DIVIDE };
		return generateQuestion(4, 0, 20, operation, true, true);
	}

	public String generateComplexQuestion() {
		SupportedOperation[] operation = { SupportedOperation.ALL };
		return generateQuestion(6, 0, 20, operation, true, true);
	}

	public String generateQuestion(int numOfOperand, int rangeMin, int rangMax, SupportedOperation[] operation,
			boolean isFractional, boolean hasbracket) {
		String question = "";
		int[] ioperands = null;
		ArrayList<Fraction> af = new ArrayList<Fraction>();
		SupportedOperation[] so = null;
		if (numOfOperand < 2) {
			System.out.println("操作数数量至少为2");
			return "";
		}
		if (rangMax > 500) {
			System.out.println("操作数数最大值不能超过500");
			return "";
		}
		getBcPrint(numOfOperand);
		if (!isFractional) {
			ScriptEngine se = new ScriptEngineManager().getEngineByName("JavaScript");
			ioperands = new int[numOfOperand];
			for (int i = 0; i < numOfOperand; i++) {
				ioperands[i] = (int) (Math.random() * rangMax / 2 + 1);

			}
			so = new SupportedOperation[numOfOperand - 1];
			for (int i = 0; i < operation.length; i++) {
				if (operation[i] == SupportedOperation.ALL) {
					operation = new SupportedOperation[4];
					operation[0] = SupportedOperation.ADD;
					operation[1] = SupportedOperation.MINUS;
					operation[2] = SupportedOperation.MULTIPLY;
					operation[3] = SupportedOperation.DIVIDE;

				}
			}
			// 除法運算，保证整除
			int value = 0;
			for (int j = numOfOperand - 1; j > 0; j--) {
				so[numOfOperand - 1 - j] = operation[(int) (Math.random() * operation.length)];
			}
			for (int j = numOfOperand - 2; j >= 0; j--) {
				if (so[j] == SupportedOperation.DIVIDE) {
					if (value < 1) {
						ioperands[j] = ioperands[j] * ioperands[j + 1];
						value++;

					} else {
						so[j] = operation[(int) (Math.random() * (operation.length - 2))];
					}
				}
			}
			// 输出括号
			for (int i = 0; i < numOfOperand - 1; i++) {
				if (frequency.containsKey(i)) {
					if (direction.get(i) == 0) {
						for (int k = 0; k < frequency.get(i); k++) {
							question += "(";
						}
					}
				}
				question += ioperands[i];
				if (frequency.containsKey(i)) {
					if (direction.get(i) == 1) {
						for (int k = 0; k < frequency.get(i); k++) {
							question += ")";
						}
					}
				}
				question += so[i];
			}
			if (frequency.containsKey(numOfOperand - 1)) {
				if (direction.get(numOfOperand - 1) == 0) {
					for (int k = 0; k < frequency.get(numOfOperand - 1); k++) {
						question += "(";
					}
				}
			}
			question += ioperands[numOfOperand - 1];
			if (frequency.containsKey(numOfOperand - 1)) {
				if (direction.get(numOfOperand - 1) == 1) {
					for (int k = 0; k < frequency.get(numOfOperand - 1); k++) {
						question += ")";
					}
				}
			}

			try {
				Integer d = (Integer) se.eval(question);
				answer = "" + d;
			} catch (Exception e) {
				generateQuestion(numOfOperand, rangeMin, rangMax, operation, isFractional, hasbracket);
			}

		} else {
			for (int i = 0; i < numOfOperand; i++) {
				af.add(Fraction.getRandiom(rangMax));
			}

			so = new SupportedOperation[numOfOperand - 1];
			for (int i = 0; i < operation.length; i++) {
				if (operation[i] == SupportedOperation.ALL) {
					operation = new SupportedOperation[4];
					operation[0] = SupportedOperation.ADD;
					operation[1] = SupportedOperation.MINUS;
					operation[2] = SupportedOperation.MULTIPLY;
					operation[3] = SupportedOperation.DIVIDE;

				}
			}
			question += af.get(0);
			for (int j = 0; j < numOfOperand - 1; j++) {
				so[j] = operation[(int) (Math.random() * operation.length)];
				question += (so[j] == SupportedOperation.DIVIDE ? "÷" : so[j].toString()) + af.get(j + 1);

			}
			answer = getanswer(af, so).toString();
			try {
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return question;

	}
}


