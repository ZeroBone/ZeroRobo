package net.zerobone.zerorobo.behaviour;

import net.zerobone.zerorobo.ZeroRobo;
import net.zerobone.zerorobo.utils.SimpleRobotBehavior;

import java.awt.*;

public class ZeroRoboBehaviour extends SimpleRobotBehavior {

    public ZeroRoboBehaviour(ZeroRobo robo) {
        super(robo);
    }

    @Override
    protected void start() {

        Color bodyColor = new Color(255, 35, 33);
        Color gunColor = new Color(132,132,132);
        Color radarColor = new Color(213,213,213);
        Color bulletColor = new Color(231,231,231);
        Color scanArcColor = new Color(111,111,111);

        setColors(bodyColor, gunColor, radarColor, bulletColor, scanArcColor);

    }

    @Override
    protected void execute() {

        ahead(5);

    }

}