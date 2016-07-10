# Remarks Client

This project demonstrates a simple approach to save Comments and Annotations into a remote server.

## Comment and Annotation classes

These value classes use [AutoValue](https://github.com/google/auto/tree/master/value), they have less code without the need to worry about toString(), equals(), hashCode().
With the use of AutoValue and its practices the class holds the information assigned at creation time, becomes immutable, and its properties cannot be changed by other external methods accessing it.
Here are a few other [benefits](http://ryanharter.com/blog/2016/03/22/autovalue/) on why to use AutoValue.

Comment class has the following properties:

`String comment`: to store the text associated with the Comment instance.

`long timestamp`: indicates a UTC timestamp for this comment.

Annotation class has the following properties:

`String annotation`: to store the text associated with the Annotation instance.

`long timestamp`: indicates a UTC timestamp for this comment.

With the use of AutoValue extensions like [AutoValue: Moshi](https://github.com/rharter/auto-value-moshi) these classes can be converted to JSON for network communication.

These classes are using the [Builder Pattern] (http://www.informit.com/articles/article.aspx?p=1216151&seqNum=2). This pattern allows instantiation of objects using different number of parameters, specially when a parameter could easily use its default value, like the current timestamp when not provided. It's easier to add new properties to the class without the need to create a new constructor with a different combination of parameters.

### Alternative implementation

It is clear that it it's possible to create an abstract super class, call it Remark, containing these common properties, from which both Comment and Annotation would extend. Finally use a factory to construct a Remark, and given the parameters the returning object instance could be either a Comment or Annotation. 

The current approach using AutoValue limits that extensibility, so it was a trade-off between having a more robust class, with better extensibility to add more properties in the future, versus having a bit more complex class hierarchy with less duplicate code.

This approach can be check at this [branch](https://github.com/sapuglha/remarks-client/tree/factory-pattern).


## TextInterface

Comment and Annotation classes implement this interface, and these are the methods they are required to implement:

`getText()`:  retrieves the message associated with the remark.
  
`getTimestamp()`: retrieves the timestamp associated with the remark.

An user interface can display a combination of both Comments and Annotations, and easily retrieve the text and timestamp associated with these two different objects.
      
## Networking

The use of AutoValue and its Moshi extension allowed for an easy integration with Retrofit 2.
All REST calls were implemented in the Rest class. This class is a singleton that holds an instance of an HTTP client. This client is initialized using a dynamic BASE_URL value according to the currently selected build variant.

New REST calls will benefit from less data usage, specially if using TLS since the HTTP client will need to perform that negotiation only on the first call.

It's possible to create new networking classes that would communicate to different backends, as long as it possible to convert the models to an input format that will be sent to the new backend. It's already possible to export the data as JSON, there is another AutoValue extension that would allow to export the data as Parcelable [AutoValue: Parcel extension](https://github.com/rharter/auto-value-parcel).

# Pending improvements

## Local data storage

Every new Comment or Annotation the user creates should be stored locally before sending to the remote store. Mark the item as not synchronized while storing locally, and on network sucess change the status to synchronized. A SQLite could be used to store the items.

More details on things to pay attention while synchronizing local and remote items: [Android Application Architecture (Android Dev Summit 2015)](https://youtu.be/BlkJzgjzL0c?t=2m29s)

## UI/UX

* Change main activity to open a list showing a list of previous Comments and Annotations. 
* Use a FAB ([floating action button](https://material.google.com/components/buttons-floating-action-button.html)) on main activity to add a new item, on press it should show a list of possible options (Comment, Annotation)
* Upon new item type selection, start the AddActivity already knowing which item type is going to be added

## Crash reporting

Applications installed on users's devices do not provide remote access to troubleshoot defects. A good crash reporting tool will allow a better understanding of the app behaviour trough the use of logs, handled and unhandled exceptions. [Crashlytics](http://fabric.io/https://fabric.io/kits/android/crashlytics) is a simple to use tool that will meet all these listed requirements.

## Analytics

Fabric also provides [Answers] (https://fabric.io/kits/android/answers), it collects information about app usage, session counts, session length, user location.