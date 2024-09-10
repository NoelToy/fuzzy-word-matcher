[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
# Fuzzy Word Matcher (FWM)

**Fuzzy Word Matcher (FWM)** is a Java-based library designed to efficiently find the most similar word from a given list of words using fuzzy logic. This library provides flexibility by allowing users to choose between two well-known similarity metrics: Jaro-Winkler and Cosine Similarity.
## Key Features
+ **Fuzzy Matching Algorithms**: Supports both Jaro-Winkler and Cosine Similarity for calculating similarity between words.  Internally, the library leverages Apache Commons Text to perform these similarity calculations.

+ **Efficient Search**: Utilizes the BK-tree data structure to perform efficient searches with a tolerance value (ranging from 0 to 1).

+ **Threshold & Default Values:** If the similarity score of the best match is below the defined threshold (0-1), a customizable default value will be returned instead.

+ **Configurable Tolerance**: The tolerance value makes the BK-tree search more efficient by limiting the search space based on allowable differences.

## Use Cases
+ Finding approximate word matches in large datasets.
+ Spell-checking or correcting user inputs by suggesting the closest matching word.
+ Matching and aligning data entries with slightly different names or terms.

## Dependencies
+ **Apache Commons Text**: Used for calculating Jaro-Winkler and Cosine Similarity scores.
+ **Java 8 or higher**: The minimum required Java version to run the library.
## Usage/Examples
### Add Maven Dependency
```xml
<dependency>
    <groupId>io.github.noeltoy</groupId>
    <artifactId>fuzzy-word-matcher</artifactId>
    <version>0.1</version>
</dependency>
```
### Example for Jaro-Winkler
```java
import io.github.fwm.WordMatcher;
import io.github.fwm.lib.enums.MatchType;

import java.util.Arrays;
import java.util.List;

public void jaroWinklerTest(){
        List<String> candidates = Arrays.asList("District Code", "District Name", "Country_Code", "Country_Name", "Pin_Code");
        WordMatcher wordMatcher = new WordMatcher.WordMatcherBuilder(candidates, MatchType.JARO_WINKLER)
                .setTolerance(.85)
                .setThreshold(.85)
                .setDefaultValue(null).build();
        String bestMatch = wordMatcher.findBestMatch("Dist Na");
        System.out.printf(bestMatch);
    }
```
**_Output_**: District Name
### Example for Cosine-Similarity
```java
import io.github.fwm.WordMatcher;
import io.github.fwm.lib.enums.MatchType;

import java.util.Arrays;
import java.util.List;

public void cosineSimilarityTest(){
        List<String> candidates = Arrays.asList("District Code", "District Name", "Country_Code", "Country_Name", "Pin_Code");
        WordMatcher wordMatcher = new WordMatcher.WordMatcherBuilder(candidates, MatchType.COSINE_SIMILARITY)
                .setTolerance(.85)
                .setThreshold(.85)
                .setDefaultValue(null).build();
        String bestMatch = wordMatcher.findBestMatch("Country_Na");
        System.out.printf(bestMatch);
    }
```
**_Output_**: Country_Name
## Parameters
| Parameter    | Type           | Description                                                                                                                                                                                                                                                                               | Default Value  |
|--------------|----------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| candidates   | List of String | A list of strings from which  **Fuzzy Word Matcher (FWM)** will identify the best match.  Internally, this list is converted into a BK-tree  to enable efficient fuzzy searching and matching  based on the selected similarity metric.                                                   | Not Applicable |
| matchType    | MatchType      | Specifies the algorithm to be used for calculating the similarity distance.  It accepts two values: `JARO_WINKLER` or `COSINE_SIMILARITY`.  This determines whether **Fuzzy Word Matcher (FWM)** will use  the _Jaro-Winkler_ or _Cosine Similarity_ algorithm for word matching.         | Not Applicable |
| tolerance    | Double         | Defines the tolerance level used in the  _BK-tree_ search algorithm. This value (ranging from 0 to 1) controls  the allowable difference between words during the search, helping  **Fuzzy Word Matcher (FWM)** optimize the search process by limiting the search space.                 | 0.85           |
| threshold    | Double         | Sets the minimum similarity score required for a match.  If the similarity distance between the best match and  the search input word is less than this threshold (ranging from 0 to 1),  **Fuzzy Word Matcher (FWM)** will return a predefined  default value instead of the best match. | 0.60           |
| defaultValue | String         | The value to be returned if the similarity score between the  best match and the search input word is below the defined threshold.  This ensures **Fuzzy Word Matcher (FWM)** provides a fallback option  when no sufficiently close match is found.                                      | NULL           |

## License
[Apache License 2.0](https://choosealicense.com/licenses/apache-2.0/)

## Authors
- [@noeltoy](https://github.com/NoelToy)