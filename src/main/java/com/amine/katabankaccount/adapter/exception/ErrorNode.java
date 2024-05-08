package com.amine.katabankaccount.adapter.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorNode {

    private String name;
    private String reason;

    public static ErrorNode from(final ObjectError error) {
        String fieldName = ((FieldError) error).getField();
        String errorMessage = error.getDefaultMessage();

        return ErrorNode.builder()
                .name(fieldName)
                .reason(errorMessage)
                .build();
    }
}
