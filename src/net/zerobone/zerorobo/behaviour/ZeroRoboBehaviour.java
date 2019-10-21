package net.zerobone.zerorobo.behaviour;

import net.zerobone.zerorobo.ZeroRobo;
import net.zerobone.zerorobo.utils.SimpleRobotBehaviour;

import java.awt.*;

public class ZeroRoboBehaviour extends SimpleRobotBehaviour {

    public ZeroRoboBehaviour(ZeroRobo robo) {
        super(robo);
    }

    @Override
    protected void start() {

        Color bodyColor = new Color(255, 35, 33);
        Color gunColor = new Color(136, 208, 255);
        Color radarColor = new Color(129, 44, 213);
        Color bulletColor = new Color(231, 96, 0);
        Color scanArcColor = new Color(57, 0,111);

        setColors(bodyColor, gunColor, radarColor, bulletColor, scanArcColor);

    }

    @Override
    protected void execute() {

        // ahead(5);

    }

}