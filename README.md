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

<img src = "https://user-images.githubusercontent.com/51442256/65415615-1b213380-ddc4-11e9-949e-0290b916596c.jpeg"align="left" width="200"
    hspace="10" vspace="10">
<img  src = "https://user-images.githubusercontent.com/51442256/65415659-2c6a4000-ddc4-11e9-8d3b-a677d9610e01.jpeg" align="left"
width="200"
    hspace="10" vspace="10">
<img src = "https://user-images.githubusercontent.com/51442256/65415661-2d9b6d00-ddc4-11e9-97e5-156b26644f50.jpeg"align="left"
width="200"
    hspace="10" vspace="10">
<img src = "https://user-images.githubusercontent.com/51442256/65415665-2ecc9a00-ddc4-11e9-80dc-8a690385ed65.jpeg" align="left"
width="200"
    hspace="10" vspace="10">
<img src = "https://user-images.githubusercontent.com/51442256/65415669-30965d80-ddc4-11e9-815f-6a4633d47d46.jpeg" align="left"
width="200"
    hspace="10" vspace="10">


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
