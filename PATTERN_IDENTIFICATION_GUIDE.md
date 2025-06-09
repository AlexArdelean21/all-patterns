# Design Pattern Identification Guide

## 🎯 How to Choose the Right Design Pattern

This guide helps you identify which design patterns to use based on keywords and phrases commonly found in requirements and problem descriptions.

---

## 🏗️ **CREATIONAL PATTERNS**

### **Singleton Pattern**
**When you see these keywords:**
- "only one instance"
- "global access point"
- "shared resource"
- "single point of control"
- "configuration manager"
- "logging service"
- "database connection pool"
- "cache manager"

**Example phrases:**
- *"The system should have only one configuration manager"*
- *"There should be a single point of access to the database"*
- *"The application needs one global logger"*

---

### **Factory Pattern**
**When you see these keywords:**
- "create objects without specifying exact classes"
- "object creation based on input"
- "different types of objects"
- "creation logic"
- "switch between implementations"
- "runtime object creation"

**Example phrases:**
- *"Create different types of payment processors based on user selection"*
- *"The system should create appropriate handlers based on file type"*
- *"Generate different report formats depending on user preference"*

---

### **Abstract Factory Pattern**
**When you see these keywords:**
- "families of related objects"
- "platform-specific components"
- "different product lines"
- "consistent object families"
- "multiple related products"
- "theme-based creation"

**Example phrases:**
- *"Create UI components for different operating systems"*
- *"Generate related database objects for different vendors"*
- *"Provide different sets of widgets for various themes"*

---

### **Builder Pattern**
**When you see these keywords:**
- "complex object construction"
- "step-by-step creation"
- "optional parameters"
- "configuration object"
- "many constructor parameters"
- "immutable objects"
- "fluent interface"
- "method chaining"

**Example phrases:**
- *"Configure objects with many optional settings"*
- *"Build complex objects step by step"*
- *"Create objects with telescoping constructor problem"*
- *"Allow customizable object creation with preset configurations"*

---

### **Prototype Pattern**
**When you see these keywords:**
- "clone existing objects"
- "copy objects"
- "expensive object creation"
- "object templates"
- "duplicate instances"
- "avoid subclassing"

**Example phrases:**
- *"Create new objects by copying existing ones"*
- *"Clone configuration objects for different environments"*
- *"Duplicate complex objects without knowing their concrete types"*

---

## 🏗️ **STRUCTURAL PATTERNS**

### **Adapter Pattern**
**When you see these keywords:**
- "incompatible interfaces"
- "legacy system integration"
- "third-party library"
- "interface mismatch"
- "wrapper"
- "convert interface"
- "make compatible"

**Example phrases:**
- *"Integrate legacy payment system with new API"*
- *"Make third-party library work with our interface"*
- *"Convert old data format to new system requirements"*

---

### **Facade Pattern**
**When you see these keywords:**
- "simplified interface"
- "hide complexity"
- "unified interface"
- "single entry point"
- "complex subsystem"
- "easy-to-use interface"
- "orchestrate multiple services"

**Example phrases:**
- *"Provide simple interface to complex video conversion library"*
- *"Create unified API for multiple microservices"*
- *"Hide implementation complexity from clients"*

---

### **Decorator Pattern** ⭐
**When you see these keywords:**
- "add behavior dynamically"
- "enhance objects at runtime"
- "optional features"
- "customizable functionality"
- "modify behavior without changing code"
- "extend functionality"
- "wrap objects"
- "filters"
- "plugins"

**Example phrases:**
- *"Add filters or sorting criteria to email client"*
- *"Customize transmission function with additional confirmation"*
- *"Enhance objects with optional features during use"*
- *"Apply multiple filters to data stream"*

---

### **Composite Pattern** ⭐
**When you see these keywords:**
- "tree structure"
- "part-whole hierarchy"
- "individual and groups treated uniformly"
- "recursive structure"
- "nested elements"
- "collections of objects"
- "hierarchical data"

**Example phrases:**
- *"Email groups composed of several email addresses"*
- *"File system with files and directories"*
- *"Organization chart with employees and departments"*
- *"Menu items and submenus treated the same way"*

---

### **Flyweight Pattern**
**When you see these keywords:**
- "large number of similar objects"
- "memory optimization"
- "shared state"
- "intrinsic vs extrinsic state"
- "reduce memory usage"
- "object pooling"

**Example phrases:**
- *"Display thousands of similar characters in text editor"*
- *"Manage large number of similar game objects"*
- *"Optimize memory for repeated UI elements"*

---

### **Proxy Pattern**
**When you see these keywords:**
- "placeholder"
- "lazy loading"
- "access control"
- "caching"
- "remote object"
- "surrogate"
- "control access"

**Example phrases:**
- *"Load expensive resources only when needed"*
- *"Control access to sensitive operations"*
- *"Cache results of expensive operations"*
- *"Provide local representative of remote object"*

---

## 🎭 **BEHAVIORAL PATTERNS**

### **Strategy Pattern**
**When you see these keywords:**
- "multiple algorithms"
- "interchangeable algorithms"
- "different ways to do same thing"
- "algorithm selection at runtime"
- "switch between implementations"
- "payment methods"
- "sorting algorithms"

