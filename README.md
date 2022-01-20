# GalaxyTrucker



<img src="https://gitlab.informatik.uni-bremen.de/fkehlenb/galaxytrucker/-/raw/master/core/assets/logo.png" text-align="center" height="300px"/>










## Gameplay & Documentation

GalaxyTrucker is a round-based multiplayer game, created with LibGDX/Java. 

<img src="https://gitlab.informatik.uni-bremen.de/fkehlenb/galaxytrucker/-/raw/master/doc/Benutzerhandbuch/DasSpiel/Kampf/miniboss.png" text-align="center" height="400px"/>

To view the full documentation please navigate to ```/doc/BenutzerHandbuch/handbuch.pdf ```
*or just click this :* [Handbuch.pdf](https://gitlab.informatik.uni-bremen.de/fkehlenb/galaxytrucker/-/blob/5705b1b842cde8cb8638061848d737314824c39d/doc/Benutzerhandbuch/handbuch.pdf)

## Legal disclaimer
This Game was created during a University project. I don't own any of the used textures and this game isn't meant to be used for commercial purposes. GalaxyTrucker is a open-source educational project.

-------------------------

## Installation


### Requirements:

* JDK 11+ installation

### Walkthrough:

Clone the repository with git. 

```
git clone https://gitlab.informatik.uni-bremen.de/fkehlenb/galaxytrucker.git
```

After doing so, navigate into the root folder of the project and execute the following command:

``` 
.\gradlew clean desktop:dist
```

The Jarfile will be available under *desktop > build > libs* (/desktop/build/libs/)

To execute the application use the following command, or double click the Jarfile

```
java -jar desktop-1.0.jar
```
