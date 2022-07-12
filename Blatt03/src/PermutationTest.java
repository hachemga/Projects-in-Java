import java.math.BigInteger;
import java.util.*;


import static java.lang.Math.exp;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PermutationTest {
	PermutationVariation p1;
	PermutationVariation p2;
	public int n1;
	public int n2;
	int cases=0;

	void initialize() {
		n1=4;
		n2=6;
		Cases c= new Cases();
		p1= c.switchforTesting(cases, n1);
		p2= c.switchforTesting(cases, n2);
	}
	

	@Test
	void testPermutation() {
		initialize();
		// TODO
		assertNotNull (p1.original, "Original in p1 is empty");
		assertNotNull (p2.original, "Original in p2 is empty");
		assertEquals(p1.original.length, n1, "Die Variable original in p1 ist nicht mit einer Folge " +
				"der vorgegeben Länge n1 initialisiert");
		assertEquals(p2.original.length, n2, "Die Variable original in p2 ist nicht mit einer Folge " +
				"der vorgegeben Länge n2 initialisiert");
		Set<Integer> test1 = new HashSet<>();
		for (Integer i : p1.original) {
			assertTrue (test1.add(i), "There is a double in original in p1");
		}
		test1.clear();

		Set<Integer> test2 = new HashSet<>();
		for (Integer i : p2.original) {
			assertTrue (test2.add(i), "There is a double in original in p2");
		}
		test2.clear();
		assertNotNull(p1.allDerangements, "Derangements isn't initialised");
		assertTrue(p1.allDerangements.isEmpty(), "allDerangements in p1 isn't empty");
		assertNotNull(p2.allDerangements, "Derangements isn't initialised");
		assertTrue(p2.allDerangements.isEmpty(), "allDerangements in p2 isn't empty");
	}

	@Test
	void testDerangements() {
		initialize();
		//in case there is something wrong with the constructor
		fixConstructor();
		// TODO
		double e = 2.71828;
		p1.derangements();
		int sum1 = 1;
		for (int i=1; i <= n1; i++){
			sum1 = sum1 * i;
		}
		sum1 = (int) ((sum1 + 1)/e);
		assertEquals(p1.allDerangements.size(), sum1, "Some Derangements are missing");		for (int [] i : p1.allDerangements){
			for (int j= 0 ; j < i.length; j++){
				assertNotEquals(p1.original[j], i[j], "Error: Derangement with Index ["+j+"] didn't do a " +
						"permutation compared to Original p2");
			}
		}
		p2.derangements();
		int sum2 = 1;
		for (int i=1; i <= n2; i++){
			sum2 = sum2 * i;
		}
		sum2 = (int) ((sum2 + 1)/e);
		assertEquals(p2.allDerangements.size(), sum2, "Some Derangements are missing");
		for (int [] k : p2.allDerangements){
			for (int h= 0 ; h < k.length; h++){
				assertNotEquals(p2.original[h], k[h], "Error: Derangement with Index ["+h+"] didn't do a " +
						"permutation compared to Original p2");
			}
		}

	}
	
	@Test
	void testsameElements() {
		initialize();
		//in case there is something wrong with the constructor
		fixConstructor();
		// TODO
		p1.derangements();
		p2.derangements();
		int sizedera1 = 0;
		int sizedera2 = 0;

		for (int[] i : p1.allDerangements) {
			int size1 = 0;
			for (int value1 : i) {
				for (int k = 0; k < p1.original.length; k++) {
					if (p1.original[k] == value1) {
						size1 += 1;
					}
				}
			}
			assertEquals(size1, p1.original.length, "This is not a permutation in P1");
			if (size1 == p1.original.length) {
				sizedera1 += 1;
			}
		}
		assertNotEquals(0, sizedera1, "Fehler in P1");

		for (int[] j : p2.allDerangements) {
			int size2 = 0;
			for (int value2 : j) {
				for (int m = 0; m < p2.original.length; m++) {
					if (p2.original[m] == value2) {
						size2 += 1;
					}
				}
			}
			assertEquals(size2, p2.original.length, "This is not a permutation in P2");
			if (size2 == p2.original.length) {
				sizedera2 += 1;
			}
		}
		assertNotEquals(0, sizedera2, "Fehler in P2");
	}
	
	void setCases(int c) {
		this.cases=c;
	}
	
	public void fixConstructor() {
		//in case there is something wrong with the constructor
		p1.allDerangements=new LinkedList<int[]>();
		for(int i=0;i<n1;i++)
			p1.original[i]=2*i+1;
		
		p2.allDerangements=new LinkedList<int[]>();
		for(int i=0;i<n2;i++)
			p2.original[i]=i+1;
	}
}


