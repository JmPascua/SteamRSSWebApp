/*******************************************************************
 * CSCI 428/528             ASSIGNMENT 6             SPRING 2026   *
 *                                                                 *
 *             App Name: Assignment 6 Web Content App              *
 *                                                                 *
 *           Class Name: MainActivity.java                         *
 *       Developer Name: John Mark C Pascua                        *
 *             Due Date: 05/01/2026                                *
 *              Purpose: Write an app similar to Web Content App   *
 *              Choosing two different RSS feeds, one that defaults*
 *              as many titles as possible, that also allows a     *
 *              filter for a user to enter the amount of days a    *
 *              an article was posted. While another app uses      *
 *              another RSS feed that adds a front activity        *
 *              that allows the user to specify a search term and  *
 *              browse the various articles.                       *
 ******************************************************************/
package edu.niu.android.steamrsswebapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/******************************************************************
 *              Class Name: MainActivity                          *
 *                                                                *
 *              Purpose: Main activity responsible for loading    *
 *              the Steam RSS feed, displays article titles,      *
 *              allows the user to filter articles based on       *
 *              the amount of days entered, and opening selected  *
 *              articles in the browser.                          *
 ******************************************************************/
public class MainActivity extends AppCompatActivity
{
    //store the Steam RSS feed URL
    private static final String STEAM_RSS_URL =
            "https://store.steampowered.com/feeds/news.xml";

    //store the date formats for parsing
    private static final String[] DATE_FORMATS =
            {
                    "EEE, dd MMM yyyy HH:mm:ss z",
                    "EEE, dd MMM yyyy HH:mm:ss Z",
                    "dd MMM yyyy HH:mm:ss z",
                    "yyyy-MM-dd'T'HH:mm:ssz"
            };

    private ListView listView; //display the RSS titles
    private EditText daysInput; //field for user input
    private Button filterButton; //button to filter articles
    private TextView resultCount; //display the filtered results
    private ArrayList<Item> allItems; //store all RSS titles

    /******************************************************************
     *          Method name: onCreate                                 *
     *                                                                *
     *          Purpose: Connect the UI components to their           *
     *          variables, begin parsing the RSS feed,                *
     *          and assign functions to the filter button.            *
     ******************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //call parent method
        super.onCreate(savedInstanceState);
        //set the layout
        setContentView(R.layout.activity_main);

        //UI components to variables
        listView = findViewById(R.id.list_view);
        daysInput = findViewById(R.id.days_input);
        filterButton = findViewById(R.id.filter_button);
        resultCount = findViewById(R.id.result_count);

        //create parse task object and execute it
        ParseTask task = new ParseTask(this);
        //start the task
        task.execute(STEAM_RSS_URL);

        //assign the button functionality
        filterButton.setOnClickListener(v -> applyFilter());
    }

    /******************************************************************
     *          Method name: displayList                              *
     *                                                                *
     *          Purpose: Receives the parsed RSS feed items and       *
     *          stores them. Displays a toast message if no items.    *
     ******************************************************************/
    public void displayList(ArrayList<Item> items)
    {
        //check if null or empty
        if (items == null || items.isEmpty())
        {
            //toast error message
            Toast.makeText(this, "Sorry- no Steam news were found.",
                    Toast.LENGTH_LONG).show();
            return;
        }

        //store items
        allItems = items;
        //render the list
        renderList(allItems);
    }

    /******************************************************************
     *          Method name: applyFilter                              *
     *                                                                *
     *          Purpose: Filters the articles based on the amount     *
     *          of days entered by the user. Displays only            *
     *          articles posted after the calculated cutoff date.     *
     ******************************************************************/
    private void applyFilter()
    {
        //prevent filter if no items
        if (allItems == null)
        {
            return;
        }

        //retrieves input
        String input = daysInput.getText().toString().trim();

        //display all items even without input
        if (input.isEmpty())
        {
            renderList(allItems);
            return;
        }

        int days;
        try
        {
            //convert input to integer
            days = Integer.parseInt(input);
            //number must be positive
            if (days <= 0) throw new NumberFormatException("non-positive");
        }
        catch (NumberFormatException e)
        {
            //toast message if input is invalid
            Toast.makeText(this, "Please enter a valid number of days.",
                    Toast.LENGTH_LONG).show();
            return;
        }

        //create object for date
        Calendar cal = Calendar.getInstance();
        //subtract days from current date
        cal.add(Calendar.DAY_OF_YEAR, -days);
        //store the date
        Date cutoff = cal.getTime();

        //store filtered items
        ArrayList<Item> filtered = new ArrayList<>();
        //loop through items
        for (Item item : allItems)
        {
            //parse date from item
            Date pubDate = parsePubDate(item.getPubDate());
            //check if article was posted after cutoff date
            if (pubDate != null && pubDate.after(cutoff))
            {
                filtered.add(item);
            }
        }

        //toast message if none was found
        if (filtered.isEmpty())
        {
            Toast.makeText(this, "No articles were found in the last " + days + " day(s).",
                    Toast.LENGTH_LONG).show();
        }

        //render filter
        renderList(filtered);
    }

    /******************************************************************
     *          Method name: parsePubDate                             *
     *                                                                *
     *          Purpose: Converts the publication date string from    *
     *          the RSS feed into a Date object by trying multiple    *
     *          date format patterns.                                 *
     ******************************************************************/
    private Date parsePubDate(String raw)
    {
        //check if null or empty
        if (raw == null || raw.isEmpty())
        {
            return null;
        }

        //loop through all date formats
        for (String fmt : DATE_FORMATS)
        {
            try
            {
                //create format object
                SimpleDateFormat sdf = new SimpleDateFormat(fmt, Locale.ENGLISH);
                return sdf.parse(raw);
            }
            //keep trying if fails
            catch (ParseException ignored)
            {
            }
        }

        //return null if all fails
        return null;
    }

    /******************************************************************
     *          Method name: renderList                               *
     *                                                                *
     *          Purpose: Displays the RSS article titles inside the   *
     *          ListView and allows users to click an article title   *
     *          to open its link in a web browser.                    *
     ******************************************************************/
    private void renderList(ArrayList<Item> items)
    {
        //store article titles
        ArrayList<String> titles = new ArrayList<>();
        //loop through items and retrieve titles
        for (Item item : items)
        {
            titles.add(item.getTitle());
        }

        //create adapter and set it
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                titles);

        //assigns adapter
        listView.setAdapter(adapter);

        //update result count
        resultCount.setText(getString(R.string.results_format, items.size()));

        //allow users to click article
        listView.setOnItemClickListener((parent, view, position, id) ->
        {
            //retrieve selected item
            Item selected = items.get(position);
            //convert article into object
            Uri uri = Uri.parse(selected.getLink());
            //create intent to launch
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
            //open article in browser
            startActivity(browserIntent);
        });
    }
}
