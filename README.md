# Windows Wallpaper Daemon

A Java-based desktop application that automatically changes Windows wallpapers at specified intervals. This application provides a user-friendly GUI interface with system tray integration and database-backed wallpaper management.

## 🚀 Features

- **Automatic Wallpaper Changing**: Set custom intervals (1-1440 minutes) for automatic wallpaper rotation
- **GUI Interface**: Easy-to-use Swing-based interface for managing wallpapers and settings
- **System Tray Integration**: Runs in the background with system tray icon and notifications
- **Database Storage**: Persistent storage of wallpaper files and settings using H2 database
- **Multiple Image Formats**: Supports JPG, JPEG, PNG, BMP, and GIF image formats
- **Wallpaper History**: Tracks wallpaper change history with timestamps
- **Configurable Settings**: Customizable change intervals and wallpaper styles
- **Windows API Integration**: Uses JNA (Java Native Access) to call Windows SystemParametersInfo API

## 📋 Requirements

### System Requirements
- **Operating System**: Windows 7/8/10/11
- **Java Version**: Java 8 or higher
- **Architecture**: x86 or x64

### Dependencies
- **JNA (Java Native Access)**: Version 5.13.0 or higher
  - `jna-5.13.0.jar`
  - `jna-platform-5.13.0.jar`
- **H2 Database**: Version 2.3.232 or higher
  - `h2-2.3.232.jar`

## 📦 Installation & Setup

### 1. Download Dependencies

Create a `lib` folder in your project directory and download the following JAR files:

```
lib/
├── jna-5.13.0.jar
├── jna-platform-5.13.0.jar
└── h2-2.3.232.jar
```

**Download Links:**
- JNA: [Maven Central - JNA](https://mvnrepository.com/artifact/net.java.dev.jna/jna)
- JNA Platform: [Maven Central - JNA Platform](https://mvnrepository.com/artifact/net.java.dev.jna/jna-platform)
- H2 Database: [Maven Central - H2](https://mvnrepository.com/artifact/com.h2database/h2)

### 2. Database Setup

Run the provided SQL schema to initialize the database:

```sql
-- Execute wallpaper_daemon_schema.sql
-- Creates tables: wallpaper_settings, wallpaper_files, change_history
```

The application will automatically create the H2 database file on first run.

### 3. Compilation

Compile all Java files with the required dependencies:

```bash
javac -cp ".;lib/jna-5.13.0.jar;lib/jna-platform-5.13.0.jar;lib/h2-2.3.232.jar" *.java
```

**Note**: Use semicolon (`;`) as classpath separator on Windows, colon (`:`) on Linux/Mac.

### 4. Running the Application

#### Option 1: Direct Launch
```bash
java -cp ".;lib/jna-5.13.0.jar;lib/jna-platform-5.13.0.jar;lib/h2-2.3.232.jar" WallpaperDaemon
```

#### Option 2: Using Launcher
```bash
java -cp ".;lib/jna-5.13.0.jar;lib/jna-platform-5.13.0.jar;lib/h2-2.3.232.jar" WallpaperDaemonLauncher
```

## 🎯 Usage Instructions

### Adding Wallpapers
1. Click **"Add Wallpaper"** button
2. Select one or multiple image files (JPG, PNG, BMP, GIF)
3. Selected wallpapers will be added to the collection and database

### Configuring Settings
1. Set **Change Interval** (1-1440 minutes)
2. Choose **Wallpaper Style** (CENTER, TILE, STRETCH, FIT, FILL)
3. Click **"Save Settings"** to persist changes

### Starting the Daemon
1. Ensure wallpapers are added to the collection
2. Click **"Start Daemon"** to begin automatic wallpaper changing
3. Application will minimize to system tray
4. Receive notifications when wallpapers change

### System Tray Operations
- **Right-click tray icon**: Access context menu
- **Show**: Restore main window
- **Exit**: Stop daemon and close application

## 🗂️ Project Structure

```
Windows-Wallpaper-Daemon/
├── src/
│   ├── WallpaperDaemon.java          # Main GUI application
│   ├── WallpaperChanger.java         # Windows API wallpaper changer
│   ├── WallpaperDaemonLauncher.java  # Application launcher
│   ├── WallpaperFile.java            # Wallpaper file data model
│   ├── WallpaperSettings.java        # Settings data model
│   └── DatabaseManager.java          # Database operations (missing)
├── lib/
│   ├── jna-5.13.0.jar
│   ├── jna-platform-5.13.0.jar
│   └── h2-2.3.232.jar
├── wallpaper_daemon_schema.sql       # Database schema
└── README.md
```

## 🔧 Technical Details

### Database Schema
- **wallpaper_settings**: Stores daemon configuration
- **wallpaper_files**: Manages wallpaper file information
- **change_history**: Logs wallpaper change events

### Windows API Integration
- Uses `SystemParametersInfoA/W` for wallpaper changes
- Supports Unicode file paths
- Validates image file formats before setting

### Supported Wallpaper Styles
- **CENTER**: Center image on desktop
- **TILE**: Repeat image as tiles
- **STRETCH**: Stretch to fill screen
- **FIT**: Fit maintaining aspect ratio
- **FILL**: Fill screen, may crop image

## 🐛 Troubleshooting

### Common Issues

#### 1. Black Screen Wallpaper
- **Cause**: Invalid file path or unsupported image format
- **Solution**: 
  - Verify image file exists and is accessible
  - Use supported formats (JPG, PNG, BMP)
  - Check file permissions

#### 2. Compilation Errors
- **Cause**: Missing JNA dependencies
- **Solution**:
  - Download correct JNA JAR files (non-JPMS versions)
  - Verify classpath includes all required JARs
  - Use proper classpath separators for your OS

#### 3. System Tray Not Working
- **Cause**: System tray not supported on the system
- **Solution**: 
  - Check if `SystemTray.isSupported()` returns true
  - Run with proper desktop environment

#### 4. Database Connection Issues
- **Cause**: Missing H2 database dependency
- **Solution**: Include `h2-2.3.232.jar` in classpath

### Debug Tips
- Check console output for error messages
- Verify wallpaper file paths are absolute
- Ensure Windows API calls return success status
- Test with different image formats and sizes

## 📝 Development Notes

### Missing Components
The project references `DatabaseManager.java` which is not included in the provided files. This class should implement:
- Database connection management
- CRUD operations for wallpapers and settings
- Change history logging

### Future Enhancements
- Multiple monitor support
- Scheduled wallpaper changes
- Image filters and effects
- Online wallpaper sources
- Wallpaper collections/playlists

## 🏫 Academic Project Information

**Course**: Master of Computer Applications (MCA)  
**Institution**: Reva University, Bengaluru  
**Project Type**: Java GUI Application with Database Integration  
**Technologies**: Java Swing, JNA, H2 Database, Windows API  

## 📄 License

This project is developed for educational purposes as part of MCA curriculum.

## 👨‍💻 Author

**Atharv Shukla**  
MCA Student, Reva University  
Bengaluru, Karnataka, India

---

*For any issues or questions, please refer to the troubleshooting section or check the console output for detailed error messages.*