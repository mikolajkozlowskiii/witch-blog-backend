package com.example.witchblog.components.divination;

import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Sentence {
    private List<String> firstSentences;
    private Map<String, List<String>> midSentences;
    private Map<String, List<String>> lastSentences;
}
