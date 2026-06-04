package com.example.libms.author;

import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    public AuthorDto toDto(Author author){
        return new AuthorDto(
                author.getId(), author.getName(), author.getBiography()
        );
    }

    public Author toEntity(AuthorDto authorDto){
        Author author = new Author();
        author.setId(authorDto.getId());
        author.setName(authorDto.getName());
        author.setBiography(authorDto.getBiography());
        return author;
    }
}
