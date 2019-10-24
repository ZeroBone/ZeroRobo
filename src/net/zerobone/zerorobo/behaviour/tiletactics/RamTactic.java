package net.zerobone.zerorobo.behaviour.tiletactics;

import net.zerobone.zerorobo.behaviour.TileBehaviour;
import net.zerobone.zerorobo.behaviour.TileTactic;
import net.zerobone.zerorobo.utils.IntPoint;

import java.util.ArrayList;

public class RamTactic extends TileTactic {

    @Override
    public IntPoint getNextQuad(ArrayList<IntPoint> availableQuads, TileBehaviour context, IntPoint myQuad, IntPoint enemyQuad) {
        return availableQuads.get(0);
    }

    @Override
    public boolean acceptAvailableQuad(TileBehaviour context, IntPoint quad, IntPoint myQuad, IntPoint enemyQuad) {
        return quad.equals(myQuad);
    }

}