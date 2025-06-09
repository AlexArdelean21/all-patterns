# Strategy Pattern - Visual Diagrams

## 🏗️ UML Class Diagram

```mermaid
classDiagram
    class PaymentStrategy {
        <<interface>>
        +pay(double amount) boolean
        +getPaymentMethod() String
    }
    
    class CreditCardPayment {
        -String cardNumber
        -String cardholderName
        -String cvv
        
        +CreditCardPayment(String cardNumber, String cardholderName, String cvv)
        +pay(double amount) boolean
        +getPaymentMethod() String
    }
    
    class PayPalPayment {
        -String email
        -String password
        
        +PayPalPayment(String email, String password)
        +pay(double amount) boolean
        +getPaymentMethod() String
    }
    
    class BankTransferPayment {
        -String accountNumber
        -String routingNumber
        -String bankName
        
        +BankTransferPayment(String accountNumber, String routingNumber, String bankName)
        +pay(double amount) boolean
        +getPaymentMethod() String
    }
    
    class CryptoPayment {
        -String walletAddress
        -String cryptoType
        
        +CryptoPayment(String walletAddress, String cryptoType)
        +pay(double amount) boolean
        +getPaymentMethod() String
    }
    
    class ShoppingCart {
        -List~String~ items
        -double totalAmount
        -PaymentStrategy paymentStrategy
        
        +addItem(String item, double price) void
        +removeItem(String item, double price) void
        +setPaymentStrategy(PaymentStrategy strategy) void
        +checkout() void
        +showCart() void
    }
    
    class PaymentProcessor {
        +processPayment(double amount, PaymentStrategy strategy)$ void
        +selectBestStrategy(double amount)$ PaymentStrategy
    }
    
    PaymentStrategy <|.. CreditCardPayment
    PaymentStrategy <|.. PayPalPayment
    PaymentStrategy <|.. BankTransferPayment
    PaymentStrategy <|.. CryptoPayment
    ShoppingCart --> PaymentStrategy : uses
    PaymentProcessor ..> PaymentStrategy : uses
```

## 🔄 Sequence Diagram - Payment Processing

```mermaid
sequenceDiagram
    participant Client
    participant ShoppingCart
    participant PaymentStrategy
    participant CreditCardPayment
    participant PayPalPayment
    
    Note over Client, PayPalPayment: Shopping and Payment Selection
    Client->>ShoppingCart: addItem("Laptop", 999.99)
    ShoppingCart-->>Client: item added
    Client->>ShoppingCart: addItem("Mouse", 29.99)
    ShoppingCart-->>Client: item added
    
    Note over Client, PayPalPayment: Credit Card Payment
    Client->>CreditCardPayment: new CreditCardPayment(...)
    CreditCardPayment-->>Client: strategy instance
    Client->>ShoppingCart: setPaymentStrategy(creditCard)
    ShoppingCart-->>Client: strategy set
    Client->>ShoppingCart: checkout()
    ShoppingCart->>PaymentStrategy: pay(1029.98)
    PaymentStrategy->>CreditCardPayment: pay(1029.98)
    CreditCardPayment->>CreditCardPayment: validate card details
    CreditCardPayment->>CreditCardPayment: process payment
    CreditCardPayment-->>PaymentStrategy: true (success)
    PaymentStrategy-->>ShoppingCart: payment result
    ShoppingCart-->>Client: checkout complete
    
    Note over Client, PayPalPayment: Switching to PayPal
    Client->>PayPalPayment: new PayPalPayment(...)
    PayPalPayment-->>Client: strategy instance
    Client->>ShoppingCart: setPaymentStrategy(paypal)
    ShoppingCart-->>Client: strategy set
    Client->>ShoppingCart: checkout()
    ShoppingCart->>PaymentStrategy: pay(1029.98)
    PaymentStrategy->>PayPalPayment: pay(1029.98)
    PayPalPayment->>PayPalPayment: authenticate user
    PayPalPayment->>PayPalPayment: process payment
    PayPalPayment-->>PaymentStrategy: true (success)
    PaymentStrategy-->>ShoppingCart: payment result
    ShoppingCart-->>Client: checkout complete
```

## 💳 Strategy Selection Flow

```mermaid
flowchart TD
    A[Client Needs Payment] --> B[ShoppingCart Context]
    B --> C{Choose Payment Strategy}
    
    C -->|Instant Processing| D[Credit Card Strategy]
    C -->|Online Account| E[PayPal Strategy]
    C -->|Large Amount/Secure| F[Bank Transfer Strategy]
    C -->|Anonymous/Modern| G[Crypto Strategy]
    
    D --> D1[Validate Card Details]
    D1 --> D2[Process Credit Card]
    D2 --> H[Payment Result]
    
    E --> E1[Authenticate PayPal]
    E1 --> E2[Process PayPal Payment]
    E2 --> H
    
    F --> F1[Validate Bank Details]
    F1 --> F2[Initiate Bank Transfer]
    F2 --> H
    
    G --> G1[Validate Wallet Address]
    G1 --> G2[Process Crypto Transaction]
    G2 --> H
    
    H --> I{Payment Successful?}
    I -->|Yes| J[Complete Purchase]
    I -->|No| K[Retry with Different Strategy]
    K --> C
    
    style D fill:#e1f5fe
    style E fill:#f3e5f5
    style F fill:#fff3e0
    style G fill:#e8f5e8
```

## 🎯 Strategy Pattern Structure

