// Product class
class Computer {
    // Required parameters
    private String cpu;
    private int ram;
    private int storage;
    
    // Optional parameters
    private String gpu;
    private boolean bluetooth;
    private boolean wifi;
    private String operatingSystem;
    private String caseColor;
    private boolean liquidCooling;
    private int powerSupply;
    private String motherboard;
    
    // Private constructor - only accessible through builder
    private Computer(ComputerBuilder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.storage = builder.storage;
        this.gpu = builder.gpu;
        this.bluetooth = builder.bluetooth;
        this.wifi = builder.wifi;
        this.operatingSystem = builder.operatingSystem;
        this.caseColor = builder.caseColor;
        this.liquidCooling = builder.liquidCooling;
        this.powerSupply = builder.powerSupply;
        this.motherboard = builder.motherboard;
    }
    
    // Getters
    public String getCpu() { return cpu; }
    public int getRam() { return ram; }
    public int getStorage() { return storage; }
    public String getGpu() { return gpu; }
    public boolean hasBluetooth() { return bluetooth; }
    public boolean hasWifi() { return wifi; }
    public String getOperatingSystem() { return operatingSystem; }
    public String getCaseColor() { return caseColor; }
    public boolean hasLiquidCooling() { return liquidCooling; }
    public int getPowerSupply() { return powerSupply; }
    public String getMotherboard() { return motherboard; }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Computer Configuration:\n");
        sb.append("  CPU: ").append(cpu).append("\n");
        sb.append("  RAM: ").append(ram).append("GB\n");
        sb.append("  Storage: ").append(storage).append("GB\n");
        sb.append("  GPU: ").append(gpu != null ? gpu : "Integrated").append("\n");
        sb.append("  Motherboard: ").append(motherboard != null ? motherboard : "Standard").append("\n");
        sb.append("  Power Supply: ").append(powerSupply).append("W\n");
        sb.append("  Operating System: ").append(operatingSystem != null ? operatingSystem : "None").append("\n");
        sb.append("  Case Color: ").append(caseColor != null ? caseColor : "Black").append("\n");
        sb.append("  Bluetooth: ").append(bluetooth ? "Yes" : "No").append("\n");
        sb.append("  WiFi: ").append(wifi ? "Yes" : "No").append("\n");
        sb.append("  Liquid Cooling: ").append(liquidCooling ? "Yes" : "No").append("\n");
        return sb.toString();
    }
    
    public double calculatePrice() {
        double basePrice = 500; // Base price
        
        // CPU pricing
        if (cpu.contains("i9") || cpu.contains("Ryzen 9")) basePrice += 400;
        else if (cpu.contains("i7") || cpu.contains("Ryzen 7")) basePrice += 300;
        else if (cpu.contains("i5") || cpu.contains("Ryzen 5")) basePrice += 200;
        
        // RAM pricing
        basePrice += ram * 5; // $5 per GB
        
        // Storage pricing
        basePrice += storage * 0.1; // $0.1 per GB
        
        // GPU pricing
        if (gpu != null) {
            if (gpu.contains("RTX 4090")) basePrice += 1500;
            else if (gpu.contains("RTX 4080")) basePrice += 1200;
            else if (gpu.contains("RTX 4070")) basePrice += 800;
            else if (gpu.contains("GTX")) basePrice += 400;
        }
        
        // Other components
        if (liquidCooling) basePrice += 150;
        if (bluetooth) basePrice += 20;
        if (wifi) basePrice += 30;
        if (powerSupply > 750) basePrice += 100;
        
        return basePrice;
    }
    
    // Static nested Builder class
    public static class ComputerBuilder {
        // Required parameters
        private String cpu;
        private int ram;
        private int storage;
        
        // Optional parameters - initialized to default values
        private String gpu = null;
        private boolean bluetooth = false;
        private boolean wifi = false;
        private String operatingSystem = null;
        private String caseColor = "Black";
        private boolean liquidCooling = false;
        private int powerSupply = 500;
        private String motherboard = null;
        
        // Constructor with required parameters
        public ComputerBuilder(String cpu, int ram, int storage) {
            this.cpu = cpu;
            this.ram = ram;
            this.storage = storage;
        }
        
        // Methods for optional parameters - return builder for chaining
        public ComputerBuilder gpu(String gpu) {
            this.gpu = gpu;
            return this;
        }
        
        public ComputerBuilder bluetooth(boolean bluetooth) {
            this.bluetooth = bluetooth;
            return this;
        }
        
        public ComputerBuilder wifi(boolean wifi) {
            this.wifi = wifi;
            return this;
        }
        
        public ComputerBuilder operatingSystem(String os) {
            this.operatingSystem = os;
            return this;
        }
        
        public ComputerBuilder caseColor(String color) {
            this.caseColor = color;
            return this;
        }
        
        public ComputerBuilder liquidCooling(boolean liquidCooling) {
            this.liquidCooling = liquidCooling;
            return this;
        }
        
        public ComputerBuilder powerSupply(int watts) {
            this.powerSupply = watts;
            return this;
        }
        
        public ComputerBuilder motherboard(String motherboard) {
            this.motherboard = motherboard;
            return this;
        }
        
        // Build method
        public Computer build() {
            return new Computer(this);
        }
        
        // Preset configurations
        public ComputerBuilder gamingPreset() {
            return this.gpu("RTX 4070")
                      .liquidCooling(true)
                      .powerSupply(750)
                      .wifi(true)
                      .bluetooth(true)
                      .operatingSystem("Windows 11")
                      .caseColor("RGB");
        }
        
        public ComputerBuilder officePreset() {
            return this.wifi(true)
                      .bluetooth(true)
                      .operatingSystem("Windows 11")
                      .caseColor("Black");
        }
        
        public ComputerBuilder workstationPreset() {
            return this.gpu("RTX 4090")
                      .liquidCooling(true)
                      .powerSupply(1000)
                      .wifi(true)
                      .bluetooth(true)
                      .operatingSystem("Linux")
                      .motherboard("Professional X570");
        }
    }
}

