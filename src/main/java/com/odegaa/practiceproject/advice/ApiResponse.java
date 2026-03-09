package com.odegaa.practiceproject.advice;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ApiResponse<T> {
    @Schema(description = "Response message", example = "Successfully authenticated")
    private String message;

    @Schema(description = "Operation status code", example = "200")
    private boolean success;

    @Schema(description = "Payload data (e.g. JWT Token)")
    private T data;
    @Builder.Default
    private long timeStamp = System.currentTimeMillis();
}
