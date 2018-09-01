# Rorrim - AndroidApp

This is an Native Android Application to manage Smart Mirror System.

<br>

## About Smart Mirror System 

Rorrim is a smart system that provides personalized service with Deep-Learning based face classification.

<br>

### Structure

![structure](/img/structure.png)

You can find other program's source in..

* [Server](https://github.com/WelovePath/Rorrim.WebServer.git)
* [Mirror(python program for rpi)](https://github.com/WelovePath/Rorrim.RPI.git)

<br>

### Features

* **Face Classification** : User can sign-in to the system through face recognition and classification(based on Deep-Learning with fine tuned Google Inception V3)

* **Voice Recognition** : All functions can be executed with the voice command through google speech api

  <br>

All functions can be turned on/off by application.

<br>

**public**

* Clock : provide current time

* Weather : provide current weather according to location

* path : provide a route to your destination

  <br>

**private**

* Music : play music for each user

* Calendar : show calendar for each user

* News : show news of the appropriate topic for each user

  <br>

## How to use

### 1. Login

You can login with your google account.

<br>

### 2. Mypage

![mypage](/img/mypage.png)

1. tap '별명등록' button to register your name for Smart Mirror system.

2. tap '현재위치' button to register current location.

3. tap '카메라' or '앨범' button to register photo for Face ID.

4. tap '거울등록' button to synchronize with Mirror Iot.

   <br>

### 3. Menu

![menu](/img/calendar.png)

Chose a function to manage from the menu bar.

<br>

## Building

If you want to build from source just do

~~~
git clone https://github.com/WelovePath/Rorrim.AndroidApp.git
~~~

In Android Studio selection "Open an Existing Android Studio Project"

<br>

## Libraries used

* Data Binding
* Retrofit 2
* Glide
* Gson


## API used
* [Firebase](https://firebase.google.com/)
* [Oauth 2.0](https://oauth.net/2/)
* [Google Calendar API](https://developer.salesforce.com/page/Google_Calendar_API)
* [Google Storage API]
