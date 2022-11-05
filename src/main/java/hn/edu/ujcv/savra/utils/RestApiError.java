package hn.edu.ujcv.savra.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@ToString
public class RestApiError {
    HttpStatus httpStatus;
    String errorMessage;
    String errorDetails;
}
