# Abstract Factory Pattern - Class Diagram

```mermaid
classDiagram
    class UIFactory {
        <<interface>>
        +createButton() Button
        +createCheckbox() Checkbox
        +createTextField() TextField
        +getPlatformName() String
    }
    
    class WindowsFactory {
        +createButton() Button
        +createCheckbox() Checkbox
        +createTextField() TextField
        +getPlatformName() String
    }
    
    class MacFactory {
        +createButton() Button
        +createCheckbox() Checkbox
        +createTextField() TextField
        +getPlatformName() String
    }
    
    class LinuxFactory {
        +createButton() Button
        +createCheckbox() Checkbox
        +createTextField() TextField
        +getPlatformName() String
    }
    
    class Button {
        <<interface>>
        +render() void
        +onClick() void
        +getStyle() String
    }
    
    class Checkbox {
        <<interface>>
        +render() void
        +toggle() void
        +getStyle() String
    }
    
    class TextField {
        <<interface>>
        +render() void
        +setText(String) void
        +getText() String
        +getStyle() String
    }
    
    class WindowsButton {
        -clicked: boolean
        +render() void
        +onClick() void
        +getStyle() String
    }
    
    class WindowsCheckbox {
        -checked: boolean
        +render() void
        +toggle() void
        +getStyle() String
    }
    
    class WindowsTextField {
        -text: String
        +render() void
        +setText(String) void
        +getText() String
        +getStyle() String
    }
    
    class MacButton {
        -clicked: boolean
        +render() void
        +onClick() void
        +getStyle() String
    }
    
    class MacCheckbox {
        -checked: boolean
        +render() void
        +toggle() void
        +getStyle() String
    }
    
    class MacTextField {
        -text: String
        +render() void
        +setText(String) void
        +getText() String
        +getStyle() String
    }
    
    class LinuxButton {
        -clicked: boolean
        +render() void
        +onClick() void
        +getStyle() String
    }
    
    class LinuxCheckbox {
        -checked: boolean
        +render() void
        +toggle() void
        +getStyle() String
    }
    
    class LinuxTextField {
        -text: String
        +render() void
        +setText(String) void
        +getText() String
        +getStyle() String
    }
    
    class Application {
        -factory: UIFactory
        -button: Button
        -checkbox: Checkbox
        -textField: TextField
        +Application(UIFactory)
        +renderUI() void
        +interactWithUI() void
        +showUIInfo() void
    }
    
    class UIFactoryProvider {
        <<enumeration>>
        WINDOWS
        MACOS
        LINUX
        +getFactory(Platform) UIFactory
        +getFactory(String) UIFactory
        +getFactoryForCurrentOS() UIFactory
    }
    
    UIFactory <|.. WindowsFactory
    UIFactory <|.. MacFactory
    UIFactory <|.. LinuxFactory
    
    Button <|.. WindowsButton
    Button <|.. MacButton
    Button <|.. LinuxButton
    
    Checkbox <|.. WindowsCheckbox
    Checkbox <|.. MacCheckbox
    Checkbox <|.. LinuxCheckbox
    
    TextField <|.. WindowsTextField
    TextField <|.. MacTextField
    TextField <|.. LinuxTextField
    
    WindowsFactory ..> WindowsButton : creates
    WindowsFactory ..> WindowsCheckbox : creates
    WindowsFactory ..> WindowsTextField : creates
    
    MacFactory ..> MacButton : creates
    MacFactory ..> MacCheckbox : creates
    MacFactory ..> MacTextField : creates
    
    LinuxFactory ..> LinuxButton : creates
    LinuxFactory ..> LinuxCheckbox : creates
    LinuxFactory ..> LinuxTextField : creates
    
    Application --> UIFactory : uses
    Application --> Button : uses
    Application --> Checkbox : uses
    Application --> TextField : uses
    
    UIFactoryProvider ..> UIFactory : provides
```

## Key Relationships

1. **Factory Hierarchy**: All concrete factories implement UIFactory interface
2. **Product Hierarchies**: Each UI component type has platform-specific implementations
3. **Family Consistency**: Each factory creates components from the same platform family
4. **Client Independence**: Application works with abstract interfaces only
5. **Factory Provider**: Encapsulates factory selection logic

## Benefits Demonstrated

- **Consistency**: All components from same factory have consistent styling
- **Isolation**: Client code isolated from concrete product classes
- **Flexibility**: Easy to add new platform families
- **Encapsulation**: Product creation logic encapsulated in factories 