package com.example.libms.author;

import com.example.libms.book.dto.OpenLibraryAuthorResponseDto;
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

    public Author toEntity(OpenLibraryAuthorResponseDto dto) {
        Author author = new Author();
        author.setId(null);
        author.setName(dto.getName());
        if (dto.getBio() == null) {
            author.setBiography("No Biography Available");
        } else {
            author.setBiography(dto.getBio().getValue());
        }
        return author;
    }
}
