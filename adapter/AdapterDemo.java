// Legacy Printer interface that we cannot modify
interface LegacyPrinter {
    void printOldFormat(String text);
}

// Legacy Printer implementation
class OldPrinter implements LegacyPrinter {
    @Override
    public void printOldFormat(String text) {
        System.out.println("Legacy Printer: " + text.toUpperCase());
    }
}

// Modern Printer interface that our system expects
interface ModernPrinter {
    void print(String text, boolean color);
    void printWithHeader(String text, String header);
}

// Adapter class that makes LegacyPrinter work with ModernPrinter interface
class PrinterAdapter implements ModernPrinter {
    private LegacyPrinter legacyPrinter;
    
    public PrinterAdapter(LegacyPrinter legacyPrinter) {
        this.legacyPrinter = legacyPrinter;
    }
    
    @Override
    public void print(String text, boolean color) {
        String colorPrefix = color ? "[COLOR] " : "[B&W] ";
        legacyPrinter.printOldFormat(colorPrefix + text);
    }
    
    @Override
    public void printWithHeader(String text, String header) {
        legacyPrinter.printOldFormat("=== " + header + " ===");
        legacyPrinter.printOldFormat(text);
    }
}

// Modern Printer implementation for comparison
class NewPrinter implements ModernPrinter {
    @Override
    public void print(String text, boolean color) {
        String colorCode = color ? "\033[32m" : "\033[0m";
        System.out.println("Modern Printer: " + colorCode + text + "\033[0m");
    }
    
    @Override
    public void printWithHeader(String text, String header) {
        System.out.println("Modern Printer Header: " + header);
        System.out.println("Modern Printer Content: " + text);
    }
}

// Client class that uses ModernPrinter interface
class PrinterClient {
    private ModernPrinter printer;
    
    public PrinterClient(ModernPrinter printer) {
        this.printer = printer;
    }
    
    public void executeTask() {
        printer.print("Hello World", true);
        printer.printWithHeader("Important Document", "CONFIDENTIAL");
    }
}

// Demonstration
public class AdapterDemo {
    public static void main(String[] args) {
        System.out.println("=== Adapter Pattern Demo ===\n");
        
        // Using legacy printer through adapter
        System.out.println("1. Using Legacy Printer through Adapter:");
        LegacyPrinter oldPrinter = new OldPrinter();
        ModernPrinter adaptedPrinter = new PrinterAdapter(oldPrinter);
        PrinterClient client1 = new PrinterClient(adaptedPrinter);
        client1.executeTask();
        
        System.out.println("\n2. Using Modern Printer directly:");
        ModernPrinter newPrinter = new NewPrinter();
        PrinterClient client2 = new PrinterClient(newPrinter);
        client2.executeTask();
        
        System.out.println("\n=== Demo Complete ===");
    }
} 