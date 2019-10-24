package net.zerobone.zerorobo.behaviour.tiletactics;

import net.zerobone.zerorobo.behaviour.TileBehaviour;
import net.zerobone.zerorobo.behaviour.TileTactic;
import net.zerobone.zerorobo.utils.IntPoint;

import java.util.ArrayList;
import java.util.Random;

public class RandomTactic extends TileTactic {

    @Override
    public IntPoint getNextQuad(ArrayList<IntPoint> availableQuads, TileBehaviour context, IntPoint myQuad, IntPoint enemyQuad) {
        return availableQuads.get(new Random().nextInt(availableQuads.size()));
    }

    @Override
    public boolean acceptAvailableQuad(TileBehaviour context, IntPoint quad) {
        return true;
    }

}