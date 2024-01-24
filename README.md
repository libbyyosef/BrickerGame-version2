# BrickerGame-version2

Within this repository lies the codebase of a Java-powered brick-breaker game, unveiling a world of diverse game elements, intricate collisions, and dynamic behaviors to craft an immersive gaming odyssey. BrickerGame-version2 elevates the gaming realm with novel features: introducing pucks and second paddles, advanced collision strategies, a strategy factory for dynamic behavior creation, and refined controls. This iteration promises a more enriched and engaging gaming experience, setting itself apart from the earlier BrickerGame-version1.

**Game Objects**

**Ball**

The Ball class represents the game ball. It inherits from GameObject and includes features such as collision handling, random initial direction, and a counter to track collisions with the camera.

**Brick**

The Brick class represents the bricks in the game. It inherits from GameObject and includes collision handling strategies, such as regular collisions and additional behaviors like camera movement.

**Paddle**

The Paddle class represents the player's paddle. It inherits from GameObject and includes methods for updating the paddle's position based on user input.

**BrickerGameManager**

The BrickerGameManager class serves as the main game manager. It initializes the game, creates game objects, and manages game logic, including win/loss conditions, collisions, and user input.

**GraphicLifeCounter**

The GraphicLifeCounter class represents the graphic life counter in the game, displayed as hearts. It extends the GameObject class and includes methods to update the graphic lives representation on the board based on the number of lives left.

**Heart**

The Heart class represents a heart object. It extends the GameObject class and handles the falling movement of the heart. It also specifies collision conditions with the main paddle.

**NumericLifeCounter**

The NumericLifeCounter class represents the textual representation of the number of lives left. It extends the GameObject class and updates the color and value of the textual representation based on the number of lives left.

**Puck**

The Puck class represents a puck object, extending the Ball class. It includes a sound for collisions and allows resizing.

**SecondPaddle**

The SecondPaddle class represents a second paddle instance. It extends the Paddle class and includes attributes and methods to track collisions and removal conditions.

**CameraMove**

The CameraMove class represents the camera movement behavior. It extends the CollisionStrategy class and handles camera movement based on ball collisions.

**CollisionStrategy**

The CollisionStrategy class is a base class for collision strategies. It includes methods to handle collisions and remove objects from the game.

**DoubledBehaviour**

The DoubledBehaviour class represents the doubled behavior for bricks. It extends the CollisionStrategy class and includes methods to randomize behaviors and handle strategy arrays.

**ExtraPaddle**

The ExtraPaddle class represents the extra paddle behavior for bricks. It extends the CollisionStrategy class and includes methods to handle collisions and create extra paddles.

**ExtraPucks**

The ExtraPucks class represents the extra pucks behavior for bricks. It extends the CollisionStrategy class and includes methods to handle collisions and create extra pucks.

**ReturnHeart**

The ReturnHeart class represents the return heart behavior for bricks. It extends the CollisionStrategy class and includes methods to handle collisions and create return hearts.

**StrategyFactory**

The StrategyFactory class is responsible for creating different collision strategies based on an input StrategyRand. It includes methods to build specific strategies for various behaviors.

**StrategyRand**

An enum representing all the types of bricks' strategies.

**Bricker Game**

This repository contains the source code for a simple brick-breaker game implemented in Java. The game includes various game objects, collisions, and behaviors to create an interactive gaming experience.


**Controls**

Move paddle left: Left Arrow Key
Move paddle right: Right Arrow Key
Restart the game: 'W' key (only after winning or when prompted)

**Dependencies**

The game uses the danogl library for game development, including features like collision detection, rendering, and user input.

Notes
The game includes various behaviors for bricks, such as camera movement, extra paddles, and more.
The user can restart the game by pressing the 'W' key after winning or when prompted.
The game has a simple win/loss mechanism based on the number of lives and remaining bricks.

Feel free to explore and modify the code to enhance or customize the game further!


Powered by DanoGameLab, a library crafted by Dan Nirel.
