import java.util.*;

// Component interface
interface FileSystemComponent {
    void showDetails();
    int getSize();
    String getName();
}

// Leaf class - File
class File implements FileSystemComponent {
    private String name;
    private int size;
    
    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }
    
    @Override
    public void showDetails() {
        System.out.println("File: " + name + " (Size: " + size + " KB)");
    }
    
    @Override
    public int getSize() {
        return size;
    }
    
    @Override
    public String getName() {
        return name;
    }
}

// Composite class - Directory
class Directory implements FileSystemComponent {
    private String name;
    private List<FileSystemComponent> components;
    
    public Directory(String name) {
        this.name = name;
        this.components = new ArrayList<>();
    }
    
    public void addComponent(FileSystemComponent component) {
        components.add(component);
    }
    
    public void removeComponent(FileSystemComponent component) {
        components.remove(component);
    }
    
    public List<FileSystemComponent> getComponents() {
        return components;
    }
    
    @Override
    public void showDetails() {
        System.out.println("Directory: " + name + " (Total Size: " + getSize() + " KB)");
        for (FileSystemComponent component : components) {
            System.out.print("  ");
            component.showDetails();
        }
    }
    
    @Override
    public int getSize() {
        int totalSize = 0;
        for (FileSystemComponent component : components) {
            totalSize += component.getSize();
        }
        return totalSize;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    // Additional utility methods
    public void showTree(String indent) {
        System.out.println(indent + "📁 " + name + " (" + getSize() + " KB)");
        for (FileSystemComponent component : components) {
            if (component instanceof Directory) {
                ((Directory) component).showTree(indent + "  ");
            } else {
                System.out.println(indent + "  📄 " + component.getName() + 
                                 " (" + component.getSize() + " KB)");
            }
        }
    }
    
    public FileSystemComponent findComponent(String name) {
        if (this.name.equals(name)) {
            return this;
        }
        
        for (FileSystemComponent component : components) {
            if (component.getName().equals(name)) {
                return component;
            }
            if (component instanceof Directory) {
                FileSystemComponent found = ((Directory) component).findComponent(name);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }
}

// Client class to demonstrate usage
class FileManager {
    private Directory root;
    
    public FileManager() {
        root = new Directory("Root");
    }
    
    public Directory getRoot() {
        return root;
    }
    
    public void showFileSystem() {
        System.out.println("=== File System Structure ===");
        root.showTree("");
        System.out.println("Total System Size: " + root.getSize() + " KB\n");
    }
    
    public void copyDirectory(Directory source, Directory destination) {
        Directory newDir = new Directory(source.getName() + "_copy");
        
        for (FileSystemComponent component : source.getComponents()) {
            if (component instanceof File) {
                newDir.addComponent(new File(component.getName(), component.getSize()));
            } else if (component instanceof Directory) {
                copyDirectory((Directory) component, newDir);
            }
        }
        
        destination.addComponent(newDir);
    }
}

public class CompositeDemo {
    public static void main(String[] args) {
        System.out.println("=== Composite Pattern Demo - File System ===\n");
        
        // Create file manager
        FileManager fileManager = new FileManager();
        Directory root = fileManager.getRoot();
        
        // Create files
        File file1 = new File("document.txt", 15);
        File file2 = new File("image.jpg", 250);
        File file3 = new File("video.mp4", 1500);
        File file4 = new File("readme.md", 5);
        File file5 = new File("config.xml", 8);
        
        // Create directories
        Directory documents = new Directory("Documents");
        Directory media = new Directory("Media");
        Directory projects = new Directory("Projects");
        Directory project1 = new Directory("Project1");
        Directory project2 = new Directory("Project2");
        
        // Build file system structure
        documents.addComponent(file1);
        documents.addComponent(file4);
        
        media.addComponent(file2);
        media.addComponent(file3);
        
        project1.addComponent(new File("main.java", 45));
        project1.addComponent(new File("test.java", 20));
        project1.addComponent(file5);
        
        project2.addComponent(new File("app.js", 35));
        project2.addComponent(new File("style.css", 12));
        
        projects.addComponent(project1);
        projects.addComponent(project2);
        
        root.addComponent(documents);
        root.addComponent(media);
        root.addComponent(projects);
        
        // Display file system
        fileManager.showFileSystem();
        
        // Demonstrate uniform treatment
        System.out.println("=== Uniform Treatment Demo ===");
        List<FileSystemComponent> components = Arrays.asList(
            file1, documents, media, projects
        );
        
        for (FileSystemComponent component : components) {
            System.out.println(component.getName() + " - Size: " + 
                             component.getSize() + " KB");
        }
        
        System.out.println("\n=== Search Demo ===");
        FileSystemComponent found = root.findComponent("Project1");
        if (found != null) {
            System.out.println("Found: " + found.getName());
            found.showDetails();
        }
        
        System.out.println("\n=== Copy Demo ===");
        fileManager.copyDirectory(project1, root);
        fileManager.showFileSystem();
        
        System.out.println("=== Demo Complete ===");
    }
} 