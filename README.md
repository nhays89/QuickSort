# QuickSort
Quicksort is a comparison sort, meaning that it can sort items of any type for which a "less-than" relation (formally, a total order) is defined. Therefore, any object that implements the <a href="https://docs.oracle.com/javase/7/docs/api/java/lang/Comparable.htmlComparable"> Comparable</a> interface can be sorted using this algorithm. 

### Runtime
The Quicksort algorithm has a Θ(n^2) worst case complexity even though it is only Θ(nlogn) on average. Our goal here is to observe the impact of this behavior difference in practice. 

### How does it work?
The code below is a slightly modified version of <a href="https://en.wikipedia.org/wiki/Tony_Hoare">Tony Hoare's</a> algorithm. 
```java
  public static void Quicksort1st(Comparable[] a) {
		Quicksort1st(a, 0, a.length - 1);
  }
  ```
  As long as the objects in the array are <a href="https://docs.oracle.com/javase/7/docs/api/java/lang/Comparable.htmlComparable"> Comparable</a>, it works. 
```java 
  public static void Quicksort1st(Comparable[] a, int l, int r) {
	  if (r > l) {
		  int p = Pivot1st(a, l, r);
		  Quicksort1st(a, l, p - 1);
		  Quicksort1st(a, p + 1, r);
	  }
  }
``` 
The method above recursively sorts each partition of the array by selecting a pivot using the pivot procedure below.  

### Pivot Procedure

The pivot procedure above uses an in-place sorting algorithm that selects a pivot at the low index of the array. The value at this index will serve as the value for all other elements of this partitioned array to compare against. Once the pivot has been selected, two indicies, i and j, will be created and set pointing to the beginning and ending index of the array, respectively. They will continue to move towards each other while comparing the values located at their index against the pivot. If ```i``` iterates over a value that is greater than the value of the pivot it will halt; likewise, if ```j```  iterates over a value less than the pivot it will halt. Once ```i``` and ```j``` have both halted, their values will be swapped. This process will repeat itslef until ```i``` and ```j``` are essentially equal. 

```java  
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
```

### Safety First

The above procedures work great for most inputs of the quicksort algorithm, but we can do better. The code below outlines a unique approach to generating faster results across all input ranges. It does so by always choosing to sort the smaller of the two partitioned arrays first. 

``` java
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
``` 

### Sample Data

The objective of this repo is to analyze the runtime differences when the input parameters change. Below is a table that outputs the runtimes of various different input sizes using integer objects. If you notice, you can discover large runtime differences when the input array is sorted vs unsorted. Likewise, when the sorting procedure uses a "safe" quicksort prodcedure as outlined below. 
![](https://github.com/nhays89/QuickSort/blob/master/QuickSort/img/hmk3_quicksort_table.png)

### For Your Information

For more information on how this algorithm works check out <a href="http://me.dt.in.th/page/Quicksort/">this</a> site for the deets. If you are really curious, give <a href="https://stackoverflow.com/questions/12454866/how-to-optimize-quicksort">this</a> a whirl. 
