# Proxy Pattern

## Definition
The Proxy pattern provides a placeholder or surrogate for another object to control access to it. It acts as an intermediary that can add functionality like lazy loading, access control, caching, or logging without changing the original object.

## Problem it Solves
- Need to control access to an object
- Want to add functionality without modifying original object
- Object creation is expensive and should be delayed
- Need to cache results for performance
- Security access control is required
- Remote objects need local representation

## Key Components
1. **Subject Interface**: Common interface for RealSubject and Proxy
2. **RealSubject**: The actual object that performs the real work
3. **Proxy**: Controls access to RealSubject and may add additional behavior
4. **Client**: Works with objects through Subject interface

## Types of Proxies
1. **Virtual Proxy**: Lazy loading of expensive objects
2. **Protection Proxy**: Access control and security
3. **Caching Proxy**: Cache results for performance
4. **Remote Proxy**: Local representative of remote object
5. **Smart Proxy**: Additional functionality like reference counting

## Implementation Example
Our demo shows three proxy types:
- **Virtual Proxy**: ImageProxy for lazy loading images
- **Protection Proxy**: ProtectedFileService with role-based access
- **Caching Proxy**: CachingWebService for HTTP response caching

## When to Use
✅ Need to control access to an object  
✅ Want to add functionality transparently  
✅ Object creation is expensive  
✅ Need security access control  
✅ Want to cache expensive operations  
✅ Need local representative of remote object  

## When NOT to Use
❌ Simple direct access is sufficient  
❌ Overhead is not justified  
❌ Additional abstraction adds confusion  
❌ Performance cost outweighs benefits  

## Real-World Examples
- Image loading in applications (virtual proxy)
- Security systems (protection proxy)
- Web caches (caching proxy)
- ORM lazy loading (virtual proxy)
- Remote procedure calls (remote proxy)

## Advantages
✅ **Transparency**: Client doesn't know about proxy existence  
✅ **Control**: Can control when and how to access real object  
✅ **Performance**: Can optimize through caching or lazy loading  
✅ **Security**: Can add access control  
✅ **Functionality**: Can add features without changing original  

## Disadvantages
❌ **Complexity**: Additional layer of abstraction  
❌ **Performance**: May introduce overhead  
❌ **Maintenance**: More classes to maintain  

## Related Patterns
- **Adapter**: Changes interface, Proxy maintains same interface
- **Decorator**: Adds functionality, Proxy controls access
- **Facade**: Simplifies interface, Proxy controls access to single object

## Recognition in Code
Look for these indicators:
```java
// Common interface
interface Subject {
    void request();
}

// Real subject
class RealSubject implements Subject {
    public void request() {
        // Actual work
    }
}

// Proxy with same interface
class Proxy implements Subject {
    private RealSubject realSubject;
    
    public void request() {
        // Additional behavior (lazy loading, access control, etc.)
        if (realSubject == null) {
            realSubject = new RealSubject();
        }
        realSubject.request();
    }
}
```

## Virtual Proxy Pattern
```java
class ImageProxy implements Image {
    private String filename;
    private RealImage realImage;
    
    public void display() {
        if (realImage == null) {
            realImage = new RealImage(filename); // Lazy loading
        }
        realImage.display();
    }
}
```

## Protection Proxy Pattern
```java
class ProtectedService implements Service {
    private RealService realService;
    private User currentUser;
    
    public void operation() {
        if (hasPermission()) {
            realService.operation();
        } else {
            throw new SecurityException("Access denied");
        }
    }
}
```

## Best Practices
1. **Maintain same interface as real subject**
2. **Initialize real subject lazily when possible**
3. **Handle null real subjects gracefully**
4. **Document proxy behavior clearly**
5. **Consider thread safety for shared proxies**

## Testing Strategy
- Test proxy behavior independently
- Test with and without real subject
- Verify transparent interface implementation
- Test performance benefits (caching, lazy loading)
- Security testing for protection proxies

## Common Mistakes
1. Not maintaining same interface as real subject
2. Exposing proxy-specific functionality
3. Not handling real subject lifecycle properly
4. Making proxy behavior too complex
5. Not considering thread safety
6. Forgetting to delegate all necessary methods 