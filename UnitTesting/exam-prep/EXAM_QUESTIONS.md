# Întrebări de Examen - Unit Testing cu JUnit

## Întrebări Teoretice

### 1. Concepte de Bază

**Q1:** Ce este Unit Testing și care sunt caracteristicile sale principale?

**Răspuns:** Unit Testing este o metodă simplă și rapidă de testare a codului sursă de către programatori care:
- Are loc în faza de dezvoltare
- Evaluează părți mici și bine definite din cod (clase sau metode)
- Este automată și reutilizabilă
- Servește ca bloc de bază pentru TDD

**Q2:** Explicați ciclul TDD și cele 3 faze ale sale.

**Răspuns:** TDD (Test-Driven Development) constă în 3 faze:
1. **RED** - Scrie un test care eșuează
2. **GREEN** - Scrie codul minim pentru a trece testul
3. **REFACTOR** - Refactorizează codul la standarde acceptabile

**Q3:** Enumerați și explicați tipurile de testare menționate în curs.

**Răspuns:**
- **Unit Testing** - testarea celor mai mici părți din cod
- **Integration Testing** - testarea componentelor combinate
- **System Testing** - testarea întregului sistem
- **Acceptance Testing** - teste realizate de client
- **Regression Testing** - testarea automată după modificări
- **Black-box Testing** - testarea fără cunoștințe de implementare
- **White-box Testing** - testarea cu cunoștințe complete

### 2. CORRECT și Right-BICEP

**Q4:** Explicați acronimul CORRECT pentru Boundary Conditions.

**Răspuns:**
- **C**onformance - Valoarea are formatul corect?
- **O**rdering - Setul trebuie ordonat?
- **R**ange - Valoarea e în limitele acceptate?
- **R**eference - Referă componente externe?
- **E**xistence - Valoarea există (non-null)?
- **C**ardinality - Setul conține suficiente valori (0-1-n)?
- **T**ime - Totul se întâmplă la momentul potrivit?

**Q5:** Explicați acronimul Right-BICEP.

**Răspuns:**
- **Right** - Rezultatele sunt corecte?
- **B**oundary conditions - Limitele sunt definite CORRECT?
- **I**nverse relationships - Poți verifica opusul?
- **C**ross-check - Rezultatul poate fi verificat prin alte metode?
- **E**rror conditions - Condițiile de eroare pot fi evaluate?
- **P**erformance - Performanța e în limite acceptabile?

### 3. JUnit 3 vs JUnit 4

**Q6:** Care sunt diferențele principale între JUnit 3 și JUnit 4?

**Răspuns:**

**JUnit 3:**
- Extinde `TestCase`
- Constructor obligatoriu
- Metode `setUp()` și `tearDown()`
- Nume teste încep cu "test"
- Test Suites prin metode statice

**JUnit 4:**
- Folosește adnotări (`@Test`, `@Before`, `@After`)
- Nu mai extinde `TestCase`
- `@BeforeClass` și `@AfterClass`
- `@Ignore` pentru dezactivare
- `@Test(expected=Exception.class)`
- `@Test(timeout=1000)`
- Categorii cu `@Category`

**Q7:** Cum creezi un Test Suite în JUnit 3?

**Răspuns:**
```java
public static Test suite() {
    TestSuite suite = new TestSuite();
    // Întreaga clasă
    suite.addTestSuite(TestClass.class);
    // Teste specifice
    suite.addTest(new TestClass("testMethod"));
    return suite;
}
```

**Q8:** Cum creezi un Test Suite în JUnit 4?

**Răspuns:**
```java
@RunWith(Suite.class)
@Suite.SuiteClasses({
    TestClass1.class,
    TestClass2.class
})
public class TestSuite {
    // Clasa poate fi goală
}
```

### 4. Adnotări JUnit 4

**Q9:** Explicați rolul fiecărei adnotări din JUnit 4.

**Răspuns:**
- `@Test` - marchează o metodă ca test unit
- `@Before` - se execută înaintea fiecărui test
- `@After` - se execută după fiecare test
- `@BeforeClass` - se execută o dată la început
- `@AfterClass` - se execută o dată la sfârșit
- `@Ignore` - ignoră testul
- `@Test(expected=Exception.class)` - verifică excepții
- `@Test(timeout=1000)` - limitează timpul de execuție
- `@Category` - organizează testele în categorii

**Q10:** Când folosești `@Before` vs `@BeforeClass`?

**Răspuns:**
- `@Before` - pentru inițializare necesară fiecărui test individual
- `@BeforeClass` - pentru inițializare costisitoare care se face o singură dată pentru toată clasa

## Întrebări Practice

### 5. Scrierea Testelor

**Q11:** Scrie un test JUnit 4 care verifică că o metodă `divide(int a, int b)` aruncă `IllegalArgumentException` când b=0.

**Răspuns:**
```java
@Test(expected = IllegalArgumentException.class)
public void testDivideByZero() {
    calculator.divide(10, 0);
}
```

**Q12:** Scrie un test care verifică că o operație se termină în maximum 100ms.

