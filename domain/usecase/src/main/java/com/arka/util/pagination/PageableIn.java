package com.arka.util.pagination;

public record PageableIn(
        int page,
        int size,
        String sortBy,
        PageSortDirection sortDirection
) {
}
