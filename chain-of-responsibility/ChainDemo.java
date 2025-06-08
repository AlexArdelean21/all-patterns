// Abstract handler class
abstract class SupportHandler {
    protected SupportHandler nextHandler;
    protected String handlerName;
    protected int maxTicketLevel;
    
    public SupportHandler(String name, int maxLevel) {
        this.handlerName = name;
        this.maxTicketLevel = maxLevel;
    }
    
    public void setNext(SupportHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
    
    public void handleRequest(SupportTicket ticket) {
        if (canHandle(ticket)) {
            processTicket(ticket);
        } else if (nextHandler != null) {
            System.out.println(handlerName + " cannot handle ticket #" + ticket.getId() + 
                             " (Level " + ticket.getPriority() + "). Forwarding...");
            nextHandler.handleRequest(ticket);
        } else {
            System.out.println("❌ No handler available for ticket #" + ticket.getId() + 
                             " (Level " + ticket.getPriority() + ")");
        }
    }
    
    protected boolean canHandle(SupportTicket ticket) {
        return ticket.getPriority() <= maxTicketLevel;
    }
    
    protected abstract void processTicket(SupportTicket ticket);
}

// Request object
class SupportTicket {
    private int id;
    private String description;
    private int priority; // 1-5: 1=Low, 2=Medium, 3=High, 4=Critical, 5=Emergency
    private String customerName;
    private String category;
    private boolean resolved;
    
    public SupportTicket(int id, String description, int priority, String customerName, String category) {
        this.id = id;
        this.description = description;
        this.priority = priority;
        this.customerName = customerName;
        this.category = category;
        this.resolved = false;
    }
    
    // Getters and setters
    public int getId() { return id; }
    public String getDescription() { return description; }
    public int getPriority() { return priority; }
    public String getCustomerName() { return customerName; }
    public String getCategory() { return category; }
    public boolean isResolved() { return resolved; }
    public void setResolved(boolean resolved) { this.resolved = resolved; }
    
    public String getPriorityString() {
        switch (priority) {
            case 1: return "Low";
            case 2: return "Medium";
            case 3: return "High";
            case 4: return "Critical";
            case 5: return "Emergency";
            default: return "Unknown";
        }
    }
    
    @Override
    public String toString() {
        return "Ticket #" + id + " [" + getPriorityString() + "] " + 
               description + " (Customer: " + customerName + ", Category: " + category + ")";
    }
}

// Concrete handlers
class Level1Support extends SupportHandler {
    public Level1Support() {
        super("Level 1 Support (Basic)", 2);
    }
    
    @Override
    protected void processTicket(SupportTicket ticket) {
        System.out.println("🎯 " + handlerName + " handling ticket #" + ticket.getId());
        System.out.println("   Checking knowledge base...");
        System.out.println("   Providing standard solution...");
        
        // Simulate processing time
        try { Thread.sleep(500); } catch (InterruptedException e) {}
        
        ticket.setResolved(true);
        System.out.println("✅ Ticket #" + ticket.getId() + " resolved by " + handlerName);
    }
}

class Level2Support extends SupportHandler {
    public Level2Support() {
        super("Level 2 Support (Technical)", 3);
    }
    
    @Override
    protected void processTicket(SupportTicket ticket) {
        System.out.println("🔧 " + handlerName + " handling ticket #" + ticket.getId());
        System.out.println("   Performing technical analysis...");
        System.out.println("   Checking system logs...");
        System.out.println("   Applying technical solution...");
        
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        
        ticket.setResolved(true);
        System.out.println("✅ Ticket #" + ticket.getId() + " resolved by " + handlerName);
    }
}

class Level3Support extends SupportHandler {
    public Level3Support() {
        super("Level 3 Support (Expert)", 4);
    }
    
    @Override
    protected void processTicket(SupportTicket ticket) {
        System.out.println("🚀 " + handlerName + " handling ticket #" + ticket.getId());
        System.out.println("   Escalating to expert team...");
        System.out.println("   Performing deep system analysis...");
        System.out.println("   Implementing custom solution...");
        
        try { Thread.sleep(1500); } catch (InterruptedException e) {}
        
        ticket.setResolved(true);
        System.out.println("✅ Ticket #" + ticket.getId() + " resolved by " + handlerName);
    }
}

class ManagerSupport extends SupportHandler {
    public ManagerSupport() {
        super("Manager Support (Emergency)", 5);
    }
    
    @Override
    protected void processTicket(SupportTicket ticket) {
        System.out.println("🚨 " + handlerName + " handling ticket #" + ticket.getId());
        System.out.println("   Emergency protocol activated...");
        System.out.println("   Involving development team...");
        System.out.println("   Direct customer communication...");
        
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
        
        ticket.setResolved(true);
        System.out.println("✅ Ticket #" + ticket.getId() + " resolved by " + handlerName);
    }
}

// Support system using the chain
class SupportSystem {
    private SupportHandler firstHandler;
    private java.util.List<SupportTicket> tickets;
    private int nextTicketId;
    
    public SupportSystem() {
        setupChain();
        this.tickets = new java.util.ArrayList<>();
        this.nextTicketId = 1001;
    }
    
