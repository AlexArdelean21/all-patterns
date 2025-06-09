# Command Pattern - Visual Diagrams

## 🏗️ UML Class Diagram

```mermaid
classDiagram
    class Command {
        <<interface>>
        +execute() void
        +undo() void
        +getDescription() String
    }
    
    class InsertCommand {
        -TextEditor editor
        -String text
        -int position
        
        +InsertCommand(TextEditor editor, String text, int position)
        +execute() void
        +undo() void
        +getDescription() String
    }
    
    class DeleteCommand {
        -TextEditor editor
        -int start
        -int length
        -String deletedText
        
        +DeleteCommand(TextEditor editor, int start, int length)
        +execute() void
        +undo() void
        +getDescription() String
    }
    
    class ReplaceCommand {
        -TextEditor editor
        -int start
        -int length
        -String newText
        -String originalText
        
        +ReplaceCommand(TextEditor editor, int start, int length, String newText)
        +execute() void
        +undo() void
        +getDescription() String
    }
    
    class MacroCommand {
        -List~Command~ commands
        -String description
        
        +MacroCommand(String description)
        +addCommand(Command command) void
        +execute() void
        +undo() void
        +getDescription() String
    }
    
    class TextEditor {
        -StringBuilder content
        -int cursorPosition
        
        +insertText(String text, int position) void
        +deleteText(int start, int length) void
        +replaceText(int start, int length, String newText) void
        +getText() String
        +getCursorPosition() int
        +setCursorPosition(int position) void
        +clear() void
        +showContent() void
    }
    
    class EditorInvoker {
        -Stack~Command~ undoStack
        -Stack~Command~ redoStack
        -TextEditor editor
        
        +EditorInvoker(TextEditor editor)
        +executeCommand(Command command) void
        +undo() void
        +redo() void
        +showHistory() void
        +canUndo() boolean
        +canRedo() boolean
    }
    
    class Light {
        -boolean isOn
        -int brightness
        
        +turnOn() void
        +turnOff() void
        +setBrightness(int level) void
        +isOn() boolean
        +getBrightness() int
    }
    
    class LightOnCommand {
        -Light light
        -boolean wasOn
        -int previousBrightness
        
        +LightOnCommand(Light light)
        +execute() void
        +undo() void
        +getDescription() String
    }
    
    Command <|.. InsertCommand
    Command <|.. DeleteCommand
    Command <|.. ReplaceCommand
    Command <|.. MacroCommand
    Command <|.. LightOnCommand
    MacroCommand --> Command : contains
    InsertCommand --> TextEditor : uses
    DeleteCommand --> TextEditor : uses
    ReplaceCommand --> TextEditor : uses
    EditorInvoker --> Command : executes
    EditorInvoker --> TextEditor : manages
    LightOnCommand --> Light : controls
```

## 🔄 Sequence Diagram - Command Execution with Undo

```mermaid
sequenceDiagram
    participant Client
    participant EditorInvoker
    participant InsertCommand
    participant TextEditor
    
    Note over Client, TextEditor: Command Creation and Execution
    Client->>InsertCommand: new InsertCommand(editor, "Hello", 0)
    InsertCommand-->>Client: command instance
    
    Client->>EditorInvoker: executeCommand(insertCmd)
    EditorInvoker->>InsertCommand: execute()
    InsertCommand->>TextEditor: insertText("Hello", 0)
    TextEditor-->>InsertCommand: text inserted
    InsertCommand-->>EditorInvoker: execution complete
    EditorInvoker->>EditorInvoker: push to undoStack
    EditorInvoker-->>Client: command executed
    
    Note over Client, TextEditor: Undo Operation
    Client->>EditorInvoker: undo()
    EditorInvoker->>EditorInvoker: pop from undoStack
    EditorInvoker->>InsertCommand: undo()
    InsertCommand->>TextEditor: deleteText(0, 5)
    TextEditor-->>InsertCommand: text deleted
    InsertCommand-->>EditorInvoker: undo complete
    EditorInvoker->>EditorInvoker: push to redoStack
    EditorInvoker-->>Client: undo complete
    
    Note over Client, TextEditor: Redo Operation
    Client->>EditorInvoker: redo()
    EditorInvoker->>EditorInvoker: pop from redoStack
    EditorInvoker->>InsertCommand: execute()
    InsertCommand->>TextEditor: insertText("Hello", 0)
    TextEditor-->>InsertCommand: text inserted
    InsertCommand-->>EditorInvoker: execution complete
    EditorInvoker->>EditorInvoker: push to undoStack
    EditorInvoker-->>Client: redo complete
```

## 📝 Command Execution Flow

```mermaid
flowchart TD
    A[Client Creates Command] --> B[Command Object<br/>InsertCommand]
    B --> C[Invoker.executeCommand]
    C --> D[Command.execute]
    D --> E[Receiver.insertText]
    E --> F[Push to Undo Stack]
    F --> G[Clear Redo Stack]
    
    H[Client Calls Undo] --> I[Pop from Undo Stack]
    I --> J[Command.undo]
    J --> K[Receiver.deleteText]
    K --> L[Push to Redo Stack]
    
    M[Client Calls Redo] --> N[Pop from Redo Stack]
    N --> O[Command.execute]
    O --> P[Receiver.insertText]
    P --> Q[Push to Undo Stack]
    
    style A fill:#e1f5fe
    style H fill:#fff3e0
    style M fill:#f3e5f5
    style F fill:#c8e6c9
    style L fill:#c8e6c9
    style Q fill:#c8e6c9
```

