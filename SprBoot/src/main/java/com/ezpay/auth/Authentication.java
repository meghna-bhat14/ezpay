//package com.ezpay.auth;
//
//
//import java.util.Timer;
//import java.util.TimerTask;
//
//public class Authentication {
//    private boolean sessionActive;
//    private Timer timer;
//
//    public Authentication() {
//        this.sessionActive = false;
//    }
//
//    public void startSession() {
//        this.sessionActive = true;
//        System.out.println("Session started.");
//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                if (sessionActive) {
//                    endSession();
//                    System.out.println("Session expired due to inactivity.");
//                    return;
//                }
//            }
//        }, 2*60 * 1000); //2 mins kept for now 
//    }
//
//    public void endSession() {
//        sessionActive = false;
//        if (timer != null) {
//            timer.cancel();
//        }
//        System.out.println("Session ended.");
//        return;
//    }
//
//    public boolean isSessionActive() {
//        return sessionActive;
//    }
//}
//

package com.ezpay.auth;

import java.util.Timer;
import java.util.TimerTask;

import org.springframework.stereotype.Component;

/**
 * Manages user authentication sessions.
 * This class handles the starting and ending of user sessions, 
 * including automatic session expiration due to inactivity.
 * 
 * @author Meghna Bhat
 * @date 02-09-2024
 */
@Component
public class Authentication {
    private boolean sessionActive; // Indicates whether the session is currently active
    private Timer timer; // Timer to schedule automatic session expiration

    /**
     * Constructor to initialize the Authentication object.
     * Sets the session to inactive initially.
     */
    public Authentication() {
        this.sessionActive = false;
    }

    /**
     * Starts a new session and schedules it to expire after 2 minutes of inactivity.
     */
    public void startSession() {
        this.sessionActive = true;
        System.out.println("Session started.");
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (sessionActive) {
                    endSession();
                    System.out.println("Session expired due to inactivity.");
                    return;
                }
            }
        }, 2*60 * 1000); // Schedule expiration after 2 minutes
    }

    /**
     * Ends the current session and cancels any pending expiration timer.
     */
    public void endSession() {
        sessionActive = false;
        if (timer != null) {
            timer.cancel();
        }
        System.out.println("Session ended.");
        return;
    }

    /**
     * Checks if the session is currently active.
     * 
     * @return true if the session is active, false otherwise
     */
    public boolean isSessionActive() {
        return sessionActive;
    }
}

