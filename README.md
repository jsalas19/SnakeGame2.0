# SnakeGame2.0

A simple Java Swing Snake game built in VS Code.

## Overview

This project contains a playable Snake game with a classic grid-based board, keyboard controls, food collection, collision detection, and a game-over reset flow.

## Project Structure

- `src/main/java/com/example/snake/App.java` - starts the Swing application
- `src/main/java/com/example/snake/SnakeGame.java` - main game logic and rendering
- `bin/` - compiled Java classes
- `.vscode/` - Java and debugging configuration for VS Code

## Requirements

- Java JDK 17 or newer
- VS Code with the Java extension pack installed

## Run the Game

From the project root, you can either:

1. Open the project in VS Code and run the `SnakeGame` configuration from Run and Debug.
2. Or double-click `run.bat` to compile and launch the game.

## Build Manually

```bash
javac -d bin src/main/java/com/example/snake/*.java
java -cp bin com.example.snake.App
```

## Controls

- Arrow keys: move the snake
- Press arrow keys after game over: restart the round

## Notes

This project was set up as a standard Java project layout to make the code easier to view, build, and debug in VS Code.
