package com.example.libms.author;

import com.example.libms.author.exceptions.AuthorNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private static final Logger log = LoggerFactory.getLogger(AuthorService.class);
    private final AuthorRepository repository;
    private final AuthorMapper mapper;

    public List<AuthorDto> getAllAuthors() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public AuthorDto getAuthorById(Long id) {
        Author author = repository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("no author with id: " + id));

        return mapper.toDto(author);
    }

    public AuthorDto createAuthor(AuthorDto dto) {
        Author savedAuthor = repository.save(mapper.toEntity(dto));
        log.info("added new author {}", savedAuthor.getName());
        return mapper.toDto(savedAuthor);
    }

    public void deleteAuthor(Long id) {
        if (!repository.existsById(id)) {
            throw new AuthorNotFoundException("no author with id: " + id);
        }

        repository.deleteById(id);
        log.info("Deleted user with id {}", id);
    }

    public AuthorDto updateAuthor(Long id, AuthorDto dto) {
        Author existingAuthor = repository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("no author with id " + id));

        existingAuthor.setName(dto.getName());
        existingAuthor.setBiography(dto.getBiography());

        return mapper.toDto(repository.save(existingAuthor));
    }

}
