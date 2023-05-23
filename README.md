# UPOL-TV-Remote-App

Simple Android app made in Kotlin and MVVM architecture. This app can control TVs by using mobile phone's IR blaster and it only works on phones that have this piece of hardware. If the phone has no IR port, the user is notfied about it.

## Using the remote control

The user can add a new remote control by tapping the '+' button in the bottom right corner. Once name and TV brand are filled, a new remote control is added to local database and will appear on the app's home screen. If the user doesn't input any values, default values will be used.
After tapping on any of the remotes added by user, an activity with five remote buttons, 'delete' button and 'back' button will appear.

## Buttons

There are only 5 buttons, but more buttons can be easily added by following these steps:
1. Add the button to the UI in activity_remote.xml
2. Add ClickListener to RemoteActivity.kt
3. Add button name to res\values\button_info.xml
4. Add button to db (main\assets\remote_transmission_data.db) for all existing TV brands.

This process will be easier in the future once I get my hands on a usable databse of remote codes.

## Dark / Light mode

The user can switch between light and dark modes by tapping on the button in the top right corner. Value is stored in shared preferences.
