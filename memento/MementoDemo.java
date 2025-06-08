import java.util.*;

// Memento class - stores state
class TextMemento {
    private final String content;
    private final int cursorPosition;
    private final long timestamp;
    private final String operation;
    
    public TextMemento(String content, int cursorPosition, String operation) {
        this.content = content;
        this.cursorPosition = cursorPosition;
        this.operation = operation;
        this.timestamp = System.currentTimeMillis();
    }
    
    public String getContent() { return content; }
    public int getCursorPosition() { return cursorPosition; }
    public long getTimestamp() { return timestamp; }
    public String getOperation() { return operation; }
    
    @Override
    public String toString() {
        return "Memento{operation='" + operation + "', timestamp=" + new Date(timestamp) + "}";
    }
}

// Originator class - creates and restores mementos
class TextEditor {
    private StringBuilder content;
    private int cursorPosition;
    private String filename;
    
    public TextEditor(String filename) {
        this.filename = filename;
        this.content = new StringBuilder();
        this.cursorPosition = 0;
    }
    
    // Create memento
    public TextMemento createMemento(String operation) {
        return new TextMemento(content.toString(), cursorPosition, operation);
    }
    
    // Restore from memento
    public void restoreFromMemento(TextMemento memento) {
        this.content = new StringBuilder(memento.getContent());
        this.cursorPosition = memento.getCursorPosition();
        System.out.println("🔄 Restored to state: " + memento.getOperation());
    }
    
    // Editor operations
    public void insertText(String text) {
        content.insert(cursorPosition, text);
        cursorPosition += text.length();
        System.out.println("✏️  Inserted: '" + text + "' at position " + (cursorPosition - text.length()));
    }
    
    public void deleteText(int length) {
        if (cursorPosition >= length) {
            String deleted = content.substring(cursorPosition - length, cursorPosition);
            content.delete(cursorPosition - length, cursorPosition);
            cursorPosition -= length;
            System.out.println("🗑️  Deleted: '" + deleted + "'");
        }
    }
    
    public void moveCursor(int position) {
        if (position >= 0 && position <= content.length()) {
            cursorPosition = position;
            System.out.println("📍 Cursor moved to position: " + position);
        }
    }
    
    public void replaceText(int start, int end, String replacement) {
        if (start >= 0 && end <= content.length() && start <= end) {
            String original = content.substring(start, end);
            content.replace(start, end, replacement);
            cursorPosition = start + replacement.length();
            System.out.println("🔄 Replaced '" + original + "' with '" + replacement + "'");
        }
    }
    
    public void clear() {
        content.setLength(0);
        cursorPosition = 0;
        System.out.println("🧹 Editor cleared");
    }
    
    // Display current state
    public void showContent() {
        System.out.println("📄 File: " + filename);
        System.out.println("📄 Content: \"" + content.toString() + "\"");
        System.out.println("📄 Cursor at: " + cursorPosition + " | Length: " + content.length());
    }
    
    public String getContent() { return content.toString(); }
    public int getCursorPosition() { return cursorPosition; }
    public String getFilename() { return filename; }
}

// Caretaker class - manages mementos
class EditorHistory {
    private List<TextMemento> history;
    private int currentIndex;
    private final int maxHistorySize;
    
    public EditorHistory(int maxHistorySize) {
        this.history = new ArrayList<>();
        this.currentIndex = -1;
        this.maxHistorySize = maxHistorySize;
    }
    
    public void save(TextMemento memento) {
        // Remove any mementos after current index (when new operation after undo)
        while (history.size() > currentIndex + 1) {
            history.remove(history.size() - 1);
        }
        
        // Add new memento
        history.add(memento);
        currentIndex++;
        
        // Maintain max history size
        if (history.size() > maxHistorySize) {
            history.remove(0);
            currentIndex--;
        }
        
        System.out.println("💾 Saved state: " + memento.getOperation() + " (History: " + history.size() + " items)");
    }
    
    public TextMemento undo() {
        if (canUndo()) {
            currentIndex--;
            TextMemento memento = history.get(currentIndex);
            System.out.println("↶ Undo to: " + memento.getOperation());
            return memento;
        }
        System.out.println("❌ Cannot undo - no more history");
        return null;
    }
    
    public TextMemento redo() {
        if (canRedo()) {
            currentIndex++;
            TextMemento memento = history.get(currentIndex);
            System.out.println("↷ Redo to: " + memento.getOperation());
            return memento;
        }
        System.out.println("❌ Cannot redo - no more forward history");
        return null;
    }
    
    public boolean canUndo() {
        return currentIndex > 0;
    }
    
    public boolean canRedo() {
        return currentIndex < history.size() - 1;
    }
    
    public void showHistory() {
        System.out.println("\n📚 Editor History:");
        for (int i = 0; i < history.size(); i++) {
            String indicator = (i == currentIndex) ? " ← CURRENT" : "";
            System.out.println("  " + i + ". " + history.get(i).getOperation() + indicator);
        }
        System.out.println("Can undo: " + canUndo() + " | Can redo: " + canRedo());
    }
    
