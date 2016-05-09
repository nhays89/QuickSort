//Author: Nicholas Hays
//Assignment: Algorithms 
//Design and Analysis of Quicksort Algorithms
import java.util.Random;

public class QuicksortTest {

	static Random r = new Random();
	private static Integer[] a1st;
	private static Integer[] amid;
	private static long startTime;
	private static long endTime;
	private static int[] arrayOfInputs = { 1000, 10000, 100000, 1000000, 10000000 };
	private static StringBuilder tableBuilder;
	private static String sizeBorder;
	private static String leftAlignFormat;

	public static void main(String[] args) {
		displayColumns();
		for (int inputSize : arrayOfInputs) {
			a1st = new Integer[inputSize];
			amid = new Integer[inputSize];

			fillArray(a1st);
			fillArray(amid);

			// Tests normal quicksorts
			try {
				addTableRow(inputSize, "1st", "unsorted", "unsafe", testQuicksort1stUnsorted());
			} catch (StackOverflowError e) {
				addTableRow(inputSize, "1st", "unsorted", "unsafe", -1);
			}
			try {
				addTableRow(inputSize, "1st", "sorted", "unsafe", testQuicksort1stSorted());
			} catch (StackOverflowError e) {
				addTableRow(inputSize, "1st", "sorted", "unsafe", -1);
			}
			try {
				addTableRow(inputSize, "mid", "unsorted", "unsafe", testQuicksortMidUnsorted());
			} catch (StackOverflowError e) {
				addTableRow(inputSize, "mid", "unsorted", "unsafe", -1);
			}
			try {
				addTableRow(inputSize, "mid", "sorted", "unsafe", testQuicksortMidSorted());
			} catch (StackOverflowError e) {
				addTableRow(inputSize, "mid", "sorted", "unsafe", -1);
			}

			// randomize arrays again
			fillArray(a1st);
			fillArray(amid);
			try {
				addTableRow(inputSize, "1st", "unsorted", "safe", testSafeQuicksort1stUnsorted());
			} catch (StackOverflowError e) {
				addTableRow(inputSize, "1st", "unsorted", "safe", -1);
			}
			try {
				addTableRow(inputSize, "1st", "sorted", "safe", testSafeQuicksort1stSorted());
			} catch (StackOverflowError e) {
				addTableRow(inputSize, "mid", "sorted", "safe", -1);
			}
			try {
				addTableRow(inputSize, "mid", "unsorted", "safe", testSafeQuicksortMidUnsorted());
			} catch (StackOverflowError e) {
				addTableRow(inputSize, "mid", "unsorted", "safe", -1);
			}
			try {
				addTableRow(inputSize, "mid", "sorted", "safe", testSafeQuicksortMidSorted());
			} catch (StackOverflowError e) {
				addTableRow(inputSize, "mid", "sorted", "safe", -1);
			}
		}

		displayRowLine();

	}

	@SuppressWarnings("rawtypes")
	public static void Quicksort1st(Comparable[] a) {
		Quicksort1st(a, 0, a.length - 1);
	}

