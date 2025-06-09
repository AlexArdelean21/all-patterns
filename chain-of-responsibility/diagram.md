# Chain of Responsibility Pattern - Visual Diagrams

## 🏗️ UML Class Diagram

```mermaid
classDiagram
    class SupportHandler {
        <<abstract>>
        #SupportHandler nextHandler
        #String handlerName
        #int maxTicketLevel
        
        +SupportHandler(String name, int maxLevel)
        +setNext(SupportHandler nextHandler) void
        +handleRequest(SupportTicket ticket) void
        #canHandle(SupportTicket ticket) boolean
        #processTicket(SupportTicket ticket)* void
    }
    
    class Level1Support {
        +Level1Support()
        #processTicket(SupportTicket ticket) void
    }
    
    class Level2Support {
        +Level2Support()
        #processTicket(SupportTicket ticket) void
    }
    
    class Level3Support {
        +Level3Support()
        #processTicket(SupportTicket ticket) void
    }
    
    class ManagerSupport {
        +ManagerSupport()
        #processTicket(SupportTicket ticket) void
    }
    
    class SupportTicket {
        -int id
        -String description
        -int priority
        -String customerName
        -String category
        -boolean resolved
        
        +SupportTicket(int id, String description, int priority, String customerName, String category)
        +getId() int
        +getPriority() int
        +getDescription() String
        +getPriorityString() String
        +isResolved() boolean
        +setResolved(boolean resolved) void
    }
    
    class SupportSystem {
        -SupportHandler firstHandler
        -List~SupportTicket~ tickets
        -int nextTicketId
        
        +SupportSystem()
        -setupChain() void
        +createTicket(String description, int priority, String customerName, String category) SupportTicket
        +processTicket(SupportTicket ticket) void
        +processAllTickets() void
        +showTicketStats() void
    }
    
    class ExpenseApprover {
        <<abstract>>
        #ExpenseApprover successor
        #String title
        #double approvalLimit
        
        +ExpenseApprover(String title, double limit)
        +setSuccessor(ExpenseApprover successor) void
        +approveExpense(double amount, String description) void
    }
    
    SupportHandler <|-- Level1Support
    SupportHandler <|-- Level2Support
    SupportHandler <|-- Level3Support
    SupportHandler <|-- ManagerSupport
    SupportHandler --> SupportHandler : nextHandler
    SupportSystem --> SupportHandler : uses
    SupportSystem --> SupportTicket : manages
    SupportHandler --> SupportTicket : processes
```

## 🔄 Sequence Diagram - Support Ticket Processing

```mermaid
sequenceDiagram
    participant Client
    participant SupportSystem
    participant Level1Support
    participant Level2Support
    participant Level3Support
    participant SupportTicket
    
    Note over Client, SupportTicket: High Priority Ticket Creation
    Client->>SupportSystem: createTicket("Server down", 4, "Jane Smith", "Infrastructure")
    SupportSystem->>SupportTicket: new SupportTicket(...)
    SupportTicket-->>SupportSystem: ticket instance
    SupportSystem-->>Client: high priority ticket
    
    Note over Client, SupportTicket: Ticket Processing Chain
    Client->>SupportSystem: processTicket(ticket)
    SupportSystem->>Level1Support: handleRequest(ticket)
    Level1Support->>Level1Support: canHandle(ticket)?
    Level1Support-->>Level1Support: false (priority 4 > maxLevel 2)
    Level1Support->>Level2Support: handleRequest(ticket)
    Level2Support->>Level2Support: canHandle(ticket)?
    Level2Support-->>Level2Support: false (priority 4 > maxLevel 3)
    Level2Support->>Level3Support: handleRequest(ticket)
    Level3Support->>Level3Support: canHandle(ticket)?
    Level3Support-->>Level3Support: true (priority 4 <= maxLevel 4)
    Level3Support->>Level3Support: processTicket(ticket)
    Level3Support->>SupportTicket: setResolved(true)
    Level3Support-->>SupportSystem: ticket resolved
    SupportSystem-->>Client: processing complete
```

