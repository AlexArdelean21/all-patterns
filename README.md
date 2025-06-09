# Design Patterns Collection

This repository contains comprehensive demonstrations, diagrams, and documentation for 19 essential design patterns. Each pattern includes:

- 📝 **Demonstration Code**: Working Java examples showing real-world usage
- 📊 **UML Diagrams**: Visual representation of pattern structure  
- 📖 **Documentation**: Detailed explanations, use cases, and recognition guides

## 🏗️ Structural Patterns

| Pattern | Purpose | When to Use |
|---------|---------|-------------|
| [**Adapter**](./adapter/) | Make incompatible interfaces work together | Legacy system integration, third-party libraries |
| [**Facade**](./facade/) | Provide simplified interface to complex subsystem | Complex API simplification, multiple service coordination |
| [**Decorator**](./decorator/) | Add behavior to objects dynamically | Optional features, customizable functionality |
| [**Composite**](./composite/) | Treat individual and composite objects uniformly | Tree structures, hierarchical data |
| [**Flyweight**](./flyweight/) | Share common state to support large numbers of objects | Memory optimization, many similar objects |
| [**Proxy**](./proxy/) | Provide placeholder/surrogate for another object | Access control, lazy loading, caching |

## 🏭 Creational Patterns

| Pattern | Purpose | When to Use |
|---------|---------|-------------|
| [**Singleton**](./singleton/) | Ensure only one instance exists | Global state, resource management |
| [**Factory**](./factory/) | Create objects without specifying exact classes | Object creation abstraction, type flexibility |
| [**Abstract Factory**](./abstract-factory/) | Create families of related objects | Multiple product families, platform independence |
| [**Builder**](./builder/) | Construct complex objects step by step | Complex object creation, optional parameters |
| [**Prototype**](./prototype/) | Create objects by cloning existing instances | Expensive object creation, configuration copying |

## 🎭 Behavioral Patterns

| Pattern | Purpose | When to Use |
|---------|---------|-------------|
| [**Strategy**](./strategy/) | Define family of algorithms and make them interchangeable | Multiple algorithms, runtime algorithm selection |
| [**Observer**](./observer/) | Define one-to-many dependency between objects | Event systems, model-view `architectures |
| [**Chain of Responsibility**](./chain-of-responsibility/) | Pass requests along chain of handlers | Request processing, middleware, validation |
| [**Template Method**](./template/) | Define skeleton of algorithm, subclasses override steps | Common algorithm structure, code reuse |
| [**State**](./state/) | Alter object behavior when internal state changes | State machines, context-dependent behavior |
| [**Command**](./command/) | Encapsulate requests as objects | Undo/redo, queuing, logging operations |
| [**Iterator**](./iterator/) | Provide way to access elements sequentially | Collection traversal, hiding internal structure |
| [**Memento**](./memento/) | Capture and restore object state | Undo functionality, checkpoints, snapshots |

## 🚀 Quick Start

### Running Examples
Each pattern folder contains a demo file that can be compiled and run:

```bash
# Navigate to any pattern folder
cd adapter

# Compile and run the demo
javac AdapterDemo.java
java AdapterDemo
```

### Pattern Recognition Guide

When faced with a design challenge, look for these keywords:

#### 🔧 **Need to integrate different systems?** → **Adapter**
- "legacy system", "third-party API", "incompatible interface"

#### 🎯 **Need to simplify complex interactions?** → **Facade** 
- "unified interface", "hide complexity", "single entry point"

#### ✨ **Need optional features?** → **Decorator**
- "customizable", "add-ons", "optional functionality"

#### 🌳 **Need to handle tree structures?** → **Composite**
- "hierarchical", "tree-like", "part-whole relationships"

#### 🏭 **Need to create objects flexibly?** → **Factory/Abstract Factory**
- "create different types", "platform independent", "object families"

#### 👁️ **Need event notifications?** → **Observer**
- "notify when changes", "publish-subscribe", "event system"

#### ⛓️ **Need to process requests sequentially?** → **Chain of Responsibility**
- "middleware", "request processing", "validation chain"

## 📚 Learning Path

### Beginner Level
1. **Singleton** - Understand instance control
2. **Factory** - Learn object creation patterns
3. **Observer** - Grasp event-driven programming

### Intermediate Level
4. **Adapter** - Master interface integration
5. **Decorator** - Understand behavior extension
6. **Strategy** - Learn algorithm interchange

### Advanced Level
7. **Composite** - Handle complex structures
8. **Chain of Responsibility** - Master request processing
9. **Builder** - Complex object construction

## 🎯 Common Interview Questions

### Structural Patterns
- "How would you integrate legacy code with new system?" → **Adapter**
- "How would you simplify a complex API?" → **Facade**
- "How would you add features without modifying classes?" → **Decorator**

### Creational Patterns  
- "How would you ensure only one instance?" → **Singleton**
- "How would you create objects without knowing their type?" → **Factory**
- "How would you build complex objects step by step?" → **Builder**

### Behavioral Patterns
- "How would you implement undo functionality?" → **Command/Memento**
- "How would you notify multiple objects of changes?" → **Observer**
- "How would you implement different algorithms?" → **Strategy**

## 🛠️ Best Practices

### When NOT to Use Patterns
- ❌ Don't force patterns where simple solutions work
- ❌ Don't over-engineer simple problems
- ❌ Don't use patterns just to show knowledge

### When TO Use Patterns
- ✅ When you have a recurring design problem
- ✅ When you need flexible, maintainable code
- ✅ When working in team environments
- ✅ When building frameworks or libraries

## 🤝 Contributing

Feel free to improve examples, add more use cases, or fix bugs. Each pattern should maintain:
- Clear, compilable code examples
- Real-world scenarios
- Comprehensive documentation
- Visual diagrams

---

*"Design patterns are not about finding clever solutions to problems; they're about finding simple solutions to common problems."* 