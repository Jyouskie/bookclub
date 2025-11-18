package com.bookclub.bookclub.web;


import com.bookclub.dao.impl.MongoWishlistDao;
import com.bookclub.model.WishlistItem;
import com.bookclub.web.WishlistRestController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * “Web” unit tests for WishlistRestController
 * (controller-level tests with mocked dependencies)
 */
@ExtendWith(MockitoExtension.class)
class WishlistRestControllerTest {

    @Mock
    private MongoWishlistDao wishlistDao;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private WishlistRestController controller;

    // Web Test #1: list() returns the logged-in user's wishlist
    @Test
    void list_returnsUserWishlist() {
        // Arrange
        when(authentication.getName()).thenReturn("testuser01");

        WishlistItem a = new WishlistItem();
        a.setId("abc123");
        a.setIsbn("9780439023481");
        a.setTitle("The Games");
        a.setUsername("testuser01");

        List<WishlistItem> mockList = List.of(a);
        when(wishlistDao.list("testuser01")).thenReturn(mockList);

        // Act
        List<WishlistItem> result = controller.list(authentication);

        // Assert
        assertEquals(1, result.size());
        assertEquals("abc123", result.get(0).getId());
        assertEquals("9780439023481", result.get(0).getIsbn());
        assertEquals("The Games", result.get(0).getTitle());
        assertEquals("testuser01", result.get(0).getUsername());
        verify(wishlistDao).list("testuser01");
    }

    // Web Test #2: delete() calls Dao remove with username + id
    @Test
    void delete_deletesWhenOwnedByUser() {
        // Arrange
        when(authentication.getName()).thenReturn("testuser01");
        when(wishlistDao.remove("testuser01", "abc123")).thenReturn(true);

        // Act
        controller.delete("abc123", authentication);

        // Assert
        verify(wishlistDao).remove("testuser01", "abc123");
    }
}
