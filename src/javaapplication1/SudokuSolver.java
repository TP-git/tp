package javaapplication1;

/**
     * This program is executed in the following way:
     *    java SudokuSolver <input-file>
     * For details of the input-file format,see the Grid.java class.
     *
     * @author  Patrick Chan
     * @version 1,12/31/05
     * @see Grid
     */
    import java.io.*;
    import java.util.*;

    public class SudokuSolver {
        public static void main(String[] args) throws Exception {
            // Open the file containing the givens
                try {
                    File file = new File("sudokus.txt");
                    FileReader rd = new FileReader("sudokus.txt");
//créer les grilles prend le plus de mémoire
//résoudre la grille prend le plus de temps
//create est appelée 2 fois, une pour la grille initiale et l'autre pour la grille résolue
//
                    // Process each grid in the file
                    while (true) {
                    Grid grid = Grid.create(rd);
                    if (grid == null) {
                        // No more grids
                        break;
                    }

                    // Find a solution
                    List<Grid> solutions = solve(grid);

                    printSolutions(grid,solutions);
                    }
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Exception de type ArrayIndexOutOfBounds");
                }
                catch (IndexOutOfBoundsException e) {
                    System.out.println("Exception de type IndexOutOfBounds");
                }

        }

        // Recursive routine that
        private static List<Grid> solve(Grid grid) {
            List<Grid> solutions = new ArrayList();
            solve(grid,solutions);
            return solutions;
        }

        private static void solve(Grid grid,List<Grid> solutions) {
            // Return if there is already more than two solution
            if (solutions.size() >= 2) {
                return;
            }

            // Find first empty cell
            int loc = grid.findEmptyCell();

            // If no empty cells are found,a solution is found
            if (loc < 0) {
                solutions.add(grid.clone());
                return;
            }

            // Try each of the 9 digits in this empty cell
            for (int n=1; n<10; n++  ){
                if (grid.set(loc,n)) {
                    // With this cell set,work on the next cell
                    solve(grid,solutions);

                    // Clear the cell so that it can be filled with another digit
                    grid.clear(loc);
                }
            }
        }

        private static void printSolutions(Grid grid,List<Grid> solutions) {
            // Print the grid with the givens
            System.out.println("Original");
            System.out.println(grid);

            // Print the solution
            if (solutions.isEmpty()) {
                System.out.println("Unsolveable");
            } else if (solutions.size() == 1) {
                System.out.println("Solved");
            } else {
                System.out.println("At least two solutions");
            }

            // Print the solution(s)
            for (int i=0; i<=solutions.size(); i++) {
                System.out.println(solutions.get(i));
            }
            System.out.println();
            System.out.println();
        }
    }
