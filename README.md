# Game of Life

This application is a multi-threaded implementation of the popular "Conway's Game of Life". It features an extensive graphical user interface built with Swing, allowing users to control the simulation speed and monitor the execution in real-time. The main distinguishing feature of this project is the parallelization of computing successive generations, where each working thread is assigned a proportional number of rows on the board.

## Architecture and Project Purpose

The project is designed with an object-oriented approach and focuses on parallelizing CPU-intensive operations:
- **Simulation Engine (`game`)**: Manages the board and enforces the rules of the game. Calculating subsequent generations is fully parallelized using a thread pool (`ExecutorService`) and a synchronization mechanism (`CountDownLatch`).
- **Graphical Interface (`gui`)**: Displays the board, maps thread colors to their respective rows, and allows controlling the game through buttons (Start/Stop, Reset) and a speed slider. A dedicated statistics panel presents real-time information on how the board is divided (which batch of rows is handled by which thread).
- **Configuration (`input`)**: The initial state (configuration) is loaded from external test files (scenarios) stored in a specific application directory.

## Used Technologies and Libraries

- **Language:** Java 21
- **Build and Dependency Management:** Maven
- **GUI Framework:** Java Swing
- **Unit Testing:** JUnit 5 (jupiter-api, jupiter-engine) and Mockito (core, junit-jupiter)
- **Multithreading:** Java's standard `java.util.concurrent` package (specifically `ExecutorService`, `CountDownLatch`)

## Module Structure

The project is divided into the following independent layers (packages):

* `game.board` – Interface and standard implementation of the board (`Board`, `StandardBoard`), responsible for keeping the cell states in memory (a logical 2D truth table).
* `game.core` – The central simulation manager (`GameOfLife`). It ties together the rules, neighborhoods, and delegates the computation of the new generation to the thread pool.
* `game.neighbors` – Implementation for counting living neighbor cells (`NeighborCounter`). The application supports a torus topology (`TorusNeighborCounter`), where the edges wrap around, ensuring no border cutoffs.
* `game.rules` – Defines when cells live or die based on a contract (e.g., Conway's rules in `ConwayRules`).
* `game.task` – `Runnable` entities (like `GenerationTask`) used by the engine for the thread pool. They represent a chunk of work that performs partial calculations only for a designated sub-section of the entire canvas.
* `gui` – The visual component of the software based on an interconnected pattern. It includes the main window (`GameWindow`), rendering panels (`GameBoardPanel`, `ThreadStatsPanel`, `ControlPanel`), and an action manager class (`GameGuiController`).
* `input` – Tools for reading `.txt` configuration files (`TxtFileGameConfigParser`) to ultimately create the target configuration model `GameConfig`.

## Build and Run Instructions

The application uses Maven to manage the software build lifecycle.

### Building

From the main project directory, run the following command in the terminal to clean the `target` folder and compile the code:

```bash
mvn clean package
```

### Running the Application

The `Main` class requires two configuration arguments:
1. The name of the scenario file (e.g., `glider.txt`). The application looks for these by default in `src/main/resources/scenarios/`.
2. The maximum target number of calculation generating threads.

```bash
# A standard built project can be run by pointing the Java ClassPath to target/classes:
java -cp target/classes Main <scenario_file_name> <number_of_threads>

# Example usage - running the glider scenario using 4 threads:
java -cp target/classes Main glider.txt 4
```
