package com.example.remarks.models;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

@AutoValue
public abstract class Comment {
    public static JsonAdapter<Comment> jsonAdapter(Moshi moshi) {
        return new AutoValue_Comment.MoshiJsonAdapter(moshi);
    }

    public static Comment create(String comment, long timestamp) {
        return new AutoValue_Comment(comment, timestamp);
    }

    abstract String comment();

    abstract long timestamp();
}
