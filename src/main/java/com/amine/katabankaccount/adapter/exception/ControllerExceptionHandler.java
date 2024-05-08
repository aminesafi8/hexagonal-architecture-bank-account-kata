package com.amine.katabankaccount.adapter.exception;

import com.amine.katabankaccount.application.core.exception.InsufficientFundsException;
import com.amine.katabankaccount.application.core.exception.InvalidAmountException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestControllerAdvice
public class ControllerExceptionHandler {

    private static final String INTERNAL_SERVER_ERROR = "Technical error";
    private static final String TECHNICAL_ERROR_DETAIL = "Sorry, we are experiencing some technical issues, the problem wil be fixed as soon as possible";

    @ExceptionHandler(AccountNotFoundException.class)
    public ProblemDetail handleAccountNotFoundException(final AccountNotFoundException exception) {
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getLocalizedMessage());

        problemDetail.setType(URI.create(getCurrentUri() + "/errors/account-not-found"));
        problemDetail.setTitle("Account not found");

        return problemDetail;
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ProblemDetail handleInsufficientFundsException(final InsufficientFundsException exception) {
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getLocalizedMessage());
        problemDetail.setType(URI.create(getCurrentUri() + "/errors/insufficient-funds"));
        problemDetail.setTitle("Insufficient funds");

        return problemDetail;
    }

    @ExceptionHandler(InvalidAmountException.class)
    public ProblemDetail handleInvalidAmountsException(final InvalidAmountException exception) {
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage());

        problemDetail.setType(URI.create(getCurrentUri() + "/errors/invalid-amounts"));
        problemDetail.setTitle("Invalid amount");

        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationExceptions(final MethodArgumentNotValidException exception) {
        final ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problemDetail.setType(URI.create(getCurrentUri() + "/errors/validation-errors"));
        problemDetail.setTitle("Invalid request parameters");

        final List<ErrorNode> errorsNodes = exception.getBindingResult().getAllErrors()
                .stream()
                .map(ErrorNode::from)
                .toList();

        problemDetail.setProperty("invalid-params", errorsNodes);

        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleTypeMismatch(final MethodArgumentTypeMismatchException ex) {
        final String name = ex.getName();
        final String type = ex.getRequiredType().getSimpleName();
        final Object value = ex.getValue();
        final String messageDetail = String.format("'%s' should be a valid '%s' and '%s' isn't", name, type, value);

        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, messageDetail);

        problemDetail.setType(URI.create(getCurrentUri() + "/errors/validation-errors"));
        problemDetail.setTitle("Invalid parameters");

        return problemDetail;
    }

    /**
     * The most generic exception interceptor
     */

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGlobalExceptions(final Exception exception) {
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, TECHNICAL_ERROR_DETAIL);

        problemDetail.setType(URI.create(getCurrentUri() + "/errors/server-errors"));
        problemDetail.setTitle(INTERNAL_SERVER_ERROR);

        log.error(INTERNAL_SERVER_ERROR, exception);
        return problemDetail;
    }

    private String getCurrentUri() {
        return ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
    }
}
