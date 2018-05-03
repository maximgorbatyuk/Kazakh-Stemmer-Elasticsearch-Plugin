package org.elasticsearch.plugin;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

// Код взят отсюда
// https://github.com/apache/lucene-solr/blob/master/lucene/analysis/common/src/java/org/apache/lucene/analysis/ru/RussianAnalyzer.java

public class KazakhAnalyzer extends Analyzer {
    @Override
    protected TokenStreamComponents createComponents(String fieldName) {

        final Tokenizer source = new StandardTokenizer();
        TokenStream result = new StandardFilter(source);
        result = new LowerCaseFilter(result);

        result = new KazakhStemmerTokenFilter(result);

        return new TokenStreamComponents(source, result);
    }

    @Override
    protected TokenStream normalize(String fieldName, TokenStream in) {
        TokenStream result = new StandardFilter(in);
        result = new LowerCaseFilter(result);
        return result;
    }
}
