# Плагин "Стеммер казахского языка" для Elasticsearch 5.6.0

Требование к структуре плагина для установки:
1. Должен быть zip-архив
2. В архиве должна быть папка elasticsearch
3. В папке должен быть набор необходимых jar-файлов. К именам требования нет.
4. В папке также должен быть файл plugin-descriptor.properties


## Полезные команды
Установка плагина:
```elasctisearch-plugin install file:///<Путь до файла>```

Удаление существующего плагина:
```elasctisearch-plugin remove "<Имя плагина, заданное в файле plugin-descriptor.properties в графе name>"```

Основной стемминг происходит в классе KazakhStemmerTokenFilter в методе stem().
Там же и набор обрабатываемых окончаний.

## Использование в проекте этого стеммера как фильтра.
Если не используется никакой клиент, а только лишь REST:
```
PUT /kazakh_stemmer_example_index
{
    "settings" : {
        "analysis" : {
            "analyzer" : {
                "default" : {
                    "tokenizer" : "standard",
                    "filter" : ["standard", "kazakh_stemmer"]
                }
            },
            "filter" : {
                "kazakh_stemmer" : {
                    "type" : "kazakh_stemmer_token_filter"
                }
            }
        }
    }
}
```

Если используется .NET и клиент NEST, то можно так:
```
public method Nest.ICreateIndexRequest CreateIndexDescriptor(string awesomeIndexName)
{
    var descriptor = new CreateIndexDescriptor(awesomeIndexName)
        .Settings(settings => settings
            .Analysis(analysis => analysis
                .TokenFilters(tokenFilters =>
                    tokenFilters
                        .UserDefined("kazakh_stemmer", new CustomStemmerTokenFilter("kazakh_stemmer_token_filter"))
                .Analyzers(analyzer => analyzer
                    .Custom(ElasticUtils.KazAnalyzerName, customAnalyzer => customAnalyzer
                        .Tokenizer("standard")
                        .Filters("standard", "lowercase", "asciifolding", "kaspiguide_kazakh_stemmer")))))
        .Mappings(mappings => mappings
            .Map<AwesomeIndexData>(m => m.AutoMap()));

    return descriptor;
}

private class CustomStemmerTokenFilter : IStemmerTokenFilter
{
    public CustomStemmerTokenFilter(string type)
    {
        Type = type;
    }
    public string Version { get; set; }
    public string Type { get; }
    public string Language { get; set; }
}
```
