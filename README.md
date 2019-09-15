# Occuli
Visual help for the visually impaired

## Description:
Identity theft is a problem that affects almost everybody, but the visually impaired is affected more by it.<br />
Our android application aims to solve this problem by using face recognition and object detection.
## What it does:
This is the general flow of our app:
The visually impaired person wears the phone like an id card with the screen facing outside. Earphones are also connected to the app.
* #### Detection mode:
By default, the app is in detection mode.
If a known Person approaches the visually impaired person, the camera picks up the persons face and sends the person’s name as an audio message to the visually impaired person.
* #### Recognition Mode:
If an unknown person approaches the visually impaired person, the visually impaired person clicks the volume down button(switches to recognition mode) and gives the phone to the unknown person who would then state his name and then the phone’s camera will scan his face and add it to the list of known faces.
* #### Sentry mode:
Another important feature of this app is the object detection built into it. When the visually impaired person clicks the volume up button, the app switches to sentry mode in which this person can point to one direction and take a photo and then the app would send an audio message explaining about all the things in that direction. (ex: There are 2 persons in that direction)
## Flowchart:
![Flowchart](https://i.imgur.com/tcwB3r9.jpg)
## Installation:
#### For Sentry Mode:
First, clone the repo,<br />
``` $git clone https://github.com/bnair2001/oculli.git```<br />
<br />
Then run,<br />
``` $pip3 install requirements```<br />
<br />
``` $pip3 install torchvision```<br />
<br />
&nbsp;&nbsp;&nbsp;   if the above dosent work then use,<br />
&nbsp;&nbsp;&nbsp;   ```$pip3 --no-cache-dir install torchvision```<br/>
<br />
Then to run the API,<br />
```$flask run```<br />
<br />
#### For Detection mode and Recognition mode:
...
<br />

## APK:
[Drive link to apk]()

## Credits:
[Steve Paul](https://github.com/ST2-EV),&nbsp; [Bharath Nair](https://github.com/bnair2001),&nbsp; [Matt Wang](https://github.com/mooosu)
