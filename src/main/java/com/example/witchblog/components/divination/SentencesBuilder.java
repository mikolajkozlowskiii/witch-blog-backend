package com.example.witchblog.components.divination;

import com.example.witchblog.entity.divination.DivinationCard;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SentencesBuilder {

    public String getSentencesOfPrediction(String userName, Sentence sentence, Set<DivinationCard> divinationCards){
        final String firstSentence = getFirstSentenceOfPrediction(userName, sentence.getFirstSentences());
        final String midSentences = getMidSentencesOfPrediction(divinationCards, sentence.getMidSentences());
        final String lastSentence = getLastSentenceOfPredicition(divinationCards, sentence.getLastSentences());

        return firstSentence + midSentences + lastSentence;
    }
    public String getFirstSentenceOfPrediction(String userName, List<String> firstSentences) {
        final String drawnSentence = firstSentences.get(new Random().nextInt(firstSentences.size()));
        return assignNameAndDate(userName, drawnSentence);
    }

    private String assignNameAndDate(String userName, String sentence) {
        sentence =
                sentence.replace(FirstSentence.NAME.getValue(), userName);
        sentence =
                sentence.replace(FirstSentence.DATE.getValue(), LocalDate.now().toString());
        return sentence;
    }
    public String getLastSentenceOfPredicition(Set<DivinationCard> divinationCards,
                                               Map<String, List<String>> lastSentences) {
        final double percentageOfLuckyCards = getPercentageOfLuckyCards(divinationCards);
        if(percentageOfLuckyCards > LastSentence.HIGH.getPercentage()){
            final List<String> veryLuckySentences = lastSentences.get(LastSentence.VERY_HIGH.getPercentageKey());
            return veryLuckySentences.get(new Random().nextInt(veryLuckySentences.size()));
        }

        else if(percentageOfLuckyCards > LastSentence.MEDIUM.getPercentage()){
            final List<String> luckySentences = lastSentences.get(LastSentence.HIGH.getPercentageKey());
            return luckySentences.get(new Random().nextInt(luckySentences.size()));
        }

        else if(percentageOfLuckyCards > LastSentence.LOW.getPercentage()){
            final List<String> midSentences = lastSentences.get(LastSentence.MEDIUM.getPercentageKey());
            return midSentences.get(new Random().nextInt(midSentences.size()));
        }

        else{
            final List<String> unLuckySentences = lastSentences.get(LastSentence.VERY_LOW.getPercentageKey());
            return unLuckySentences.get(new Random().nextInt(unLuckySentences.size()));
        }
    }

    public String getMidSentencesOfPrediction(Set<DivinationCard> divinationCards, Map<String, List<String>> midSentences) {
        final double percentageOfPositiveCards = getPercentageOfLuckyCards(divinationCards);
        StringBuilder sentence = new StringBuilder();
        if(percentageOfPositiveCards > LastSentence.HIGH.getPercentage()){
            final String positiveSentence = generateMidSentence(midSentences, MidSentence.POSITIVE);
            sentence.append(positiveSentence);
            final String divinationsSentence = getSentenceOfDivinations(getPositiveCards(divinationCards));
            sentence.append(divinationsSentence);

            return sentence.toString();
        }
        else if(percentageOfPositiveCards > LastSentence.MEDIUM.getPercentage()){
            final String positiveSentence = generateMidSentence(midSentences, MidSentence.POSITIVE);
            sentence.append(positiveSentence);
            sentence.append(getSentenceOfDivinations(getPositiveCards(divinationCards)));

            final String connectSentence = generateMidSentence(midSentences, MidSentence.CONNECT);
            sentence.append(connectSentence);

            final String negativeSentence = generateMidSentence(midSentences, MidSentence.NEGATIVE);
            sentence.append(negativeSentence.toLowerCase());
            sentence.append(getSentenceOfDivinations(getNegativesCards(divinationCards)));

            return sentence.toString();
        }
        else if(percentageOfPositiveCards > LastSentence.LOW.getPercentage()){
            final String negativeSentence = generateMidSentence(midSentences, MidSentence.NEGATIVE);
            sentence.append(negativeSentence);
            sentence.append(getSentenceOfDivinations(getNegativesCards(divinationCards)));

            final String connectSentence = generateMidSentence(midSentences, MidSentence.CONNECT);
            sentence.append(connectSentence);

            final String positiveSentence = generateMidSentence(midSentences, MidSentence.POSITIVE);
            sentence.append(positiveSentence.toLowerCase());
            sentence.append(getSentenceOfDivinations(getPositiveCards(divinationCards)));

            return sentence.toString();
        }
        else{
            final String negativeSentence = generateMidSentence(midSentences, MidSentence.NEGATIVE);
            sentence.append(negativeSentence);
            final String divinationsSentence = getSentenceOfDivinations(getNegativesCards(divinationCards));
            sentence.append(divinationsSentence);

            return sentence.toString();
        }
    }

    private Set<DivinationCard> getPositiveCards(Set<DivinationCard> divinationCards) {
        return divinationCards.stream().filter(s -> !s.isReversed()).collect(Collectors.toSet());
    }

    private Set<DivinationCard> getNegativesCards(Set<DivinationCard> divinationCards) {
        return divinationCards.stream().filter(DivinationCard::isReversed).collect(Collectors.toSet());
    }

    private String generateMidSentence(Map<String, List<String>> midSentences, MidSentence keynote) {
        List<String> sentences = midSentences.get(keynote.getValue());
        return sentences.get(new Random().nextInt(sentences.size()));
    }

    private  String getSentenceOfDivinations(Set<DivinationCard> divinationCards) {
        StringBuilder sentence = new StringBuilder();
        for(DivinationCard divinationCard : divinationCards){
            String divination = divinationCard.getMeaning().toLowerCase();
            sentence.append(divination).append(" (").append(divinationCard.getCard().getName()).append(")").append(", ");
        }
        return sentence.replace(sentence.length() - 2, sentence.length(), ". ").toString();
    }

    private double getPercentageOfLuckyCards(Set<DivinationCard> divinationCards) {
        double percent =  (double) divinationCards.stream()
                .filter(s -> !s.isReversed())
                .count() / divinationCards.size() * 100;
        return percent;
    }
}
