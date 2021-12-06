package oleg.bryl.springbootweblibrary.paginator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.mockito.Mockito.*;

class PageRenderTest {
    @Mock
    Page<T> page;
    @Mock
    List<PageItem> pageItems;
    @InjectMocks
    PageRender pageRender;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testIsFirst() {
        boolean result = pageRender.isFirst();
        Assertions.assertEquals(true, result);
    }

    @Test
    void testIsLast() {
        boolean result = pageRender.isLast();
        Assertions.assertEquals(true, result);
    }

    @Test
    void testIsHasNext() {
        boolean result = pageRender.isHasNext();
        Assertions.assertEquals(true, result);
    }

    @Test
    void testIsHasPrevious() {
        boolean result = pageRender.isHasPrevious();
        Assertions.assertEquals(true, result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme