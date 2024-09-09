package io.github.fwm.lib.bk_tree;

import io.github.fwm.lib.matchers.SimilarityMatcher;

import java.util.HashSet;
import java.util.Set;

public class BKTreeNode {
    String word;
    Set<BKTreeNode> children = new HashSet<>();
    double distance;

    public BKTreeNode(String word, double distance) {
        this.word = word;
        this.distance = distance;
    }
    public void insert(String newWord, SimilarityMatcher similarityMatcher) {
        double dist = similarityMatcher.getDistance(this.word, newWord);
        for (BKTreeNode child : children) {
            if (child.distance == dist) {
                child.insert(newWord, similarityMatcher);
                return;
            }
        }
        children.add(new BKTreeNode(newWord, dist));
    }
    public String search(String searchWord, SimilarityMatcher similarityMatcher, double tolerance) {
        double dist = similarityMatcher.getDistance(this.word, searchWord);
        String bestMatch = this.word;
        for (BKTreeNode child : children) {
            if (Math.abs(child.distance - dist) <= tolerance) {
                String childMatch = child.search(searchWord, similarityMatcher, tolerance);
                if (similarityMatcher.getDistance(childMatch, searchWord) > similarityMatcher.getDistance(bestMatch, searchWord)) {
                    bestMatch = childMatch;
                }
            }
        }
        return bestMatch;
    }
}
