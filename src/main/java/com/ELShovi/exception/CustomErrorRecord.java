package com.ELShovi.exception;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CustomErrorRecord(
        LocalDateTime dateTime,
        String message,
        String details
) {
}
