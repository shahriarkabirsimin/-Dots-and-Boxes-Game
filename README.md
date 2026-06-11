# -Dots-and-Boxes-Game

Description:

A console-based strategy game where two players take turns drawing lines between adjacent dots on a grid. When a player completes all four sides of a box, the box is claimed by that player and a point is awarded. The player with the highest score at the end of the game wins.

Tools / Resources

• Language: Java (JDK 8 or higher)
• Libraries: java.util.Scanner (User Input), java.util.ArrayList (Move Tracking)
• IDE: IntelliJ IDEA or Eclipse

Steps

• Display a grid of dots on the console screen.
• Allow players to draw horizontal or vertical lines between dots.
• Validate moves to prevent duplicate line entries.
• Detect completed boxes automatically.
• Update player scores and declare the winner after all boxes are completed.

Architecture

Game Board Manager → Move Validator → Line Drawing Engine → Box Detection Module → Score Calculator → Winner Announcement.

Outcome

An interactive strategy game that demonstrates array manipulation, game logic, score tracking, and object-oriented programming without requiring a database.

