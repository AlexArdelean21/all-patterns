# Flyweight Pattern - Class Diagram

```mermaid
classDiagram
    %% Core Flyweight Interface
    class CharacterFlyweight {
        <<interface>>
        +display(int, int, String, int, String) void
    }
    
    %% Concrete Flyweight
    class Character {
        -character: char
        +Character(char)
        +display(int, int, String, int, String) void
        +getCharacter() char
    }
    
    %% Flyweight Factory
    class CharacterFactory {
        -flyweights: Map~Character,CharacterFlyweight~$
        -creationCount: int$
        +getCharacter(char) CharacterFlyweight$
        +getCreationCount() int$
        +getFlyweightCount() int$
        +showStatistics() void$
    }
    
    %% Context Class
    class CharacterContext {
        -row: int
        -column: int
        -font: String
        -size: int
        -color: String
        -flyweight: CharacterFlyweight
        +CharacterContext(char, int, int, String, int, String)
        +display() void
        +setPosition(int, int) void
        +setFont(String) void
        +setSize(int) void
        +setColor(String) void
    }
    
    %% Document Class
    class Document {
        -characters: List~CharacterContext~
        -documentName: String
        +Document(String)
        +addCharacter(char, int, int, String, int, String) void
        +addText(String, int, int, String, int, String) void
        +display() void
        +getCharacterCount() int
        +showMemoryUsage() void
    }
    
    %% Particle System Example
    class ParticleFlyweight {
        <<interface>>
        +move(double, double, double, double, String, double) void
    }
    
    class ParticleType {
        -name: String
        -texture: String
        -behavior: String
        +ParticleType(String, String, String)
        +move(double, double, double, double, String, double) void
        +getName() String
    }
    
    class ParticleFactory {
        -particleTypes: Map~String,ParticleFlyweight~$
        +getParticleType(String, String, String) ParticleFlyweight$
        +getParticleTypeCount() int$
        +showParticleTypes() void$
    }
    
    class Particle {
        -x: double
        -y: double
        -velocityX: double
        -velocityY: double
        -color: String
        -size: double
        -type: ParticleFlyweight
        +Particle(double, double, double, double, String, double, ParticleFlyweight)
        +update() void
        +setPosition(double, double) void
        +setVelocity(double, double) void
    }
    
    class ParticleSystem {
        -particles: List~Particle~
        -systemName: String
        +ParticleSystem(String)
        +addParticle(double, double, double, double, String, double, String, String, String) void
        +update() void
        +showStatistics() void
    }
    
    %% Memory Simulator
    class MemorySimulator {
        +simulateWithoutFlyweight(int) void$
        +simulateWithFlyweight(int, int) void$
    }
    
    %% Relationships
    CharacterFlyweight <|.. Character
    ParticleFlyweight <|.. ParticleType
    
    CharacterFactory ..> Character : creates and manages
    ParticleFactory ..> ParticleType : creates and manages
    
    CharacterContext --> CharacterFlyweight : uses
    Document --> CharacterContext : contains
    
    Particle --> ParticleFlyweight : uses
    ParticleSystem --> Particle : contains
    
    note for Character
        Intrinsic State:
        - character value (shared)
        
        Extrinsic State:
        - position, font, size, color
        - passed as parameters
    end note
    
    note for CharacterFactory
        Flyweight Pool:
        - Manages flyweight instances
        - Ensures sharing
        - Memory statistics
    end note
    
    note for CharacterContext
        Context Object:
        - Stores extrinsic state
        - References flyweight
        - Delegates operations
    end note
    
    note for ParticleType
        Shared Behavior:
        - texture, behavior (intrinsic)
        
        Per-Instance Data:
        - position, velocity (extrinsic)
    end note
```

# Flyweight Pattern - Object Diagram

```mermaid
graph TD
    subgraph "Flyweight Pool"
        F1["Character 'A'"]
        F2["Character 'B'"]
        F3["Character 'C'"]
        F4["Character ' ' (space)"]
    end
    
    subgraph "Document: Hello World"
        C1["Context: row=1, col=1, font=Arial, color=Black"] --> F1
        C2["Context: row=1, col=2, font=Arial, color=Black"] --> F2
        C3["Context: row=1, col=3, font=Arial, color=Black"] --> F3
        C4["Context: row=1, col=4, font=Arial, color=Black"] --> F4
        C5["Context: row=1, col=5, font=Arial, color=Blue"] --> F1
    end
    
    subgraph "Another Document: ABCA"
        C6["Context: row=1, col=1, font=Times, color=Red"] --> F1
        C7["Context: row=1, col=2, font=Times, color=Red"] --> F2
        C8["Context: row=1, col=3, font=Times, color=Red"] --> F3
        C9["Context: row=1, col=4, font=Times, color=Red"] --> F1
    end
    
    subgraph "Memory Savings"
        MS1["Without Flyweight: 9 Character objects"]
        MS2["With Flyweight: 4 Character flyweights + 9 contexts"]
        MS3["Memory Saved: ~40% reduction"]
    end
```

# Flyweight Pattern - Sequence Diagram

```mermaid
sequenceDiagram
    participant Client
    participant Document
    participant CharacterFactory
    participant Context
    participant Flyweight
    
    Note over Client: Adding text to document
    
    Client->>Document: addText("AB", 1, 1, "Arial", 12, "Black")
    
    loop For each character
        Document->>CharacterFactory: getCharacter('A')
        CharacterFactory->>CharacterFactory: Check flyweight pool
        
        opt Flyweight doesn't exist
            CharacterFactory->>Flyweight: new Character('A')
            CharacterFactory->>CharacterFactory: Store in pool
        end
        
        CharacterFactory-->>Document: Character flyweight
        Document->>Context: new CharacterContext('A', 1, 1, "Arial", 12, "Black")
        Context->>Context: Store extrinsic state
        Context->>Context: Store flyweight reference
    end
    
    Note over Client: Displaying document
    
    Client->>Document: display()
    
    loop For each context
        Document->>Context: display()
        Context->>Flyweight: display(row, column, font, size, color)
        Flyweight->>Flyweight: Use intrinsic state + extrinsic parameters
        Flyweight-->>Context: Display complete
    end
```

## State Separation Examples

### Text Editor
- **Intrinsic State (Flyweight)**: Character value ('A', 'B', 'C', etc.)
- **Extrinsic State (Context)**: Position, font, size, color, style

### Particle System  
- **Intrinsic State (Flyweight)**: Particle type, texture, behavior
- **Extrinsic State (Context)**: Position, velocity, color, size

## Memory Optimization Benefits

1. **Shared Objects**: Multiple contexts share same flyweight instances
2. **Reduced Memory**: Dramatic reduction in object count for large datasets
3. **Performance**: Faster object creation through reuse
4. **Scalability**: System scales better with increasing object count 