
package net.thousandparsec.netlib.Client;

import java.util.Vector;
import net.thousandparsec.netlib.tp03.Object;
import java.util.Random;
import net.thousandparsec.netlib.Client.GameObjects.Planet;
/**
 *  Just some fake objects to be used for testing the starmap.
 * @author Brendan
 */
public class FakeObjects {
    Vector objectList;
    
    public FakeObjects(){
        Random generator = new Random();
        objectList = new Vector();
        //Lets Create some planets!
        Planet p = new Planet(-1);
        p.setId(101);
        p.setName("Planet X");
        p.setOtype(3);
        p.setPos(new Object.PosType(10,10,0));
        generator.setSeed(System.currentTimeMillis());
        objectList.addElement(p);
        for(int i = 0; i < 50; i ++){
            Planet o = new Planet(-1);
            o.setId(i);
            o.setName("Planet " + i );
            o.setOtype(3);
            
            long x = (generator.nextLong()*10000)%10000;
            long y = (generator.nextLong()*10000)%10000;
            
            if(x < 0)
                x*=-1;
            if(y<0)
                y*=-1;

            o.setPos(new Object.PosType(x, y, 0));
            o.createPlanetResource(1, 10, 25, 40);
            objectList.addElement(o);
        }
    }
    public Vector getList(){
        return objectList;
    }
}
