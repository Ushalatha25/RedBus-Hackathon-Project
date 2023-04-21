# RedBus-Hackathon-Project
An Android application that collects data from the motion sensors of a Mobile device and displays it in a Graphical User Interface. The app detects the motion using the accelerometer and proximity sensor classes of Java and displays the fluctuating X-,Y- and Z- axes when the mobile device is being kept face down and face up, making it switch from DND (Do Not Disturb) mode to normal ringing mode. This project is built using Android Studio, Firebase as the back-end and HTML & CSS for the front-end.

## Getting Started

To get started with this project, you'll need to follow these steps:

1. Clone this repository onto your local machine.
2. Open the project in Android Studio.
3. Create a Firebase project in the Firebase console.
4. Add the `google-services.json` file to your project by following the Firebase setup instructions.
5. Build and run the project.
6.Navigate to the `Web Part of The Project` directory.
- Open the index.html file in your web browser.
- Make sure that you add your config in app.js file. 

## Dependencies

This project uses the following dependencies:

- Firebase Realtime Database

## Features
This project includes the following features:

- Detects changes in phone orientation (face up/face down) to switch from DND mode to normal ringing mode.
- Displays whether the phone is in DND mode or not in the website.
- Automatically updates the DND status in real-time database.


## Contributing

If you'd like to contribute to this project, please follow these steps:

1. Fork this repository.
2. Create a new branch for your feature/bugfix.
3. Make your changes and commit them.
4. Push your changes to your forked repository.
5. Open a pull request to this repository.

