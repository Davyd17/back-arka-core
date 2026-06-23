package com.arka.util;

import com.arka.util.pagination.PageWrapper;
import com.arka.util.pagination.PageableIn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.function.Function;

public class PageableMapper {

    public static Pageable toPageable(PageableIn pageableInput) {
        return PageRequest.of(
                pageableInput.page(),
                pageableInput.size(),
                Sort.Direction.valueOf(
                        pageableInput.sortDirection().name()),
                pageableInput.sortBy());
    }

    public static <T, R> PageWrapper<R> toPageWrapper(Page<T> page,
                                                      Function<T, R> mapper){
        return new PageWrapper<>(
                page.getContent().stream().map(mapper).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages());
    }
}
