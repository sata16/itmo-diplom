package com.example.diplom.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@UtilityClass
public class PaginationUtil {
    public static PageRequest getPageRequests(Integer page, Integer perPage, String sort, Sort.Direction order) {
        if(page == null){
            page = 0;
        } else if (page > 0) {
            page = page - 1;
        }

        if(perPage == null){
            perPage = 10;
        }

        if(order == null || sort == null){
            return PageRequest.of(page, perPage);
        } else if(order.equals(Sort.Direction.DESC)) {
            return PageRequest.of(page,perPage, Sort.by(Sort.Direction.DESC, sort));
        }else {
            return PageRequest.of(page, perPage, Sort.by(Sort.Direction.ASC, sort));
        }

    }
}
