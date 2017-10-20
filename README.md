# Stash Challenge #

You are given a semi-complete Getty image searching app. You will be tasked with upgrading the application and adding a brand new feature! We will be looking for code quality, ability to work with existing code base, and demonstration of your android skills.

### What does the app currently do though? ###
Currently the app has a search field that you can enter something cool and it will fill the screen will a grid of images.

### Tasks ###
1. Convert the existing code base to use RxJava
1. Convert the existing code base to the Model-View-Presenter pattern
1. Add a new feature
1. Create an archive (.tar, .zip, etc.) of the codebase and return it

### So whats the new feature? ###
When a user performs a long press on an image, pop up a dialog that display the pressed image, image title, image artist, and 3 similar images. (Hint: Check out the api folder for models and requests available)

### Extra Credit ###
Create unit test for your Model-View-Presenter changes

### Notes ###
You will need to create your own set of Getty image API keys in order for your request to run. (https://developer.gettyimages.com/apps/mykeys)
Once you obtain your key you can integrate it by adding it to "getty_api_key" in the string resources file.

If you have any questions don't hesitate to reach out.
