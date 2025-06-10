package examples;

import org.junit.*;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import static org.junit.Assert.*;

/**
 * Exemplu de testare cu JUnit 4
 * Demonstrează toate adnotările și funcționalitățile moderne
 */
public class CalculatorTestJUnit4 {
    
    // Fixture - obiectele necesare testării
    private Calculator calculator;
    private static int testCount = 0;
    
    // ==================== CATEGORII PENTRU ORGANIZAREA TESTELOR ====================
    
    /**
     * Categorie pentru teste rapide
     */
    public interface FastTests {}
    
    /**
     * Categorie pentru teste lente
     */
    public interface SlowTests {}
    
    /**
     * Categorie pentru teste de integrare
     */
    public interface IntegrationTests {}
    
    // ==================== SETUP ȘI CLEANUP ====================
    
    /**
     * Se execută o singură dată înainte de toate testele din clasă
     */
    @BeforeClass
    public static void setUpBeforeClass() {
        System.out.println("=== ÎNCEPEREA TESTELOR CALCULATOR ===");
        System.out.println("Inițializare globală pentru toată clasa de teste");
        testCount = 0;
    }
    
    /**
     * Se execută o singură dată după toate testele din clasă
     */
    @AfterClass
    public static void tearDownAfterClass() {
        System.out.println("=== FINALIZAREA TESTELOR CALCULATOR ===");
        System.out.println("Cleanup global - au fost executate " + testCount + " teste");
    }
    
    /**
     * Se execută înaintea fiecărui test
     */
    @Before
    public void setUp() {
        testCount++;
        System.out.println("Test #" + testCount + " - Inițializez Calculator");
        calculator = new Calculator();
    }
    
    /**
     * Se execută după fiecare test
     */
    @After
    public void tearDown() {
        System.out.println("Test #" + testCount + " - Curăț resursele");
        calculator = null;
    }
    
    // ==================== TESTE DE BAZĂ CU DIFERITE ASSERT-URI ====================
    
    /**
     * Test rapid pentru operații de bază
     */
    @Category(FastTests.class)
    @Test
    public void testBasicOperations() {
        // Test adunare
        assertEquals("2 + 3 = 5", 5, calculator.add(2, 3));
        
        // Test scădere
        assertEquals("10 - 4 = 6", 6, calculator.subtract(10, 4));
        
        // Test înmulțire
        assertEquals("3 * 7 = 21", 21, calculator.multiply(3, 7));
        
        // Test împărțire
        assertEquals("15 / 3 = 5", 5, calculator.divide(15, 3));
    }
    
    /**
     * Test pentru verificări booleene
     */
    @Category(FastTests.class)
    @Test
    public void testBooleanAssertions() {
        // assertTrue și assertFalse
        assertTrue("2 trebuie să fie prim", calculator.isPrime(2));
        assertTrue("17 trebuie să fie prim", calculator.isPrime(17));
        
        assertFalse("4 nu trebuie să fie prim", calculator.isPrime(4));
        assertFalse("15 nu trebuie să fie prim", calculator.isPrime(15));
    }
    
    /**
     * Test pentru verificări null
     */
    @Category(FastTests.class)
    @Test
    public void testNullAssertions() {
        // Objectul calculator nu trebuie să fie null
        assertNotNull("Calculator nu trebuie să fie null", calculator);
        
        // După tearDown, obiectul va fi null (demonstrativ)
        Calculator tempCalc = null;
        assertNull("Calculator temporar trebuie să fie null", tempCalc);
    }
    
    // ==================== TESTE PENTRU EXCEPȚII ====================
    
    /**
     * Test pentru excepție la împărțirea la zero (JUnit 4 style)
     */
    @Category(FastTests.class)
    @Test(expected = IllegalArgumentException.class)
    public void testDivideByZeroException() {
        calculator.divide(10, 0);
        // Nu mai este nevoie de try-catch, JUnit se ocupă de asta
    }
    
    /**
     * Test pentru excepție cu verificarea mesajului
     */
    @Category(FastTests.class)
    @Test
    public void testDivideByZeroWithMessage() {
        try {
            calculator.divide(5, 0);
            fail("Trebuia să arunce IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Mesajul de eroare trebuie să fie corect",
                        "Nu se poate împărți la zero!", e.getMessage());
        }
    }
    