    private void setupChain() {
        // Create handlers
        SupportHandler level1 = new Level1Support();
        SupportHandler level2 = new Level2Support();
        SupportHandler level3 = new Level3Support();
        SupportHandler manager = new ManagerSupport();
        
        // Chain them together
        level1.setNext(level2);
        level2.setNext(level3);
        level3.setNext(manager);
        
        firstHandler = level1;
    }
    
    public SupportTicket createTicket(String description, int priority, String customerName, String category) {
        SupportTicket ticket = new SupportTicket(nextTicketId++, description, priority, customerName, category);
        tickets.add(ticket);
        System.out.println("📝 New ticket created: " + ticket);
        return ticket;
    }
    
    public void processTicket(SupportTicket ticket) {
        System.out.println("\n🔄 Processing " + ticket);
        firstHandler.handleRequest(ticket);
    }
    
    public void processAllTickets() {
        System.out.println("\n=== Processing All Pending Tickets ===");
        for (SupportTicket ticket : tickets) {
            if (!ticket.isResolved()) {
                processTicket(ticket);
                System.out.println();
            }
        }
    }
    
    public void showTicketStats() {
        System.out.println("\n=== Ticket Statistics ===");
        int resolved = 0, pending = 0;
        for (SupportTicket ticket : tickets) {
            if (ticket.isResolved()) resolved++;
            else pending++;
        }
        System.out.println("Total tickets: " + tickets.size());
        System.out.println("Resolved: " + resolved);
        System.out.println("Pending: " + pending);
    }
}

// Alternative chain example - Expense approval system
abstract class ExpenseApprover {
    protected ExpenseApprover successor;
    protected String title;
    protected double approvalLimit;
    
    public ExpenseApprover(String title, double limit) {
        this.title = title;
        this.approvalLimit = limit;
    }
    
    public void setSuccessor(ExpenseApprover successor) {
        this.successor = successor;
    }
    
    public void approveExpense(double amount, String description) {
        if (amount <= approvalLimit) {
            System.out.println("✅ " + title + " approved $" + String.format("%.2f", amount) + 
                             " for: " + description);
        } else if (successor != null) {
            System.out.println("⬆️  " + title + " cannot approve $" + String.format("%.2f", amount) + 
                             ". Forwarding to " + successor.title);
            successor.approveExpense(amount, description);
        } else {
            System.out.println("❌ No one can approve $" + String.format("%.2f", amount) + 
                             " for: " + description);
        }
    }
}

class TeamLead extends ExpenseApprover {
    public TeamLead() { super("Team Lead", 500); }
}

class Manager extends ExpenseApprover {
    public Manager() { super("Manager", 2000); }
}

class Director extends ExpenseApprover {
    public Director() { super("Director", 10000); }
}

class CEO extends ExpenseApprover {
    public CEO() { super("CEO", 50000); }
}

public class ChainDemo {
    public static void main(String[] args) {
        System.out.println("=== Chain of Responsibility Pattern Demo ===\n");
        
        // 1. Support System Demo
        System.out.println("1. Support Ticket System:");
        SupportSystem supportSystem = new SupportSystem();
        
        // Create various tickets
        supportSystem.createTicket("Password reset needed", 1, "John Doe", "Account");
        supportSystem.createTicket("Email not working", 2, "Jane Smith", "Email");
        supportSystem.createTicket("Database connection issues", 3, "Bob Johnson", "Database");
        supportSystem.createTicket("Server down - production impact", 4, "Alice Wilson", "Infrastructure");
        supportSystem.createTicket("Security breach - immediate action needed", 5, "Emergency Contact", "Security");
        
        // Process all tickets
        supportSystem.processAllTickets();
        supportSystem.showTicketStats();
        
        // 2. Expense Approval Demo
        System.out.println("\n2. Expense Approval System:");
        
        // Setup approval chain
        ExpenseApprover teamLead = new TeamLead();
        ExpenseApprover manager = new Manager();
        ExpenseApprover director = new Director();
        ExpenseApprover ceo = new CEO();
        
        teamLead.setSuccessor(manager);
        manager.setSuccessor(director);
        director.setSuccessor(ceo);
        
        // Test different expense amounts
        double[] expenses = {250, 750, 1500, 5000, 15000, 75000};
        String[] descriptions = {
            "Office supplies",
            "Team lunch",
            "Software license",
            "Conference attendance",
            "New equipment purchase",
            "Major infrastructure upgrade"
        };
        
        for (int i = 0; i < expenses.length; i++) {
            System.out.println("\nRequesting approval:");
            teamLead.approveExpense(expenses[i], descriptions[i]);
        }
        
        // 3. Dynamic Chain Building
        System.out.println("\n3. Dynamic Chain Building:");
        SupportHandler customChain = new Level2Support();
        customChain.setNext(new ManagerSupport());
        
        SupportTicket urgentTicket = new SupportTicket(9999, "Critical system failure", 4, 
                                                      "Important Customer", "System");
        
        System.out.println("Using custom chain (skips Level 1):");
        customChain.handleRequest(urgentTicket);
        
        System.out.println("\n=== Demo Complete ===");
    }
} 