package com.example.remarks.models;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

@AutoValue
public abstract class Comment {
    // This jsonAdapter is used for conversion to/from JSON
    public static JsonAdapter<Comment> jsonAdapter(Moshi moshi) {
        return new AutoValue_Comment.MoshiJsonAdapter(moshi);
    }

    // Builder pattern: http://www.informit.com/articles/article.aspx?p=1216151&seqNum=2
    public static Builder builder() {
        return new AutoValue_Comment.Builder()
                .timestamp(System.currentTimeMillis()); // set default timestamp
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder comment(String comment);

        public abstract Builder timestamp(long timestamp);

        public abstract Comment build();
    }

    // ====================
    // Class properties
    abstract String comment();

    abstract long timestamp();

    // ====================
    // Only create getters() to prevent values from changing from outside
    public String getText() {
        return comment();
    }

    public long getTimestamp() {
        return timestamp();
    }
}
