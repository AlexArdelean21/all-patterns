# Decorator Pattern - Visual Diagrams

## 1. Class Diagram

```mermaid
classDiagram
    class Coffee {
        <<interface>>
        +getDescription() String
        +getCost() double
    }
    
    class BasicCoffee {
        +getDescription() String
        +getCost() double
    }
    
    class EspressoCoffee {
        +getDescription() String
        +getCost() double
    }
    
    class CoffeeDecorator {
        <<abstract>>
        #coffee: Coffee
        +CoffeeDecorator(coffee: Coffee)
        +getDescription() String
        +getCost() double
    }
    
    class MilkDecorator {
        +MilkDecorator(coffee: Coffee)
        +getDescription() String
        +getCost() double
    }
    
    class SugarDecorator {
        +SugarDecorator(coffee: Coffee)
        +getDescription() String
        +getCost() double
    }
    
    class WhipDecorator {
        +WhipDecorator(coffee: Coffee)
        +getDescription() String
        +getCost() double
    }
    
    class VanillaDecorator {
        +VanillaDecorator(coffee: Coffee)
        +getDescription() String
        +getCost() double
    }
    
    class CaramelDecorator {
        +CaramelDecorator(coffee: Coffee)
        +getDescription() String
        +getCost() double
    }
    
    Coffee <|.. BasicCoffee
    Coffee <|.. EspressoCoffee
    Coffee <|.. CoffeeDecorator
    CoffeeDecorator <|-- MilkDecorator
    CoffeeDecorator <|-- SugarDecorator
    CoffeeDecorator <|-- WhipDecorator
    CoffeeDecorator <|-- VanillaDecorator
    CoffeeDecorator <|-- CaramelDecorator
    CoffeeDecorator o-- Coffee : wraps
```

## 2. Object Composition Diagram

```mermaid
graph TD
    A["BasicCoffee<br/>$2.00"] --> B["MilkDecorator<br/>+$0.50"]
    B --> C["SugarDecorator<br/>+$0.20"]
    C --> D["VanillaDecorator<br/>+$0.60"]
    D --> E["WhipDecorator<br/>+$0.70"]
    
    F["Final Coffee:<br/>Basic Coffee, Milk, Sugar, Vanilla, Whipped Cream<br/>Total: $4.00"]
    E --> F
    
    style A fill:#e1f5fe
    style B fill:#f3e5f5
    style C fill:#f3e5f5
    style D fill:#f3e5f5
    style E fill:#f3e5f5
    style F fill:#e8f5e8
```

## 3. Decorator Pattern Structure

```mermaid
graph LR
    subgraph "Component Interface"
        CI[Coffee Interface<br/>- getDescription()<br/>- getCost()]
    end
    
    subgraph "Concrete Components"
        CC1[BasicCoffee<br/>Basic Coffee - $2.00]
        CC2[EspressoCoffee<br/>Espresso - $3.50]
    end
    
    subgraph "Base Decorator"
        BD[CoffeeDecorator<br/>- coffee: Coffee<br/>+ delegates to wrapped object]
    end
    
    subgraph "Concrete Decorators"
        CD1[MilkDecorator<br/>+$0.50]
        CD2[SugarDecorator<br/>+$0.20]
        CD3[WhipDecorator<br/>+$0.70]
        CD4[VanillaDecorator<br/>+$0.60]
        CD5[CaramelDecorator<br/>+$0.65]
    end
    
    CI --> CC1
    CI --> CC2
    CI --> BD
    BD --> CD1
    BD --> CD2
    BD --> CD3
    BD --> CD4
    BD --> CD5
    
    style CI fill:#ffeb3b
    style BD fill:#ff9800
    style CC1 fill:#4caf50
    style CC2 fill:#4caf50
```

## 4. Sequence Diagram - Creating Decorated Coffee

