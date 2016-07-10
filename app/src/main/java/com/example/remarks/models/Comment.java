package com.example.remarks.models;

import com.squareup.moshi.Json;

public class Comment extends Remark {
    @Json(name = "comment")
    public String message;

    private Comment(Builder builder) {
        message = builder.message;
        timestamp = builder.timestamp;
    }

    static class Builder {
        // Required parameters
        private final String message;

        // Optional parameters - initialized to default values
        private long timestamp = System.currentTimeMillis();

        Builder(String message) {
            this.message = message;
        }

        public Builder timestamp(long val) {
            timestamp = val;
            return this;
        }

        public Comment build() {
            return new Comment(this);
        }
    }
}
