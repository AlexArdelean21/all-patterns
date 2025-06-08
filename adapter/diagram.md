# Adapter Pattern Diagram

```mermaid
classDiagram
    class LegacyPrinter {
        +printOldFormat(String text)
    }
    
    class OldPrinter {
        +printOldFormat(String text)
    }
    
    class ModernPrinter {
        +print(String text, boolean color)
        +printWithHeader(String text, String header)
    }
    
    class PrinterAdapter {
        -LegacyPrinter legacyPrinter
        +PrinterAdapter(LegacyPrinter printer)
        +print(String text, boolean color)
        +printWithHeader(String text, String header)
    }
    
    class NewPrinter {
        +print(String text, boolean color)
        +printWithHeader(String text, String header)
    }
    
    class PrinterClient {
        -ModernPrinter printer
        +PrinterClient(ModernPrinter printer)
        +executeTask()
    }
    
    LegacyPrinter <|-- OldPrinter
    ModernPrinter <|-- PrinterAdapter
    ModernPrinter <|-- NewPrinter
    PrinterAdapter --> LegacyPrinter : "adapts"
    PrinterClient --> ModernPrinter : "uses"
```

The diagram shows how the Adapter pattern works:
- **LegacyPrinter**: The old interface we cannot modify
- **PrinterAdapter**: The adapter that implements the new interface while using the legacy printer
- **ModernPrinter**: The new interface our system expects
- **PrinterClient**: The client that only knows about the modern interface 