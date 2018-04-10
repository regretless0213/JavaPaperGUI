package GUIProject.paperUtil;

public class Fraction {
	public int up;
	public int down;

	public Fraction(int up, int down) {
		if (down == 0 || up == 0) {
			System.out.println("divided by zero error");
			return;
		}
		int smaller = up > down ? up : down;
		int maxCommonFactor = 1;
		for (int i = 1; i <= smaller; i++) {
			if (up % i == 0 && down % i == 0) {
				maxCommonFactor = i;
			}
		}

		this.up = up / maxCommonFactor;
		this.down = down / maxCommonFactor;
	}


	public Fraction gcd(Fraction f) {
		int smaller = f.up > f.down ? f.up : f.down;
		int maxCommonFactor = 1;
		for (int i = 1; i <= smaller; i++) {
			if (f.up % i == 0 && f.down % i == 0) {
				maxCommonFactor = i;
			}
		}
		f.up = f.up / maxCommonFactor;
		f.down = f.down / maxCommonFactor;

		return f;
	}

	public String toString() {
		if (down == 1)
			return "" + up;
		if(Math.abs(up)/down>0){
			return up>0?up/down+" "+up%down+"/"+down:"-"+Math.abs(up)/down+" "+Math.abs(up)%down+"/"+down;
		}
		return up + "/" + down;
	}

	public Fraction add(Fraction f) {
		Fraction a = new Fraction(up, down);
		a.up = f.up * a.down + a.up * f.down;
		a.down = a.down * f.down;

		return a.gcd(a);
	}

	public Fraction minus(Fraction f) {
		Fraction a = new Fraction(up, down);
		a.up = a.up * f.down - f.up * a.down;
		a.down = a.down * f.down;

		return a.gcd(a);
	}

	public Fraction multiply(Fraction f) {
		Fraction a = new Fraction(up, down);
		a.up = a.up * f.up;
		a.down = a.down * f.down;
		return a.gcd(a);
	}

	public Fraction divide(Fraction f) {
		Fraction a = new Fraction(up, down);
		a.up = a.up * f.down;
		a.down = a.down * f.up;
		return a.gcd(a);
	}

	public Fraction changeSign(){
		up = -up;
		return this;
	}

	public static Fraction getRandiom(int Max) {
		return new Fraction((int) (Math.random() * Max / 2) + 1, (int) (Math.random() * Max / 2) + 1);
	}

	public static Fraction getRandiom(int Max, boolean isInt) {
		return new Fraction((int) (Math.random() * Max / 2) + 1, isInt ? 1 : (int) (Math.random() * Max / 2) + 1);
	}
	

//	public Fraction(Fraction f) {
//		if (f.down == 0 || up == 0) {
//			System.out.println("divided by zero error");
//			return;
//		}
//
//		f = f.gcd(f);
//		this.up = f.up;
//		this.down = f.down;
//	}
	
	/*	public Fraction add(int f) {
		Fraction a = new Fraction(up, down);
		a.up = a.up + f * a.down;
		return a.gcd(a);
	}

	public Fraction minus(int f) {
		Fraction a = new Fraction(up, down);
		a.up = a.up - f * a.down;
		return a.gcd(a);
	}

	public Fraction multiply(int f) {
		Fraction a = new Fraction(up, down);
		a.up = a.up * f;
		return a.gcd(a);
	}

	public Fraction divide(int f) {
		Fraction a = new Fraction(up, down);
		a.down = a.down * f;
		return a.gcd(a);
	}*/
}