package com.popova.util;

import com.popova.entity.Page;

import java.util.Set;

public final class PageUtils {

    private PageUtils() {
    }

    public static void numberPagesGraph(Set<Page> pages) {
        int lastId = 0;
        for (Page page : pages) {
            if (page.getId() < 0) {
                page.setId(lastId++);
            }
            for (Page innerPages : page.getRefersTo()) {
                if (innerPages.getId() < 0) {
                    innerPages.setId(lastId++);
                }
            }
        }
    }

    public static void removeSelfLinks(Set<Page> pages) {
        for (Page page : pages) {
            page.getRefersTo().remove(page);
            page.getReferredBy().remove(page);
        }
    }
}
