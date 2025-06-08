// Strategy interface
interface PaymentStrategy {
    boolean pay(double amount);
    String getPaymentMethod();
}

// Concrete Strategies
class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private String cardholderName;
    private String cvv;
    
    public CreditCardPayment(String cardNumber, String cardholderName, String cvv) {
        this.cardNumber = cardNumber;
        this.cardholderName = cardholderName;
        this.cvv = cvv;
    }
    
    @Override
    public boolean pay(double amount) {
        System.out.println("Processing credit card payment...");
        System.out.println("Card: **** **** **** " + cardNumber.substring(cardNumber.length() - 4));
        System.out.println("Cardholder: " + cardholderName);
        System.out.println("Amount: $" + String.format("%.2f", amount));
        
        // Simulate payment processing
        if (amount > 0 && amount <= 10000) {
            System.out.println("✅ Credit card payment successful!");
            return true;
        } else {
            System.out.println("❌ Credit card payment failed - Invalid amount!");
            return false;
        }
    }
    
    @Override
    public String getPaymentMethod() {
        return "Credit Card";
    }
}

class PayPalPayment implements PaymentStrategy {
    private String email;
    private String password;
    
    public PayPalPayment(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    @Override
    public boolean pay(double amount) {
        System.out.println("Processing PayPal payment...");
        System.out.println("Email: " + email);
        System.out.println("Amount: $" + String.format("%.2f", amount));
        
        // Simulate authentication and payment
        if (password.length() >= 6 && amount > 0) {
            System.out.println("✅ PayPal payment successful!");
            return true;
        } else {
            System.out.println("❌ PayPal payment failed - Authentication error!");
            return false;
        }
    }
    
    @Override
    public String getPaymentMethod() {
        return "PayPal";
    }
}

class BankTransferPayment implements PaymentStrategy {
    private String accountNumber;
    private String routingNumber;
    private String bankName;
    
    public BankTransferPayment(String accountNumber, String routingNumber, String bankName) {
        this.accountNumber = accountNumber;
        this.routingNumber = routingNumber;
        this.bankName = bankName;
    }
    
    @Override
    public boolean pay(double amount) {
        System.out.println("Processing bank transfer...");
        System.out.println("Bank: " + bankName);
        System.out.println("Account: *****" + accountNumber.substring(accountNumber.length() - 4));
        System.out.println("Routing: " + routingNumber);
        System.out.println("Amount: $" + String.format("%.2f", amount));
        
        // Simulate bank transfer processing (usually takes longer)
        try {
            Thread.sleep(1000); // Simulate processing time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        if (amount > 0 && amount >= 1.00) { // Bank transfers usually have minimum amounts
            System.out.println("✅ Bank transfer successful!");
            return true;
        } else {
            System.out.println("❌ Bank transfer failed - Minimum amount not met!");
            return false;
        }
    }
    
    @Override
    public String getPaymentMethod() {
        return "Bank Transfer";
    }
}

class CryptoPayment implements PaymentStrategy {
    private String walletAddress;
    private String cryptoType;
    
    public CryptoPayment(String walletAddress, String cryptoType) {
        this.walletAddress = walletAddress;
        this.cryptoType = cryptoType;
    }
    
    @Override
    public boolean pay(double amount) {
        System.out.println("Processing cryptocurrency payment...");
        System.out.println("Crypto: " + cryptoType);
        System.out.println("Wallet: " + walletAddress.substring(0, 6) + "..." + 
                          walletAddress.substring(walletAddress.length() - 6));
        System.out.println("Amount: $" + String.format("%.2f", amount) + " USD equivalent");
        
        // Simulate blockchain transaction
        double conversionRate = cryptoType.equals("Bitcoin") ? 45000 : 3000; // Simplified rates
        double cryptoAmount = amount / conversionRate;
        System.out.println("Crypto amount: " + String.format("%.8f", cryptoAmount) + " " + cryptoType);
        
        if (amount > 0) {
            System.out.println("✅ Cryptocurrency payment successful!");
            return true;
        } else {
            System.out.println("❌ Cryptocurrency payment failed!");
            return false;
        }
    }
    
    @Override
    public String getPaymentMethod() {
        return "Cryptocurrency (" + cryptoType + ")";
    }
}

// Context class
class ShoppingCart {
    private java.util.List<String> items;
    private double totalAmount;
    private PaymentStrategy paymentStrategy;
    
    public ShoppingCart() {
        this.items = new java.util.ArrayList<>();
        this.totalAmount = 0.0;
    }
    
    public void addItem(String item, double price) {
        items.add(item);
        totalAmount += price;
        System.out.println("Added: " + item + " - $" + String.format("%.2f", price));
    }
    
