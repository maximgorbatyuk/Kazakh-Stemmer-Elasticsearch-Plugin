package org.elasticsearch.plugin;

import org.apache.lucene.analysis.Analyzer;
import org.elasticsearch.index.analysis.TokenFilterFactory;
import org.elasticsearch.indices.analysis.AnalysisModule;
import org.elasticsearch.plugins.AnalysisPlugin;
import org.elasticsearch.plugins.Plugin;

import org.elasticsearch.index.analysis.AnalyzerProvider;

import java.util.LinkedHashMap;
import java.util.Map;
import static java.util.Collections.singletonMap;

// код класса в целом взят из украинского стеммера от эластика
// https://github.com/elastic/elasticsearch/blob/6.2/plugins/analysis-ukrainian/src/main/java/org/elasticsearch/plugin/analysis/ukrainian/AnalysisUkrainianPlugin.java

public class KazakhStemmerPlugin extends Plugin implements AnalysisPlugin {


    @Override
    public Map<String, AnalysisModule.AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> getAnalyzers() {
        return singletonMap("kazakh", KazakhStemmerAnalyzerProvider::new);
    }

    // Код ниже взят из проекта индуса
    // https://github.com/Mangu-Singh-Rajpurohit/hinglish-stemmer/blob/master/org/rt/hinglish/elasticsearch/plugin/HinglishStemmerPlugin.java
    @Override
    public Map<String, AnalysisModule.AnalysisProvider<TokenFilterFactory>> getTokenFilters()
    {
        Map<String, AnalysisModule.AnalysisProvider<TokenFilterFactory>> dictOut = new LinkedHashMap<>();
        dictOut.put("kazakh_stemmer_token_filter", KazakhStemmerTokenFilterFactory::new);
        return dictOut;
    }
}