	@SuppressWarnings("rawtypes")
	public static void Quicksort1st(Comparable[] a, int l, int r) {
		if (r > l) {
			int p = Pivot1st(a, l, r);
			Quicksort1st(a, l, p - 1);
			Quicksort1st(a, p + 1, r);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static int Pivot1st(Comparable[] a, int l, int r) {
		Comparable p = a[l];
		int i = l + 1;
		int j = r;
		while (i <= j) {
			while (p.compareTo(a[i]) > 0) {
				i++;
				if (i > r)
					break;
			}
			while (p.compareTo(a[j]) < 0) {
				--j;
			}
			if (i <= j) {
				swap(a, i, j);
				i++;
				j--;
			}
		}
		swap(a, l, j);
		return j;
	}

	@SuppressWarnings("rawtypes")
	private static void SafeQuicksort1st(Comparable[] a) {
		SafeQuicksort1st(a, 0, a.length - 1);
	}

	@SuppressWarnings("rawtypes")
	private static void SafeQuicksort1st(Comparable[] a, int l, int r) {
		while (r > l) {
			int p = Pivot1st(a, l, r);
			if (p - l <= r - p) {
				SafeQuicksort1st(a, l, p - 1); // shorter part
				l = p + 1; // emulate SafeQuicksort1st(a, p + 1, r);
			} else {
				SafeQuicksort1st(a, p + 1, r); // shorter part
				r = p - 1; // emulate SafeQuicksort1st(a, l, p - 1);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public static void QuicksortMid(Comparable[] a) {
		QuicksortMid(a, 0, a.length - 1);
	}

	@SuppressWarnings("rawtypes")
	public static void QuicksortMid(Comparable[] a, int l, int r) {
		if (r > l) {
			int p = PivotMid(a, l, r);
			QuicksortMid(a, l, p - 1);
			QuicksortMid(a, p + 1, r);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static int PivotMid(Comparable[] a, int l, int r) {
		Comparable p = a[l + (r - l) / 2];
		int m = l + (r - l) / 2;
		int i = l + 1;
		int j = r;
		swap(a, m, l);
		while (i <= j) {
			while (p.compareTo(a[i]) > 0) {
				i++;
				if (i > r)
					break;
			}
			while (p.compareTo(a[j]) < 0) {
				--j;
			}
			if (i <= j) {
				swap(a, i, j);
				i++;
				j--;
			}
		}
		if (i == j) {
			swap(a, l, i - 1);
			return i - 1;
		} else if (j < i) {
			swap(a, l, j);
			return j;
		}
		return j;
	}

	@SuppressWarnings("rawtypes")
	private static void SafeQuicksortMid(Comparable[] a) {
		SafeQuicksortMid(a, 0, a.length - 1);
	}

	@SuppressWarnings("rawtypes")
	private static void SafeQuicksortMid(Comparable[] a, int l, int r) {
		while (r > l) {
			int p = PivotMid(a, l, r);
			if (p - l <= r - p) {
				SafeQuicksortMid(a, l, p - 1); // shorter part
				l = p + 1; // emulate SafeQuicksort1st(a, p + 1, r);
			} else {
				SafeQuicksortMid(a, p + 1, r); // shorter part
				r = p - 1; // emulate SafeQuicksort1st(a, l, p - 1);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	private static void swap(Comparable[] a, int i, int j) {
		Comparable t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

	private static void displayColumns() {
		tableBuilder = new StringBuilder();
		leftAlignFormat = "| %-22d | %-14s | %-12s | %-10s | %-6.3f |%n";

		tableBuilder.append(
				String.format("+------------------------+----------------+--------------+------------+--------+%n"));
		tableBuilder.append(
				String.format("| Input Size             | Pivot location | (un)sorted   | (un)safe   | time   |%n"));

		System.out.print(tableBuilder.toString());
	}

	private static void addTableRow(int inputSize, String pivPos, String sortPro, String safeType, double time) {
		leftAlignFormat = "| %-22d | %-14s | %-12s | %-10s | %-6.3f |%n";
		if (time == -1) {
			leftAlignFormat = "| %-22d | %-14s | %-12s | %-10s | %-6s |%n";
			String stackOverFlow = "error";
			String ANSI_RED = "\u001B[31m";
			String s = String.format(leftAlignFormat, inputSize, pivPos, sortPro, safeType, stackOverFlow);
			displayRowLine();
			System.out.print(s.toString());
			return;

		}
		String s = String.format(leftAlignFormat, inputSize, pivPos, sortPro, safeType, time);
		displayRowLine();
		System.out.print(s.toString());
	}

	private static void displayRowLine() {
		String s = String.format("+------------------------+----------------+--------------+------------+--------+%n",
				sizeBorder);
		System.out.print(s.toString());
	}

	@SuppressWarnings("unchecked")
	private static boolean check(Comparable[] a) {
		boolean sorted = true;
		for (int i = 0; i < a.length - 1; i++) {
			if (a[i].compareTo(a[i + 1]) > 0)
				sorted = false;
		}
		return sorted;
	}

	private static void fillArray(Integer[] a) {
		Random r = new Random();
		for (int i = 0; i < a.length; i++) {
			a[i] = r.nextInt(a.length);
		}
	}

	// non safe methods

	private static double testQuicksort1stUnsorted() {

		startTime = System.currentTimeMillis();
		Quicksort1st(a1st);
		endTime = System.currentTimeMillis();
		return (endTime - startTime) / 1000.0;
	}

	private static double testQuicksortMidUnsorted() {

		startTime = System.currentTimeMillis();
		QuicksortMid(amid);
		endTime = System.currentTimeMillis();
		return (endTime - startTime) / 1000.0;
	}

	private static double testQuicksort1stSorted() {

		startTime = System.currentTimeMillis();
		Quicksort1st(a1st);
		endTime = System.currentTimeMillis();
		return (endTime - startTime) / 1000.0;
	}

	private static double testQuicksortMidSorted() {

		startTime = System.currentTimeMillis();
		QuicksortMid(amid);
		endTime = System.currentTimeMillis();
		return (endTime - startTime) / 1000.0;
	}

	// safe methods

	private static double testSafeQuicksort1stUnsorted() {

		startTime = System.currentTimeMillis();
		SafeQuicksort1st(a1st);
		endTime = System.currentTimeMillis();
		return (endTime - startTime) / 1000.0;
	}

	private static double testSafeQuicksortMidUnsorted() {

		startTime = System.currentTimeMillis();
		SafeQuicksortMid(amid);
		endTime = System.currentTimeMillis();
		return (endTime - startTime) / 1000.0;
	}

	private static double testSafeQuicksort1stSorted() {

		startTime = System.currentTimeMillis();
		SafeQuicksort1st(a1st);
		endTime = System.currentTimeMillis();
		return (endTime - startTime) / 1000.0;
	}

	private static double testSafeQuicksortMidSorted() {

		startTime = System.currentTimeMillis();
		SafeQuicksortMid(amid);
		endTime = System.currentTimeMillis();
		return (endTime - startTime) / 1000.0;
	}

}
