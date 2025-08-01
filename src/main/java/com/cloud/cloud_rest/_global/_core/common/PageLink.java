package com.cloud.cloud_rest._global._core.common;

import lombok.Data;

@Data
public class PageLink {

    private int pageNumber;
    private int displayNumber;
    private boolean active;

    public PageLink (int pageNumber,
                     int displayNumber,
                     boolean active) {
        this.pageNumber = pageNumber;
        this.displayNumber = displayNumber;
        this.active = active;
    }

}
