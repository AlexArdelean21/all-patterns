# Proxy Pattern - Class Diagram

```mermaid
classDiagram
    %% Image Proxy Example (Virtual Proxy)
    class Image {
        <<interface>>
        +display() void
        +getImageInfo() String
        +getSize() int
    }
    
    class RealImage {
        -filename: String
        -size: int
        -loaded: boolean
        +RealImage(String)
        +display() void
        +getImageInfo() String
        +getSize() int
        -loadImageFromDisk() void
    }
    
    class ImageProxy {
        -filename: String
        -realImage: RealImage
        +ImageProxy(String)
        +display() void
        +getImageInfo() String
        +getSize() int
    }
    
    %% File Service Example (Protection Proxy)
    class FileService {
        <<interface>>
        +readFile(String) String
        +writeFile(String, String) void
        +deleteFile(String) void
        +listFiles() List~String~
    }
    
    class RealFileService {
        -files: Map~String,String~
        +RealFileService()
        +readFile(String) String
        +writeFile(String, String) void
        +deleteFile(String) void
        +listFiles() List~String~
    }
    
    class ProtectedFileService {
        -realFileService: RealFileService
        -currentUser: User
        +ProtectedFileService(RealFileService)
        +setCurrentUser(User) void
        +readFile(String) String
        +writeFile(String, String) void
        +deleteFile(String) void
        +listFiles() List~String~
        -hasReadPermission(String) boolean
        -hasWritePermission(String) boolean
        -hasDeletePermission(String) boolean
    }
    
    class User {
        -username: String
        -role: String
        +User(String, String)
        +getUsername() String
        +getRole() String
    }
    
    %% Web Service Example (Caching Proxy)
    class WebService {
        <<interface>>
        +getData(String) String
        +clearCache() void
    }
    
    class RealWebService {
        +getData(String) String
        +clearCache() void
    }
    
    class CachingWebService {
        -realWebService: RealWebService
        -cache: Map~String,String~
        -cacheTimestamps: Map~String,Long~
        -cacheExpirationTime: long
        +CachingWebService(RealWebService, long)
        +getData(String) String
        +clearCache() void
        +showCacheStats() void
    }
    
    %% Relationships
    Image <|.. RealImage
    Image <|.. ImageProxy
    ImageProxy --> RealImage : creates when needed
    
    FileService <|.. RealFileService
    FileService <|.. ProtectedFileService
    ProtectedFileService --> RealFileService : delegates to
    ProtectedFileService --> User : checks permissions
    
    WebService <|.. RealWebService
    WebService <|.. CachingWebService
    CachingWebService --> RealWebService : delegates to
    
    note for ImageProxy
        Virtual Proxy:
        - Lazy loading
        - Expensive object creation
        - Transparent access
    end note
    
    note for ProtectedFileService
        Protection Proxy:
        - Access control
        - Role-based permissions
        - Security enforcement
    end note
    
    note for CachingWebService
        Caching Proxy:
        - Performance optimization
        - Response caching
        - Expiration management
    end note
```

# Proxy Pattern - Sequence Diagrams

## Virtual Proxy Sequence

```mermaid
sequenceDiagram
    participant Client
    participant ImageProxy
    participant RealImage
    
    Client->>ImageProxy: new ImageProxy("photo.jpg")
    Note over ImageProxy: Proxy created, no real image yet
    
    Client->>ImageProxy: display()
    ImageProxy->>ImageProxy: Check if realImage exists
    Note over ImageProxy: First access - need to load
    ImageProxy->>RealImage: new RealImage("photo.jpg")
    Note over RealImage: Expensive loading operation
    RealImage-->>ImageProxy: Real image created
    ImageProxy->>RealImage: display()
    RealImage-->>ImageProxy: Display complete
    ImageProxy-->>Client: Display complete
    
    Client->>ImageProxy: display()
    Note over ImageProxy: Subsequent access - already loaded
    ImageProxy->>RealImage: display()
    RealImage-->>ImageProxy: Display complete
    ImageProxy-->>Client: Display complete
```

## Protection Proxy Sequence

```mermaid
sequenceDiagram
    participant Client
    participant ProtectedFileService
    participant RealFileService
    participant User
    
    Client->>ProtectedFileService: setCurrentUser(adminUser)
    ProtectedFileService->>User: Store current user
    
    Client->>ProtectedFileService: readFile("confidential.txt")
    ProtectedFileService->>ProtectedFileService: hasReadPermission("confidential.txt")
    ProtectedFileService->>User: getRole()
    User-->>ProtectedFileService: "admin"
    Note over ProtectedFileService: Permission granted
    ProtectedFileService->>RealFileService: readFile("confidential.txt")
    RealFileService-->>ProtectedFileService: File content
    ProtectedFileService-->>Client: File content
    
    Client->>ProtectedFileService: setCurrentUser(guestUser)
    Client->>ProtectedFileService: readFile("confidential.txt")
    ProtectedFileService->>ProtectedFileService: hasReadPermission("confidential.txt")
    ProtectedFileService->>User: getRole()
    User-->>ProtectedFileService: "guest"
    Note over ProtectedFileService: Permission denied
    ProtectedFileService-->>Client: "Access denied"
```

## Benefits of Each Proxy Type

1. **Virtual Proxy**: Delays expensive object creation until actually needed
2. **Protection Proxy**: Controls access based on user permissions and roles
3. **Caching Proxy**: Improves performance by storing frequently accessed data

## Key Characteristics

- **Transparent Interface**: Clients use same interface for proxy and real subject
- **Controlled Access**: Proxy controls when and how real subject is accessed
- **Additional Functionality**: Proxies can add features without changing real subject 