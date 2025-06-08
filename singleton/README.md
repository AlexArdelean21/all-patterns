# Singleton Pattern

## Definition
The Singleton pattern ensures that a class has only one instance and provides a global point of access to that instance. It's a creational pattern that restricts the instantiation of a class to a single object.

## Problem it Solves
- Need exactly one instance of a class throughout the application
- Global access point required for shared resources
- Control over instantiation process
- Lazy initialization for expensive objects
- Thread-safe access to shared resources

## Key Components
1. **Private Constructor**: Prevents external instantiation
2. **Static Instance Variable**: Holds the single instance
3. **Static Access Method**: Provides global access point (usually getInstance())
4. **Thread Safety**: Ensures proper behavior in multi-threaded environments

## Implementation Example
Our demo shows multiple singleton implementations:
- **DatabaseConnection**: Thread-safe lazy initialization with double-checked locking
- **Logger**: Enum-based singleton (recommended approach)
- **AppConfig**: Eager initialization singleton
- **SingletonRegistry**: Registry pattern for managing multiple singleton types

## When to Use
✅ Exactly one instance needed throughout application  
✅ Global access point required  
✅ Shared resource management (database, file system)  
✅ Configuration management  
✅ Logging systems  
✅ Thread pools, cache systems  

## When NOT to Use
❌ Multiple instances might be needed in future  
❌ Testing becomes difficult  
❌ Creates hidden dependencies  
❌ Violates Single Responsibility Principle  
❌ Simple utility classes (use static methods instead)  

## Real-World Examples
- Database connection pools
- Application configuration managers
- Logging systems
- Print spoolers
- Device drivers
- Cache systems

## Implementation Strategies

### 1. Eager Initialization
```java
public class EagerSingleton {
    private static final EagerSingleton INSTANCE = new EagerSingleton();
    
    private EagerSingleton() {}
    
    public static EagerSingleton getInstance() {
        return INSTANCE;
    }
}
```

### 2. Lazy Initialization (Not Thread-Safe)
```java
public class LazySingleton {
    private static LazySingleton instance;
    
    private LazySingleton() {}
    
    public static LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}
```

### 3. Thread-Safe with Synchronized Method
```java
public class ThreadSafeSingleton {
    private static ThreadSafeSingleton instance;
    
    private ThreadSafeSingleton() {}
    
    public static synchronized ThreadSafeSingleton getInstance() {
        if (instance == null) {
            instance = new ThreadSafeSingleton();
        }
        return instance;
    }
}
```

### 4. Double-Checked Locking
```java
public class DoubleCheckedSingleton {
    private static volatile DoubleCheckedSingleton instance;
    
    private DoubleCheckedSingleton() {}
    
    public static DoubleCheckedSingleton getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckedSingleton.class) {
                if (instance == null) {
                    instance = new DoubleCheckedSingleton();
                }
            }
        }
        return instance;
    }
}
```

### 5. Enum Singleton (Recommended)
```java
public enum EnumSingleton {
    INSTANCE;
    
    public void doSomething() {
        // Implementation
    }
}
```

## Advantages
✅ **Controlled Access**: Ensures only one instance exists  
✅ **Global Access**: Available throughout the application  
✅ **Lazy Loading**: Can defer initialization until needed  
✅ **Memory Efficiency**: Only one instance in memory  

## Disadvantages
❌ **Global State**: Can create hidden dependencies  
❌ **Testing Difficulty**: Hard to mock or substitute  
❌ **Thread Safety**: Requires careful implementation  
❌ **Tight Coupling**: Classes become dependent on singleton  
❌ **Single Responsibility**: Often violates SRP  

## Thread Safety Considerations
1. **Eager Initialization**: Thread-safe by default
2. **Synchronized Methods**: Thread-safe but performance overhead
3. **Double-Checked Locking**: Optimal performance with thread safety
4. **Enum Singleton**: JVM guarantees thread safety

## Related Patterns
- **Factory Method**: Singletons often used in factory implementations
- **Abstract Factory**: Factory instances are often singletons
- **Builder**: Builder instances can be singletons

## Recognition in Code
Look for these indicators:
```java
// Private constructor
private ClassName() { }

// Static instance variable
private static ClassName instance;

// Static access method
public static ClassName getInstance() {
    // Lazy initialization logic
    return instance;
}

// Enum singleton
public enum SingletonEnum {
    INSTANCE;
}
```

## Best Practices
1. **Use Enum Singleton when possible** (simplest and safest)
2. **Make constructor private** to prevent external instantiation
3. **Handle thread safety** in multi-threaded environments
4. **Consider using dependency injection** instead of singleton
5. **Implement Serializable carefully** to maintain singleton property

## Testing Challenges and Solutions
- **Problem**: Hard to mock singletons in unit tests
- **Solution**: Use dependency injection or interface-based design
- **Problem**: Shared state between tests
- **Solution**: Provide reset mechanism for test environments

## Common Mistakes
1. Not handling thread safety properly
2. Using lazy initialization without synchronization
3. Not making constructor private
4. Violating singleton property during serialization
5. Creating memory leaks by holding references
6. Overusing singleton pattern

## Alternatives to Consider
- **Dependency Injection**: Better for testing and flexibility
- **Static Methods**: For utility functions
- **Monostate Pattern**: Multiple instances, shared state
- **Factory Pattern**: For controlled object creation 