# Flickr-to-Google
Spring boot app to migrate photos from flickr to google photos with unlimited storage


1. Prepare
* Clone git source to your hard disk
* Create on console google service and download client secret json to root catalog with name: client_secret.json
* Create on Flickr website api to receive Key and Secret and paste content to /src/main/resources/config.properties
2. Run gradle build
3. Run app java -jar build/libs/Flickr-to-Google-0.0.1-SNAPSHOT.jar
4. Follow to instruction in app
5. App create credentials information in accessTokenFlickr and accessTokenGoogle in root catalog with content and database file database.mv.db
5. If everything is ok you should see log from app that photos details are downloading and storage in database
6. App has 2 job, first to obtain information from flickr to database, second use data in database to download each photo and send to google photos.
7. Information: google photos (picassa) api can not receive movies and photos without exif (create photos in upload date - not original), for example screenshots and photos created by application: fb, messenger, whtasup. But information about this is in the database, you can download this manualy. 
8. If you want use application by ssh you should create credentials information on your local host and send to server - in google api redirect url is localhost and not work in remote machine without gui.
