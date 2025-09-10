# Wallpaper Daemon Setup Guide

## Quick Start

### Step 1: Download Dependencies

Create a `lib` folder in your project directory and download these JAR files:

1. **JNA (Java Native Access)**
   - Download: https://github.com/java-native-access/jna/releases
   - Files needed: `jna-5.12.1.jar`, `jna-platform-5.12.1.jar`

2. **H2 Database**
   - Download: https://www.h2database.com/html/download.html
   - File needed: `h2-2.1.214.jar`

### Step 2: Folder Structure

Your project folder should look like this:
```
WallpaperDaemon/
├── lib/
│   ├── jna-5.12.1.jar
│   ├── jna-platform-5.12.1.jar
│   └── h2-2.1.214.jar
├── *.java (all Java source files)
├── compile.bat
├── run.bat
└── README.md
```

### Step 3: Compile and Run

1. Double-click `compile.bat` to compile
2. Double-click `run.bat` to start the application

## Manual Compilation

If you prefer manual compilation:

```bash
# Compile
javac -cp "lib/jna-5.17.0.jar;lib/jna-platform-5.17.0.jar;lib/h2-2.3.232.jar;." *.java

# Run
java -cp "lib/jna-5.17.0.jar;lib/jna-platform-5.17.0.jar;lib/h2-2.3.232.jar;." WallpaperDaemonLauncher
```

## IDE Setup (Eclipse/IntelliJ/NetBeans)

1. Create new Java project
2. Add JAR files to build path/classpath
3. Import all Java source files
4. Run `WallpaperDaemonLauncher.main()`

## First Run

1. Application will create `wallpaper_daemon_db.mv.db` file (H2 database)
2. Add some wallpaper images using "Add Wallpaper" button
3. Configure change interval (default: 60 minutes)
4. Click "Start Daemon" to begin automatic wallpaper rotation
5. Application minimizes to system tray - right-click tray icon to control

## Features Overview

- **Automatic wallpaper changing** based on timer
- **System tray integration** for background operation
- **Database storage** of wallpaper collections and settings
- **File management** with drag-and-drop support
- **Change history tracking** with timestamps
- **Configurable intervals** and wallpaper styles

Enjoy your automated wallpaper experience!
