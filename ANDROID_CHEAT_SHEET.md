# ANDROID EXAM CHEAT SHEET - COPY & PASTE READY

## 🎯 FILE LOCATIONS (Where to put code)

| What | Where |
|------|-------|
| Activity.java | `app/src/main/java/com/example/yourapp/` |
| Layout XML | `app/src/main/res/layout/` |
| strings.xml | `app/src/main/res/values/strings.xml` |
| colors.xml | `app/src/main/res/values/colors.xml` |
| AndroidManifest.xml | `app/src/main/AndroidManifest.xml` |
| build.gradle.kts | `app/build.gradle.kts` |

---

## 📱 PATTERN 1: Create Activity (COPY THIS!)

### Step 1: Create Java Class
**File:** `app/src/main/java/com/example/yourapp/MainActivity.java`
```java
package com.example.yourapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
```

### Step 2: Create Layout
**File:** `app/src/main/res/layout/activity_main.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World" />

</LinearLayout>
```

### Step 3: Register in Manifest
**File:** `app/src/main/AndroidManifest.xml`
```xml
<activity
    android:name=".MainActivity"
    android:exported="true">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity>
```

---

## 🔄 PATTERN 2: Navigate Between Activities

### Start Another Activity
```java
Intent intent = new Intent(MainActivity.this, SecondActivity.class);
startActivity(intent);
```

### Pass Data
```java
// Sender
Intent intent = new Intent(this, SecondActivity.class);
intent.putExtra("name", "John");
intent.putExtra("age", 25);
startActivity(intent);

// Receiver (in SecondActivity.onCreate)
String name = getIntent().getStringExtra("name");
int age = getIntent().getIntExtra("age", 0);
```

### Get Result Back (Modern Way)
```java
// In MainActivity
ActivityResultLauncher<Intent> launcher = registerForActivityResult(
    new ActivityResultContracts.StartActivityForResult(),
    result -> {
        if (result.getResultCode() == RESULT_OK) {
            // Success!
        }
    }
);

// Launch it
launcher.launch(new Intent(this, AddActivity.class));

// In AddActivity - return result
setResult(RESULT_OK);
finish();
```

---

## 📝 PATTERN 3: Form Validation

```java
String name = editName.getText().toString().trim();

if (TextUtils.isEmpty(name)) {
    editName.setError("Name is required");
    editName.requestFocus();
    return;  // Stop here
}

// Validate email
if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
    editEmail.setError("Invalid email");
    return;
}

// Validate phone
if (!Patterns.PHONE.matcher(phone).matches()) {
    editPhone.setError("Invalid phone");
    return;
}
```

---

## 📋 PATTERN 4: RecyclerView (List)

### Step 1: Add to Layout
```xml
<androidx.recyclerview.widget.RecyclerView
    android:id="@+id/recyclerView"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

### Step 2: Create Item Layout
**File:** `app/src/main/res/layout/item_layout.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:padding="16dp">

    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textSize="18sp" />
</LinearLayout>
```

### Step 3: Setup in Activity
```java
RecyclerView recyclerView = findViewById(R.id.recyclerView);
recyclerView.setLayoutManager(new LinearLayoutManager(this));

List<String> items = new ArrayList<>();
items.add("Item 1");
items.add("Item 2");

MyAdapter adapter = new MyAdapter(this, items);
recyclerView.setAdapter(adapter);

