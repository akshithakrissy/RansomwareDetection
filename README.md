# Ransomware Detection & Simulation

## 🔍 Project Overview
This project simulates ransomware attacks using **Python** and detects suspicious file modifications using **Java**. It includes:

- **Simulation Module (Python):** Encrypts files in a specified directory to mimic ransomware behavior.
- **Detection Module (Java):** Monitors a folder and logs suspicious file modifications.

## 📂 Project Structure
```
Ransomware-Detection/
│── simulation/   (Python - Ransomware Simulation)
│── detection/    (Java - Ransomware Detection)
│── logs/         (Folder to store logs)
│── README.md     (Project Documentation)
```

## ⚙️ Setup & Installation
### **1️⃣ Clone the Repository**
```sh
git clone https://github.com/akshithakrissy/RansomwareDetection.git
cd RansomwareDetection
```

### **2️⃣ Set Up Python for Ransomware Simulation**
#### Install dependencies:
```sh
pip install cryptography
```
#### Run the simulation:
```sh
python simulation/ransomware_sim.py
```
This will encrypt all files in the `logs/` folder.

### **3️⃣ Set Up Java for Ransomware Detection**
#### Compile the Java code:
```sh
cd detection
javac DetectionService.java
```
#### Run the Detection Service:
```sh
java DetectionService
```
This will monitor the `logs/` folder for any suspicious modifications.

## 📌 Features
✅ **Ransomware Simulation** – Encrypts files in a target folder using Python.  
✅ **Real-time File Monitoring** – Detects and logs unauthorized file modifications.  
✅ **Cross-Platform** – Works on Windows, macOS, and Linux.  
✅ **Logging System** – Stores detection logs for analysis.  

## 🚀 Future Enhancements
- Add a **Decryption Feature** to restore encrypted files.
- Improve ransomware simulation with **advanced encryption techniques**.
- Implement **email alerts** for suspicious activity.
