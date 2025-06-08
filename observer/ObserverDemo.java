import java.util.*;

// Observer interface
interface Observer {
    void update(String news);
}

// Subject interface
interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}

// Concrete Subject
class NewsAgency implements Subject {
    private List<Observer> observers;
    private String latestNews;
    private String agencyName;
    
    public NewsAgency(String name) {
        this.agencyName = name;
        this.observers = new ArrayList<>();
    }
    
    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
        System.out.println("New subscriber registered to " + agencyName);
    }
    
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
        System.out.println("Subscriber unregistered from " + agencyName);
    }
    
    @Override
    public void notifyObservers() {
        System.out.println("\n[" + agencyName + "] Broadcasting news to " + 
                          observers.size() + " subscribers...");
        for (Observer observer : observers) {
            observer.update(latestNews);
        }
    }
    
    public void setNews(String news) {
        this.latestNews = news;
        System.out.println("\n[" + agencyName + "] Breaking News: " + news);
        notifyObservers();
    }
    
    public String getLatestNews() {
        return latestNews;
    }
    
    public String getAgencyName() {
        return agencyName;
    }
    
    public int getSubscriberCount() {
        return observers.size();
    }
}

// Concrete Observers
class NewsChannel implements Observer {
    private String channelName;
    private List<String> newsHistory;
    
    public NewsChannel(String name) {
        this.channelName = name;
        this.newsHistory = new ArrayList<>();
    }
    
    @Override
    public void update(String news) {
        newsHistory.add(news);
        System.out.println("📺 [" + channelName + "] Received news: " + news);
        broadcastNews(news);
    }
    
    private void broadcastNews(String news) {
        System.out.println("📺 [" + channelName + "] Broadcasting: " + news);
    }
    
    public void showNewsHistory() {
        System.out.println("\n📺 " + channelName + " News History:");
        for (int i = 0; i < newsHistory.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + newsHistory.get(i));
        }
    }
    
    public String getChannelName() {
        return channelName;
    }
}

class NewsPaper implements Observer {
    private String paperName;
    private List<String> articles;
    
    public NewsPaper(String name) {
        this.paperName = name;
        this.articles = new ArrayList<>();
    }
    
    @Override
    public void update(String news) {
        articles.add(news);
        System.out.println("📰 [" + paperName + "] Received news: " + news);
        publishArticle(news);
    }
    
    private void publishArticle(String news) {
        System.out.println("📰 [" + paperName + "] Publishing article: " + news);
    }
    
    public void printNewspaper() {
        System.out.println("\n📰 " + paperName + " - Today's Edition:");
        System.out.println("=" + "=".repeat(40));
        for (int i = 0; i < articles.size(); i++) {
            System.out.println("HEADLINE " + (i + 1) + ": " + articles.get(i));
        }
        System.out.println("=" + "=".repeat(40));
    }
    
    public String getPaperName() {
        return paperName;
    }
}

class MobileApp implements Observer {
    private String appName;
    private Queue<String> notifications;
    private boolean notificationsEnabled;
    
    public MobileApp(String name) {
        this.appName = name;
        this.notifications = new LinkedList<>();
        this.notificationsEnabled = true;
    }
    
    @Override
    public void update(String news) {
        if (notificationsEnabled) {
            notifications.offer(news);
            System.out.println("📱 [" + appName + "] Push notification: " + news);
            sendPushNotification(news);
        }
    }
    
    private void sendPushNotification(String news) {
        System.out.println("📱 [" + appName + "] 🔔 NOTIFICATION: " + 
                          (news.length() > 30 ? news.substring(0, 30) + "..." : news));
    }
    
    public void toggleNotifications() {
        notificationsEnabled = !notificationsEnabled;
        System.out.println("📱 [" + appName + "] Notifications " + 
                          (notificationsEnabled ? "ENABLED" : "DISABLED"));
    }
    
