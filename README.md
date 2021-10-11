**Bottle Rocket®**

**Senior Android Developer Test**

This document outlines features and tasks surrounding our test weather Android app.
Please feel free to create a project from scratch or you’re welcome to use the BR open source architecture demo project as a starting point or as references.
Please submit a zip file so that we may review your code (please perform a “gradle clean” first so the /build files won’t be included in the zip file. Also, please make sure your name is NOT included in the code / zip file.) We will compile and run the app on devices for testing.
Listed below are the minimum requirements with optional features. Show off your Android dev chops by adding your own fancy features! Good luck and have fun!

**Minimum Requirements**

• Please use Kotlin.

• Use our “All The Weather” API for pulling down and parsing the weather data. This following information is included in the Apiary link above but just for your reference, please note:

o Base URL is https://weather.exam.bottlerocketservices.com

o Sample city list includes the following:
(https://weather.exam.bottlerocketservices.com/cities) Center Point Chelsea Chickasaw

• Implement 2 screens with the design provided here – including the Main and City Search Screens described below.

• Use the provided image assets for the icons seen in the mockups.

• Display a message to the user if a data connection or cache is unavailable for any of the network calls.

• Nice to have - Implement an error state, empty state, and loading state for data fetching.

**Main Screen**

• Display the header (including city name, date, time and temperature for the day), weekly (non-scrollable) and hourly forecast data (scrollable) in a list view for the city Chelsea.

• When the user types a city search term in the search bar and selects enter, they are taken to the city search screen.

• Optional / Bonus points –

o Radar icon: Selecting the radar icon transitions to the radar image screen of the current city they are viewing in the pager.

o Page indicator: After a city is selected from the search result, add a new screen / page for the selected city and allow user to navigate between the cities.

o Remove icon: Clicking on the Remove icon removes the current screen / page. If this is the last one, show an empty state.

**City Search Screen**

• Perform a city search using the search text from the main screen toolbar.

• Show a loading, no results, and error state.

• If found, take user back to the Main screen and update it with the parsed city weather data.

**Radar Image Screen (Optional)**

• Display the radar image full screen.

• Display the city name as the title in the toolbar.
