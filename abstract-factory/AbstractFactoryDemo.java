// Abstract products
interface Button {
    void render();
    void onClick();
    String getStyle();
}

interface Checkbox {
    void render();
    void toggle();
    String getStyle();
}

interface TextField {
    void render();
    void setText(String text);
    String getText();
    String getStyle();
}

// Windows implementations
class WindowsButton implements Button {
    private boolean clicked = false;
    
    @Override
    public void render() {
        System.out.println("🪟 Rendering Windows-style button with 3D border");
    }
    
    @Override
    public void onClick() {
        clicked = true;
        System.out.println("🪟 Windows button clicked with classic click sound");
    }
    
    @Override
    public String getStyle() {
        return "Windows 3D Style";
    }
}

class WindowsCheckbox implements Checkbox {
    private boolean checked = false;
    
    @Override
    public void render() {
        System.out.println("🪟 Rendering Windows-style checkbox with square border");
    }
    
    @Override
    public void toggle() {
        checked = !checked;
        System.out.println("🪟 Windows checkbox " + (checked ? "checked" : "unchecked"));
    }
    
    @Override
    public String getStyle() {
        return "Windows Square Style";
    }
}

class WindowsTextField implements TextField {
    private String text = "";
    
    @Override
    public void render() {
        System.out.println("🪟 Rendering Windows-style text field with inset border");
    }
    
    @Override
    public void setText(String text) {
        this.text = text;
        System.out.println("🪟 Windows text field set to: " + text);
    }
    
    @Override
    public String getText() {
        return text;
    }
    
    @Override
    public String getStyle() {
        return "Windows Inset Style";
    }
}

// macOS implementations
class MacButton implements Button {
    private boolean clicked = false;
    
    @Override
    public void render() {
        System.out.println("🍎 Rendering macOS-style button with rounded corners");
    }
    
    @Override
    public void onClick() {
        clicked = true;
        System.out.println("🍎 macOS button clicked with smooth animation");
    }
    
    @Override
    public String getStyle() {
        return "macOS Rounded Style";
    }
}

class MacCheckbox implements Checkbox {
    private boolean checked = false;
    
    @Override
    public void render() {
        System.out.println("🍎 Rendering macOS-style checkbox with smooth rounded corners");
    }
    
    @Override
    public void toggle() {
        checked = !checked;
        System.out.println("🍎 macOS checkbox " + (checked ? "checked with smooth transition" : "unchecked"));
    }
    
    @Override
    public String getStyle() {
        return "macOS Smooth Style";
    }
}

class MacTextField implements TextField {
    private String text = "";
    
    @Override
    public void render() {
        System.out.println("🍎 Rendering macOS-style text field with clean border");
    }
    
    @Override
    public void setText(String text) {
        this.text = text;
        System.out.println("🍎 macOS text field set to: " + text);
    }
    
    @Override
    public String getText() {
        return text;
    }
    
    @Override
    public String getStyle() {
        return "macOS Clean Style";
    }
}

// Linux implementations
class LinuxButton implements Button {
    private boolean clicked = false;
    
    @Override
    public void render() {
        System.out.println("🐧 Rendering Linux-style button with flat design");
    }
    
    @Override
    public void onClick() {
        clicked = true;
        System.out.println("🐧 Linux button clicked with minimal feedback");
    }
    
    @Override
    public String getStyle() {
        return "Linux Flat Style";
    }
}

class LinuxCheckbox implements Checkbox {
    private boolean checked = false;
    
    @Override
    public void render() {
        System.out.println("🐧 Rendering Linux-style checkbox with material design");
    }
    
    @Override
    public void toggle() {
        checked = !checked;
        System.out.println("🐧 Linux checkbox " + (checked ? "checked" : "unchecked") + " with material ripple");
    }
    
    @Override
    public String getStyle() {
        return "Linux Material Style";
    }
}

class LinuxTextField implements TextField {
    private String text = "";
    
    @Override
    public void render() {
        System.out.println("🐧 Rendering Linux-style text field with underline focus");
    }
    
    @Override
    public void setText(String text) {
        this.text = text;
        System.out.println("🐧 Linux text field set to: " + text);
    }
    
    @Override
    public String getText() {
        return text;
    }
    
    @Override
    public String getStyle() {
        return "Linux Underline Style";
    }
}

// Abstract Factory interface
interface UIFactory {
    Button createButton();
    Checkbox createCheckbox();
    TextField createTextField();
    String getPlatformName();
}

// Concrete Factories
class WindowsFactory implements UIFactory {
    @Override
    public Button createButton() {
        return new WindowsButton();
    }
    
    @Override
    public Checkbox createCheckbox() {
        return new WindowsCheckbox();
    }
    
    @Override
    public TextField createTextField() {
        return new WindowsTextField();
    }
    
    @Override
    public String getPlatformName() {
        return "Windows";
    }
}

class MacFactory implements UIFactory {
    @Override
    public Button createButton() {
        return new MacButton();
    }
    
    @Override
    public Checkbox createCheckbox() {
        return new MacCheckbox();
    }
    
    @Override
    public TextField createTextField() {
        return new MacTextField();
    }
    
    @Override
    public String getPlatformName() {
        return "macOS";
    }
}

class LinuxFactory implements UIFactory {
    @Override
    public Button createButton() {
        return new LinuxButton();
    }
    
    @Override
    public Checkbox createCheckbox() {
        return new LinuxCheckbox();
    }
    
