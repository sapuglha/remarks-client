package com.example.remarks.models;

import com.squareup.moshi.Json;

public class Annotation extends Remark {
    @Json(name = "annotation")
    public String message;

    private Annotation(Builder builder) {
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

        public Annotation build() {
            return new Annotation(this);
        }
    }

}
