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
[<img src="/readme/Wallabag%20Reading%20List.png" align="left"
width="200"
    hspace="10" vspace="10">](/readme/Wallabag%20Reading%20List.png)
[<img src="/readme/Wallabag%20Article%20View.png" align="center"
width="200"
    hspace="10" vspace="10">](/readme/Wallabag%20Article%20View.png)
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
