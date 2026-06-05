package com.example.libms.borrower;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BorrowerDto {
    private Long id;

    @NotBlank(message = "name is mandatory")
    private String name;

    @Email(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    @NotBlank(message = "email is mandatory")
    private String email;

    @NotBlank(message = "password is mandatory")
    private String password;

    @Pattern(regexp = "^((?:\\+961|00961)[\\s-]?([1-9]|70|71|76|78|79|81)|(0[1-9]|70|71|76|78|79|81))\\d{6}$", message = "number must be in Lebanese format")
    @NotBlank(message = "phone is mandatory")
    private String phone;

    public BorrowerDto() {}

    public BorrowerDto(Long id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public BorrowerDto(Long id, String name, String email, String password, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

}
