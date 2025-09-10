# Windows Wallpaper Daemon

A Java-based desktop application that automatically changes Windows wallpapers at specified intervals. This application provides a user-friendly GUI interface with system tray integration and database-backed wallpaper management.

![Automatic Wallpaper Changing](https://user-gen-media-assets.s3.amazonaws.com/gpt4o_images/0f107821-f2e0-4806-b348-d949d3d5c45a.png)

## 🚀 Features

- **Automatic Wallpaper Changing**: Set custom intervals (1-1440 minutes) for automatic wallpaper rotation
- **GUI Interface**: Easy-to-use Swing-based interface for managing wallpapers and settings
- **System Tray Integration**: Runs in the background with system tray icon and notifications
- **Database Storage**: Persistent storage of wallpaper files and settings using SQLite database
- **Multiple Image Formats**: Supports JPG, JPEG, PNG, BMP, and GIF image formats
- **Wallpaper History**: Tracks wallpaper change history with timestamps
- **Configurable Settings**: Customizable change intervals for automatic wallpaper rotation
- **Windows API Integration**: Uses JNA (Java Native Access) to call Windows SystemParametersInfo API

![Windows Automatic Wallpaper Settings](https://d2d00szk9na1qq.cloudfront.net/Product/Product_Details/1/ContentImage/1/0001_20240123001425.png)

## 🏗️ System Architecture

The Windows Wallpaper Daemon follows a modular architecture with clear separation of concerns:

![System Architecture Diagram](https://user-gen-media-assets.s3.amazonaws.com/gpt4o_images/0f107821-f2e0-4806-b348-d949d3d5c45a.png)

### Architecture Components:

1. **Presentation Layer (GUI)**
   - Java Swing-based user interface
   - System tray integration with notifications
   - Real-time status updates and controls

2. **Business Logic Layer**
   - Wallpaper changing logic and scheduling
   - File validation and management
   - Settings management and persistence

3. **Data Access Layer**
   - SQLite database operations
   - CRUD operations for wallpapers and settings
   - Change history logging

4. **System Integration Layer**
   - Windows API integration via JNA
   - File system operations
   - System tray and notification services

![Java Database Management Interface](https://d2d00szk9na1qq.cloudfront.net/Product/Product_Details/1/ContentImage/1/0001_20240313002525.png)

## 📋 Requirements

### System Requirements
- **Operating System**: Windows 7/8/10/11
- **Java Version**: Java 8 or higher
- **Architecture**: x86 or x64

### Dependencies
- **JNA (Java Native Access)**: Version 5.17.0 (Latest)
  - `jna-5.17.0.jar`
  - `jna-platform-5.17.0.jar`
- **SQLite JDBC**: Version 3.50.3.0 (Latest)
  - `sqlite-jdbc-3.50.3.0.jar`

## 📦 Installation & Setup

### 1. Download Dependencies

Create a `lib` folder in your project directory and download the following JAR files:

```
lib/
├── jna-5.17.0.jar
├── jna-platform-5.17.0.jar
└── sqlite-jdbc-3.50.3.0.jar
```

**Download Links:**
- JNA: [Maven Central - JNA 5.17.0](https://mvnrepository.com/artifact/net.java.dev.jna/jna/5.17.0)
- JNA Platform: [Maven Central - JNA Platform 5.17.0](https://mvnrepository.com/artifact/net.java.dev.jna/jna-platform/5.17.0)
- SQLite JDBC: [Maven Central - SQLite JDBC 3.50.3.0](https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc/3.50.3.0)

### 2. Database Setup

Run the provided SQL schema to initialize the SQLite database:

```sql
-- Execute wallpaper_daemon_schema.sql
-- Creates tables: wallpaper_settings, wallpaper_files, change_history
```

The application will automatically create the SQLite database file (`wallpaper_daemon.db`) on first run.

### 3. Compilation

Compile all Java files with the required dependencies:

```bash
javac -cp ".;lib/jna-5.17.0.jar;lib/jna-platform-5.17.0.jar;lib/sqlite-jdbc-3.50.3.0.jar" *.java
```

**Note**: Use semicolon (`;`) as classpath separator on Windows, colon (`:`) on Linux/Mac.

### 4. Running the Application

#### Option 1: Direct Launch
```bash
java -cp ".;lib/jna-5.17.0.jar;lib/jna-platform-5.17.0.jar;lib/sqlite-jdbc-3.50.3.0.jar" WallpaperDaemon
```

#### Option 2: Using Launcher
```bash
java -cp ".;lib/jna-5.17.0.jar;lib/jna-platform-5.17.0.jar;lib/sqlite-jdbc-3.50.3.0.jar" WallpaperDaemonLauncher
```

## 🎯 Usage Instructions

### Adding Wallpapers
1. Click **"Add Wallpaper"** button
2. Select one or multiple image files (JPG, PNG, BMP, GIF)
3. Selected wallpapers will be added to the collection and database

### Configuring Settings
1. Set **Change Interval** (1-1440 minutes)
2. Click **"Save Settings"** to persist changes

### Starting the Daemon
1. Ensure wallpapers are added to the collection
2. Click **"Start Daemon"** to begin automatic wallpaper changing
3. Application will minimize to system tray
4. Receive notifications when wallpapers change

### System Tray Operations

The application integrates seamlessly with the Windows system tray for background operation:

![Windows System Tray](https://d2d00szk9na1qq.cloudfront.net/Product/Product_Details/1/ContentImage/1/0001_20240112002325.png)

- **Right-click tray icon**: Access context menu
- **Show**: Restore main window
- **Exit**: Stop daemon and close application

![System Tray Expanded View](https://d2d00szk9na1qq.cloudfront.net/Product/Product_Details/1/ContentImage/1/0001_20240112002526.png)

## 🗂️ Project Structure

```
Windows-Wallpaper-Daemon/
├── src/
│   ├── WallpaperDaemon.java          # Main GUI application
│   ├── WallpaperChanger.java         # Windows API wallpaper changer
│   ├── WallpaperDaemonLauncher.java  # Application launcher
│   ├── WallpaperFile.java            # Wallpaper file data model
│   ├── WallpaperSettings.java        # Settings data model
│   └── DatabaseManager.java          # SQLite database operations
├── lib/
│   ├── jna-5.17.0.jar
│   ├── jna-platform-5.17.0.jar
│   └── sqlite-jdbc-3.50.3.0.jar
├── wallpaper_daemon_schema.sql       # SQLite database schema
├── wallpaper_daemon.db               # SQLite database file (auto-created)
└── README.md
```

## 🔧 Technical Details

### Database Schema (SQLite)
- **wallpaper_settings**: Stores daemon configuration (change intervals, auto-start preferences)
- **wallpaper_files**: Manages wallpaper file information (file paths, names, sizes)
- **change_history**: Logs wallpaper change events with timestamps

### SQLite Database Connection
```java
// JDBC URL for SQLite
String url = "jdbc:sqlite:wallpaper_daemon.db";
Connection conn = DriverManager.getConnection(url);
```

### Windows API Integration
- Uses `SystemParametersInfoA/W` for wallpaper changes
- Supports Unicode file paths
- Validates image file formats before setting
- Automatically applies system default wallpaper positioning

### Key Classes and Responsibilities

#### WallpaperDaemon.java
- Main GUI application with Swing components
- Event handling for user interactions
- Timer management for automatic wallpaper changes
- System tray integration and notifications

#### WallpaperChanger.java
- JNA interface for Windows User32.dll
- SystemParametersInfo API calls
- Image file validation
- Error handling for wallpaper setting operations

#### DatabaseManager.java
- SQLite connection management
- CRUD operations for wallpapers and settings
- Database schema initialization
- Transaction management

## 🐛 Troubleshooting

### Common Issues

#### 1. Black Screen Wallpaper
- **Cause**: Invalid file path or unsupported image format
- **Solution**: 
  - Verify image file exists and is accessible
  - Use supported formats (JPG, PNG, BMP)
  - Check file permissions
  - Test with different image sizes and resolutions

#### 2. Compilation Errors
- **Cause**: Missing JNA or SQLite JDBC dependencies
- **Solution**:
  - Download correct JNA JAR files (version 5.17.0)
  - Include SQLite JDBC JAR (version 3.50.3.0) in classpath
  - Verify classpath includes all required JARs
  - Use proper classpath separators for your OS

#### 3. System Tray Not Working
- **Cause**: System tray not supported on the system
- **Solution**: 
  - Check if `SystemTray.isSupported()` returns true
  - Run with proper desktop environment

#### 4. Database Connection Issues
- **Cause**: Missing SQLite JDBC dependency or database file permissions
- **Solution**: 
  - Include `sqlite-jdbc-3.50.3.0.jar` in classpath
  - Ensure write permissions in application directory
  - Check SQLite database file creation

#### 5. SQLite Database Errors
- **Cause**: Corrupted database file or schema issues
- **Solution**:
  - Delete `wallpaper_daemon.db` file to recreate
  - Re-run schema initialization
  - Check SQL syntax in schema file

### Debug Tips
- Check console output for error messages
- Verify wallpaper file paths are absolute
- Ensure Windows API calls return success status
- Test with different image formats and sizes
- Monitor SQLite database file creation and growth

## 📝 Development Notes

### Latest Dependencies (September 2025)
- **JNA 5.17.0**: Released March 16, 2025 - Latest stable version with enhanced Windows API support
- **SQLite JDBC 3.50.3.0**: Released July 21, 2025 - Latest version with improved performance and bug fixes

### SQLite Integration
The application uses SQLite JDBC for local database storage:
- **Database File**: `wallpaper_daemon.db` (created automatically)
- **Driver Class**: `org.sqlite.JDBC`
- **Connection URL**: `jdbc:sqlite:wallpaper_daemon.db`

### DatabaseManager Implementation
The `DatabaseManager.java` class should implement:
- SQLite database connection management
- CRUD operations for wallpapers and settings
- Change history logging
- Database schema initialization

### Design Patterns Used
- **MVC Pattern**: Separation of GUI, business logic, and data access
- **Observer Pattern**: Event-driven GUI updates and notifications
- **Singleton Pattern**: Database connection management
- **Timer Pattern**: Scheduled wallpaper changes

### Future Enhancements
- Multiple monitor support
- Scheduled wallpaper changes
- Image filters and effects
- Online wallpaper sources
- Wallpaper collections/playlists
- Database backup and restore
- Custom wallpaper positioning and alignment options
- Web-based remote control interface

## 🏫 Academic Project Information

**Course**: Master of Computer Applications (MCA)  
**Institution**: Reva University, Bengaluru  
**Project Type**: Java GUI Application with SQLite Database Integration  
**Technologies**: Java Swing, JNA 5.17.0, SQLite JDBC 3.50.3.0, Windows API  

### Learning Objectives Achieved:
- **Database Integration**: Practical implementation of SQLite with Java using latest JDBC drivers
- **GUI Development**: Advanced Swing components and event handling
- **System Programming**: Windows API integration using latest JNA libraries
- **Software Architecture**: Modular design with clear separation of concerns
- **Error Handling**: Comprehensive exception handling and user feedback
- **Version Management**: Working with latest stable dependency versions

## 📄 License

This project is developed for educational purposes as part of MCA curriculum.

## 👨‍💻 Author

**Atharv Shukla**  
MCA Student, Reva University  
Bengaluru, Karnataka, India

---

*For any issues or questions, please refer to the troubleshooting section or check the console output for detailed error messages.*