import java.util.*;

// Subject interface
interface Image {
    void display();
    String getImageInfo();
    int getSize();
}

// Real subject
class RealImage implements Image {
    private String filename;
    private int size;
    private boolean loaded;
    
    public RealImage(String filename) {
        this.filename = filename;
        this.loaded = false;
        loadImageFromDisk();
    }
    
    private void loadImageFromDisk() {
        System.out.println("🔄 Loading image from disk: " + filename);
        try {
            // Simulate expensive loading operation
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Simulate image properties
        this.size = filename.hashCode() % 1000 + 100; // Mock size
        this.loaded = true;
        System.out.println("✅ Image loaded successfully: " + filename + " (" + size + "KB)");
    }
    
    @Override
    public void display() {
        if (loaded) {
            System.out.println("🖼️  Displaying image: " + filename);
        } else {
            System.out.println("❌ Image not loaded: " + filename);
        }
    }
    
    @Override
    public String getImageInfo() {
        return "RealImage{filename='" + filename + "', size=" + size + "KB, loaded=" + loaded + "}";
    }
    
    @Override
    public int getSize() {
        return size;
    }
}

// Virtual Proxy - lazy loading
class ImageProxy implements Image {
    private String filename;
    private RealImage realImage;
    
    public ImageProxy(String filename) {
        this.filename = filename;
        System.out.println("📁 ImageProxy created for: " + filename + " (not loaded yet)");
    }
    
    @Override
    public void display() {
        if (realImage == null) {
            System.out.println("🔄 First access - loading real image...");
            realImage = new RealImage(filename);
        }
        realImage.display();
    }
    
    @Override
    public String getImageInfo() {
        if (realImage == null) {
            return "ImageProxy{filename='" + filename + "', realImage=not_loaded}";
        }
        return "ImageProxy{filename='" + filename + "', realImage=" + realImage.getImageInfo() + "}";
    }
    
    @Override
    public int getSize() {
        if (realImage == null) {
            return 0; // Unknown size until loaded
        }
        return realImage.getSize();
    }
}

// Service interface for protection proxy
interface FileService {
    String readFile(String filename);
    void writeFile(String filename, String content);
    void deleteFile(String filename);
    List<String> listFiles();
}

// Real file service
class RealFileService implements FileService {
    private Map<String, String> files;
    
    public RealFileService() {
        this.files = new HashMap<>();
        // Add some sample files
        files.put("public.txt", "This is public content");
        files.put("confidential.txt", "This is confidential content");
        files.put("admin.txt", "This is admin-only content");
    }
    
    @Override
    public String readFile(String filename) {
        System.out.println("📖 Reading file: " + filename);
        return files.getOrDefault(filename, "File not found");
    }
    
    @Override
    public void writeFile(String filename, String content) {
        System.out.println("✏️  Writing to file: " + filename);
        files.put(filename, content);
    }
    
    @Override
    public void deleteFile(String filename) {
        System.out.println("🗑️  Deleting file: " + filename);
        files.remove(filename);
    }
    
    @Override
    public List<String> listFiles() {
        return new ArrayList<>(files.keySet());
    }
}

// User class for authentication
class User {
    private String username;
    private String role;
    
    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }
    
    public String getUsername() { return username; }
    public String getRole() { return role; }
    
    @Override
    public String toString() {
        return username + " (" + role + ")";
    }
}

// Protection Proxy - access control
class ProtectedFileService implements FileService {
    private RealFileService realFileService;
    private User currentUser;
    
    public ProtectedFileService(RealFileService realFileService) {
        this.realFileService = realFileService;
    }
    
    public void setCurrentUser(User user) {
        this.currentUser = user;
        System.out.println("👤 User set: " + user);
    }
    
    @Override
    public String readFile(String filename) {
        if (!hasReadPermission(filename)) {
            return "❌ Access denied: Insufficient permissions to read " + filename;
        }
        return realFileService.readFile(filename);
    }
    
