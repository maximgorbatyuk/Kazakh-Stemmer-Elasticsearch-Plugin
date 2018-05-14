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
        for (String suffix : suffixes) {
            if (word.endsWith(suffix)) {
                String trimmed = word.substring(0, word.length() - suffix.length());
                if (trimmed.length() > PROCESSING_MINIMAL_WORD_LENGTH) {
                    return trimmed;
                }
            }
        }
        return word;
    }

    // окончания расставлены в массиве так, чтобы сначала отсекались наиболее длинное сочетание букв
    // была проблема с кодировкой этих текстов, но проставление метки в gradle.build с явной кодировкой UTF-8 помогает
    private static String suffixes[] = {
            "сыңдар", "сiңдер","ңыздар", "ңiздер","сыздар", "сiздер",

            "шалық", "шелік", "даған", "деген", "таған", "теген", "лаған", "леген","дайын", "дейін", "тайын", "тейін",

            "ңдар", "ңдер", "дiкi", "тiкi", "нiкi", "атын", "етiн","йтын", "йтiн",
            "гелi", "қалы", "келi", "ғалы", "шама", "шеме",

            "мын", "мiн", "бын", "бiн", "пын", "пiн", "мыз", "мiз", "быз", "бiз", "пыз", "пiз", "сың", "сiң",
            "сыз", "сiз", "ңыз", "ңiз", "дан", "ден", "тан", "тен", "нан", "нен", "нда", "нде", "дың", "дiң", "тың",
            "тiң", "ның", "нiң", "дар", "дер", "тар", "тер", "лар", "лер", "бен", "пен", "мен",
            "дай", "дей", "тай", "тей", "дық", "дiк", "тық", "тiк", "лық", "лiк", "паз",
            "ғыш", "гiш", "қыш", "кiш", "шек", "шақ", "шыл", "шiл", "ншi", "ншы", "дап", "деп",
            "тап", "теп", "лап", "леп", "даc", "деc", "таc", "теc", "лаc", "леc", "ғар", "гер", "қар", "кер", "дыр",
            "дiр", "тыр", "тiр", "ғыз", "гiз", "қыз", "кiз", "ған", "ген", "қан", "кен",
            "ушы", "ушi", "лай", "лей", "сын", "сiн", "бақ", "бек", "пақ", "пек", "мақ", "мек", "йын", "йiн", "йық", "йiк",

            "сы", "сi", "да", "де", "та", "те", "ға", "ге", "қа", "ке", "на", "не",
            "дi", "ты", "тi", "ны", "нi", "ды", "ба", "бе", "па", "пе", "ма", "ме",
            "лы", "лi", "ғы", "гi", "қы", "кi", "ау", "еу", "ла", "ле", "ар", "ер",
            "ып", "iп", "ша", "ше", "са", "се",

            "н", "р", "п", "й",
    };
}
