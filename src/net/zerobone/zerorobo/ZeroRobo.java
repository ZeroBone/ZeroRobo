package net.zerobone.zerorobo;

import net.zerobone.zerorobo.behaviour.ZeroRoboBehaviour;
import net.zerobone.zerorobo.utils.SimpleRobot;

public class ZeroRobo extends SimpleRobot {

    public ZeroRobo() {

        behavior = new ZeroRoboBehaviour(this);

    }

}