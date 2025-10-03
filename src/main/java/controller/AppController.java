package controller;

import model.Author;
import model.Paper;
import service.ScholarService;

import java.util.List;

public class AppController {

    private final ScholarService service;
    private final ConsoleView view;

    public AppController(ScholarService service, ConsoleView view) {
        this.service = service;
        this.view = view;
    }

    /** Busca autores y los muestra */
    public void searchAndShowAuthors(String query) {
        try {
            List<Author> authors = service.searchAuthors(query);
            view.showAuthors(authors);
        } catch (Exception e) {
            view.error("Search error: " + e.getMessage());
        }
    }

    /** Lista artículos del autor y los muestra */
    public void showPapersFor(String authorId) {
        try {
            List<Paper> papers = service.listPapers(authorId);
            view.showPapers(papers);
        } catch (Exception e) {
            view.error("List papers error: " + e.getMessage());
        }
    }
}

