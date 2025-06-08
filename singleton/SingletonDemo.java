// Thread-safe Singleton implementation
class DatabaseConnection {
    // Volatile ensures visibility across threads
    private static volatile DatabaseConnection instance;
    private String connectionString;
    private boolean isConnected;
    
    // Private constructor prevents external instantiation
    private DatabaseConnection() {
        // Simulate expensive connection setup
        try {
            Thread.sleep(100); // Simulate connection delay
            this.connectionString = "jdbc:mysql://localhost:3306/mydb";
            this.isConnected = true;
            System.out.println("Database connection established: " + connectionString);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    // Double-checked locking for thread safety
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }
    
    public void executeQuery(String query) {
        if (!isConnected) {
            throw new IllegalStateException("Database not connected");
        }
        System.out.println("Executing query: " + query);
    }
    
    public String getConnectionString() {
        return connectionString;
    }
    
    public void disconnect() {
        isConnected = false;
        System.out.println("Database connection closed");
    }
    
    // Prevent cloning
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Singleton cannot be cloned");
    }
}

// Enum-based Singleton (Joshua Bloch's approach)
enum Logger {
    INSTANCE;
    
    private String logLevel;
    
    Logger() {
        this.logLevel = "INFO";
    }
    
    public void log(String message) {
        System.out.println("[" + logLevel + "] " + message);
    }
    
    public void setLogLevel(String level) {
        this.logLevel = level;
    }
    
    public String getLogLevel() {
        return logLevel;
    }
}

// Application Configuration Singleton
class AppConfig {
    private static AppConfig instance;
    private String appName;
    private String version;
    private java.util.Properties properties;
    
    private AppConfig() {
        appName = "MyApplication";
        version = "1.0.0";
        properties = new java.util.Properties();
        loadDefaultConfig();
    }
    
    public static synchronized AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }
    
    private void loadDefaultConfig() {
        properties.setProperty("max.connections", "100");
        properties.setProperty("timeout", "30");
        properties.setProperty("debug.mode", "false");
        System.out.println("Configuration loaded with defaults");
    }
    
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }
    
    public String getAppInfo() {
        return appName + " v" + version;
    }
}

// Thread-safe demonstration
class DatabaseWorker extends Thread {
    private String workerName;
    
    public DatabaseWorker(String name) {
        this.workerName = name;
    }
    
    @Override
    public void run() {
        System.out.println(workerName + " requesting database connection...");
        DatabaseConnection db = DatabaseConnection.getInstance();
        System.out.println(workerName + " got connection: " + 
                          db.getConnectionString());
        db.executeQuery("SELECT * FROM users WHERE worker = '" + workerName + "'");
    }
}

public class SingletonDemo {
    public static void main(String[] args) {
        System.out.println("=== Singleton Pattern Demo ===\n");
        
        // Test 1: Basic Singleton usage
        System.out.println("1. Basic Database Connection Singleton:");
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        DatabaseConnection db2 = DatabaseConnection.getInstance();
        
        System.out.println("Are both references the same? " + (db1 == db2));
        System.out.println("Hash codes - db1: " + db1.hashCode() + 
                          ", db2: " + db2.hashCode());
        
        db1.executeQuery("SELECT * FROM products");
        db2.executeQuery("SELECT * FROM orders");
        
        System.out.println("\n2. Enum-based Logger Singleton:");
        Logger logger1 = Logger.INSTANCE;
        Logger logger2 = Logger.INSTANCE;
        
        System.out.println("Are loggers the same? " + (logger1 == logger2));
        logger1.log("Application started");
        logger2.setLogLevel("DEBUG");
        logger1.log("Debug information");
        
        System.out.println("\n3. Configuration Singleton:");
        AppConfig config1 = AppConfig.getInstance();
        AppConfig config2 = AppConfig.getInstance();
        
        System.out.println("Are configs the same? " + (config1 == config2));
        System.out.println("App Info: " + config1.getAppInfo());
        System.out.println("Max Connections: " + config1.getProperty("max.connections"));
        
        config1.setProperty("custom.setting", "enabled");
        System.out.println("Custom setting from config2: " + 
                          config2.getProperty("custom.setting"));
        
        System.out.println("\n4. Thread Safety Test:");
        Thread worker1 = new DatabaseWorker("Worker-1");
        Thread worker2 = new DatabaseWorker("Worker-2");
        Thread worker3 = new DatabaseWorker("Worker-3");
        
        worker1.start();
        worker2.start();
        worker3.start();
        
        try {
            worker1.join();
            worker2.join();
            worker3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\n5. Singleton Characteristics:");
        System.out.println("✓ Single instance per JVM");
        System.out.println("✓ Global access point");
        System.out.println("✓ Lazy initialization");
        System.out.println("✓ Thread-safe implementation");
        
        System.out.println("\n=== Demo Complete ===");
    }
} 