```mermaid
mindmap
  root((Strategy Pattern))
    Strategy Interface
      Common Contract
        pay(amount)
        getPaymentMethod()
      Algorithm Definition
        Defines what all strategies must implement
        Ensures interchangeability
    
    Concrete Strategies
      CreditCardPayment
        Card validation
        CVV checking
        Instant processing
      PayPalPayment
        Email authentication
        Password validation
        Online processing
      BankTransferPayment
        Account verification
        Routing validation
        Secure processing
      CryptoPayment
        Wallet validation
        Blockchain processing
        Conversion rates
    
    Context
      ShoppingCart
        Maintains strategy reference
        Delegates algorithm execution
        Manages business logic
        Independent of specific strategies
    
    Client Usage
      Strategy Selection
        Runtime choice
        Dynamic switching
      Business Logic
        Shopping operations
        Payment processing
```

## 🔄 Strategy Switching Visualization

```mermaid
graph LR
    subgraph "Runtime Strategy Changes"
        A[Initial: Credit Card] --> B[Switch to: PayPal]
        B --> C[Switch to: Bank Transfer]
        C --> D[Switch to: Crypto]
        D --> A
    end
    
    subgraph "Context Behavior"
        E[Same checkout() method]
        F[Same business logic]
        G[Different payment processing]
    end
    
    subgraph "Benefits"
        H[No code changes<br/>in context]
        I[Easy testing<br/>of strategies]
        J[Runtime flexibility<br/>🔄]
    end
    
    A -.-> E
    B -.-> F
    C -.-> G
    
    E --> H
    F --> I
    G --> J
    
    style A fill:#ffeb3b
    style B fill:#4caf50
    style C fill:#2196f3
    style D fill:#ff9800
```

## 📊 Strategy Comparison Matrix

```mermaid
graph TB
    subgraph "Payment Strategies Comparison"
        A[Credit Card<br/>💳]
        B[PayPal<br/>🅿️]
        C[Bank Transfer<br/>🏦]
        D[Cryptocurrency<br/>₿]
    end
    
    subgraph "Characteristics"
        E[Speed: Fast ⚡]
        F[Security: High 🔒]
        G[Fees: Low 💰]
        H[Global: Yes 🌍]
    end
    
    subgraph "Trade-offs"
        I[Convenience vs Security]
        J[Speed vs Cost]
        K[Availability vs Privacy]
    end
    
    A --> E
    A --> F
    B --> E
    B --> H
    C --> F
    C --> G
    D --> H
    D --> K
    
    E -.-> I
    F -.-> I
    G -.-> J
    E -.-> J
    H -.-> K
```

## 🔧 Implementation Patterns

```mermaid
classDiagram
    class StrategyFactory {
        +createStrategy(String type) PaymentStrategy
        +getAvailableStrategies() List~String~
        +getBestStrategy(double amount, String criteria) PaymentStrategy
    }
    
    class StrategyRegistry {
        -Map~String,PaymentStrategy~ strategies
        +register(String name, PaymentStrategy strategy) void
        +get(String name) PaymentStrategy
        +remove(String name) void
    }
    
    class StrategyValidator {
        +validate(PaymentStrategy strategy, double amount) boolean
        +getValidationRules(String strategyType) List~Rule~
    }
    
    class PaymentContext {
        -PaymentStrategy strategy
        -StrategyValidator validator
        +setStrategy(PaymentStrategy strategy) void
        +processPayment(double amount) PaymentResult
    }
    
    StrategyFactory ..> PaymentStrategy : creates
    StrategyRegistry --> PaymentStrategy : manages
    StrategyValidator --> PaymentStrategy : validates
    PaymentContext --> PaymentStrategy : uses
    PaymentContext --> StrategyValidator : uses
```

## ⚡ Performance & Behavior Analysis

```mermaid
graph TD
    subgraph "Strategy Performance"
        A[Credit Card: ~100ms]
        B[PayPal: ~200ms]
        C[Bank Transfer: ~1000ms]
        D[Crypto: ~500ms]
    end
    
    subgraph "Error Handling"
        E[Card Declined<br/>Retry Logic]
        F[Auth Failed<br/>Re-authentication]
        G[Insufficient Funds<br/>Alternative Strategy]
        H[Network Error<br/>Retry Mechanism]
    end
    
    subgraph "Strategy Selection Criteria"
        I[Amount Threshold<br/>💰]
        J[User Preference<br/>👤]
        K[Security Level<br/>🔒]
        L[Processing Speed<br/>⚡]
    end
    
    A --> E
    B --> F
    C --> G
    D --> H
    
    I --> A
    J --> B
    K --> C
    L --> A
    
    style A fill:#4caf50
    style B fill:#ff9800
    style C fill:#f44336
    style D fill:#9c27b0
```

## 🎮 Strategy vs State Pattern

```mermaid
graph LR
    subgraph "Strategy Pattern"
        A[Client chooses algorithm]
        B[Algorithm varies independently]
        C[Context delegates to strategy]
    end
    
    subgraph "State Pattern"
        D[Object changes behavior]
        E[Behavior depends on state]
        F[State transitions automatically]
    end
    
    subgraph "Key Differences"
        G[Strategy: Composition]
        H[State: State management]
        I[Strategy: Client-driven]
        J[State: Object-driven]
    end
    
    A --> G
    B --> I
    D --> H
    E --> J
    
    style A fill:#4caf50
    style D fill:#2196f3
```

## 💡 Key Design Insights

### 1. **Algorithm Encapsulation**
```
Each strategy encapsulates:
- Algorithm implementation
- Validation logic
- Error handling
- Performance characteristics
```

### 2. **Context Independence**
```
Context (ShoppingCart) remains unchanged when:
- Adding new payment methods
- Modifying existing algorithms
- Changing strategy selection logic
```

### 3. **Runtime Flexibility**
```
Strategies can be:
- Selected at runtime
- Switched dynamically
- Configured based on conditions
- Tested independently
```

---

*Visual diagrams demonstrate how the Strategy pattern enables flexible algorithm selection while maintaining a consistent interface and promoting code reusability.* 