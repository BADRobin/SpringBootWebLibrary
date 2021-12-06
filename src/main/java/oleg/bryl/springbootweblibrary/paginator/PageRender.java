package oleg.bryl.springbootweblibrary.paginator;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class PageRender<T> {
    private String url;
    private Page<T> page;
    private int totalPages;
    private int numberOfItemsPerPage;
    private int actualPage;

    private List<PageItem> pageItems;

    /**
     *
     * @param url
     * @param page
     */
    public PageRender(String url, Page<T> page) {
        this.url = url;
        this.page = page;
        this.pageItems = new ArrayList<PageItem>();
        numberOfItemsPerPage = page.getSize();
        totalPages = page.getTotalPages();
        actualPage = page.getNumber() + 1;


        int sinth, until;
        if (totalPages <= numberOfItemsPerPage) {
            sinth = 1;
            until = totalPages;
        } else {
            if (actualPage <= numberOfItemsPerPage / 2) {
                sinth = 1;
                until = numberOfItemsPerPage;
            } else if (actualPage >= totalPages - numberOfItemsPerPage / 2) {
                sinth = totalPages - numberOfItemsPerPage + 1;
                until = numberOfItemsPerPage;
            } else {
                sinth = actualPage - numberOfItemsPerPage / 2;
                until = numberOfItemsPerPage;
            }
        }

        for (int i = 0; i < until; i++) {
            pageItems.add(new PageItem(sinth + i, actualPage == sinth + i));
        }
    }

    public PageRender(String url, List<T> allBooks) {
    }

    public String getUrl() {
        return url;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getActualPage() {
        return actualPage;
    }

    public List<PageItem> getPageItems() {
        return pageItems;
    }

    public boolean isFirst(){
        return page.isFirst();
    }
    public boolean isLast(){
        return page.isLast();
    }

    public boolean isHasNext(){
        return page.hasNext();
    }
    public boolean isHasPrevious(){
        return page.hasPrevious();
    }

}

