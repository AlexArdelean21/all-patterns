import java.util.*;

// Flyweight interface
interface CharacterFlyweight {
    void display(int row, int column, String font, int size, String color);
}

// Concrete flyweight - stores intrinsic state
class Character implements CharacterFlyweight {
    private final char character; // Intrinsic state - shared
    
    public Character(char character) {
        this.character = character;
        System.out.println("🔤 Creating flyweight for character: '" + character + "'");
    }
    
    @Override
    public void display(int row, int column, String font, int size, String color) {
        // Extrinsic state is passed as parameters
        System.out.println("📝 Displaying '" + character + "' at (" + row + "," + column + 
                          ") font:" + font + " size:" + size + " color:" + color);
    }
    
    public char getCharacter() {
        return character;
    }
}

// Flyweight factory
class CharacterFactory {
    private static final Map<java.lang.Character, CharacterFlyweight> flyweights = new HashMap<>();
    private static int creationCount = 0;
    
    public static CharacterFlyweight getCharacter(char character) {
        CharacterFlyweight flyweight = flyweights.get(character);
        
        if (flyweight == null) {
            flyweight = new Character(character);
            flyweights.put(character, flyweight);
            creationCount++;
        }
        
        return flyweight;
    }
    
    public static int getCreationCount() {
        return creationCount;
    }
    
    public static int getFlyweightCount() {
        return flyweights.size();
    }
    
    public static void showStatistics() {
        System.out.println("📊 Flyweight Statistics:");
        System.out.println("   Unique flyweights created: " + creationCount);
        System.out.println("   Flyweights in pool: " + flyweights.size());
        System.out.println("   Memory efficiency: " + 
                          (creationCount == flyweights.size() ? "Optimal" : "Suboptimal"));
    }
}

// Context class that uses flyweight
class CharacterContext {
    private int row;
    private int column;
    private String font;
    private int size;
    private String color;
    private CharacterFlyweight flyweight;
    
    public CharacterContext(char character, int row, int column, String font, int size, String color) {
        this.flyweight = CharacterFactory.getCharacter(character);
        this.row = row;
        this.column = column;
        this.font = font;
        this.size = size;
        this.color = color;
    }
    
    public void display() {
        flyweight.display(row, column, font, size, color);
    }
    
    // Getters and setters for extrinsic state
    public void setPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }
    
    public void setFont(String font) { this.font = font; }
    public void setSize(int size) { this.size = size; }
    public void setColor(String color) { this.color = color; }
}

// Document class that manages many characters
class Document {
    private List<CharacterContext> characters;
    private String documentName;
    
    public Document(String documentName) {
        this.documentName = documentName;
        this.characters = new ArrayList<>();
    }
    
    public void addCharacter(char character, int row, int column, String font, int size, String color) {
        CharacterContext context = new CharacterContext(character, row, column, font, size, color);
        characters.add(context);
    }
    
    public void addText(String text, int startRow, int startColumn, String font, int size, String color) {
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == '\n') {
                startRow++;
                startColumn = 0;
            } else {
                addCharacter(ch, startRow, startColumn + i, font, size, color);
            }
        }
    }
    
    public void display() {
        System.out.println("\n📄 Displaying document: " + documentName);
        for (CharacterContext context : characters) {
            context.display();
        }
    }
    
    public int getCharacterCount() {
        return characters.size();
    }
    
    public void showMemoryUsage() {
        System.out.println("📊 Document Memory Usage:");
        System.out.println("   Document: " + documentName);
        System.out.println("   Total characters: " + characters.size());
        System.out.println("   Unique flyweights: " + CharacterFactory.getFlyweightCount());
        System.out.println("   Memory savings: " + 
                          (characters.size() - CharacterFactory.getFlyweightCount()) + 
                          " character objects saved");
    }
}

// Particle system example for flyweight pattern
interface ParticleFlyweight {
    void move(double x, double y, double velocityX, double velocityY, String color, double size);
}

class ParticleType implements ParticleFlyweight {
    private final String name;
    private final String texture;
    private final String behavior; // Intrinsic state
    
    public ParticleType(String name, String texture, String behavior) {
        this.name = name;
        this.texture = texture;
        this.behavior = behavior;
        System.out.println("🎨 Creating particle type: " + name);
    }
    
    @Override
    public void move(double x, double y, double velocityX, double velocityY, String color, double size) {
        // Simulate particle movement and rendering
        System.out.println("🎆 " + name + " particle at (" + 
                          String.format("%.1f", x) + "," + String.format("%.1f", y) + 
                          ") moving (" + String.format("%.1f", velocityX) + "," + 
                          String.format("%.1f", velocityY) + ") " + 
                          "color:" + color + " size:" + size + " texture:" + texture);
    }
    