    public void clear() {
        history.clear();
        currentIndex = -1;
        System.out.println("🗑️  History cleared");
    }
}

// Game state memento
class GameStateMemento {
    private final String playerName;
    private final int level;
    private final int health;
    private final int score;
    private final Map<String, Integer> inventory;
    private final String location;
    private final long saveTime;
    private final String saveName;
    
    public GameStateMemento(String playerName, int level, int health, int score, 
                           Map<String, Integer> inventory, String location, String saveName) {
        this.playerName = playerName;
        this.level = level;
        this.health = health;
        this.score = score;
        this.inventory = new HashMap<>(inventory); // Deep copy
        this.location = location;
        this.saveName = saveName;
        this.saveTime = System.currentTimeMillis();
    }
    
    // Getters
    public String getPlayerName() { return playerName; }
    public int getLevel() { return level; }
    public int getHealth() { return health; }
    public int getScore() { return score; }
    public Map<String, Integer> getInventory() { return new HashMap<>(inventory); }
    public String getLocation() { return location; }
    public long getSaveTime() { return saveTime; }
    public String getSaveName() { return saveName; }
    
    @Override
    public String toString() {
        return saveName + " - " + playerName + " (Level " + level + ", Score " + score + ") at " + 
               new Date(saveTime);
    }
}

// Game class (Originator)
class Game {
    private String playerName;
    private int level;
    private int health;
    private int score;
    private Map<String, Integer> inventory;
    private String currentLocation;
    
    public Game(String playerName) {
        this.playerName = playerName;
        this.level = 1;
        this.health = 100;
        this.score = 0;
        this.inventory = new HashMap<>();
        this.currentLocation = "Starting Village";
        
        // Initialize starting inventory
        inventory.put("Gold", 100);
        inventory.put("Health Potions", 3);
    }
    
    public GameStateMemento createSave(String saveName) {
        System.out.println("💾 Creating save: " + saveName);
        return new GameStateMemento(playerName, level, health, score, inventory, currentLocation, saveName);
    }
    
    public void loadSave(GameStateMemento save) {
        this.playerName = save.getPlayerName();
        this.level = save.getLevel();
        this.health = save.getHealth();
        this.score = save.getScore();
        this.inventory = save.getInventory();
        this.currentLocation = save.getLocation();
        
        System.out.println("📂 Loaded save: " + save.getSaveName());
    }
    
    // Game actions
    public void levelUp() {
        level++;
        health = Math.min(health + 20, 100);
        score += 100;
        System.out.println("🎉 Level up! Now level " + level);
    }
    
    public void takeDamage(int damage) {
        health = Math.max(0, health - damage);
        System.out.println("💔 Took " + damage + " damage. Health: " + health);
    }
    
    public void addScore(int points) {
        score += points;
        System.out.println("⭐ Score increased by " + points + ". Total: " + score);
    }
    
    public void addItem(String item, int quantity) {
        inventory.put(item, inventory.getOrDefault(item, 0) + quantity);
        System.out.println("🎒 Added " + quantity + "x " + item);
    }
    
    public void useItem(String item, int quantity) {
        int current = inventory.getOrDefault(item, 0);
        if (current >= quantity) {
            inventory.put(item, current - quantity);
            System.out.println("🎒 Used " + quantity + "x " + item);
            
            // Special effects
            if (item.equals("Health Potions")) {
                health = Math.min(100, health + 25 * quantity);
                System.out.println("💚 Health restored to " + health);
            }
        } else {
            System.out.println("❌ Not enough " + item + " (have " + current + ", need " + quantity + ")");
        }
    }
    
    public void moveToLocation(String location) {
        currentLocation = location;
        System.out.println("🗺️  Moved to: " + location);
    }
    
    public void showStatus() {
        System.out.println("\n👤 Player Status:");
        System.out.println("   Name: " + playerName);
        System.out.println("   Level: " + level);
        System.out.println("   Health: " + health + "/100");
        System.out.println("   Score: " + score);
        System.out.println("   Location: " + currentLocation);
        System.out.println("   Inventory: " + inventory);
    }
}

// Save game manager (Caretaker)
class SaveGameManager {
    private Map<String, GameStateMemento> saves;
    private final int maxSaves;
    
    public SaveGameManager(int maxSaves) {
        this.saves = new HashMap<>();
        this.maxSaves = maxSaves;
    }
    
    public void save(GameStateMemento save) {
        if (saves.size() >= maxSaves && !saves.containsKey(save.getSaveName())) {
            // Remove oldest save
            String oldestSave = saves.entrySet().stream()
                    .min(Map.Entry.comparingByValue((s1, s2) -> Long.compare(s1.getSaveTime(), s2.getSaveTime())))
                    .map(Map.Entry::getKey)
                    .orElse(null);
            
            if (oldestSave != null) {
                saves.remove(oldestSave);
                System.out.println("🗑️  Removed oldest save: " + oldestSave);
            }
        }
        
        saves.put(save.getSaveName(), save);
        System.out.println("💾 Save stored: " + save.getSaveName());
    }
    