**Răspuns:**
```java
@Test(timeout = 100)
public void testPerformance() {
    calculator.complexOperation();
}
```

**Q13:** Scrie un test care verifică boundary conditions pentru o metodă `factorial(int n)`.

**Răspuns:**
```java
@Test
public void testFactorialBoundaryConditions() {
    // Existence (0)
    assertEquals(1, calculator.factorial(0));
    
    // Range (valori mici pozitive)
    assertEquals(1, calculator.factorial(1));
    assertEquals(6, calculator.factorial(3));
    
    // Error conditions (valori negative)
    try {
        calculator.factorial(-1);
        fail("Trebuia să arunce excepție");
    } catch (IllegalArgumentException e) {
        // Expected
    }
}
```

### 6. Test Suites și Categorii

**Q14:** Creează un Test Suite JUnit 4 care include doar testele rapide dintr-o clasă.

**Răspuns:**
```java
@RunWith(Categories.class)
@IncludeCategory(FastTests.class)
@Suite.SuiteClasses({MyTestClass.class})
public class FastTestsSuite {
}
```

**Q15:** Cum marchezi un test ca aparținând unei categorii specifice?

**Răspuns:**
```java
@Category(FastTests.class)
@Test
public void testQuickOperation() {
    // test rapid
}
```

### 7. Assert-uri

**Q16:** Enumerați principalele tipuri de assert-uri și când se folosesc.

**Răspuns:**
- `assertEquals(expected, actual)` - verifică egalitatea
- `assertTrue(condition)` - verifică că e true
- `assertFalse(condition)` - verifică că e false
- `assertNull(object)` - verifică că e null
- `assertNotNull(object)` - verifică că nu e null
- `assertSame(expected, actual)` - verifică aceeași referință
- `assertNotSame(expected, actual)` - verifică referințe diferite
- `fail(message)` - forțează eșecul testului

**Q17:** Care este diferența între `assertEquals` și `assertSame`?

**Răspuns:**
- `assertEquals` - verifică egalitatea folosind `equals()`
- `assertSame` - verifică că sunt exact același obiect (aceeași referință)

### 8. Scenarii de Testare

**Q18:** Pentru o metodă `isPrime(int n)`, ce boundary conditions trebuie testate conform CORRECT?

**Răspuns:**
- **Conformance**: n trebuie să fie întreg
- **Range**: n >= 2 (numere mai mici nu pot fi prime)
- **Existence**: verificare pentru valori null (dacă e aplicabil)
- **Cardinality**: teste pentru 0 valori invalide, 1 valoare validă, n valori
- Cazuri specifice: 2 (primul prim), numere pare, numere impare, numere mari

**Q19:** Cum testezi inverse relationships pentru operațiile matematice?

**Răspuns:**
```java
@Test
public void testInverseOperations() {
    int a = 15, b = 7;
    
    // Adunare/Scădere
    assertEquals(a, calculator.subtract(calculator.add(a, b), b));
    
    // Înmulțire/Împărțire
    int c = 24, d = 6;
    assertEquals(c, calculator.divide(calculator.multiply(c, d), d));
}
```

### 9. Gestionarea Excepțiilor

**Q20:** Scrie un test JUnit 3 care verifică că o metodă aruncă o excepție specifică.

**Răspuns:**
```java
public void testExceptionJUnit3() {
    try {
        calculator.divide(5, 0);
        fail("Trebuia să arunce IllegalArgumentException");
    } catch (IllegalArgumentException e) {
        assertEquals("Nu se poate împărți la zero!", e.getMessage());
    }
}
```

## Întrebări Complexe de Sinteză

**Q21:** Proiectați o strategie de testare completă pentru o clasă `BankAccount` cu metode `deposit(double amount)`, `withdraw(double amount)`, `getBalance()`. Includeți toate aspectele CORRECT și Right-BICEP.

**Q22:** Explicați de ce TDD poate reduce numărul de bug-uri și cum se integrează cu Unit Testing.

**Q23:** Comparați avantajele și dezavantajele White-box vs Black-box testing în contextul Unit Testing.

**Q24:** Descrieți cum ați organiza testele pentru un proiect mare folosind Test Suites și categorii în JUnit 4.

**Q25:** Explicați relația dintre Unit Testing, Integration Testing și System Testing în contextul unui ciclu complet de dezvoltare.

---

## Checklist Final pentru Examen

- [ ] Știu să definesc Unit Testing și TDD
- [ ] Pot explica CORRECT și Right-BICEP cu exemple
- [ ] Cunosc diferențele JUnit 3 vs JUnit 4
- [ ] Știu toate adnotările JUnit 4 și când se folosesc
- [ ] Pot scrie teste pentru excepții (ambele versiuni)
- [ ] Știu să creez Test Suites (ambele versiuni)
- [ ] Pot organiza teste în categorii
- [ ] Înțeleg toate tipurile de assert-uri
- [ ] Pot identifica boundary conditions pentru orice metodă
- [ ] Știu să testez inverse relationships
- [ ] Pot scrie teste cu timeout
- [ ] Știu când să folosesc @Ignore și de ce 