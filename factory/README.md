# Factory Pattern

## 🎯 Intent
The Factory pattern provides an interface for creating objects without specifying their exact classes. It encapsulates object creation logic and promotes loose coupling between clients and concrete products.

## 🏗️ Structure

### Key Components:
- **Product**: Interface/abstract class defining the product
- **Concrete Products**: Specific implementations of the product
- **Factory**: Class responsible for creating products
- **Client**: Uses the factory to create objects

### Class Diagram:
```
Product <<interface>>
├── Car (implements Product)
├── Motorcycle (implements Product)
└── Truck (implements Product)

VehicleFactory
├── + createVehicle(type, model): Vehicle
├── + createVehicle(VehicleType, model): Vehicle
└── + createVehicleForPurpose(purpose, model): Vehicle
```

## 💡 When to Use

### ✅ Use Factory Pattern When:
- Object creation logic is complex or might change
- You need to centralize object creation
- Client shouldn't know about concrete classes
- You want to provide different ways to create objects
- Object creation depends on runtime conditions

### ❌ Avoid When:
- Object creation is simple and straightforward
- You only have one type of product
- Adding new products rarely happens

## 🔍 Real-World Examples

### From the Demo:
```java
// Simple factory usage
Vehicle car = VehicleFactory.createVehicle("car", "Toyota Camry");
Vehicle bike = VehicleFactory.createVehicle("motorcycle", "Honda CBR");

// Enum-based factory for type safety
Vehicle truck = VehicleFactory.createVehicle(VehicleType.TRUCK, "Ford F-150");

// Purpose-based factory
Vehicle familyCar = VehicleFactory.createVehicleForPurpose("family", "Honda Accord");
```

### Common Use Cases:
- **Database Connections**: Creating different database connectors
- **UI Components**: Creating platform-specific UI elements
- **File Parsers**: Creating parsers for different file formats
- **Payment Processors**: Creating different payment gateway handlers

## 🎪 Demo Walkthrough

The `FactoryDemo.java` demonstrates:

1. **Simple Factory Method**: Create vehicles by string type
2. **Enum-based Factory**: Type-safe vehicle creation
3. **Purpose-based Factory**: Create vehicles based on intended use
4. **Real-world Usage**: Vehicle dealer and test drive scenarios

### Key Features:
- Multiple factory methods for different creation strategies
- Integration with business logic (dealer, test drive)
- Error handling for invalid types
- Demonstration of factory benefits

## 🚀 Running the Demo

```bash
# Compile and run
javac FactoryDemo.java
java FactoryDemo
```

**Expected Output:**
- Vehicle creation demonstrations
- Dealer inventory management
- Test drive scenarios
- Different factory method usages

## 🔄 Variations

### 1. Simple Factory
- Static method that creates objects
- Not a true GoF pattern but widely used

### 2. Factory Method Pattern
- Each product type has its own creator class
- More flexible but more complex

### 3. Parameterized Factory
- Factory takes parameters to determine what to create
- Good for runtime decisions

## 💭 Benefits & Drawbacks

### ✅ Benefits:
- **Loose Coupling**: Clients don't depend on concrete classes
- **Centralized Creation**: Object creation logic in one place
- **Easy Extension**: Adding new products is straightforward
- **Consistency**: Ensures objects are created correctly

### ❌ Drawbacks:
- **Additional Complexity**: Extra layer of abstraction
- **More Classes**: Can increase the number of classes
- **Runtime Overhead**: Slight performance cost

## 🎯 Key Takeaways

1. **Encapsulation**: Hide object creation complexity from clients
2. **Flexibility**: Easy to modify creation logic without changing clients
3. **Type Safety**: Use enums or constants instead of strings when possible
4. **Error Handling**: Validate inputs and provide meaningful error messages

## 🔗 Related Patterns

- **Abstract Factory**: Creates families of related objects
- **Builder**: Constructs complex objects step by step
- **Prototype**: Creates objects by cloning existing instances
- **Singleton**: Ensures only one instance exists

---

*The Factory pattern is fundamental to object-oriented design and forms the basis for many other creational patterns.* 