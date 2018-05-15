package org.elasticsearch.plugin;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.KeywordAttribute;

import java.io.IOException;

// Код практически скопипастен отсюда
// https://github.com/vgrichina/ukrainian-stemmer/blob/master/src/main/groovy/com/componentix/nlp/stemmer/uk/Stemmer.groovy

public class KazakhStemmerTokenFilter extends TokenFilter {

    /**
     * Construct a token stream filtering the given input.
     *
     * @param input
     */
    public KazakhStemmerTokenFilter(TokenStream input) {
        super(input);
    }

    private final CharTermAttribute termAttribute = addAttribute(CharTermAttribute.class);
    private final KeywordAttribute keywordAttr = addAttribute(KeywordAttribute.class);

    @Override
    public final boolean incrementToken() throws IOException {

        if (!input.incrementToken()) return false;

        if (!keywordAttr.isKeyword()) {
            String result = stem(new String(termAttribute.buffer(), 0, termAttribute.length()));
            termAttribute.setEmpty().append(result);
        }
        return true;
    }

    private static final int PROCESSING_MINIMAL_WORD_LENGTH = 2;

    // Оставлю, если нужно будет вернуть логгирование зачем-нибудь
    // private final static Logger LOGGER = LogManager.getLogger(KazakhStemmerTokenFilter.class);

    public static String stem(String word)
    {
        // don't change short words
        if (word.length() <= PROCESSING_MINIMAL_WORD_LENGTH ) return word;

        // try simple trim
        for (int i = 0; i < suffixes.length; i++) {
            String suffix = suffixes[i];

            if (word.endsWith(suffix)) {

                String trimmed = word.substring(0, word.length() - suffix.length());

                if (trimmed.length() > PROCESSING_MINIMAL_WORD_LENGTH) {
                    return stem(trimmed);
                }
            }
        }
        return word;
    }

    // окончания расставлены в массиве так, чтобы сначала отсекались наиболее длинное сочетание букв
    // была проблема с кодировкой этих текстов, но проставление метки в gradle.build с явной кодировкой UTF-8 помогает
    private static String suffixes[] = {
            //"сыңдар", "сіңдер","ңыздар", "ңіздер","сыздар", "сіздер",

            "шалық", "шелік", "даған", "деген", "таған", "теген", "лаған", "леген","дайын", "дейін", "тайын", "тейін",

            "ңдар", "ңдер", "дікі", "тікі", "нікі", "атын", "етін","йтын", "йтін",
            "гелі", "қалы", "келі", "ғалы", "шама", "шеме",

            "мын", "мін", "бын", "бін", "пын", "пін", "мыз", "міз", "быз", "біз", "пыз", "піз", "сың", "сің",
            "сыз", "сіз", "ңыз", "ңіз", "дан", "ден", "тан", "тен", "нан", "нен", "нда", "нде", "дың", "дің", "тың",
            "тің", "ның", "нің", "дар", "дер", "тар", "тер", "лар", "лер", "бен", "пен", "мен",
            "дай", "дей", "тай", "тей", "дық", "дік", "тық", "тік", "лық", "лік", "паз",
            "ғыш", "гіш", "қыш", "кіш", "шек", "шақ", "шыл", "шіл", "нші", "ншы", "дап", "деп",
            "тап", "теп", "лап", "леп", "даc", "деc", "таc", "теc", "лаc", "леc", "ғар", "гер", "қар", "кер", "дыр",
            "дір", "тыр", "тір", "ғыз", "гіз", "қыз", "кіз", "ған", "ген", "қан", "кен",
            "ушы", "уші", "лай", "лей", "сын", "сін", "бақ", "бек", "пақ", "пек", "мақ", "мек", "йын", "йін", "йық", "йік",

            "сы", "сі", "да", "де", "та", "те", "ға", "ге", "қа", "ке", "на", "не",
            "ді", "ты", "ті", "ны", "ні", "ды", "ба", "бе", "па", "пе", "ма", "ме",
            "лы", "лі", "ғы", "гі", "қы", "кі", "ау", "еу", "ла", "ле", "ар", "ер",
            "ып", "іп", "ша", "ше", "са", "се",

            "н", "р", "п", "й", "ы", "і"
    };
}
