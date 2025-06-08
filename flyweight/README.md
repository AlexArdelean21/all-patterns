# Flyweight Pattern

## Definition
The Flyweight pattern minimizes memory usage by sharing efficiently among multiple objects that contain duplicate data. It separates intrinsic state (shared) from extrinsic state (context-specific) to reduce memory footprint.

## Problem it Solves
- Large numbers of similar objects consume excessive memory
- Objects contain duplicate data that can be shared
- Need to support many fine-grained objects efficiently
- Memory optimization is critical for performance
- Object creation overhead is significant

## Key Components
1. **Flyweight Interface**: Declares methods that accept extrinsic state
2. **Concrete Flyweight**: Implements flyweight interface and stores intrinsic state
3. **Flyweight Factory**: Creates and manages flyweight instances
4. **Context**: Stores extrinsic state and references to flyweight
5. **Client**: Maintains references to flyweights and computes extrinsic state

## Intrinsic vs Extrinsic State
- **Intrinsic State**: Stored in flyweight, shared among contexts, context-independent
- **Extrinsic State**: Stored in context, unique per context, passed to flyweight methods

## Implementation Example
Our demo shows two main examples:
- **Text Editor**: Character flyweights with position/formatting as extrinsic state
- **Particle System**: Particle type flyweights with position/velocity as extrinsic state
- **Memory Comparison**: Demonstrates significant memory savings

## When to Use
✅ Application uses large numbers of objects  
✅ Storage costs are high due to object quantity  
✅ Object state can be divided into intrinsic/extrinsic  
✅ Extrinsic state can be computed or passed as parameters  
✅ Objects can be replaced by few shared flyweights  

## When NOT to Use
❌ Few objects exist  
❌ Objects don't share significant state  
❌ Extrinsic state cannot be separated easily  
❌ Application doesn't create many objects  

## Real-World Examples
- Text editors (character formatting)
- Game engines (particles, sprites, bullets)
- Web browsers (DOM element styling)
- Graphics systems (fonts, textures)
- CAD systems (geometric shapes)

## Advantages
✅ **Memory Reduction**: Dramatic decrease in memory usage  
✅ **Performance**: Reduced object creation overhead  
✅ **Sharing**: Efficient sharing of common data  
✅ **Scalability**: Supports large numbers of objects  

## Disadvantages
❌ **Complexity**: More complex design and implementation  
❌ **Runtime Overhead**: May increase runtime costs for extrinsic state  
❌ **Design Constraints**: Restricts how objects can be used  

## Related Patterns
- **Factory**: Flyweight Factory manages flyweight creation
- **Singleton**: Flyweight instances are often managed as singletons
- **Composite**: Flyweights can be part of composite structures

## Recognition in Code
Look for these indicators:
```java
// Flyweight interface accepting extrinsic state
interface Flyweight {
    void operation(String extrinsicState);
}

// Concrete flyweight storing intrinsic state
class ConcreteFlyweight implements Flyweight {
    private final String intrinsicState;
    
    public ConcreteFlyweight(String intrinsicState) {
        this.intrinsicState = intrinsicState;
    }
    
    public void operation(String extrinsicState) {
        // Use both intrinsic and extrinsic state
    }
}

// Flyweight factory
class FlyweightFactory {
    private static Map<String, Flyweight> flyweights = new HashMap<>();
    
    public static Flyweight getFlyweight(String key) {
        if (!flyweights.containsKey(key)) {
            flyweights.put(key, new ConcreteFlyweight(key));
        }
        return flyweights.get(key);
    }
}

// Context holding extrinsic state
class Context {
    private String extrinsicState;
    private Flyweight flyweight;
    
    public void operation() {
        flyweight.operation(extrinsicState);
    }
}
```

## Flyweight Factory Pattern
```java
class CharacterFactory {
    private static final Map<Character, CharacterFlyweight> flyweights = new HashMap<>();
    
    public static CharacterFlyweight getCharacter(char c) {
        CharacterFlyweight flyweight = flyweights.get(c);
        if (flyweight == null) {
            flyweight = new Character(c);
            flyweights.put(c, flyweight);
        }
        return flyweight;
    }
    
    public static int getFlyweightCount() {
        return flyweights.size();
    }
}
```

## State Separation Guidelines
### Intrinsic State (Flyweight)
- Independent of context
- Shareable among multiple contexts
- Immutable
- Examples: character value, particle type, sprite image

### Extrinsic State (Context)
- Dependent on specific context
- Unique to each context
- Passed as parameters
- Examples: position, color, size, font

## Memory Optimization Techniques
1. **Object Pooling**: Reuse flyweight instances
2. **Lazy Creation**: Create flyweights only when needed
3. **Weak References**: Allow garbage collection when appropriate
4. **Compression**: Compress intrinsic state data
5. **Batch Operations**: Process multiple contexts together

## Best Practices
1. **Immutable Flyweights**: Keep flyweights immutable
2. **Factory Management**: Use factory for flyweight creation
3. **State Separation**: Clearly separate intrinsic/extrinsic state
4. **Thread Safety**: Ensure flyweights are thread-safe
5. **Documentation**: Document state separation clearly

## Testing Strategy
- Test flyweight sharing (same instance returned)
- Verify memory usage improvements
- Test with large numbers of objects
- Validate state separation correctness
- Performance testing vs non-flyweight implementation

## Common Mistakes
1. Storing extrinsic state in flyweight
2. Making flyweights mutable
3. Not using factory for flyweight creation
4. Poor intrinsic/extrinsic state separation
5. Creating too many flyweight types
6. Not considering thread safety 