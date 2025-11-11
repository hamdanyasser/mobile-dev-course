# Android Dev Exam Reference Builder

**Repository:** hamdanyasser/mobile-dev-course
**Project:** HotelAppRef (Hotel Management Android App)
**Purpose:** Complete reference app + PDF documentation for Android midterm exam

## 🚀 Quick Start

### Prerequisites
- **Android Studio** (Latest version: Hedgehog/Iguana/Koala recommended)
- **JDK 17 or higher**
- **Minimum SDK:** 24 (Android 7.0)
- **Target SDK:** 34 (Android 14)

### Installation Steps

1. **Install Android Studio**
   - Download from: https://developer.android.com/studio
   - Follow the installation wizard
   - Install Android SDK 34 when prompted

2. **Clone and Open Project**
   ```bash
   git clone https://github.com/hamdanyasser/mobile-dev-course.git
   cd mobile-dev-course
   ```

3. **Open in Android Studio**
   - Launch Android Studio
   - Click "Open" → Navigate to `mobile-dev-course/HotelAppRef`
   - Click "Trust Project" when prompted
   - Wait for Gradle sync to complete (3-5 minutes first time)

4. **Run the App**
   - Click the green ▶ "Run" button (or Shift+F10)
   - Select an emulator or connected device
   - App will build, install, and launch

### One-Command Build (Terminal)
```bash
cd HotelAppRef
./gradlew assembleDebug
# APK location: app/build/outputs/apk/debug/app-debug.apk
```

---

## 📂 Project Structure: Where Everything Goes

```
HotelAppRef/
├── app/
│   ├── build.gradle.kts           ← App-level Gradle config (dependencies, SDK versions)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/hotelappref/
│   │   │   │   ├── MainActivity.java                    ← Main screen (hotel list)
│   │   │   │   ├── AddHotelActivity.java               ← Add hotel form
│   │   │   │   ├── HotelDetailsActivity.java           ← Hotel details screen
│   │   │   │   ├── models/
│   │   │   │   │   └── Hotel.java                       ← Data model (@Entity)
│   │   │   │   ├── adapters/
│   │   │   │   │   └── HotelAdapter.java                ← RecyclerView adapter
│   │   │   │   └── database/
│   │   │   │       ├── AppDatabase.java                 ← Room database
│   │   │   │       └── HotelDao.java                    ← Data access object
│   │   │   │
│   │   │   ├── res/
│   │   │   │   ├── layout/                               ← XML UI layouts
│   │   │   │   │   ├── activity_main.xml                ← Main screen layout
│   │   │   │   │   ├── activity_add_hotel.xml           ← Add form layout
│   │   │   │   │   ├── activity_hotel_details.xml       ← Details layout
│   │   │   │   │   └── hotel_item.xml                   ← RecyclerView item
│   │   │   │   │
│   │   │   │   ├── values/                               ← Resource values
│   │   │   │   │   ├── strings.xml                      ← Text strings
│   │   │   │   │   ├── colors.xml                       ← Color definitions
│   │   │   │   │   └── themes.xml                       ← App theme/styles
│   │   │   │   │
│   │   │   │   ├── mipmap-*/                             ← App icons (all densities)
│   │   │   │   │   └── ic_launcher.png
│   │   │   │   │
│   │   │   │   └── drawable/                             ← Images, shapes, icons
│   │   │   │
│   │   │   └── AndroidManifest.xml                       ← App configuration (activities, permissions)
│   │   │
│   │   ├── androidTest/                                  ← Instrumentation tests
│   │   └── test/                                         ← Unit tests
│   │
│   └── proguard-rules.pro                                ← Code obfuscation rules
│
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar                            ← Gradle executable
│       └── gradle-wrapper.properties                     ← Gradle version config
│
├── build.gradle.kts                                      ← Project-level Gradle config
├── settings.gradle.kts                                   ← Project settings
├── gradlew                                               ← Gradle wrapper script (Linux/Mac)
├── gradlew.bat                                           ← Gradle wrapper script (Windows)
└── local.properties                                      ← Local SDK path (auto-generated)
```

### Key File Purposes

| File | Purpose | When to Edit |
|------|---------|--------------|
| **MainActivity.java** | Main screen logic | Always (core app functionality) |
| **Hotel.java** | Data model/entity | When changing data structure |
| **HotelDao.java** | Database queries | When adding DB operations |
| **activity_main.xml** | Main screen UI | When changing layout |
| **strings.xml** | All text in app | When adding/changing text |
| **colors.xml** | Color palette | When changing app colors |
| **themes.xml** | App-wide styles | When changing theme/styling |
| **AndroidManifest.xml** | App config, permissions | When adding activities/permissions |
| **app/build.gradle.kts** | Dependencies, SDK versions | When adding libraries |

---

## 🏗️ App Architecture

### Components
1. **Activities** (3 total)
   - `MainActivity`: Displays hotel list using RecyclerView
   - `AddHotelActivity`: Form to add new hotels
   - `HotelDetailsActivity`: Shows full hotel information

