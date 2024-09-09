package io.github.fwm;

import io.github.fwm.lib.enums.MatchType;
import io.github.fwm.lib.exceptions.ThresholdValueException;
import io.github.fwm.lib.exceptions.ToleranceValueException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class FuzzyWordMatcherTest {
    @Test
    public void jaroWinklerTest(){
        List<String> candidates = Arrays.asList("District Code", "District Name", "Country_Code", "Country_Name", "Pin_Code");
        WordMatcher wordMatcher = new WordMatcher.WordMatcherBuilder(candidates, MatchType.JARO_WINKLER)
                .setTolerance(.85)
                .setThreshold(.85)
                .setDefaultValue(null).build();
        String bestMatch = wordMatcher.findBestMatch("Dist Na");
        Assertions.assertEquals("District Name",bestMatch);
    }

    @Test
    public void cosineSimilarityTest(){
        List<String> candidates = Arrays.asList("District Code", "District Name", "Country_Code", "Country_Name", "Pin_Code");
        WordMatcher wordMatcher = new WordMatcher.WordMatcherBuilder(candidates, MatchType.COSINE_SIMILARITY)
                .setTolerance(.85)
                .setThreshold(.85)
                .setDefaultValue(null).build();
        String bestMatch = wordMatcher.findBestMatch("Country_Na");
        Assertions.assertEquals("Country_Name",bestMatch);
    }

    @Test
    public void bkTreeInsertionTest(){
        List<String> candidates = Arrays.asList("District Code", "District Name", "Country_Code", "Country_Name", "Pin_Code");
        WordMatcher wordMatcher = new WordMatcher.WordMatcherBuilder(candidates, MatchType.COSINE_SIMILARITY)
                .setTolerance(.85)
                .setThreshold(.85)
                .setDefaultValue(null).build();
        String match = wordMatcher.findBestMatch("First_Name");
        Assertions.assertNull(match);
        wordMatcher.insert("First Name");
        String bestMatch = wordMatcher.findBestMatch("First_Name");
        Assertions.assertEquals("First Name",bestMatch);
    }

    @Test
    public void toleranceValueException(){
        Assertions.assertThrows(ToleranceValueException.class,()->{
            List<String> candidates = Arrays.asList("District Code", "District Name", "Country_Code", "Country_Name", "Pin_Code");
            WordMatcher wordMatcher = new WordMatcher.WordMatcherBuilder(candidates, MatchType.COSINE_SIMILARITY)
                    .setTolerance(1.85)
                    .setThreshold(.85)
                    .setDefaultValue(null).build();
        });
    }

    @Test
    public void thresholdValueException(){
        Assertions.assertThrows(ThresholdValueException.class,()->{
            List<String> candidates = Arrays.asList("District Code", "District Name", "Country_Code", "Country_Name", "Pin_Code");
            WordMatcher wordMatcher = new WordMatcher.WordMatcherBuilder(candidates, MatchType.COSINE_SIMILARITY)
                    .setTolerance(.85)
                    .setThreshold(-1)
                    .setDefaultValue(null).build();
        });
    }

}
