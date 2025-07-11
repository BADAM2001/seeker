# 🔋 Battery Guardian - Smart Battery & Temperature Monitor

An Android application that protects your device by monitoring battery health and temperature in real-time, with intelligent alerts and automated safety features.

## 🌟 Key Features

- **Real-time Battery Monitoring** (percentage, health, charging status)
- **Device Temperature Tracking** with overheating protection
- **Smart Alerts**:
  - 🔊 Voice warning at 5% battery ("Master, the battery is low please charge")
  - 📳 Vibration alerts at critical levels (≤5% or ≥95%)
- **Sleep Mode** activation when device overheats
- **Charging Analytics** with time estimates-will bedone under 2.o version
- **Historical Data Logging** to MySQL database (XAMPP/PHPMyAdmin)-will be done 2.o  version 

## 📸 Screenshots

### Splash Screen
<!-- Paste your splash.jpg link between the brackets below -->
[![Splash Screen](YOUR_SPLASH_SCREEN_LINK_HERE)](YOUR_SPLASH_SCREEN_LINK_HERE)

### Main Dashboard
<!-- Paste your main.jpg link between the brackets below -->
[![Main Activity](YOUR_MAIN_ACTIVITY_LINK_HERE)](YOUR_MAIN_ACTIVITY_LINK_HERE)

### Battery Monitor
<!-- Paste your battery.jpg link between the brackets below -->
[![Battery Page](YOUR_BATTERY_PAGE_LINK_HERE)](YOUR_BATTERY_PAGE_LINK_HERE)

### Temperature Gauge
<!-- Paste your temperature.jpg link between the brackets below -->
[![Temperature Page](YOUR_TEMPERATURE_PAGE_LINK_HERE)](YOUR_TEMPERATURE_PAGE_LINK_HERE)

## 🛠️ Technical Implementation

### Android Components
- `BroadcastReceiver` for battery changes
- `BatteryManager` for temperature readings
- `TextToSpeech` for voice alerts
- `Vibrator` service for haptic feedback
- Retrofit for MySQL data logging
  
### this section will be done in version 2.o

### Database Schema (MySQL)
```sql
CREATE TABLE device_stats (
    id INT AUTO_INCREMENT PRIMARY KEY,
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    battery_level INT,
    temperature FLOAT,
    is_charging BOOLEAN,
    charge_estimate INT
);
