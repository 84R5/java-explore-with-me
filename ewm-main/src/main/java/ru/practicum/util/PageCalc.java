package ru.practicum.util;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@UtilityClass
public class PageCalc {

    public Pageable getPage(Integer from, Integer size) {
        if (from != null && size != null) {
            int pageNumber = (int) Math.ceil((double) from / size);
            return PageRequest.of(pageNumber, size, Sort.by("id").ascending());
        }
        return Pageable.unpaged();
    }

    public Pageable getPage(Integer from, Integer size, Sort sort) {
        if (from != null && size != null) {
            int pageNumber = (int) Math.ceil((double) from / size);
            return PageRequest.of(pageNumber, size, sort);
        }
        return Pageable.unpaged();
    }

}
