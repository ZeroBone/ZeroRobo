package net.zerobone.zerorobo.behaviour;

import net.zerobone.zerorobo.utils.IntPoint;

import java.util.ArrayList;

public interface ITileTactic {

    public IntPoint getNextQuad(ArrayList<IntPoint> availableQuads, TileBehaviour context, IntPoint myQuad, IntPoint enemyQuad);

    public boolean acceptAvailableQuad(TileBehaviour context, IntPoint quad, IntPoint myQuad, IntPoint enemyQuad);

}