    /**
     * Test pentru exponent negativ
     */
    @Category(FastTests.class)
    @Test(expected = IllegalArgumentException.class)
    public void testPowerNegativeExponent() {
        calculator.power(5, -2);
    }
    
    /**
     * Test pentru factorial negativ
     */
    @Category(FastTests.class)
    @Test(expected = IllegalArgumentException.class)
    public void testFactorialNegative() {
        calculator.factorial(-3);
    }
    
    /**
     * Test pentru verificare număr prim cu valoare prea mică
     */
    @Category(FastTests.class)
    @Test(expected = IllegalArgumentException.class)
    public void testIsPrimeInvalidInput() {
        calculator.isPrime(1);
    }
    
    // ==================== TESTE CU TIMEOUT ====================
    
    /**
     * Test pentru performanță - factorial mare
     * Trebuie să se termine în maximum 100ms
     */
    @Category(SlowTests.class)
    @Test(timeout = 100)
    public void testFactorialPerformance() {
        // Calculează factorial pentru numere mai mari
        assertEquals("10! = 3628800", 3628800, calculator.factorial(10));
        assertEquals("12! = 479001600", 479001600, calculator.factorial(12));
    }
    
    /**
     * Test pentru performanță - verificare numere prime
     */
    @Category(SlowTests.class)
    @Test(timeout = 50)
    public void testPrimeCheckPerformance() {
        // Verifică câteva numere prime mai mari
        assertTrue("97 este prim", calculator.isPrime(97));
        assertTrue("101 este prim", calculator.isPrime(101));
        assertFalse("99 nu este prim", calculator.isPrime(99));
    }
    
    // ==================== TESTE DEZACTIVATE TEMPORAR ====================
    
    /**
     * Test dezactivat temporar - în dezvoltare
     */
    @Ignore("Funcționalitatea încă nu este implementată")
    @Test
    public void testSquareRoot() {
        // Acest test va fi implementat când se adaugă funcția sqrt
        // assertEquals(3.0, calculator.sqrt(9), 0.001);
    }
    
    /**
     * Test dezactivat pentru debugging
     */
    @Ignore("Temporar dezactivat pentru debugging")
    @Test
    public void testComplexOperation() {
        // Test complex care cauzează probleme
        fail("Acest test nu trebuie să ruleze acum");
    }
    
    // ==================== TESTE PENTRU BOUNDARY CONDITIONS ====================
    
    /**
     * Test pentru limitele intervalelor (CORRECT - Range)
     */
    @Category(IntegrationTests.class)
    @Test
    public void testBoundaryConditions() {
        // Testează limitele pentru operații
        
        // Limite pentru adunare
        assertEquals("MAX + 0", Integer.MAX_VALUE, calculator.add(Integer.MAX_VALUE, 0));
        assertEquals("MIN + 0", Integer.MIN_VALUE, calculator.add(Integer.MIN_VALUE, 0));
        
        // Limite pentru înmulțire
        assertEquals("MAX * 1", Integer.MAX_VALUE, calculator.multiply(Integer.MAX_VALUE, 1));
        assertEquals("MIN * 1", Integer.MIN_VALUE, calculator.multiply(Integer.MIN_VALUE, 1));
        assertEquals("Orice * 0 = 0", 0, calculator.multiply(Integer.MAX_VALUE, 0));
        
        // Teste cu zero (Existence)
        assertEquals("0^0 = 1", 1, calculator.power(0, 0));
        assertEquals("0! = 1", 1, calculator.factorial(0));
    }
    
    // ==================== TEST PENTRU CROSS-CHECK ====================
    
    /**
     * Test pentru verificarea încrucișată (Right-BICEP - C)
     */
    @Category(IntegrationTests.class)
    @Test
    public void testCrossCheck() {
        // Verifică puterea prin înmulțiri repetate
        int base = 3, exponent = 4;
        int powerResult = calculator.power(base, exponent);
        
        // Calculează prin înmulțiri repetate
        int manualResult = 1;
        for (int i = 0; i < exponent; i++) {
            manualResult = calculator.multiply(manualResult, base);
        }
        
        assertEquals("3^4 calculat prin power() și înmulțiri repetate", 
                    powerResult, manualResult);
    }
} 