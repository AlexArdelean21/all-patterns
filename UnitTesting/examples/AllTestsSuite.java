package examples;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Demonstrează diferite modalități de creare a Test Suites
 * conform conceptelor din curs
 */
public class AllTestsSuite {
    
    // ==================== JUNIT 3 TEST SUITES ====================
    
    /**
     * Test Suite complet JUnit 3 - include toate testele din toate clasele
     */
    public static Test suiteCompleteJUnit3() {
        TestSuite suite = new TestSuite("Suite Complet Calculator");
        
        // Adaugă întreaga clasă de teste
        suite.addTestSuite(CalculatorTestJUnit3.class);
        
        // Poate adăuga și alte clase de teste când vor exista
        // suite.addTestSuite(MathUtilsTest.class);
        // suite.addTestSuite(StatisticsTest.class);
        
        return suite;
    }
    
    /**
     * Test Suite parțial JUnit 3 - doar anumite teste
     */
    public static Test suitePartialJUnit3() {
        TestSuite suite = new TestSuite("Suite Parțial - Operații de Bază");
        
        // Adaugă doar testele de bază
        suite.addTest(new CalculatorTestJUnit3("testAdd"));
        suite.addTest(new CalculatorTestJUnit3("testSubtract"));
        suite.addTest(new CalculatorTestJUnit3("testMultiply"));
        suite.addTest(new CalculatorTestJUnit3("testDivide"));
        
        return suite;
    }
    
    /**
     * Test Suite pentru testarea excepțiilor JUnit 3
     */
    public static Test suiteExceptionsJUnit3() {
        TestSuite suite = new TestSuite("Suite Excepții");
        
        suite.addTest(new CalculatorTestJUnit3("testDivideByZero"));
        suite.addTest(new CalculatorTestJUnit3("testPowerWithNegativeExponent"));
        suite.addTest(new CalculatorTestJUnit3("testFactorialNegativeNumber"));
        
        return suite;
    }
    
    // ==================== JUNIT 4 TEST SUITES ====================
    
    /**
     * Test Suite complet JUnit 4 - toate clasele
     */
    @RunWith(Suite.class)
    @Suite.SuiteClasses({
        CalculatorTestJUnit4.class,
        CalculatorTestJUnit3.class
        // Pot fi adăugate alte clase aici
    })
    public static class CompleteSuite {
        // Clasa poate fi goală - Suite runner se ocupă de execuție
    }
    
    /**
     * Test Suite cu categorii - doar teste rapide
     */
    @RunWith(Categories.class)
    @Categories.IncludeCategory(CalculatorTestJUnit4.FastTests.class)
    @Suite.SuiteClasses({
        CalculatorTestJUnit4.class
    })
    public static class FastTestsSuite {
        // Rulează doar testele marcate cu @Category(FastTests.class)
    }
    
    /**
     * Test Suite cu categorii - doar teste lente
     */
    @RunWith(Categories.class)
    @Categories.IncludeCategory(CalculatorTestJUnit4.SlowTests.class)
    @Suite.SuiteClasses({
        CalculatorTestJUnit4.class
    })
    public static class SlowTestsSuite {
        // Rulează doar testele marcate cu @Category(SlowTests.class)
    }
    
    /**
     * Test Suite cu categorii - teste de integrare
     */
    @RunWith(Categories.class)
    @Categories.IncludeCategory(CalculatorTestJUnit4.IntegrationTests.class)
    @Suite.SuiteClasses({
        CalculatorTestJUnit4.class
    })
    public static class IntegrationTestsSuite {
        // Rulează doar testele marcate cu @Category(IntegrationTests.class)
    }
    
    /**
     * Test Suite cu categorii multiple - teste rapide ȘI de integrare
     */
    @RunWith(Categories.class)
    @Categories.IncludeCategory({
        CalculatorTestJUnit4.FastTests.class,
        CalculatorTestJUnit4.IntegrationTests.class
    })
    @Suite.SuiteClasses({
        CalculatorTestJUnit4.class
    })
    public static class FastAndIntegrationSuite {
        // Rulează testele care sunt marcate cu FastTests SAU IntegrationTests
    }
    
    /**
     * Test Suite cu excludere - toate testele EXCEPTÂND cele lente
     */
    @RunWith(Categories.class)
    @Categories.ExcludeCategory(CalculatorTestJUnit4.SlowTests.class)
    @Suite.SuiteClasses({
        CalculatorTestJUnit4.class
    })
    public static class AllExceptSlowSuite {
        // Rulează toate testele EXCEPTÂND cele marcate cu @Category(SlowTests.class)
    }
}

/**
 * Exemplu de clasă care demonstrează cum să creezi categorii personalizate
 */
class TestCategories {
    
    /**
     * Categorie pentru teste de smoke (verificări de bază)
     */
    public interface SmokeTests {}
    
    /**
     * Categorie pentru teste de regresie
     */
    public interface RegressionTests {}
    
    /**
     * Categorie pentru teste care necesită resurse externe
     */
    public interface ExternalResourceTests {}
    
    /**
     * Categorie pentru teste care rulează doar pe Windows
     */
    public interface WindowsOnlyTests {}
    
    /**
     * Categorie pentru teste care rulează doar pe Linux
     */
    public interface LinuxOnlyTests {}
} 