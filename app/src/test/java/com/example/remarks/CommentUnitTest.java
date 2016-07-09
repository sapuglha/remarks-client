package com.example.remarks;


import android.util.Log;

import com.example.remarks.models.Comment;
import com.ryanharter.auto.value.moshi.AutoValueMoshiAdapterFactory;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import org.junit.Test;

import java.util.Locale;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class CommentUnitTest {
    @Test
    public void comment_compareEquals() throws Exception {
        long now = System.currentTimeMillis();
        Comment first = Comment.create("Comment", now);
        Comment second = Comment.create("Comment", now);

        assertTrue(first.equals(second));
    }

    @Test
    public void comment_compareDifferent() throws Exception {
        long now = System.currentTimeMillis();

        Comment first = Comment.create("First", now);
        Comment second = Comment.create("First", now + 1);

        assertFalse(first.equals(second));
    }

    @Test
    public void comment_stringFormat() throws Exception {
        long timestamp = System.currentTimeMillis();
        String message = "A new comment.";

        Comment comment = Comment.create(message, timestamp);

        String expectedOutput = String.format(Locale.ENGLISH, "Comment{comment=%s, timestamp=%d}", message, timestamp);

        assertTrue(expectedOutput.equals(comment.toString()));
    }

    @Test
    public void comment_MoshiConversion() throws Exception {
        Moshi moshi = new Moshi.Builder()
                .add(new AutoValueMoshiAdapterFactory())
                .build();

        Comment comment = Comment.create("blah", 1468099366980L);

        JsonAdapter<Comment> jsonAdapter = moshi.adapter(Comment.class);

        String json = jsonAdapter.toJson(comment);
        assertEquals("{\"comment\":\"blah\",\"timestamp\":1468099366980}", json);
    }
}
