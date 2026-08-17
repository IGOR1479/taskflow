package com.igor.taskflow;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static com.igor.taskflow.ConsoleHelper.showMenu;

public class Main
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();
        int choice = -1;

        // Show the menu until the user exits
        do
        {
            showMenu();

            try
            {
                choice = input.nextInt();
                input.nextLine(); // Clear the newline
            }
            catch (InputMismatchException e)
            {
                System.out.println("Please Enter a Number");
                input.nextLine(); // Clear invalid input
                System.out.println();
                continue;
            }

            switch (choice)
            {
                case 0:
                    System.out.println("Goodbye");
                    break;

                case 1:
                    TaskService.addTask(tasks, input);
                    break;

                case 2:
                    TaskService.showAllTasks(tasks);
                    break;

                case 3:
                    TaskService.completeTask(tasks, input);
                    break;

                case 4:
                    TaskService.deleteTask(tasks, input);
                    break;

                case 5:
                    TaskService.editTask(tasks, input);
                    break;

                case 6:
                    TaskService.filterTasks(tasks, input);
                    break;

                case 7:
                    TaskService.sortTasks(tasks, input);
                    break;

                default:
                    System.out.println("Invalid Choice");
                    break;
            }

            System.out.println();

        } while (choice != 0);

        input.close();
    }
}
