package examples;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Exemplu de testare cu JUnit 3
 * Demonstrează toate conceptele prezentate în curs
 */
public class CalculatorTestJUnit3 extends TestCase {
    
    // Fixture - obiectele necesare testării
    private Calculator calculator;
    
    /**
     * Constructor obligatoriu pentru JUnit 3
     * @param name numele testului
     */
    public CalculatorTestJUnit3(String name) {
        super(name);
    }
    
    /**
     * setUp - se execută înaintea fiecărui test
     * Inițializează resursele necesare
     */
    public void setUp() {
        System.out.println("setUp: Inițializez Calculator");
        calculator = new Calculator();
    }
    
    /**
     * tearDown - se execută după fiecare test
     * Eliberează resursele utilizate
     */
    public void tearDown() {
        System.out.println("tearDown: Curăț resursele");
        calculator = null;
    }
    
    // ========================== TESTE DE BAZĂ ==========================
    
    /**
     * Test pentru operația de adunare - scenarii normale
     */
    public void testAdd() {
        // ARRANGE & ACT & ASSERT
        assertEquals("2 + 3 trebuie să fie 5", 5, calculator.add(2, 3));
        assertEquals("10 + 15 trebuie să fie 25", 25, calculator.add(10, 15));
        assertEquals("0 + 0 trebuie să fie 0", 0, calculator.add(0, 0));
    }
    
    /**
     * Test pentru operația de scădere - scenarii normale
     */
    public void testSubtract() {
        assertEquals("5 - 3 trebuie să fie 2", 2, calculator.subtract(5, 3));
        assertEquals("10 - 10 trebuie să fie 0", 0, calculator.subtract(10, 10));
        assertEquals("3 - 5 trebuie să fie -2", -2, calculator.subtract(3, 5));
    }
    
    /**
     * Test pentru operația de înmulțire
     */
    public void testMultiply() {
        assertEquals("3 * 4 trebuie să fie 12", 12, calculator.multiply(3, 4));
        assertEquals("5 * 0 trebuie să fie 0", 0, calculator.multiply(5, 0));
        assertEquals("-3 * 4 trebuie să fie -12", -12, calculator.multiply(-3, 4));
    }
    
    /**
     * Test pentru împărțire - cazuri normale
     */
    public void testDivide() {
        assertEquals("10 / 2 trebuie să fie 5", 5, calculator.divide(10, 2));
        assertEquals("15 / 3 trebuie să fie 5", 5, calculator.divide(15, 3));
        assertEquals("7 / 2 trebuie să fie 3", 3, calculator.divide(7, 2)); // împărțire întreagă
    }
    
    // =============== TESTE PENTRU BOUNDARY CONDITIONS ===============
    
    /**
     * Test pentru limitele adunării (CORRECT - Range)
     */
    public void testAddBoundaryConditions() {
        // Testează cu valori la limite
        assertEquals("Integer.MAX_VALUE + 0", Integer.MAX_VALUE, 
                    calculator.add(Integer.MAX_VALUE, 0));
        assertEquals("Integer.MIN_VALUE + 0", Integer.MIN_VALUE, 
                    calculator.add(Integer.MIN_VALUE, 0));
        
        // Valori negative
        assertEquals("-5 + -3 trebuie să fie -8", -8, calculator.add(-5, -3));
        assertEquals("-10 + 10 trebuie să fie 0", 0, calculator.add(-10, 10));
    }
    
    /**
     * Test pentru zero în operații (CORRECT - Existence)
     */
    public void testZeroValues() {
        assertEquals("0 + 5 = 5", 5, calculator.add(0, 5));
        assertEquals("5 + 0 = 5", 5, calculator.add(5, 0));
        assertEquals("0 * 100 = 0", 0, calculator.multiply(0, 100));
        assertEquals("100 * 0 = 0", 0, calculator.multiply(100, 0));
    }
    
    // ==================== TESTE PENTRU EXCEPȚII ====================
    
    /**
     * Test că împărțirea la zero aruncă excepție
     * Demonstrează testarea condițiilor de eroare
     */
    public void testDivideByZero() {
        try {
            calculator.divide(5, 0);
            fail("Trebuia să arunce IllegalArgumentException pentru împărțirea la zero");
        } catch (IllegalArgumentException e) {
            assertEquals("Mesajul de eroare trebuie să fie corect", 
                        "Nu se poate împărți la zero!", e.getMessage());
        }
    }
    
    /**
     * Test pentru puterea cu exponent negativ
     */
    public void testPowerWithNegativeExponent() {
        try {
            calculator.power(2, -3);
            fail("Trebuia să arunce IllegalArgumentException pentru exponent negativ");
        } catch (IllegalArgumentException e) {
            assertEquals("Exponentul nu poate fi negativ!", e.getMessage());
        }
    }
    
