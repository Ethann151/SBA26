import java.awt.Color;

import ihs.apcs.spacebattle.*;
import ihs.apcs.spacebattle.commands.*;

public class Spaceship extends BasicSpaceship {
    public static void main(String[] args)
    {
        TextClient.run("10.56.98.121", new Spaceship());
    }

    @Override
    public RegistrationData registerShip(int numImages, int worldWidth, int worldHeight)
    {
        return new RegistrationData("HAR[PER IS LAME", new Color(127, 0, 255), 0);
    }

    @Override
    public ShipCommand getNextCommand(BasicEnvironment env)
    {
        return new IdleCommand(0.1);
    }
}