package oleg.bryl.springbootweblibrary.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

class MvcConfigTest {

    private MvcConfig mvcConfigUnderTest;

    @BeforeEach
    void setUp() {
        mvcConfigUnderTest = new MvcConfig();
    }

    @Test
    void testPasswordEncoder() {
        // Setup
        // Run the test
        final BCryptPasswordEncoder result = mvcConfigUnderTest.passwordEncoder();

        // Verify the results
    }

    @Test
    void testLocaleResolver() {
        // Setup
        // Run the test
        final LocaleResolver result = mvcConfigUnderTest.localeResolver();

        // Verify the results
    }

    @Test
    void testLocaleChangeInterceptor() {
        // Setup
        // Run the test
        final LocaleChangeInterceptor result = mvcConfigUnderTest.localeChangeInterceptor();

        // Verify the results
    }

    @Test
    void testAddInterceptors() {
        // Setup
        final InterceptorRegistry registry = new InterceptorRegistry();

        // Run the test
        mvcConfigUnderTest.addInterceptors(registry);

        // Verify the results
    }
}
