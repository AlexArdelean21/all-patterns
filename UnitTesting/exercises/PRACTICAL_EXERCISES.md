# Exerciții Practice - Unit Testing cu JUnit

## Exercițiul 1: Clasa Student (JUnit 3)

### Cerința:
Creați o clasă `Student` cu următoarele metode:
- `Student(String nume, int varsta, double nota)`
- `String getNume()`
- `int getVarsta()`
- `double getNota()`
- `boolean isPromovat()` - returnează true dacă nota >= 5
- `void setNota(double nota)` - validează că nota e între 1-10

### Implementați:
1. Clasa `Student` cu validări corespunzătoare
2. Clasa de teste `StudentTestJUnit3` care extinde `TestCase`
3. Testați toate boundary conditions folosind CORRECT
4. Creați un Test Suite care rulează doar testele de validare

### Template de pornire:
```java
public class Student {
    private String nume;
    private int varsta;
    private double nota;
    
    // TODO: Implementați constructorul cu validări
    // TODO: Implementați getteri
    // TODO: Implementați setNota cu validare
    // TODO: Implementați isPromovat
}
```

### Puncte de verificat:
- [ ] Constructorul validează parametrii (nume non-null, vârstă 14-100, nota 1-10)
- [ ] `setNota` aruncă excepție pentru valori invalide
- [ ] `isPromovat` returnează corect pentru toate cazurile
- [ ] Teste pentru toate boundary conditions (CORRECT)
- [ ] Test Suite funcțional

---

## Exercițiul 2: Clasa BankAccount (JUnit 4)

### Cerința:
Creați o clasă `BankAccount` cu următoarele funcționalități:
- `BankAccount(String iban, double soldInitial)`
- `void deposit(double suma)` - adaugă bani
- `void withdraw(double suma)` - scoate bani (validare sold suficient)
- `double getBalance()` - returnează soldul
- `void transfer(BankAccount destinatie, double suma)` - transfer între conturi
- `List<String> getTransactionHistory()` - istoricul tranzacțiilor

### Implementați:
1. Clasa `BankAccount` cu toate validările
2. Teste JUnit 4 cu toate adnotările moderne
3. Teste cu categorii (FastTests, SlowTests, IntegrationTests)
4. Teste pentru excepții, timeout și inverse relationships

### Template de pornire:
```java
public class BankAccount {
    private String iban;
    private double balance;
    private List<String> transactionHistory;
    
    // TODO: Implementați toate metodele cu validări
}
```

### Testele trebuie să includă:
- [ ] `@Test` pentru operații normale
- [ ] `@Test(expected=Exception.class)` pentru cazuri de eroare
- [ ] `@Test(timeout=100)` pentru operații care trebuie să fie rapide
- [ ] `@Category` pentru organizarea testelor
- [ ] `@Before/@After` pentru setup/cleanup
- [ ] `@BeforeClass/@AfterClass` pentru setup global
- [ ] Teste pentru inverse relationships (deposit/withdraw)
- [ ] Cross-check cu multiple metode de verificare

---

## Exercițiul 3: Clasa Matrix (Combinat JUnit 3 & 4)

### Cerința:
Creați o clasă `Matrix` pentru operații cu matrici:
- `Matrix(int rows, int cols)` - matrice cu zero
- `Matrix(double[][] data)` - din array 2D
- `double get(int row, int col)`
- `void set(int row, int col, double value)`
- `Matrix add(Matrix other)` - adunare matrici
- `Matrix multiply(Matrix other)` - înmulțire matrici
- `Matrix transpose()` - transpusa
- `double determinant()` - determinant (doar 2x2 și 3x3)
- `boolean equals(Object obj)` - comparare matrici

### Implementați:
1. Clasa `Matrix` completă
2. `MatrixTestJUnit3` - teste clasice cu TestCase
3. `MatrixTestJUnit4` - teste moderne cu adnotări
4. Test Suite care combină ambele clase de teste
5. Categorii pentru teste: BasicOperations, AdvancedOperations, PerformanceTests

### Puncte speciale de testat:
- [ ] Boundary conditions pentru indici (0, max, -1, prea mare)
- [ ] Compatibilitatea dimensiunilor pentru operații
- [ ] Excepții pentru operații invalide
- [ ] Performanța pentru matrici mari (timeout tests)
- [ ] Propriități matematice (A+B = B+A, (A^T)^T = A)

---

## Exercițiul 4: Sistema de Login (Test Suites Avansate)

### Cerința:
Creați un sistem simplu de autentificare:
- `User(String username, String password, String email, Role role)`
- `UserService` cu metode de management
- `AuthenticationService` pentru login/logout
- `PasswordValidator` pentru validarea parolelor

### Clasele necesare:
```java
enum Role { ADMIN, USER, GUEST }

class User {
    // TODO: proprietăți și metode
}

class UserService {
    public void createUser(User user) { /* TODO */ }
    public User findByUsername(String username) { /* TODO */ }
    public void updateUser(User user) { /* TODO */ }
    public void deleteUser(String username) { /* TODO */ }
}

class AuthenticationService {
    public boolean login(String username, String password) { /* TODO */ }
    public void logout(String username) { /* TODO */ }
    public boolean isLoggedIn(String username) { /* TODO */ }
}

class PasswordValidator {
    public boolean isValid(String password) { /* TODO */ }
    public List<String> getValidationErrors(String password) { /* TODO */ }
}
```

