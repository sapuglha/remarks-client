package com.example.remarks;


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

        Comment first = Comment
                .builder()
                .comment("Comment")
                .timestamp(now)
                .build();

        Comment second = Comment
                .builder()
                .comment("Comment")
                .timestamp(now)
                .build();

        assertTrue(first.equals(second));
    }

    @Test
    public void comment_compareDifferent() throws Exception {
        Comment first = Comment
                .builder()
                .comment("first")
                .build(); // using constructor provided timestamp, since we're not specifying one here

        long firstTimestamp = first.getTimestamp();

        Comment second = Comment
                .builder()
                .comment("first")
                .timestamp(firstTimestamp + 1) // the timestamp will be different from the previous
                .build();

        assertFalse(first.equals(second));
    }

    @Test
    public void comment_stringFormat() throws Exception {
        long timestamp = System.currentTimeMillis();
        String message = "A new comment.";

        Comment comment = Comment
                .builder()
                .comment(message)
                .timestamp(timestamp)
                .build();

        String expectedOutput = String.format(Locale.ENGLISH, "Comment{comment=%s, timestamp=%d}", message, timestamp);

        assertTrue(expectedOutput.equals(comment.toString()));
    }

    @Test
    public void comment_MoshiConversion() throws Exception {
        Moshi moshi = new Moshi.Builder()
                .add(new AutoValueMoshiAdapterFactory())
                .build();

        Comment comment = Comment
                .builder()
                .comment("blah")
                .timestamp(1468099366980L)
                .build();

        JsonAdapter<Comment> jsonAdapter = moshi.adapter(Comment.class);

        String json = jsonAdapter.toJson(comment);
        assertEquals("{\"comment\":\"blah\",\"timestamp\":1468099366980}", json);
    }
}
