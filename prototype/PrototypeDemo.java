import java.util.*;

// Prototype interface
interface Cloneable {
    Object clone();
}

// Complex object to demonstrate deep cloning
class Address implements Cloneable {
    private String street;
    private String city;
    private String country;
    private String zipCode;
    
    public Address(String street, String city, String country, String zipCode) {
        this.street = street;
        this.city = city;
        this.country = country;
        this.zipCode = zipCode;
    }
    
    // Copy constructor
    public Address(Address other) {
        this.street = other.street;
        this.city = other.city;
        this.country = other.country;
        this.zipCode = other.zipCode;
    }
    
    @Override
    public Address clone() {
        return new Address(this);
    }
    
    // Getters and setters
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
    
    @Override
    public String toString() {
        return street + ", " + city + ", " + country + " " + zipCode;
    }
}

// Document prototype
class Document implements Cloneable {
    private String title;
    private String content;
    private String author;
    private Date createdDate;
    private Address authorAddress;
    private List<String> tags;
    private Map<String, String> metadata;
    
    public Document(String title, String content, String author, Address authorAddress) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdDate = new Date();
        this.authorAddress = authorAddress;
        this.tags = new ArrayList<>();
        this.metadata = new HashMap<>();
        
        System.out.println("📄 Creating new document: " + title);
        simulateExpensiveInitialization();
    }
    
    // Copy constructor for cloning
    private Document(Document other) {
        this.title = other.title;
        this.content = other.content;
        this.author = other.author;
        this.createdDate = new Date(other.createdDate.getTime());
        this.authorAddress = other.authorAddress.clone(); // Deep clone
        this.tags = new ArrayList<>(other.tags); // Deep clone of list
        this.metadata = new HashMap<>(other.metadata); // Deep clone of map
        
        System.out.println("📄 Cloning document: " + title);
    }
    
    private void simulateExpensiveInitialization() {
        // Simulate expensive operations like template loading, formatting, etc.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("   ✅ Document initialization completed");
    }
    
    @Override
    public Document clone() {
        return new Document(this);
    }
    
    public void addTag(String tag) {
        tags.add(tag);
    }
    
    public void addMetadata(String key, String value) {
        metadata.put(key, value);
    }
    
    // Getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public Date getCreatedDate() { return createdDate; }
    public Address getAuthorAddress() { return authorAddress; }
    public List<String> getTags() { return tags; }
    public Map<String, String> getMetadata() { return metadata; }
    
    @Override
    public String toString() {
        return "Document{" +
               "title='" + title + '\'' +
               ", author='" + author + '\'' +
               ", created=" + createdDate +
               ", address=" + authorAddress +
               ", tags=" + tags +
               ", metadata=" + metadata +
               '}';
    }
}

// Game character prototype
abstract class GameCharacter implements Cloneable {
    protected String name;
    protected int health;
    protected int level;
    protected List<String> abilities;
    protected Map<String, Integer> stats;
    protected String characterClass;
    
    public GameCharacter(String name, String characterClass) {
        this.name = name;
        this.characterClass = characterClass;
        this.health = 100;
        this.level = 1;
        this.abilities = new ArrayList<>();
        this.stats = new HashMap<>();
        initializeCharacter();
    }
    
    // Copy constructor
    protected GameCharacter(GameCharacter other) {
        this.name = other.name;
        this.characterClass = other.characterClass;
        this.health = other.health;
        this.level = other.level;
        this.abilities = new ArrayList<>(other.abilities);
        this.stats = new HashMap<>(other.stats);
    }
    
    protected abstract void initializeCharacter();
    
    public abstract GameCharacter clone();
    
    public void levelUp() {
        level++;
        health += 20;
        System.out.println("🎮 " + name + " leveled up to " + level + "!");
    }
    
    public void addAbility(String ability) {
        abilities.add(ability);
        System.out.println("✨ " + name + " learned: " + ability);
    }
    
    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }
    public int getLevel() { return level; }
    public String getCharacterClass() { return characterClass; }
    public List<String> getAbilities() { return abilities; }
    public Map<String, Integer> getStats() { return stats; }
    
    @Override
    public String toString() {
        return characterClass + "{" +
               "name='" + name + '\'' +
               ", health=" + health +
               ", level=" + level +
               ", abilities=" + abilities +
               ", stats=" + stats +
               '}';
    }
}

// Concrete character types
class Warrior extends GameCharacter {
    public Warrior(String name) {
        super(name, "Warrior");
    }
    
    private Warrior(Warrior other) {
        super(other);
    }
    
    @Override
    protected void initializeCharacter() {
        stats.put("strength", 15);
        stats.put("defense", 12);
        stats.put("agility", 8);
        stats.put("magic", 5);
        abilities.add("Sword Strike");
        abilities.add("Shield Block");
        System.out.println("⚔️  Warrior " + name + " initialized");
    }
    
