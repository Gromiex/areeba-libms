package com.example.libms.book.enums;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public enum BookCategory {
    FICTION,
    NON_FICTION,
    SCIENCE,
    HISTORY,
    NOVEL_DRAMA,
    MYSTERY,
    THRILLER,
    FANTASY,
    SCI_FICTION,
    HORROR,
    UNCATEGORIZED;

    // Keywords that map to each category
    private static final Map<BookCategory, List<String>> KEYWORDS = Map.of(
            FICTION,      List.of("fiction"),
            NON_FICTION,  List.of("non-fiction", "nonfiction", "biography", "autobiography", "essay"),
            SCIENCE,      List.of("science", "biology", "chemistry", "physics", "mathematics"),
            HISTORY,      List.of("history", "historical"),
            NOVEL_DRAMA,  List.of("drama", "novel", "romance"),
            MYSTERY,      List.of("mystery", "detective", "crime"),
            THRILLER,     List.of("thriller", "suspense"),
            FANTASY,      List.of("fantasy", "mythology", "fairy"),
            SCI_FICTION,  List.of("science fiction", "sci-fi", "space", "dystopian"),
            HORROR,       List.of("horror", "gothic", "supernatural")
    );

    // Returns the first category whose keywords match any subject name
    public static BookCategory fromSubjectName(String subjectName) {  // no longer Optional
        if (subjectName == null) return UNCATEGORIZED;
        String lower = subjectName.toLowerCase();

        return KEYWORDS.entrySet().stream()
                .filter(entry -> entry.getValue().stream().anyMatch(lower::contains))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(UNCATEGORIZED);    // ← fallback here
    }
}
