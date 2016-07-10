package com.example.remarks;


import com.example.remarks.models.Comment;
import com.example.remarks.models.RemarkFactory;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

public class CommentUnitTest {
    @Test
    public void Comment_compareDifferent() throws Exception {
        // using constructor provided timestamp, since we're not specifying one here
        Comment first = (Comment) RemarkFactory.getRemark("Comment", "first", 0);

        long firstTimestamp = first.getTimestamp();

        Comment second = (Comment) RemarkFactory.getRemark("Comment", "first", firstTimestamp + 1);

        assertFalse(first.equals(second));
    }

    @Test
    public void Comment_MoshiConversion() throws Exception {
        Moshi moshi = new Moshi.Builder().build();

        Comment comment = (Comment) RemarkFactory.getRemark("Comment", "blah", 1468099366980L);

        JsonAdapter<Comment> jsonAdapter = moshi.adapter(Comment.class);

        String json = jsonAdapter.toJson(comment);
        assertEquals("{\"comment\":\"blah\",\"timestamp\":1468099366980}", json);
    }
}
