package oleg.bryl.springbootweblibrary.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RoleTest {

    private Role roleUnderTest;

    @BeforeEach
    void setUp() {
        roleUnderTest = new Role("rolename");
    }

}
