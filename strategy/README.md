# Strategy Pattern

## 🎯 Intent
The Strategy pattern defines a family of algorithms, encapsulates each one, and makes them interchangeable. It lets the algorithm vary independently from clients that use it, promoting composition over inheritance.

## 🏗️ Structure

### Key Components:
- **Strategy**: Interface defining the algorithm contract
- **Concrete Strategy**: Specific implementations of the algorithm
- **Context**: Uses a Strategy interface to call the algorithm
- **Client**: Configures the Context with a Concrete Strategy

### Class Diagram:
```
PaymentStrategy <<interface>>
└── + pay(double amount): boolean
└── + getPaymentMethod(): String

Concrete Strategies:
├── CreditCardPayment (implements PaymentStrategy)
├── PayPalPayment (implements PaymentStrategy)
├── BankTransferPayment (implements PaymentStrategy)
└── CryptoPayment (implements PaymentStrategy)

ShoppingCart (Context)
├── - paymentStrategy: PaymentStrategy
├── + setPaymentStrategy(PaymentStrategy): void
└── + checkout(): void
```

## 💡 When to Use

### ✅ Use Strategy Pattern When:
- You have multiple ways to perform a task
- You want to choose algorithm at runtime
- You want to eliminate conditional statements for algorithm selection
- Classes differ only in their behavior
- You need to isolate algorithm implementation details

### ❌ Avoid When:
- You only have one algorithm
- Algorithms rarely change
- The conditional logic is simple and stable

## 🔍 Real-World Examples

### From the Demo:
```java
// Create shopping cart (context)
ShoppingCart cart = new ShoppingCart();
cart.addItem("Laptop", 999.99);
cart.addItem("Mouse", 29.99);

// Choose payment strategy at runtime
PaymentStrategy creditCard = new CreditCardPayment("1234567890123456", "John Doe", "123");
cart.setPaymentStrategy(creditCard);
cart.checkout();

// Switch to different strategy
PaymentStrategy paypal = new PayPalPayment("john@email.com", "password123");
cart.setPaymentStrategy(paypal);
cart.checkout();
```

### Common Use Cases:
- **Payment Processing**: Credit card, PayPal, bank transfer, cryptocurrency
- **Sorting Algorithms**: QuickSort, MergeSort, BubbleSort
- **Compression**: ZIP, RAR, GZIP algorithms
- **File Format Conversion**: PDF, Word, Excel exporters
- **Tax Calculation**: Different tax strategies for different regions
- **Discount Calculation**: Percentage, fixed amount, buy-one-get-one

## 🎪 Demo Walkthrough

The `StrategyDemo.java` demonstrates:

1. **Payment Processing System**: Different payment methods as strategies
2. **Runtime Strategy Selection**: Switching payment methods dynamically
3. **Algorithm Encapsulation**: Each payment method handles its own logic
4. **Context Management**: Shopping cart manages items and payment processing
5. **Strategy Comparison**: Different behaviors for different payment types

### Key Features:
- Multiple payment strategies with different processing logic
- Dynamic strategy switching at runtime
- Strategy-specific validation and processing
- Context maintains state while strategies handle algorithms
- Realistic payment processing scenarios

## 🚀 Running the Demo

```bash
# Compile and run
javac StrategyDemo.java
java StrategyDemo
```

**Expected Output:**
- Shopping cart operations
- Different payment method demonstrations
- Strategy switching scenarios
- Payment processing results

## 🔄 Variations

### 1. Simple Strategy
- Basic interface with single method
- Minimal strategy implementations
- Direct strategy swapping

### 2. Parameterized Strategy
- Strategies accept parameters for customization
- More flexible algorithm configuration
- Context provides additional data to strategies

### 3. Strategy Factory
- Factory pattern combined with Strategy
- Centralized strategy creation
- Configuration-driven strategy selection

## 💭 Benefits & Drawbacks

### ✅ Benefits:
- **Open/Closed Principle**: Easy to add new algorithms without modifying existing code
- **Single Responsibility**: Each algorithm is isolated in its own class
- **Runtime Flexibility**: Can switch algorithms at runtime
- **Testability**: Each strategy can be tested independently
- **Eliminates Conditionals**: Replaces complex if-else chains

### ❌ Drawbacks:
- **Increased Classes**: More classes in the system
- **Client Awareness**: Client must know about different strategies
- **Communication Overhead**: Context-strategy communication can be complex

## 🔧 Implementation Best Practices

### 1. **Strategy Interface Design**
```java
interface PaymentStrategy {
    boolean pay(double amount);           // Core algorithm
    String getPaymentMethod();            // Strategy identification
    // void validate() - if needed       // Pre-processing
}
```

### 2. **Context Implementation**
```java
class ShoppingCart {
    private PaymentStrategy paymentStrategy;
    
    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.paymentStrategy = strategy;
    }
    
    public void checkout() {
        if (paymentStrategy != null) {
            paymentStrategy.pay(totalAmount);
        }
    }
}
```

### 3. **Strategy Selection**
```java
// Runtime selection based on conditions
public PaymentStrategy selectStrategy(double amount, String preference) {
    if (amount > 1000 && "secure".equals(preference)) {
        return new BankTransferPayment(...);
    } else if ("fast".equals(preference)) {
        return new CreditCardPayment(...);
    }
    return new PayPalPayment(...);
}
```

## 🎯 Key Takeaways

1. **Algorithm Interchangeability**: Strategies can be swapped without changing context
2. **Encapsulation**: Each algorithm is encapsulated in its own class
3. **Composition over Inheritance**: Use composition to vary behavior
4. **Runtime Flexibility**: Strategies can be chosen and changed at runtime
5. **Testability**: Each strategy can be unit tested in isolation

## 🔗 Related Patterns

- **State**: Similar structure but State changes behavior based on internal state
- **Command**: Encapsulates requests as objects
- **Template Method**: Defines algorithm structure, Strategy defines whole algorithms
- **Factory**: Often used together to create strategies

---

*The Strategy pattern is fundamental for creating flexible, maintainable systems where algorithms need to vary independently from their usage context.* 