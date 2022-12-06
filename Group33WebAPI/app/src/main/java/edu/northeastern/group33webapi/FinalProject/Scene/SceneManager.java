package edu.northeastern.group33webapi.FinalProject.Scene;

import android.graphics.Canvas;

import java.util.ArrayList;

public class SceneManager {
    private ArrayList<Scene> scenes = new ArrayList<>();
    public static int ACTIVE_SCENE;

    public SceneManager(){
        ACTIVE_SCENE = 0;
        scenes.add(new GamePlayScene(this));
    }

    public ArrayList<Scene> getScenes() {
        return scenes;
    }

    public void update(){
        scenes.get(ACTIVE_SCENE).update();
    }

    public void draw(Canvas canvas){
        scenes.get(ACTIVE_SCENE).draw(canvas);
    }




}