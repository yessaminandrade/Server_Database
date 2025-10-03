package model;

public class Author {
    private String name;
    private String authorId;
    private String affiliation;
    private int hIndex;
    private int i10Index;   // <- i10-index correcto
    private int citedBy;

    public Author() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAuthorId() { return authorId; }
    public void setAuthorId(String authorId) { this.authorId = authorId; }

    public String getAffiliation() { return affiliation; }
    public void setAffiliation(String affiliation) { this.affiliation = affiliation; }

    public int getHIndex() { return hIndex; }
    public void setHIndex(int hIndex) { this.hIndex = hIndex; }

    public int getI10Index() { return i10Index; }      // <- getter que pide ConsoleView
    public void setI10Index(int i10Index) { this.i10Index = i10Index; }  // <- setter

    public int getCitedBy() { return citedBy; }
    public void setCitedBy(int citedBy) { this.citedBy = citedBy; }
}




