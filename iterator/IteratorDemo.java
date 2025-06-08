import java.util.*;

// Iterator interface
interface Iterator<T> {
    boolean hasNext();
    T next();
    void remove();
}

// Aggregate interface
interface Iterable<T> {
    Iterator<T> createIterator();
}

// Book class for our collection
class Book {
    private String title;
    private String author;
    private String genre;
    private int year;
    private double rating;
    
    public Book(String title, String author, String genre, int year, double rating) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.year = year;
        this.rating = rating;
    }
    
    // Getters
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }
    public int getYear() { return year; }
    public double getRating() { return rating; }
    
    @Override
    public String toString() {
        return "'" + title + "' by " + author + " (" + year + ") - " + genre + " [Rating: " + rating + "]";
    }
}

// Custom book collection
class BookCollection implements Iterable<Book> {
    private List<Book> books;
    
    public BookCollection() {
        this.books = new ArrayList<>();
    }
    
    public void addBook(Book book) {
        books.add(book);
        System.out.println("📚 Added: " + book.getTitle());
    }
    
    public void removeBook(Book book) {
        if (books.remove(book)) {
            System.out.println("🗑️  Removed: " + book.getTitle());
        }
    }
    
    public int size() {
        return books.size();
    }
    
    // Internal access to books for iterators
    protected List<Book> getBooks() {
        return books;
    }
    
    @Override
    public Iterator<Book> createIterator() {
        return new ForwardIterator();
    }
    
    // Different iterator implementations
    public Iterator<Book> createReverseIterator() {
        return new ReverseIterator();
    }
    
    public Iterator<Book> createGenreIterator(String genre) {
        return new GenreFilterIterator(genre);
    }
    
    public Iterator<Book> createYearRangeIterator(int startYear, int endYear) {
        return new YearRangeIterator(startYear, endYear);
    }
    
    public Iterator<Book> createHighRatedIterator(double minRating) {
        return new HighRatedIterator(minRating);
    }
    
    // Forward iterator (default)
    private class ForwardIterator implements Iterator<Book> {
        private int currentIndex = 0;
        private boolean canRemove = false;
        
        @Override
        public boolean hasNext() {
            return currentIndex < books.size();
        }
        
