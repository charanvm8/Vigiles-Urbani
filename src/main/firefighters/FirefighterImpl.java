package main.firefighters;

import main.api.Building;
import main.api.CityNode;
import main.api.Firefighter;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class FirefighterImpl implements Firefighter {
  CityNode location;
  int distanceTravelled = 0;

  public FirefighterImpl(Building fireStation){
    this.location = fireStation.getLocation();
  }

  @Override
  public CityNode getLocation() {
    return location;
  }

  public void setLocation(CityNode node){
    location = node;
  }

  @Override
  public int distanceTraveled() {
    return distanceTravelled;
  }

  public void addDistanceTraveled(int distance){
    distanceTravelled+=distance;
  }

}
