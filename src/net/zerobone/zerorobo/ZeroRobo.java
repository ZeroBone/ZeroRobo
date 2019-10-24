package net.zerobone.zerorobo;

import net.zerobone.zerorobo.behaviour.TileBehaviour;
import net.zerobone.zerorobo.behaviour.tiletactics.*;
import net.zerobone.zerorobo.utils.SimpleRobot;

import java.awt.*;

public class ZeroRobo extends SimpleRobot {

    public ZeroRobo() {

        behaviour = new TileBehaviour(this, new RandomTactic());
        // behaviour = new TileBehaviour(this, new StraightRandomTactic());
        // behaviour = new TileBehaviour(this, new CircleTactic());
        // behaviour = new TileBehaviour(this, new RamTactic());

    }

    @Override
    public void onPaint(Graphics2D g) {
        // super.onPaint(g);

        ((TileBehaviour) behaviour).onPaint(g);

    }
}