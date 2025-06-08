# Template Method Pattern

## Definition
The Template Method pattern defines the skeleton of an algorithm in a base class, letting subclasses override specific steps without changing the algorithm's structure. It's a behavioral pattern that promotes code reuse through inheritance.

## Problem it Solves
- Common algorithm steps need to be shared across classes
- Algorithm structure should remain fixed while allowing step customization
- Need to prevent subclasses from changing the algorithm flow
- Want to eliminate code duplication in similar algorithms
- Control extension points in class hierarchies

## Key Components
1. **Abstract Class**: Defines template method and abstract/concrete operations
2. **Template Method**: Defines algorithm skeleton (usually final)
3. **Abstract Operations**: Steps that subclasses must implement
4. **Hook Operations**: Optional steps that subclasses can override
5. **Concrete Class**: Implements abstract operations for specific algorithm variant

## Implementation Example
Our demo shows a data processing pipeline:
- **Abstract Class**: DataProcessor with processData() template method
- **Steps**: readData(), validateData(), processDataImplementation(), saveResults()
- **Concrete Classes**: CSVDataProcessor, LogDataProcessor, SurveyDataProcessor
- **Hooks**: needsTransformation(), needsCleanup()

## When to Use
✅ Multiple classes have similar algorithms with minor differences  
✅ Algorithm structure should be preserved  
✅ Want to control which parts subclasses can extend  
✅ Need to eliminate code duplication  
✅ Algorithm has well-defined steps  

## When NOT to Use
❌ Algorithm varies significantly between implementations  
❌ No common steps between algorithms  
❌ Composition would be more appropriate  
❌ Algorithm structure changes frequently  

## Real-World Examples
- Data processing pipelines (ETL processes)
- Web frameworks (request handling lifecycle)
- Game engines (game loop, AI behavior)
- Test frameworks (setup, execute, teardown)
- Build systems (compile, test, package, deploy)

## Advantages
✅ **Code Reuse**: Common steps implemented once  
✅ **Control**: Algorithm structure is protected  
✅ **Flexibility**: Subclasses customize specific steps  
✅ **Maintenance**: Changes to common steps affect all subclasses  

## Disadvantages
❌ **Inheritance**: Tight coupling through inheritance  
❌ **Liskov Substitution**: Subclasses must maintain contract  
❌ **Debugging**: Call flow spans multiple classes  
❌ **Flexibility**: Limited to inheritance-based extension  

## Related Patterns
- **Strategy**: Template Method uses inheritance, Strategy uses composition
- **Factory Method**: Often used as steps in Template Method
- **Observer**: Template methods can notify observers at specific steps

## Recognition in Code
Look for these indicators:
```java
// Abstract class with template method
abstract class AbstractClass {
    // Template method (usually final)
    public final void templateMethod() {
        step1();
        step2();
        if (hook()) {
            step3();
        }
        step4();
    }
    
    // Abstract steps
    protected abstract void step2();
    protected abstract void step4();
    
    // Concrete steps
    protected void step1() { /* common implementation */ }
    
    // Hook methods
    protected boolean hook() { return true; }
}
```

## Types of Operations
1. **Concrete Operations**: Implemented in abstract class
2. **Abstract Operations**: Must be implemented by subclasses
3. **Hook Operations**: Optional override points for subclasses
4. **Factory Methods**: Create objects used by algorithm

## Best Practices
1. **Make template method final**
2. **Minimize abstract operations**
3. **Provide meaningful hook methods**
4. **Document algorithm steps clearly**
5. **Use descriptive method names**

## Hook Method Guidelines
- Provide default implementation (often empty)
- Use boolean hooks for conditional steps
- Document when hooks are called
- Keep hooks focused and single-purpose

## Testing Strategy
- Test template method with different concrete classes
- Verify algorithm flow is maintained
- Test each abstract operation independently
- Test hook method variations
- Integration tests for complete algorithms

## Common Mistakes
1. Making template method non-final
2. Too many abstract operations
3. Complex hook methods
4. Violating Liskov Substitution Principle
5. Not documenting algorithm flow clearly 