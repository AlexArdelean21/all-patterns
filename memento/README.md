# Memento Pattern

## Definition
The Memento pattern captures and externalizes an object's internal state without violating encapsulation, so that the object can be restored to this state later. It's a behavioral pattern that provides undo/redo functionality and state restoration.

## Problem it Solves
- Need to save and restore object state
- Want undo/redo functionality
- Need checkpoints for rollback capability
- Object's internal state should remain encapsulated
- State saving shouldn't violate object's interface

## Key Components
1. **Originator**: Object whose state needs to be saved
2. **Memento**: Stores internal state of Originator
3. **Caretaker**: Manages mementos but doesn't access their content
4. **Memento Interface**: Narrow interface for Caretaker (often empty)

## Implementation Example
Our demo shows two main examples:
- **Text Editor**: Undo/redo functionality with EditorHistory caretaker
- **Game Save System**: Game state preservation with SaveGameManager
- **Multiple Snapshots**: Managing multiple save states with timestamps

## When to Use
✅ Need to save/restore object state  
✅ Want undo/redo functionality  
✅ Need checkpoint/rollback capability  
✅ Direct state access would violate encapsulation  
✅ State saving logic should be separate from business logic  

## When NOT to Use
❌ Object state is simple and public  
❌ State doesn't change frequently  
❌ Memory usage is a major concern  
❌ Undo functionality is not needed  

## Real-World Examples
- Text editors (undo/redo)
- Graphics editors (layer history)
- Database transactions (rollback)
- Game save states
- Configuration snapshots

## Advantages
✅ **Encapsulation**: Preserves object's encapsulation boundaries  
✅ **Simplicity**: Simplifies Originator by moving state management out  
✅ **Flexibility**: Can save state at any point  
✅ **Recovery**: Provides recovery from errors  

## Disadvantages
❌ **Memory**: Can be expensive if mementos are large  
❌ **Lifecycle**: Caretaker must manage memento lifecycle  
❌ **Hidden Costs**: Clients might create mementos too frequently  

## Related Patterns
- **Command**: Often combined for undo operations
- **Iterator**: Caretakers can use iterators to traverse mementos
- **Prototype**: Mementos can use cloning for state copying

## Recognition in Code
Look for these indicators:
```java
// Memento class (usually nested in Originator)
class Memento {
    private final String state;
    private final long timestamp;
    
    public Memento(String state) {
        this.state = state;
        this.timestamp = System.currentTimeMillis();
    }
    
    // Package-private or protected access
    String getState() { return state; }
}

// Originator creates and restores from mementos
class Originator {
    private String state;
    
    public Memento createMemento() {
        return new Memento(state);
    }
    
    public void restoreFromMemento(Memento memento) {
        this.state = memento.getState();
    }
}

// Caretaker manages mementos
class Caretaker {
    private List<Memento> history = new ArrayList<>();
    
    public void saveState(Originator originator) {
        history.add(originator.createMemento());
    }
    
    public void undo(Originator originator) {
        if (!history.isEmpty()) {
            Memento memento = history.remove(history.size() - 1);
            originator.restoreFromMemento(memento);
        }
    }
}
```

## Memento Access Levels
1. **Wide Interface**: For Originator (full access to memento state)
2. **Narrow Interface**: For Caretaker (limited or no access to state)
3. **Package-Private**: Memento in same package as Originator

## Undo/Redo Implementation
```java
class UndoRedoManager {
    private List<Memento> history = new ArrayList<>();
    private int currentIndex = -1;
    
    public void save(Memento memento) {
        // Remove any mementos after current index
        while (history.size() > currentIndex + 1) {
            history.remove(history.size() - 1);
        }
        history.add(memento);
        currentIndex++;
    }
    
    public Memento undo() {
        if (currentIndex > 0) {
            return history.get(--currentIndex);
        }
        return null;
    }
    
    public Memento redo() {
        if (currentIndex < history.size() - 1) {
            return history.get(++currentIndex);
        }
        return null;
    }
}
```

## Memento Serialization
```java
// For persistent storage
class SerializableMemento implements Serializable {
    private final byte[] state;
    
    public SerializableMemento(Object stateObject) {
        // Serialize object to byte array
        this.state = serialize(stateObject);
    }
    
    public Object getState() {
        return deserialize(state);
    }
}
```

## Best Practices
1. **Keep mementos immutable**
2. **Limit memento lifetime**
3. **Consider memory usage for large states**
4. **Use nested classes for strong encapsulation**
5. **Implement efficient state copying**

## Memory Management Strategies
- **Limited History**: Keep only N recent mementos
- **Incremental Snapshots**: Store only state changes
- **Compression**: Compress memento data
- **Lazy Loading**: Load memento data on demand
- **Weak References**: Allow garbage collection when appropriate

## Testing Strategy
- Test state restoration accuracy
- Verify encapsulation is maintained
- Test memory usage with many mementos
- Test undo/redo sequences
- Test edge cases (empty history, multiple undos)

## Common Mistakes
1. Exposing memento internals to caretaker
2. Making mementos mutable
3. Not managing memento memory usage
4. Creating too many mementos
5. Not handling memento lifecycle properly
6. Violating encapsulation through memento access 