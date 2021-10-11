package com.fitzville.maze;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Read the CSV config file.
 * Each line represents a room.  The first number on the line is the room number.  The remaining numbers on that line
 * represent the destination room numbers from walking through a door.
 *
 * The List that is returned is indexed to match the room numbers.  I inserted a dummy empty list at index 0.
 */
public class ConfigReader
{
    public List<List<Integer>> read()
    {
        final var config = new ArrayList<List<Integer>>();

        try(final var is = getClass().getClassLoader().getResourceAsStream("pages.csv");
            final var isr = new InputStreamReader(is);
            final var br = new BufferedReader(isr);)
        {
            String line;

            // bump up the list so that it's 1 based.  This is a dummy record at index 0.
            config.add(0, new ArrayList<>());

            while ((line = br.readLine()) != null)
            {
                final var doorpages = new ArrayList<Integer>();
                final var doorpagestring = line.split(",");
                for (int index = 0; index < doorpagestring.length; index++)
                {
                    final var i = Integer.parseInt(doorpagestring[index]);
                    if (index == 0)
                    {
                        assert (index == i);
                        // ignore... the index number will be handled by the arraylist
                    }
                    else
                    {
                        doorpages.add(i);
                    }
                }

                config.add(doorpages);
            }
        }
        catch(IOException e)
        {
            throw new RuntimeException("Unable to read config file");
        }

        return config;
    }
}
