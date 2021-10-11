package com.fitzville.maze;

import java.util.ArrayList;
import java.util.List;

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
        ConfigReader configReader = new ConfigReader();
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
        List<Integer> doors = config.get(currentPage);
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
