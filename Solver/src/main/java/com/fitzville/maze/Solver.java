package com.fitzville.maze;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a brute force solver for the book "Maze: Solve the World's Most Challenging Puzzle"
 * I browsed through all of the pages and created a CSV input file.
 * Each row represents a room and all of the doors it leads to.
 *
 * I did a recursive depth-first search.  It prunes out repeated rooms in a trail of breadcrumbs, and it prunes longer
 * breadcrumb trails once it finds a solution.
 *
 * In a normal maze of doors, getting from the start to the end and back to the start would be symmetric,
 * but in this book, there is not always a door returning to the room from which you came.
 *
 * I broke it up into 2 calls to the recursive function to solve forwards and then backwards.
 *
 * At first I got no results forward.  I had to resort to this website to confirm my list of doors for each page:
 * http://www.intotheabyss.net/
 *
 * The problem was that on page 33, I did not record door 17... the door looks like a coffin and the numbers 717 look
 * like part of a candelabra.
 *
 * Book Info
 * Author : Christopher Manson
 * Publisher : Holt Paperbacks (November 15, 1985)
 * ISBN-10 : 0805010882
 * ISBN-13 : 978-0805010886
 *
 * Code Info
 * @author joefitz
 */
public class Solver
{
    private final List<List<Integer>> config;

    public static void main(String args[])
    {
        final var solver = new Solver();
        final var breadcrumbs = new ArrayList<Integer>();
        final var bestBreadcrumb = new ArrayList<Integer>();

        breadcrumbs.add(1);
        solver.solve(1, 45, breadcrumbs, bestBreadcrumb);

        System.out.println("best breadcrumbs from 1 to 45: " + bestBreadcrumb);

        breadcrumbs.clear();
        bestBreadcrumb.clear();
        breadcrumbs.add(45);
        solver.solve(45, 1, breadcrumbs, bestBreadcrumb);

        System.out.println("best breadcrumbs from 45 to 1: " + bestBreadcrumb);
    }

    public Solver()
    {
        final var configReader = new ConfigReader();
        config = configReader.read();
    }

    public void solve(int currentPage, int pageGoal, List<Integer> breadcrumbs, List<Integer> bestBreadcrumbs)
    {
        // is this already worse than the best breadcrumb?
        if (!bestBreadcrumbs.isEmpty() && bestBreadcrumbs.size() < breadcrumbs.size())
        {
            return;
        }

        // am I at the goal?
        if (currentPage == pageGoal)
        {
            // is this the shortest breadcrumb so far?
            if (bestBreadcrumbs.isEmpty() || breadcrumbs.size() < bestBreadcrumbs.size())
            {
                bestBreadcrumbs.clear();
                bestBreadcrumbs.addAll(breadcrumbs);
            }
        }

        // recurse into the next list of doors for this page
        final var doors = config.get(currentPage);
        for(Integer door : doors)
        {
            // only visit a door if we haven't been there in this breadcrumb
            if (!breadcrumbs.contains(door))
            {
                breadcrumbs.add(door);
                solve(door, pageGoal, breadcrumbs, bestBreadcrumbs);
                breadcrumbs.remove(breadcrumbs.size() - 1);
            }
        }
    }
}
