# Builder Pattern - Visual Diagrams

## 🏗️ UML Class Diagram

```mermaid
classDiagram
    class Computer {
        -String cpu
        -int ram
        -int storage
        -String gpu
        -boolean bluetooth
        -boolean wifi
        -String operatingSystem
        -String caseColor
        -boolean liquidCooling
        -int powerSupply
        -String motherboard
        
        -Computer(ComputerBuilder builder)
        +getCpu() String
        +getRam() int
        +getStorage() int
        +toString() String
        +calculatePrice() double
    }
    
    class ComputerBuilder {
        -String cpu
        -int ram
        -int storage
        -String gpu
        -boolean bluetooth
        -boolean wifi
        -String operatingSystem
        -String caseColor
        -boolean liquidCooling
        -int powerSupply
        -String motherboard
        
        +ComputerBuilder(String cpu, int ram, int storage)
        +gpu(String gpu) ComputerBuilder
        +bluetooth(boolean bluetooth) ComputerBuilder
        +wifi(boolean wifi) ComputerBuilder
        +operatingSystem(String os) ComputerBuilder
        +caseColor(String color) ComputerBuilder
        +liquidCooling(boolean cooling) ComputerBuilder
        +powerSupply(int watts) ComputerBuilder
        +motherboard(String mb) ComputerBuilder
        +build() Computer
        +gamingPreset() ComputerBuilder
        +officePreset() ComputerBuilder
        +workstationPreset() ComputerBuilder
    }
    
    class ComputerDirector {
        +buildGamingComputer() Computer
        +buildOfficeComputer() Computer
        +buildWorkstation() Computer
        +buildBudgetComputer() Computer
        +buildCustomComputer(String cpu, int ram, int storage, Function customizer) Computer
    }
    
    class ComputerStore {
        -ComputerDirector director
        -List~Computer~ inventory
        +ComputerStore()
        -setupInventory() void
        +showInventory() void
        +customBuild(String cpu, int ram, int storage) Computer
    }
    
    Computer +-- ComputerBuilder : nested class
    ComputerDirector ..> ComputerBuilder : uses
    ComputerStore --> ComputerDirector : uses
    ComputerBuilder ..> Computer : creates
```

## 🔄 Sequence Diagram - Computer Building Process

```mermaid
sequenceDiagram
    participant Client
    participant Director
    participant Builder
    participant Computer
    
    Note over Client, Computer: Director-Managed Construction
    Client->>Director: buildGamingComputer()
    Director->>Builder: new ComputerBuilder("Intel i7", 32, 1000)
    Builder-->>Director: builder instance
    Director->>Builder: gamingPreset()
    Builder->>Builder: gpu("RTX 4070")
    Builder->>Builder: liquidCooling(true)
    Builder->>Builder: powerSupply(750)
    Builder-->>Director: configured builder
    Director->>Builder: build()
    Builder->>Computer: new Computer(this)
    Computer-->>Builder: computer instance
    Builder-->>Director: computer instance
    Director-->>Client: completed computer
    
    Note over Client, Computer: Direct Builder Usage
    Client->>Builder: new ComputerBuilder("Intel i5", 16, 512)
    Builder-->>Client: builder instance
    Client->>Builder: wifi(true)
    Builder-->>Client: builder (chained)
    Client->>Builder: bluetooth(true)
    Builder-->>Client: builder (chained)
    Client->>Builder: build()
    Builder->>Computer: new Computer(this)
    Computer-->>Builder: computer instance
    Builder-->>Client: completed computer
```

## 🏗️ Builder Construction Flow

```mermaid
flowchart TD
    A[Client Needs Computer] --> B{Construction Method}
    B -->|Simple| C[Direct Builder Usage]
    B -->|Complex| D[Director-Managed]
    B -->|Standard| E[Preset Configuration]
    
    C --> F[new ComputerBuilder<br/>cpu, ram, storage]
    F --> G[Add Optional Components]
    G --> H[Method Chaining]
    H --> I[build()]
    
    D --> J[Director.buildGamingComputer]
    J --> K[Director Uses Builder]
    K --> L[Apply Gaming Preset]
    L --> I
    
    E --> M[Builder.gamingPreset]
    M --> N[Pre-configured Components]
    N --> I
    
    I --> O[Computer Instance]
    
    style C fill:#e1f5fe
    style D fill:#f3e5f5
    style E fill:#fff3e0
    style O fill:#e8f5e8
```

## 🔧 Method Chaining Visualization

