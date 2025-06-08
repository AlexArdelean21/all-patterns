# Decorator Pattern

## Definition
The Decorator Pattern is a structural design pattern that allows behavior to be added to objects dynamically without altering their basic structure. It provides a flexible alternative to subclassing for extending functionality.

## Intent
- Add new functionality to objects dynamically and transparently
- Provide a flexible alternative to inheritance for extending behavior
- Allow multiple decorations to be applied to a single object
- Keep the core object unchanged while adding new capabilities

## Structure
The pattern involves:
- **Component**: Interface defining operations that can be dynamically added
- **ConcreteComponent**: Basic implementation of the component
- **Decorator**: Base decorator class that maintains a reference to a component
- **ConcreteDecorator**: Specific decorators that add new behavior

## When to Use (Recognition in Requirements)

### 🔍 **Look for these keywords in requirements:**
- "add features dynamically"
- "optional features"
- "customizable behavior"
- "layered functionality"
- "configurable options"
- "enhance objects at runtime"

### 📋 **Scenarios that indicate Decorator pattern:**
1. **UI Components**: "Add scrollbars, borders, or shadows to windows dynamically"
2. **Food/Drink Customization**: "Allow customers to add toppings, sauces, or extras"
3. **Text Processing**: "Add formatting like bold, italic, underline in any combination"
4. **Stream Processing**: "Add compression, encryption, or buffering to data streams"
5. **Gaming**: "Add power-ups, abilities, or effects to game characters"
6. **Configuration**: "Allow optional features to be enabled/disabled"

## Real-world Examples
- **Coffee Shop**: Basic coffee + milk + sugar + whip cream + vanilla
- **Pizza Ordering**: Base pizza + cheese + pepperoni + mushrooms + extra sauce
- **Text Editors**: Plain text + bold + italic + underline + highlighting
- **Java I/O Streams**: FileInputStream + BufferedInputStream + GZIPInputStream
- **Web Middleware**: Request + Authentication + Logging + Compression + Caching

## Benefits
✅ **Flexibility**: Add/remove responsibilities dynamically  
✅ **Single Responsibility**: Each decorator handles one concern  
✅ **Open/Closed Principle**: Extend behavior without modifying existing code  
✅ **Composition over Inheritance**: More flexible than static inheritance  
✅ **Runtime Configuration**: Behavior can be configured at runtime  

## Drawbacks
❌ **Complexity**: Can create many small objects  
❌ **Debugging**: Stack of decorators can be hard to debug  
❌ **Object Identity**: Decorated object != original object  
❌ **Performance**: Multiple wrapper calls can impact performance  

## How to Implement
1. **Define** component interface with core operations
2. **Create** concrete component with basic implementation
3. **Create** base decorator class implementing component interface
4. **Implement** concrete decorators extending base decorator
5. **Compose** decorators at runtime as needed

## Code Example Usage
```java
// Start with basic coffee
Coffee coffee = new BasicCoffee();

// Add decorations dynamically
coffee = new MilkDecorator(coffee);
coffee = new SugarDecorator(coffee);
coffee = new VanillaDecorator(coffee);

// Use the decorated object
System.out.println(coffee.getDescription()); // "Basic Coffee, Milk, Sugar, Vanilla"
System.out.println(coffee.getCost());        // 2.00 + 0.50 + 0.20 + 0.60 = 3.30
```

## Decorator vs Other Patterns
- **Adapter**: Changes interface; Decorator enhances interface
- **Composite**: Focuses on structure; Decorator focuses on behavior
- **Strategy**: Changes algorithm; Decorator adds features
- **Proxy**: Controls access; Decorator adds functionality

## Common Use Cases in Software
- **Middleware**: Express.js middleware stack
- **GUI Components**: Swing/AWT decorators for borders, scrollbars
- **Stream Processing**: Java I/O decorator classes
- **Authentication**: Multiple authentication layers
- **Caching**: Multiple levels of caching decorators

## Test Questions That Indicate Decorator Pattern
1. "How would you add optional features to objects without changing their class?"
2. "Design a system where customers can customize products with various add-ons."
3. "How would you implement a text editor with multiple formatting options?"
4. "Create a coffee ordering system with customizable ingredients."
5. "Design a data stream that can be compressed, encrypted, and buffered in any combination."
6. "How would you add logging, authentication, and validation to API calls?" 