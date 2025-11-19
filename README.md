
# 🚀 Getting Started with OversleepGuard in Android Studio

This guide provides instructions for quickly setting up and running the **OversleepGuard** project using Android Studio.

### 1. **Clone the Repository** 📥

First, clone the project from the Git repository to your local machine.

```bash
git clone [https://github.com/okitamisan01/OversleepGuard.git](https://github.com/okitamisan01/OversleepGuard.git)
````

### 2\. **Open the Project in Android Studio** 📂

1.  Launch **Android Studio**.
2.  From the welcome screen, select **"Open"** or **"Open an Existing Project."**
3.  Navigate to and select the root directory of the cloned project (the `OversleepGuard/` folder). Click **"OK."**
    * **Crucial Note:** Ensure you select the folder that contains the configuration files like `build.gradle.kts` and `settings.gradle.kts`.

### 3\. **Gradle Synchronization (Sync)** 🔄

Upon opening the project, Android Studio will automatically attempt a **Gradle sync** to resolve dependencies and apply build settings.

* Wait until you see the message **"Gradle sync finished."**
* If the sync fails or does not start, manually trigger it by clicking the **"Sync Project with Gradle Files"** button (often shaped like an elephant) in the toolbar.

### 4\. **Run the Application** ▶️

Once the Gradle sync is successful, you are ready to build and launch the app.

1.  **Select a Target Device:** Use the dropdown menu in the toolbar to select an **Emulator** or a **USB-connected Physical Device**.
    * If you do not have an emulator set up, use the **Device Manager** to create a new Virtual Device.
2.  **Click the Run Button:** Click the **Run 'app'** button (the green play icon ▶️) in the toolbar.
3.  Android Studio will build the project, install the app on the selected device, and launch it.
