# Unit Testing cu JUnit - Ghid Complet de Învățare

## Cuprins
1. [Ce este Unit Testing?](#ce-este-unit-testing)
2. [Test-Driven Development (TDD)](#test-driven-development-tdd)
3. [Tipuri de testare](#tipuri-de-testare)
4. [De ce să folosești Unit Testing?](#de-ce-să-folosești-unit-testing)
5. [Ce să testezi?](#ce-să-testezi)
6. [CORRECT Boundary Conditions](#correct-boundary-conditions)
7. [The Right-BICEP](#the-right-bicep)
8. [JUnit 3 vs JUnit 4](#junit-3-vs-junit-4)
9. [Structura unui Test Case](#structura-unui-test-case)
10. [Test Suites](#test-suites)
11. [Exemple Practice](#exemple-practice)
12. [Exerciții pentru Examen](#exerciții-pentru-examen)

---

## Ce este Unit Testing?

**Unit Testing** este o metodă simplă și rapidă de testare a codului sursă de către programatori care:
- Are loc în faza de dezvoltare și este destinat programatorilor
- Evaluează o parte bine definită și de mici dimensiuni din codul sursă (clasă sau metodă)
- Evaluează modul de funcționare al unei metode într-un context bine definit
- Este blocul de bază pentru abordarea Test-Driven Development

### Caracteristici cheie:
- **Rapiditate**: Testele se execută foarte repede
- **Automatizare**: Pot fi rulate automat de fiecare dată când e nevoie
- **Simplitate**: Ușor de scris și înțeles
- **Independență**: Fiecare test trebuie să fie independent

---

## Test-Driven Development (TDD)

TDD este un proces de dezvoltare software care se bazează pe repetarea unui ciclu foarte scurt:

```
1. SCRIE un test care eșuează (RED)
   ↓
2. SCRIE codul minim pentru a trece testul (GREEN)
   ↓
3. REFACTORIZEAZĂ codul la standarde acceptabile (REFACTOR)
   ↓
(repetă ciclul)
```

### Avantajele TDD:
- Cod mai curat și mai bine structurat
- Documentație vie a codului
- Reducerea bug-urilor
- Încredere în modificările de cod

---

## Tipuri de testare

### 1. **Unit Testing**
- Testarea celor mai mici părți din cod (clase sau metode)

### 2. **Integration Testing**
- Testarea mai multor componente combinate sau integrate

### 3. **System Testing**
- Testarea întregului sistem (toate componentele)

### 4. **Acceptance Testing**
- Teste realizate de client pentru a verifica dacă design-ul corespunde cerințelor

### 5. **Regression Testing**
- Testarea automată după modificări pentru a evita reapariția bug-urilor

### 6. **Black-box Testing**
- Testarea interfeței publice fără informații despre implementare

### 7. **White-box Testing**
- Testarea cu informații complete despre implementare

---

## De ce să folosești Unit Testing?

### ✅ **Avantaje:**
- **Ușor de scris** - Sintaxă simplă și intuitivă
- **Reutilizabil** - *Write once, use many times*
- **Detectare rapidă** - Identifică bug-urile în faza de dezvoltare
- **Reducere costuri** - Costul reparării bug-urilor scade exponențial cu timpul
- **Încredere în cod** - Permite refactorizări sigure
- **Documentație** - Testele servesc ca documentație vie

### ❌ **Scuze comune (de evitat):**
- "Nu am timp" - De fapt, economisește timp pe termen lung
- "Este greu" - Cu practică devine natural
- "Nu știu să fac asta" - Se învață rapid cu exercițiu
- "Nu e în specificații" - Este o practică esențială de dezvoltare

---

## Ce să testezi?

### Scenarii de testat:
- ✅ **Scenarii reale** - Cazuri normale de utilizare
- ✅ **Limitele intervalelor** - Valorile de la marginea domeniului
- ✅ **Valori negative** (-1, -2, -100, etc.)
- ✅ **Zero** (0)
- ✅ **Valori pozitive** (1, 2, 100, etc.)
- ✅ **Null** - Referințe inexistente
- ✅ **String-uri goale** ("")
- ✅ **Colecții goale** ([], {})

---

## CORRECT Boundary Conditions

Acronimul **CORRECT** te ajută să identifici condițiile de testare:

### **C** - Conformance (Conformitate)
- Valoarea are formatul corect?
- Exemple: format email, format dată, număr telefon

### **O** - Ordering (Ordonare)
- Setul de valori trebuie să fie ordonat sau nu?
- Exemple: liste sortate, secvențe cronologice

### **R** - Range (Interval)
- Este valoarea între limitele acceptate (min-max)?
- Exemple: vârstă 0-150, note 1-10

### **R** - Reference (Referință)
- Codul referă componente externe necontrolate direct?
- Exemple: fișiere, baze de date, servicii web

### **E** - Existence (Existență)
- Valoarea există (non-null, non-zero, parte din set)?
- Exemple: verificare null, verificare fișier existent

### **C** - Cardinality (Cardinalitate)
- Setul conține suficiente valori (regula 0-1-n)?
- Exemple: liste goale, cu un element, cu multe elemente

### **T** - Time (Timp)
- Totul se întâmplă în ordine și la momentul potrivit?
- Exemple: timeout-uri, sincronizare, fuse orare

---

## The Right-BICEP

Acronimul **Right-BICEP** te ghidează în scrierea testelor complete:

### **Right** - Rezultate corecte
- Sunt rezultatele corecte?

### **B** - Boundary conditions
- Sunt limitele definite CORRECT?

### **I** - Inverse relationships
- Poți verifica opusul situației?

### **C** - Cross-check
- Se poate verifica rezultatul prin alte metode?

### **E** - Error conditions
- Se pot evalua condițiile care generează erori?

### **P** - Performance characteristics
- Performanța execuției este în limite acceptabile?

---

## JUnit 3 vs JUnit 4

### JUnit 3 (Versiunea clasică)
```java
import junit.framework.TestCase;

public class TestMath extends TestCase {
    // Constructor obligatoriu
    public TestMath(String name) {
        super(name);
    }
    
    // setUp se execută înaintea fiecărui test
    public void setUp() {
        // inițializare resurse
    }
    
    // tearDown se execută după fiecare test
    public void tearDown() {
        // eliberare resurse
    }
    
    // Metoda de test (începe cu "test")
    public void testAdunare() {
        assertEquals(5, Math.add(2, 3));
    }
}
```

### JUnit 4 (Cu adnotări)
```java
import org.junit.*;

public class TestMath {
    
    @Before
    public void setUp() {
        // inițializare resurse
    }
    
    @After
    public void tearDown() {
        // eliberare resurse
    }
    
    @BeforeClass
    public static void setUpBeforeClass() {
        // inițializare globală (o singură dată)
    }
    
    @AfterClass
    public static void tearDownAfterClass() {
        // cleanup global (o singură dată)
    }
    
    @Test
    public void testAdunare() {
        assertEquals(5, Math.add(2, 3));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testExceptie() {
        Math.divide(5, 0);
    }
    
    @Test(timeout = 1000)
    public void testPerformanta() {
        // testul trebuie să se termine în max 1 secundă
    }
    
    @Ignore("Temporar dezactivat")
    @Test
    public void testDezactivat() {
        // acest test nu se va executa
    }
}
```

---

## Structura unui Test Case

### Anatomia unui test:
```java
@Test
public void testNumeDescriptiv() {
    // 1. ARRANGE - Pregătire (Given)
    Calculator calc = new Calculator();
    int a = 5, b = 3;
    
    // 2. ACT - Acțiune (When)
    int rezultat = calc.aduna(a, b);
    
    // 3. ASSERT - Verificare (Then)
    assertEquals(8, rezultat);
}
```

### Metode Assert principale:
```java
// Verificări de egalitate
assertEquals(expected, actual);
assertEquals("Mesaj custom", expected, actual);

// Verificări booleene
assertTrue(condition);
assertFalse(condition);

// Verificări null
assertNull(object);
assertNotNull(object);

// Verificări referințe
assertSame(expected, actual);        // același obiect
assertNotSame(expected, actual);     // obiecte diferite

// Forțare eșec
fail("Mesaj de eșec");
```

---

## Test Suites

### JUnit 3 - Suite tradiționale
```java
public static Test suite() {
    TestSuite suite = new TestSuite();
    
    // Adaugă întreaga clasă
    suite.addTestSuite(TestMath.class);
    
    // Adaugă teste specifice
    suite.addTest(new TestMath("testAdunare"));
    suite.addTest(new TestMath("testScadere"));
    
    return suite;
}
```

### JUnit 4 - Suite cu adnotări
```java
@RunWith(Suite.class)
@Suite.SuiteClasses({
    TestMath.class,
    TestCalculator.class,
    TestStatistics.class
})
public class AllTestsSuite {
    // Clasa poate fi goală
}
```

### Suite cu categorii (JUnit 4)
```java
// Definire categorie
public interface FastTests { }
public interface SlowTests { }

// Marcare teste
@Category(FastTests.class)
@Test
public void testRapid() { }

@Category(SlowTests.class)
@Test
public void testLent() { }

// Suite pentru categoria fast
@RunWith(Categories.class)
@IncludeCategory(FastTests.class)
@Suite.SuiteClasses({
    TestMath.class,
    TestCalculator.class
})
public class FastTestsSuite { }
```

---

## Exemple Practice

Vezi folderele:
- `examples/` - Exemple complete de cod
- `exercises/` - Exerciții practice
- `exam-prep/` - Întrebări tip examen

---

## Exerciții pentru Examen

### Întrebări teoretice importante:

1. **Ce este TDD și care sunt cele 3 faze?**
2. **Diferența între JUnit 3 și JUnit 4?**
3. **Ce înseamnă CORRECT și Right-BICEP?**
4. **Când folosești @Before vs @BeforeClass?**
5. **Cum creezi un Test Suite?**
6. **Ce tipuri de assert-uri știi?**

### Exerciții practice:

1. **Scrie teste pentru o clasă Calculator**
2. **Creează un Test Suite care să includă mai multe clase de test**
3. **Scrie teste care verifică excepții**
4. **Implementează teste cu timeout**
5. **Creează teste cu categorii**

---

## Resurse suplimentare

- **Cărți recomandate:**
  - Lasse Koskela - "Effective Unit Testing", Manning, 2013
  - Andrew Hunt, David Thomas - "Pragmatic Unit Testing in Java with JUnit"

- **Site-uri utile:**
  - [junit.org](http://www.junit.org)
  - [Vogella JUnit Tutorial](http://www.vogella.com/tutorials/JUnit/article.html)

---

## Checklist pentru Examen

- [ ] Știu să explic ce este Unit Testing
- [ ] Înțeleg principiile TDD
- [ ] Cunosc diferențele JUnit 3 vs JUnit 4
- [ ] Pot scrie teste cu toate tipurile de assert
- [ ] Știu să creez Test Suites
- [ ] Înțeleg CORRECT și Right-BICEP
- [ ] Pot scrie teste pentru excepții și timeout
- [ ] Știu să folosesc @Before, @After, @BeforeClass, @AfterClass
- [ ] Pot organiza teste în categorii

**Succes la examen! 🚀** 