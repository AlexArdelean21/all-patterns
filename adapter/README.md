# Adapter Pattern

## Definition
The Adapter Pattern is a structural design pattern that allows objects with incompatible interfaces to collaborate. It acts as a bridge between two incompatible interfaces by wrapping an existing class with a new interface.

## Intent
- Allow classes with incompatible interfaces to work together
- Convert the interface of a class into another interface clients expect
- Let classes work together that couldn't otherwise because of incompatible interfaces

## Structure
The pattern involves:
- **Target**: The interface that the client expects
- **Adaptee**: The existing class with an incompatible interface
- **Adapter**: The class that makes the Adaptee work with the Target interface
- **Client**: The class that uses the Target interface

## When to Use (Recognition in Requirements)

### 🔍 **Look for these keywords in requirements:**
- "integrate with legacy system"
- "use existing library with different interface"
- "make X work with Y"
- "wrap existing functionality"
- "convert between formats"
- "third-party integration"

### 📋 **Scenarios that indicate Adapter pattern:**
1. **Legacy Integration**: "We need to integrate our new system with the old database API"
2. **Third-party Libraries**: "Use this external payment processor that has a different interface than our current one"
3. **Interface Mismatch**: "Make this XML parser work with our JSON-based system"
4. **Format Conversion**: "Convert data from the old format to the new format"
5. **API Compatibility**: "Make our mobile app work with both v1 and v2 of the API"

## Real-world Examples
- **Media Players**: Adapting different audio/video formats (MP3, MP4, AVI) to work with a unified player interface
- **Database Drivers**: JDBC drivers that adapt different database systems to a common interface
- **Payment Gateways**: Adapting different payment processors (PayPal, Stripe, Square) to a unified payment interface
- **File Formats**: Adapting different document formats (PDF, DOC, TXT) to a common document reader interface

## Benefits
✅ **Single Responsibility**: Separates interface conversion from business logic  
✅ **Open/Closed Principle**: Add new adapters without modifying existing code  
✅ **Code Reuse**: Reuse existing classes even if they have incompatible interfaces  
✅ **Decoupling**: Client code is decoupled from the concrete adaptee classes  

## Drawbacks
❌ **Complexity**: Increases the overall complexity of the code  
❌ **Performance**: May introduce a slight performance overhead  
❌ **Maintenance**: Additional layer to maintain and test  

## How to Implement
1. **Identify** the incompatible interfaces
2. **Create** the adapter class that implements the target interface
3. **Compose** the adaptee inside the adapter
4. **Delegate** calls from the target interface to the adaptee's interface
5. **Handle** any necessary data transformation

## Code Example Usage
```java
// Create legacy printer
LegacyPrinter oldPrinter = new OldPrinter();

// Adapt it to work with modern interface
ModernPrinter adaptedPrinter = new PrinterAdapter(oldPrinter);

// Client can now use it through the modern interface
PrinterClient client = new PrinterClient(adaptedPrinter);
client.executeTask();
```

## Related Patterns
- **Bridge**: Adapter makes things work after they're designed; Bridge makes them work before
- **Decorator**: Decorator enhances interface without changing it; Adapter changes the interface
- **Facade**: Facade simplifies interface; Adapter makes incompatible interfaces work together

## Test Questions That Indicate Adapter Pattern
1. "How would you integrate a legacy system with a new application?"
2. "You need to use a third-party library, but its interface doesn't match your system. What do you do?"
3. "How would you make an old component work with a new framework?"
4. "Design a system that can work with multiple payment processors with different APIs."
5. "How would you handle data format conversion between systems?" 