// Director class - knows how to construct specific types
class ComputerDirector {
    
    public Computer buildGamingComputer() {
        return new Computer.ComputerBuilder("Intel i7-13700K", 32, 1000)
                .gamingPreset()
                .build();
    }
    
    public Computer buildOfficeComputer() {
        return new Computer.ComputerBuilder("Intel i5-13400", 16, 500)
                .officePreset()
                .build();
    }
    
    public Computer buildWorkstation() {
        return new Computer.ComputerBuilder("AMD Ryzen 9 7950X", 64, 2000)
                .workstationPreset()
                .build();
    }
    
    public Computer buildBudgetComputer() {
        return new Computer.ComputerBuilder("AMD Ryzen 5 5600", 8, 500)
                .wifi(true)
                .operatingSystem("Linux")
                .build();
    }
    
    public Computer buildCustomComputer(String cpu, int ram, int storage, 
                                      java.util.function.Function<Computer.ComputerBuilder, Computer.ComputerBuilder> customizer) {
        Computer.ComputerBuilder builder = new Computer.ComputerBuilder(cpu, ram, storage);
        return customizer.apply(builder).build();
    }
}

// Computer store simulation
class ComputerStore {
    private ComputerDirector director;
    private java.util.List<Computer> inventory;
    
    public ComputerStore() {
        this.director = new ComputerDirector();
        this.inventory = new java.util.ArrayList<>();
        setupInventory();
    }
    
    private void setupInventory() {
        inventory.add(director.buildGamingComputer());
        inventory.add(director.buildOfficeComputer());
        inventory.add(director.buildWorkstation());
        inventory.add(director.buildBudgetComputer());
    }
    
    public void showInventory() {
        System.out.println("=== Computer Store Inventory ===");
        for (int i = 0; i < inventory.size(); i++) {
            System.out.println("\n" + (i + 1) + ". " + getComputerType(inventory.get(i)));
            System.out.println("Price: $" + String.format("%.2f", inventory.get(i).calculatePrice()));
            System.out.println(inventory.get(i).toString());
        }
    }
    
