# Currency Converter

A currency converter software application in Java, implementing with [JavaFX](https://openjfx.io/) and testing with [JUnit](https://junit.org/junit4/) and [fxTest](https://github.com/TestFX/TestFX).

## Usage

Firstly, run the command `gradle build`. Note it will run the test automatically. If you don't want it, please run `gradle build -x test`.

Then you can run the program by `gradle run`.

You can test it by `gradle test`. If you want to have a test report, you can run `gralde test jacocoTestReport`. The report locates in `build/jacocoHtml/index.html`.

## Demo

### Auto Convert

- User enter the main  window of Currency Converter and click the `convert` button in the upper left corner to convert currency.

   <a href="https://sm.ms/image/1BX4xzGWTP2aoQy" target="_blank"><img src="https://i.loli.net/2020/10/09/1BX4xzGWTP2aoQy.png" ></a>

- User input the the amount of currency he wants to convert into the middle blue box,  and the output amount will come out at the same time as user input.

   <a href="https://sm.ms/image/S4UGy8nE1IvskuV" target="_blank"><img src="https://i.loli.net/2020/10/10/S4UGy8nE1IvskuV.png" ></a>

- User press the delete key on the keyboard to reset the original amount to 0, and then input a new amount to convert.

   <a href="https://sm.ms/image/JwSPjZpTlbeCBcg" target="_blank"><img src="https://i.loli.net/2020/10/10/JwSPjZpTlbeCBcg.png" ></a>

- User input letter, symbols or more than one decimal point. There is no reaction in middle blue box.

- User click the button in the blue box to the right of the center, then choose a new currency from the list to convert.

   <a href="https://sm.ms/image/UjNGQFvkytBHa6u" target="_blank"><img src="https://i.loli.net/2020/10/10/UjNGQFvkytBHa6u.png" ></a>

### Display History Table

- User click the button `History` on the left to get history table.

   <a href="https://sm.ms/image/ZyDkMGB2zKV1Jv8" target="_blank"><img src="https://i.loli.net/2020/10/10/ZyDkMGB2zKV1Jv8.png" ></a>

- User choose two currencies which their exchange rate user need, then choose the start and end date from the list. The history table will show in the previous blank box.

   <a href="https://sm.ms/image/JakIcqFOWUTr2BE" target="_blank"><img src="https://i.loli.net/2020/10/10/JakIcqFOWUTr2BE.png" ></a>

   <a href="https://sm.ms/image/7YHR3KeknfW2EJM" target="_blank"><img src="https://i.loli.net/2020/10/10/7YHR3KeknfW2EJM.png" ></a>

### Feature for Admin Only

- User separately click three buttons `Add Currency`, `Add Rate` and `Change Pop` without admin login, after each click, there is a waring pop-up in the middle of the window.

   <a href="https://sm.ms/image/ta2blJECkXVPDWq" target="_blank"><img src="https://i.loli.net/2020/10/10/ta2blJECkXVPDWq.png" ></a>

- User click the button `Admin `on the left bottom corner, then there is a login pop-up in the middle of the window.

   <a href="https://sm.ms/image/4Heh9nFjkwyVBQu" target="_blank"><img src="https://i.loli.net/2020/10/10/4Heh9nFjkwyVBQu.png" ></a>

- User input wrong password and click the button `login`, then there is a error pop-up in the middle of the window.

   <a href="https://sm.ms/image/3qXroJPSdOF2ufM" target="_blank"><img src="https://i.loli.net/2020/10/10/3qXroJPSdOF2ufM.png" ></a>

   <a href="https://sm.ms/image/HjzGSxUvbsd8pBV" target="_blank"><img src="https://i.loli.net/2020/10/10/HjzGSxUvbsd8pBV.png" ></a>

- User click the button `Try Again` and input the correct password `admin`, then return to the main window.

   <a href="https://sm.ms/image/jB6Yn9clkEvwMKJ" target="_blank"><img src="https://i.loli.net/2020/10/10/jB6Yn9clkEvwMKJ.png" ></a>

   <a href="https://sm.ms/image/QcOEhSM7UVB935u" target="_blank"><img src="https://i.loli.net/2020/10/10/QcOEhSM7UVB935u.png" ></a>

### Add Currency Rate

- User click the button `Add Rate` on the left of the window, then there is a pop-up in the middle of window.

- User choose two currencies, input today`s exchange rate and click the button 'Add' at the bottom of window, then there is a message pop-up display in the middle of window.

   <a href="https://sm.ms/image/BlEDHUbPtKYdjaI" target="_blank"><img src="https://i.loli.net/2020/10/10/BlEDHUbPtKYdjaI.png" ></a>

   <a href="https://sm.ms/image/lwsyDGiVrEJP8qH" target="_blank"><img src="https://i.loli.net/2020/10/10/lwsyDGiVrEJP8qH.png" ></a>

### Add Currency

- User click the button `Add Currency` on the left of the window, then there is a pop-up displays the middle of window.

   <a href="https://sm.ms/image/ZSMKD6OTxrbuwP3" target="_blank"><img src="https://i.loli.net/2020/10/10/ZSMKD6OTxrbuwP3.png" ></a>

- User input the name of Currency in the pop-up and click the button `Add`, the message pop-p shows it has successful added.

   <a href="https://sm.ms/image/AuZmPheWfpajTrz" target="_blank"><img src="https://i.loli.net/2020/10/10/AuZmPheWfpajTrz.png" ></a>

###  Change Popular Currency

- User click the button `Change Pop` on the left of the window, then there is a pop-up displays the middle of window.

- User choose the a current currency which want to be changed and the new popular Currency, then click the button `Change`

   <a href="https://sm.ms/image/ylGEuTtjzVMsIfx" target="_blank"><img src="https://i.loli.net/2020/10/10/ylGEuTtjzVMsIfx.png" ></a>

   - There is a pop-up display in the middle of the window.

      <a href="https://sm.ms/image/ErkZFGuN2DKmOtx" target="_blank"><img src="https://i.loli.net/2020/10/10/ErkZFGuN2DKmOtx.png" ></a>

   - The popular currency window has changed.

      <a href="https://sm.ms/image/4vZpI7ymYD6QAg2" target="_blank"><img src="https://i.loli.net/2020/10/10/4vZpI7ymYD6QAg2.png" ></a>

### Logout

- User click the button `Logout` on the left bottom of the window, change to the normal user.

- User separately click three buttons `Add Currency`, `Add Rate` and `Change Pop` without admin login, after each click, there is a waring pop-up in the middle of the window.

   <a href="https://sm.ms/image/ta2blJECkXVPDWq" target="_blank"><img src="https://i.loli.net/2020/10/10/ta2blJECkXVPDWq.png" ></a>

> **NOTE:** check [this YouTube link](https://www.youtube.com/watch?v=XTlN92OxlHE) to see the whole process of this demo.
