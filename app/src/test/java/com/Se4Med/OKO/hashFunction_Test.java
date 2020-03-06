package com.Se4Med.OKO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class hashFunction_Test {

    @Test
    public void hashTest() throws Exception {

        Login myObjectUnderTest = new Login();
        String password = "test";

        String risultato = myObjectUnderTest.getPassword_hash(password);

        String risultato_str = "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08";
        assertTrue(risultato_str.equals(risultato));
    }
}