    @Override
    public Warrior clone() {
        return new Warrior(this);
    }
}

class Mage extends GameCharacter {
    public Mage(String name) {
        super(name, "Mage");
    }
    
    private Mage(Mage other) {
        super(other);
    }
    
    @Override
    protected void initializeCharacter() {
        stats.put("strength", 6);
        stats.put("defense", 8);
        stats.put("agility", 10);
        stats.put("magic", 16);
        abilities.add("Fireball");
        abilities.add("Heal");
        abilities.add("Teleport");
        System.out.println("🔮 Mage " + name + " initialized");
    }
    
    @Override
    public Mage clone() {
        return new Mage(this);
    }
}

class Archer extends GameCharacter {
    public Archer(String name) {
        super(name, "Archer");
    }
    
    private Archer(Archer other) {
        super(other);
    }
    
    @Override
    protected void initializeCharacter() {
        stats.put("strength", 10);
        stats.put("defense", 9);
        stats.put("agility", 15);
        stats.put("magic", 6);
        abilities.add("Arrow Shot");
        abilities.add("Eagle Eye");
        abilities.add("Quick Dodge");
        System.out.println("🏹 Archer " + name + " initialized");
    }
    
    @Override
    public Archer clone() {
        return new Archer(this);
    }
}

// Prototype manager/registry
class CharacterPrototypeManager {
    private Map<String, GameCharacter> prototypes;
    
    public CharacterPrototypeManager() {
        prototypes = new HashMap<>();
        initializePrototypes();
    }
    
    private void initializePrototypes() {
        System.out.println("🎮 Initializing character prototypes...");
        
        // Create template characters
        Warrior warriorTemplate = new Warrior("Template Warrior");
        warriorTemplate.levelUp();
        warriorTemplate.levelUp();
        warriorTemplate.addAbility("Berserker Rage");
        
        Mage mageTemplate = new Mage("Template Mage");
        mageTemplate.levelUp();
        mageTemplate.addAbility("Lightning Bolt");
        
        Archer archerTemplate = new Archer("Template Archer");
        archerTemplate.levelUp();
        archerTemplate.levelUp();
        archerTemplate.levelUp();
        archerTemplate.addAbility("Multi-Shot");
        
        prototypes.put("warrior", warriorTemplate);
        prototypes.put("mage", mageTemplate);
        prototypes.put("archer", archerTemplate);
        
        System.out.println("✅ Prototype initialization complete\n");
    }
    
    public GameCharacter createCharacter(String type, String name) {
        GameCharacter prototype = prototypes.get(type.toLowerCase());
        if (prototype == null) {
            throw new IllegalArgumentException("Unknown character type: " + type);
        }
        
        GameCharacter newCharacter = prototype.clone();
        newCharacter.setName(name);
        
        System.out.println("🎭 Created " + type + " character: " + name);
        return newCharacter;
    }
    
    public void showAvailableTypes() {
        System.out.println("📋 Available character types:");
        for (String type : prototypes.keySet()) {
            GameCharacter prototype = prototypes.get(type);
            System.out.println("   " + type.toUpperCase() + " (Level " + 
                             prototype.getLevel() + ", " + 
                             prototype.getAbilities().size() + " abilities)");
        }
    }
}

// Document template manager
class DocumentTemplateManager {
    private Map<String, Document> templates;
    
    public DocumentTemplateManager() {
        templates = new HashMap<>();
        initializeTemplates();
    }
    
    private void initializeTemplates() {
        System.out.println("📝 Initializing document templates...");
        
        // Create business letter template
        Address businessAddress = new Address("123 Business St", "Business City", "Business Country", "12345");
        Document businessTemplate = new Document("Business Letter Template", 
                                                "Dear [Recipient],\n\n[Content]\n\nSincerely,\n[Sender]", 
                                                "Template Author", businessAddress);
        businessTemplate.addTag("business");
        businessTemplate.addTag("formal");
        businessTemplate.addMetadata("type", "letter");
        businessTemplate.addMetadata("format", "formal");
        
        // Create report template
        Address reportAddress = new Address("456 Report Ave", "Report City", "Report Country", "67890");
        Document reportTemplate = new Document("Report Template",
                                             "EXECUTIVE SUMMARY\n\n[Summary]\n\nDETAILS\n\n[Details]\n\nCONCLUSION\n\n[Conclusion]",
                                             "Report Author", reportAddress);
        reportTemplate.addTag("report");
        reportTemplate.addTag("business");
        reportTemplate.addMetadata("type", "report");
        reportTemplate.addMetadata("format", "structured");
        
        templates.put("business-letter", businessTemplate);
        templates.put("report", reportTemplate);
        
        System.out.println("✅ Document template initialization complete\n");
    }
    
