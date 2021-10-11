package com.fitzville.maze;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConfigReader
{
    public List<List<Integer>> read()
    {
        InputStream is = getClass().getClassLoader().getResourceAsStream("pages.csv");
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String s;
        List<List<Integer>> config = new ArrayList<>();

        // bump up the list so that it's 1 based, because that's what the config is.
        config.add(0, new ArrayList<>());

        try
        {
            while ((s = br.readLine()) != null)
            {
                List<Integer> doorpages = new ArrayList<>();
                String[] doorpagestring = s.split(",");
                for (int index = 0; index < doorpagestring.length; index++)
                {
                    int i = Integer.parseInt(doorpagestring[index]);
                    if (index == 0)
                    {
                        assert(index == i);
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
