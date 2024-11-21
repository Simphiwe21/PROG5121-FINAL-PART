/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.prog5121poepart3;


import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javax.swing.table.DefaultTableModel;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author RC_Student_lab
 */
public class TasksDataTest {
    
    private TasksData tasksData;
    
    
    @BeforeEach
    public void setUp() {
        tasksData = new TasksData();
        tasksData.setVisible(false);
    }
    
   @Test
    public void testFindLongestTask() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        // Setup: Add tasks to the model
        DefaultTableModel model = (DefaultTableModel) tasksData.AddData.getModel();
        model.addRow(new Object[]{0, "TA:0:DEV", "Task A", "Dev A", 3, "To Do"});
        model.addRow(new Object[]{1, "TA:1:DEV", "Task B", "Dev B", 5, "Doing"});
        model.addRow(new Object[]{2, "TA:2:DEV", "Task C", "Dev C", 2, "Done"});

        // Act: Trigger the method to find the longest task
       Method findLongestBtnActionPerformed = tasksData.getClass().getDeclaredMethod("FindLongestBtnActionPerformed", ActionEvent.class);
        findLongestBtnActionPerformed.setAccessible(true);
        findLongestBtnActionPerformed.invoke(tasksData, (Object) null);

        // Assert: Check that the longest task is correctly identified
        String expectedMessage = "Longest Task: Task B\nDeveloper: Dev B\nDuration: 5";
        JOptionPane pane = new JOptionPane();
        JDialog dialog = pane.createDialog("Longest Task");
        dialog.setVisible(true);
        
        // Get the message displayed in the dialog
        String actualMessage = pane.getMessage().toString();
        assertEquals(expectedMessage, actualMessage);
    }
    
    @Test
    public void testSearchTasks() {
        // Act: Search for "Task B"
        tasksData.searchTasks("Task B");

        // Assert: Check that the table only shows "Task B"
        DefaultTableModel model = (DefaultTableModel) tasksData.getAddData().getModel();
        int visibleRowCount = tasksData.getAddData().getRowCount();

        assertEquals(1, visibleRowCount, "Expected to find 1 task matching 'Task B'");

        // Verify that the visible task is indeed "Task B"
        String actualTaskName = (String) model.getValueAt(tasksData.getAddData().getRowSorter().convertRowIndexToModel(0), 2);
        assertEquals("Task B", actualTaskName, "Expected task name to be 'Task B'");
    }
    @Test
    public void testDeleteTask() {
        // Act: Delete the task at index 1 (Task B)
        tasksData.deleteTask(1);

        // Assert: Check that the number of tasks is now 2
        DefaultTableModel model = (DefaultTableModel) tasksData.getAddData().getModel();
        int remainingRowCount = model.getRowCount();

        assertEquals(2, remainingRowCount, "Expected to find 2 tasks after deletion");

        // Verify that the remaining tasks are "Task A" and "Task C"
        String remainingTask1 = (String) model.getValueAt(0, 2); // Task A
        String remainingTask2 = (String) model.getValueAt(1, 2); // Task C

        assertEquals("Task A", remainingTask1, "Expected first remaining task to be 'Task A'");
        assertEquals("Task C", remainingTask2, "Expected second remaining task to be 'Task C'");
    }
}

   