        @Override
        public Book next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more books");
            }
            canRemove = true;
            return books.get(currentIndex++);
        }
        
        @Override
        public void remove() {
            if (!canRemove) {
                throw new IllegalStateException("Cannot remove - call next() first");
            }
            books.remove(--currentIndex);
            canRemove = false;
            System.out.println("🗑️  Removed book at index " + currentIndex);
        }
    }
    
    // Reverse iterator
    private class ReverseIterator implements Iterator<Book> {
        private int currentIndex;
        private boolean canRemove = false;
        
        public ReverseIterator() {
            this.currentIndex = books.size() - 1;
        }
        
        @Override
        public boolean hasNext() {
            return currentIndex >= 0;
        }
        
        @Override
        public Book next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more books");
            }
            canRemove = true;
            return books.get(currentIndex--);
        }
        
        @Override
        public void remove() {
            if (!canRemove) {
                throw new IllegalStateException("Cannot remove - call next() first");
            }
            books.remove(currentIndex + 1);
            canRemove = false;
            System.out.println("🗑️  Removed book at index " + (currentIndex + 1));
        }
    }
    
    // Genre filter iterator
    private class GenreFilterIterator implements Iterator<Book> {
        private String targetGenre;
        private int currentIndex = 0;
        private Book nextBook = null;
        private boolean canRemove = false;
        private int lastReturnedIndex = -1;
        
        public GenreFilterIterator(String genre) {
            this.targetGenre = genre.toLowerCase();
            findNext();
        }
        
        private void findNext() {
            nextBook = null;
            while (currentIndex < books.size()) {
                Book book = books.get(currentIndex);
                if (book.getGenre().toLowerCase().equals(targetGenre)) {
                    nextBook = book;
                    break;
                }
                currentIndex++;
            }
        }
        
        @Override
        public boolean hasNext() {
            return nextBook != null;
        }
        
        @Override
        public Book next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more books of genre: " + targetGenre);
            }
            
            Book result = nextBook;
            lastReturnedIndex = currentIndex;
            currentIndex++;
            canRemove = true;
            findNext();
            return result;
        }
        
        @Override
        public void remove() {
            if (!canRemove) {
                throw new IllegalStateException("Cannot remove - call next() first");
            }
            books.remove(lastReturnedIndex);
            currentIndex = lastReturnedIndex;
            canRemove = false;
            findNext();
            System.out.println("🗑️  Removed book of genre " + targetGenre);
        }
    }
    
    // Year range iterator
    private class YearRangeIterator implements Iterator<Book> {
        private int startYear;
        private int endYear;
        private int currentIndex = 0;
        private Book nextBook = null;
        private boolean canRemove = false;
        private int lastReturnedIndex = -1;
        
        public YearRangeIterator(int startYear, int endYear) {
            this.startYear = startYear;
            this.endYear = endYear;
            findNext();
        }
        
        private void findNext() {
            nextBook = null;
            while (currentIndex < books.size()) {
                Book book = books.get(currentIndex);
                if (book.getYear() >= startYear && book.getYear() <= endYear) {
                    nextBook = book;
                    break;
                }
                currentIndex++;
            }
        }
        
        @Override
        public boolean hasNext() {
            return nextBook != null;
        }
        
        @Override
        public Book next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more books in year range: " + startYear + "-" + endYear);
            }
            
            Book result = nextBook;
            lastReturnedIndex = currentIndex;
            currentIndex++;
            canRemove = true;
            findNext();
            return result;
        }
        
        @Override
        public void remove() {
            if (!canRemove) {
                throw new IllegalStateException("Cannot remove - call next() first");
            }
            books.remove(lastReturnedIndex);
            currentIndex = lastReturnedIndex;
            canRemove = false;
            findNext();
            System.out.println("🗑️  Removed book from year range " + startYear + "-" + endYear);
        }
    }
    
    // High rated books iterator
    private class HighRatedIterator implements Iterator<Book> {
        private double minRating;
        private int currentIndex = 0;
        private Book nextBook = null;
        private boolean canRemove = false;
        private int lastReturnedIndex = -1;
        
        public HighRatedIterator(double minRating) {
            this.minRating = minRating;
            findNext();
        }
        
        private void findNext() {
            nextBook = null;
            while (currentIndex < books.size()) {
                Book book = books.get(currentIndex);
                if (book.getRating() >= minRating) {
                    nextBook = book;
                    break;
                }
                currentIndex++;
            }
        }
        
        @Override
        public boolean hasNext() {
            return nextBook != null;
        }
        
        @Override
        public Book next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more high-rated books (>= " + minRating + ")");
            }
            
            Book result = nextBook;
            lastReturnedIndex = currentIndex;
            currentIndex++;
            canRemove = true;
            findNext();
            return result;
        }
        
        @Override
        public void remove() {
            if (!canRemove) {
                throw new IllegalStateException("Cannot remove - call next() first");
            }
            books.remove(lastReturnedIndex);
            currentIndex = lastReturnedIndex;
            canRemove = false;
            findNext();
            System.out.println("🗑️  Removed high-rated book");
        }
    }
}

// Tree structure for demonstrating tree traversal iterators
class TreeNode<T> {
    T data;
    List<TreeNode<T>> children;
    
    public TreeNode(T data) {
        this.data = data;
        this.children = new ArrayList<>();
    }
    
    public void addChild(TreeNode<T> child) {
        children.add(child);
    }
    
    public T getData() { return data; }
    public List<TreeNode<T>> getChildren() { return children; }
    
    @Override
    public String toString() {
        return data.toString();
    }
}

// Tree collection with different traversal strategies
class Tree<T> implements Iterable<T> {
    private TreeNode<T> root;
    
    public Tree(T rootData) {
        this.root = new TreeNode<>(rootData);
    }
    
    public TreeNode<T> getRoot() { return root; }
    
    @Override
    public Iterator<T> createIterator() {
        return new DepthFirstIterator();
    }
    
    public Iterator<T> createBreadthFirstIterator() {
        return new BreadthFirstIterator();
    }
    
