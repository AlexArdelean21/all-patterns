import java.util.*;

// Abstract template class
abstract class DataProcessor {
    
    // Template method - defines the skeleton of the algorithm
    public final void processData(String filePath) {
        System.out.println("🔄 Starting data processing pipeline...\n");
        
        // Step 1: Read data
        String rawData = readData(filePath);
        if (rawData == null || rawData.isEmpty()) {
            System.out.println("❌ No data to process");
            return;
        }
        
        // Step 2: Validate data
        if (!validateData(rawData)) {
            System.out.println("❌ Data validation failed");
            return;
        }
        
        // Step 3: Process data (abstract - must be implemented by subclasses)
        String processedData = processDataImplementation(rawData);
        
        // Step 4: Transform data (optional hook)
        if (needsTransformation()) {
            processedData = transformData(processedData);
        }
        
        // Step 5: Save results
        saveResults(processedData);
        
        // Step 6: Optional cleanup
        if (needsCleanup()) {
            cleanup();
        }
        
        System.out.println("\n✅ Data processing pipeline completed");
    }
    
    // Concrete methods (common for all processors)
    protected String readData(String filePath) {
        System.out.println("📖 Reading data from: " + filePath);
        // Simulate reading file
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return simulateFileContent(filePath);
    }
    
    protected boolean validateData(String data) {
        System.out.println("✅ Validating data...");
        // Basic validation
        boolean isValid = data != null && data.length() > 0 && !data.trim().isEmpty();
        System.out.println("   Validation result: " + (isValid ? "PASS" : "FAIL"));
        return isValid;
    }
    
    protected void saveResults(String processedData) {
        System.out.println("💾 Saving processed results...");
        System.out.println("   Data size: " + processedData.length() + " characters");
        System.out.println("   Saved to: processed_" + getProcessorType().toLowerCase() + "_data.txt");
    }
    
    // Abstract method - must be implemented by subclasses
    protected abstract String processDataImplementation(String rawData);
    protected abstract String getProcessorType();
    
    // Hook methods - can be overridden by subclasses (optional)
    protected boolean needsTransformation() {
        return false; // Default: no transformation needed
    }
    
    protected String transformData(String data) {
        System.out.println("🔄 Applying data transformation...");
        return data; // Default: no transformation
    }
    
    protected boolean needsCleanup() {
        return false; // Default: no cleanup needed
    }
    
    protected void cleanup() {
        System.out.println("🧹 Performing cleanup...");
    }
    
    // Helper method to simulate file content
    private String simulateFileContent(String filePath) {
        if (filePath.contains("sales")) {
            return "Date,Product,Quantity,Price\n2024-01-01,Laptop,5,999.99\n2024-01-02,Mouse,10,29.99\n2024-01-03,Keyboard,8,79.99";
        } else if (filePath.contains("logs")) {
            return "[INFO] Application started\n[ERROR] Database connection failed\n[WARN] Memory usage high\n[INFO] User login successful";
        } else if (filePath.contains("survey")) {
            return "Q1:5,Q2:4,Q3:3,Q4:5,Q5:2\nQ1:3,Q2:4,Q3:4,Q4:3,Q5:4\nQ1:5,Q2:5,Q3:5,Q4:4,Q5:5";
        } else {
            return "Sample data for processing";
        }
    }
}

// Concrete implementation 1: CSV Data Processor
class CSVDataProcessor extends DataProcessor {
    
    @Override
    protected String processDataImplementation(String rawData) {
        System.out.println("🔢 Processing CSV data...");
        
        String[] lines = rawData.split("\n");
        List<String> processedLines = new ArrayList<>();
        
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (i == 0) {
                // Header row
                processedLines.add("PROCESSED_" + line.toUpperCase());
            } else {
                // Data rows - add row number and process
                String[] fields = line.split(",");
                StringBuilder processedLine = new StringBuilder();
                processedLine.append("ROW_").append(i).append(",");
                
                for (String field : fields) {
                    processedLine.append(field.trim()).append(",");
                }
                
                // Remove trailing comma
                processedLines.add(processedLine.toString().replaceAll(",$", ""));
            }
        }
        
        System.out.println("   Processed " + (lines.length - 1) + " data rows");
        return String.join("\n", processedLines);
    }
    
    @Override
    protected boolean needsTransformation() {
        return true; // CSV needs transformation
    }
    
    @Override
    protected String transformData(String data) {
        System.out.println("🔄 Transforming CSV to JSON format...");
        // Simulate CSV to JSON transformation
        return "{\n  \"csv_data\": [\n    " + data.replace("\n", "\",\n    \"") + "\"\n  ]\n}";
    }
    
    @Override
    protected String getProcessorType() {
        return "CSV";
    }
}

// Concrete implementation 2: Log Data Processor
class LogDataProcessor extends DataProcessor {
    
