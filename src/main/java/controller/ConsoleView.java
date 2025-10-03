package controller;

import model.Author;
import model.Paper;

import java.util.List;

public class ConsoleView {

    public void showAuthors(List<Author> authors) {
        if (authors == null || authors.isEmpty()) {
            System.out.println("No authors found.");
            return;
        }
        System.out.println("=== Authors ===");
        authors.forEach(a ->
                System.out.printf("- %s | %s | h=%d i10=%d cited=%d | id=%s%n",
                        a.getName(), a.getAffiliation(), a.getHIndex(),
                        a.getI10Index(), a.getCitedBy(), a.getAuthorId()));
    }

    public void showPapers(List<Paper> papers) {
        if (papers == null || papers.isEmpty()) {
            System.out.println("No papers found.");
            return;
        }
        System.out.println("=== Papers ===");
        papers.forEach(p ->
                System.out.printf("- %s (%d) | cited=%d | %s%n",
                        p.getTitle(), p.getYear(), p.getCitedBy(), p.getLink()));
    }

    public void info(String msg)  { System.out.println(msg); }
    public void error(String msg) { System.err.println(msg); }
}

