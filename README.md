This project was built in Android Studio using Kotlin and Google Firebase. 

When the app loads, the user is prompted to login or register. Selecting "register"
takes the user to the screen below. Selecting "login" takes the user to a similar screen
but there is no "confirm password" innput field and the button says "login" rather than "register." 
![register](https://user-images.githubusercontent.com/73101557/122858066-40b3d780-d2df-11eb-8207-c97362d7a2d4.PNG)

After logging in or registering with a valid email and password, the user is taken to the main menu, where the 
following options are displayed:
![menuOptions](https://user-images.githubusercontent.com/73101557/122858453-e8c9a080-d2df-11eb-9f4a-bafd50608f04.png)

Currently, the online multiplayer gameplay mode is still under development so selecting that option does nothing. 

When the user selects the single player option, they are prompted for a nickname and the number of questions they 
would like the game to last. After entering that information, the game begins and the first question is displayed.
The questions are randomly selected from a question bank stored in Firebase Realtime Database so they will be different in every game. 
When the user submits an incorrect answer, the selected answer turns red and the correct answer turns green as shown:
![incorrect](https://user-images.githubusercontent.com/73101557/122858772-74433180-d2e0-11eb-9d91-ad16021dd84f.PNG)

When the user submits a correct answer, the correct answer turns green. 

Upon completing a game, the user is shown their score for the game and is given the option to return to the 
main menu:
![results](https://user-images.githubusercontent.com/73101557/122858949-d00dba80-d2e0-11eb-9888-6a903047eef5.PNG)

Behind the scenes, the user's statistics are ubdated in firebase at the end of each game. 

From the main menu, the user can also elect to view their stats. When a user views their stats, they are shown their 
total games played, total questions answered, and total questions answered correctly as shown: 
![stats](https://user-images.githubusercontent.com/73101557/122859040-f7fd1e00-d2e0-11eb-8e32-ca968a6362d3.PNG)
