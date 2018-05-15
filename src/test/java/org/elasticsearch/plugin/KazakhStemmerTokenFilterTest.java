package org.elasticsearch.plugin;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class KazakhStemmerTokenFilterTest {

    @Test
    public void stem_СловоДлинееДвухСимволов() {
        final String source = "словобын";
        final String expected = "слово";

        assertEquals(expected, KazakhStemmerTokenFilter.stem(source));
    }

    @Test
    public void stem_ВСловеБольшеОдногоОкончания() {
        final String source = "словобынңіздер";
        final String expected = "слово";

        assertEquals(expected, KazakhStemmerTokenFilter.stem(source));
    }

    @Test
    public void stem_ВСловеБольшеОдногоОкончания_1() {
        final String source = "құқықтары";
        final String expected = "құқық";

        assertEquals(expected, KazakhStemmerTokenFilter.stem(source));
    }

    @Test
    public void stem_ВСловеБольшеОдногоОкончания_2() {
        // Это - очень неправильный пример стемминга, ибо одно слово становится совершенно другим.
        // // Однако тест написан для того, чтобы показать возможности стеммера, правильные и не очень
        final String source = "жасаулары";
        final String expected = "жас";

        assertEquals(expected, KazakhStemmerTokenFilter.stem(source));
    }

    @Test
    public void stem_ВСловеБольшеОдногоОкончания_3() {
        final String source = "келеді";
        final String expected = "келе";

        assertEquals(expected, KazakhStemmerTokenFilter.stem(source));
    }

    @Test
    public void stem_ВСловеБольшеОдногоОкончания_4() {
        final String source = "құралдарын";
        final String expected = "құрал";

        assertEquals(expected, KazakhStemmerTokenFilter.stem(source));
    }

    @Test
    public void stem_ВСловеБольшеОдногоОкончания_5() {
        final String source = "мерзімдерден";
        final String expected = "мерзім";

        assertEquals(expected, KazakhStemmerTokenFilter.stem(source));
    }

    @Test
    public void stem_ВСловеБольшеОдногоОкончания_6() {
        final String source = "тумысышалықбен";
        final String expected = "тум";

        assertEquals(expected, KazakhStemmerTokenFilter.stem(source));
    }

    @Test
    public void stem_СловоБезКазахскихОкончаний() {
        final String source = "интернет";
        final String expected = "интернет";

        assertEquals(expected, KazakhStemmerTokenFilter.stem(source));
    }

    @Test
    public void stem_СловоПолностьюПовторяетОдноИзОкончаний_ВозвращаетсяМинимальнаяЧасть() {
        final String source = "сiздер";
        final String expected = "сiз";

        assertEquals(expected, KazakhStemmerTokenFilter.stem(source));
    }

    @Test
    public void stem_ИзначальноеСловоМеньшеДвухСимволов() {
        final String source = "сл";
        final String expected = "сл";

        assertEquals(expected, KazakhStemmerTokenFilter.stem(source));
    }
}