    @Override
    public void writeFile(String filename, String content) {
        if (!hasWritePermission(filename)) {
            System.out.println("❌ Access denied: Insufficient permissions to write " + filename);
            return;
        }
        realFileService.writeFile(filename, content);
    }
    
    @Override
    public void deleteFile(String filename) {
        if (!hasDeletePermission(filename)) {
            System.out.println("❌ Access denied: Insufficient permissions to delete " + filename);
            return;
        }
        realFileService.deleteFile(filename);
    }
    
    @Override
    public List<String> listFiles() {
        if (currentUser == null) {
            System.out.println("❌ Access denied: Not authenticated");
            return new ArrayList<>();
        }
        
        List<String> allFiles = realFileService.listFiles();
        List<String> allowedFiles = new ArrayList<>();
        
        for (String file : allFiles) {
            if (hasReadPermission(file)) {
                allowedFiles.add(file);
            }
        }
        
        return allowedFiles;
    }
    
    private boolean hasReadPermission(String filename) {
        if (currentUser == null) return false;
        
        if (filename.contains("admin") && !"admin".equals(currentUser.getRole())) {
            return false;
        }
        if (filename.contains("confidential") && "guest".equals(currentUser.getRole())) {
            return false;
        }
        return true;
    }
    
    private boolean hasWritePermission(String filename) {
        if (currentUser == null) return false;
        if ("guest".equals(currentUser.getRole())) return false;
        if (filename.contains("admin") && !"admin".equals(currentUser.getRole())) {
            return false;
        }
        return true;
    }
    
    private boolean hasDeletePermission(String filename) {
        if (currentUser == null) return false;
        return "admin".equals(currentUser.getRole());
    }
}

// Interface for caching proxy
interface WebService {
    String getData(String url);
    void clearCache();
}

// Real web service
class RealWebService implements WebService {
    @Override
    public String getData(String url) {
        System.out.println("🌐 Making HTTP request to: " + url);
        try {
            // Simulate network delay
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Simulate response based on URL
        if (url.contains("users")) {
            return "{'users': [{'id': 1, 'name': 'John'}, {'id': 2, 'name': 'Jane'}]}";
        } else if (url.contains("products")) {
            return "{'products': [{'id': 1, 'name': 'Laptop'}, {'id': 2, 'name': 'Mouse'}]}";
        } else {
            return "{'message': 'Data from " + url + "'}";
        }
    }
    
    @Override
    public void clearCache() {
        // Real service doesn't have cache
    }
}

// Caching Proxy
class CachingWebService implements WebService {
    private RealWebService realWebService;
    private Map<String, String> cache;
    private Map<String, Long> cacheTimestamps;
    private long cacheExpirationTime; // in milliseconds
    
    public CachingWebService(RealWebService realWebService, long cacheExpirationSeconds) {
        this.realWebService = realWebService;
        this.cache = new HashMap<>();
        this.cacheTimestamps = new HashMap<>();
        this.cacheExpirationTime = cacheExpirationSeconds * 1000;
    }
    
    @Override
    public String getData(String url) {
        // Check if data is in cache and not expired
        if (cache.containsKey(url)) {
            long cacheTime = cacheTimestamps.get(url);
            long currentTime = System.currentTimeMillis();
            
            if (currentTime - cacheTime < cacheExpirationTime) {
                System.out.println("💾 Cache HIT for: " + url);
                return cache.get(url);
            } else {
                System.out.println("⏰ Cache EXPIRED for: " + url);
                cache.remove(url);
                cacheTimestamps.remove(url);
            }
        }
        
        System.out.println("💾 Cache MISS for: " + url);
        String data = realWebService.getData(url);
        
        // Store in cache
        cache.put(url, data);
        cacheTimestamps.put(url, System.currentTimeMillis());
        System.out.println("💾 Data cached for: " + url);
        
        return data;
    }
    
    @Override
    public void clearCache() {
        cache.clear();
        cacheTimestamps.clear();
        System.out.println("🗑️  Cache cleared");
    }
    