### Creați Test Suites pentru:
1. **Smoke Tests** - verificări de bază
2. **Security Tests** - teste de securitate
3. **Integration Tests** - teste între componente
4. **Performance Tests** - cu timeout-uri
5. **Complete Regression Suite** - toate testele

### Regulile de validare parole:
- Minimum 8 caractere
- Cel puțin o literă mare, una mică, o cifră
- Cel puțin un caracter special
- Nu poate conține username-ul

---

## Exercițiul 5: Simulare TDD - Calculator Scientific

### Cerința:
Folosind TDD strict, implementați un `ScientificCalculator` cu metode:
- Operații de bază (+, -, *, /)
- Funcții trigonometrice (sin, cos, tan)
- Logaritmi (log10, ln)
- Rădăcină pătrată și puteri
- Constante matematice (PI, E)

### Procesul TDD:
Pentru fiecare funcționalitate, urmați strict:

1. **RED** - Scrieți testul care eșuează
2. **GREEN** - Implementați codul minim
3. **REFACTOR** - Curățați codul

### Exemplu pentru sqrt():
```java
// STEP 1: RED - Testul eșuează
@Test
public void testSquareRoot() {
    assertEquals(3.0, calculator.sqrt(9.0), 0.001);
}

// STEP 2: GREEN - Implementare minimă
public double sqrt(double x) {
    return Math.sqrt(x); // implementare simplă
}

// STEP 3: REFACTOR - Adăugați validări
public double sqrt(double x) {
    if (x < 0) {
        throw new IllegalArgumentException("Cannot compute sqrt of negative number");
    }
    return Math.sqrt(x);
}
```

### Testați la fiecare pas:
- [ ] Boundary conditions pentru fiecare funcție
- [ ] Excepții pentru input invalid
- [ ] Precizie pentru operații cu double
- [ ] Performance pentru calcule complexe

---

## Exercițiul 6: Mock Objects și Dependency Injection

### Cerința:
Creați un sistem care testează componente cu dependențe:

```java
interface DatabaseService {
    void saveUser(User user);
    User getUserById(int id);
    List<User> getAllUsers();
}

interface EmailService {
    void sendWelcomeEmail(User user);
    void sendPasswordResetEmail(User user);
}

class UserManager {
    private DatabaseService db;
    private EmailService email;
    
    public UserManager(DatabaseService db, EmailService email) {
        this.db = db;
        this.email = email;
    }
    
    public void registerUser(User user) {
        // validare user
        db.saveUser(user);
        email.sendWelcomeEmail(user);
    }
    
    public void resetPassword(int userId) {
        User user = db.getUserById(userId);
        // generează parolă nouă
        email.sendPasswordResetEmail(user);
    }
}
```

### Implementați teste care:
1. Creează mock objects pentru dependențe
2. Verifică interacțiunile cu mock-urile
3. Testează comportamentul în diverse scenarii
4. Simulează erori din dependențe

---

## Ghid de Evaluare

### Pentru fiecare exercițiu, verificați:

#### Aspecte Tehnice:
- [ ] Implementarea corectă a claselor
- [ ] Gestionarea excepțiilor
- [ ] Validarea input-urilor
- [ ] Folosirea corectă a JUnit 3/4

#### Calitatea Testelor:
- [ ] Aplicarea CORRECT boundary conditions
- [ ] Implementarea Right-BICEP
- [ ] Coverage complet al codului
- [ ] Teste pentru happy path și edge cases

#### Organizarea:
- [ ] Structure logică a testelor
- [ ] Nomenclatură descriptivă
- [ ] Gruparea în categorii/suites
- [ ] Comentarii explicative

#### Practici Avansate:
- [ ] Setup/Teardown corespunzător
- [ ] Evitarea duplicatului de cod
- [ ] Teste independente
- [ ] Performanță optimă

### Criterii de Succes:
- **Începător**: Implementează clasele și testele de bază
- **Intermediar**: Aplică toate conceptele CORRECT și Right-BICEP
- **Avansat**: Organizează testele professional cu suites și categorii
- **Expert**: Folosește TDD și mock objects eficient

---

## Resurse Adiționale

### Pentru debugging teste:
```java
// Activează logging detaliat
System.setProperty("junit.verbose", "true");

// Afișează stack trace complet
@Test
public void testWithDetailedOutput() {
    try {
        // test code
    } catch (Exception e) {
        e.printStackTrace();
        fail("Test failed with: " + e.getMessage());
    }
}
```

### Pentru măsurarea coverage:
- Folosiți IDE-ul pentru coverage reports
- Ținta: minimum 80% coverage pentru cod de producție
- 100% coverage pentru metode critice

**Succes cu exercițiile! 🚀** 