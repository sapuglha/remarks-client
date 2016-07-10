package com.example.remarks;


import com.example.remarks.models.Annotation;
import com.example.remarks.models.RemarkFactory;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

public class AnnotationUnitTest {
    @Test
    public void Annotation_compareDifferent() throws Exception {
        // using constructor provided timestamp, since we're not specifying one here
        Annotation first = (Annotation) RemarkFactory.getRemark("Annotation", "first", 0);

        long firstTimestamp = first.getTimestamp();

        Annotation second = (Annotation) RemarkFactory.getRemark("Annotation", "first", firstTimestamp + 1);

        assertFalse(first.equals(second));
    }

    @Test
    public void Annotation_MoshiConversion() throws Exception {
        Moshi moshi = new Moshi.Builder().build();

        Annotation annotation = (Annotation) RemarkFactory.getRemark("Annotation", "blah", 1468099366980L);

        JsonAdapter<Annotation> jsonAdapter = moshi.adapter(Annotation.class);

        String json = jsonAdapter.toJson(annotation);
        assertEquals("{\"annotation\":\"blah\",\"timestamp\":1468099366980}", json);
    }
}
