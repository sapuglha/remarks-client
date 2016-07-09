package com.example.remarks;


import com.example.remarks.models.Annotation;
import com.ryanharter.auto.value.moshi.AutoValueMoshiAdapterFactory;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import org.junit.Test;

import java.util.Locale;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class AnnotationUnitTest {
    @Test
    public void annotation_compareEquals() throws Exception {
        long now = System.currentTimeMillis();

        Annotation first = Annotation
                .builder()
                .annotation("Annotation")
                .timestamp(now)
                .build();

        Annotation second = Annotation
                .builder()
                .annotation("Annotation")
                .timestamp(now)
                .build();

        assertTrue(first.equals(second));
    }

    @Test
    public void Annotation_compareDifferent() throws Exception {
        Annotation first = Annotation
                .builder()
                .annotation("first")
                .build(); // using constructor provided timestamp, since we're not specifying one here

        long firstTimestamp = first.getTimestamp();

        Annotation second = Annotation
                .builder()
                .annotation("first")
                .timestamp(firstTimestamp + 1) // the timestamp will be different from the previous
                .build();

        assertFalse(first.equals(second));
    }

    @Test
    public void Annotation_stringFormat() throws Exception {
        long timestamp = System.currentTimeMillis();
        String message = "A new Annotation.";

        Annotation annotation = Annotation
                .builder()
                .annotation(message)
                .timestamp(timestamp)
                .build();

        String expectedOutput = String.format(Locale.ENGLISH, "Annotation{annotation=%s, timestamp=%d}", message, timestamp);

        assertTrue(expectedOutput.equals(annotation.toString()));
    }

    @Test
    public void Annotation_MoshiConversion() throws Exception {
        Moshi moshi = new Moshi.Builder()
                .add(new AutoValueMoshiAdapterFactory())
                .build();

        Annotation annotation = Annotation
                .builder()
                .annotation("blah")
                .timestamp(1468099366980L)
                .build();

        JsonAdapter<Annotation> jsonAdapter = moshi.adapter(Annotation.class);

        String json = jsonAdapter.toJson(annotation);
        assertEquals("{\"annotation\":\"blah\",\"timestamp\":1468099366980}", json);
    }
}
