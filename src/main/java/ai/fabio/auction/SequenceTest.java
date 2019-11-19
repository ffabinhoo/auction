package ai.fabio.auction;

import java.math.BigInteger;

public class SequenceTest {
	
	public static void main(String[] args) {
		
		Long  n = 10000000L;
		Long  C = 200L;	
		BigInteger  sum = BigInteger.valueOf(0);
		
		for (long i=0; i<n; i++) {
		  if (i <= C) {
			  sum = sum.add(BigInteger.valueOf(i));
		  }else {
			  long t = (i * (i-1));
			  sum = sum.add(BigInteger.valueOf(t)); 
		  }
		}
		System.out.println(sum);
	}
}
