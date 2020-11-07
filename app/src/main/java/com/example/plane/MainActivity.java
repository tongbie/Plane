package com.example.plane;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FrameLayout root;
    private Plane plane;
    private Controller controller;
    private Background background;
    private ArrayList<Bullet> bulletList;
    private long bulletTime;
    private boolean firstBullet = true;
    private int time = 0;
    private ArrayList<Enemy> enemyList;
    private int BULLETNUMBER = 10;
    private ScoreView score;
    private ScoreView bullet_number;
    public int SCORE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        score = findViewById(R.id.score);
        bullet_number = findViewById(R.id.bullet_number);
        root = findViewById(R.id.root);
        plane = new Plane(this);
        controller = new Controller(this);
        Button fire = findViewById(R.id.fire);
        fire.setOnClickListener(this);
        background = findViewById(R.id.background);
        bulletList = new ArrayList<>();
        bulletTime = System.currentTimeMillis();
        enemyList = new ArrayList<>();
        root.addView(plane);
        root.addView(controller);
        handler.sendEmptyMessage(0x000);
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            time++;//时间
            /* 刷新视图 */
            background.invalidate();
            controller.invalidate();
            plane.invalidate();
            /* 刷新hero位置 */
            plane.setXhero(plane.getXhero() + controller.getXspeed() / 18);
            plane.setYhero(plane.getYhero() + controller.getYspeed() / 18);
            /* 产生敌人 */
            if (time % 166 == 0 || time == 0) {
                Enemy enemy = new Enemy(getApplicationContext());
                enemyList.add(enemy);
                root.addView(enemyList.get(enemyList.size() - 1));
            }
            /* 计算与自己碰撞 */
            for (int i = 0; i < enemyList.size(); i++) {
                enemyList.get(i).setTime(time / 15);//传入时间
                enemyList.get(i).invalidate();
                int X_hero_enemy = enemyList.get(i).getXEnemy() - plane.getXhero();
                int Y_hero_enemy = enemyList.get(i).getYEnemy() - plane.getYhero();
                if ((X_hero_enemy > -plane.Xlength && X_hero_enemy < plane.Xlength) && (Y_hero_enemy > -plane.Ylength && Y_hero_enemy < plane.Ylength)) {
                    root.removeView(enemyList.get(i));
                    enemyList.remove(i);
                    root.removeView(plane);
                    finishHandler.sendEmptyMessageDelayed(666, 800);
                    return false;
                }
                /* 计算与子弹碰撞 */
                for (int j = 0; j < bulletList.size(); j++) {
                    int X_bullet_enemy = enemyList.get(i).getXEnemy() - bulletList.get(j).getXbullet();
                    int Y_bullet_enemy = enemyList.get(i).getYEnemy() - bulletList.get(j).getYbullet();
                    if ((X_bullet_enemy > -plane.Xlength && X_bullet_enemy < plane.Xlength) && (Y_bullet_enemy > -plane.Ylength && Y_bullet_enemy < plane.Ylength)) {
                        root.removeView(enemyList.get(i));
                        root.removeView(bulletList.get(j));
                        enemyList.remove(i);
                        bulletList.remove(j);
                        BULLETNUMBER++;
                        SCORE += 10;
                        break;
                    }
                }
            }
            /* 回收敌人内存 */
            if (enemyList.size() > 1) {
                if (enemyList.get(0).getYEnemy() > 3000) {
                    root.removeView(enemyList.get(0));
                    enemyList.remove(0);
                    SCORE += 5;
                }
            }
            /* 回收子弹内存 */
            if (bulletList.size() > 1) {
                if (bulletList.get(0).getYbullet() < -200) {
                    root.removeView(bulletList.get(0));
                    bulletList.remove(0);
                }
            }
            /* 重置时间 */
            if (time == 1000) {
                time = 0;
                BULLETNUMBER++;
            }
            for (int i = 0; i < bulletList.size(); i++) {//刷新子弹视图
                bulletList.get(i).invalidate();
            }
            handler.sendEmptyMessageDelayed(0x000, 10);
            runOnUiThread(new Runnable() {
                @SuppressLint("SetTextI18n")
                @Override
                public void run() {
                    score.setText("Score=" + String.valueOf(SCORE));
                    bullet_number.setText("Bullet=" + String.valueOf(BULLETNUMBER));
                }
            });
            return false;
        }
    });

    @Override
    public void onClick(View view) {
        if ((System.currentTimeMillis() - bulletTime > 300 || firstBullet) && BULLETNUMBER > 0) {
            Bullet bullet = new Bullet(getApplicationContext(), plane.getXhero(), plane.getYhero() - plane.Ylength - 100);
            bulletList.add(bullet);
            root.addView(bulletList.get(bulletList.size() - 1));
            bulletTime = System.currentTimeMillis();
            firstBullet = false;
            BULLETNUMBER--;
        }
    }

    Handler finishHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == 666) {
                Intent intent = new Intent(MainActivity.this, GameOverActivity.class);
                intent.putExtra("score", String.valueOf(SCORE));
                startActivity(intent);
                finish();
            }
            return false;
        }
    });
}
