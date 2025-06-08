import java.util.*;

// Command interface
interface Command {
    void execute();
    void undo();
    String getDescription();
}

// Receiver - the object that performs the actual work
class TextEditor {
    private StringBuilder content;
    private int cursorPosition;
    
    public TextEditor() {
        this.content = new StringBuilder();
        this.cursorPosition = 0;
    }
    
    public void insertText(String text, int position) {
        if (position >= 0 && position <= content.length()) {
            content.insert(position, text);
            cursorPosition = position + text.length();
            System.out.println("Inserted: '" + text + "' at position " + position);
        }
    }
    
    public void deleteText(int start, int length) {
        if (start >= 0 && start < content.length() && length > 0) {
            int end = Math.min(start + length, content.length());
            content.delete(start, end);
            cursorPosition = start;
            System.out.println("Deleted " + length + " characters from position " + start);
        }
    }
    
    public void replaceText(int start, int length, String newText) {
        if (start >= 0 && start < content.length()) {
            int end = Math.min(start + length, content.length());
            content.replace(start, end, newText);
            cursorPosition = start + newText.length();
            System.out.println("Replaced text at position " + start + " with '" + newText + "'");
        }
    }
    
    public String getText() {
        return content.toString();
    }
    
    public int getCursorPosition() {
        return cursorPosition;
    }
    
    public void setCursorPosition(int position) {
        if (position >= 0 && position <= content.length()) {
            this.cursorPosition = position;
        }
    }
    
    public void clear() {
        content.setLength(0);
        cursorPosition = 0;
        System.out.println("Editor cleared");
    }
    
    public void showContent() {
        System.out.println("Content: \"" + content.toString() + "\"");
        System.out.println("Cursor at position: " + cursorPosition);
    }
}

// Concrete Commands
class InsertCommand implements Command {
    private TextEditor editor;
    private String text;
    private int position;
    
    public InsertCommand(TextEditor editor, String text, int position) {
        this.editor = editor;
        this.text = text;
        this.position = position;
    }
    
    @Override
    public void execute() {
        editor.insertText(text, position);
    }
    
    @Override
    public void undo() {
        editor.deleteText(position, text.length());
    }
    
    @Override
    public String getDescription() {
        return "Insert '" + text + "' at position " + position;
    }
}

class DeleteCommand implements Command {
    private TextEditor editor;
    private int start;
    private int length;
    private String deletedText;
    
    public DeleteCommand(TextEditor editor, int start, int length) {
        this.editor = editor;
        this.start = start;
        this.length = length;
        // Store the text that will be deleted for undo
        String content = editor.getText();
        if (start >= 0 && start < content.length()) {
            int end = Math.min(start + length, content.length());
            this.deletedText = content.substring(start, end);
        } else {
            this.deletedText = "";
        }
    }
    
    @Override
    public void execute() {
        editor.deleteText(start, length);
    }
    
    @Override
    public void undo() {
        editor.insertText(deletedText, start);
    }
    
    @Override
    public String getDescription() {
        return "Delete " + length + " characters from position " + start;
    }
}

class ReplaceCommand implements Command {
    private TextEditor editor;
    private int start;
    private int length;
    private String newText;
    private String originalText;
    
    public ReplaceCommand(TextEditor editor, int start, int length, String newText) {
        this.editor = editor;
        this.start = start;
        this.length = length;
        this.newText = newText;
        
        // Store original text for undo
        String content = editor.getText();
        if (start >= 0 && start < content.length()) {
            int end = Math.min(start + length, content.length());
            this.originalText = content.substring(start, end);
        } else {
            this.originalText = "";
        }
    }
    
    @Override
    public void execute() {
        editor.replaceText(start, length, newText);
    }
    
    @Override
    public void undo() {
        editor.replaceText(start, newText.length(), originalText);
    }
    
    @Override
    public String getDescription() {
        return "Replace text at position " + start + " with '" + newText + "'";
    }
}

// Macro command - composite command
class MacroCommand implements Command {
    private List<Command> commands;
    private String description;
    
    public MacroCommand(String description) {
        this.commands = new ArrayList<>();
        this.description = description;
    }
    
    public void addCommand(Command command) {
        commands.add(command);
    }
    
    @Override
    public void execute() {
        System.out.println("Executing macro: " + description);
        for (Command command : commands) {
            command.execute();
        }
    }
    
    @Override
    public void undo() {
        System.out.println("Undoing macro: " + description);
        // Undo in reverse order
        for (int i = commands.size() - 1; i >= 0; i--) {
            commands.get(i).undo();
        }
    }
    
    @Override
    public String getDescription() {
        return "Macro: " + description + " (" + commands.size() + " commands)";
    }
}

// Invoker - manages command execution and undo/redo
class EditorInvoker {
    private Stack<Command> undoStack;
    private Stack<Command> redoStack;
    private TextEditor editor;
    
    public EditorInvoker(TextEditor editor) {
        this.editor = editor;
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }
    
    public void executeCommand(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear(); // Clear redo stack when new command is executed
        System.out.println("Command executed: " + command.getDescription());
    }
    
    public void undo() {
        if (!undoStack.isEmpty()) {
            Command command = undoStack.pop();
            command.undo();
            redoStack.push(command);
            System.out.println("Undid: " + command.getDescription());
        } else {
            System.out.println("Nothing to undo");
        }
    }
    
