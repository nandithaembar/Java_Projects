public class AStarSearch
{
    // instance variables - replace the example below with your own
    int limit, expansionCount;
    String initialLoc, destinationLoc;
    Map graph;
    Location init;

     */
    public AStarSearch(Map graph, String initialLoc, String destinationLoc, int limit)
    {
        this.graph = graph;
        this.initialLoc = initialLoc;
        this.destinationLoc = destinationLoc;
        this.limit = limit;
    }
    

    public Waypoint search(boolean check){
        expansionCount = 0;
        //Create a new sorted frontier and a frontier containing explored waypoints
        SortedFrontier sf = new SortedFrontier(SortBy.f);
        SortedFrontier explored = new SortedFrontier(SortBy.f);
        //Initialize start location and put it in the sorted frontier        
        Location destLoc = graph.findLocation(destinationLoc);
        GoodHeuristic h = new GoodHeuristic(graph, destLoc);
        Waypoint current;
        init = graph.findLocation(initialLoc);
        sf.addSorted(new Waypoint(init));
        if(check == false){
            while(!sf.isEmpty()){
                //Initialize the current value to the top function
                current = sf.removeTop();
                //Check if the current value is the final destination or if the depth passes the limit
                if(current.isFinalDestination(destinationLoc)){
                    return current;
                }
                if (current.depth >= limit){
                    return null;
                }
                //If not then expand the current and increase expansion count, using the heuristic
                current.expand(h);
                expansionCount++;
                for(Waypoint w: current.options){
                    sf.addSorted(w);
                }
            }
        }
        else{
            //Repeated state checking is true so the while loop is similar to the previous one
            while(!sf.isEmpty()){
                //Save the top as current
                current = sf.removeTop();
                if(current.isFinalDestination(destinationLoc)){
                    return current;
                }
                if(current.depth >= limit){
                    return null;
                }
                //If we're not at the final destination then expand the current way, incrementing current expansion count
                current.expand(h);
                expansionCount++;
                //Add the current waypoint to the explored list.
                explored.addSorted(current);
       
                //Check if the explored frontier contains the waypoint we are at, if they do, do not add it, if they 
                //do not then compare to see if this waypoint has a lower partial path cost than the previous element.
                for(Waypoint w: current.options){
                    if(!explored.contains(w) && !sf.contains(w)){
                        sf.addSorted(w);
                    }
                    else if(sf.contains(w) && sf.find(w).partialPathCost > w.partialPathCost){
                        sf.remove(sf.find(w));
                        sf.addSorted(w);
                    }
                }
            }
        }
        return null;
    }
}
