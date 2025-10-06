# Server\_Database

A small Java (Maven) console app that integrates with SerpApi to query Google Scholar and display results in the terminal.
It was built following a lightweight MVC approach (controller + service + simple view) to keep the code easy to read and extend.

### Project purpose:

The project involves automating the integration of information about researchers and published articles at a university by using the Google Scholar API to retrieve relevant data on researchers and research papers. This system must enable efficient integration with the institution’s research database.

### Key functionalities:

Query Google Scholar (via SerpApi) for:

* Articles (titles, links, venues, years, citation info when available)
* Author profiles (names, affiliations, citation metrics)
* Filter and customize searches (language, year ranges, pagination).
* DB integration \& persistence.

### Why it matters?

* Eliminates manual copy/paste from Scholar pages.
* Standardizes how data is collected and makes it reproducible.
* Provides a foundation to plug results into other systems (registries, analytics, dashboards).
