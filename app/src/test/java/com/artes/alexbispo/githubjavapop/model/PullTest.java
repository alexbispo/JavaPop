package com.artes.alexbispo.githubjavapop.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class PullTest {

    @Test
    public void testGetCreatedAtFormatted() throws Exception {
        Pull pull = new Pull();
        pull.setCreatedAt("2017-05-11T16:13:29Z");
        assertEquals("11/05/2017", pull.getCreatedAtFormatted("dd/MM/yyyy"));
    }
}