import java.awt.Color;

import ihs.apcs.spacebattle.*;
import ihs.apcs.spacebattle.commands.*;

public class Spaceship extends BasicSpaceship {
    public int i = 1;
    public int worldWidth;
    public int worldHeight;
    public Point midpoint;
    public boolean killHarper = false;
    public static void main(String[] args)
    {
        TextClient.run("10.56.98.121", new Spaceship());
    }

    @Override
    public RegistrationData registerShip(int numImages, int worldWidth, int worldHeight)
    {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.midpoint = new Point(this.worldWidth/2, this.worldHeight/2);
        return new RegistrationData("KILL ALL HARPER", new Color(127, 0, 255), 0);
    }

    @Override
    public ShipCommand getNextCommand(BasicEnvironment env)
    {
        BasicGameInfo gameInfo = env.getGameInfo();
        ObjectStatus ship = env.getShipStatus();
        System.out.println(ship.getOrientation());
        
        if(i == 0) {
           i++;
           System.out.println("Turning...");
           if(ship.getPosition().getAngleTo(this.midpoint) - ship.getOrientation() > 180) {
              return new RotateCommand(ship.getPosition().getAngleTo(this.midpoint) - ship.getOrientation() -360);
           } else {
              return new RotateCommand(ship.getPosition().getAngleTo(this.midpoint) - ship.getOrientation());
           }
        }
        else if(i == 1) {
           i++;
           System.out.println("Scanning for Harpers...");
           return new RadarCommand(5);
        }
        else if(i == 2) {
           i++;
           java.util.List<ObjectStatus> results = env.getRadar();
           System.out.print("Radar results [ ");
           if(results != null) {
              for(int i = 0; i < results.size(); i++) {
                 System.out.print(results.get(i)+" ");
                 if(results.get(i).getName() != null && ((results.get(i).getName().toLowerCase().contains("c") && results.get(i).getName().toLowerCase().contains("h") && results.get(i).getName().toLowerCase().contains("u") && results.get(i).getName().toLowerCase().contains("d")) || results.get(i).getName().toLowerCase().contains("destroyer") )) {
                    this.midpoint = results.get(i).getPosition();
                    System.out.println("]\nHarper found, aiming...");
                    //Target harpers id
                    System.out.println("ID: "+results.get(i).toString().substring(results.get(i).toString().substring(results.get(i).toString().indexOf("MASS")).indexOf("ID=")+results.get(i).toString().indexOf("MASS"), results.get(i).toString().substring(results.get(i).toString().indexOf("MASS")).indexOf(", MAXENERGY")+results.get(i).toString().indexOf("MASS")));
                    killHarper = true;
                    if(ship.getPosition().getAngleTo(this.midpoint) - ship.getOrientation() > 180) {
                       return new RotateCommand(ship.getPosition().getAngleTo(this.midpoint) - ship.getOrientation() -360);
                    } else {
                       return new RotateCommand(ship.getPosition().getAngleTo(this.midpoint) - ship.getOrientation());
                    }
                 }
              }
           }
        System.out.print("]\n");
        return new ThrustCommand('B', 1, 1);
        }
        else if((i < 6) && killHarper) {
           i++;
           System.out.println("Killing Harper...");
           return new FireTorpedoCommand('F');
        }
        else if(i < 5) {
           i++;
           System.out.println("Thrusting...");
           return new ThrustCommand('B', 1, 1);
        }
        else {
           i = 1;
           killHarper = false;
           System.out.println("Thrusting...");
           return new ThrustCommand('B', 1, 1);
           //System.out.println("Braking...");
           //return new BrakeCommand(0);
        }
    }
}