    public void removeItem(String item, double price) {
        if (items.remove(item)) {
            totalAmount -= price;
            System.out.println("Removed: " + item + " - $" + String.format("%.2f", price));
        }
    }
    
    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.paymentStrategy = strategy;
        System.out.println("Payment method set to: " + strategy.getPaymentMethod());
    }
    
    public void checkout() {
        if (paymentStrategy == null) {
            System.out.println("❌ Please select a payment method!");
            return;
        }
        
        if (items.isEmpty()) {
            System.out.println("❌ Cart is empty!");
            return;
        }
        
        System.out.println("\n=== CHECKOUT ===");
        System.out.println("Items in cart:");
        for (int i = 0; i < items.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + items.get(i));
        }
        System.out.println("Total: $" + String.format("%.2f", totalAmount));
        System.out.println("Payment method: " + paymentStrategy.getPaymentMethod());
        System.out.println();
        
        boolean success = paymentStrategy.pay(totalAmount);
        
        if (success) {
            System.out.println("🎉 Order completed successfully!");
            items.clear();
            totalAmount = 0.0;
        } else {
            System.out.println("❌ Order failed. Please try different payment method.");
        }
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public void showCart() {
        System.out.println("\n📋 Shopping Cart:");
        if (items.isEmpty()) {
            System.out.println("  Cart is empty");
        } else {
            for (String item : items) {
                System.out.println("  - " + item);
            }
            System.out.println("Total: $" + String.format("%.2f", totalAmount));
        }
    }
}

// Payment processor that can use different strategies
class PaymentProcessor {
    public static void processPayment(double amount, PaymentStrategy strategy) {
        System.out.println("\n--- Payment Processor ---");
        System.out.println("Processing $" + String.format("%.2f", amount) + 
                          " using " + strategy.getPaymentMethod());
        strategy.pay(amount);
    }
    
    public static PaymentStrategy selectBestStrategy(double amount) {
        if (amount < 10) {
            return new CreditCardPayment("1234567890123456", "John Doe", "123");
        } else if (amount < 100) {
            return new PayPalPayment("user@example.com", "password123");
        } else if (amount < 1000) {
            return new BankTransferPayment("1234567890", "021000021", "Chase Bank");
        } else {
            return new CryptoPayment("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa", "Bitcoin");
        }
    }
}

public class StrategyDemo {
    public static void main(String[] args) {
        System.out.println("=== Strategy Pattern Demo ===\n");
        
        // Create shopping cart
        ShoppingCart cart = new ShoppingCart();
        
        // Add items to cart
        System.out.println("1. Adding items to cart:");
        cart.addItem("Laptop", 999.99);
        cart.addItem("Mouse", 25.50);
        cart.addItem("Keyboard", 75.00);
        cart.showCart();
        
        // Try different payment strategies
        System.out.println("\n2. Trying different payment methods:");
        
        // Credit Card Payment
        System.out.println("\n--- Credit Card Payment ---");
        PaymentStrategy creditCard = new CreditCardPayment("4532123456789012", "Alice Johnson", "456");
        cart.setPaymentStrategy(creditCard);
        cart.checkout();
        
        // Add items again for next demo
        cart.addItem("Monitor", 299.99);
        cart.addItem("Speakers", 89.99);
        cart.showCart();
        
        // PayPal Payment
        System.out.println("\n--- PayPal Payment ---");
        PaymentStrategy paypal = new PayPalPayment("alice@email.com", "securepass");
        cart.setPaymentStrategy(paypal);
        cart.checkout();
        
        // Add expensive item for crypto demo
        cart.addItem("Gaming Setup", 2500.00);
        cart.showCart();
        
        // Cryptocurrency Payment
        System.out.println("\n--- Cryptocurrency Payment ---");
        PaymentStrategy crypto = new CryptoPayment("bc1qxy2kgdygjrsqtzq2n0yrf2493p83kkfjhx0wlh", "Bitcoin");
        cart.setPaymentStrategy(crypto);
        cart.checkout();
        
        // Demonstrate runtime strategy selection
        System.out.println("\n3. Dynamic Strategy Selection:");
        double[] amounts = {5.99, 49.99, 199.99, 1999.99};
        
        for (double amount : amounts) {
            PaymentStrategy bestStrategy = PaymentProcessor.selectBestStrategy(amount);
            System.out.println("\nFor amount $" + String.format("%.2f", amount) + 
                             ", best strategy: " + bestStrategy.getPaymentMethod());
            PaymentProcessor.processPayment(amount, bestStrategy);
        }
        
        // Error handling demo
        System.out.println("\n4. Error Handling Demo:");
        PaymentStrategy failingPayment = new CreditCardPayment("invalid", "Test User", "000");
        PaymentProcessor.processPayment(-50.00, failingPayment); // Invalid amount
        
        System.out.println("\n=== Demo Complete ===");
    }
} 