/*******************************************************************
 * CSCI 428/528             ASSIGNMENT 6             SPRING 2026   *
 *                                                                 *
 *             App Name: Assignment 6 Web Content App              *
 *                                                                 *
 *           Class Name: ParseTask.java                            *
 *       Developer Name: John Mark C Pascua                        *
 *             Due Date: 05/01/2026                                *
 *              Purpose: Handles the background retrieval and      *
 *              parsing of RSS feed data using AsyncTask. Uses     *
 *              SAX parsing to process XML data and returns the    *
 *              parsed RSS feed items to the MainActivity.         *
 ******************************************************************/
package edu.niu.android.steamrsswebapp;

import android.os.AsyncTask;
import android.util.Log;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/******************************************************************
 *              Class Name: ParseTask                             *
 *                                                                *
 *              Purpose: Handles the background retrieval and     *
 *              parsing of RSS feed data using AsyncTask. Uses    *
 *              SAX parsing to process XML data and returns the   *
 *              parsed RSS feed items to the MainActivity.        *
 ******************************************************************/
public class ParseTask extends AsyncTask<String, Void, ArrayList<Item>>
{
    //store the activity
    private MainActivity activity;

    /******************************************************************
     *          Method name: ParseTask                                *
     *                                                                *
     *          Purpose: Constructor method that stores the           *
     *          MainActivity reference for later use when returning   *
     *          parsed data.                                          *
     ******************************************************************/
    public ParseTask(MainActivity fromActivity)
    {
        //store reference
        activity = fromActivity;
    }

    /******************************************************************
     *          Method name: doInBackground                           *
     *                                                                *
     *          Purpose: Runs in the background to retrieve and       *
     *          parse the RSS feed XML data using a SAX parser.       *
     *          Returns the list of parsed RSS items.                 *
     ******************************************************************/
    @Override
    protected ArrayList<Item> doInBackground(String... urls)
    {
        try
        {
            //create SAX parser
            SAXParserFactory factory = SAXParserFactory.newInstance();
            //create parser object
            SAXParser saxParser = factory.newSAXParser();
            //create handler
            SAXHandler handler  = new SAXHandler();
            //parses the RSS feed using handler
            saxParser.parse(urls[0], handler);
            //return the RSS feed
            return handler.getItems();
        }
        catch (Exception e)
        {
            //logs errors for debugging
            Log.w("SteamNewsApp", "ParseTask error: " + e.toString());
            //return null if fails
            return null;
        }
    }

    /******************************************************************
     *          Method name: onPostExecute                            *
     *                                                                *
     *          Purpose: Receives the parsed RSS feed items after     *
     *          background execution is complete and sends the data   *
     *          to MainActivity for display.                          *
     ******************************************************************/
    @Override
    protected void onPostExecute(ArrayList<Item> returnedItems)
    {
        activity.displayList(returnedItems);
    }
}