    public GameStateMemento load(String saveName) {
        GameStateMemento save = saves.get(saveName);
        if (save != null) {
            System.out.println("📂 Loading save: " + saveName);
            return save;
        } else {
            System.out.println("❌ Save not found: " + saveName);
            return null;
        }
    }
    
    public void deleteSave(String saveName) {
        if (saves.remove(saveName) != null) {
            System.out.println("🗑️  Deleted save: " + saveName);
        } else {
            System.out.println("❌ Save not found: " + saveName);
        }
    }
    
    public void listSaves() {
        System.out.println("\n💾 Available Saves:");
        if (saves.isEmpty()) {
            System.out.println("   No saves available");
        } else {
            saves.values().stream()
                    .sorted((s1, s2) -> Long.compare(s2.getSaveTime(), s1.getSaveTime()))
                    .forEach(save -> System.out.println("   " + save));
        }
    }
}

public class MementoDemo {
    public static void main(String[] args) {
        System.out.println("=== Memento Pattern Demo ===\n");
        
        // 1. Text Editor Undo/Redo Demo
        System.out.println("1. Text Editor with Undo/Redo:");
        System.out.println("=".repeat(50));
        
        TextEditor editor = new TextEditor("document.txt");
        EditorHistory history = new EditorHistory(10);
        
        // Save initial state
        history.save(editor.createMemento("Initial state"));
        
        // Edit document
        editor.insertText("Hello");
        history.save(editor.createMemento("Insert 'Hello'"));
        editor.showContent();
        
        editor.insertText(" World");
        history.save(editor.createMemento("Insert ' World'"));
        editor.showContent();
        
        editor.insertText("!");
        history.save(editor.createMemento("Insert '!'"));
        editor.showContent();
        
        // Undo operations
        System.out.println("\n🔄 Undo operations:");
        TextMemento memento = history.undo();
        if (memento != null) {
            editor.restoreFromMemento(memento);
            editor.showContent();
        }
        
        memento = history.undo();
        if (memento != null) {
            editor.restoreFromMemento(memento);
            editor.showContent();
        }
        
        // Redo operations
        System.out.println("\n🔄 Redo operations:");
        memento = history.redo();
        if (memento != null) {
            editor.restoreFromMemento(memento);
            editor.showContent();
        }
        
        // New edit after undo (should clear redo history)
        editor.insertText(" Everyone");
        history.save(editor.createMemento("Insert ' Everyone'"));
        editor.showContent();
        
        history.showHistory();
        
        // 2. Game Save/Load Demo
        System.out.println("\n\n2. Game Save/Load System:");
        System.out.println("=".repeat(50));
        
        Game game = new Game("Hero");
        SaveGameManager saveManager = new SaveGameManager(5);
        
        game.showStatus();
        
        // Save initial state
        saveManager.save(game.createSave("NewGame"));
        
        // Play the game
        System.out.println("\n🎮 Playing the game...");
        game.moveToLocation("Forest");
        game.addScore(50);
        game.addItem("Sword", 1);
        saveManager.save(game.createSave("Forest_Checkpoint"));
        
        game.levelUp();
        game.moveToLocation("Castle");
        game.addScore(150);
        game.addItem("Shield", 1);
        game.takeDamage(30);
        saveManager.save(game.createSave("Castle_Battle"));
        
        game.levelUp();
        game.moveToLocation("Dragon's Lair");
        game.takeDamage(50);
        game.useItem("Health Potions", 2);
        game.addScore(500);
        saveManager.save(game.createSave("Dragon_Victory"));
        
        game.showStatus();
        saveManager.listSaves();
        
        // Load previous save
        System.out.println("\n📂 Loading previous save...");
        GameStateMemento save = saveManager.load("Forest_Checkpoint");
        if (save != null) {
            game.loadSave(save);
            game.showStatus();
        }
        
        // Continue from loaded state
        System.out.println("\n🎮 Continuing from Forest...");
        game.moveToLocation("Mountain");
        game.addScore(75);
        saveManager.save(game.createSave("Mountain_Path"));
        
        saveManager.listSaves();
        
        // 3. Demonstration of memento independence
        System.out.println("\n\n3. Memento Independence Test:");
        System.out.println("=".repeat(50));
        
        TextEditor editor2 = new TextEditor("test.txt");
        editor2.insertText("Original text");
        TextMemento snapshot = editor2.createMemento("Snapshot");
        
        // Modify editor after creating memento
        editor2.insertText(" - Modified");
        System.out.println("📄 Current editor content: \"" + editor2.getContent() + "\"");
        
        // Restore from memento
        editor2.restoreFromMemento(snapshot);
        System.out.println("📄 After restore: \"" + editor2.getContent() + "\"");
        
        System.out.println("\n=== Memento Pattern Benefits Demonstrated ===");
        System.out.println("✅ State preservation without violating encapsulation");
        System.out.println("✅ Undo/Redo functionality implementation");
        System.out.println("✅ Checkpoint and rollback capabilities");
        System.out.println("✅ State independence from originator");
        System.out.println("✅ Multiple save slots management");
        
        System.out.println("\n=== Demo Complete ===");
    }
} 