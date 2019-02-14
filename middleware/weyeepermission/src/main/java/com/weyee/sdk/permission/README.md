Android Intents
===============

- Email intents:

    `EmailIntents.newEmailIntent( "me@example.com", "My subject", "Hey there!" )`

- Phone intents:

    `PhoneIntents.newDialNumberIntent(null)`

    `PhoneIntents.newCallNumberIntent("+123456789")`

    `PhoneIntents.newDialNumberIntent("+123456789")`

    `PhoneIntents.newSmsIntent("+123456789", "this is a test SMS")`

    `PhoneIntents.newSmsIntent("this is a test SMS")`

    `PhoneIntents.newPickContactIntent()`

    `PhoneIntents.newPickContactWithPhoneIntent()`
	
- Geo intents:

    `GeoIntents.newMapsIntent( "Musée du Louvre 75058 Paris", "Le Louvre" )`

    `GeoIntents.newMapsIntent( 43.481055f, -1.561959f, "My label for that place" )`

    `GeoIntents.newStreetViewIntent( 43.481055f, -1.561959f )`

    `GeoIntents.newNavigationIntent( "1 rue du Louvre 75058 Paris, France" )`
	
- System intents:

    `SystemIntents.newMarketForAppIntent( getApplicationContext() )`

- Media intents:

    `MediaIntents.newPlayYouTubeVideoIntent("b_yiWIXBI7o")`

	`MediaIntents.newPlayImageIntent("http://upload.wikimedia.org/wikipedia/commons/thumb/a/a9/Biarritz-Plage.JPG/1920px-Biarritz-Plage.JPG")`

    `MediaIntents.newPlayAudioIntent("http://www.stephaniequinn.com/Music/Allegro%20from%20Duet%20in%20C%20Major.mp3")`

    `MediaIntents.newPlayVideoIntent("https://www.youtube.com/watch?v=b_yiWIXBI7o")`

    `MediaIntents.newOpenWebBrowserIntent("http://vincentprat.info")`

    `MediaIntents.newTakePictureIntent(Environment.getExternalStorageDirectory().toString() + "/temp.jpg")`

    `MediaIntents.newSelectPictureIntent()`

- Permission intents:

    `PermissionIntents.toPermissionSetting()`