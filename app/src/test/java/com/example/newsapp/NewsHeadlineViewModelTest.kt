package com.example.newsapp

import org.junit.Test
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue

class NewsHeadlineViewModelTest
{
    @Test
    fun onGetHeadlines(source: String?)
    {
        if (source == null)
            assertFalse(true)
    }
}