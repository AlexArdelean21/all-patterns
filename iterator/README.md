# Iterator Pattern

## Definition
The Iterator pattern provides a way to access elements of a collection sequentially without exposing its underlying representation. It encapsulates the traversal algorithm and allows multiple simultaneous traversals of the same collection.

## Problem it Solves
- Need to traverse collections without exposing internal structure
- Want to support multiple simultaneous traversals
- Different traversal algorithms for same collection
- Uniform interface for different collection types
- Memory-efficient traversal of large collections

## Key Components
1. **Iterator Interface**: Defines methods for traversing (hasNext(), next(), remove())
2. **Concrete Iterator**: Implements specific traversal algorithm
3. **Aggregate Interface**: Defines method for creating iterator
4. **Concrete Aggregate**: Implements iterator creation for specific collection

## Implementation Example
Our demo shows multiple iterator types:
- **Book Collection**: Custom collection with various iterator strategies
- **Forward/Reverse Iterators**: Different traversal directions
- **Filter Iterators**: Genre, year range, rating-based filtering
- **Tree Traversal**: Depth-first and breadth-first tree iterators

## When to Use
✅ Need to traverse collection without exposing structure  
✅ Want multiple simultaneous traversals  
✅ Different traversal algorithms needed  
✅ Uniform interface across different collections  
✅ Memory-efficient access to large collections  

## When NOT to Use
❌ Simple array access is sufficient  
❌ Only one traversal method needed  
❌ Collection structure is simple and fixed  
❌ Performance overhead is critical  

## Real-World Examples
- Java Collections Framework (Iterator interface)
- Database result sets (ResultSet in JDBC)
- File system traversal
- DOM node traversal
- Stream processing

## Advantages
✅ **Encapsulation**: Hides collection implementation details  
✅ **Flexibility**: Multiple iterators on same collection  
✅ **Uniformity**: Same interface for different collections  
✅ **Memory Efficiency**: Lazy evaluation possible  
✅ **Concurrent Access**: Multiple simultaneous traversals  

## Disadvantages
❌ **Overhead**: Additional abstraction layer  
❌ **Complexity**: More complex than direct access  
❌ **State Management**: Iterator must maintain position state  

## Related Patterns
- **Composite**: Iterators often used to traverse composite structures
- **Factory Method**: Iterator creation can use factory methods
- **Memento**: Iterator state can be saved using mementos

## Recognition in Code
Look for these indicators:
```java
// Iterator interface
interface Iterator<T> {
    boolean hasNext();
    T next();
    void remove();
}

// Iterable collection
interface Iterable<T> {
    Iterator<T> iterator();
}

// Concrete iterator implementation
class ListIterator<T> implements Iterator<T> {
    private List<T> list;
    private int index = 0;
    
    public boolean hasNext() {
        return index < list.size();
    }
    
    public T next() {
        return list.get(index++);
    }
}

// Usage pattern
for (Iterator<T> it = collection.iterator(); it.hasNext(); ) {
    T item = it.next();
    // process item
}
```

## Iterator Types
1. **External Iterator**: Client controls iteration
2. **Internal Iterator**: Iterator controls iteration
3. **Forward Iterator**: Move forward only
4. **Bidirectional Iterator**: Move forward and backward
5. **Random Access Iterator**: Jump to any position

## Filter Iterator Pattern
```java
class FilterIterator<T> implements Iterator<T> {
    private Iterator<T> source;
    private Predicate<T> filter;
    private T nextItem;
    
    public FilterIterator(Iterator<T> source, Predicate<T> filter) {
        this.source = source;
        this.filter = filter;
        advance();
    }
    
    private void advance() {
        while (source.hasNext()) {
            T item = source.next();
            if (filter.test(item)) {
                nextItem = item;
                return;
            }
        }
        nextItem = null;
    }
    
    public boolean hasNext() {
        return nextItem != null;
    }
    
    public T next() {
        T result = nextItem;
        advance();
        return result;
    }
}
```

## Tree Traversal Iterators
```java
// Depth-first iterator
class DepthFirstIterator<T> implements Iterator<T> {
    private Stack<TreeNode<T>> stack = new Stack<>();
    
    public DepthFirstIterator(TreeNode<T> root) {
        if (root != null) stack.push(root);
    }
    
    public boolean hasNext() {
        return !stack.isEmpty();
    }
    
    public T next() {
        TreeNode<T> node = stack.pop();
        // Add children to stack (reverse order for correct DFS)
        for (int i = node.getChildren().size() - 1; i >= 0; i--) {
            stack.push(node.getChildren().get(i));
        }
        return node.getData();
    }
}
```

## Best Practices
1. **Fail-fast behavior**: Detect concurrent modifications
2. **Remove support**: Implement remove() when appropriate
3. **Exception handling**: Proper NoSuchElementException usage
4. **State management**: Maintain iterator position correctly
5. **Memory efficiency**: Avoid loading entire collection

## Iterator Safety
- **Concurrent Modification**: Handle modifications during iteration
- **Fail-fast**: Throw exceptions on illegal modifications
- **Thread Safety**: Consider multi-threaded access
- **Resource Management**: Clean up resources when done

## Testing Strategy
- Test all iterator methods (hasNext, next, remove)
- Verify proper exception throwing
- Test with empty collections
- Test concurrent modification detection
- Performance testing for large collections

## Common Mistakes
1. Not implementing remove() properly
2. Allowing concurrent modification without detection
3. Not handling empty collections
4. Forgetting to advance iterator in filtered iterators
5. Not maintaining proper iterator state
6. Memory leaks in long-lived iterators 