    public String getName() { return name; }
}

class ParticleFactory {
    private static final Map<String, ParticleFlyweight> particleTypes = new HashMap<>();
    
    public static ParticleFlyweight getParticleType(String name, String texture, String behavior) {
        String key = name + "_" + texture + "_" + behavior;
        ParticleFlyweight particleType = particleTypes.get(key);
        
        if (particleType == null) {
            particleType = new ParticleType(name, texture, behavior);
            particleTypes.put(key, particleType);
        }
        
        return particleType;
    }
    
    public static int getParticleTypeCount() {
        return particleTypes.size();
    }
    
    public static void showParticleTypes() {
        System.out.println("🎨 Available particle types: " + particleTypes.keySet());
    }
}

class Particle {
    private double x, y; // Position
    private double velocityX, velocityY; // Velocity
    private String color;
    private double size;
    private ParticleFlyweight type; // Reference to flyweight
    
    public Particle(double x, double y, double velocityX, double velocityY, 
                   String color, double size, ParticleFlyweight type) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.color = color;
        this.size = size;
        this.type = type;
    }
    
    public void update() {
        // Update position
        x += velocityX;
        y += velocityY;
        
        // Use flyweight for rendering
        type.move(x, y, velocityX, velocityY, color, size);
    }
    
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public void setVelocity(double vx, double vy) {
        this.velocityX = vx;
        this.velocityY = vy;
    }
}

class ParticleSystem {
    private List<Particle> particles;
    private String systemName;
    
    public ParticleSystem(String systemName) {
        this.systemName = systemName;
        this.particles = new ArrayList<>();
    }
    
    public void addParticle(double x, double y, double vx, double vy, 
                          String color, double size, String particleTypeName, 
                          String texture, String behavior) {
        ParticleFlyweight type = ParticleFactory.getParticleType(particleTypeName, texture, behavior);
        Particle particle = new Particle(x, y, vx, vy, color, size, type);
        particles.add(particle);
    }
    
    public void update() {
        System.out.println("\n🎆 Updating particle system: " + systemName);
        for (Particle particle : particles) {
            particle.update();
        }
    }
    
    public void showStatistics() {
        System.out.println("\n📊 Particle System Statistics:");
        System.out.println("   System: " + systemName);
        System.out.println("   Total particles: " + particles.size());
        System.out.println("   Particle types: " + ParticleFactory.getParticleTypeCount());
        System.out.println("   Memory efficiency: " + 
                          (particles.size() / Math.max(1, ParticleFactory.getParticleTypeCount())) + 
                          " particles per type");
    }
}

// Memory usage simulator
class MemorySimulator {
    public static void simulateWithoutFlyweight(int characterCount) {
        System.out.println("\n💾 Memory simulation WITHOUT Flyweight:");
        System.out.println("   Characters: " + characterCount);
        System.out.println("   Memory per character object: ~64 bytes");
        System.out.println("   Total memory: ~" + (characterCount * 64) + " bytes");
    }
    
    public static void simulateWithFlyweight(int characterCount, int uniqueCharacters) {
        System.out.println("\n💾 Memory simulation WITH Flyweight:");
        System.out.println("   Characters: " + characterCount);
        System.out.println("   Unique flyweights: " + uniqueCharacters);
        System.out.println("   Memory per flyweight: ~32 bytes");
        System.out.println("   Memory per context: ~32 bytes");
        int totalMemory = (uniqueCharacters * 32) + (characterCount * 32);
        System.out.println("   Total memory: ~" + totalMemory + " bytes");
        
        int savedMemory = (characterCount * 64) - totalMemory;
        System.out.println("   Memory saved: ~" + savedMemory + " bytes (" + 
                          String.format("%.1f", (savedMemory / (double)(characterCount * 64)) * 100) + "%)");
    }
}

