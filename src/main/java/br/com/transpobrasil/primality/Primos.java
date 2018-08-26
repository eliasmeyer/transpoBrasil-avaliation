package br.com.transpobrasil.primality;

public final class Primos {

    private Primos() {}

    private static final int ACCURACY = 100;
    private static final long MIN = 41;
    private static final long MAX = 5002;

    public static void main(String[] args) {

        MillerRabinPrimality primalityTest = new MillerRabinPrimality(ACCURACY);
        System.out.println(String.format("######PRIME NUMBERS######"));
        System.out.println(String.format("Prime numbers between %s and %s.", MIN, MAX));
        for (long number = MIN, j = 1; number <= MAX; number++, j++) {
            if (primalityTest.isPrime(number)) {
                System.out.println(String.format("#%s: Prime Number: %s", j, number));
            }
        }
    }
}
