package model;

public class Paper {
    private String title;
    private int year;
    private int citedBy;
    private String link;

    public Paper() {}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public int getCitedBy() { return citedBy; }
    public void setCitedBy(int citedBy) { this.citedBy = citedBy; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }
}