```mermaid
graph LR
    A[new ComputerBuilder<br/>"Intel i7", 32, 1000] --> B[.gpu<br/>"RTX 4070"]
    B --> C[.wifi<br/>true]
    C --> D[.bluetooth<br/>true]
    D --> E[.liquidCooling<br/>true]
    E --> F[.powerSupply<br/>750]
    F --> G[.operatingSystem<br/>"Windows 11"]
    G --> H[.build<br/>Computer]
    
    style A fill:#ffecb3
    style H fill:#c8e6c9
```

## 📊 Builder Pattern Components

```mermaid
mindmap
  root((Builder Pattern))
    Product
      Computer
        Required Fields
          CPU
          RAM
          Storage
        Optional Fields
          GPU
          Bluetooth
          WiFi
          Operating System
          Case Color
          Liquid Cooling
          Power Supply
          Motherboard
    
    Builder
      ComputerBuilder
        Constructor
          Required Parameters
        Setter Methods
          Optional Parameters
          Method Chaining
        Presets
          Gaming
          Office
          Workstation
        Build Method
          Creates Product
    
    Director
      ComputerDirector
        Gaming Computer
        Office Computer
        Workstation
        Budget Computer
        Custom Computer
        
    Client Usage
      Direct Building
      Director Building
      Preset Usage
      Store Integration
```

## 🎯 Construction Strategies Comparison

```mermaid
graph TB
    subgraph "Construction Approaches"
        A[Direct Constructor<br/>❌ Too many parameters]
        B[Telescoping Constructor<br/>❌ Multiple overloads]
        C[JavaBean Pattern<br/>❌ Mutable, inconsistent state]
        D[Builder Pattern<br/>✅ Flexible, immutable, readable]
    end
    
    subgraph "Builder Benefits"
        E[Method Chaining<br/>🔗]
        F[Preset Configurations<br/>⚙️]
        G[Validation<br/>✅]
        H[Immutability<br/>🔒]
    end
    
    D --> E
    D --> F
    D --> G
    D --> H
    
    style A fill:#ffcdd2
    style B fill:#ffcdd2
    style C fill:#fff3e0
    style D fill:#c8e6c9
```

## 🔄 Builder Lifecycle

```mermaid
stateDiagram-v2
    [*] --> Created: new ComputerBuilder(required params)
    Created --> Configuring: add optional components
    Configuring --> Configuring: method chaining
    Configuring --> PresetApplied: apply preset
    PresetApplied --> Configuring: override preset settings
    Configuring --> Built: build()
    PresetApplied --> Built: build()
    Built --> [*]: Computer instance created
    
    note right of Created: CPU, RAM, Storage required
    note right of Configuring: GPU, WiFi, Bluetooth, etc.
    note right of PresetApplied: Gaming, Office, Workstation
    note right of Built: Immutable Computer object
```

## 💰 Price Calculation Flow

```mermaid
flowchart TD
    A[Computer Configuration] --> B[Base Price: $500]
    B --> C{CPU Type}
    C -->|i9/Ryzen 9| D[+$400]
    C -->|i7/Ryzen 7| E[+$300]
    C -->|i5/Ryzen 5| F[+$200]
    C -->|Other| G[+$0]
    
    D --> H[Calculate RAM: $5/GB]
    E --> H
    F --> H
    G --> H
    
    H --> I[Calculate Storage: $0.1/GB]
    I --> J{GPU Type}
    J -->|RTX 4090| K[+$1500]
    J -->|RTX 4080| L[+$1200]
    J -->|RTX 4070| M[+$800]
    J -->|Other/None| N[+$0]
    
    K --> O[Add Optional Components]
    L --> O
    M --> O
    N --> O
    
    O --> P{Liquid Cooling?}
    P -->|Yes| Q[+$150]
    P -->|No| R[+$0]
    
    Q --> S[Final Price]
    R --> S
```

## 🔗 Pattern Relationships

```mermaid
graph LR
    subgraph "Creational Patterns"
        A[Builder]
        B[Factory]
        C[Abstract Factory]
        D[Singleton]
        E[Prototype]
    end
    
    A -.->|Complex Construction| B
    A -.->|Step-by-step| C
    B -.->|Simple Creation| A
    A -.->|Immutable Objects| D
    A -.->|Configuration| E
    
    style A fill:#4caf50
```

## 💡 Key Design Insights

### 1. **Required vs Optional Parameters**
```
Constructor: ComputerBuilder(cpu, ram, storage)  // Required
Methods: .gpu(), .wifi(), .bluetooth()           // Optional
```

### 2. **Fluent Interface Pattern**
```
Each method returns 'this' → Method chaining possible
```

### 3. **Preset Strategy**
```
Preset methods group related configurations
gamingPreset() = gpu + cooling + power + connectivity
```

---

*Visual diagrams demonstrate how the Builder pattern handles complex object construction with flexibility and readability.* 