    public void showCacheStats() {
        System.out.println("📊 Cache Statistics:");
        System.out.println("   Cached items: " + cache.size());
        System.out.println("   Cache expiration: " + (cacheExpirationTime / 1000) + " seconds");
        if (!cache.isEmpty()) {
            System.out.println("   Cached URLs: " + cache.keySet());
        }
    }
}

public class ProxyDemo {
    public static void main(String[] args) {
        System.out.println("=== Proxy Pattern Demo ===\n");
        
        // 1. Virtual Proxy Demo (Lazy Loading)
        System.out.println("1. Virtual Proxy Demo (Lazy Loading):");
        System.out.println("=".repeat(50));
        
        Image[] images = {
            new ImageProxy("photo1.jpg"),
            new ImageProxy("photo2.jpg"),
            new ImageProxy("photo3.jpg")
        };
        
        System.out.println("\n📋 Images created (proxies only):");
        for (Image img : images) {
            System.out.println("   " + img.getImageInfo());
        }
        
        System.out.println("\n🖼️  Displaying first image (triggers loading):");
        images[0].display();
        
        System.out.println("\n🖼️  Displaying first image again (already loaded):");
        images[0].display();
        
        // 2. Protection Proxy Demo (Access Control)
        System.out.println("\n\n2. Protection Proxy Demo (Access Control):");
        System.out.println("=".repeat(50));
        
        RealFileService realFileService = new RealFileService();
        ProtectedFileService protectedService = new ProtectedFileService(realFileService);
        
        // Test with different users
        User guest = new User("guest_user", "guest");
        User regularUser = new User("john_doe", "user");
        User admin = new User("admin_user", "admin");
        
        User[] users = {guest, regularUser, admin};
        
        for (User user : users) {
            System.out.println("\n👤 Testing with user: " + user);
            protectedService.setCurrentUser(user);
            
            System.out.println("📂 Available files: " + protectedService.listFiles());
            
            System.out.println("📖 Reading confidential.txt:");
            System.out.println("   " + protectedService.readFile("confidential.txt"));
            
            System.out.println("✏️  Attempting to write new_file.txt:");
            protectedService.writeFile("new_file.txt", "New content");
            
            System.out.println("🗑️  Attempting to delete admin.txt:");
            protectedService.deleteFile("admin.txt");
        }
        
        // 3. Caching Proxy Demo
        System.out.println("\n\n3. Caching Proxy Demo:");
        System.out.println("=".repeat(50));
        
        RealWebService realWebService = new RealWebService();
        CachingWebService cachingService = new CachingWebService(realWebService, 5); // 5 seconds cache
        
        String[] urls = {
            "https://api.example.com/users",
            "https://api.example.com/products",
            "https://api.example.com/users" // Duplicate to test cache
        };
        
        System.out.println("🌐 Making requests through caching proxy:");
        
        for (String url : urls) {
            System.out.println("\n📡 Request to: " + url);
            String response = cachingService.getData(url);
            System.out.println("📨 Response: " + response.substring(0, Math.min(50, response.length())) + "...");
        }
        
        cachingService.showCacheStats();
        
        // Test cache expiration
        System.out.println("\n⏰ Waiting for cache to expire...");
        try {
            Thread.sleep(6000); // Wait longer than cache expiration
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("\n📡 Making request after cache expiration:");
        cachingService.getData("https://api.example.com/users");
        
        cachingService.showCacheStats();
        
        // 4. Summary
        System.out.println("\n\n=== Proxy Pattern Benefits Demonstrated ===");
        System.out.println("✅ Virtual Proxy: Lazy loading of expensive resources");
        System.out.println("✅ Protection Proxy: Access control and security");
        System.out.println("✅ Caching Proxy: Performance optimization through caching");
        System.out.println("✅ Transparent interface: Clients use same interface");
        System.out.println("✅ Additional functionality without modifying original classes");
        
        System.out.println("\n=== Demo Complete ===");
    }
} 