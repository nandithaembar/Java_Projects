
/**
 * Write a description of class BFSearch here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BFSearch{
    public int expansionCount;
    Map m;
    Location init;
    String initLoc, destLoc; //save them as a string rather than a location.
    int lim;
    Frontier f = new Frontier(); //This would be the "Set"
    Frontier explored = new Frontier(); //For the explored locations
    Waypoint w = new Waypoint();
    public BFSearch(Map graph, String initialLoc, String destination, int limit){
        m = graph;
        initLoc = initialLoc;
        destLoc = destination;
        lim = limit;
    }
    //Repeated state checking asks to check if any of the state
    public Waypoint search(boolean check){
        //use the map to find the location so that it has children to expand to.
        expansionCount = 0;
        Frontier f = new Frontier();
        Frontier explored = new Frontier();
        Location startLoc = m.findLocation(initLoc);
        Waypoint startWP = new Waypoint(startLoc);
        
        init = m.findLocation(initLoc);  
        f.addToBottom(new Waypoint(init));
        //explored.addToBottom(new Waypoint(init));
        Waypoint current;
        Waypoint node;
        expansionCount = 0;
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
                    f.addToBottom(w);
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
                        f.addToBottom(w);
                    }
                }               
            }
        }
        System.out.println("Frontier empty");
        return null;
    }
}