## 🎯 Chain Request Flow

```mermaid
flowchart TD
    A[Client Request] --> B[First Handler<br/>Level 1 Support]
    B --> C{Can Handle?<br/>Priority ≤ 2}
    C -->|Yes| D[Process Request<br/>✅ Resolve Ticket]
    C -->|No| E[Forward to Next<br/>Level 2 Support]
    
    E --> F{Can Handle?<br/>Priority ≤ 3}
    F -->|Yes| G[Process Request<br/>🔧 Technical Solution]
    F -->|No| H[Forward to Next<br/>Level 3 Support]
    
    H --> I{Can Handle?<br/>Priority ≤ 4}
    I -->|Yes| J[Process Request<br/>🚀 Expert Analysis]
    I -->|No| K[Forward to Next<br/>Manager Support]
    
    K --> L{Can Handle?<br/>Priority ≤ 5}
    L -->|Yes| M[Process Request<br/>🚨 Emergency Protocol]
    L -->|No| N[❌ No Handler Available]
    
    style D fill:#c8e6c9
    style G fill:#c8e6c9
    style J fill:#c8e6c9
    style M fill:#c8e6c9
    style N fill:#ffcdd2
```

## 📊 Chain of Responsibility Structure

```mermaid
mindmap
  root((Chain of Responsibility))
    Abstract Handler
      Request Interface
        handleRequest()
        canHandle()
        processTicket()
      Chain Management
        nextHandler reference
        setNext() method
        Forward to successor
      
    Concrete Handlers
      Level1Support
        Basic issues
        Priority ≤ 2
        Standard solutions
      Level2Support
        Technical issues
        Priority ≤ 3
        System analysis
      Level3Support
        Expert issues
        Priority ≤ 4
        Custom solutions
      ManagerSupport
        Emergency issues
        Priority ≤ 5
        Executive involvement
    
    Request Object
      SupportTicket
        Priority level
        Description
        Customer info
        Category
        Resolution status
    
    Client System
      SupportSystem
        Chain setup
        Ticket creation
        Request routing
        Statistics tracking
```

## 🔗 Chain Configuration Patterns

```mermaid
graph TB
    subgraph "Linear Chain (Standard)"
        A1[Level 1] --> A2[Level 2]
        A2 --> A3[Level 3]
        A3 --> A4[Manager]
    end
    
    subgraph "Branching Chain"
        B1[Front Desk] --> B2{Request Type}
        B2 -->|Technical| B3[Technical Team]
        B2 -->|Billing| B4[Billing Team]
        B2 -->|Sales| B5[Sales Team]
    end
    
    subgraph "Hierarchical Chain"
        C1[Team Lead] --> C2[Manager]
        C1 --> C3[Senior Dev]
        C2 --> C4[Director]
        C3 --> C2
    end
    
    style A1 fill:#e1f5fe
    style B1 fill:#fff3e0
    style C1 fill:#f3e5f5
```

## 💰 Expense Approval Chain

```mermaid
flowchart LR
    A[Employee Request] --> B[Team Lead<br/>$0 - $500]
    B --> C{Amount ≤ $500?}
    C -->|Yes| D[✅ Approved by Team Lead]
    C -->|No| E[Manager<br/>$501 - $2,000]
    
    E --> F{Amount ≤ $2,000?}
    F -->|Yes| G[✅ Approved by Manager]
    F -->|No| H[Director<br/>$2,001 - $10,000]
    
    H --> I{Amount ≤ $10,000?}
    I -->|Yes| J[✅ Approved by Director]
    I -->|No| K[CEO<br/>$10,001 - $50,000]
    
    K --> L{Amount ≤ $50,000?}
    L -->|Yes| M[✅ Approved by CEO]
    L -->|No| N[❌ Board Approval Required]
    
    style D fill:#c8e6c9
    style G fill:#c8e6c9
    style J fill:#c8e6c9
    style M fill:#c8e6c9
    style N fill:#ffcdd2
```