## 🎯 Command Pattern Components

```mermaid
mindmap
  root((Command Pattern))
    Command Interface
      Common Contract
        execute()
        undo()
        getDescription()
      Behavioral Definition
        Defines what all commands must implement
        Ensures consistent interface
    
    Concrete Commands
      InsertCommand
        Text insertion
        Position tracking
        Undo by deletion
      DeleteCommand
        Text deletion
        Content preservation
        Undo by insertion
      ReplaceCommand
        Text replacement
        Original text storage
        Undo by restore
      MacroCommand
        Composite commands
        Sequential execution
        Reverse undo order
    
    Receiver
      TextEditor
        Content management
        Cursor positioning
        Text operations
      Light
        Device state
        Brightness control
        On/off operations
    
    Invoker
      EditorInvoker
        Command execution
        History management
        Undo/Redo stacks
        Command validation
```

## 🔄 Macro Command Structure

```mermaid
graph TD
    A[MacroCommand: "Format Text"] --> B[Command 1<br/>InsertCommand]
    A --> C[Command 2<br/>ReplaceCommand]
    A --> D[Command 3<br/>DeleteCommand]
    
    E[Execute Macro] --> F[Execute Command 1]
    F --> G[Execute Command 2]
    G --> H[Execute Command 3]
    
    I[Undo Macro] --> J[Undo Command 3]
    J --> K[Undo Command 2]
    K --> L[Undo Command 1]
    
    style A fill:#ffeb3b
    style E fill:#4caf50
    style I fill:#ff9800
```

## 📚 Command History Management

```mermaid
stateDiagram-v2
    [*] --> Empty: Initialize
    Empty --> HasHistory: Execute Command
    HasHistory --> HasHistory: Execute Command
    HasHistory --> HasUndo: Undo Command
    HasUndo --> HasHistory: Execute Command
    HasUndo --> HasUndo: Undo Command
    HasUndo --> HasHistory: Redo Command
    HasHistory --> Empty: Clear History
    HasUndo --> Empty: Clear History
    
    note right of HasHistory: Commands in undo stack<br/>Redo stack empty
    note right of HasUndo: Commands in both stacks<br/>Can undo and redo
```

## 🏠 Smart Home Command Example

```mermaid
flowchart LR
    subgraph "Smart Home Controller"
        A[Remote Control] --> B[Command Invoker]
    end
    
    subgraph "Commands"
        C[LightOnCommand]
        D[LightOffCommand]
        E[SetBrightnessCommand]
        F[MacroCommand]
    end
    
    subgraph "Devices (Receivers)"
        G[Living Room Light]
        H[Kitchen Light]
        I[Bedroom Light]
    end
    
    B --> C
    B --> D
    B --> E
    B --> F
    
    C --> G
    D --> H
    E --> I
    
    F --> C
    F --> D
    F --> E
    
    style A fill:#e1f5fe
    style B fill:#fff3e0
    style F fill:#f3e5f5
```

## ⚡ Command vs Strategy vs State

```mermaid
graph TB
    subgraph "Command Pattern"
        A[Encapsulates requests<br/>as objects]
        B[Supports undo/redo<br/>operations]
        C[Queues and logs<br/>operations]
    end
    
    subgraph "Strategy Pattern"
        D[Encapsulates algorithms<br/>as objects]
        E[Runtime algorithm<br/>selection]
        F[No undo/redo<br/>typically]
    end
    
    subgraph "State Pattern"
        G[Encapsulates states<br/>as objects]
        H[Behavior changes<br/>with state]
        I[State transitions<br/>managed internally]
    end
    
    subgraph "Common Characteristics"
        J[Object-oriented<br/>encapsulation]
        K[Runtime behavior<br/>modification]
        L[Polymorphic<br/>interfaces]
    end
    
    A --> J
    D --> K
    G --> L
    
    style A fill:#4caf50
    style D fill:#ff9800
    style G fill:#2196f3
```

## 🔧 Command Implementation Patterns

```mermaid
classDiagram
    class CommandQueue {
        -Queue~Command~ pendingCommands
        -Thread executorThread
        +enqueue(Command command) void
        +processCommands() void
        +pause() void
        +resume() void
    }
    
    class TransactionCommand {
        -List~Command~ commands
        -boolean executed
        +addCommand(Command command) void
        +commit() void
        +rollback() void
        +getCommands() List~Command~
    }
    
    class LoggingCommand {
        -Command wrappedCommand
        -Logger logger
        +LoggingCommand(Command command, Logger logger)
        +execute() void
        +undo() void
        +getDescription() String
    }
    
    class RemoteCommand {
        -String targetHost
        -Command command
        -NetworkService network
        +execute() void
        +undo() void
        +sendToRemote() void
    }
    
    CommandQueue --> Command : processes
    TransactionCommand --> Command : manages
    LoggingCommand --> Command : wraps
    RemoteCommand --> Command : delegates
```

## 💡 Key Design Insights

### 1. **Request Encapsulation**
```
Traditional: client.method(params)
Command: invoker.execute(new MethodCommand(client, params))
```

### 2. **Undo Implementation**
```
Store state before execution
Undo reverses the operation
Redo re-executes the command
```

### 3. **Macro Commands**
```
Composite pattern structure
Execute in order
Undo in reverse order
```

---

*Visual diagrams illustrate how the Command pattern encapsulates requests as objects, enabling flexible operation management, undo/redo functionality, and command composition.* 