package net.zerobone.zerorobo;

import net.zerobone.zerorobo.behaviour.ZeroRoboBehaviour;
import net.zerobone.zerorobo.utils.SimpleRobot;
import robocode.ScannedRobotEvent;

import java.awt.*;

public class ZeroRobo extends SimpleRobot {

    public ZeroRobo() {

        behavior = new ZeroRoboBehaviour(this);

    }

    @Override
    public void onPaint(Graphics2D g) {
        // super.onPaint(g);

        ((ZeroRoboBehaviour)behavior).onPaint(g);

    }
}