    private String getComputerType(Computer computer) {
        if (computer.getGpu() != null && computer.getGpu().contains("RTX 40")) {
            if (computer.getRam() >= 32) return "Gaming Computer";
            else return "Gaming Computer (Entry)";
        } else if (computer.getGpu() != null && computer.getGpu().contains("RTX 4090")) {
            return "Workstation";
        } else if (computer.getRam() <= 16 && computer.getStorage() <= 500) {
            return "Budget Computer";
        } else {
            return "Office Computer";
        }
    }
    
    public Computer customBuild(String cpu, int ram, int storage) {
        return new Computer.ComputerBuilder(cpu, ram, storage).build();
    }
}

public class BuilderDemo {
    public static void main(String[] args) {
        System.out.println("=== Builder Pattern Demo ===\n");
        
        // 1. Basic Builder Usage
        System.out.println("1. Basic Builder Usage:");
        Computer basicComputer = new Computer.ComputerBuilder("Intel i5-13400", 16, 1000)
                .wifi(true)
                .bluetooth(true)
                .operatingSystem("Windows 11")
                .build();
        
        System.out.println(basicComputer);
        System.out.println("Price: $" + String.format("%.2f", basicComputer.calculatePrice()));
        
        // 2. Complex Builder with Method Chaining
        System.out.println("\n2. Complex Configuration:");
        Computer gamingRig = new Computer.ComputerBuilder("AMD Ryzen 7 7700X", 32, 2000)
                .gpu("RTX 4080")
                .liquidCooling(true)
                .powerSupply(850)
                .motherboard("ASUS ROG X670")
                .wifi(true)
                .bluetooth(true)
                .operatingSystem("Windows 11")
                .caseColor("RGB")
                .build();
        
        System.out.println(gamingRig);
        System.out.println("Price: $" + String.format("%.2f", gamingRig.calculatePrice()));
        
        // 3. Using Director for Predefined Configurations
        System.out.println("\n3. Using Director Pattern:");
        ComputerDirector director = new ComputerDirector();
        
        Computer[] preBuilt = {
            director.buildGamingComputer(),
            director.buildOfficeComputer(),
            director.buildWorkstation(),
            director.buildBudgetComputer()
        };
        
        String[] types = {"Gaming", "Office", "Workstation", "Budget"};
        
        for (int i = 0; i < preBuilt.length; i++) {
            System.out.println("\n" + types[i] + " Computer:");
            System.out.println("Price: $" + String.format("%.2f", preBuilt[i].calculatePrice()));
            System.out.println(preBuilt[i].toString());
        }
        
        // 4. Computer Store Simulation
        System.out.println("\n4. Computer Store Demo:");
        ComputerStore store = new ComputerStore();
        store.showInventory();
        
        // 5. Custom Configuration with Lambda
        System.out.println("\n5. Custom Build with Function:");
        Computer customComputer = director.buildCustomComputer(
            "Intel i9-13900K", 64, 4000,
            builder -> builder.gpu("RTX 4090")
                             .liquidCooling(true)
                             .powerSupply(1200)
                             .wifi(true)
                             .bluetooth(true)
                             .operatingSystem("Linux")
                             .caseColor("Black")
        );
        
        System.out.println("Custom High-End Build:");
        System.out.println("Price: $" + String.format("%.2f", customComputer.calculatePrice()));
        System.out.println(customComputer);
        
        // 6. Demonstrate Validation
        System.out.println("\n6. Different Build Approaches:");
        
        // Minimal build
        Computer minimal = new Computer.ComputerBuilder("Intel i3-13100", 8, 256).build();
        System.out.println("Minimal Build - Price: $" + String.format("%.2f", minimal.calculatePrice()));
        
        // Maximum build
        Computer maximal = new Computer.ComputerBuilder("AMD Ryzen 9 7950X", 128, 8000)
                .gpu("RTX 4090")
                .liquidCooling(true)
                .powerSupply(1500)
                .motherboard("Premium X670E")
                .wifi(true)
                .bluetooth(true)
                .operatingSystem("Windows 11 Pro")
                .caseColor("Custom RGB")
                .build();
        
        System.out.println("Maximum Build - Price: $" + String.format("%.2f", maximal.calculatePrice()));
        
        System.out.println("\n=== Demo Complete ===");
    }
} 