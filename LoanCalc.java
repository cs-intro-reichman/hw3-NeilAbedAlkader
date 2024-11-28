// Computes the periodical payment necessary to pay a given loan.
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

		// Computes the ending balance of the loan, given a periodical payment
		double payment = 10000;
		double endBalance = endBalance(loan, rate, n, payment);
		System.out.println("If your periodical payment is " + payment + ", your ending balance is: " + (int) endBalance);

		// Computes the periodical payment using brute force search
		System.out.print("\nPeriodical payment, using brute force: ");
		System.out.println((int) bruteForceSolver(loan, rate, n, epsilon));
		System.out.println("number of iterations: " + iterationCounter);

		// Computes the periodical payment using bisection search
		System.out.print("\nPeriodical payment, using bi-section search: ");
		System.out.println((int) bisectionSolver(loan, rate, n, epsilon));
		System.out.println("number of iterations: " + iterationCounter);
	}

	// Computes the ending balance of a loan, given the loan amount, the periodical
	// interest rate (as a percentage), the number of periods (n), and the periodical payment.
	private static double endBalance(double loan, double rate, int n, double payment) {
		for (int i = 0; i < n; i++) {
			loan = (loan - payment) * (1 + rate / 100);
		}
		return loan;
	}

	// Uses sequential search to compute an approximation of the periodical payment
	// that will bring the ending balance of a loan close to 0.
	// Given: the sum of the loan, the periodical interest rate (as a percentage),
	// the number of periods (n), and epsilon, the approximation's accuracy
	// Side effect: modifies the class variable iterationCounter.
	public static double bruteForceSolver(double loan, double rate, int n, double epsilon) {
		iterationCounter = 0; // Initialize the iteration counter

		// Convert rate from percentage to decimal
		double monthlyRate = rate / 100;

		// Start with a low guess for payment
		double payment = 0.01;

		while (true) {
			iterationCounter++;

			// Calculate the remaining balance after n periods
			double balance = loan;
			for (int i = 0; i < n; i++) {
				balance += balance * monthlyRate; // Apply interest
				balance -= payment;              // Subtract payment
				if (balance <= 0) break;         // Break early if balance goes negative
			}

			// If the balance is close enough to 0, return the payment
			if (Math.abs(balance) <= epsilon) {
				return payment; // Return the calculated payment
			}

			// Increment the payment slightly for the next guess
			payment += epsilon;
		}
	}

	// Uses bisection search to compute an approximation of the periodical payment
	// that will bring the ending balance of a loan close to 0.
	// Given: the sum of the loan, the periodical interest rate (as a percentage),
	// the number of periods (n), and epsilon, the approximation's accuracy
	// Side effect: modifies the class variable iterationCounter.
	public static double bisectionSolver(double loan, double rate, int n, double epsilon) {
		double UpperBound = loan / n + 1000;
		double LowerBound = 0;
		double MiddleBound = 0;
		iterationCounter = 0;

		while ((UpperBound - LowerBound) > epsilon) {
			MiddleBound = (UpperBound + LowerBound) / 2;
			double endBalance = endBalance(loan, rate, n, MiddleBound);
			iterationCounter++; // Increment counter

			if (endBalance > 0) {
				LowerBound = MiddleBound;
			} else {
				UpperBound = MiddleBound;
			}
		}

		return (UpperBound + LowerBound) / 2;
	}
}