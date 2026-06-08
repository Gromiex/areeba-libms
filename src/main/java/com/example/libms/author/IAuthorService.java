package com.example.libms.author;

import java.util.List;

public interface IAuthorService {
    List<AuthorDto> getAllAuthors();

    AuthorDto getAuthorById(Long id);

    AuthorDto createAuthor(AuthorDto dto);

    void deleteAuthor(Long id);

    AuthorDto updateAuthor(Long id, AuthorDto dto);
}
