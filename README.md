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
<img src="https://github.com/BADAM2001/seeker/blob/2b1056c26f459bec841d4c50b32ad2f61ffb652a/6310103429900520282.jpg?raw=true" width="500">

### Main Dashboard
<img src="https://github.com/BADAM2001/seeker/blob/8844681b23542f98b2aa020d488b51d1bb83e433/6310103429900520285.jpg?raw=true" width="500">

### Battery Monitor
<img src="https://github.com/BADAM2001/seeker/blob/824f3fe319cc82dd21cbdc02f561b15e6f17e149/6310103429900520283.jpg?raw=true" width="500">

### Temperature Gauge
<img src="https://github.com/BADAM2001/seeker/blob/0052bce68eab2e8bc98c84bc9cf071736e1d9b3a/6310103429900520284.jpg?raw=true" width="500">

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
