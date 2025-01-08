# README: TymeX_OnlineTest-MobileIntern

## 1. Overview
This project is composed of three main parts:

### **Part 1: Core Mobile Application Development**
This section focuses on developing a basic mobile application, which is the core skill in this project. The mobile app allows users to convert currencies using real-time exchange rates.

To run this part of the project:
- Navigate to `java/com/example/currencyconverter/MainActivity.kt`.
- Click the green arrow beside the `MainActivity : ComponentActivity()` class to launch the application.

### **Part 2: Product Inventory Management**
This module focuses on managing product inventory, which includes adding, updating, and listing products. It helps in developing data management and processing skills.

To run this part of the project:
- Navigate to `java/com/example/devskillchecker/ProductInventoryManagement.kt`.
- Click the green arrow beside the `fun main()` function to run the program.

### **Part 3: Array Manipulation and Missing Number Problem**
This section involves solving the missing number problem and manipulating arrays. It emphasizes algorithm development and problem-solving skills.

To run this part of the project:
- Navigate to `java/com/example/missingnumberfinder/MissingNumberFinder.kt`.
- Click the green arrow beside the `fun main()` function to execute the program.

---

## 2. Installation and Usage

### **Installation**

#### 1. System Requirements:
- Android device running **Android 8.0 (Oreo)** or higher.
- Active internet connection to fetch real-time exchange rate data from the API.

#### 2. Installation Steps:
1. Clone or download the source code from the repository:
   ```bash
   git clone https://github.com/VanChien411/TymeX_OnlineTest-MobileIntern.git
   ```
2. Open the project in **Android Studio** and wait for Gradle to sync and download necessary dependencies.
3. Connect a real Android device or use an emulator in Android Studio.
4. Navigate to `java/com/example/currencyconverter/MainActivity.kt`.
   - Click the green arrow beside the `MainActivity : ComponentActivity()` class to launch the application.

**Note:**
If you encounter the following error during the build process:
```plaintext
Could not determine the dependencies of task ':app:compileDebugJavaWithJavac'.
> SDK location not found. Define a valid SDK location with an ANDROID_HOME environment variable or by setting the sdk.dir path in your project's local properties file at 'C:\ThucTap\TymeX_OnlineTest-MobileIntern\MissingNumberFinder\local.properties'.
```
Fix this issue by editing the `local.properties` file located in the `CurrencyConverter/` directory and setting the correct SDK path.
- You can create a new Android project, copy the SDK path from its `local.properties` file, and paste it into the problematic file.

Example:
```plaintext
sdk.dir=C:\Users\admin\AppData\Local\Android\Sdk
```

## Author
Van Chien - [GitHub Profile](https://github.com/VanChien411)

Email: phuthienchien3@gmail.com 
Phone: 0328089720