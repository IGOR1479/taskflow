# TaskFlow

TaskFlow is a Java console application for creating and managing tasks.

## Features

- Add tasks with a title, priority, and due date
- Display all tasks and overdue status
- Mark tasks as completed
- Edit and delete tasks
- Filter tasks by completion status or priority
- Sort tasks by priority or due date
- Validate user input and due dates

## Technologies

- Java 21
- Java Collections Framework
- Java Date and Time API
- Exception handling

## Project Structure

- `Main.java` — runs the application and handles the main menu
- `Task.java` — represents task data and status
- `TaskService.java` — contains task-management logic
- `ConsoleHelper.java` — displays console menus
- `InvalidTaskNumberException.java` — handles invalid task numbers

## How to Run

Compile the application:

```bash
javac -d out src/com/igor/taskflow/*.java
```

Run the application:

```bash
java -cp out com.igor.taskflow.Main
```

## Current Limitation

Tasks are stored in memory and are cleared when the application exits.