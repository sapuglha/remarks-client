package com.example.remarks.models;

import com.example.remarks.interfaces.TextInterface;
import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

@AutoValue
public abstract class Annotation implements TextInterface {
    // This jsonAdapter is used for conversion to/from JSON
    public static JsonAdapter<Annotation> jsonAdapter(Moshi moshi) {
        return new AutoValue_Annotation.MoshiJsonAdapter(moshi);
    }

    // Builder pattern: http://www.informit.com/articles/article.aspx?p=1216151&seqNum=2
    public static Builder builder() {
        return new AutoValue_Annotation.Builder()
                .timestamp(System.currentTimeMillis()); // set default timestamp
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder annotation(String annotation);

        public abstract Builder timestamp(long timestamp);

        public abstract Annotation build();
    }

    // ====================
    // Class properties
    abstract String annotation();

    abstract long timestamp();

    // ====================
    // Only create getters() to prevent values from changing from outside
    public String getText() {
        return annotation();
    }

    public long getTimestamp() {
        return timestamp();
    }


}
