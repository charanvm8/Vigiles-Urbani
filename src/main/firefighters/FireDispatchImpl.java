package main.firefighters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import main.api.City;
import main.api.CityNode;
import main.api.FireDispatch;
import main.api.Firefighter;
import main.api.exceptions.NoFireFoundException;

public class FireDispatchImpl implements FireDispatch {
  City city;
  List<Firefighter> firefighters = new ArrayList<>();
  public FireDispatchImpl(City city) {
    this.city = city;
  }

  @Override
  public void setFirefighters(int numFirefighters) {
    for(int i=0;i<numFirefighters;i++){
      this.firefighters.add(new FirefighterImpl(this.city.getFireStation()));
    }
  }

  @Override
  public List<Firefighter> getFirefighters() {
    return this.firefighters;
  }

  @Override
  public void dispatchFirefighers(CityNode... burningBuildings) {
    if(burningBuildings==null || burningBuildings.length==0){
      return;
    }
    for(CityNode burningBuilding:burningBuildings){
      // Checking if the building is fire proof and continue
      if(!city.getBuilding(burningBuilding).isFireproof() &&
          city.getBuilding(burningBuilding).isBurning()){
        extinguishBuilding(burningBuilding);
      }
    }
  }

  private void extinguishBuilding(CityNode burningBuilding){
    Firefighter nearestFireFighter = null;
    int currentDistanceFromBuilding = Integer.MAX_VALUE;
    // Find the nearest fire fighter
    Iterator<Firefighter> itr = firefighters.iterator();
    while (itr.hasNext()){
      Firefighter currFireFighter = itr.next();
      int fighterDistanceFromBuilding = distanceFromNode(burningBuilding,currFireFighter);
      if(fighterDistanceFromBuilding<currentDistanceFromBuilding){
        nearestFireFighter = currFireFighter;
        currentDistanceFromBuilding = fighterDistanceFromBuilding;
      }
    }
    // Update the firefighter location and extinguish building
    updateAndExtinguish(nearestFireFighter,burningBuilding,currentDistanceFromBuilding);
  }

  private void updateAndExtinguish(Firefighter firefighter,CityNode burningBuilding,int distanceTraveled){
    firefighter.addDistanceTraveled(distanceTraveled);
    firefighter.setLocation(burningBuilding);
    try{
      city.getBuilding(burningBuilding).extinguishFire();
    } catch (NoFireFoundException e) {
      // logging
      System.out.println(String.format("Unable to extinguish fire : {}",e));
    }
  }

  // Assuming that the fire fighter cannot navigate diagonally
  private int distanceFromNode(CityNode building,Firefighter firefighter){
    CityNode fireFighterLocation = firefighter.getLocation();
    int xDistance = Math.abs(fireFighterLocation.getX()-building.getX());
    int yDistance = Math.abs(fireFighterLocation.getY()-building.getY());
    return xDistance+yDistance;
  }
}
