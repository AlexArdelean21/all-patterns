package examples;

/**
 * Clasa Calculator - exemplu pentru demonstrarea conceptelor de Unit Testing
 * Această clasă implementează operații matematice de bază
 */
public class Calculator {
    
    /**
     * Adună două numere întregi
     * @param a primul număr
     * @param b al doilea număr
     * @return suma celor două numere
     */
    public int add(int a, int b) {
        return a + b;
    }
    
    /**
     * Scade două numere întregi
     * @param a primul număr (minuendul)
     * @param b al doilea număr (scăzătorul)
     * @return diferența celor două numere
     */
    public int subtract(int a, int b) {
        return a - b;
    }
    
    /**
     * Înmulțește două numere întregi
     * @param a primul număr
     * @param b al doilea număr
     * @return produsul celor două numere
     */
    public int multiply(int a, int b) {
        return a * b;
    }
    
    /**
     * Împarte două numere întregi
     * @param a împărțitul
     * @param b împărțitorul
     * @return câtul împărțirii
     * @throws IllegalArgumentException dacă împărțitorul este 0
     */
    public int divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Nu se poate împărți la zero!");
        }
        return a / b;
    }
    
    /**
     * Calculează puterea unui număr
     * @param base baza
     * @param exponent exponentul (trebuie să fie pozitiv)
     * @return rezultatul ridicării la putere
     * @throws IllegalArgumentException dacă exponentul este negativ
     */
    public int power(int base, int exponent) {
        if (exponent < 0) {
            throw new IllegalArgumentException("Exponentul nu poate fi negativ!");
        }
        
        int result = 1;
        for (int i = 0; i < exponent; i++) {
            result *= base;
        }
        return result;
    }
    
    /**
     * Calculează factorialul unui număr
     * @param n numărul pentru care se calculează factorialul
     * @return factorialul lui n
     * @throws IllegalArgumentException dacă n este negativ
     */
    public long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorialul nu este definit pentru numere negative!");
        }
        
        if (n == 0 || n == 1) {
            return 1;
        }
        
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
    
    /**
     * Verifică dacă un număr este prim
     * @param n numărul de verificat
     * @return true dacă numărul este prim, false altfel
     * @throws IllegalArgumentException dacă n < 2
     */
    public boolean isPrime(int n) {
        if (n < 2) {
            throw new IllegalArgumentException("Numerele mai mici decât 2 nu pot fi prime!");
        }
        
        if (n == 2) {
            return true;
        }
        
        if (n % 2 == 0) {
            return false;
        }
        
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Calculează cel mai mare divizor comun (CMMDC)
     * @param a primul număr
     * @param b al doilea număr
     * @return CMMDC-ul celor două numere
     * @throws IllegalArgumentException dacă unul dintre numere este negativ
     */
    public int gcd(int a, int b) {
        if (a < 0 || b < 0) {
            throw new IllegalArgumentException("Numerele trebuie să fie pozitive!");
        }
        
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        
        return a;
    }
} 