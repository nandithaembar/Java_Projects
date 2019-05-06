
/**
 * Write a description of class BFSearch here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DFSearch
{
    public int expansionCount;
    Map m;
    Location init;
    String initLoc, destLoc;
    int lim;
    Frontier f = new Frontier(); //This would be the "Set"
    Waypoint w = new Waypoint();
    Frontier explored = new Frontier();
    public DFSearch(Map graph, String initialLoc, String destination, int limit){
        m = graph;
        initLoc = initialLoc;
        destLoc = destination;
        lim = limit;
    }
    
    public Waypoint search(boolean check){
        expansionCount = 0;
        Frontier f = new Frontier();
        Frontier explored = new Frontier();
        Location startLoc = m.findLocation(initLoc);
        Waypoint startWP = new Waypoint(startLoc);
        
        init = m.findLocation(initLoc);
        f.addToBottom(new Waypoint(init));
        Waypoint current;
        Waypoint node;
        //Exactly the same code as breadth first search except for except we put the waypoint to the top of the set. 
        if(check == false){
            while(!f.isEmpty()){
                current = f.removeTop();
                if(current.isFinalDestination(destLoc)){
                    return current;
                }
                
                if (current.depth >= lim){
                    return null;
                }
                current.expand();
                expansionCount++;
                for(Waypoint w: current.options){
                    f.addToTop(w);
                }

            }
        }
        else{
            while(!f.isEmpty()){
                current = f.removeTop();
                explored.addToBottom(current);
                if(current.isFinalDestination(destLoc)){
                    return current;
                }
                
                if (current.depth >= lim){
                    return null;
                }
                current.expand();
                expansionCount++;
                for(Waypoint w: current.options){
                    if(!explored.contains(w) && !f.contains(w)){
                        f.addToTop(w);
                    }
                }
               
            }
        }
        System.out.println("Frontier empty");
        return null;
    }
}
