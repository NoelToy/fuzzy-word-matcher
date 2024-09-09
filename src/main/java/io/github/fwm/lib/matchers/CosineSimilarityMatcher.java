package io.github.fwm.lib.matchers;

import org.apache.commons.text.similarity.CosineSimilarity;

import java.util.HashMap;
import java.util.Map;

public class CosineSimilarityMatcher implements SimilarityMatcher{
    private static final CosineSimilarity COSINE_SIMILARITY = new CosineSimilarity();
    @Override
    public double getDistance(String input, String candidate) {
        Map<CharSequence, Integer> inputVector = toFrequencyMap(input);
        Map<CharSequence, Integer> candidateVector = toFrequencyMap(candidate);
        return COSINE_SIMILARITY.cosineSimilarity(inputVector, candidateVector);
    }
    private Map<CharSequence, Integer> toFrequencyMap(String text) {
        Map<CharSequence, Integer> frequencyMap = new HashMap<>();
        for (char ch : text.toCharArray()) {
            frequencyMap.put(String.valueOf(ch), frequencyMap.getOrDefault(String.valueOf(ch), 0) + 1);
        }
        return frequencyMap;
    }
}
