import java.io.IOException;
import java.nio.file.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class DetectionService {
    // Logger for printing to console & writing to a file
    private static final Logger logger = Logger.getLogger(DetectionService.class.getName());
    
    // Path to the folder we want to monitor
    private static final String WATCH_FOLDER = "C:/Users/Admin/Ransomware-Detection/logs";
    
    // Path to the log file (where warnings & errors are stored)
    private static final String LOG_FILE = "C:/Users/Admin/Ransomware-Detection/logs/detection.log";
    
    // Path to quarantine folder (where encrypted files will be moved)
    private static final String QUARANTINE_FOLDER = "C:/Users/Admin/Ransomware-Detection/quarantine";

    public static void main(String[] args) {
        try {
            // 1. Setup File Logging
            FileHandler fileHandler = new FileHandler(LOG_FILE, true); // 'true' for appending
            logger.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.setLevel(Level.ALL);

            // 2. Create Quarantine Folder if it doesn't exist
            Path quarantinePath = Paths.get(QUARANTINE_FOLDER);
            if (!Files.exists(quarantinePath)) {
                Files.createDirectories(quarantinePath);
                System.out.println("📁 Created quarantine folder: " + quarantinePath);
            }

            // 3. WatchService to monitor folder
            WatchService watchService = FileSystems.getDefault().newWatchService();
            
            // Convert our WATCH_FOLDER path to an absolute path
            Path folderToWatch = Paths.get(WATCH_FOLDER).toAbsolutePath();
            if (!Files.exists(folderToWatch)) {
                Files.createDirectories(folderToWatch);
                System.out.println("📁 Created logs folder: " + folderToWatch);
            }

            // Register events: CREATE, MODIFY, (optional DELETE)
            folderToWatch.register(watchService,
                                   StandardWatchEventKinds.ENTRY_CREATE,
                                   StandardWatchEventKinds.ENTRY_MODIFY);

            System.out.println("🔍 Monitoring folder for suspicious changes: " + folderToWatch);
            logger.info("Detection Service Started. Monitoring: " + folderToWatch);

            // 4. Infinite Loop to Watch for Events
            while (true) {
                WatchKey key = watchService.take(); // Wait for an event
                for (WatchEvent<?> event : key.pollEvents()) {
                    // The file that was changed/created
                    Path changedFileName = (Path) event.context();
                    Path changedFilePath = folderToWatch.resolve(changedFileName);

                    String message = "Suspicious activity detected: " + changedFileName;
                    System.out.println("⚠️ " + message);
                    logger.warning(message);

                    // 5. Check if File is Encrypted
                    if (changedFilePath.toString().endsWith(".encrypted")) {
                        String ransomwareMsg = "🚨 Ransomware activity detected: " + changedFilePath;
                        System.out.println(ransomwareMsg);
                        logger.severe(ransomwareMsg);

                        // 6. Move Encrypted File to Quarantine
                        Path quarantinedFilePath = quarantinePath.resolve(changedFileName);
                        try {
                            Files.move(changedFilePath, quarantinedFilePath, StandardCopyOption.REPLACE_EXISTING);
                            String quarantineMsg = "🔒 Moved to quarantine: " + quarantinedFilePath;
                            System.out.println(quarantineMsg);
                            logger.severe(quarantineMsg);
                        } catch (IOException e) {
                            String errorMsg = "❌ Failed to move file to quarantine: " + e.getMessage();
                            System.err.println(errorMsg);
                            logger.severe(errorMsg);
                        }
                    }
                }
                // 7. Reset the Key to Continue Watching
                key.reset();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            logger.severe("Error in DetectionService: " + e.getMessage());
        }
    }
}
