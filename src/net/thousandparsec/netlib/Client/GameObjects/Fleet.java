/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.thousandparsec.netlib.Client.GameObjects;

import java.util.Vector;
/**
 *
 * @author Brendan
 */
public class Fleet extends net.thousandparsec.netlib.tp03.Object {
    /**
     * Fleets have a type of 4
     */
    
    /**
     * The owner of the fleet.
     */
    private int owner_id;
    
    /**
     * A list of the ships in this fleet.
     */
    private Vector ships_list;
    
    /**
     * Current fleet damage
     */
    private int damage;
    
    public Fleet(int owner){
        super.setOtype(4);
        owner_id = owner;
        ships_list = new Vector();
    }
    
    public int getOwner(){
        return owner_id;
    }
    public Vector getShips(){
        return ships_list;
    }
    public int getDamage(){
        return damage;
    }
    
    public void createShip(int type, int number){
        ships_list.addElement(new Ship(type, number));
    }

    public class Ship {
        int ship_type;
        int number_of_ships;
        
        Ship(int type, int number){
            ship_type = type;
            number_of_ships = number;
        }
        
        public int getShipType(){
            return ship_type;
        }
        /**
         * Gets the number of ships
         * @return returns the number of ships of this type
         */
        public int getNumberofShips(){
            return number_of_ships;
        }
        
        public void setShipType(int type){
            ship_type=type;
        }
        public void setNumberOfShips(int number){
            number_of_ships = number;
        }
    }
}
