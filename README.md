![top](/imgs/newsapp.jpg)

### **Purpose**

Answer a technical test in an interview process with an application that meets the demands of the
test, uses a Clean Architecture with MVI and the latest Android libraries from Jetpack in 2024.

### **Test Description**

The app should connect to this API https://hn.algolia.com/api/v1/search_by_date?query=mobile and
display published articles.
If the app is used offline, it should display the last downloaded items.
The main view of the app is a list view of recent posts. If the user taps on a post, it shows a
WebView (within the app) with the linked article.
Additionally, you should be able to swipe over a cell and delete an individual post. Once a post is
deleted, it should not appear again even if the data is updated.
Regarding the design, the test comes with mockups that must be considered.

### **My Approach**

To answer this test, the first thing I did was create a new project configured with Jetpack Compose.
Then create the data layer with its unit tests, including a remote model as a mirror of the API, and
a local model to save the data in the database. Also the Room, Retrofit, Mockk and Hilt libraries.
Continue the architecture with the domain, presentation and UI layer, along with the functionalities
requested in the test. The data flow is handled with the MVI architecture pattern. Finally add some
instrumental tests.
As for deleting articles, I chose to only hide them, since this way it would be easy to add
functionality that recovers those articles in the future.

### **Libraries/concepts used**

* Clean Architecture with MVI pattern in presentation layer
* Jetpack Compose with Material3 and Material components - for UI layer
* Kotlin Coroutines & Kotlin Flow - for concurrency & reactive approach
* Kotlin Serialization converter - for JSON parsing
* Retrofit - for networking
* Hilt - for Dependency Injection pattern implementation
* Room - for local database
* Gradle version catalogs - to add and maintain dependencies and plugins
* Navigation Compose - for navigation between screens
* Unit Testing - for data and domain layers
* Instrumental Testing - For ui layer with Jetpack Compose
