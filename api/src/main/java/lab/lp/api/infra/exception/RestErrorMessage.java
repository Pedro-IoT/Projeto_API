package lab.lp.api.infra.exception;

import org.springframework.http.HttpStatus;

public record RestErrorMessage (HttpStatus status, String message) {
}
