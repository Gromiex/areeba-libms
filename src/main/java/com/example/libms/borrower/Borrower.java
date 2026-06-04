package com.example.libms.borrower;

import com.example.libms.borrowing.Borrowing;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "borrowers")
public class Borrower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "name is mandatory")
    private String name;

    @Email(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    @NotBlank(message = "email is mandatory")
    @Column(unique=true)
    private String email;

    @Pattern(regexp = "^((?:\\+961|00961)[\\s-]?([1-9]|70|71|76|78|79|81)|(0[1-9]|70|71|76|78|79|81))\\d{6}$", message = "number must be in Lebanese format")
    @NotBlank(message = "phone is mandatory")
    @Column(unique=true)
    private String phone;

    @OneToMany(mappedBy = "borrower", cascade = CascadeType.ALL)
    private List<Borrowing> borrowings;

    public Borrower() {}

    public Borrower(Long id) {
        this.id = id;
    }

    public Borrower(Long id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

}
