# 🎁 Secret Draw (Console Application)

A **Java console application** that organizes a **Secret Draw** event.
The program allows users to create a draw, register participants, generate random assignments, and display the final results.

This project was designed as a **single-class Java application**, focusing on:

* Input validation
* Random assignment logic
* Clean control flow
* Simple but robust program structure

---

# 📌 Features

The application supports the following operations:

1. **Create Draw**

   * Register a new draw with:

     * Name
     * Description
     * Suggested budget
     * Event date

2. **Register Participants**

   * Add participants to the draw
   * Prevent duplicate names
   * Validate inputs

3. **List Participants**

   * Show all registered participants

4. **Run Draw**

   * Randomly assign each participant another participant
   * Ensures **no participant is assigned to themselves**

5. **Draw Summary**

   * Displays draw information
   * Shows final assignments (participant → secret friend)

---

# 🧠 Core Logic

The assignment algorithm ensures:

✔ Every participant receives **exactly one person**
✔ No participant receives **themselves**
✔ Assignments are **random**
✔ No duplicate assignments occur

The program uses the **Fisher-Yates Shuffle algorithm** with Java's `Random` class.

Example internal assignment:

```
Participants:
Ana
Luis
Carlos
Maria

Assignments:
Ana -> Carlos
Luis -> Maria
Carlos -> Luis
Maria -> Ana
```

---

# ⚙️ Technologies Used

* **Java**
* **java.util.Random**
* **ArrayList**
* **LocalDate**
* **Console I/O (Scanner)**

No external dependencies are required.

---

# 📂 Project Structure

```
SecretDraw
│
├── SecretDraw.java
└── README.md
```

The application is intentionally implemented in **a single class** to meet the assignment constraints.

---

# ▶️ How to Run

### 1️⃣ Compile the program

```bash
javac SecretDraw.java
```

### 2️⃣ Run the application

```bash
java SecretDraw
```

---

# 🖥️ Example Menu

```
**********************
*     SECRET DRAW    *
**********************

        MENU

1. Create Draw
2. Register Participants
3. List Participants
4. Run Draw
5. Draw summary
0. Exit
```

---

# 🔐 Input Validation

The program includes validation for:

* Empty inputs
* Invalid numbers
* Invalid dates
* Duplicate participant names
* Invalid menu options
* Running the draw without enough participants

---

# 👨‍💻 Author

**Juan Camilo Jojoa**

Estudiante de Diseño de Medios Interactivos