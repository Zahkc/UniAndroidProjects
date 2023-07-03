package com.zb147.quizforkids;

import android.app.Application;

public class CurrentUser extends Application {
    private String currentUsername;
    private DatabaseHelper db = new DatabaseHelper(this, null, 1);
    private int currentUserPoints;

    public String getUsername(){
        return(this.currentUsername);
    }

    public void setUsername(String newUser){
        this.currentUsername = newUser;
    }

    public void logout(){
        currentUsername = null;
    }


    public int getUserPoints(){
        return this.currentUserPoints;
    }


    public void updateUserPoints(){
        db.updateUserPoints(this.currentUsername);
        currentUserPoints = db.getUserPoints(this.currentUsername);
    }
}
