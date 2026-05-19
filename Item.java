/*******************************************************************
 * CSCI 428/528             ASSIGNMENT 6             SPRING 2026   *
 *                                                                 *
 *             App Name: Assignment 6 Web Content App              *
 *                                                                 *
 *           Class Name: Item.java                                 *
 *       Developer Name: John Mark C Pascua                        *
 *             Due Date: 05/01/2026                                *
 *              Purpose: RSS feed items used in the application.   *
 *              Storing data such as the title, link, and date.    *
 *              All are used for the displayed content that allow  *
 *              the user to open the selected article in the       *
 *              browser.                                           *
 ******************************************************************/
package edu.niu.android.steamrsswebapp;

/******************************************************************
 *              Class Name: Item                                  *
 *                                                                *
 *              Purpose: Stores and manages the data of a single  *
 *              RSS feed article, including its title, link, and  *
 *              publication date.                                 *
 ******************************************************************/
public class Item
{
    //store RSS titles
    private String title;
    //store the article link
    private String link;
    //store the RSS article dates
    private String pubDate;

    /******************************************************************
     *          Method name: Item                                     *
     *                                                                *
     *          Purpose: Constructor method that initializes the      *
     *          Item object with its title, link, and publication     *
     *          date.                                                 *
     ******************************************************************/
    public Item(String newTitle, String newLink, String newPubDate)
    {
        //sets the title, link, and date
        setTitle(newTitle);
        setLink(newLink);
        setPubDate(newPubDate);
    }

    /******************************************************************
     *          Method name: setTitle                                 *
     *                                                                *
     *          Purpose: Updates the title of the RSS article.        *
     ******************************************************************/
    public void setTitle(String newTitle)
    {
        title = newTitle;
    }

    /******************************************************************
     *          Method name: setLink                                  *
     *                                                                *
     *          Purpose: Updates the link of the RSS article.         *
     ******************************************************************/
    public void setLink(String newLink)
    {
        link = newLink;
    }

    /******************************************************************
     *          Method name: setPubDate                               *
     *                                                                *
     *          Purpose: Updates the publication date of the RSS      *
     *          article.                                              *
     ******************************************************************/
    public void setPubDate(String newPubDate)
    {
        pubDate = newPubDate;
    }

    /******************************************************************
     *          Method name: getTitle                                 *
     *                                                                *
     *          Purpose: Retrieves the title of the RSS article.      *
     ******************************************************************/
    public String getTitle()
    {
        return title;
    }

    /******************************************************************
     *          Method name: getLink                                  *
     *                                                                *
     *          Purpose: Retrieves the link of the RSS article.       *
     ******************************************************************/
    public String getLink()
    {
        return link;
    }

    /******************************************************************
     *          Method name: getPubDate                               *
     *                                                                *
     *          Purpose: Retrieves the publication date of the RSS    *
     *          article.                                              *
     ******************************************************************/
    public String getPubDate()
    {
        return pubDate;
    }

    /******************************************************************
     *          Method name: toString                                 *
     *                                                                *
     *          Purpose: Returns the Item object data as a formatted  *
     *          string containing the title, link, and publication    *
     *          date.                                                 *
     ******************************************************************/
    @Override
    public String toString()
    {
        return title + "\n" + link + "\n" + pubDate;
    }
}
