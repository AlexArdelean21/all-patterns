// Base component interface
interface Coffee {
    String getDescription();
    double getCost();
}

// Concrete component
class BasicCoffee implements Coffee {
    @Override
    public String getDescription() {
        return "Basic Coffee";
    }
    
    @Override
    public double getCost() {
        return 2.00;
    }
}

// Base decorator class
abstract class CoffeeDecorator implements Coffee {
    protected Coffee coffee;
    
    public CoffeeDecorator(Coffee coffee) {
        this.coffee = coffee;
    }
    
    @Override
    public String getDescription() {
        return coffee.getDescription();
    }
    
    @Override
    public double getCost() {
        return coffee.getCost();
    }
}

// Concrete decorators
class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }
    
    @Override
    public String getDescription() {
        return coffee.getDescription() + ", Milk";
    }
    
    @Override
    public double getCost() {
        return coffee.getCost() + 0.50;
    }
}

class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee coffee) {
        super(coffee);
    }
    
    @Override
    public String getDescription() {
        return coffee.getDescription() + ", Sugar";
    }
    
    @Override
    public double getCost() {
        return coffee.getCost() + 0.20;
    }
}

class WhipDecorator extends CoffeeDecorator {
    public WhipDecorator(Coffee coffee) {
        super(coffee);
    }
    
    @Override
    public String getDescription() {
        return coffee.getDescription() + ", Whipped Cream";
    }
    
    @Override
    public double getCost() {
        return coffee.getCost() + 0.70;
    }
}

class VanillaDecorator extends CoffeeDecorator {
    public VanillaDecorator(Coffee coffee) {
        super(coffee);
    }
    
    @Override
    public String getDescription() {
        return coffee.getDescription() + ", Vanilla";
    }
    
    @Override
    public double getCost() {
        return coffee.getCost() + 0.60;
    }
}

class CaramelDecorator extends CoffeeDecorator {
    public CaramelDecorator(Coffee coffee) {
        super(coffee);
    }
    
    @Override
    public String getDescription() {
        return coffee.getDescription() + ", Caramel";
    }
    
    @Override
    public double getCost() {
        return coffee.getCost() + 0.65;
    }
}

// Another concrete component for variety
class EspressoCoffee implements Coffee {
    @Override
    public String getDescription() {
        return "Espresso";
    }
    
    @Override
    public double getCost() {
        return 3.50;
    }
}

// Coffee shop class to demonstrate usage
class CoffeeShop {
    public void printOrder(Coffee coffee) {
        System.out.printf("Order: %s - $%.2f%n", 
                         coffee.getDescription(), coffee.getCost());
    }
    
    public Coffee createSpecialCoffee() {
        // Create a complex coffee with multiple decorators
        Coffee coffee = new BasicCoffee();
        coffee = new MilkDecorator(coffee);
        coffee = new SugarDecorator(coffee);
        coffee = new VanillaDecorator(coffee);
        coffee = new WhipDecorator(coffee);
        return coffee;
    }
}

public class DecoratorDemo {
    public static void main(String[] args) {
        System.out.println("=== Decorator Pattern Demo - Coffee Shop ===\n");
        
        CoffeeShop shop = new CoffeeShop();
        
        // Basic coffee
        Coffee basicCoffee = new BasicCoffee();
        shop.printOrder(basicCoffee);
        
        // Coffee with milk
        Coffee milkCoffee = new MilkDecorator(new BasicCoffee());
        shop.printOrder(milkCoffee);
        
        // Coffee with milk and sugar
        Coffee milkSugarCoffee = new SugarDecorator(
            new MilkDecorator(new BasicCoffee()));
        shop.printOrder(milkSugarCoffee);
        
        // Complex coffee with multiple decorators
        Coffee complexCoffee = new CaramelDecorator(
            new WhipDecorator(
                new VanillaDecorator(
                    new MilkDecorator(
                        new BasicCoffee()))));
        shop.printOrder(complexCoffee);
        
        System.out.println();
        
        // Espresso based drinks
        Coffee espresso = new EspressoCoffee();
        shop.printOrder(espresso);
        
        Coffee espressoLatte = new MilkDecorator(
            new MilkDecorator(new EspressoCoffee())); // Double milk
        shop.printOrder(espressoLatte);
        
        // Shop's special coffee
        Coffee specialCoffee = shop.createSpecialCoffee();
        shop.printOrder(specialCoffee);
        
        System.out.println("\n=== Demonstrating Runtime Flexibility ===");
        
        // Build coffee step by step
        Coffee buildUp = new BasicCoffee();
        System.out.println("Starting with: " + buildUp.getDescription() + 
                          " - $" + String.format("%.2f", buildUp.getCost()));
        
        buildUp = new MilkDecorator(buildUp);
        System.out.println("Added milk: " + buildUp.getDescription() + 
                          " - $" + String.format("%.2f", buildUp.getCost()));
        
        buildUp = new SugarDecorator(buildUp);
        System.out.println("Added sugar: " + buildUp.getDescription() + 
                          " - $" + String.format("%.2f", buildUp.getCost()));
        
        buildUp = new VanillaDecorator(buildUp);
        System.out.println("Added vanilla: " + buildUp.getDescription() + 
                          " - $" + String.format("%.2f", buildUp.getCost()));
        
        System.out.println("\n=== Demo Complete ===");
    }
} 