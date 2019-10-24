package net.zerobone.zerorobo.behaviour.tiletactics;

import net.zerobone.zerorobo.behaviour.TileBehaviour;
import net.zerobone.zerorobo.behaviour.TileTactic;
import net.zerobone.zerorobo.utils.IntPoint;

import java.util.ArrayList;

public class EscapeTactic extends TileTactic {

    private IntPoint previousPoint;

    @Override
    public IntPoint getNextQuad(ArrayList<IntPoint> availableQuads, TileBehaviour context, IntPoint myQuad, IntPoint enemyQuad) {

        int farthestQuadIndex = 0;

        int maxDistance;

        {
            IntPoint currentQuad = availableQuads.get(0);

            currentQuad = currentQuad.copy();
            currentQuad.subtract(enemyQuad);

            maxDistance = currentQuad.x * currentQuad.x + currentQuad.y * currentQuad.y;
        }

        for (int i = 1; i < availableQuads.size(); i++) {

            IntPoint currentQuad = availableQuads.get(i);

            currentQuad = currentQuad.copy();
            currentQuad.subtract(enemyQuad);

            int currentDistance = currentQuad.x * currentQuad.x + currentQuad.y * currentQuad.y;

            if (currentDistance >= maxDistance) {
                maxDistance = currentDistance;
                farthestQuadIndex = i;
            }

        }

        previousPoint = availableQuads.get(farthestQuadIndex);

        return previousPoint;

    }

    @Override
    public boolean acceptAvailableQuad(TileBehaviour context, IntPoint quad, IntPoint myQuad, IntPoint enemyQuad) {

        if (quad.equals(previousPoint)) {
            return false;
        }

        return !quad.equals(previousPoint);
    }

}