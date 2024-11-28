public class LoanCalc {

	static double epsilon = 0.001;  // Approximation accuracy
	static int iterationCounter;    // Number of iterations

	// Gets the loan data and computes the periodical payment.
	// Expects to get three command-line arguments: loan amount (double),
	// interest rate (double, as a percentage), and number of payments (int).
	public static void main(String[] args) {
		// Gets the loan data
		double loan = Double.parseDouble(args[0]);
		double rate = Double.parseDouble(args[1]);
		int n = Integer.parseInt(args[2]);
		System.out.println("Loan = " + loan + ", interest rate = " + rate + "%, periods = " + n);

		// Computes the periodical payment using brute force search
		System.out.print("\nPeriodical payment, using brute force: ");
		System.out.println(bruteForceSolver(loan, rate, n, 1.0)); // Using epsilon = 1.0 for practicality
		System.out.println("number of iterations: " + iterationCounter);

		// Computes the periodical payment using bisection search
		System.out.print("\nPeriodical payment, using bi-section search: ");
		System.out.println(bisectionSolver(loan, rate, n, epsilon));
		System.out.println("number of iterations: " + iterationCounter);
	}

	// Computes the ending balance of a loan, given the loan amount, the periodical
	// interest rate (as a percentage), the number of periods (n), and the periodical payment.
	private static double endBalance(double loan, double rate, int n, double payment) {
		double balance = loan;
		for (int i = 0; i < n; i++) {
			balance = (balance - payment) * (1 + rate / 100.0);
		}
		return balance;
	}

	// Uses sequential search to compute an approximation of the periodical payment
	// that will bring the ending balance of a loan close to 0.
	// Given: the sum of the loan, the periodical interest rate (as a percentage),
	// the number of periods (n), and epsilon, the approximation's accuracy
	// Side effect: modifies the class variable iterationCounter.
	public static double bruteForceSolver(double loan, double rate, int n, double epsilon) {
		iterationCounter = 0; // Reset counter
		double payment = loan / n;
		while (true) {
			double balance = endBalance(loan, rate, n, payment);
			if (balance <= 0) {
				break;
			}
			payment += epsilon;
			iterationCounter++;
		}
		return payment;
	}

	// Uses bisection search to compute an approximation of the periodical payment
	// that will bring the ending balance of a loan close to 0.
	// Given: the sum of the loan, the periodical interest rate (as a percentage),
	// the number of periods (n), and epsilon, the approximation's accuracy
	// Side effect: modifies the class variable iterationCounter.
	public static double bisectionSolver(double loan, double rate, int n, double epsilon) {
		iterationCounter = 0; // Reset counter
		// Initialize lo and hi
		double lo = loan / n;
		double hi = loan * (1 + rate / 100.0);

		double f_lo = endBalance(loan, rate, n, lo);
		if (f_lo <= 0) {
			lo = 0;
			f_lo = endBalance(loan, rate, n, lo);
		}

		double f_hi = endBalance(loan, rate, n, hi);
		while (f_hi > 0) {
			hi *= 2;
			f_hi = endBalance(loan, rate, n, hi);
		}

		double mid = 0;
		while (hi - lo > epsilon) {
			mid = (lo + hi) / 2;
			double f_mid = endBalance(loan, rate, n, mid);
			if (f_mid == 0.0) {
				return mid;
			} else if (f_mid * f_lo > 0) {
				lo = mid;
				f_lo = f_mid;
			} else {
				hi = mid;
				f_hi = f_mid;
			}
			iterationCounter++;
		}
		return mid;
	}
}