package org.elasticsearch.plugin;

import org.apache.lucene.analysis.*;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractIndexAnalyzerProvider;

public class KazakhStemmerAnalyzerProvider extends AbstractIndexAnalyzerProvider<Analyzer> {

    private final KazakhAnalyzer analyzer;

    public KazakhStemmerAnalyzerProvider(IndexSettings indexSettings, Environment env, String name, Settings settings) {
        super(indexSettings, name, settings);

        analyzer = new KazakhAnalyzer();

        analyzer.setVersion(version);
    }

    @Override
    public Analyzer get() {
        return this.analyzer;
    }
}
