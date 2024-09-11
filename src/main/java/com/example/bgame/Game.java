package com.example.bgame;

public class Game {

    public int score()
    {
        return scoreForFrame(itsCurrentFrame);
    }
    public void add(int pins)
    {
        itsScorer.addThrow(pins);
        adjustCurrentFrame(pins);
    }

    private void adjustCurrentFrame(int pins)
    {
        if (firstThrowInFrame)
        {
            if (!adjustFrameForStrike(pins))
                firstThrowInFrame = false;
        }
        else
        {
            firstThrowInFrame=true;
            advanceFrame();
        }
    }
    private boolean adjustFrameForStrike(int pins)
    {
        if (pins == 10)
        {
            advanceFrame();
            return true;
        }
        return false;
    }
    private void advanceFrame()
    {
        itsCurrentFrame = Math.min(10, itsCurrentFrame + 1);
    }
    public int scoreForFrame(int theFrame)
    {
        return itsScorer.scoreForFrame(theFrame);
    }

    public void reset() {
        itsCurrentFrame = 0;
        firstThrowInFrame = true;
        itsScorer.reset(); // Assuming Scorer class has a reset method
    }

    private int itsCurrentFrame = 0;
    private boolean firstThrowInFrame = true;
    private final Scorer itsScorer = new Scorer();

}

