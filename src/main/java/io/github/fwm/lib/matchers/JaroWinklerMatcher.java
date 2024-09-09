package io.github.fwm.lib.matchers;

import org.apache.commons.text.similarity.JaroWinklerSimilarity;

public class JaroWinklerMatcher implements SimilarityMatcher{
    public static final JaroWinklerSimilarity jaroWinklerSimilarity = new JaroWinklerSimilarity();
    @Override
    public double getDistance(String input, String candidate) {
        return jaroWinklerSimilarity.apply(input, candidate);
    }
}
