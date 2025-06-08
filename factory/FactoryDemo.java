// Product interface
interface Vehicle {
    void start();
    void stop();
    String getType();
    int getMaxSpeed();
}

// Concrete Products
class Car implements Vehicle {
    private String model;
    
    public Car(String model) {
        this.model = model;
        System.out.println("Car manufactured: " + model);
    }
    
    @Override
    public void start() {
        System.out.println("Car engine started with key ignition");
    }
    
    @Override
    public void stop() {
        System.out.println("Car engine stopped");
    }
    
    @Override
    public String getType() {
        return "Car - " + model;
    }
    
    @Override
    public int getMaxSpeed() {
        return 200;
    }
}

class Motorcycle implements Vehicle {
    private String model;
    
    public Motorcycle(String model) {
        this.model = model;
        System.out.println("Motorcycle manufactured: " + model);
    }
    
    @Override
    public void start() {
        System.out.println("Motorcycle started with kick/button start");
    }
    
    @Override
    public void stop() {
        System.out.println("Motorcycle engine stopped");
    }
    
    @Override
    public String getType() {
        return "Motorcycle - " + model;
    }
    
    @Override
    public int getMaxSpeed() {
        return 180;
    }
}

class Truck implements Vehicle {
    private String model;
    
    public Truck(String model) {
        this.model = model;
        System.out.println("Truck manufactured: " + model);
    }
    
    @Override
    public void start() {
        System.out.println("Truck diesel engine started");
    }
    
    @Override
    public void stop() {
        System.out.println("Truck engine stopped");
    }
    
    @Override
    public String getType() {
        return "Truck - " + model;
    }
    
    @Override
    public int getMaxSpeed() {
        return 120;
    }
}

// Factory class
class VehicleFactory {
    
    // Simple Factory Method
    public static Vehicle createVehicle(String type, String model) {
        switch (type.toLowerCase()) {
            case "car":
                return new Car(model);
            case "motorcycle":
            case "bike":
                return new Motorcycle(model);
            case "truck":
                return new Truck(model);
            default:
                throw new IllegalArgumentException("Unknown vehicle type: " + type);
        }
    }
    
    // Factory method with enum for better type safety
    public enum VehicleType {
        CAR, MOTORCYCLE, TRUCK
    }
    
    public static Vehicle createVehicle(VehicleType type, String model) {
        switch (type) {
            case CAR:
                return new Car(model);
            case MOTORCYCLE:
                return new Motorcycle(model);
            case TRUCK:
                return new Truck(model);
            default:
                throw new IllegalArgumentException("Unsupported vehicle type");
        }
    }
    
    // Factory method for creating vehicles based on requirements
    public static Vehicle createVehicleForPurpose(String purpose, String model) {
        switch (purpose.toLowerCase()) {
            case "family":
            case "daily":
                return new Car(model + " Family Edition");
            case "adventure":
            case "sport":
                return new Motorcycle(model + " Sport");
            case "delivery":
            case "cargo":
                return new Truck(model + " Cargo");
            default:
                return new Car(model + " Standard");
        }
    }
}

// Client classes
class VehicleDealer {
    private java.util.List<Vehicle> inventory;
    
    public VehicleDealer() {
        inventory = new java.util.ArrayList<>();
    }
    
    public void addVehicleToInventory(String type, String model) {
        Vehicle vehicle = VehicleFactory.createVehicle(type, model);
        inventory.add(vehicle);
    }
    
    public void showInventory() {
        System.out.println("\n=== Dealer Inventory ===");
        for (int i = 0; i < inventory.size(); i++) {
            Vehicle vehicle = inventory.get(i);
            System.out.println((i + 1) + ". " + vehicle.getType() + 
                             " (Max Speed: " + vehicle.getMaxSpeed() + " km/h)");
        }
    }
    
    public Vehicle sellVehicle(int index) {
        if (index >= 0 && index < inventory.size()) {
            return inventory.remove(index);
        }
        return null;
    }
}

class VehicleTestDrive {
    public void testVehicle(Vehicle vehicle) {
        System.out.println("\n--- Test Drive: " + vehicle.getType() + " ---");
        vehicle.start();
        System.out.println("Driving... Max speed: " + vehicle.getMaxSpeed() + " km/h");
        vehicle.stop();
    }
}

public class FactoryDemo {
    public static void main(String[] args) {
        System.out.println("=== Factory Pattern Demo ===\n");
        
        // 1. Simple Factory Usage
        System.out.println("1. Simple Factory Creation:");
        Vehicle car1 = VehicleFactory.createVehicle("car", "Toyota Camry");
        Vehicle bike1 = VehicleFactory.createVehicle("motorcycle", "Honda CBR");
        Vehicle truck1 = VehicleFactory.createVehicle("truck", "Ford F-150");
        
        // 2. Enum-based Factory
        System.out.println("\n2. Enum-based Factory:");
        Vehicle car2 = VehicleFactory.createVehicle(
            VehicleFactory.VehicleType.CAR, "BMW X5");
        Vehicle bike2 = VehicleFactory.createVehicle(
            VehicleFactory.VehicleType.MOTORCYCLE, "Yamaha R1");
        
        // 3. Purpose-based Factory
        System.out.println("\n3. Purpose-based Factory Creation:");
        Vehicle familyCar = VehicleFactory.createVehicleForPurpose("family", "Honda Accord");
        Vehicle sportBike = VehicleFactory.createVehicleForPurpose("sport", "Kawasaki Ninja");
        Vehicle deliveryTruck = VehicleFactory.createVehicleForPurpose("delivery", "Mercedes Sprinter");
        
        // 4. Vehicle Dealer Example
        System.out.println("\n4. Vehicle Dealer Scenario:");
        VehicleDealer dealer = new VehicleDealer();
        dealer.addVehicleToInventory("car", "Tesla Model 3");
        dealer.addVehicleToInventory("motorcycle", "Harley Davidson");
        dealer.addVehicleToInventory("truck", "Chevrolet Silverado");
        dealer.addVehicleToInventory("car", "Audi A4");
        
        dealer.showInventory();
        
        // 5. Test Drive
        System.out.println("\n5. Test Drive Demo:");
        VehicleTestDrive testDrive = new VehicleTestDrive();
        testDrive.testVehicle(car1);
        testDrive.testVehicle(bike1);
        testDrive.testVehicle(truck1);
        
        // 6. Customer Purchase
        System.out.println("\n6. Customer Purchase:");
        Vehicle purchasedVehicle = dealer.sellVehicle(0);
        if (purchasedVehicle != null) {
            System.out.println("Customer purchased: " + purchasedVehicle.getType());
            testDrive.testVehicle(purchasedVehicle);
        }
        
        dealer.showInventory();
        
        // 7. Error Handling
        System.out.println("\n7. Error Handling:");
        try {
            Vehicle unknown = VehicleFactory.createVehicle("airplane", "Boeing 747");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        System.out.println("\n=== Demo Complete ===");
    }
} 