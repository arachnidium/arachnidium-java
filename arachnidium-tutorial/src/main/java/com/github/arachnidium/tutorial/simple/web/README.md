Here are samples of page objects which describe interaction with browser.

Application: [VK](http://vk.com).

Described user actions:

- login

- select one of sections (Videos)

- the searching for video

- select video which is found.


Recomended sequence:

 - 1 [VKLogin](https://github.com/arachnidium/arachnidium-java/blob/master/arachnidium-tutorial/src/main/java/com/github/arachnidium/tutorial/simple/web/VKLogin.java) and [VKLoginBrowserOnly](https://github.com/arachnidium/arachnidium-java/blob/master/arachnidium-tutorial/src/main/java/com/github/arachnidium/tutorial/simple/web/VKLoginBrowserOnly.java)

 - 2 [HasSearchField](https://github.com/arachnidium/arachnidium-java/blob/master/arachnidium-tutorial/src/main/java/com/github/arachnidium/tutorial/simple/web/HasSearchField.java) as an example of an abstract interaction description and [Videos](https://github.com/arachnidium/arachnidium-java/blob/master/arachnidium-tutorial/src/main/java/com/github/arachnidium/tutorial/simple/web/Videos.java) as an example of an extension.
 
- 3 [UserPage](https://github.com/arachnidium/arachnidium-java/blob/master/arachnidium-tutorial/src/main/java/com/github/arachnidium/tutorial/simple/web/UserPage.java) and  [HasSearchField](https://github.com/arachnidium/arachnidium-java/blob/master/arachnidium-tutorial/src/main/java/com/github/arachnidium/tutorial/simple/web/HasSearchField.java)&[Videos](https://github.com/arachnidium/arachnidium-java/blob/master/arachnidium-tutorial/src/main/java/com/github/arachnidium/tutorial/simple/web/Videos.java) and as the sample of "parent-child" relation representation.
