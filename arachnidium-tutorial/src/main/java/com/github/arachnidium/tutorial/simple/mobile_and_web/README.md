Here are samples of page objects which describe interaction with web page and native Android client.

Applications: 

- [VK web](http://vk.com)

- [VK for Android](https://play.google.com/store/apps/details?id=com.vkontakte.android).

Described user actions:

- login

- select one of sections (Videos)

- the searching for video

- select video which is found.

Recomended sequence:

 - 1 [VKLogin](https://github.com/arachnidium/arachnidium-java/blob/master/arachnidium-tutorial/src/main/java/com/github/arachnidium/tutorial/simple/mobile_and_web/VKLogin.java)

 - 2 [HasSearchField](https://github.com/arachnidium/arachnidium-java/blob/master/arachnidium-tutorial/src/main/java/com/github/arachnidium/tutorial/simple/mobile_and_web/HasSearchField.java) as an example of an abstract interaction description and [Videos](https://github.com/arachnidium/arachnidium-java/blob/master/arachnidium-tutorial/src/main/java/com/github/arachnidium/tutorial/simple/mobile_and_web/Videos.java) as an example of an extension.
 
 - 3 [User](https://github.com/arachnidium/arachnidium-java/blob/master/arachnidium-tutorial/src/main/java/com/github/arachnidium/tutorial/simple/mobile_and_web/UserScreen.java) and  [HasSearchField](https://github.com/arachnidium/arachnidium-java/blob/master/arachnidium-tutorial/src/main/java/com/github/arachnidium/tutorial/simple/mobile_and_web/HasSearchField.java)&[Videos](https://github.com/arachnidium/arachnidium-java/blob/master/arachnidium-tutorial/src/main/java/com/github/arachnidium/tutorial/simple/mobile_and_web/Videos.java) as the sample of "parent-child" relation representation.
