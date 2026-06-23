package com.arka.util.pagination;

import java.util.List;
import java.util.function.Function;

public record PageWrapper<T>(
        List<T> content,
        int page,
        int pageSize,
        long totalElements,
        int totalPages
) {

    public <R> PageWrapper<R> map(Function<T, R> mapper){

        return new PageWrapper<>(
                this.content.stream().map(mapper).toList(),
                this.page,
                this.pageSize,
                this.totalElements,
                this.totalPages);
    }
}
