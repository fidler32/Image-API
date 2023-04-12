# Getting Started
This project is set up to analyze objects with images using Google Cloud API to detect objects in photos.

### Configuration

#### MongoDB

Must run a mongoDB instance and configure in application.properties

`spring.data.mongodb.host=localhost`

 `spring.data.mongodb.port=27017`

 `spring.data.mongodb.database=imagesdb`
 
 For a relational DB example please checkout relationalDb branch
 
#### Google Cloud Credentials 
Must set Google Cloud Environment variables

`export GOOGLE_APPLICATION_CREDENTIALS=${PathToCloudCredentials}`

#### To run 

./gradlew bootrun

# Google Cloud API Links
[Google Cloud API Object detection](https://cloud.google.com/vision/docs/object-localizer?apix_params=%7B%22resource%22%3A%7B%22requests%22%3A%5B%7B%22features%22%3A%5B%7B%22maxResults%22%3A10%2C%22type%22%3A%22OBJECT_LOCALIZATION%22%7D%5D%2C%22image%22%3A%7B%22source%22%3A%7B%22imageUri%22%3A%22https%3A%2F%2Fcloud.google.com%2Fvision%2Fdocs%2Fimages%2Fbicycle_example.png%22%7D%7D%7D%5D%7D%7D#vision_localize_objects_gcs-java)

[Google Cloud Client Libraries](https://developers.google.com/analytics/devguides/config/admin/v1/quickstart-client-libraries)

