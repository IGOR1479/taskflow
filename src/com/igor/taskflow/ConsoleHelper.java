package com.igor.taskflow;

public class ConsoleHelper
{
    // Utility class; do not create objects
    private ConsoleHelper()
    {
    }

    public static void showMenu()
    {
        System.out.println("=== TaskFlow Advanced Console ===");
        System.out.println("1. Add a Task");
        System.out.println("2. Show All Tasks");
        System.out.println("3. Complete a Task");
        System.out.println("4. Delete a Task");
        System.out.println("5. Edit a Task");
        System.out.println("6. Filter Tasks");
        System.out.println("7. Sort Tasks");
        System.out.println("0. Exit");
        System.out.print("Choose: ");
    }

    public static void showEditMenu()
    {
        System.out.println("1. Edit the Title");
        System.out.println("2. Edit the Priority");
        System.out.println("3. Edit the Due Date");
        System.out.print("Choose: ");
    }

    public static void showFilterMenu()
    {
        System.out.println("1. Filter by Completed Status");
        System.out.println("2. Filter by Pending Status");
        System.out.println("3. Filter by High Priority");
        System.out.println("4. Filter by Medium Priority");
        System.out.println("5. Filter by Low Priority");
        System.out.print("Choose: ");
    }

    public static void showSortMenu()
    {
        System.out.println("1. Sort by Priority");
        System.out.println("2. Sort by Due Date");
        System.out.print("Choose: ");
    }
}