    public void redo() {
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            command.execute();
            undoStack.push(command);
            System.out.println("Redid: " + command.getDescription());
        } else {
            System.out.println("Nothing to redo");
        }
    }
    
    public void showHistory() {
        System.out.println("\n=== Command History ===");
        System.out.println("Undo Stack (" + undoStack.size() + " commands):");
        for (int i = undoStack.size() - 1; i >= 0; i--) {
            System.out.println("  " + (undoStack.size() - i) + ". " + undoStack.get(i).getDescription());
        }
        
        System.out.println("Redo Stack (" + redoStack.size() + " commands):");
        for (int i = redoStack.size() - 1; i >= 0; i--) {
            System.out.println("  " + (redoStack.size() - i) + ". " + redoStack.get(i).getDescription());
        }
    }
    
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }
    
    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
}

// Remote control example (another common Command pattern use case)
class Light {
    private boolean isOn = false;
    private int brightness = 0; // 0-100
    
    public void turnOn() {
        isOn = true;
        brightness = 50; // Default brightness
        System.out.println("Light turned ON (brightness: " + brightness + "%)");
    }
    
    public void turnOff() {
        isOn = false;
        brightness = 0;
        System.out.println("Light turned OFF");
    }
    
    public void setBrightness(int level) {
        if (isOn && level >= 0 && level <= 100) {
            brightness = level;
            System.out.println("Light brightness set to " + brightness + "%");
        }
    }
    
    public boolean isOn() { return isOn; }
    public int getBrightness() { return brightness; }
}

class LightOnCommand implements Command {
    private Light light;
    private boolean wasOn;
    private int previousBrightness;
    
    public LightOnCommand(Light light) {
        this.light = light;
    }
    
    @Override
    public void execute() {
        wasOn = light.isOn();
        previousBrightness = light.getBrightness();
        light.turnOn();
    }
    
    @Override
    public void undo() {
        if (!wasOn) {
            light.turnOff();
        } else {
            light.setBrightness(previousBrightness);
        }
    }
    
    @Override
    public String getDescription() {
        return "Turn light ON";
    }
}

class LightOffCommand implements Command {
    private Light light;
    private boolean wasOn;
    private int previousBrightness;
    
    public LightOffCommand(Light light) {
        this.light = light;
    }
    
    @Override
    public void execute() {
        wasOn = light.isOn();
        previousBrightness = light.getBrightness();
        light.turnOff();
    }
    
    @Override
    public void undo() {
        if (wasOn) {
            light.turnOn();
            light.setBrightness(previousBrightness);
        }
    }
    
    @Override
    public String getDescription() {
        return "Turn light OFF";
    }
}

public class CommandDemo {
    public static void main(String[] args) {
        System.out.println("=== Command Pattern Demo ===\n");
        
        // Text Editor Demo
        System.out.println("1. Text Editor with Undo/Redo:");
        TextEditor editor = new TextEditor();
        EditorInvoker invoker = new EditorInvoker(editor);
        
        // Execute various commands
        invoker.executeCommand(new InsertCommand(editor, "Hello", 0));
        editor.showContent();
        
        invoker.executeCommand(new InsertCommand(editor, " World", 5));
        editor.showContent();
        
        invoker.executeCommand(new InsertCommand(editor, "!", 11));
        editor.showContent();
        
        // Demonstrate undo
        System.out.println("\n--- Undo Operations ---");
        invoker.undo();
        editor.showContent();
        
        invoker.undo();
        editor.showContent();
        
        // Demonstrate redo
        System.out.println("\n--- Redo Operations ---");
        invoker.redo();
        editor.showContent();
        
        // More complex operations
        System.out.println("\n--- Complex Operations ---");
        invoker.executeCommand(new ReplaceCommand(editor, 6, 5, "Universe"));
        editor.showContent();
        
        invoker.executeCommand(new DeleteCommand(editor, 0, 6));
        editor.showContent();
        
        // Show command history
        invoker.showHistory();
        
        // Macro command demo
        System.out.println("\n2. Macro Command Demo:");
        MacroCommand formatMacro = new MacroCommand("Format Text");
        formatMacro.addCommand(new InsertCommand(editor, "*** ", 0));
        formatMacro.addCommand(new InsertCommand(editor, " ***", editor.getText().length() + 4));
        
        invoker.executeCommand(formatMacro);
        editor.showContent();
        
        System.out.println("\n--- Undo Macro ---");
        invoker.undo();
        editor.showContent();
        
        // Light control demo (different use case)
        System.out.println("\n3. Light Control Demo:");
        Light light = new Light();
        EditorInvoker lightInvoker = new EditorInvoker(editor); // Reusing invoker
        
        Command lightOn = new LightOnCommand(light);
        Command lightOff = new LightOffCommand(light);
        
        lightInvoker.executeCommand(lightOn);
        lightInvoker.executeCommand(lightOff);
        lightInvoker.executeCommand(lightOn);
        
        System.out.println("\n--- Undo Light Commands ---");
        lightInvoker.undo(); // Undo turn on
        lightInvoker.undo(); // Undo turn off
        lightInvoker.undo(); // Undo initial turn on
        
        System.out.println("\n=== Demo Complete ===");
    }
} 