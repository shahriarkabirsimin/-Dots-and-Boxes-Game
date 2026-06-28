# Week 2 – Window Creation and Game Framework

## Objective

The objective of this week was to create the basic framework of the Dots and Boxes game using Java Swing.

---

## 1. Create the Main Application Window using Java Swing

The main application window was created using Java Swing by extending the JFrame class. The window title, size, close operation, and screen position were configured successfully.

### Main Class (DotAndBox.java)

The **DotAndBox.java** file contains the `main()` method, which serves as the entry point of the application. It starts the Dots and Boxes game by creating an instance of the `GameFrame` class.

![DotAndBox.java](SS-1.png)

---

## 2. Develop the Game Panel

A separate `GamePanel` class was created by extending `JPanel`. This panel is responsible for displaying the game board and handling user interactions.

### Game Frame (GameFrame.java)

The **GameFrame.java** class creates the main application window using Java Swing. It configures the window properties and loads the `GamePanel` to display the game interface.

![GameFrame.java](SS-2.png)

---

## 3. Set up the Event-Handling System

Mouse event handling was implemented using `MouseAdapter`. The application detects mouse clicks and prints the mouse coordinates in the output window.

### Game Panel (GamePanel.java)

The **GamePanel.java** class extends `JPanel` and handles the game panel, draws the game board, and processes mouse click events for user interaction.

![GamePanel.java](SS-3.png)

---

## 4. Display the Game Board Grid

A 4×4 grid of dots was drawn using the `Graphics` class and the `paintComponent()` method. This grid forms the initial game board for the Dots and Boxes game.

### Game Board Display

The application successfully displays the game board window with a properly rendered 4×4 grid of dots, confirming that the rendering process works correctly.

![Game Board](SS-4.png)

---

## 5. Verify Rendering and Application Lifecycle Management

The rendering process was verified by checking that the game window, panel, and grid were displayed correctly. The application opened, responded to user interactions, and closed successfully without any runtime errors.

---

## 6. Test Basic Window Functionality

The application window was tested by verifying the title, size, background color, mouse interaction, and close operation. All basic functions worked correctly as expected.

---

## Source Files

- DotAndBox.java
- GameFrame.java
- GamePanel.java

---

## Week 2 Summary

During this week, the basic framework of the Dots and Boxes game was successfully developed. The main application window, game panel, event-handling system, and game board grid were implemented and tested successfully. This completed the foundation of the project and prepared it for the next development phase.