    @Override
    public TextField createTextField() {
        return new LinuxTextField();
    }
    
    @Override
    public String getPlatformName() {
        return "Linux";
    }
}

// Client application
class Application {
    private UIFactory factory;
    private Button button;
    private Checkbox checkbox;
    private TextField textField;
    
    public Application(UIFactory factory) {
        this.factory = factory;
        createUI();
    }
    
    private void createUI() {
        System.out.println("Creating UI for platform: " + factory.getPlatformName());
        this.button = factory.createButton();
        this.checkbox = factory.createCheckbox();
        this.textField = factory.createTextField();
    }
    
    public void renderUI() {
        System.out.println("\n=== Rendering Application UI ===");
        button.render();
        checkbox.render();
        textField.render();
        System.out.println("UI Style: " + button.getStyle() + " theme");
    }
    
    public void interactWithUI() {
        System.out.println("\n=== User Interactions ===");
        textField.setText("Hello " + factory.getPlatformName() + "!");
        System.out.println("Text field content: " + textField.getText());
        
        button.onClick();
        checkbox.toggle();
        checkbox.toggle(); // Toggle again
    }
    
    public void showUIInfo() {
        System.out.println("\n=== UI Component Information ===");
        System.out.println("Platform: " + factory.getPlatformName());
        System.out.println("Button Style: " + button.getStyle());
        System.out.println("Checkbox Style: " + checkbox.getStyle());
        System.out.println("TextField Style: " + textField.getStyle());
    }
}

// Factory provider to abstract factory selection
class UIFactoryProvider {
    public enum Platform {
        WINDOWS, MACOS, LINUX
    }
    
    public static UIFactory getFactory(Platform platform) {
        switch (platform) {
            case WINDOWS:
                return new WindowsFactory();
            case MACOS:
                return new MacFactory();
            case LINUX:
                return new LinuxFactory();
            default:
                throw new IllegalArgumentException("Unsupported platform: " + platform);
        }
    }
    
    public static UIFactory getFactory(String osName) {
        String os = osName.toLowerCase();
        if (os.contains("windows")) {
            return new WindowsFactory();
        } else if (os.contains("mac") || os.contains("darwin")) {
            return new MacFactory();
        } else if (os.contains("linux") || os.contains("unix")) {
            return new LinuxFactory();
        } else {
            // Default to a cross-platform factory
            return new LinuxFactory(); // Use Linux as default
        }
    }
    
    public static UIFactory getFactoryForCurrentOS() {
        String osName = System.getProperty("os.name");
        return getFactory(osName);
    }
}

// Cross-platform application launcher
class CrossPlatformLauncher {
    public static void launchApplication(UIFactoryProvider.Platform platform) {
        try {
            UIFactory factory = UIFactoryProvider.getFactory(platform);
            Application app = new Application(factory);
            app.renderUI();
            app.interactWithUI();
            app.showUIInfo();
        } catch (Exception e) {
            System.err.println("Error launching application: " + e.getMessage());
        }
    }
    
    public static void demonstrateAllPlatforms() {
        UIFactoryProvider.Platform[] platforms = UIFactoryProvider.Platform.values();
        
        for (UIFactoryProvider.Platform platform : platforms) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("Testing " + platform + " Platform");
            System.out.println("=".repeat(50));
            launchApplication(platform);
        }
    }
}

public class AbstractFactoryDemo {
    public static void main(String[] args) {
        System.out.println("=== Abstract Factory Pattern Demo ===\n");
        
        // 1. Demonstrate all platforms
        System.out.println("1. Cross-Platform UI Demo:");
        CrossPlatformLauncher.demonstrateAllPlatforms();
        
        // 2. Dynamic platform detection
        System.out.println("\n" + "=".repeat(50));
        System.out.println("2. Current OS Detection:");
        System.out.println("=".repeat(50));
        
        String currentOS = System.getProperty("os.name");
        System.out.println("Detected OS: " + currentOS);
        
        UIFactory autoFactory = UIFactoryProvider.getFactoryForCurrentOS();
        Application autoApp = new Application(autoFactory);
        autoApp.renderUI();
        autoApp.interactWithUI();
        
        // 3. Factory family consistency demonstration
        System.out.println("\n" + "=".repeat(50));
        System.out.println("3. Factory Family Consistency Test:");
        System.out.println("=".repeat(50));
        
        // Test that all components from same factory have consistent styling
        for (UIFactoryProvider.Platform platform : UIFactoryProvider.Platform.values()) {
            UIFactory factory = UIFactoryProvider.getFactory(platform);
            
            Button btn = factory.createButton();
            Checkbox chk = factory.createCheckbox();
            TextField txt = factory.createTextField();
            
            System.out.println("\n" + platform + " Factory produces:");
            System.out.println("  Button: " + btn.getStyle());
            System.out.println("  Checkbox: " + chk.getStyle());
            System.out.println("  TextField: " + txt.getStyle());
            
            // Verify all components have consistent platform styling
            String expectedStyle = platform.toString().toLowerCase();
            boolean consistent = btn.getStyle().toLowerCase().contains(expectedStyle.split("_")[0]) &&
                               chk.getStyle().toLowerCase().contains(expectedStyle.split("_")[0]) &&
                               txt.getStyle().toLowerCase().contains(expectedStyle.split("_")[0]);
            
            System.out.println("  Style Consistency: " + (consistent ? "✅ PASS" : "❌ FAIL"));
        }
        
        System.out.println("\n=== Demo Complete ===");
    }
} 