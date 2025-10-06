package com.university.server;

import controller.AppController;
import controller.ConsoleView;
import service.ScholarService;

/**
 * Uso:
 *  - Sin argumentos: busca por "universidad"
 *  - Con argumento de consulta: Main "MIT"
 *  - Con autor: Main "author:ABCDEFG123456789"
 */
public class Main {
    public static void main(String[] args) {
        String apiKey = System.getenv("SERPAPI_API_KEY");
        var service = new ScholarService(apiKey);
        var view    = new ConsoleView();
        var app     = new AppController(service, view);

        if (args.length > 0 && args[0].startsWith("author:")) {
            String authorId = args[0].substring("author:".length()).trim();
            if (authorId.isEmpty()) {
                view.error("Missing author id after 'author:'.");
                return;
            }
            app.showPapersFor(authorId);
        } else {
            String query = (args.length > 0) ? args[0] : "universidad";
            app.searchAndShowAuthors(query);
        }
    }
}