**Example phrases:**
- *"Choose different payment methods at runtime"*
- *"Select appropriate sorting algorithm based on data size"*
- *"Switch between different compression algorithms"*

---

### **Observer Pattern**
**When you see these keywords:**
- "notify multiple objects"
- "event system"
- "publish-subscribe"
- "broadcast changes"
- "one-to-many dependency"
- "automatic updates"
- "listeners"

**Example phrases:**
- *"Notify all subscribers when new content is published"*
- *"Update multiple views when model changes"*
- *"Broadcast events to interested parties"*

---

### **Command Pattern**
**When you see these keywords:**
- "undo/redo operations"
- "queue operations"
- "log operations"
- "macro commands"
- "encapsulate requests"
- "parameterize objects with operations"
- "remote execution"

**Example phrases:**
- *"Support undo/redo in text editor"*
- *"Queue database operations for batch processing"*
- *"Create macros from multiple operations"*
- *"Log all user actions for replay"*

---

### **Chain of Responsibility Pattern**
**When you see these keywords:**
- "pass request along chain"
- "multiple handlers"
- "escalation"
- "approval workflow"
- "middleware"
- "filter chain"
- "request processing pipeline"

**Example phrases:**
- *"Route support tickets based on priority level"*
- *"Process expense approvals through management hierarchy"*
- *"Apply series of filters to incoming requests"*

---

### **State Pattern**
**When you see these keywords:**
- "object behavior changes with state"
- "state machine"
- "different behavior in different states"
- "state transitions"
- "context-dependent behavior"

**Example phrases:**
- *"Document behaves differently when in draft vs published state"*
- *"Connection object has different behavior when connected/disconnected"*
- *"Game character abilities change based on current state"*

---

### **Template Method Pattern**
**When you see these keywords:**
- "skeleton of algorithm"
- "common algorithm structure"
- "override specific steps"
- "invariant parts and variant parts"
- "framework with hooks"

**Example phrases:**
- *"Define common data processing steps, allow customization of specific operations"*
- *"Framework provides overall structure, subclasses implement details"*
- *"Common workflow with customizable steps"*

---

### **Iterator Pattern**
**When you see these keywords:**
- "traverse collection"
- "access elements sequentially"
- "hide internal structure"
- "multiple traversal methods"
- "aggregate objects"

**Example phrases:**
- *"Iterate through collection without exposing internal structure"*
- *"Provide different ways to traverse same data structure"*
- *"Access elements of complex data structure sequentially"*

---

### **Memento Pattern**
**When you see these keywords:**
- "save object state"
- "restore previous state"
- "snapshot"
- "checkpoints"
- "rollback"
- "versioning"

**Example phrases:**
- *"Save game state for later restoration"*
- *"Create checkpoints during long operations"*
- *"Implement version control for documents"*

---

## 🔍 **QUICK PATTERN LOOKUP BY PROBLEM TYPE**

### **Object Creation Problems**
- Too many constructor parameters → **Builder**
- Need only one instance → **Singleton**
- Create objects without knowing exact type → **Factory/Abstract Factory**
- Expensive object creation → **Prototype**

### **Interface Problems**
- Incompatible interfaces → **Adapter**
- Too complex interface → **Facade**
- Need controlled access → **Proxy**

### **Behavior Modification**
- Add behavior dynamically → **Decorator**
- Switch algorithms at runtime → **Strategy**
- Object behavior depends on state → **State**

### **Communication Problems**
- One-to-many notifications → **Observer**
- Chain of request handlers → **Chain of Responsibility**
- Encapsulate requests → **Command**

### **Structure Problems**
- Part-whole hierarchies → **Composite**
- Memory optimization for similar objects → **Flyweight**

---

## 📧 **EXAMPLE: Email Client System Analysis**

**Requirements:**
> *"Email clients must be able to send and receive emails for a specific email address but also they can be used to manage email groups composed of several email addresses. Users should be able to manage this structure from their solution. Users have the ability to modify/customize the email client during use. The proposed solution must allow clients to modify/customize the solution by adding filters or email sorting criteria. Also, the transmission function of a message can be modified by adding an additional confirmation or by delaying the sending or specifying specific hours."*

**Pattern Identification:**

1. **Composite Pattern** ⭐
   - Keywords: *"email groups composed of several email addresses"*
   - Reason: Need to treat individual emails and email groups uniformly

2. **Decorator Pattern** ⭐
   - Keywords: *"modify/customize during use"*, *"adding filters"*, *"adding additional confirmation"*, *"delaying the sending"*
   - Reason: Need to add behavior dynamically without changing core functionality

**Alternative/Supporting Patterns:**
- **Strategy Pattern**: For different email sorting criteria algorithms
- **Command Pattern**: For queuing/scheduling email transmission
- **Chain of Responsibility**: For email filtering pipeline

---

## 💡 **PATTERN IDENTIFICATION PROCESS**

1. **Read the requirements carefully**
2. **Identify key verbs and nouns**
3. **Look for structural relationships**
4. **Identify behavioral requirements**
5. **Match keywords to patterns**
6. **Consider pattern combinations**
7. **Validate with use cases**

---

*Use this guide to quickly identify appropriate design patterns based on problem descriptions and requirements. Remember that real-world solutions often combine multiple patterns!* 