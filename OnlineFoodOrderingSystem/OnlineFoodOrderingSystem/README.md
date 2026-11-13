# Online Food Ordering System (Java Console)

A Java 17+ console application using OOP and `ArrayList` for in-memory storage.
No database or external libraries are required.

## Run in VS Code
1. Install a JDK 17 or newer.
2. Install the **Extension Pack for Java** in VS Code.
3. Open this folder in VS Code.
4. Open `src/main/java/foodordering/Main.java` and click **Run**, or use the terminal:

```bash
javac -d out $(find src/main/java -name "*.java")
java -cp out foodordering.Main
```

On Windows PowerShell, compile with:
```powershell
Get-ChildItem -Recurse src/main/java -Filter *.java | ForEach-Object { $_.FullName } | Set-Content sources.txt
javac -d out @sources.txt
java -cp out foodordering.Main
```

## Demo accounts
- Admin: `admin` / `admin123`
- Customer: register a new account from the menu.

## Important
Data is stored in `ArrayList` objects while the program is running. All data is lost when
the application closes. Payment is simulated; no real money is charged. Delivery routing
is represented by courier assignment and status updates, not live GPS navigation.
