# Remarks Client

This project demonstrates a simple approach to save instances of Comments class to a remote server.

## Class: Comment

This class is using [AutoValue](https://github.com/google/auto/tree/master/value), so it's simpler without the need to worry about toString(), equals(), hashCode().

The class has the following properties:
`String comment` to store the text associated with the Comment instance.
`long timestamp` indicates a UTC timestamp for this comment.

With the use of AutoValue Extensions like [AutoValue: Moshi Extension](https://github.com/rharter/auto-value-moshi) the class can be converted to JSON for network communication.
