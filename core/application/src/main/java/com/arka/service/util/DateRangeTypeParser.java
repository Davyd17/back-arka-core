package com.arka.service.util;

import com.arka.dto.value.InstantDateRange;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class DateRangeTypeParser {

    public static InstantDateRange toInstant(LocalDate start, LocalDate end){

        ZoneId zone = ZoneId.of("America/Bogota");

        Instant startInst = start
                .atStartOfDay(zone)
                .toInstant();

        Instant endInst  = end
                .plusDays(1)
                .atStartOfDay(zone)
                .toInstant();


        return new InstantDateRange(startInst, endInst);




    }

}
