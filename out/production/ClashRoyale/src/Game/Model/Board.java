package Game.Model;

import Game.Model.Towers.King;
import Game.Model.Towers.Queen;
import Game.Model.Towers.Tower;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Board {

    private final int length;
    private final int width;
    private Location[][] locations;
    private LinkedList<Fightable> AFightables;
    private LinkedList<Fightable> BFightables;
    private final ArrayList<Bridge> bridges;
    private final int searchFightableRange;

    public Board(HashMap<Team, Region> teams, int length, int width, ArrayList<Bridge> bridges, int searchFightableRange) {
        this.length = length;
        this.width = width;
        this.bridges = bridges;
        this.searchFightableRange = searchFightableRange;
        AFightables = new LinkedList<>();
        BFightables = new LinkedList<>();
    }


    public ArrayList<Bridge> getBridges() {
        return bridges;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getSearchFightableRange() {
        return searchFightableRange;
    }

    public LinkedList<Fightable> getAFightables() {
        return AFightables;
    }

    public LinkedList<Fightable> getBFightables() {
        return BFightables;
    }

    //    public Fightable getNearestEnemy(Fightable theFightable, double range) {
//        double min = range;
//        Fightable nearestEnemy = null;
//        LinkedList<Fightable> enemy = (theFightable.getTeam().equals(Team.A)) ? BFightables : AFightables;
//        for (Fightable fightable : enemy) {
//            if (theFightable.getLocation().getRegion().equals(fightable.getLocation().getRegion())) {
//                if (theFightable.getLocation().getDistance(fightable.getLocation()) < min) {
//                    nearestEnemy = fightable;
//                    min = theFightable.getLocation().getDistance(fightable.getLocation());
//                }
//            }
//        }
//        return nearestEnemy;
//    }

    public void removeFightable(Fightable fightable) {
        //Update screen...

        //TODO: How to to know fightable is in "myArmy" or "enemies"?
//        myArmy.remove(fightable);
//        enemies.remove(fightable);


        if (fightable instanceof Tower) {
            //Update crowns...
            if (fightable instanceof King) {
                gameOver();
            } else {
                //Activates King Tower

            }
        }
    }

    public void removeFightable(Fightable deadFightable, Team killerTeam) {
        //Update screen...

        if (killerTeam.equals(Team.A)){
            BFightables.remove(deadFightable);
        }
        else {
            AFightables.remove(deadFightable);
        }


        if (deadFightable instanceof Tower) {
            //Update crowns...
            if (deadFightable instanceof King) {
                gameOver();
            } else {
                //Activates King Tower
            }
        }


    }

    private void gameOver() {
        //Showing final result and saving game in history
    }

    public Location getNearestTower(Location location) {
        // TODO: should be this way:
        /*
        if (width / 2 < location.getX())
            return bridges.getAHead();
        else
            return bridges.getBHead();

         */

        return null;
    }


    public boolean isLocationValid(Team team, Location location){
        double y = location.getY();

        if (team == Team.A){
            if (location.getY() < 17){
                return true;
            }

            int nQueens = 0;
            for (Fightable bFightable : BFightables){
                if (bFightable instanceof Queen) {
                    nQueens++;
                }
            }

            if (nQueens == 1 && y < 19){
                return true;
            }

            if (nQueens == 0 && y < 21){
                return true;
            }
        }

        if (team == Team.B){
            if (location.getY() > 16){
                return true;
            }

            int nQueens = 0;
            for (Fightable aFightable : AFightables){
                if (aFightable instanceof Queen) {
                    nQueens++;
                }
            }

            if (nQueens == 1 && y > 18){
                return true;
            }

            if (nQueens == 0 && y > 20){
                return true;
            }
        }

        return false;
    }

}
