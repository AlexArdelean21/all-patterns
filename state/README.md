# State Pattern

## Definition
The State pattern allows an object to alter its behavior when its internal state changes. The object will appear to change its class. It's a behavioral pattern that encapsulates state-specific behavior into separate classes.

## Problem it Solves
- Object behavior changes dramatically based on its state
- Large conditional statements based on object state
- State-specific behavior scattered throughout code
- Need to add new states without modifying existing code
- State transitions need to be managed cleanly

## Key Components
1. **Context**: Maintains reference to current state and delegates operations
2. **State Interface**: Defines interface for encapsulating behavior associated with a state
3. **Concrete States**: Implement behavior associated with a specific state
4. **State Transitions**: Logic for changing from one state to another

## Implementation Example
Our demo shows a media player with different states:
- **Context**: MediaPlayer class
- **States**: StoppedState, PlayingState, PausedState, BufferingState
- **Behavior**: Play, pause, stop, next, previous operations

## When to Use
✅ Object behavior depends on its state  
✅ Large conditional statements based on state  
✅ State-specific behavior needs isolation  
✅ New states will be added frequently  
✅ State transitions are complex  

## When NOT to Use
❌ Simple state changes (use boolean flags)  
❌ Few states that rarely change  
❌ State behavior is minimal  
❌ No clear state transitions  

## Real-World Examples
- Media players (playing, paused, stopped)
- Game characters (idle, walking, running, jumping)
- Network connections (connected, disconnected, connecting)
- Document workflow (draft, review, approved, published)
- Order processing (pending, shipped, delivered, cancelled)

## Advantages
✅ **Encapsulation**: State-specific behavior in separate classes  
✅ **Extensibility**: Easy to add new states  
✅ **Maintainability**: Eliminates large conditional statements  
✅ **Single Responsibility**: Each state handles its own behavior  

## Disadvantages
❌ **Complexity**: More classes for simple state machines  
❌ **Overhead**: May be overkill for simple scenarios  
❌ **State Management**: Need to manage state transitions carefully  

## Related Patterns
- **Strategy**: States can be viewed as strategies for different behaviors
- **Singleton**: State objects often implemented as Singletons
- **Flyweight**: States can be flyweights if they don't maintain instance data

## Recognition in Code
Look for these indicators:
```java
// State interface
interface State {
    void handle(Context context);
}

// Concrete states
class ConcreteStateA implements State {
    public void handle(Context context) {
        // State-specific behavior
        context.setState(new ConcreteStateB());
    }
}

// Context with state delegation
class Context {
    private State state;
    public void request() {
        state.handle(this);
    }
}
```

## State Transition Patterns
1. **States control transitions**: States decide when to change
2. **Context controls transitions**: Context manages state changes
3. **External control**: Outside entity manages transitions
4. **Table-driven**: Transition table defines valid changes

## Best Practices
1. **Keep states stateless when possible**
2. **Use state objects as singletons**
3. **Clearly define valid state transitions**
4. **Handle invalid operations gracefully**
5. **Document state diagram**

## Testing Strategy
- Test each state independently
- Verify all valid state transitions
- Test invalid state transitions
- Mock states for context testing
- Integration tests for complete workflows

## Common Mistakes
1. Creating stateful state objects unnecessarily
2. Not handling invalid operations
3. Complex state transition logic
4. Forgetting to update context state
5. Using when simple flags would suffice 