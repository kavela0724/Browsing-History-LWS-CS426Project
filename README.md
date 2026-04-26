# Browsing-History-LWS-CS426Project
This is a class project that deals with Browsing History with the use of FTK imager, SQLite and Java Code 
ava -version


Download SQLite JDBC Driver (make sure it is NOT the javadoc file).

---

## Step 1: Get Chrome History File

1. Open File Explorer  
2. Go to:

%LOCALAPPDATA%\Google\Chrome\User Data\Default\

3. Find the file named:

History

4. Close Google Chrome  
5. Copy the `History` file  
6. Paste it into your project folder  

---

## Step 2: Project Structure

Your folder should look like:

CS426_Project/
├── Main.java
├── History
└── sqlite-jdbc-3.xx.x.jar


---

## Step 3: Compile the Program

Open terminal in your project folder and run:

javac -cp ".;sqlite-jdbc-3.xx.x.jar" Main.java


---

## Step 4: Run the Program


java -cp ".;sqlite-jdbc-3.xx.x.jar" Main


---

## Step 5: Output

The program will display:

- Browser Analysis:
  - Top visited sites
  - Visit counts
  - Most recent activity

- Behavioral Profile:
  - Classified as High Risk, Social, Research, etc.
  - Generated using keyword detection and browsing patterns

---

## Step 6: Testing Different Behaviors

To test behavior detection:

Search in Chrome:
- "how to hack wifi" → High Risk  
- "youtube" → Social  
- "how to code java" → Research  

Then:
1. Close Chrome  
2. Re-copy the History file  
3. Run the program again  

---

## How It Works

- Connects to Chrome’s SQLite database  
- Extracts browsing data  
- Converts timestamps to readable format  
- Detects keywords and patterns  
- Generates a behavioral profile using LWS  

---

## Notes

- Chrome must be closed before copying the History file  
- Ensure the correct `.jar` file is used  
- Commands must include the JDBC driver in the classpath  

---

## Example Output


========== BROWSER ANALYSIS ==========
Top Sites: [Google, YouTube, Gmail...]
Total Typed URLs: 4
Average Visits: 12.3

========== BEHAVIOR PROFILE ==========
HIGH RISK: User shows repeated interaction with suspicious content...
