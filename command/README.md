# Command Pattern

## 🎯 Intent
The Command pattern encapsulates a request as an object, thereby letting you parameterize clients with different requests, queue or log requests, and support undoable operations. It decouples the invoker of a request from the receiver that processes it.

## 🏗️ Structure

### Key Components:
- **Command**: Interface that declares method for executing commands
- **Concrete Command**: Implements Command interface and defines action-receiver binding
- **Receiver**: Knows how to perform operations associated with a request
- **Invoker**: Asks the command to carry out the request
- **Client**: Creates ConcreteCommand objects and sets their receiver

### Class Diagram:
```
Command <<interface>>
├── + execute(): void
├── + undo(): void
└── + getDescription(): String

Concrete Commands:
├── InsertCommand (implements Command)
├── DeleteCommand (implements Command)
├── ReplaceCommand (implements Command)
└── MacroCommand (implements Command)

TextEditor (Receiver)
├── + insertText(String, int): void
├── + deleteText(int, int): void
└── + replaceText(int, int, String): void

EditorInvoker (Invoker)
├── - undoStack: Stack<Command>
├── - redoStack: Stack<Command>
├── + executeCommand(Command): void
├── + undo(): void
└── + redo(): void
```

## 💡 When to Use

### ✅ Use Command Pattern When:
- You want to parameterize objects by an action to perform
- You need to queue operations, schedule them, or execute them remotely
- You want to support undo/redo operations
- You need to log changes for crash recovery
- You want to structure a system around high-level operations built on primitives

### ❌ Avoid When:
- Simple callback functions suffice
- Operations don't need queuing, logging, or undo capabilities
- The overhead of creating command objects isn't justified

## 🔍 Real-World Examples

### From the Demo:
```java
// Create receiver
TextEditor editor = new TextEditor();
EditorInvoker invoker = new EditorInvoker(editor);

// Create and execute commands
Command insertCmd = new InsertCommand(editor, "Hello", 0);
invoker.executeCommand(insertCmd);

Command deleteCmd = new DeleteCommand(editor, 0, 5);
invoker.executeCommand(deleteCmd);

// Undo operations
invoker.undo(); // Undo delete
invoker.undo(); // Undo insert

// Create macro command
MacroCommand macro = new MacroCommand("Format Text");
macro.addCommand(new InsertCommand(editor, "World", 5));
macro.addCommand(new ReplaceCommand(editor, 0, 5, "Hi"));
invoker.executeCommand(macro);
```

### Common Use Cases:
- **Text Editors**: Undo/redo operations, macros
- **GUI Applications**: Button clicks, menu selections
- **Database Transactions**: Transaction logging, rollback operations
- **Remote Method Invocation**: Network requests as commands
- **Task Queues**: Job scheduling, batch processing
- **Smart Home Systems**: Device control commands

## 🎪 Demo Walkthrough

The `CommandDemo.java` demonstrates:

1. **Text Editor Commands**: Insert, delete, replace operations with undo capability
2. **Command History**: Undo/redo stack management
3. **Macro Commands**: Composite commands that group multiple operations
4. **Smart Home Example**: Light control with command pattern
5. **Command Queuing**: Storing and executing commands in sequence

### Key Features:
- Full undo/redo functionality
- Macro command composition
- Command history tracking
- State preservation for undo operations
- Multiple receiver types (TextEditor, Light)

## 🚀 Running the Demo

```bash
# Compile and run
javac CommandDemo.java
java CommandDemo
```

**Expected Output:**
- Text editing operations with undo/redo
- Command history display
- Macro command execution
- Smart home device control demonstrations

## 🔄 Variations

### 1. Simple Command
- Basic execute() method
- No undo capability
- Minimal state storage

### 2. Undoable Command
- execute() and undo() methods
- State preservation for reversal
- Command history management

### 3. Macro/Composite Command
- Contains multiple sub-commands
- Executes commands in sequence
- Undoes in reverse order

### 4. Queued Command
- Commands stored in queue for later execution
- Batch processing capabilities
- Transaction-like behavior

## 💭 Benefits & Drawbacks

### ✅ Benefits:
- **Decoupling**: Separates invoker from receiver
- **Undo/Redo**: Easy to implement reversible operations
- **Macro Support**: Can combine multiple commands
- **Logging**: Commands can be logged for audit/recovery
- **Queuing**: Commands can be queued, scheduled, or executed remotely
- **Extensibility**: Easy to add new commands without changing existing code

### ❌ Drawbacks:
- **Complexity**: Increases number of classes
- **Memory Overhead**: Command objects consume memory
- **Indirection**: Adds layer between client and receiver

## 🔧 Implementation Best Practices

### 1. **Command Interface Design**
```java
interface Command {
    void execute();
    void undo();           // For undoable commands
    String getDescription(); // For logging/debugging
}
```

### 2. **State Preservation for Undo**
```java
class DeleteCommand implements Command {
    private String deletedText; // Store for undo
    
    public DeleteCommand(TextEditor editor, int start, int length) {
        // Capture state before execution
        this.deletedText = editor.getText().substring(start, start + length);
    }
}
```

### 3. **Macro Command Implementation**
```java
class MacroCommand implements Command {
    private List<Command> commands = new ArrayList<>();
    
    public void execute() {
        commands.forEach(Command::execute);
    }
    
    public void undo() {
        // Undo in reverse order
        for (int i = commands.size() - 1; i >= 0; i--) {
            commands.get(i).undo();
        }
    }
}
```

### 4. **Invoker with History**
```java
class EditorInvoker {
    private Stack<Command> undoStack = new Stack<>();
    private Stack<Command> redoStack = new Stack<>();
    
    public void executeCommand(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear(); // Clear redo stack
    }
}
```

## 🎯 Key Takeaways

1. **Encapsulation**: Requests are encapsulated as objects
2. **Parameterization**: Objects can be parameterized with commands
3. **Decoupling**: Invoker doesn't need to know about receiver details
4. **Reversibility**: Easy to implement undo/redo functionality
5. **Composability**: Commands can be combined into macros
6. **Persistence**: Commands can be stored, logged, or transmitted

## 🔗 Related Patterns

- **Memento**: Often used together for undo functionality
- **Composite**: Macro commands use Composite structure
- **Strategy**: Similar structure but different intent
- **Observer**: Commands can notify observers of execution

---

*The Command pattern is essential for building flexible, undoable operations and decoupling request senders from request processors.* 