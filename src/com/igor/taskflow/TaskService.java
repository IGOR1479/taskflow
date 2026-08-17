package com.igor.taskflow;

import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class TaskService
{
    // Utility class; do not create objects
    private TaskService()
    {
    }

    // Reject invalid dates and require yyyy/MM/dd
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("uuuu/MM/dd")
                    .withResolverStyle(ResolverStyle.STRICT);

    public static void addTask(List<Task> tasks, Scanner input)
    {
        System.out.println("Enter the Title: ");
        String title = input.nextLine().trim();

        if (title.isBlank())
        {
            System.out.println("Title Cannot Be Empty");
            return;
        }

        System.out.println("Enter the Priority (High / Medium / Low): ");
        String priority = input.nextLine().trim();

        if (!priority.equalsIgnoreCase("High")
                && !priority.equalsIgnoreCase("Medium")
                && !priority.equalsIgnoreCase("Low"))
        {
            System.out.println("Invalid Priority");
            return;
        }

        // Save priority in one format
        priority = priority.substring(0, 1).toUpperCase()
                + priority.substring(1).toLowerCase();

        System.out.println("Enter the Due Date (yyyy/MM/dd): ");
        String dueDate = input.nextLine().trim();

        if (dueDate.isBlank())
        {
            System.out.println("Due Date Cannot Be Empty");
            return;
        }

        try
        {
            dueDate = validateAndNormalizeDueDate(dueDate);
        }
        catch (DateTimeParseException e)
        {
            System.out.println("Invalid Date Format. Please Use yyyy/MM/dd");
            return;
        }

        Task newTask = new Task(title, false, priority, dueDate);
        tasks.add(newTask);

        System.out.println("New Task Added");
    }

    public static void showAllTasks(List<Task> tasks)
    {
        if (tasks.isEmpty())
        {
            System.out.println("No Tasks Found");
            return;
        }

        for (int i = 0; i < tasks.size(); i++)
        {
            System.out.println((i + 1) + ".");
            tasks.get(i).printTask();
        }
    }

    public static void completeTask(List<Task> tasks, Scanner input)
    {
        if (tasks.isEmpty())
        {
            System.out.println("No Tasks Found");
            return;
        }

        showAllTasks(tasks);

        System.out.println("Enter the Task Number to Complete: ");

        int taskNumber;

        try
        {
            taskNumber = input.nextInt();
            input.nextLine();
        }
        catch (InputMismatchException e)
        {
            System.out.println("Please Enter a Number");
            input.nextLine();
            return;
        }

        try
        {
            validateTaskNumber(taskNumber, tasks);
        }
        catch (InvalidTaskNumberException e)
        {
            System.out.println(e.getMessage());
            return;
        }

        tasks.get(taskNumber - 1).markAsCompleted();
        System.out.println("Task Completed");
    }

    public static void deleteTask(List<Task> tasks, Scanner input)
    {
        if (tasks.isEmpty())
        {
            System.out.println("No Tasks Found");
            return;
        }

        showAllTasks(tasks);

        System.out.println("Enter the Task Number to Delete: ");

        int taskNumber;

        try
        {
            taskNumber = input.nextInt();
            input.nextLine();
        }
        catch (InputMismatchException e)
        {
            System.out.println("Please Enter a Number");
            input.nextLine();
            return;
        }

        try
        {
            validateTaskNumber(taskNumber, tasks);
        }
        catch (InvalidTaskNumberException e)
        {
            System.out.println(e.getMessage());
            return;
        }

        tasks.remove(taskNumber - 1);
        System.out.println("Task Deleted");
    }

    public static void editTask(List<Task> tasks, Scanner input)
    {
        if (tasks.isEmpty())
        {
            System.out.println("No Tasks Found");
            return;
        }

        showAllTasks(tasks);

        System.out.println("Enter the Task Number to Edit: ");
        int taskNumber;

        try
        {
            taskNumber = input.nextInt();
            input.nextLine();
        }
        catch (InputMismatchException e)
        {
            System.out.println("Please Enter a Number");
            input.nextLine();
            return;
        }

        try
        {
            validateTaskNumber(taskNumber, tasks);
        }
        catch (InvalidTaskNumberException e)
        {
            System.out.println(e.getMessage());
            return;
        }

        Task selectedTask = tasks.get(taskNumber - 1);

        ConsoleHelper.showEditMenu();

        int editChoice;

        try
        {
            editChoice = input.nextInt();
            input.nextLine();
        }
        catch (InputMismatchException e)
        {
            System.out.println("Please Enter a Number");
            input.nextLine();
            return;
        }

        switch (editChoice)
        {
            case 1:
                System.out.println("Enter New Title: ");
                String newTitle = input.nextLine().trim();
                if (newTitle.isBlank())
                {
                    System.out.println("Title Cannot Be Empty");
                    return;
                }

                selectedTask.setTitle(newTitle);

                System.out.println("Task Updated");
                break;

            case 2:
                System.out.println("Enter New Priority: ");
                String newPriority = input.nextLine().trim();
                if (!newPriority.equalsIgnoreCase("High")
                        && !newPriority.equalsIgnoreCase("Medium")
                        && !newPriority.equalsIgnoreCase("Low"))
                {
                    System.out.println("Invalid Priority");
                    return;
                }

                newPriority = newPriority.substring(0, 1).toUpperCase()
                        + newPriority.substring(1).toLowerCase();

                selectedTask.setPriority(newPriority);

                System.out.println("Task Updated");
                break;

            case 3:
                System.out.println("Enter New Due Date (yyyy/MM/dd): ");
                String newDueDate = input.nextLine().trim();

                if (newDueDate.isBlank())
                {
                    System.out.println("Due Date Cannot Be Empty");
                    return;
                }

                try
                {
                    newDueDate = validateAndNormalizeDueDate(newDueDate);
                }
                catch (DateTimeParseException e)
                {
                    System.out.println("Invalid Date Format. Please Use yyyy/MM/dd");
                    return;
                }

                selectedTask.setDueDate(newDueDate);

                System.out.println("Task Updated");
                break;

            default:
                System.out.println("Invalid Choice");
                break;
        }
    }

    public static void filterTasks(List<Task> tasks, Scanner input)
    {
        if (tasks.isEmpty())
        {
            System.out.println("No Tasks Found");
            return;
        }

        ConsoleHelper.showFilterMenu();

        int filterChoice;
        try
        {
            filterChoice = input.nextInt();
            input.nextLine();
        }
        catch (InputMismatchException e)
        {
            System.out.println("Please Enter a Number");
            input.nextLine();
            return;
        }

        if (filterChoice < 1 || filterChoice > 5)
        {
            System.out.println("Invalid Choice");
            return;
        }

        boolean found = false;

        for (int i = 0; i < tasks.size(); i++)
        {
            Task task = tasks.get(i);

            switch (filterChoice)
            {
                case 1:
                    if (task.isCompleted())
                    {
                        System.out.println((i + 1) + ".");
                        task.printTask();
                        found = true;
                    }
                    break;

                case 2:
                    if (!task.isCompleted())
                    {
                        System.out.println((i + 1) + ".");
                        task.printTask();
                        found = true;
                    }
                    break;

                case 3:
                    if (task.getPriority().equalsIgnoreCase("High"))
                    {
                        System.out.println((i + 1) + ".");
                        task.printTask();
                        found = true;
                    }
                    break;

                case 4:
                    if (task.getPriority().equalsIgnoreCase("Medium"))
                    {
                        System.out.println((i + 1) + ".");
                        task.printTask();
                        found = true;
                    }
                    break;

                case 5:
                    if (task.getPriority().equalsIgnoreCase("Low"))
                    {
                        System.out.println((i + 1) + ".");
                        task.printTask();
                        found = true;
                    }
                    break;
            }
        }

        if (!found)
        {
            System.out.println("No Matching Tasks Found");
        }
    }

    private static int getPriorityRank(Task task)
    {
        String priority = task.getPriority();

        if (priority.equalsIgnoreCase("High"))
        {
            return 1;
        }

        else if (priority.equalsIgnoreCase("Medium"))
        {
            return 2;
        }

        else if (priority.equalsIgnoreCase("Low"))
        {
            return 3;
        }

        else
        {
            return 4;
        }
    }

    public static void sortTasks(List<Task> tasks, Scanner input)
    {
        if (tasks.isEmpty())
        {
            System.out.println("No Tasks Found.");
            return;
        }

        ConsoleHelper.showSortMenu();

        int sortChoice;
        try
        {
            sortChoice = input.nextInt();
            input.nextLine();
        }
        catch (InputMismatchException e)
        {
            System.out.println("Please Enter a Number");
            input.nextLine();
            return;
        }

        switch (sortChoice)
        {
            case 1:
                tasks.sort(Comparator.comparingInt(TaskService::getPriorityRank));
                System.out.println("Tasks Sorted by Priority.");
                break;

            case 2:
                tasks.sort(Comparator.comparing(Task::getDueDate));
                System.out.println("Tasks Sorted by Due Date.");
                break;

            default:
                System.out.println("Invalid Choice.");
                return;
        }

        showAllTasks(tasks);
    }

    private static void validateTaskNumber(int taskNumber, List<Task> tasks)
            throws InvalidTaskNumberException
    {
        if (taskNumber < 1 || taskNumber > tasks.size())
        {
            throw new InvalidTaskNumberException("Invalid Task Number");
        }
    }

    private static String validateAndNormalizeDueDate(String dueDate)
            throws DateTimeParseException
    {
        LocalDate parsedDate = LocalDate.parse(dueDate, DATE_FORMATTER);
        return parsedDate.format(DATE_FORMATTER);
    }
}
