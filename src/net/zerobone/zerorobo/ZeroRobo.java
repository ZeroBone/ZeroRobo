package net.zerobone.zerorobo;

import net.zerobone.zerorobo.behaviour.TileBehaviour;
import net.zerobone.zerorobo.behaviour.tiletactics.RandomTactic;
import net.zerobone.zerorobo.utils.SimpleRobot;

import java.awt.*;

public class ZeroRobo extends SimpleRobot {

    public ZeroRobo() {

        behaviour = new TileBehaviour(this, new RandomTactic());

    }

    @Override
    public void onPaint(Graphics2D g) {
        // super.onPaint(g);

        ((TileBehaviour) behaviour).onPaint(g);

    }
}