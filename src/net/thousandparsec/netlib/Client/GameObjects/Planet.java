package net.thousandparsec.netlib.Client.GameObjects;
import java.util.Vector;
/**
 * 
 * @author Brendan
 */
public class Planet extends net.thousandparsec.netlib.tp03.Object{
    /**
     * The ID of the owner of the planet, or -1 if not known or owned by
     * nobody.
     */
    private int owner_id;
    
    /**
     * A List of Planet Resources
     */
    Vector planet_resource_list;
    
    /**
     * Constructs a new Planet
     * @param owner the id of who initially owns the planet. Setting this to -1
     * means nobody, or some unknown party owns the planet.
     * @param x the X Coordinate of the Planet
     * @param y the Y Coordinate of the Planet
     * @param z the Z Coordinate of the Planet
     */
    public Planet(int owner){
        owner_id = owner;
        planet_resource_list = new Vector();
    }
    
    /**
     * Creates a new PlanetResource and adds it to the planet resource list
     * @param id the resource id
     * @param units the units of the resource that are available
     * @param minable the units of the resource that are minable
     * @param inaccessible the innaccessible units of the resource
     */
    public void createPlanetResource(int id, int units, int minable, int inaccessible){
        planet_resource_list.addElement(new PlanetResource(id, units, minable, inaccessible));
    }
    
    public Vector getResources(){
        return planet_resource_list;
    }
    
    /**
     * A Planet may have numerous different resources. A PlanetResource object
     * represents a single resource on the planet.
     */
    private class PlanetResource{
        private int resource_id;
        private int surface_resource_units;
        private int maximum_minable_units;
        private int maximum_inaccessible_units;
        
        PlanetResource(int id, int units, int minable, int inaccessible){
            resource_id = id;
            surface_resource_units = units;
            maximum_minable_units = minable;
            maximum_inaccessible_units = inaccessible;
        }
        
        /**
         * Gets the maximum units of this resource remaining which are inaccessable
         * @return maximum inaccessible resource units remaining
         */
        public int getMaximumInaccessibleUnits(){
            return maximum_inaccessible_units;
        }
        
        /**
         * The maximum units of this resource remaining which are minable
         * @return the maximum minable units
         */
        public int getMaximumMinableUnits(){
            return maximum_minable_units;
        }
        
        /**
         * Get the Resource ID
         * @return returns the Resource ID
         */
        public int getResourceID(){
            return resource_id;
        }
        
        /**
         * the units of this resource on the "surface"
         * @return returns the units of the resource on the surface
         */
        public int getSurfaceResourceUnits(){
            return surface_resource_units;
        }

    }
    
}
