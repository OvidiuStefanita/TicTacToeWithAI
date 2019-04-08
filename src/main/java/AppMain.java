/* Tic-Tac-Toe with GUI and Advanced AI
* version 1.0
* by Ovidiu Stefanita
*
* ***created from/based on various sources***
*
* */

import app.graphics.AppFrame;

import javax.swing.*;


public class AppMain {

    public static void main(String[] args) {

        // Event-Dispatching thread for thread safety
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {

                new AppFrame(); // the constructor will start the building process of the app
            }
        });
    }
}
