package io.github.fwm.lib.matchers;

public interface SimilarityMatcher {
    double getDistance(String input,String candidate);
}
