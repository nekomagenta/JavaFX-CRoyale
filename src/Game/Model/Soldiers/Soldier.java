package Game.Model.Soldiers;

import Debugging.Deb;
import Game.Controller.GameController;
import Game.Model.*;
import javafx.application.Platform;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Soldier extends Fightable implements Card {


    protected Speed speed;
    protected final Target target;
    protected final boolean isAreaSplash;
    protected final int count;
    protected final int cost;

    protected Mode mode;


    protected long moveTime;
    protected int moveProgress;
    protected ImageView walk_closed_r;
    protected ImageView walk_open_r;
    protected ImageView walk_closed_l;
    protected ImageView walk_open_l;
    protected ImageView walk_closed_u;
    protected ImageView walk_open_u;
    protected ImageView walk_closed_d;
    protected ImageView walk_open_d;


    protected long fightTime;
    protected ImageView fight_1_r;
    protected ImageView fight_1_l;
    protected ImageView fight_1_u;
    protected ImageView fight_1_d;
    protected ImageView fight_2_r;
    protected ImageView fight_2_l;
    protected ImageView fight_2_u;
    protected ImageView fight_2_d;
    protected ImageView fight_3_r;
    protected ImageView fight_3_l;
    protected ImageView fight_3_u;
    protected ImageView fight_3_d;
    protected ImageView fight_4_r;
    protected ImageView fight_4_l;
    protected ImageView fight_4_u;
    protected ImageView fight_4_d;
    protected ImageView[] fightImageViews;
    protected double tileWidth = 976 / 35.0;
    protected double tileHeight = 549 / 18.0;
    protected double sizeOfChar = 50;
    protected GameController controller;


    public Soldier(Board board, int hp, int damage, long hitSpeed, double range, Location location, Speed speed, Target target,
                   boolean isAreaSplash, int count, int cost, Team team, Type type,GameController controller) {
        super(board, hp, damage, hitSpeed, range, location, team, type);
        this.speed = speed;
        this.target = target;
        this.isAreaSplash = isAreaSplash;
        this.count = count;
        this.cost = cost;
        fightTime = (long) hitSpeed * 1000;
        moveTime = getMoveTime();
        currentImage = new ImageView();

//        moveTime = 10* 1000;
        initializeWalkImages();
        initializeFightImages();
        if (team.equals(Team.A)) {
            currentImage = walk_open_r;
            direction = Direction.RIGHT;
        } else {
            currentImage = walk_open_l;
            direction = Direction.LEFT;
        }
        currentImage.setX(tileWidth * location.getX());
        currentImage.setY(tileHeight * location.getY());
        this.controller = controller;

    }

    public void initializeFightImages() {
        String address = null;
        fightImageViews = new ImageView[16];
        fightImageViews[0] = fight_1_r;
        fightImageViews[4] = fight_1_l;
        fightImageViews[8] = fight_1_u;
        fightImageViews[12] = fight_1_d;
        fightImageViews[1] = fight_2_r;
        fightImageViews[5] = fight_2_l;
        fightImageViews[9] = fight_2_u;
        fightImageViews[13] = fight_2_d;
        fightImageViews[2] = fight_3_r;
        fightImageViews[6] = fight_3_l;
        fightImageViews[10] = fight_3_u;
        fightImageViews[14] = fight_3_d;
        fightImageViews[3] = fight_4_r;
        fightImageViews[7] = fight_4_l;
        fightImageViews[11] = fight_4_u;
        fightImageViews[15] = fight_4_d;

        for (int i = 1; i < 5; i++) {
            if (this instanceof Barbarian) {
                address = "characters/Barbarians/Bar_Fight_" + i + ".png";
            } else if (this instanceof Archers) {
                address = "characters/Archers/Arc_Fight_" + i + ".png";
            } else if (this instanceof BabyDragon) {
                address = "characters/BabyDragon/Bab_Fight_" + i + ".png";
            } else if (this instanceof Giant) {
                address = "characters/Giant/Gia_Fight_" + i + ".png";
            } else if (this instanceof MiniPEKKA) {
                address = "characters/MiniPEKKA/Min_Fight_" + i + ".png";
            } else if (this instanceof Valkyrie) {
                address = "characters/MiniPEKKA/Min_Fight_" + i + ".png";
            } else if (this instanceof Wizard) {
                address = "characters/Wizard/Wiz_Fight_" + i + ".png";
            }

            System.out.println(address);
            if (i == 3)
                fightImageViews[i - 1] = makeRotationForms(i, address);
            else if (i == 0)
                fightImageViews[i + 3] = makeRotationForms(i, address);
            else if (i == 1)
                fightImageViews[i + 7] = makeRotationForms(i, address);
            else fightImageViews[i + 11] = makeRotationForms(i, address);
        }
    }

    public void initializeWalkImages() {
        String addressForOpen = new String();
        String addressForClosed = new String();

        if (this instanceof Barbarian) {
            addressForClosed = "characters/Barbarians/Bar_Walk_Closed.png";
            addressForOpen = "characters/Barbarians/Bar_Walk_Open.png";
        } else if (this instanceof Archers) {
            addressForClosed = "characters/Archers/Arc_Walk_Closed.png";
            addressForOpen = "characters/Archers/Arc_Walk_Open.png";
        } else if (this instanceof BabyDragon) {
            addressForClosed = "characters/BabyDragon/Bab_Walk_Open.png";
            addressForOpen = addressForClosed;
        } else if (this instanceof Giant) {
            addressForClosed = "characters/Giant/Gia_Walk_Closed.png";
            addressForOpen = "characters/Giant/Gia_Walk_Open.png";
        } else if (this instanceof MiniPEKKA) {
            addressForClosed = "characters/MiniPEKKA/Min_Walk_Closed.png";
            addressForOpen = "characters/MiniPEKKA/Min_Walk_Open.png";
        } else if (this instanceof Valkyrie) {
            addressForClosed = "characters/MiniPEKKA/Min_Walk_Closed.png";
            addressForOpen = "characters/MiniPEKKA/Min_Walk_Open.png";
        } else if (this instanceof Wizard) {
            addressForClosed = "characters/Wizard/Wiz_Walk_Closed.png";
            addressForOpen = "characters/Wizard/Wiz_Walk_Open.png";
        }
        System.out.println(addressForClosed);
        System.out.println(addressForOpen);

        for (int i = 0; i < 4; i++) {
            if (i == 3)
                walk_open_r = makeRotationForms(i, addressForOpen);
            else if (i == 0)
                walk_open_l = makeRotationForms(i, addressForOpen);
            else if (i == 1)
                walk_open_u = makeRotationForms(i, addressForOpen);
            else walk_open_d = makeRotationForms(i, addressForOpen);
        }
        for (int i = 0; i < 4; i++) {
            if (i == 3)
                walk_closed_r = makeRotationForms(i, addressForClosed);
            else if (i == 0)
                walk_closed_l = makeRotationForms(i, addressForClosed);
            else if (i == 1)
                walk_closed_u = makeRotationForms(i, addressForClosed);
            else walk_closed_d = makeRotationForms(i, addressForClosed);
        }
    }

    public ImageView makeRotationForms(int i, String address) {
        ImageView imageView = new ImageView(new Image(address));
        convertAppropriateSize(imageView);

        if (i == 0) {
            imageView.setRotationAxis(new Point3D(0, 1, 0));
            imageView.setRotate(-180);
        } else if (i == 1 || i == 2) {
            imageView.setRotationAxis(new Point3D(0, 0, 1));
            if (i == 1) {
                imageView.setRotate(-90);
            } else {
                imageView.setRotate(90);
            }
        }
        return imageView;
    }

    public void convertAppropriateSize(ImageView imageView) {
        imageView.setFitHeight(sizeOfChar);
        imageView.setFitWidth(sizeOfChar);
    }

    public Mode getMode() {
        return mode;
    }

    public int getMoveProgress() {
        return moveProgress;
    }

    @Override
    public void run() {
        while (alive) {
            LinkedList<Fightable> enemies = getNearEnemies();

            if (enemies == null) {
                move();
                Deb.print(toString() + "between two steps : " + moveTime + "seconds.");
            } else {
                fight(enemies);
                try {
                    Thread.sleep(hitSpeed);
                    Deb.print(toString() + "between two damages : " + hitSpeed + "seconds.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void move() {

        //find nearest enemy
        //if it is null go to bridge
        //if it is on bridge go over
        move(new Location(12, 12));
//        Fightable target = getNearestEnemy(board.getSearchFightableRange());
//        if (target != null) {
//            move(target.getLocation());
//            Deb.print(toString() + " moving towards the target : " + target.toString() + " in X = " + target.getLocation().getX()
//                    + " Y = " + target.getLocation().getY());
//        } else {
//            move(getNearestBridge());
//            Deb.print(toString() + " moving towards the bridge head : " + " in X = " + getLocation().getX()
//                    + " Y = " + target.getLocation().getY());
//        }
    }


    public LinkedList<Fightable> getNearEnemies() {
        LinkedList<Fightable> nearEnemies = new LinkedList<>();
        LinkedList<Fightable> enemies = (this.team.equals(Team.A)) ? board.getBFightables() : board.getAFightables();

        if (isAreaSplash) {

            for (Fightable enemy : enemies) {
                if (location.getDistance(enemy.getLocation()) <= range) {
                    if (isValidEnemy(enemy)) {
                        nearEnemies.add(enemy);
                    }
                }
            }

            if (nearEnemies.size() == 0) {
                return null;
            }
        } else {// AreaSplash == false -> we'll return the nearest enemy
            Fightable nearestEnemy = getNearestEnemy(enemies);

            if (nearestEnemy == null) {
                return null;
            }

            nearEnemies.add(nearestEnemy);
        }

        return nearEnemies;
    }


    public Fightable getNearestEnemy(LinkedList<Fightable> enemies) {
        double min = range;
        Fightable nearestEnemy = null;

        for (Fightable enemy : enemies) {
            if (location.getDistance(enemy.getLocation()) <= min) {
                if (isValidEnemy(enemy)) {
                    nearestEnemy = enemy;
                    min = location.getDistance(enemy.getLocation());
                }
            }
        }
        return nearestEnemy;
    }

    public void changeSpeed() {
        //todo : relationship between speed and numbers
    }


    public void fight(LinkedList<Fightable> targets) {

        while (alive) {
            for (Fightable target : targets) {
                target.toGetHurt(damage);
            }
        }
    }


    public void move(Location destination) {
        if (location.getDistance(destination) == 0)
            return;
        location.setEmpty(true);
        if (destination.getX() == location.getX()) {
            if (destination.getY() > location.getY()) {
                direction = Direction.DOWN;
                moveSteps();
                location = board.getLocations()[location.getX()][location.getY() + 1];
            } else {
                direction = Direction.UP;
                moveSteps();
                location = board.getLocations()[location.getX()][location.getY() - 1];
            }
        } else {
            if (destination.getX() > location.getX()) {
                direction = Direction.RIGHT;
                moveSteps();
                location = board.getLocations()[location.getX() + 1][location.getY()];
            } else {
                direction = Direction.LEFT;
                moveSteps();
                location = board.getLocations()[location.getX() - 1][location.getY()];
            }
        }
        Deb.print("new Location : X = "+location.getX()+" Y = "+location.getY());
        location.setEmpty(false);
        currentImage.setX(location.getX() * tileWidth);
        currentImage.setY(location.getY() * tileHeight);
        Deb.print("moved to  :  X = " + location.getX() + " Y = " + location.getY());
    }

    public void moveSteps() {
        try {
            for (int i = 0; i < 4; i++) {
                Deb.print("step "+i+" : progress : "+ moveProgress);
                Thread.sleep(moveTime / 4);
                prepareMoveImageView();
                moveProgress++;
            }
            moveProgress = 0;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public void prepareMoveImageView() {
        controller.clear(currentImage);
        if (direction.equals(Direction.RIGHT)) {
            currentImage = (moveProgress % 2 == 1) ? walk_closed_r : walk_open_r;
            currentImage.setY(location.getY()*tileHeight);
            currentImage.setX(location.getX() * tileWidth + moveProgress * tileWidth / 4);
        } else if (direction.equals(Direction.LEFT)) {
            currentImage = (moveProgress % 2 == 1) ? walk_closed_l : walk_open_l;
            currentImage.setY(location.getY()*tileHeight);
            currentImage.setX(location.getX() * tileWidth - moveProgress * tileWidth / 4);
        } else if (direction.equals(Direction.UP)) {
            currentImage = (moveProgress % 2 == 1) ? walk_closed_u : walk_open_u;
            currentImage.setX(location.getX()*tileWidth);
            currentImage.setY(location.getY() * tileHeight - moveProgress * tileHeight / 4);
        } else {
            currentImage = (moveProgress % 2 == 1) ? walk_closed_d : walk_open_d;
            currentImage.setX(location.getX()*tileWidth);
            currentImage.setY(location.getY() * tileHeight + moveProgress * tileHeight / 4);
        }
        controller.addElement(currentImage);
        Deb.print("Image for walk has set. direction : "+direction+" image : "+currentImage.getImage().getUrl()
        +" position : X = " +currentImage.getX()+" Y = "+currentImage.getY());
    }
//
//    public Location getNearestBridge() {
//        //TODO: should be transferred to "Board", and bridges needs some changes.
//        double min = board.getLength();
//        Location nearestBridge = null;
//
//        if (isOnBridge()) {
//            return getAnotherHead();
//        }
//
//        if (location.getRegion().equals(Region.A)) {
//            for (Bridge bridge : board.getBridges()) {
//                if (bridge.getAHead().getDistance(location) < min) {
//                    min = bridge.getAHead().getDistance(location);
//                    nearestBridge = bridge.getAHead();
//                }
//            }
//        } else {
//            for (Bridge bridge : board.getBridges()) {
//                if (bridge.getBHead().getDistance(location) < min) {
//                    min = bridge.getBHead().getDistance(location);
//                    nearestBridge = bridge.getBHead();
//                }
//            }
//        }
//        return nearestBridge;
//    }
//
//    public Location getAnotherHead() {
//        if (location.getY() == board.getBridges().get(0).getAHead().getY())
//            return (team.equals(Team.A)) ? board.getBridges().get(0).getBHead() : board.getBridges().get(0).getAHead();
//        else
//            return (team.equals(Team.A)) ? board.getBridges().get(1).getBHead() : board.getBridges().get(1).getAHead();
//    }
//
//    public boolean isOnBridge() {
//        if (location.getY() == board.getBridges().get(0).getAHead().getY()) {
//            if (location.getX() >= board.getBridges().get(0).getAHead().getX()
//                    && location.getX() <= board.getBridges().get(0).getBHead().getY())
//                return true;
//        } else if (location.getY() == board.getBridges().get(1).getAHead().getY()) {
//            if (location.getX() >= board.getBridges().get(1).getAHead().getX()
//                    && location.getX() <= board.getBridges().get(1).getBHead().getY())
//                return true;
//        }
//        return false;
//    }

    public long getMoveTime() {
        // TODO
        if (speed.equals(Speed.SLOW))
            return 3 * 1000;
        else if (speed.equals(Speed.MEDIUM))
            return 2 * 1000;
        else
            return 1 * 1000;
    }

    public void die() {

        //This method is to short, we can put it in the end of "run method".
        board.removeFightable(this, team);

        //todo : in game controller delete character.

    }

    public boolean isValidEnemy(Fightable enemy) {

        Type enemyType = enemy.getType();

        if (target.equals(Target.GROUND) && enemyType.equals(Type.AIR))
            return false;

        if (target.equals(Target.BUILDINGS) && !enemy.getType().equals(Type.BUILDING))
            return false;

        return true;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public void rage(long duration) {
        damage *= 1.4;
        hitSpeed /= 1.4;
        // TODO speed?

        (new Timer()).schedule(new TimerTask() {
            @Override
            public void run() {
                damage /= 1.4;
                hitSpeed *= 1.4;
            }
        }, duration);
    }
}
