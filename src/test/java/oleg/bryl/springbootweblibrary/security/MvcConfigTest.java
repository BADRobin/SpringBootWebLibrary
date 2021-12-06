package oleg.bryl.springbootweblibrary.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

class MvcConfigTest {
    MvcConfig mvcConfig = new MvcConfig();

    @Test
    void testPasswordEncoder() {
        BCryptPasswordEncoder result = mvcConfig.passwordEncoder();
        Assertions.assertEquals(null, result);
    }

    @Test
    void testLocaleResolver() {
        LocaleResolver result = mvcConfig.localeResolver();
        Assertions.assertEquals(null, result);
    }

    @Test
    void testLocaleChangeInterceptor() {
        LocaleChangeInterceptor result = mvcConfig.localeChangeInterceptor();
        Assertions.assertEquals(null, result);
    }

    @Test
    void testAddInterceptors() {
        mvcConfig.addInterceptors(null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme