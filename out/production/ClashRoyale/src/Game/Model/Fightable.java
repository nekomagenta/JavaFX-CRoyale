package Game.Model;


import Debugging.a;
import Game.Model.Towers.Tower;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Fightable extends Thread{
    protected Board board;
    protected boolean alive;
    protected int hp;
    protected int damage;
    protected long hitSpeed;
    protected final double range;
    protected  Location location;
    protected final Team team;
    protected final Type type;
    protected ImageView currentImage;
    protected Direction direction;
    protected ProgressBar progressBar;
    protected final int hpPrimaryValue;

    protected double tileWidth = 976 / 35.0;
    protected double tileHeight = 549 / 18.0;
    //    private boolean isAlive;

    public Fightable(Board board, int hp, int damage, long hitSpeed, double range, Location location, Team team, Type type) {
        this.team = team;
        this.type = type;
        this.alive = true;
        this.board = board;
        this.hp = hp;
        this.damage = damage;
        this.hitSpeed = hitSpeed;
        this.range = range;
        this.location = location;
        hpPrimaryValue = hp;
        location.setEmpty(false);
    }


    public void convertProgressBarToAppropriateSize() {
        progressBar.setMaxHeight(javafx.scene.layout.Region.USE_COMPUTED_SIZE);
        progressBar.setMinHeight(javafx.scene.layout.Region.USE_COMPUTED_SIZE);
        progressBar.setMaxWidth(javafx.scene.layout.Region.USE_COMPUTED_SIZE);
        progressBar.setMinWidth(Region.USE_COMPUTED_SIZE);
        progressBar.setPrefWidth(50 * 4 / 5);
        progressBar.setPrefHeight(50 / 10);
    }

    public void setOnRightLocationBar() {
        if (this instanceof Tower){
            progressBar.setLayoutX(currentImage.getX() + 5);
            progressBar.setLayoutY(currentImage.getY() - 15);
            return;
        }
        progressBar.setLayoutX(currentImage.getX() + 1);
        progressBar.setLayoutY(currentImage.getY() + 1);
    }

    public Direction getDirection() {
        return direction;
    }

    public ImageView getCurrentImage() {
        return currentImage;
    }

    public void changeDamage(int damage) {
        this.damage += damage;
    }

    public Fightable getNearestEnemy(double range) {
        double min = range;
        Fightable nearestEnemy = null;



        LinkedList<Fightable> enemies = (team.equals(Team.A))? board.getBFightables() : board.getAFightables();
        //a.a(getClass() + " allEnemies: " + enemies);

        for (Fightable enemy: enemies){
            if (enemy == null)
                continue;

            if (location.getDistance(enemy.getLocation()) < min) {
                nearestEnemy = enemy;
                min = location.getDistance(enemy.getLocation());
            }
        }

        //if (nearestEnemy != null)
            //a.a(nearestEnemy.getClass().getSimpleName() + " is nearest to " + getClass().getSimpleName());

        return nearestEnemy;
    }

    public void changeHitSpeed(double hitSpeed) {
        this.hitSpeed += hitSpeed;
    }

    public Type getType() {
        return type;
    }

    public Team getTeam() {
        return team;
    }

    public void toGetHurt(int damage){
        a.a(getClass().getSimpleName() + " HP: " + hp + " - " + damage);
        hp -= damage;
        if (hp < 0){
            alive = false;
            System.out.println(this.getClass().getSimpleName() + " of " + team + " died.");
            board.removeFightable(this, team);


            //ERROR: IllegalStateException: Not on FX application thread; currentThread = Thread-180
            //progressBar.setProgress((double) hp / hpPrimaryValue);
        }
    }


    public boolean alive() {
        return alive;
    }

    public void endamage(Fightable fightable){
        if (fightable != null)
            fightable.toGetHurt(damage);
    }

    public Location getLocation() {
        return location;
    }

    public void rage(long duration){
        damage *= 1.4;
        hitSpeed /= 1.4;

        (new Timer()).schedule(new TimerTask() {
            @Override
            public void run() {
                damage /= 1.4;
                hitSpeed *= 1.4;
            }
        }, duration);
    }

    @Override
    public String toString() {
        return "";
    }

    public void end() {
        interrupt();
    }
}
