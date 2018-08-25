package br.com.transpobrasil.primality;

import java.math.BigInteger;
import java.util.Random;

/**
 * Implementation of the Miller-Rabim algorithm - primality test.
 *
 * @see {https://en.wikipedia.org/wiki/Miller%E2%80%93Rabin_primality_test}
 */
public class MillerRabinPrimality {
    private static final Random rand = new Random();
    public int accuracy;

    // The algorithm works to find a representation (n-1) = 2^s * d.
    private int s;
    private BigInteger d;

    /**
     * Sets up accuracy.
     */
    public MillerRabinPrimality(int tests) {
        this.accuracy = tests;
        this.s = 0;
        this.d = BigInteger.ZERO;
    }

    /**
     * Check if number is prime
     *
     * @param number
     * @return boolean
     */
    public boolean isPrime(long number) {
        BigInteger n = BigInteger.valueOf(number);
        findSDRepresentation(n);

        for (int i = 0; i < accuracy; i++) {
            // We want the test value to be a random positive integer
            // between 1 and n-1 (inclusive).
            BigInteger testValue = new BigInteger(n.bitLength(), rand);
            testValue = testValue.mod(n.subtract(BigInteger.ONE));
            testValue = testValue.add(BigInteger.ONE);

            if (!passesMRTest(n, testValue)) {
                // We failed a test, number cannot be prime.
                return false;
            }
        }

        // If we got here, we assume primality.
        return true;
    }

    /**
     * The Miller-Rabin test: Checks for a^(n-1) congruent to 1 modulo n.
     */
    private boolean passesMRTest(BigInteger n, BigInteger a) {
        BigInteger x = a.modPow(d, n);

        // This is equivalent to our square-and-signal trick in SICP.
        if ((x.compareTo(BigInteger.ONE) != 0) &&
                (x.compareTo(n.subtract(BigInteger.ONE)) != 0)) {

            for (int i = 0; i < s - 1; i++) {
                x = x.modPow(BigInteger.valueOf(2), n);

                if (x.compareTo(BigInteger.ONE) == 0) {
                    // We have a nontrivial square root of 1, and so we
                    // definitely don't have a prime.
                    return false;
                }

                if (x.compareTo(n.subtract(BigInteger.ONE)) == 0) {
                    // If we got here, our number passed the test.
                    return true;
                }
            }

            // If we went through every iteration of the loop, 
            // our number cannot be prime.
            return false;
        }

        // If we got here, our number passed the test, and -might- be prime.
        return true;
    }

    /**
     * Returns us a representation for n-1 as 2^s * d.
     */
    private void findSDRepresentation(BigInteger n) {
        // We factor out as many powers of 2 from n-1 as we can so we
        // can write n-1 = 2^s * d.
        d = n.subtract(BigInteger.ONE);
        s = 0;
        BigInteger[] divAndRem = d.divideAndRemainder(BigInteger.valueOf(2));

        while (divAndRem[1].compareTo(BigInteger.ZERO) == 0) {
            d = divAndRem[0];
            s++;
            divAndRem = d.divideAndRemainder(BigInteger.valueOf(2));
        }
    }
}
