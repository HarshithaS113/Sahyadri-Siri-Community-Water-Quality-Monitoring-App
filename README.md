# Sahyadri Siri – Community Water Quality Monitoring App

## 📌 Project Overview

Sahyadri Siri is a community-driven Android application designed to monitor water quality, report pollution incidents, and promote environmental awareness through real-time digital reporting and cloud-based technologies.

The application enables users to collect water quality observations, upload images, capture GPS locations, and visualize pollution reports using Google Maps integration. The platform encourages citizen participation in environmental monitoring and supports sustainable water conservation efforts.

---

# 🌍 Problem Statement

Water pollution is a major environmental challenge affecting ecosystems, agriculture, and public health. Many local water bodies remain unmonitored due to:

- Lack of real-time monitoring systems
- Delayed pollution reporting
- Limited community participation
- Absence of centralized environmental data systems
- Difficulty in collecting location-specific water quality data

Sahyadri Siri addresses these challenges by providing a smart mobile platform for community-based environmental reporting and water quality monitoring.

---

# 🎯 Objectives

- Develop a mobile application for water quality monitoring
- Enable users to report pollution incidents in real time
- Provide GPS-based location tracking for reports
- Integrate cloud databases for real-time synchronization
- Promote community participation in environmental conservation
- Visualize environmental data using Google Maps
- Support environmental awareness and sustainability

---

# ✨ Features

## 🔐 User Authentication
- Secure user registration and login
- Firebase Authentication integration
- Password recovery and user profile management

## 💧 Water Quality Reporting
- Submit pollution and water quality reports
- Add water condition details:
  - Water color
  - Smell
  - Pollution level
  - Waste presence
  - Water flow condition
- Add comments and observations
- Upload water body images

## 📍 GPS & Google Maps Integration
- Automatic GPS coordinate capture
- Real-time map visualization
- Pollution marker display
- Nearby water body identification

## ☁️ Real-Time Cloud Database
- Firebase Firestore integration
- Cloud-based data synchronization
- Multi-user accessibility
- Scalable environmental data management

## 🔔 Pollution Alert Notifications
- Real-time environmental alerts
- New report notifications
- Awareness campaign notifications
- Firebase Cloud Messaging integration

## 🖼️ Image Upload System
- Upload pollution evidence images
- Cloud image storage
- Historical image tracking

## 📊 Dashboard & Analytics
- Interactive environmental dashboard
- Pollution trend tracking
- User activity statistics
- Area-wise environmental monitoring

## 📶 Offline Data Collection
- Offline report storage using Room Database
- Automatic synchronization after reconnection
- Offline-first architecture support

## 🔎 Search & Filter System
- Location-based filtering
- Pollution severity filtering
- Date-wise filtering
- Water body type filtering

## 🛠️ Admin Monitoring System
- Verify pollution reports
- Monitor user activities
- Remove invalid reports
- Generate environmental statistics

---

# 🛠️ Technologies Used

## Frontend
- Kotlin
- Jetpack Compose
- XML Layouts
- Material Design Components

## Backend & Cloud
- Firebase Authentication
- Firebase Firestore
- Firebase Storage
- Firebase Cloud Messaging (FCM)

## Database
- Room Database

## Additional Technologies
- Google Maps API
- GPS & Location Services
- Git & GitHub
- Google AI Studio

---

# ⚙️ System Workflow

1. User registers and logs into the application
2. User visits a nearby water body
3. Water quality observations are recorded
4. Images and comments are uploaded
5. GPS location is captured automatically
6. Reports are stored in Firebase Firestore
7. Pollution markers appear on maps
8. Users and authorities receive notifications
9. Environmental statistics are updated

---

# 📂 Modules

- Authentication Module
- Water Quality Reporting Module
- GPS & Maps Module
- Cloud Synchronization Module
- Notification Module
- Admin Verification Module
- Offline Storage Module
- Dashboard & Analytics Module

---

# 🧠 Key Technical Implementations

## Offline-First Synchronization
- Reports are stored locally using Room Database
- Automatic cloud synchronization when internet is available

## Repository Pattern Architecture
- Clean separation of data handling and UI layers
- Improved maintainability and scalability

## Reactive UI using StateFlow
- Modern UI state management
- Real-time UI updates

## Firebase Cloud Integration
- Real-time report synchronization
- Notification support
- Cloud storage integration

## Google Maps Visualization
- Pollution markers and environmental visualization
- GPS-based report tracking

---

# 🌱 Environmental & Social Impact

- Encourages citizen participation in environmental monitoring
- Promotes water conservation awareness
- Supports pollution identification and reporting
- Enables community-driven environmental protection
- Helps authorities identify pollution-prone areas

---

# 🚀 Future Scope

- AI-based pollution prediction systems
- IoT sensor integration for automated water quality analysis
- Machine learning-based environmental analytics
- Government environmental portal integration
- Multi-language support
- Advanced analytics dashboard
- Drone-based environmental monitoring

---

# 📸 Screenshots

## Login Screen
(Add Screenshot Here)

## Dashboard
(Add Screenshot Here)

## Water Report Screen
(Add Screenshot Here)

## Google Maps Screen
(Add Screenshot Here)

## Notifications Screen
(Add Screenshot Here)

---

# 📁 Project Structure

```bash
app/
 ├── ui/
 ├── data/
 ├── repository/
 ├── viewmodel/
 ├── firebase/
 ├── maps/
 ├── database/
 └── utils/
```

---

# 📥 Installation

## Clone the Repository

```bash
git clone https://github.com/HarshithaS113/Sahyadri-Siri-Community-Water-Quality-Monitoring-App.git
```

## Open in Android Studio
- Open Android Studio
- Select "Open Existing Project"
- Choose the project folder

## Configure Firebase
- Add `google-services.json`
- Enable Firebase Authentication
- Configure Firestore Database
- Enable Firebase Storage & FCM

## Run the Application
- Sync Gradle files
- Connect Android device or emulator
- Run the application

---

# 👩‍💻 Contributors

- Harshitha S

---

# 📜 License

This project is developed for educational, environmental awareness, and community welfare purposes.

---

# ⭐ Conclusion

Sahyadri Siri demonstrates how modern Android technologies, cloud integration, and community participation can be combined to build a scalable environmental monitoring system.

The project promotes sustainable environmental practices and showcases the effective use of technology for public welfare and water conservation.
````