public class FlyweightDemo {
    public static void main(String[] args) {
        System.out.println("=== Flyweight Pattern Demo ===\n");
        
        // 1. Text Editor Flyweight Demo
        System.out.println("1. Text Editor Character Flyweights:");
        System.out.println("=".repeat(50));
        
        Document doc1 = new Document("Sample Document 1");
        
        // Add some text - notice repeated characters
        doc1.addText("Hello World!", 1, 1, "Arial", 12, "Black");
        doc1.addText("Hello Again!", 2, 1, "Arial", 12, "Blue");
        doc1.addText("World of Programming", 3, 1, "Times", 14, "Red");
        
        CharacterFactory.showStatistics();
        doc1.showMemoryUsage();
        
        // Add more documents to show sharing
        System.out.println("\n📄 Creating second document:");
        Document doc2 = new Document("Sample Document 2");
        doc2.addText("Programming is fun!", 1, 1, "Courier", 10, "Green");
        doc2.addText("Flyweight pattern rocks!", 2, 1, "Helvetica", 12, "Purple");
        
        CharacterFactory.showStatistics();
        doc2.showMemoryUsage();
        
        // 2. Particle System Demo
        System.out.println("\n\n2. Particle System Flyweight Demo:");
        System.out.println("=".repeat(50));
        
        ParticleSystem explosionSystem = new ParticleSystem("Explosion Effect");
        
        // Create explosion particles - many particles, few types
        for (int i = 0; i < 10; i++) {
            double angle = (2 * Math.PI * i) / 10;
            double speed = 2.0 + Math.random();
            double vx = Math.cos(angle) * speed;
            double vy = Math.sin(angle) * speed;
            
            if (i < 5) {
                explosionSystem.addParticle(50, 50, vx, vy, "Orange", 3.0, 
                                          "Fire", "flame.png", "fade");
            } else {
                explosionSystem.addParticle(50, 50, vx, vy, "Yellow", 2.0, 
                                          "Spark", "spark.png", "linear");
            }
        }
        
        // Add smoke particles
        for (int i = 0; i < 8; i++) {
            explosionSystem.addParticle(50 + Math.random() * 10, 50 + Math.random() * 10, 
                                      Math.random() - 0.5, -Math.random() * 2, 
                                      "Gray", 4.0, "Smoke", "smoke.png", "drift");
        }
        
        explosionSystem.showStatistics();
        ParticleFactory.showParticleTypes();
        
        // Update particle system
        explosionSystem.update();
        
        // 3. Memory Efficiency Demo
        System.out.println("\n\n3. Memory Efficiency Comparison:");
        System.out.println("=".repeat(50));
        
        int totalCharacters = doc1.getCharacterCount() + doc2.getCharacterCount();
        int uniqueCharacters = CharacterFactory.getFlyweightCount();
        
        MemorySimulator.simulateWithoutFlyweight(totalCharacters);
        MemorySimulator.simulateWithFlyweight(totalCharacters, uniqueCharacters);
        
        // 4. Large Scale Demo
        System.out.println("\n\n4. Large Scale Flyweight Benefits:");
        System.out.println("=".repeat(50));
        
        // Simulate a large document
        System.out.println("🔄 Simulating large document creation...");
        Document largeDoc = new Document("Large Document");
        
        // Add repetitive text
        String[] sentences = {
            "The quick brown fox jumps over the lazy dog. ",
            "Pack my box with five dozen liquor jugs. ",
            "How razorback-jumping frogs can level six piqued gymnasts! "
        };
        
        for (int i = 0; i < 50; i++) { // 50 repetitions
            for (String sentence : sentences) {
                largeDoc.addText(sentence, i * 3 + 1, 1, "Arial", 12, "Black");
            }
        }
        
        System.out.println("\n📊 Large Document Statistics:");
        System.out.println("   Total characters in large document: " + largeDoc.getCharacterCount());
        System.out.println("   Unique flyweights needed: " + CharacterFactory.getFlyweightCount());
        
        // Memory comparison for large scale
        MemorySimulator.simulateWithoutFlyweight(largeDoc.getCharacterCount());
        MemorySimulator.simulateWithFlyweight(largeDoc.getCharacterCount(), CharacterFactory.getFlyweightCount());
        
        // 5. Demonstrate intrinsic vs extrinsic state
        System.out.println("\n\n5. Intrinsic vs Extrinsic State Demo:");
        System.out.println("=".repeat(50));
        
        CharacterFlyweight letterA = CharacterFactory.getCharacter('A');
        
        System.out.println("🔤 Same flyweight 'A' used with different extrinsic state:");
        letterA.display(1, 1, "Arial", 12, "Red");
        letterA.display(5, 10, "Times", 16, "Blue");
        letterA.display(10, 20, "Courier", 14, "Green");
        
        System.out.println("\n📝 Intrinsic state (stored in flyweight): character = 'A'");
        System.out.println("📝 Extrinsic state (passed as parameters): position, font, size, color");
        
        System.out.println("\n=== Flyweight Pattern Benefits Demonstrated ===");
        System.out.println("✅ Dramatic memory reduction for repeated objects");
        System.out.println("✅ Separation of intrinsic and extrinsic state");
        System.out.println("✅ Object sharing through factory pattern");
        System.out.println("✅ Scalability for large numbers of similar objects");
        System.out.println("✅ Performance improvement through reduced object creation");
        
        System.out.println("\n=== Demo Complete ===");
    }
} 