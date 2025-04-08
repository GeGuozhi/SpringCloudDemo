package com.ggz.configclient.controller;

import org.junit.Test;
import org.mockito.Mockito;

import static org.apache.commons.lang.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author ggz on 2025/4/7
 */
public class MockitoTest {

    public static void main(String[] args) {
        doTest();
    }
    public static void doTest() {
        MyList listMock = Mockito.mock(MyList.class);
        when(listMock.add(anyString())).thenReturn(false);
        boolean added = listMock.add(randomAlphabetic(6));
        assertThat(added, is(false));
    }
}