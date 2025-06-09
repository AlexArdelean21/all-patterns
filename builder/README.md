# Builder Pattern

## 🎯 Intent
The Builder pattern constructs complex objects step by step. It allows you to produce different types and representations of an object using the same construction code, separating the construction of a complex object from its representation.

## 🏗️ Structure

### Key Components:
- **Product**: The complex object being built
- **Builder**: Abstract interface for creating parts of the Product
- **Concrete Builder**: Implements the Builder interface and constructs/assembles parts
- **Director**: Knows how to construct specific types using the Builder interface
- **Client**: Uses the Director and Builder to create objects

### Class Diagram:
```
Computer (Product)
├── Required: cpu, ram, storage
└── Optional: gpu, bluetooth, wifi, os, color, cooling, psu, motherboard

Computer.ComputerBuilder (Builder)
├── + ComputerBuilder(cpu, ram, storage)
├── + gpu(String): ComputerBuilder
├── + wifi(boolean): ComputerBuilder
├── + build(): Computer
└── + gamingPreset(): ComputerBuilder

ComputerDirector
├── + buildGamingComputer(): Computer
├── + buildOfficeComputer(): Computer
└── + buildWorkstation(): Computer
```

## 💡 When to Use

### ✅ Use Builder Pattern When:
- Object construction is complex with many optional parameters
- You want to create different representations of the same object
- Construction process must allow different steps/variations
- Need to ensure object is fully constructed before use
- Constructor has too many parameters (telescoping constructor problem)

### ❌ Avoid When:
- Object is simple with few attributes
- Construction process is straightforward
- All parameters are required and fixed

## 🔍 Real-World Examples

### From the Demo:
```java
// Basic builder usage
Computer basicComputer = new Computer.ComputerBuilder("Intel i5", 16, 512)
    .wifi(true)
    .bluetooth(true)
    .operatingSystem("Windows 11")
    .build();

// Using presets
Computer gamingPC = new Computer.ComputerBuilder("Intel i7-13700K", 32, 1000)
    .gamingPreset()
    .build();

// Director-managed construction
ComputerDirector director = new ComputerDirector();
Computer workstation = director.buildWorkstation();
```

### Common Use Cases:
- **SQL Query Builders**: Building complex database queries
- **Configuration Objects**: Creating configuration with many optional settings
- **HTTP Request Builders**: Building REST API requests with headers, parameters
- **Document Builders**: Creating documents with various formatting options
- **UI Form Builders**: Constructing forms with different field types

## 🎪 Demo Walkthrough

The `BuilderDemo.java` demonstrates:

1. **Basic Builder**: Step-by-step object construction with optional parameters
2. **Method Chaining**: Fluent interface for readable code
3. **Preset Configurations**: Pre-configured setups for common use cases
4. **Director Pattern**: Encapsulating construction logic for specific types
5. **Business Integration**: Computer store with inventory and custom builds

### Key Features:
- Required vs optional parameters handling
- Preset configurations (gaming, office, workstation)
- Price calculation based on components
- Fluent interface with method chaining
- Validation and error handling

## 🚀 Running the Demo

```bash
# Compile and run
javac BuilderDemo.java
java BuilderDemo
```

**Expected Output:**
- Different computer configurations
- Price calculations
- Store inventory management
- Custom computer building scenarios

## 🔄 Variations

### 1. Simple Builder
- Static nested builder class
- Required parameters in constructor
- Optional parameters via methods

### 2. Director-Managed Builder
- Director class knows construction recipes
- Encapsulates complex construction logic
- Reusable construction processes

### 3. Step Builder
- Forces specific order of parameter setting
- Type-safe construction process
- Prevents invalid object states

## 💭 Benefits & Drawbacks

### ✅ Benefits:
- **Readable Code**: Clear, fluent interface
- **Flexible Construction**: Different representations of same object
- **Parameter Validation**: Validate during construction
- **Immutable Objects**: Create immutable objects safely
- **Telescoping Constructor Solution**: Eliminates complex constructors

### ❌ Drawbacks:
- **Code Complexity**: More code than simple constructors
- **Memory Overhead**: Builder objects consume memory
- **Not Always Necessary**: Overkill for simple objects

## 🔧 Implementation Best Practices

### 1. **Required vs Optional Parameters**
```java
// Required parameters in constructor
public ComputerBuilder(String cpu, int ram, int storage) {
    this.cpu = cpu;
    this.ram = ram;
    this.storage = storage;
}

// Optional parameters via methods
public ComputerBuilder gpu(String gpu) {
    this.gpu = gpu;
    return this;
}
```

### 2. **Method Chaining**
```java
// Always return 'this' for chaining
public ComputerBuilder wifi(boolean wifi) {
    this.wifi = wifi;
    return this;  // Enable chaining
}
```

### 3. **Preset Configurations**
```java
public ComputerBuilder gamingPreset() {
    return this.gpu("RTX 4070")
              .liquidCooling(true)
              .powerSupply(750);
}
```

## 🎯 Key Takeaways

1. **Separation of Concerns**: Construction logic separate from representation
2. **Flexibility**: Same construction process, different representations
3. **Readability**: Fluent interface makes code self-documenting
4. **Validation**: Validate complex objects during construction
5. **Immutability**: Create immutable objects safely

## 🔗 Related Patterns

- **Factory Method**: Creates objects but doesn't specify construction steps
- **Abstract Factory**: Creates families of objects
- **Prototype**: Creates objects by cloning
- **Composite**: Often built using Builder pattern

---

*The Builder pattern is essential for creating complex objects with many optional parameters while maintaining clean, readable code.* 