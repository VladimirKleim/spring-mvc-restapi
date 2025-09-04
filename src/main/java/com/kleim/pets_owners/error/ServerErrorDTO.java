package com.kleim.pets_owners.error;

import java.time.LocalDateTime;

public record ServerErrorDTO(

        String message,
        String detailMessage,
        LocalDateTime date

) {}
