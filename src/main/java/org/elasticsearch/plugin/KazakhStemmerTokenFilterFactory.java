package org.elasticsearch.plugin;

import org.apache.lucene.analysis.TokenStream;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractTokenFilterFactory;

public class KazakhStemmerTokenFilterFactory extends AbstractTokenFilterFactory {

    public KazakhStemmerTokenFilterFactory(IndexSettings indexSettings, Environment e, String name, Settings settings) {
        super(indexSettings, name, settings);
    }

    @Override
    public TokenStream create(TokenStream tokenStream) {
        return new KazakhStemmerTokenFilter(tokenStream);
    }
}
