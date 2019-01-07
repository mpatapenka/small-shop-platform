package org.mpatapenka.ssp.service.filter;

import lombok.Data;

import java.util.Set;

@Data
public final class ProductFilter {
    private Long categoryId;
    private Boolean archived;
    private Boolean sale;
    private Set<Long> sizeIds;
}