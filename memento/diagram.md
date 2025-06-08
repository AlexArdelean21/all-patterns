# Memento Pattern - Class Diagram

```mermaid
classDiagram
    %% Text Editor Example
    class TextMemento {
        -content: String
        -cursorPosition: int
        -timestamp: long
        -operation: String
        +TextMemento(String, int, String)
        +getContent() String
        +getCursorPosition() int
        +getTimestamp() long
        +getOperation() String
    }
    
    class TextEditor {
        -content: StringBuilder
        -cursorPosition: int
        -filename: String
        +TextEditor(String)
        +createMemento(String) TextMemento
        +restoreFromMemento(TextMemento) void
        +insertText(String) void
        +deleteText(int) void
        +moveCursor(int) void
        +replaceText(int, int, String) void
        +clear() void
        +showContent() void
        +getContent() String
        +getCursorPosition() int
        +getFilename() String
    }
    
    class EditorHistory {
        -history: List~TextMemento~
        -currentIndex: int
        -maxHistorySize: int
        +EditorHistory(int)
        +save(TextMemento) void
        +undo() TextMemento
        +redo() TextMemento
        +canUndo() boolean
        +canRedo() boolean
        +showHistory() void
        +clear() void
    }
    
    %% Game Save System
    class GameStateMemento {
        -playerName: String
        -level: int
        -health: int
        -score: int
        -inventory: Map~String,Integer~
        -location: String
        -saveTime: long
        -saveName: String
        +GameStateMemento(String, int, int, int, Map, String, String)
        +getPlayerName() String
        +getLevel() int
        +getHealth() int
        +getScore() int
        +getInventory() Map~String,Integer~
        +getLocation() String
        +getSaveTime() long
        +getSaveName() String
    }
    
    class Game {
        -playerName: String
        -level: int
        -health: int
        -score: int
        -inventory: Map~String,Integer~
        -currentLocation: String
        +Game(String)
        +createSave(String) GameStateMemento
        +loadSave(GameStateMemento) void
        +levelUp() void
        +takeDamage(int) void
        +addScore(int) void
        +addItem(String, int) void
        +useItem(String, int) void
        +moveToLocation(String) void
        +showStatus() void
    }
    
    class SaveGameManager {
        -saves: Map~String,GameStateMemento~
        -maxSaves: int
        +SaveGameManager(int)
        +save(GameStateMemento) void
        +load(String) GameStateMemento
        +deleteSave(String) void
        +listSaves() void
    }
    
    %% Relationships
    TextEditor ..> TextMemento : creates
    TextEditor <.. TextMemento : restores from
    EditorHistory --> TextMemento : manages
    
    Game ..> GameStateMemento : creates
    Game <.. GameStateMemento : restores from
    SaveGameManager --> GameStateMemento : manages
    
    note for TextMemento
        Memento Object:
        - Immutable state snapshot
        - Timestamp for history
        - Operation description
    end note
    
    note for EditorHistory
        Caretaker:
        - Manages memento lifecycle
        - Undo/redo functionality
        - Limited history size
    end note
    
    note for Game
        Originator:
        - Creates mementos
        - Restores from mementos
        - Encapsulates complex state
    end note
    
    note for SaveGameManager
        Caretaker:
        - Named save slots
        - Persistence management
        - Save file organization
    end note
```

# Memento Pattern - Sequence Diagrams

## Text Editor Undo/Redo Sequence

```mermaid
sequenceDiagram
    participant User
    participant Editor
    participant History
    participant Memento
    
    Note over User: Text Editing Operations
    
    User->>Editor: insertText("Hello")
    Editor->>Editor: Update content and cursor
    Editor->>Memento: new TextMemento(content, position, "Insert 'Hello'")
    Editor->>History: save(memento)
    History->>History: Add to history
    
    User->>Editor: insertText(" World")
    Editor->>Editor: Update content and cursor
    Editor->>Memento: new TextMemento(content, position, "Insert ' World'")
    Editor->>History: save(memento)
    
    Note over User: Undo Operation
    User->>History: undo()
    History->>History: Get previous memento
    History-->>Editor: TextMemento("Hello")
    Editor->>Editor: restoreFromMemento(memento)
    Editor->>Editor: Update content and cursor position
    
    Note over User: Redo Operation
    User->>History: redo()
    History->>History: Get next memento
    History-->>Editor: TextMemento("Hello World")
    Editor->>Editor: restoreFromMemento(memento)
    Editor->>Editor: Update content and cursor position
```

## Game Save/Load Sequence

```mermaid
sequenceDiagram
    participant Player
    participant Game
    participant SaveManager
    participant Memento
    
    Note over Player: Game Progress
    Player->>Game: levelUp()
    Player->>Game: addScore(100)
    Player->>Game: moveToLocation("Castle")
    
    Note over Player: Save Game
    Player->>Game: createSave("Castle_Checkpoint")
    Game->>Game: Capture current state
    Game->>Memento: new GameStateMemento(all game state)
    Game-->>SaveManager: save(memento)
    SaveManager->>SaveManager: Store memento with name
    
    Note over Player: Continue Playing
    Player->>Game: takeDamage(50)
    Player->>Game: moveToLocation("Dragon's Lair")
    
    Note over Player: Game Over - Load Save
    Player->>SaveManager: load("Castle_Checkpoint")
    SaveManager->>SaveManager: Retrieve memento
    SaveManager-->>Game: GameStateMemento
    Game->>Game: loadSave(memento)
    Game->>Game: Restore all game state
    
    Note over Game: State restored to castle checkpoint
```

## Key Memento Characteristics

1. **Encapsulation Preservation**: Memento doesn't expose originator's internal structure
2. **Immutability**: Mementos are immutable snapshots
3. **Caretaker Blindness**: Caretaker doesn't interpret memento contents
4. **State Independence**: Mementos exist independently of originator

## Benefits Demonstrated

- **Undo/Redo**: Complete operation history with state restoration
- **Checkpoints**: Save game progress at key moments
- **Recovery**: Restore from errors or unwanted changes
- **Encapsulation**: Internal state remains protected 