```mermaid
sequenceDiagram
    participant Client
    participant BasicCoffee
    participant MilkDecorator
    participant SugarDecorator
    participant WhipDecorator
    
    Client->>BasicCoffee: new BasicCoffee()
    Client->>MilkDecorator: new MilkDecorator(basicCoffee)
    Client->>SugarDecorator: new SugarDecorator(milkDecorator)
    Client->>WhipDecorator: new WhipDecorator(sugarDecorator)
    
    Note over Client,WhipDecorator: Getting description and cost
    
    Client->>WhipDecorator: getDescription()
    WhipDecorator->>SugarDecorator: getDescription()
    SugarDecorator->>MilkDecorator: getDescription()
    MilkDecorator->>BasicCoffee: getDescription()
    BasicCoffee-->>MilkDecorator: "Basic Coffee"
    MilkDecorator-->>SugarDecorator: "Basic Coffee, Milk"
    SugarDecorator-->>WhipDecorator: "Basic Coffee, Milk, Sugar"
    WhipDecorator-->>Client: "Basic Coffee, Milk, Sugar, Whipped Cream"
    
    Client->>WhipDecorator: getCost()
    WhipDecorator->>SugarDecorator: getCost()
    SugarDecorator->>MilkDecorator: getCost()
    MilkDecorator->>BasicCoffee: getCost()
    BasicCoffee-->>MilkDecorator: 2.00
    MilkDecorator-->>SugarDecorator: 2.50
    SugarDecorator-->>WhipDecorator: 2.70
    WhipDecorator-->>Client: 3.40
```

## 5. Runtime Decoration Process

```mermaid
flowchart TD
    Start([Start: Create Coffee]) --> Base{Choose Base}
    Base -->|Option 1| Basic[BasicCoffee<br/>$2.00]
    Base -->|Option 2| Espresso[EspressoCoffee<br/>$3.50]
    
    Basic --> Decorate{Add Decorations?}
    Espresso --> Decorate
    
    Decorate -->|Yes| Choose{Choose Decorator}
    Decorate -->|No| End([Final Coffee])
    
    Choose -->|Milk| Milk[Add MilkDecorator<br/>+$0.50]
    Choose -->|Sugar| Sugar[Add SugarDecorator<br/>+$0.20]
    Choose -->|Whip| Whip[Add WhipDecorator<br/>+$0.70]
    Choose -->|Vanilla| Vanilla[Add VanillaDecorator<br/>+$0.60]
    Choose -->|Caramel| Caramel[Add CaramelDecorator<br/>+$0.65]
    
    Milk --> Decorate
    Sugar --> Decorate
    Whip --> Decorate
    Vanilla --> Decorate
    Caramel --> Decorate
    
    End --> Display[Display:<br/>Description + Total Cost]
    
    style Start fill:#e3f2fd
    style End fill:#e8f5e8
    style Display fill:#fff3e0
```

## 6. Memory Structure Visualization

```mermaid
graph TB
    subgraph "Memory Layout"
        subgraph "Outermost Decorator"
            WD[WhipDecorator<br/>cost: +0.70<br/>description: +Whipped Cream]
        end
        
        subgraph "Middle Decorator"
            SD[SugarDecorator<br/>cost: +0.20<br/>description: +Sugar]
        end
        
        subgraph "Inner Decorator"
            MD[MilkDecorator<br/>cost: +0.50<br/>description: +Milk]
        end
        
        subgraph "Core Component"
            BC[BasicCoffee<br/>cost: 2.00<br/>description: Basic Coffee]
        end
    end
    
    WD -->|wraps| SD
    SD -->|wraps| MD
    MD -->|wraps| BC
    
    subgraph "Method Call Chain"
        C1[getCost(): 3.40] --> C2[getCost(): 2.70]
        C2 --> C3[getCost(): 2.50]
        C3 --> C4[getCost(): 2.00]
        
        D1[getDescription():<br/>Basic Coffee, Milk, Sugar, Whipped Cream] --> D2[getDescription():<br/>Basic Coffee, Milk, Sugar]
        D2 --> D3[getDescription():<br/>Basic Coffee, Milk]
        D3 --> D4[getDescription():<br/>Basic Coffee]
    end
    
    style WD fill:#ffcdd2
    style SD fill:#f8bbd9
    style MD fill:#e1bee7
    style BC fill:#c8e6c9
```

## Key Diagram Insights

1. **Layered Structure**: Each decorator wraps the previous component, creating a nested structure
2. **Interface Consistency**: All decorators implement the same Coffee interface
3. **Delegation Chain**: Method calls are delegated through the decorator chain to the core component
4. **Runtime Composition**: Decorators can be combined in any order and quantity
5. **Transparent Enhancement**: Clients interact with decorated objects the same way as base components
6. **Cost Accumulation**: Each decorator adds its cost to the wrapped component's cost
7. **Description Building**: Each decorator appends its description to the wrapped component's description 