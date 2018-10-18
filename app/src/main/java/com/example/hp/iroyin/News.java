package com.example.hp.iroyin;

public class News {

    /** News Title*/
    private String nTitle;

    /**News Section */
    private String nSection;

    /**News Author*/
    private String nAuthor;

    /**Website URL of the News*/
    private String nWebUrl;

    /**Date of News updated*/
    private String nDate;


    /**Initialize param*/
    /*@param News Title
    /*@param News Section
    /*@param News Author
    /*@param News Url*/
    /*CONSTRUCTOR*/

    public News(String title, String section, String author, String webUrl, String date) {
        nTitle = title;
        nSection = section;
        nAuthor = author;
        nWebUrl = webUrl;
        nDate = date;
    }


    /*Get Title*/
    public String getTitle() {
        return nTitle;
    }

    /*Get Section*/
    public String getSection() {
        return nSection;
    }

    /*Get Author*/
    public String getAuthor(){
        return nAuthor;
    }

    /**Returns the website URL to News*/
    public String getWebUrl() {
        return nWebUrl;
    }

    /**Returns the Date to News*/
    public String getDate(){ return nDate; }
}