2. **Data Layer**
   - `Hotel` model: @Entity for Room database
   - `HotelDao`: Database access methods (@Dao)
   - `AppDatabase`: Room database singleton

3. **UI Layer**
   - `HotelAdapter`: Binds hotel data to RecyclerView
   - ViewBinding: Type-safe view access (no findViewById!)

### Key Patterns Used
- **MVVM-lite**: Separation of data and UI
- **Repository Pattern**: Database operations in DAO
- **Singleton**: AppDatabase instance
- **ViewBinding**: Type-safe view references
- **RecyclerView + ViewHolder**: Efficient list display

---

## 🎓 Midterm Exam Quick Reference

### Essential Concepts (Know These!)

1. **Activities & Intents**
   - Start activity: `startActivity(new Intent(this, TargetActivity.class))`
   - Pass data: `intent.putExtra("key", value)`
   - Get data: `getIntent().getStringExtra("key")`

2. **RecyclerView**
   - Requires: Adapter, LayoutManager, ViewHolder
   - `adapter.notifyDataSetChanged()` to update UI

3. **Room Database**
   - `@Entity`: Marks class as table
   - `@Dao`: Interface with database methods
   - `@Database`: Database class with version

4. **Form Validation**
   - `TextUtils.isEmpty(string)`: Check if empty
   - `editText.setError("message")`: Show inline error

5. **ViewBinding**
   - Enable: `buildFeatures { viewBinding = true }`
   - Use: `binding = ActivityMainBinding.inflate(...)`
   - Access: `binding.textView.setText(...)`

### Common Exam Tasks

| Task | Solution File | Key Code |
|------|---------------|----------|
| Add new field to hotel | `Hotel.java` + layouts | Add field + getter/setter |
| Add menu action | `MainActivity.java` | Override `onCreateOptionsMenu()` |
| Add search functionality | `HotelDao.java` | Add `@Query` with LIKE clause |
| Handle configuration change | `AndroidManifest.xml` | Add `android:configChanges` |
| Add implicit intent | Activity | `Intent.createChooser(...)` |

---

## 📚 Documentation

- **LaTeX Reference:** `reference/android_exam_reference.pdf`
  - Part I: Android Studio 101
  - Part II: Core Android Recipes
  - Part III: Hotel App Walkthrough
  - Part IV: Labs Cheatsheet
  - Part V: Debugging & Gotchas

- **Build Script:** `scripts/build.sh`
  - Builds APK + compiles LaTeX PDF

---

## 🔧 Building Everything

### Build Android App
```bash
cd HotelAppRef
./gradlew assembleDebug
```

### Build LaTeX Reference
```bash
cd reference
xelatex -shell-escape android_exam_reference.tex
# Or fallback:
pdflatex android_exam_reference.tex
```

### Build Everything (One Command)
```bash
./scripts/build.sh
```

---

## 🐛 Troubleshooting

### "Gradle sync failed"
- **Solution:** File → Invalidate Caches → Restart

### "SDK not found"
- **Solution:** File → Project Structure → SDK Location → Set Android SDK path

### "ViewBinding not found"
- **Solution:** Build → Clean Project → Rebuild Project

### "Room schema export error"
- **Solution:** Add `exportSchema = false` in @Database annotation (already done)

### App crashes on rotation
- **Solution:** Save state in `onSaveInstanceState()` or use ViewModel

---

## 📝 Graded Lab Requirements Implemented

✅ **MainActivity.java**
- RecyclerView showing hotel list
- FAB opens AddHotelActivity
- Receives and displays new hotels

✅ **AddHotelActivity.java**
- Form with EditText fields
- Validation (name, phone, location required)
- Saves to Room database
- Returns result to MainActivity

✅ **Hotel Model**
- All required fields (name, phone, website, location, nearby, food)
- Room @Entity annotations
- Serializable for Intent passing

✅ **Persistence**
- Room database (SQLite)
- DAO with CRUD operations
- Survives app restarts

✅ **Resources**
- strings.xml for all text
- colors.xml for theme colors
- themes.xml for app styling

---

## 🎯 Exam Strategy

1. **Before the exam:**
   - Read `reference/android_exam_reference.pdf` thoroughly
   - Run HotelAppRef in Android Studio
   - Practice adding a new field (e.g., hotel rating)

2. **During the exam:**
   - Use PDF search (Ctrl+F) to find relevant code quickly
   - Copy-paste code snippets and adapt them
   - Test frequently (run app after each change)
   - Check Logcat for errors (View → Tool Windows → Logcat)

3. **Common mistakes to avoid:**
   - Forgetting to update AndroidManifest.xml when adding activities
   - Not calling `adapter.notifyDataSetChanged()` after data changes
   - Missing required permissions
   - Typos in resource IDs (R.id.textView vs R.id.TextView)

---

## 📞 Support

- **Issues:** Check `reference/android_exam_reference.pdf` Part V (Debugging & Gotchas)
- **Android Docs:** https://developer.android.com
- **Stack Overflow:** https://stackoverflow.com/questions/tagged/android

---

## 📄 License

Educational reference material for mobile development course.

---

**Good luck on your midterm! 🎓**
