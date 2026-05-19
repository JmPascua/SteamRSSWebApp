/*******************************************************************
 * CSCI 428/528             ASSIGNMENT 6             SPRING 2026   *
 *                                                                 *
 *             App Name: Assignment 6 Web Content App              *
 *                                                                 *
 *           Class Name: SAXHandler.java                           *
 *       Developer Name: John Mark C Pascua                        *
 *             Due Date: 05/01/2026                                *
 *              Purpose: Handles the SAX parsing of the RSS feed.  *
 *              Extracting the article elements then storing them  *
 *              in appropriate objects for display and processing. *
 ******************************************************************/
package edu.niu.android.steamrsswebapp;

import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/******************************************************************
 *              Class Name: SAXHandler                            *
 *                                                                *
 *              Purpose: Handles the SAX parsing of the RSS feed  *
 *              XML data. Retrieves and stores article            *
 *              information including title, link, and            *
 *              publication date into Item objects.               *
 ******************************************************************/
public class SAXHandler extends DefaultHandler
{
    //check if element is valid
    private boolean validText;
    //store current element
    private String element = "";
    //store current RSS
    private Item currentItem;
    //store all parsed items
    private ArrayList<Item> items;

    /******************************************************************
     *          Method name: SAXHandler                               *
     *                                                                *
     *          Purpose: Constructor method that initializes the      *
     *          ArrayList used to store parsed RSS items.             *
     ******************************************************************/
    public SAXHandler()
    {
        items = new ArrayList<Item>();
    }

    /******************************************************************
     *          Method name: getItems                                 *
     *                                                                *
     *          Purpose: Returns the complete list of parsed RSS      *
     *          feed items.                                           *
     ******************************************************************/
    public ArrayList<Item> getItems()
    {
        return items;
    }

    /******************************************************************
     *          Method name: startElement                             *
     *                                                                *
     *          Purpose: Detects the start of an XML element and      *
     *          prepares for data parsing. Creates a new Item object  *
     *          when an item tag is encountered.                      *
     ******************************************************************/
    @Override
    public void startElement(String uri, String localName,
                             String startElement, Attributes attributes)
        throws SAXException
    {
        //allows text inside element
        validText = true;
        //store the current element
        element = startElement;

        //check if element is a valid item
        if (startElement.equals("item"))
        {
            //creates the item objects
            currentItem = new Item("", "","");
        }
    }

    /******************************************************************
     *          Method name: endElement                               *
     *                                                                *
     *          Purpose: Detects the end of an XML element and        *
     *          stores the completed Item object into the list when   *
     *          an item tag ends.                                     *
     ******************************************************************/
    @Override
    public void endElement(String uri, String localName, String endElement)
        throws SAXException
    {
        //prevent further processing after element ends
        validText = false;

        //check if element ended
        if (endElement.equals("item") && currentItem != null)
        {
            //add to list
            items.add(currentItem);
        }
    }

    /******************************************************************
     *          Method name: characters                               *
     *                                                                *
     *          Purpose: Retrieves and stores the text content inside *
     *          XML elements such as title, link, and publication     *
     *          date.                                                 *
     ******************************************************************/
    @Override
    public void characters(char[] ch, int start, int length)
        throws SAXException
    {
        //prevent processing if null or invalid text
        if (currentItem == null || !validText)
        {
            return;
        }

        //convert data into string
        String text = new String(ch, start, length);

        //check if current element is the title
        if (element.equals("title"))
        {
            //append title
            currentItem.setTitle(currentItem.getTitle() + text);
        }
        //check if element is the link
        else if (element.equals("link"))
        {
            //append link
            currentItem.setLink(currentItem.getLink() + text);
        }
        //check if element is the date
        else if (element.equals("pubDate"))
        {
            //append date
            currentItem.setPubDate(currentItem.getPubDate() + text);
        }
    }
}
