=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: arriella
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D-Arrays: I plan to store the board of the game as a 2-D of the discs.

  2. File I/O: To store the state of the game  in order to have both save
                and load functionality, as I plan to have both a save and load button.

  3. JUnit Testable Components: As this game is turn based, I will be able
                            to test edge cases of the game.

  4. Collections and Maps: As the game displays the possible moves each player
                            I store the possible moves for each player in a collection (TreeSet).
                            This set is updated after every turn as the possible moves change every turn.

===============================
=: File Structure Screenshot :=
===============================
- Include a screenshot of your project's file structure. This should include
  all of the files in your project, and the folders they are in. You can
  upload this screenshot in your homework submission to gradescope, named 
  "file_structure.png".

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  Disc Class - the class' constructor creates a disc object for the board. It generally
               has no functions other that its constructor.

  Position Class - this class has two fields, x and y. The main things about this
                    class is that it implements comparable, and thus can be
                    stored in a Collection without problems. This class has
                    two getter functions (for the x and the y), a toString
                    method and the compareTo method. The purpose of this class
                    is to provide a way of storing the array positions of the
                    discs. It is also used to store a collection of the possible
                    moves (playable positions) of the game every turn.

 Othello Class - this class provides all the background structure of the game.
                   It has a 2D array of Discs to represent the board.
                   It keeps track of the possible moves of the game. It updates
                   the game state after every play, and handles the flipping mechanism.
                   It also keeps track of the current player and the number of discs
                   each players pieces on the board.

 Board Class - this class controls the GUI. Its constructor initialises a new Othello game.
                It updates the appearance of the board accordingly after every play based on
                the state of the Othello board.

RunOthello Class - Designs the GUI and runs the program.
                    It handles the interacts with the user by calling
                    necessary functions of the other classes.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
  - I had a challenge understanding the flow and structure of the code, in terms
    of what should happen after what. I was also stuck on which classes would be the
     best to implement certain aspects of the game.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
    I think its decent enough. If I had the chance to change something,
    I would perhaps break certain functions, such as my flipAll function in the
    Othello class, into smaller helper functions, instead of having the whole
    implementation in one function.


========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.
 None.