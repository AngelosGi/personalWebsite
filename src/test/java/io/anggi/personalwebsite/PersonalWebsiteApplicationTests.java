package io.anggi.personalwebsite;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PersonalWebsiteApplicationTests {

	@Test
	void contextLoads() {
	}

	    @Test
    void simpleAssertion() {
        String expected = "Hello";
        String actual = "Hello";
        assertEquals(expected, actual, "A simple string comparison should pass");
        assertTrue(true, "This assertion should always pass");
    }

}
