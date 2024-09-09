package io.github.fwm;

import io.github.fwm.lib.bk_tree.BKTreeNode;
import io.github.fwm.lib.enums.MatchType;
import io.github.fwm.lib.exceptions.ThresholdValueException;
import io.github.fwm.lib.exceptions.ToleranceValueException;
import io.github.fwm.lib.matchers.CosineSimilarityMatcher;
import io.github.fwm.lib.matchers.JaroWinklerMatcher;
import io.github.fwm.lib.matchers.SimilarityMatcher;

import java.util.List;

public class WordMatcher {
    private final SimilarityMatcher similarityMatcher;
    private final BKTreeNode root;
    private final double tolerance;
    private final double threshold;
    private final String defaultValue;

    private WordMatcher(BKTreeNode root, double tolerance, String defaultValue, SimilarityMatcher similarityMatcher, double threshold) {
        this.root = root;
        this.tolerance = tolerance;
        this.defaultValue = defaultValue;
        this.similarityMatcher = similarityMatcher;
        this.threshold = threshold;
    }

    public void insert(String word) {
        this.root.insert(word, this.similarityMatcher);
    }

    public String findBestMatch(String searchString) {
        String bestMatch = this.root.search(searchString, this.similarityMatcher, this.tolerance);
        double bestDistance = this.similarityMatcher.getDistance(searchString, bestMatch);
        if (bestDistance < this.threshold) {
            return this.defaultValue;
        }
        return bestMatch;
    }

    public static class WordMatcherBuilder {
        private SimilarityMatcher similarityMatcher;
        private final BKTreeNode root;
        private final List<String> candidates;
        private final MatchType matchType;
        private double tolerance = 0.85;
        private double threshold = 0.6;
        private String defaultValue = null;

        public WordMatcherBuilder(List<String> candidates, MatchType matchType) {
            this.candidates = candidates;
            this.matchType = matchType;
            this.root = this.buildBKTree();
        }

        public WordMatcherBuilder setTolerance(double tolerance) {
            if (this.isValidRange(tolerance)) {
                throw new ToleranceValueException("Tolerance value range should be between 0.0 and 1.0.");
            } else {
                this.tolerance = tolerance;
                return this;
            }
        }

        public WordMatcherBuilder setThreshold(double threshold) {
            if (this.isValidRange(threshold)) {
                throw new ThresholdValueException("Threshold value range should be between 0.0 and 1.0.");
            } else {
                this.threshold = threshold;
                return this;
            }
        }

        public WordMatcherBuilder setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        public WordMatcher build() {
            return new WordMatcher(this.root, this.tolerance, this.defaultValue, this.similarityMatcher, this.threshold);
        }

        private BKTreeNode buildBKTree() {
            switch (this.matchType) {
                case JARO_WINKLER:
                    this.similarityMatcher = new JaroWinklerMatcher();
                    break;
                case COSINE_SIMILARITY:
                    this.similarityMatcher = new CosineSimilarityMatcher();
            }

            BKTreeNode root = new BKTreeNode(this.candidates.get(0), 0.0);

            for(int i = 1; i < this.candidates.size(); ++i) {
                root.insert(this.candidates.get(i), this.similarityMatcher);
            }

            return root;
        }

        private boolean isValidRange(double value) {
            return !(value <= 1.0) || !(value >= 0.0);
        }
    }

}
