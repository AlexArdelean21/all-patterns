# Chain of Responsibility Pattern

## 🎯 Intent
The Chain of Responsibility pattern passes requests along a chain of handlers. Upon receiving a request, each handler decides either to process the request or to pass it to the next handler in the chain. It avoids coupling the sender of a request to its receiver by giving multiple objects a chance to handle the request.

## 🏗️ Structure

### Key Components:
- **Handler**: Abstract class defining interface for handling requests and optional link to successor
- **Concrete Handler**: Handles requests it's responsible for; forwards others to successor
- **Client**: Initiates request to a ConcreteHandler object in the chain

### Class Diagram:
```
SupportHandler (Abstract Handler)
├── - nextHandler: SupportHandler
├── - handlerName: String
├── - maxTicketLevel: int
├── + setNext(SupportHandler): void
├── + handleRequest(SupportTicket): void
├── + canHandle(SupportTicket): boolean
└── + processTicket(SupportTicket): void (abstract)

Concrete Handlers:
├── Level1Support (extends SupportHandler)
├── Level2Support (extends SupportHandler)  
├── Level3Support (extends SupportHandler)
└── ManagerSupport (extends SupportHandler)

SupportTicket (Request)
├── - id: int
├── - priority: int
├── - description: String
└── - customerName: String

SupportSystem (Client)
├── - firstHandler: SupportHandler
└── + processTicket(SupportTicket): void
```

## 💡 When to Use

### ✅ Use Chain of Responsibility When:
- More than one object can handle a request
- You want to issue a request without specifying the receiver explicitly
- The set of objects that can handle a request should be specified dynamically
- You want to avoid coupling the sender to the receiver
- Handlers need to be added or changed at runtime

### ❌ Avoid When:
- Only one object can handle the request
- Every request must be handled (chain might not guarantee handling)
- Performance is critical (chain traversal has overhead)

## 🔍 Real-World Examples

### From the Demo:
```java
// Create support system with chain
SupportSystem support = new SupportSystem();

// Create tickets with different priorities
SupportTicket lowPriority = support.createTicket("Password reset", 1, "John Doe", "Account");
SupportTicket highPriority = support.createTicket("Server down", 4, "Jane Smith", "Infrastructure");

// Process tickets - automatically routed to appropriate handler
support.processTicket(lowPriority);  // Handled by Level 1
support.processTicket(highPriority); // Escalated to Level 3

// Expense approval chain
ExpenseApprover teamLead = new TeamLead();
ExpenseApprover manager = new Manager();
teamLead.setSuccessor(manager);
teamLead.approveExpense(300, "Office supplies");  // Approved by Team Lead
teamLead.approveExpense(1500, "Conference travel"); // Escalated to Manager
```

### Common Use Cases:
- **Help Desk Systems**: Ticket routing based on complexity/priority
- **Expense Approval**: Financial approval workflows
- **Web Request Processing**: Middleware chains in web frameworks
- **Event Handling**: GUI event propagation through widget hierarchy
- **Security Filters**: Authentication and authorization chains
- **Logging Systems**: Multiple log handlers with different levels

## 🎪 Demo Walkthrough

The `ChainDemo.java` demonstrates:

1. **Support Ticket System**: Multi-level support chain with escalation
2. **Priority-Based Routing**: Tickets routed based on priority levels
3. **Expense Approval Chain**: Financial approval workflow
4. **Dynamic Chain Setup**: Handlers can be reconfigured
5. **Request Processing**: Automatic forwarding to appropriate handler

### Key Features:
- Multiple handler levels with different capabilities
- Automatic escalation based on request complexity
- Chain reconfiguration support
- Request tracking and statistics
- Realistic business scenarios

## 🚀 Running the Demo

```bash
# Compile and run
javac ChainDemo.java
java ChainDemo
```

**Expected Output:**
- Support ticket creation and routing
- Handler-specific processing messages
- Escalation demonstrations
- Expense approval workflows

## 🔄 Variations

### 1. Linear Chain
- Simple sequential chain
- Each handler tries in order
- Most common implementation

### 2. Tree Chain
- Handlers form tree structure
- Multiple paths possible
- More complex routing logic

### 3. Composite Chain
- Handlers can contain sub-chains
- Hierarchical processing
- Complex workflow support

## 💭 Benefits & Drawbacks

### ✅ Benefits:
- **Decoupling**: Sender doesn't need to know which handler will process request
- **Flexibility**: Chain can be modified at runtime
- **Single Responsibility**: Each handler focuses on specific type of request
- **Open/Closed Principle**: Easy to add new handlers without modifying existing code
- **Dynamic Configuration**: Chain structure can be configured dynamically

### ❌ Drawbacks:
- **No Guarantee**: Request might not be handled if no suitable handler exists
- **Performance**: May need to traverse entire chain
- **Debugging**: Harder to track request flow through chain
- **Runtime Configuration**: Chain setup can be complex

## 🔧 Implementation Best Practices

### 1. **Abstract Handler Design**
```java
abstract class Handler {
    protected Handler successor;
    
    public void setSuccessor(Handler successor) {
        this.successor = successor;
    }
    
    public void handleRequest(Request request) {
        if (canHandle(request)) {
            process(request);
        } else if (successor != null) {
            successor.handleRequest(request);
        }
    }
    
    protected abstract boolean canHandle(Request request);
    protected abstract void process(Request request);
}
```

### 2. **Request Object**
```java
class Request {
    private int priority;
    private String type;
    private Object data;
    
    // Include all necessary information for handlers
    // to make decisions about handling
}
```

### 3. **Chain Builder**
```java
class ChainBuilder {
    public static Handler buildSupportChain() {
        Handler level1 = new Level1Support();
        Handler level2 = new Level2Support();
        Handler level3 = new Level3Support();
        
        level1.setSuccessor(level2);
        level2.setSuccessor(level3);
        
        return level1;
    }
}
```

### 4. **Error Handling**
```java
public void handleRequest(Request request) {
    if (canHandle(request)) {
        try {
            process(request);
        } catch (Exception e) {
            // Log error and optionally pass to next handler
            if (successor != null) {
                successor.handleRequest(request);
            }
        }
    } else if (successor != null) {
        successor.handleRequest(request);
    } else {
        handleUnprocessedRequest(request);
    }
}
```

## 🎯 Key Takeaways

1. **Request Routing**: Automatically routes requests to appropriate handlers
2. **Loose Coupling**: Sender doesn't know which specific handler will process request
3. **Chain Flexibility**: Easy to add, remove, or reorder handlers
4. **Responsibility Distribution**: Each handler has clear, focused responsibility
5. **Escalation Support**: Natural support for escalation scenarios
6. **Runtime Configuration**: Chain structure can be modified dynamically

## 🔗 Related Patterns

- **Command**: Can be used to implement requests as objects
- **Composite**: Chain handlers can form composite structures
- **Decorator**: Similar chaining concept but for adding behavior
- **Strategy**: Alternative when you know which handler to use

---

*The Chain of Responsibility pattern is essential for building flexible request processing systems where multiple handlers might be able to process a request.* 