    /**
     * Test pentru factorial cu număr negativ
     */
    public void testFactorialNegativeNumber() {
        try {
            calculator.factorial(-5);
            fail("Trebuia să arunce IllegalArgumentException pentru factorial negativ");
        } catch (IllegalArgumentException e) {
            assertEquals("Factorialul nu este definit pentru numere negative!", e.getMessage());
        }
    }
    
    // ==================== TESTE PENTRU ALGORITMI ====================
    
    /**
     * Test pentru calculul puterii
     */
    public void testPower() {
        assertEquals("2^0 = 1", 1, calculator.power(2, 0));
        assertEquals("2^1 = 2", 2, calculator.power(2, 1));
        assertEquals("2^3 = 8", 8, calculator.power(2, 3));
        assertEquals("5^2 = 25", 25, calculator.power(5, 2));
        assertEquals("1^100 = 1", 1, calculator.power(1, 100));
    }
    
    /**
     * Test pentru calculul factorialului
     */
    public void testFactorial() {
        assertEquals("0! = 1", 1, calculator.factorial(0));
        assertEquals("1! = 1", 1, calculator.factorial(1));
        assertEquals("3! = 6", 6, calculator.factorial(3));
        assertEquals("5! = 120", 120, calculator.factorial(5));
    }
    
    /**
     * Test pentru verificarea numerelor prime
     */
    public void testIsPrime() {
        // Numere prime
        assertTrue("2 este prim", calculator.isPrime(2));
        assertTrue("3 este prim", calculator.isPrime(3));
        assertTrue("17 este prim", calculator.isPrime(17));
        assertTrue("97 este prim", calculator.isPrime(97));
        
        // Numere compuse
        assertFalse("4 nu este prim", calculator.isPrime(4));
        assertFalse("9 nu este prim", calculator.isPrime(9));
        assertFalse("15 nu este prim", calculator.isPrime(15));
        assertFalse("100 nu este prim", calculator.isPrime(100));
    }
    
    /**
     * Test pentru CMMDC
     */
    public void testGCD() {
        assertEquals("gcd(12, 8) = 4", 4, calculator.gcd(12, 8));
        assertEquals("gcd(17, 13) = 1", 1, calculator.gcd(17, 13));
        assertEquals("gcd(100, 25) = 25", 25, calculator.gcd(100, 25));
        assertEquals("gcd(0, 5) = 5", 5, calculator.gcd(0, 5));
    }
    
    // ==================== TESTE PENTRU INVERSE RELATIONSHIPS ====================
    
    /**
     * Test pentru relații inverse (Right-BICEP - I)
     * Verifică că adunarea și scăderea sunt operații inverse
     */
    public void testInverseAddSubtract() {
        int a = 15, b = 7;
        int sum = calculator.add(a, b);
        assertEquals("(a + b) - b = a", a, calculator.subtract(sum, b));
        assertEquals("(a + b) - a = b", b, calculator.subtract(sum, a));
    }
    
    /**
     * Test pentru relații inverse înmulțire/împărțire
     */
    public void testInverseMultiplyDivide() {
        int a = 24, b = 6;
        int product = calculator.multiply(a, b);
        assertEquals("(a * b) / b = a", a, calculator.divide(product, b));
        assertEquals("(a * b) / a = b", b, calculator.divide(product, a));
    }
    
    // ==================== TEST SUITE ====================
    
    /**
     * Definește un Test Suite parțial - doar anumite teste
     */
    public static Test suite() {
        TestSuite suite = new TestSuite("Calculator Tests");
        
        // Adaugă teste specifice
        suite.addTest(new CalculatorTestJUnit3("testAdd"));
        suite.addTest(new CalculatorTestJUnit3("testSubtract"));
        suite.addTest(new CalculatorTestJUnit3("testDivideByZero"));
        suite.addTest(new CalculatorTestJUnit3("testPower"));
        
        return suite;
    }
    
    /**
     * Test Suite complet - toată clasa
     */
    public static Test suiteComplete() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(CalculatorTestJUnit3.class);
        return suite;
    }
    
    // ==================== METODE HELPER PENTRU TESTE ====================
    
    /**
     * Metodă helper pentru testarea multiplelor valori
     */
    private void assertMultipleAdditions(int[][] testCases) {
        for (int[] testCase : testCases) {
            int a = testCase[0];
            int b = testCase[1];
            int expected = testCase[2];
            assertEquals(a + " + " + b + " = " + expected, 
                        expected, calculator.add(a, b));
        }
    }
    
    /**
     * Test care folosește metoda helper
     */
    public void testMultipleAdditionsWithHelper() {
        int[][] testCases = {
            {1, 1, 2},
            {5, 7, 12},
            {-3, 8, 5},
            {0, 0, 0},
            {-5, -3, -8}
        };
        
        assertMultipleAdditions(testCases);
    }
} 