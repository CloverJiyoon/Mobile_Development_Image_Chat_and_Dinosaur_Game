package edu.northeastern.group33webapi.FinalProject;

import android.content.Context;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.view.SurfaceHolder;

import edu.northeastern.group33webapi.FinalProject.Scene.GamePlayScene;
import edu.northeastern.group33webapi.FinalProject.Scene.SceneManager;
import edu.northeastern.group33webapi.R;

public class MainThread extends Thread {
    public static final int MAX_FPS = 30;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;
    public Context context;


//    public int gameState;


    public void setRunning(boolean running) {
        this.running = running;
    }

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel, Context context) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
        this.context = context;
    }

    @Override
    public void run() {
        long startTime;
        long timeMillis = 1000 / MAX_FPS;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000 / MAX_FPS;
//        if (gamePanel.audio.isSoundOn) {
//            gamePanel.audio.bgm.setLooping(true);
//            gamePanel.audio.bgm.start();
//        }

        while (running) {
            startTime = System.nanoTime();
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                    GamePlayScene gamePlayScene = (GamePlayScene) gamePanel.sceneManager.getScenes().get(gamePanel.sceneManager.ACTIVE_SCENE);
                    if (gamePlayScene.gameOver) {
                        gamePanel.audio.bgm.stop();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;
            try {
                if (waitTime > 0)
                    this.sleep(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == MAX_FPS) {
                averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                //    System.out.println(averageFPS);
            }
        }

    }
}
