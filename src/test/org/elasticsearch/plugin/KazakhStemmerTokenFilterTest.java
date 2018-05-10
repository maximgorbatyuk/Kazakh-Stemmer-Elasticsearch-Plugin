package org.elasticsearch.plugin;

import static org.junit.jupiter.api.Assertions.*;

class KazakhStemmerTokenFilterTest {

    @org.junit.jupiter.api.Test
    void stem_СловоДлинееДвухСимволов() {
        final String source = "словобын";
        final String expected = "слово";

        assertEquals(expected, KazakhStemmerTokenFilter.stem(source));
    }

    @org.junit.jupiter.api.Test
    void stem_ИзначальноеСловоМеньшеДвухСимволов() {
        final String source = "сл";
        final String expected = "сл";

        assertEquals(expected, KazakhStemmerTokenFilter.stem(source));
    }
}