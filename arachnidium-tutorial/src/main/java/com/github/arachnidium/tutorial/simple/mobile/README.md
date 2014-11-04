Here are samples of page objects which describe interaction with native Android client.

Application: [VK](https://play.google.com/store/apps/details?id=com.vkontakte.android).

Described user actions:

- login

- select one of sections (Videos)

- the searching for video

- select video which is found.
 
 - 1 [VKLogin](https://github.com/arachnidium/arachnidium-java/blob/master/arachnidium-tutorial/src/main/java/com/github/arachnidium/tutorial/simple/mobile/VKLogin.java) and [VKLoginNativeOnly](https://github.com/arachnidium/arachnidium-java/blob/master/arachnidium-tutorial/src/main/java/com/github/arachnidium/tutorial/simple/mobile/VKLoginNativeOnly.java)

 - 2 [HasSearchField](https://github.com/arachnidium/arachnidium-java/blob/master/arachnidium-tutorial/src/main/java/com/github/arachnidium/tutorial/simple/mobile/HasSearchField.java) as an example of an abstract interaction description and [Videos](https://github.com/arachnidium/arachnidium-java/blob/master/arachnidium-tutorial/src/main/java/com/github/arachnidium/tutorial/simple/mobile/Videos.java) as an example of an extension.
 
- 3 [UserScreen](https://github.com/arachnidium/arachnidium-java/blob/master/arachnidium-tutorial/src/main/java/com/github/arachnidium/tutorial/simple/mobile/UserScreen.java) and  [HasSearchField](https://github.com/arachnidium/arachnidium-java/blob/master/arachnidium-tutorial/src/main/java/com/github/arachnidium/tutorial/simple/mobile/HasSearchField.java)&[Videos](https://github.com/arachnidium/arachnidium-java/blob/master/arachnidium-tutorial/src/main/java/com/github/arachnidium/tutorial/simple/mobile/Videos.java) as the sample of "parent-child" relation representation.
