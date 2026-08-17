package com.igor.taskflow;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task
{
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("uuuu/MM/dd");

    private String title;
    private boolean completed;
    private String priority;
    private String dueDate;

    public Task(String title, boolean completed, String priority, String dueDate)
    {
        this.title = title;
        this.completed = completed;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public String getTitle()
    {
        return title;
    }

    public boolean isCompleted()
    {
        return completed;
    }

    public String getPriority()
    {
        return priority;
    }

    public String getDueDate()
    {
        return dueDate;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setCompleted(boolean completed)
    {
        this.completed = completed;
    }

    public void setPriority(String priority)
    {
        this.priority = priority;
    }

    public void setDueDate(String dueDate)
    {
        this.dueDate = dueDate;
    }

    public void markAsCompleted()
    {
        this.completed = true;
    }

    public String getStatusText()
    {
        if (completed)
        {
            return "Completed";
        }

        return "Pending";
    }

    // A completed task is never overdue
    public boolean isOverdue()
    {
        LocalDate date = LocalDate.parse(dueDate, DATE_FORMATTER);
        return !completed && date.isBefore(LocalDate.now());
    }

    public void printTask()
    {
        String overdueText = isOverdue() ? " | [!] OVERDUE" : "";

        System.out.println(
                "Title: " + title
                + " | Status: " + getStatusText()
                + " | Priority: " + priority
                + " | Due Date: " + dueDate
                + overdueText
        );
    }
}