    public Iterator<T> createDepthFirstIterator() {
        return new DepthFirstIterator();
    }
    
    // Depth-first traversal iterator
    private class DepthFirstIterator implements Iterator<T> {
        private Stack<TreeNode<T>> stack;
        
        public DepthFirstIterator() {
            stack = new Stack<>();
            if (root != null) {
                stack.push(root);
            }
        }
        
        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }
        
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more nodes");
            }
            
            TreeNode<T> current = stack.pop();
            
            // Add children in reverse order for correct DFS order
            for (int i = current.getChildren().size() - 1; i >= 0; i--) {
                stack.push(current.getChildren().get(i));
            }
            
            return current.getData();
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove not supported in tree iterator");
        }
    }
    
    // Breadth-first traversal iterator
    private class BreadthFirstIterator implements Iterator<T> {
        private Queue<TreeNode<T>> queue;
        
        public BreadthFirstIterator() {
            queue = new LinkedList<>();
            if (root != null) {
                queue.offer(root);
            }
        }
        
        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }
        
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more nodes");
            }
            
            TreeNode<T> current = queue.poll();
            
            // Add all children to queue
            for (TreeNode<T> child : current.getChildren()) {
                queue.offer(child);
            }
            
            return current.getData();
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove not supported in tree iterator");
        }
    }
}

// Utility class for iterator demonstrations
class IteratorUtils {
    public static <T> void printIterator(Iterator<T> iterator, String title) {
        System.out.println("\n📋 " + title + ":");
        int count = 0;
        while (iterator.hasNext()) {
            count++;
            System.out.println("  " + count + ". " + iterator.next());
        }
        System.out.println("Total items: " + count);
    }
    
    public static <T> List<T> toList(Iterator<T> iterator) {
        List<T> result = new ArrayList<>();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result;
    }
    
    public static <T> int count(Iterator<T> iterator) {
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        return count;
    }
}

