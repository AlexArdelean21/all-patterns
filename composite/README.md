# Composite Pattern

## Definition
The Composite Pattern is a structural design pattern that lets you compose objects into tree structures and then work with these structures as if they were individual objects. It allows clients to treat individual objects and compositions of objects uniformly.

## Intent
- Compose objects into tree structures to represent part-whole hierarchies
- Let clients treat individual objects and compositions uniformly
- Make the client code simple by treating primitive and composite objects the same way
- Add new kinds of components without breaking existing code

## Structure
The pattern involves:
- **Component**: Interface for all objects in the composition
- **Leaf**: Primitive objects that have no children
- **Composite**: Objects that have children and implement child-related operations
- **Client**: Uses Component interface to work with objects

## When to Use (Recognition in Requirements)

### 🔍 **Look for these keywords in requirements:**
- "tree structure"
- "hierarchical data"
- "part-whole relationship"
- "recursive structure"
- "nested objects"
- "uniform treatment"

### 📋 **Scenarios that indicate Composite pattern:**
1. **File Systems**: "Handle files and directories uniformly"
2. **Organization Charts**: "Represent employees and departments in hierarchy"
3. **UI Components**: "Treat windows, panels, and buttons the same way"
4. **Menu Systems**: "Handle menu items and submenus uniformly"
5. **Document Structure**: "Represent paragraphs, sections, and documents"
6. **Mathematical Expressions**: "Handle numbers and complex expressions uniformly"

## Real-world Examples
- **File Explorer**: Files and folders in Windows/Mac/Linux
- **Organization Chart**: Employees, teams, departments, divisions
- **Drawing Applications**: Shapes, groups of shapes, complex drawings
- **Web Page DOM**: Elements, containers, nested structures
- **Menu Systems**: Menu items, submenus, menu bars
- **Expression Trees**: Numbers, operators, complex mathematical expressions

## Benefits
✅ **Uniform Interface**: Treat primitive and composite objects the same  
✅ **Recursive Composition**: Easy to add new kinds of components  
✅ **Simplified Client Code**: Client doesn't need to distinguish object types  
✅ **Flexible Structure**: Easy to add new component types  
✅ **Open/Closed Principle**: Open for extension, closed for modification  

## Drawbacks
❌ **Overly General**: Can make design overly general  
❌ **Type Safety**: Harder to restrict component types  
❌ **Complexity**: Can be overkill for simple hierarchies  
❌ **Performance**: Recursive operations can be expensive  

## How to Implement
1. **Define** common interface for all components
2. **Create** leaf classes that implement the interface
3. **Create** composite class that can contain other components
4. **Implement** operations to add/remove/access child components
5. **Ensure** operations work recursively on composite structures

## Code Example Usage
```java
// Create file system structure
Directory root = new Directory("Root");
Directory documents = new Directory("Documents");
File readme = new File("readme.txt", 10);

documents.addComponent(readme);
root.addComponent(documents);

// Treat all components uniformly
root.showDetails(); // Works recursively
int totalSize = root.getSize(); // Calculated recursively
```

## Composite vs Other Patterns
- **Decorator**: Adds behavior; Composite represents structure
- **Flyweight**: Shares objects; Composite contains objects
- **Iterator**: Traverses collection; Composite IS the collection structure
- **Visitor**: Operates on structure; Composite defines the structure

## Common Variations
1. **Child Management**: Who manages children (Component vs Composite)
2. **Caching**: Cache results of expensive operations
3. **Parent References**: Components know their parent
4. **Ordering**: Children are kept in specific order

## Implementation Considerations
### Child Management Approaches:
- **Component Interface**: All components can have children (more uniform)
- **Composite Only**: Only composites manage children (more type-safe)

### Navigation Support:
- **Parent References**: Components can navigate up the tree
- **Path Information**: Components know their position in hierarchy

## Test Questions That Indicate Composite Pattern
1. "How would you represent a file system with files and directories?"
2. "Design a system to handle mathematical expressions with numbers and operators."
3. "How would you implement a menu system with items and submenus?"
4. "Create a drawing application that handles shapes and groups of shapes."
5. "Design an organization chart that treats employees and departments uniformly."
6. "How would you represent a document with sections, paragraphs, and text?"

## Real-world Usage
- **Swing/AWT**: JComponent hierarchy (JPanel, JButton, etc.)
- **DOM**: HTML elements and their containers
- **AST**: Abstract Syntax Trees in compilers
- **Scene Graphs**: 3D graphics and game engines
- **File Systems**: Operating system file/directory structures 