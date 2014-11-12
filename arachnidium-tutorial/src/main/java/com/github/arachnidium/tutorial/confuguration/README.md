Here is the sample of general parameters defined in **settings.json**:

- "DesiredCapabilities". Here are capabilities which should be used at all cases;

- "Log". It is required log level for console/reports. This parameter is defined once and cann't be overriden for now.

- "screenShots". They are parameters which define what to do if there is a new browser window/context of mobile application and 
what to do when HTML element is highlighted. When "true" then a screenshot is taken.

- "ChromeDriver", "IEDriver" and "PhantomJSDriver" store path to binaries of **chromedriver**, **IEDriver** and **phantomjs**. These settings are useful when binaries are put to the project or path to them is constant. They are redundant otherwise.

-  "handleWaitingTimeOut" is the default timeout of the implicitly waiting for some window, page, context on some conditions. 

- "alertIsPresentTimeOut" is the default timeout of the implicitly waiting for alert if it has to be caught by script.

- "windowIsClosedTimeOut" is the default timeout of the implicitly waiting for a browser window is closed. It is the browser only setting. It is ignored when you work with mobile app.

This list is just example. These settings can be customized. Something can be removed and something can be added. Also you can add your own parameters that you can make up.

Available data types for now:

- **BOOL** is Boolean

- **STRING** is String

- **FLOAT** is Float

- **INT** is Integer.

- **LONG** is Long

 