    @Override
    protected String processDataImplementation(String rawData) {
        System.out.println("📊 Processing log data...");
        
        String[] lines = rawData.split("\n");
        Map<String, Integer> logCounts = new HashMap<>();
        List<String> processedEntries = new ArrayList<>();
        
        for (String line : lines) {
            if (line.contains("[INFO]")) {
                logCounts.put("INFO", logCounts.getOrDefault("INFO", 0) + 1);
                processedEntries.add("INFO: " + line.substring(line.indexOf("]") + 1).trim());
            } else if (line.contains("[ERROR]")) {
                logCounts.put("ERROR", logCounts.getOrDefault("ERROR", 0) + 1);
                processedEntries.add("ERROR: " + line.substring(line.indexOf("]") + 1).trim());
            } else if (line.contains("[WARN]")) {
                logCounts.put("WARN", logCounts.getOrDefault("WARN", 0) + 1);
                processedEntries.add("WARN: " + line.substring(line.indexOf("]") + 1).trim());
            }
        }
        
        System.out.println("   Log summary: " + logCounts);
        
        // Create summary
        StringBuilder result = new StringBuilder();
        result.append("=== LOG PROCESSING SUMMARY ===\n");
        for (Map.Entry<String, Integer> entry : logCounts.entrySet()) {
            result.append(entry.getKey()).append(": ").append(entry.getValue()).append(" entries\n");
        }
        result.append("\n=== PROCESSED ENTRIES ===\n");
        result.append(String.join("\n", processedEntries));
        
        return result.toString();
    }
    
    @Override
    protected boolean needsCleanup() {
        return true; // Logs need cleanup
    }
    
    @Override
    protected void cleanup() {
        System.out.println("🧹 Cleaning up temporary log files...");
        System.out.println("   Removed temporary files");
        System.out.println("   Cleared memory buffers");
    }
    
    @Override
    protected String getProcessorType() {
        return "LOG";
    }
}

// Concrete implementation 3: Survey Data Processor
class SurveyDataProcessor extends DataProcessor {
    
    @Override
    protected String processDataImplementation(String rawData) {
        System.out.println("📈 Processing survey data...");
        
        String[] responses = rawData.split("\n");
        Map<String, List<Integer>> questionScores = new HashMap<>();
        
        for (String response : responses) {
            String[] scores = response.split(",");
            for (int i = 0; i < scores.length; i++) {
                String question = "Q" + (i + 1);
                String scoreStr = scores[i].split(":")[1];
                int score = Integer.parseInt(scoreStr);
                
                questionScores.computeIfAbsent(question, k -> new ArrayList<>()).add(score);
            }
        }
        
        // Calculate averages
        StringBuilder result = new StringBuilder();
        result.append("=== SURVEY ANALYSIS RESULTS ===\n");
        
        for (Map.Entry<String, List<Integer>> entry : questionScores.entrySet()) {
            String question = entry.getKey();
            List<Integer> scores = entry.getValue();
            double average = scores.stream().mapToInt(Integer::intValue).average().orElse(0.0);
            
            result.append(question).append(": Average = ").append(String.format("%.2f", average));
            result.append(" (Responses: ").append(scores.size()).append(")\n");
        }
        
        System.out.println("   Analyzed " + responses.length + " survey responses");
        return result.toString();
    }
    
    @Override
    protected boolean needsTransformation() {
        return true;
    }
    
    @Override
    protected String transformData(String data) {
        System.out.println("🔄 Generating visual report...");
        return data + "\n=== VISUAL REPRESENTATION ===\n" +
               "📊 Charts and graphs would be generated here\n" +
               "📈 Trend analysis completed\n" +
               "📋 Executive summary prepared";
    }
    
    @Override
    protected String getProcessorType() {
        return "SURVEY";
    }
}

// Data processing manager
class DataProcessingManager {
    private Map<String, DataProcessor> processors;
    
    public DataProcessingManager() {
        processors = new HashMap<>();
        processors.put("csv", new CSVDataProcessor());
        processors.put("log", new LogDataProcessor());
        processors.put("survey", new SurveyDataProcessor());
    }
    
    public void processFile(String filePath) {
        String fileType = determineFileType(filePath);
        DataProcessor processor = processors.get(fileType);
        
        if (processor != null) {
            System.out.println("🎯 Using " + processor.getProcessorType() + " processor for: " + filePath);
            processor.processData(filePath);
        } else {
            System.out.println("❌ No processor available for file type: " + fileType);
        }
    }
    
    private String determineFileType(String filePath) {
        if (filePath.contains("sales") || filePath.endsWith(".csv")) {
            return "csv";
        } else if (filePath.contains("logs") || filePath.endsWith(".log")) {
            return "log";
        } else if (filePath.contains("survey")) {
            return "survey";
        } else {
            return "unknown";
        }
    }
    
    public void showAvailableProcessors() {
        System.out.println("📋 Available Data Processors:");
        for (Map.Entry<String, DataProcessor> entry : processors.entrySet()) {
            System.out.println("   " + entry.getKey().toUpperCase() + " → " + 
                             entry.getValue().getProcessorType() + " Processor");
        }
    }
}

public class TemplateDemo {
    public static void main(String[] args) {
        System.out.println("=== Template Method Pattern Demo ===\n");
        
        DataProcessingManager manager = new DataProcessingManager();
        manager.showAvailableProcessors();
        
        // Test different file types
        String[] testFiles = {
            "sales_data.csv",
            "application.log", 
            "customer_survey.txt",
            "unknown_file.xyz"
        };
        
        for (String file : testFiles) {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("Processing: " + file);
            System.out.println("=".repeat(60));
            manager.processFile(file);
        }
        
        // Demonstrate direct processor usage
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Direct Processor Usage Demo");
        System.out.println("=".repeat(60));
        
        CSVDataProcessor csvProcessor = new CSVDataProcessor();
        csvProcessor.processData("direct_sales.csv");
        
        System.out.println("\n=== Template Method Benefits Demonstrated ===");
        System.out.println("✅ Common algorithm structure shared across all processors");
        System.out.println("✅ Specific processing logic varies per data type");
        System.out.println("✅ Hook methods allow optional customization");
        System.out.println("✅ Template method prevents algorithm modification");
        
        System.out.println("\n=== Demo Complete ===");
    }
} 