package com.example.remarks.models;

public class RemarkFactory {
    public static Remark getRemark(String type, String comment, long timestamp){

        switch (type.toLowerCase()) {
            case "comment": {
                Comment.Builder builder = new Comment.Builder(comment);
                if (0 != timestamp) {
                    builder.timestamp(timestamp);
                }
                return builder.build();
            }
            case "annotation": {
                Annotation.Builder builder = new Annotation.Builder(comment);
                if (0 != timestamp) {
                    builder.timestamp(timestamp);
                }
                return builder.build();
            }
        }
        return null;
    }
}
