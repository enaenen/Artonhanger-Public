package com.artonhanger.manage.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@NoArgsConstructor
public class PageRequestDto {
    private int size = 50;
    private int page = 0;

    public Pageable getPageable() {
        Sort sort = Sort.by("createdAt").descending();
        return PageRequest.of(page, size, sort);
    }
    public Pageable getNotSortedPageable(){
        return PageRequest.of(page, size);
    }
}