## 🔄 Handler State Management

```mermaid
stateDiagram-v2
    [*] --> Ready: Handler Created
    Ready --> Processing: Request Received
    Processing --> CanHandle: Evaluate Request
    CanHandle --> Handled: Process Request
    CanHandle --> Forwarded: Pass to Next Handler
    Handled --> Ready: Complete Processing
    Forwarded --> Ready: Request Forwarded
    Ready --> [*]: Handler Destroyed
    
    note right of CanHandle: Decision based on<br/>handler capabilities<br/>and request priority
    note right of Handled: Request processed<br/>by this handler
    note right of Forwarded: Request passed to<br/>next handler in chain
```

## 📈 Performance Characteristics

```mermaid
graph LR
    subgraph "Chain Traversal Performance"
        A[Best Case<br/>O(1)]
        B[Average Case<br/>O(n/2)]
        C[Worst Case<br/>O(n)]
    end
    
    subgraph "Factors Affecting Performance"
        D[Chain Length<br/>📏]
        E[Handler Complexity<br/>⚙️]
        F[Request Distribution<br/>📊]
    end
    
    subgraph "Optimization Strategies"
        G[Priority Ordering<br/>📋]
        H[Caching Results<br/>💾]
        I[Handler Statistics<br/>📈]
    end
    
    A --> G
    B --> H
    C --> I
    
    D --> A
    E --> B
    F --> C
    
    style A fill:#c8e6c9
    style B fill:#fff3e0
    style C fill:#ffcdd2
```

## 🆚 Chain vs Other Patterns

```mermaid
graph TB
    subgraph "Chain of Responsibility"
        A[Sequential Processing]
        B[Dynamic Handler Selection]
        C[Request Forwarding]
    end
    
    subgraph "Command Pattern"
        D[Request Encapsulation]
        E[Undo/Redo Support]
        F[Request Queuing]
    end
    
    subgraph "Strategy Pattern"
        G[Algorithm Selection]
        H[Runtime Switching]
        I[Context Independence]
    end
    
    subgraph "Common Characteristics"
        J[Request Processing]
        K[Behavioral Patterns]
        L[Runtime Flexibility]
    end
    
    A --> J
    D --> K
    G --> L
    
    style A fill:#4caf50
    style D fill:#ff9800
    style G fill:#2196f3
```

## 🔧 Implementation Variations

```mermaid
classDiagram
    class FilterChain {
        -List~Filter~ filters
        +addFilter(Filter filter) void
        +doFilter(Request request, Response response) void
    }
    
    class MiddlewareChain {
        -Middleware first
        +use(Middleware middleware) void
        +handle(Request request) Response
    }
    
    class EventPropagationChain {
        -List~EventHandler~ handlers
        +addEventListener(EventHandler handler) void
        +propagateEvent(Event event) void
        +stopPropagation() void
    }
    
    class ValidationChain {
        -Validator first
        +addValidator(Validator validator) void
        +validate(Object data) ValidationResult
    }
    
    FilterChain --> Filter : manages
    MiddlewareChain --> Middleware : chains
    EventPropagationChain --> EventHandler : propagates
    ValidationChain --> Validator : validates
```

## 💡 Key Design Insights

### 1. **Handler Responsibility**
```
Each handler should have a single, clear responsibility
Handlers should be independent and loosely coupled
```

### 2. **Chain Termination**
```
Always provide a way to handle "unhandled" requests
Consider default handlers at the end of chain
```

### 3. **Request Information**
```
Request objects should contain all necessary information
Handlers shouldn't need external context to make decisions
```

---

*Visual diagrams illustrate how the Chain of Responsibility pattern enables flexible request processing through a series of loosely coupled handlers.* 