// Update list
items.add("New Item");
adapter.notifyDataSetChanged();
```

### Step 4: Create Adapter
```java
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private Context context;
    private List<String> items;

    public MyAdapter(Context context, List<String> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String item = items.get(position);
        holder.textView.setText(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
```

---

## 🗄️ PATTERN 5: Room Database

### Step 1: Add Dependencies
**File:** `app/build.gradle.kts`
```kotlin
dependencies {
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
}
```

### Step 2: Create Entity (Model)
```java
@Entity(tableName = "items")
public class Item {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;
    private String description;

    // Constructor
    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
```

### Step 3: Create DAO
```java
@Dao
public interface ItemDao {
    @Insert
    long insert(Item item);

    @Update
    void update(Item item);

    @Delete
    void delete(Item item);

    @Query("SELECT * FROM items")
    List<Item> getAll();

    @Query("SELECT * FROM items WHERE id = :id")
    Item getById(long id);
}
```

### Step 4: Create Database
```java
@Database(entities = {Item.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ItemDao itemDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                context.getApplicationContext(),
                AppDatabase.class,
                "my_database"
            ).allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}
```

### Step 5: Use Database
```java
// In Activity
AppDatabase db = AppDatabase.getInstance(this);
ItemDao dao = db.itemDao();

// Insert
Item item = new Item("Name", "Description");
long id = dao.insert(item);

// Get all
List<Item> items = dao.getAll();

// Update
item.setName("New Name");
dao.update(item);

// Delete
dao.delete(item);
```

---

## 🎨 PATTERN 6: Spinner (Dropdown)

### In Layout
```xml
<Spinner
    android:id="@+id/spinner"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```

### In strings.xml
```xml
<string-array name="options">
    <item>Option 1</item>
    <item>Option 2</item>
    <item>Option 3</item>
</string-array>
```

### In Activity
```java
Spinner spinner = findViewById(R.id.spinner);
ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
    this,
    R.array.options,
    android.R.layout.simple_spinner_item
);
adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
spinner.setAdapter(adapter);

// Get selected value
String selected = spinner.getSelectedItem().toString();
```

---

## 📞 PATTERN 7: Implicit Intents

### Call Phone
```java
Intent callIntent = new Intent(Intent.ACTION_CALL);
callIntent.setData(Uri.parse("tel:+9611234567"));
startActivity(callIntent);

// Need permission in Manifest:
// <uses-permission android:name="android.permission.CALL_PHONE" />
```

### Open URL
```java
Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
startActivity(browserIntent);
```

### Share Text
```java
Intent shareIntent = new Intent(Intent.ACTION_SEND);
shareIntent.setType("text/plain");
shareIntent.putExtra(Intent.EXTRA_TEXT, "Check this out!");
startActivity(Intent.createChooser(shareIntent, "Share via"));
```

### Send Email
```java
Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
emailIntent.setData(Uri.parse("mailto:test@example.com"));
emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
emailIntent.putExtra(Intent.EXTRA_TEXT, "Email body");
startActivity(emailIntent);
```

### WhatsApp Share
```java
String phoneNumber = "+96176867167";
String message = "Check this out!";
Intent intent = new Intent(Intent.ACTION_VIEW);
intent.setData(Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber +
    "&text=" + Uri.encode(message)));
startActivity(intent);
```

---

## 🍞 PATTERN 8: Toast Messages

```java
// Short toast (2 seconds)
Toast.makeText(this, "Message here", Toast.LENGTH_SHORT).show();

// Long toast (3.5 seconds)
Toast.makeText(this, "Message here", Toast.LENGTH_LONG).show();
```

---

## 🔔 PATTERN 9: AlertDialog

```java
new AlertDialog.Builder(this)
    .setTitle("Delete Item")
    .setMessage("Are you sure?")
    .setPositiveButton("Yes", (dialog, which) -> {
        // User clicked Yes
    })
    .setNegativeButton("No", null)
    .show();
```

---

## 🔐 PATTERN 10: Permissions (Runtime)

### Request Permission
```java
if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
        != PackageManager.PERMISSION_GRANTED) {
    ActivityCompat.requestPermissions(this,
        new String[]{Manifest.permission.CALL_PHONE}, 1);
} else {
    // Permission already granted
    makeCall();
}

@Override
public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == 1) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            makeCall();
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }
}
```

---

## 📱 COMMON MANIFEST ENTRIES

```xml
<!-- Permissions -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.CALL_PHONE" />
<uses-permission android:name="android.permission.CAMERA" />

<!-- Activities -->
<activity
    android:name=".MainActivity"
    android:exported="true">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity>

<activity
    android:name=".SecondActivity"
    android:exported="false"
    android:parentActivityName=".MainActivity" />
```

---

## 🎨 COMMON XML ATTRIBUTES

```xml
<!-- Size -->
android:layout_width="match_parent"   <!-- Full width -->
android:layout_width="wrap_content"   <!-- Fit content -->
android:layout_height="match_parent"
android:layout_height="wrap_content"

<!-- Text -->
android:text="Hello"
android:textSize="18sp"
android:textColor="@color/black"
android:textStyle="bold"
android:hint="Enter text here"

<!-- Padding/Margin -->
android:padding="16dp"
android:layout_margin="8dp"

<!-- Input Types -->
android:inputType="text"
android:inputType="textEmailAddress"
android:inputType="phone"
android:inputType="number"
```

---

## 🚨 EXAM SURVIVAL TIPS

### During Exam:
1. **Read question twice** - know what they're asking
2. **Use Ctrl+F in PDF** - search for keywords
3. **Copy from HotelAppRef** - working code examples
4. **Change package names** - match your project
5. **Test frequently** - run app after each change
6. **Check Logcat** - see errors in real-time

### If You Get Stuck:
- Search PDF for the pattern (e.g., "RecyclerView", "Intent", "validation")
- Open HotelAppRef and find similar code
- Copy the whole pattern, then modify names

### Common Mistakes:
- ❌ Forgetting to register activity in Manifest
- ❌ Wrong resource ID (R.id.textView not R.id.TextView)
- ❌ Not calling `notifyDataSetChanged()` after updating list
- ❌ Forgetting ViewBinding setup in build.gradle

---

## 🎯 QUICK REFERENCE

**Need to:**
- Display list? → RecyclerView (Pattern 4)
- Validate form? → TextUtils.isEmpty (Pattern 3)
- Save data? → Room Database (Pattern 5)
- Navigate? → Intent (Pattern 2)
- Dropdown? → Spinner (Pattern 6)
- Share/Call? → Implicit Intent (Pattern 7)

---

**REMEMBER: You can copy everything! Just adapt package names and variable names to match the exam question! 🚀**
