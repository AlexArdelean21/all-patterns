# Prototype Pattern

## Definition
The Prototype pattern creates objects by cloning existing instances rather than creating new ones from scratch. It's a creational pattern that specifies the kind of objects to create using a prototypical instance and creates new objects by copying this prototype.

## Problem it Solves
- Object creation is expensive or complex
- Need to create objects similar to existing ones
- Want to avoid subclassing just for object creation
- Runtime configuration of object creation
- Need to create objects without knowing their exact classes

## Key Components
1. **Prototype Interface**: Declares cloning interface (usually clone() method)
2. **Concrete Prototype**: Implements cloning operation
3. **Client**: Creates new objects by asking prototype to clone itself
4. **Prototype Manager**: Optional registry of available prototypes

## Implementation Example
Our demo shows two main examples:
- **Document Cloning**: Document templates with complex initialization
- **Game Characters**: Character prototypes with different classes and abilities
- **Prototype Manager**: CharacterPrototypeManager for managing character templates

## When to Use
✅ Object initialization is expensive  
✅ Need many similar objects with slight variations  
✅ Want to avoid creating factory class hierarchy  
✅ Runtime determination of object types  
✅ Object configuration is complex  

## When NOT to Use
❌ Simple object creation is sufficient  
❌ Deep copying is complex or problematic  
❌ Objects don't need cloning capability  
❌ Few object types exist  

## Real-World Examples
- GUI component templates
- Game character/item templates
- Document templates in word processors
- Database record templates
- Configuration object templates

## Advantages
✅ **Performance**: Avoid expensive initialization by cloning  
✅ **Flexibility**: Add/remove prototypes at runtime  
✅ **Reduced Subclassing**: Alternative to Factory Method  
✅ **Dynamic Configuration**: Configure applications with prototypes  

## Disadvantages
❌ **Cloning Complexity**: Deep cloning can be difficult  
❌ **Circular References**: Problems with complex object graphs  
❌ **Clone Method**: Each class needs proper clone implementation  

## Related Patterns
- **Abstract Factory**: Can use prototypes instead of factory methods
- **Composite**: Often combined with Prototype for copying complex structures
- **Decorator**: Prototypes can be decorated before cloning

## Recognition in Code
Look for these indicators:
```java
// Prototype interface
interface Prototype {
    Prototype clone();
}

// Concrete prototype with clone method
class ConcretePrototype implements Prototype {
    private String data;
    
    // Copy constructor or clone method
    public ConcretePrototype(ConcretePrototype other) {
        this.data = other.data;
    }
    
    @Override
    public Prototype clone() {
        return new ConcretePrototype(this);
    }
}

// Client uses cloning
Prototype prototype = getPrototype();
Prototype copy = prototype.clone();
```

## Clone Implementation Strategies
1. **Shallow Clone**: Copy object references (default Object.clone())
2. **Deep Clone**: Recursively copy all referenced objects
3. **Copy Constructor**: Alternative to clone() method
4. **Serialization**: Use serialization for complex deep cloning

## Deep vs Shallow Cloning
```java
// Shallow clone - shares references
class ShallowClone implements Cloneable {
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); // Default shallow copy
    }
}

// Deep clone - copies all referenced objects
class DeepClone implements Cloneable {
    private List<String> data;
    
    public Object clone() {
        DeepClone copy = new DeepClone();
        copy.data = new ArrayList<>(this.data); // Deep copy
        return copy;
    }
}
```

## Prototype Registry Pattern
```java
class PrototypeRegistry {
    private Map<String, Prototype> prototypes = new HashMap<>();
    
    public void register(String key, Prototype prototype) {
        prototypes.put(key, prototype);
    }
    
    public Prototype create(String key) {
        Prototype prototype = prototypes.get(key);
        return prototype != null ? prototype.clone() : null;
    }
}
```

## Best Practices
1. **Implement proper deep cloning when needed**
2. **Use copy constructors as clone() alternative**
3. **Handle circular references carefully**
4. **Consider using prototype registry**
5. **Document cloning behavior clearly**

## Testing Strategy
- Test clone independence (modifications don't affect original)
- Verify deep vs shallow cloning behavior
- Test with complex object graphs
- Performance testing vs direct construction
- Test prototype registry operations

## Common Mistakes
1. Implementing shallow clone when deep clone is needed
2. Not handling circular references in object graphs
3. Forgetting to clone mutable referenced objects
4. Not implementing Cloneable interface properly
5. Making clone() method too complex
6. Not considering thread safety in cloning 