    public void showNotifications() {
        System.out.println("\n📱 " + appName + " Notifications:");
        int count = 1;
        for (String notification : notifications) {
            System.out.println("  " + count + ". " + notification);
            count++;
        }
    }
    
    public String getAppName() {
        return appName;
    }
}

// News aggregator that observes multiple agencies
class NewsAggregator implements Observer {
    private String serviceName;
    private Map<String, List<String>> newsBySource;
    
    public NewsAggregator(String name) {
        this.serviceName = name;
        this.newsBySource = new HashMap<>();
    }
    
    @Override
    public void update(String news) {
        // This would need to be enhanced to identify the source
        System.out.println("🗞️  [" + serviceName + "] Aggregating news: " + news);
    }
    
    public void update(String news, String source) {
        newsBySource.computeIfAbsent(source, k -> new ArrayList<>()).add(news);
        System.out.println("🗞️  [" + serviceName + "] Aggregated from " + source + ": " + news);
    }
    
    public void showAggregatedNews() {
        System.out.println("\n🗞️  " + serviceName + " - Aggregated News:");
        for (Map.Entry<String, List<String>> entry : newsBySource.entrySet()) {
            System.out.println("  From " + entry.getKey() + ":");
            for (String news : entry.getValue()) {
                System.out.println("    - " + news);
            }
        }
    }
}

public class ObserverDemo {
    public static void main(String[] args) {
        System.out.println("=== Observer Pattern Demo ===\n");
        
        // Create news agencies (subjects)
        NewsAgency cnn = new NewsAgency("CNN");
        NewsAgency bbc = new NewsAgency("BBC");
        
        // Create news consumers (observers)
        NewsChannel channel1 = new NewsChannel("Channel 7");
        NewsChannel channel2 = new NewsChannel("News Network");
        NewsPaper paper1 = new NewsPaper("Daily Herald");
        NewsPaper paper2 = new NewsPaper("Morning Times");
        MobileApp app1 = new MobileApp("NewsApp");
        MobileApp app2 = new MobileApp("QuickNews");
        
        System.out.println("=== Subscription Phase ===");
        
        // Subscribe to CNN
        cnn.registerObserver(channel1);
        cnn.registerObserver(paper1);
        cnn.registerObserver(app1);
        
        // Subscribe to BBC
        bbc.registerObserver(channel2);
        bbc.registerObserver(paper2);
        bbc.registerObserver(app2);
        
        // Some observers subscribe to multiple agencies
        cnn.registerObserver(channel2); // Channel 2 follows both
        bbc.registerObserver(app1);     // App1 follows both
        
        System.out.println("\n=== News Broadcasting Phase ===");
        
        // CNN publishes news
        cnn.setNews("Stock market reaches all-time high");
        cnn.setNews("New technology breakthrough announced");
        
        // BBC publishes news
        bbc.setNews("Climate summit reaches historic agreement");
        bbc.setNews("Space mission launches successfully");
        
        System.out.println("\n=== Subscription Changes ===");
        
        // App1 disables notifications
        app1.toggleNotifications();
        
        // More news after notification change
        cnn.setNews("Breaking: Major earthquake hits region");
        
        // Re-enable notifications
        app1.toggleNotifications();
        bbc.setNews("Economic forecast shows positive growth");
        
        System.out.println("\n=== Unsubscription Demo ===");
        
        // Channel1 unsubscribes from CNN
        cnn.removeObserver(channel1);
        cnn.setNews("This news won't reach Channel 7");
        
        System.out.println("\n=== Content Review ===");
        
        // Show accumulated content
        channel1.showNewsHistory();
        channel2.showNewsHistory();
        
        paper1.printNewspaper();
        
        app1.showNotifications();
        app2.showNotifications();
        
        System.out.println("\n=== Statistics ===");
        System.out.println("CNN subscribers: " + cnn.getSubscriberCount());
        System.out.println("BBC subscribers: " + bbc.getSubscriberCount());
        
        System.out.println("\n=== Demo Complete ===");
    }
} 