    public Document createDocument(String templateType, String title, String author) {
        Document template = templates.get(templateType);
        if (template == null) {
            throw new IllegalArgumentException("Unknown template type: " + templateType);
        }
        
        Document newDocument = template.clone();
        newDocument.setTitle(title);
        newDocument.setAuthor(author);
        
        System.out.println("📋 Created document from template: " + templateType);
        return newDocument;
    }
    
    public void showAvailableTemplates() {
        System.out.println("📋 Available document templates:");
        for (String type : templates.keySet()) {
            Document template = templates.get(type);
            System.out.println("   " + type.toUpperCase() + " (" + 
                             template.getTags().size() + " tags, " +
                             template.getMetadata().size() + " metadata items)");
        }
    }
}

public class PrototypeDemo {
    public static void main(String[] args) {
        System.out.println("=== Prototype Pattern Demo ===\n");
        
        // 1. Document Cloning Demo
        System.out.println("1. Document Template System:");
        System.out.println("=".repeat(50));
        
        DocumentTemplateManager docManager = new DocumentTemplateManager();
        docManager.showAvailableTemplates();
        
        // Create documents from templates
        Document letter1 = docManager.createDocument("business-letter", "Welcome Letter", "John Doe");
        Document letter2 = docManager.createDocument("business-letter", "Follow-up Letter", "Jane Smith");
        Document report1 = docManager.createDocument("report", "Q1 Sales Report", "Bob Johnson");
        
        // Modify cloned documents independently
        letter1.setContent("Dear New Customer,\n\nWelcome to our company!\n\nSincerely,\nJohn Doe");
        letter1.addTag("welcome");
        
        letter2.setContent("Dear Valued Customer,\n\nThank you for your business.\n\nSincerely,\nJane Smith");
        letter2.addTag("follow-up");
        
        // Verify independence
        System.out.println("\n📄 Letter 1 tags: " + letter1.getTags());
        System.out.println("📄 Letter 2 tags: " + letter2.getTags());
        System.out.println("📄 Report 1 tags: " + report1.getTags());
        
        // 2. Game Character Cloning Demo
        System.out.println("\n\n2. Game Character Creation System:");
        System.out.println("=".repeat(50));
        
        CharacterPrototypeManager charManager = new CharacterPrototypeManager();
        charManager.showAvailableTypes();
        
        // Create characters from prototypes
        System.out.println("\n🎮 Creating player characters:");
        GameCharacter player1 = charManager.createCharacter("warrior", "Conan");
        GameCharacter player2 = charManager.createCharacter("mage", "Gandalf");
        GameCharacter player3 = charManager.createCharacter("archer", "Legolas");
        
        // Create multiple characters of same type
        System.out.println("\n🎮 Creating NPC army:");
        GameCharacter[] warriors = new GameCharacter[3];
        for (int i = 0; i < warriors.length; i++) {
            warriors[i] = charManager.createCharacter("warrior", "Warrior" + (i + 1));
        }
        
        // Modify characters independently
        System.out.println("\n🎮 Character customization:");
        player1.addAbility("Whirlwind Attack");
        player2.addAbility("Meteor");
        player3.levelUp();
        
        // Show character independence
        System.out.println("\n👥 Character comparison:");
        System.out.println("Player Warrior abilities: " + player1.getAbilities().size());
        System.out.println("NPC Warrior abilities: " + warriors[0].getAbilities().size());
        System.out.println("Player Mage level: " + player2.getLevel());
        System.out.println("Player Archer level: " + player3.getLevel());
        
        // 3. Performance Comparison
        System.out.println("\n\n3. Performance Comparison:");
        System.out.println("=".repeat(50));
        
        // Time direct creation
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 3; i++) {
            new Warrior("DirectWarrior" + i);
        }
        long directCreationTime = System.currentTimeMillis() - startTime;
        
        // Time prototype creation
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 3; i++) {
            charManager.createCharacter("warrior", "ProtoWarrior" + i);
        }
        long prototypeCreationTime = System.currentTimeMillis() - startTime;
        
        System.out.println("⏱️  Direct creation time: " + directCreationTime + "ms");
        System.out.println("⏱️  Prototype creation time: " + prototypeCreationTime + "ms");
        System.out.println("⚡ Prototype is " + 
                          (directCreationTime > prototypeCreationTime ? "faster" : "slower") +
                          " (Note: Benefits increase with more complex initialization)");
        
        System.out.println("\n=== Prototype Pattern Benefits Demonstrated ===");
        System.out.println("✅ Avoid expensive initialization by cloning");
        System.out.println("✅ Create objects without knowing their concrete classes");
        System.out.println("✅ Add and remove prototypes at runtime");
        System.out.println("✅ Configure applications with classes dynamically");
        System.out.println("✅ Deep cloning preserves object independence");
        
        System.out.println("\n=== Demo Complete ===");
    }
} 