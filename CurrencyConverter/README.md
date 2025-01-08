# Currency Converter Application

## 1. Overview
This application is a currency converter that allows users to input an amount and select the currencies for conversion. The result is displayed based on current exchange rates. The app is developed using Kotlin, with the UI built using Jetpack Compose, and follows the MVVM (Model-View-ViewModel) architectural pattern to separate concerns between the UI, logic, and data.

---

## 2. Project Structure
- **data**: Contains classes for data handling and network operations.
    - **network**:
        - `api.kt`: Defines API methods for Retrofit to fetch exchange rates from external services.
        - `RetrofitInstance.kt`: Provides a Retrofit instance to handle HTTP requests.
    - **repository**:
        - `CurrencyRepository.kt`: Manages data flow from the API and provides data to the ViewModel.
- **ui**: Contains UI-related classes built with Jetpack Compose.
    - `CurrencyConverterScreen.kt`: Main UI screen where users can input amounts and select currencies for conversion.
- **viewmodel**:
    - `MainActivity.kt`: Contains the ViewModel responsible for handling currency conversion logic and updating the UI using LiveData.

---

## 3. Key Features
1. **Currency Conversion**:
    - Users can input an amount and select currencies for conversion.
    - Instant swap functionality between two selected currencies.
    - Conversion results are displayed immediately upon retrieving exchange rate data.

2. **USD Exchange Rate Chart**:
    - A chart showing the exchange rate of USD against various currencies.
    - Users can set a maximum value to improve chart readability (useful for currencies with small exchange rates).

3. **Exchange Rate Updates**:
    - The app uses Retrofit to call an API and retrieve exchange rates from [https://openexchangerates.org/terms](https://openexchangerates.org/terms).
    - Exchange rate data is updated every time a conversion is performed.

4. **Additional Features**:
    - Supports light and dark modes based on the device’s theme.
    - Responsive UI handling for different screen sizes.

5. **Error Handling**:
    - The app handles and displays error messages when data cannot be retrieved due to API issues or lack of internet connectivity, ensuring a better user experience.

---

## 4. Application Flow
1. The user opens the app and inputs the amount and currencies for conversion.
2. `CurrencyConverterScreen` calls a method in the ViewModel to process the conversion.
3. The ViewModel uses `CurrencyRepository` to send a request to the API and retrieve exchange rate data.
4. The result is calculated and updated via LiveData, and the UI automatically displays the conversion result.

---

## 5. Technologies and Tools Used
- **Language**: Kotlin
- **User Interface**: Jetpack Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **Libraries**:
    - Retrofit: For API calls and fetching exchange rate data.
    - Coroutine: For asynchronous API calls and data updates.
    - LiveData: For maintaining data state and updating the UI automatically.

---

## 6. Installation and Usage
### Installation
#### 1. System Requirements:
- Android device running Android 8.0 (Oreo) or higher.
- Internet connection to fetch exchange rate data from the API.

#### 2. Installation Steps:
1. Clone or download the source code from the repository:
   ```bash
   git clone https://github.com/VanChien411/TymeX_OnlineTest-MobileIntern.git
   ```
2. Open the project in Android Studio and wait for Gradle to download dependencies.
3. Connect a real device or use an emulator in Android Studio.
4. Navigate to `java/com/example/currencyconverter/MainActivity.kt`. Click the green arrow beside the `MainActivity : ComponentActivity()` class to run the app.

   **Note**: If you encounter the following error:
   ```
   Could not determine the dependencies of task ':app:compileDebugJavaWithJavac'.
   > SDK location not found. Define a valid SDK location with an ANDROID_HOME environment variable or by setting the sdk.dir path in your project's local properties file at 'C:\ThucTap\TymeX_OnlineTest-MobileIntern\MissingNumberFinder\local.properties'.
   ```
   Fix this by editing the `local.properties` file in `CurrencyConverter/` and setting the correct SDK path. You can create a new project, copy the SDK path from its `local.properties`, and replace the incorrect path. Example:
   ```
   sdk.dir=C\:\Users\admin\AppData\Local\Android\Sdk
   ```

### Usage
1. Upon opening the app, the user will see the main currency converter interface.
2. Enter the amount to be converted and select two currencies (e.g., USD to EUR).
3. Press the convert button, and the result will be displayed immediately.
4. Users can view the USD exchange rate chart and adjust the maximum value for better chart visibility.

---

## 7. Notes and Development Challenges
### Notes:
- **Data Source**: The app uses the free API to get the reward rate, there are request limits, and the direct conversion API does not apply to the free API. For optimal performance, data is fetched at app launch and when the user switches interfaces, and conversions are recalculated from the API data.
- **Result Formatting**: Conversion results are formatted according to international standards using `DecimalFormat`, with U.S. style number separation (dot for decimals).

### Challenges:
- Handling network errors and ensuring a smooth user experience without internet connectivity.
- Implementing responsive UI for different device sizes and orientations.
- Managing API limits while ensuring real-time data updates.

---

## License
This project is licensed under the MIT License. See the LICENSE file for details.

---
## Video
Video - [Intern mobile test TymeX](https://youtu.be/pB_p2IGbpzw?si=8vRIXm_VlrZRxH6P)

## Author
Van Chien - [GitHub Profile](https://github.com/VanChien411)

Email: phuthienchien3@gmail.com 
Phone: 0328089720

