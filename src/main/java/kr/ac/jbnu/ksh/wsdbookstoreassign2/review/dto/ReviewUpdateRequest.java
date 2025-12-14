package kr.ac.jbnu.ksh.wsdbookstoreassign2.review.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReviewUpdateRequest {

    @NotNull
    @Min(1) @Max(5)
    private Integer rating;

    @NotBlank
    @Size(min = 1, max = 2000)
    private String content;
}
