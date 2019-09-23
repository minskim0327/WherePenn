# WherePenn
WherePenn is an android mobile application project for UPenn Penn Labs Technical Challenge, Fall 2019.It displays foodtrucks and OCR buildings in UPenn, along with its detailed information and location via Google Maps.

## Installation
Clone the repository below and import into **Android Studio**

```bash
https://github.com/minskim0327/WherePenn.git
```

It is recommended to run the project on **Actual Android Device**, becuase some virtual emulators might not support GPS/Navigation functionality.

Below is the quick link for android phone setups for running the project!

```bash
https://www.youtube.com/watch?v=CnAnOXqxQsc&t=113s
```
## Screen-Shots
![Animated GIF-downsized_large-2](https://user-images.githubusercontent.com/51442256/65445564-838e0600-de00-11e9-884d-91d08238d18d.gif)
![Animated GIF-downsized_large-3](https://user-images.githubusercontent.com/51442256/65445568-85f06000-de00-11e9-8a4d-936567001fe6.gif)
![Animated GIF-downsized_large-6](https://user-images.githubusercontent.com/51442256/65446599-b2a57700-de02-11e9-9e7e-85283e5f6798.gif)

<img width = "300" alt = "KakaoTalk_Photo_2019-09-23-13-05-06" src = "https://user-images.githubusercontent.com/51442256/65446653-cd77eb80-de02-11e9-83b5-f961c12f4841.png">
<img width="300" alt="Screen Shot 2019-09-23 at 5 51 24 AM" src="https://user-images.githubusercontent.com/51442256/65416662-60466500-ddc6-11e9-93d9-9289046636d4.png">
<img width="300" alt="Screen Shot 2019-09-23 at 5 51 46 AM" src="https://user-images.githubusercontent.com/51442256/65416706-76ecbc00-ddc6-11e9-80c9-4f39dd99c3e7.png">
<img width="300" alt="Screen Shot 2019-09-23 at 5 51 54 AM" src="https://user-images.githubusercontent.com/51442256/65416722-7ce29d00-ddc6-11e9-8a8b-b11d18915f3d.png">
<img width="300" alt="Screen Shot 2019-09-23 at 5 52 01 AM" src="https://user-images.githubusercontent.com/51442256/65416730-80762400-ddc6-11e9-879d-c16753fea352.png">
<img width="300" alt="Screen Shot 2019-09-23 at 5 52 11 AM" src="https://user-images.githubusercontent.com/51442256/65416736-823fe780-ddc6-11e9-9666-1e0fabcf9433.png">


## Features
- Uses Tablayouts, RecyclerViews, and SearchViews for visual clarity
- Uses Google Maps to locate 16 food trucks stored as foodTruck.json
- Provides two versions of sorted RecyclerViews(ratings, location proximity)
- Let users search for building info using the API from PennLabs (API end point: https://api.pennlabs.org/buildings/search?q=hill)

## Permissions
WherePenn requires the following permissions:
- Full Network Access
- Location Permission

## Maintainers
This project is mantained by:
* [Min Seok Kim](http://github.com/minskim0327)


## Contribution
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## Project Status
There are minor issues:
1. Foodtruck Fragment - sorting foodtrucks by locations (it runs fine after double-clicked)
2. Building Fragment - need further implementation of road-views.

**Project is to be modified later, and is always welcome for updates!**
