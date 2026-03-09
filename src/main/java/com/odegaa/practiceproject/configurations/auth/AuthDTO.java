package com.odegaa.practiceproject.configurations.auth;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class AuthDTO {

    @Schema(description = "Username of the employee", example = "user")
    @NotNull
    @Column(unique = true)
    private String username;

    @Schema(description = "Secret password", example = "user123")
    @NotNull
    private String password;

}