public class IteratorDemo {
    public static void main(String[] args) {
        System.out.println("=== Iterator Pattern Demo ===\n");
        
        // 1. Book Collection Iterator Demo
        System.out.println("1. Book Collection with Multiple Iterators:");
        System.out.println("=".repeat(60));
        
        BookCollection library = new BookCollection();
        
        // Add books
        library.addBook(new Book("1984", "George Orwell", "Dystopian", 1949, 4.8));
        library.addBook(new Book("To Kill a Mockingbird", "Harper Lee", "Drama", 1960, 4.7));
        library.addBook(new Book("Dune", "Frank Herbert", "Sci-Fi", 1965, 4.6));
        library.addBook(new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy", 1937, 4.9));
        library.addBook(new Book("Foundation", "Isaac Asimov", "Sci-Fi", 1951, 4.5));
        library.addBook(new Book("Brave New World", "Aldous Huxley", "Dystopian", 1932, 4.4));
        library.addBook(new Book("The Lord of the Rings", "J.R.R. Tolkien", "Fantasy", 1954, 4.9));
        
        // Forward iteration
        IteratorUtils.printIterator(library.createIterator(), "All Books (Forward)");
        
        // Reverse iteration
        IteratorUtils.printIterator(library.createReverseIterator(), "All Books (Reverse)");
        
        // Genre filtering
        IteratorUtils.printIterator(library.createGenreIterator("Sci-Fi"), "Sci-Fi Books Only");
        IteratorUtils.printIterator(library.createGenreIterator("Fantasy"), "Fantasy Books Only");
        
        // Year range filtering
        IteratorUtils.printIterator(library.createYearRangeIterator(1940, 1970), "Books from 1940-1970");
        
        // High-rated books
        IteratorUtils.printIterator(library.createHighRatedIterator(4.7), "Highly Rated Books (>= 4.7)");
        
        // 2. Tree Traversal Iterator Demo
        System.out.println("\n\n2. Tree Traversal Iterators:");
        System.out.println("=".repeat(60));
        
        // Build a sample tree
        Tree<String> tree = new Tree<>("Root");
        TreeNode<String> root = tree.getRoot();
        
        TreeNode<String> child1 = new TreeNode<>("Child 1");
        TreeNode<String> child2 = new TreeNode<>("Child 2");
        TreeNode<String> child3 = new TreeNode<>("Child 3");
        
        root.addChild(child1);
        root.addChild(child2);
        root.addChild(child3);
        
        child1.addChild(new TreeNode<>("Child 1.1"));
        child1.addChild(new TreeNode<>("Child 1.2"));
        child2.addChild(new TreeNode<>("Child 2.1"));
        child3.addChild(new TreeNode<>("Child 3.1"));
        child3.addChild(new TreeNode<>("Child 3.2"));
        child3.addChild(new TreeNode<>("Child 3.3"));
        
        // Different traversal strategies
        IteratorUtils.printIterator(tree.createDepthFirstIterator(), "Depth-First Traversal");
        IteratorUtils.printIterator(tree.createBreadthFirstIterator(), "Breadth-First Traversal");
        
        // 3. Iterator Operations Demo
        System.out.println("\n\n3. Iterator Operations Demo:");
        System.out.println("=".repeat(60));
        
        // Test remove operation
        System.out.println("\n🗑️  Testing remove operation:");
        Iterator<Book> sciFiIterator = library.createGenreIterator("Sci-Fi");
        System.out.println("Sci-Fi books before removal: " + IteratorUtils.count(library.createGenreIterator("Sci-Fi")));
        
        sciFiIterator = library.createGenreIterator("Sci-Fi");
        if (sciFiIterator.hasNext()) {
            Book book = sciFiIterator.next();
            System.out.println("Removing: " + book.getTitle());
            sciFiIterator.remove();
        }
        
        System.out.println("Sci-Fi books after removal: " + IteratorUtils.count(library.createGenreIterator("Sci-Fi")));
        
        // 4. Multiple Iterator Independence
        System.out.println("\n\n4. Multiple Iterator Independence:");
        System.out.println("=".repeat(60));
        
        Iterator<Book> iter1 = library.createIterator();
        Iterator<Book> iter2 = library.createReverseIterator();
        Iterator<Book> iter3 = library.createGenreIterator("Fantasy");
        
        System.out.println("\n📚 Demonstrating independent iteration:");
        
        System.out.println("Forward iter (1st book): " + (iter1.hasNext() ? iter1.next().getTitle() : "None"));
        System.out.println("Reverse iter (1st book): " + (iter2.hasNext() ? iter2.next().getTitle() : "None"));
        System.out.println("Fantasy iter (1st book): " + (iter3.hasNext() ? iter3.next().getTitle() : "None"));
        
        System.out.println("Forward iter (2nd book): " + (iter1.hasNext() ? iter1.next().getTitle() : "None"));
        System.out.println("Reverse iter (2nd book): " + (iter2.hasNext() ? iter2.next().getTitle() : "None"));
        System.out.println("Fantasy iter (2nd book): " + (iter3.hasNext() ? iter3.next().getTitle() : "None"));
        
        // 5. Performance and Memory Benefits
        System.out.println("\n\n5. Iterator Benefits Demo:");
        System.out.println("=".repeat(60));
        
        System.out.println("📊 Collection Statistics:");
        System.out.println("   Total books: " + library.size());
        System.out.println("   Sci-Fi books: " + IteratorUtils.count(library.createGenreIterator("Sci-Fi")));
        System.out.println("   Fantasy books: " + IteratorUtils.count(library.createGenreIterator("Fantasy")));
        System.out.println("   Books 1940-1970: " + IteratorUtils.count(library.createYearRangeIterator(1940, 1970)));
        System.out.println("   High-rated books: " + IteratorUtils.count(library.createHighRatedIterator(4.7)));
        
        System.out.println("\n=== Iterator Pattern Benefits Demonstrated ===");
        System.out.println("✅ Uniform interface for traversing different collections");
        System.out.println("✅ Multiple simultaneous iterations on same collection");
        System.out.println("✅ Different traversal strategies (forward, reverse, filtered)");
        System.out.println("✅ Memory efficient - no need to load entire collection");
        System.out.println("✅ Iterator independence - each maintains its own state");
        System.out.println("✅ Support for remove operations during iteration");
        
        System.out.println("\n=== Demo Complete ===");
    }
} 