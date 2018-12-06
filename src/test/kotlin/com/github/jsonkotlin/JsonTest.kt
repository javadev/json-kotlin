/*
 * The MIT License (MIT)
 *
 * Copyright 2018 Valentyn Kolesnikov
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.jsonkotlin

import java.util.*
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Assert.fail

import com.github.jsonkotlin.Json.JsonStringBuilder

class JsonTest {
  @Test
  fun testJsonArray() {
    var builder = JsonStringBuilder()
    Json.JsonArray.writeJson(object:ArrayList<String?>() {
      init{
        add(null as String?)
      }
    }, builder)
    assertEquals("[\n  null\n]", builder.toString())
  }
  @Test
  fun testJsonArrayCollection() {
    assertEquals("[\n  \"First item\",\n  \"Second item\"\n]",
                 Json.toJson(Arrays.asList<String>("First item", "Second item")))
    assertEquals("[\n  [\n    1,\n    2\n  ]\n]",
                 Json.toJson(Arrays.asList<Any>(byteArrayOf(1, 2))))
    assertEquals("[\n  [\n    1,\n    2\n  ]\n]",
                 Json.toJson(Arrays.asList<Any>(shortArrayOf(1, 2))))
    assertEquals("[\n  [\n    1,\n    2\n  ]\n]",
                 Json.toJson(Arrays.asList<Any>(intArrayOf(1, 2))))
    assertEquals("[\n  [\n    1,\n    2\n  ]\n]",
                 Json.toJson(Arrays.asList<Any>(longArrayOf(1, 2))))
    assertEquals("[\n  [\n    1.0,\n    2.0\n  ]\n]",
                 Json.toJson(Arrays.asList<Any>(floatArrayOf(1f, 2f))))
    assertEquals("[\n  [\n    1.0,\n    2.0\n  ]\n]",
                 Json.toJson(Arrays.asList<Any>(doubleArrayOf(1.0, 2.0))))
    assertEquals("[\n  [\n    \"1\",\n    \"2\"\n  ]\n]",
                 Json.toJson(Arrays.asList<Any>(charArrayOf('1', '2'))))
    assertEquals("[\n  [\n    true,\n    false,\n    true\n  ]\n]",
                 Json.toJson(Arrays.asList<Any>(booleanArrayOf(true, false, true))))
    assertEquals("[\n  [\n    1.0,\n    2.0\n  ]\n]",
                 Json.toJson(Arrays.asList<Any>(arrayOf<Float>(1f, 2f))))
    assertEquals("[\n  [\n    1.0,\n    2.0\n  ]\n]",
                 Json.toJson(Arrays.asList<Any>(arrayOf<Double>(1.0, 2.0))))
    assertEquals("[\n  [\n    true,\n    false,\n    true\n  ]\n]",
                 Json.toJson(Arrays.asList<Any>(arrayOf<Boolean>(true, false, true))))
    assertEquals("[\n  [\n    \"First item\",\n    \"Second item\"\n  ]\n]",
                 Json.toJson(Arrays.asList<Any>(Arrays.asList<String>("First item", "Second item"))))
    val testMap = LinkedHashMap<String, String>()
    testMap.put("1", "First item")
    testMap.put("2", "Second item")
    assertEquals("[\n  {\n    \"1\": \"First item\",\n    \"2\": \"Second item\"\n  }\n]",
                 Json.toJson(Arrays.asList<Any>(testMap)))
//    assertEquals("[\n null\n]", Json.toJson(Arrays.asList<Any>(arrayOf<String>(null as String?))))
//    assertEquals("null", Json.toJson(null as Collection<*>?))
    class Test {
      public override fun toString():String {
        return "test"
      }
    }
    val testArray = ArrayList<Array<Test>>()
    testArray.add(arrayOf<Test>(Test(), Test()))
    assertEquals("[\n  [\n    test,\n    test\n  ]\n]",
                 Json.toJson(testArray))
  }
  @Test
  fun escape() {
    assertEquals("\\\"", Json.JsonValue.escape("\""))
    assertEquals("\\\\", Json.JsonValue.escape("\\"))
    assertEquals("\\b", Json.JsonValue.escape("\b"))
    assertEquals("\\n", Json.JsonValue.escape("\n"))
    assertEquals("\\r", Json.JsonValue.escape("\r"))
    assertEquals("\\t", Json.JsonValue.escape("\t"))
    assertEquals("/", Json.JsonValue.escape("/"))
    assertEquals("€", Json.JsonValue.escape("€"))
    assertEquals("\\u0000", Json.JsonValue.escape("\u0000"))
    assertEquals("\\u001F", Json.JsonValue.escape("\u001F"))
    assertEquals("\u0020", Json.JsonValue.escape("\u0020"))
    assertEquals("\\u007F", Json.JsonValue.escape("\u007F"))
    assertEquals("\\u009F", Json.JsonValue.escape("\u009F"))
    assertEquals("\u00A0", Json.JsonValue.escape("\u00A0"))
    assertEquals("\\u2000", Json.JsonValue.escape("\u2000"))
    assertEquals("\\u20FF", Json.JsonValue.escape("\u20FF"))
    assertEquals("\u2100", Json.JsonValue.escape("\u2100"))
    assertEquals("\uFFFF", Json.JsonValue.escape("\uFFFF"))
  }
  @Test
  fun testByteArrayToString() {
    var builder:Json.JsonStringBuilder
//    builder = JsonStringBuilder()
//    Json.JsonArray.writeJson(null as ByteArray?, builder)
//    assertEquals("null", builder.toString())
    builder = JsonStringBuilder()
    Json.JsonArray.writeJson(ByteArray(0), builder)
    assertEquals("[]", builder.toString())
    builder = JsonStringBuilder()
    Json.JsonArray.writeJson(byteArrayOf(12), builder)
    assertEquals("[\n  12\n]", builder.toString())
    builder = JsonStringBuilder()
    Json.JsonArray.writeJson(byteArrayOf(-7, 22, 86, -99), builder)
    assertEquals("[\n  -7,\n  22,\n  86,\n  -99\n]", builder.toString())
  }
  @Test
  fun testShortArrayToString() {
    var builder:JsonStringBuilder
//    builder = JsonStringBuilder()
//    Json.JsonArray.writeJson(null as ShortArray?, builder)
//    assertEquals("null", builder.toString())
    builder = JsonStringBuilder()
    Json.JsonArray.writeJson(ShortArray(0), builder)
    assertEquals("[]", builder.toString())
    builder = JsonStringBuilder()
    Json.JsonArray.writeJson(shortArrayOf(12), builder)
    assertEquals("[\n  12\n]", builder.toString())
    builder = JsonStringBuilder()
    Json.JsonArray.writeJson(shortArrayOf(-7, 22, 86, -99), builder)
    assertEquals("[\n  -7,\n  22,\n  86,\n  -99\n]", builder.toString())
  }
  @Test
  fun testIntArrayToString() {
    var builder:Json.JsonStringBuilder
//    builder = JsonStringBuilder()
//    Json.JsonArray.writeJson(null as IntArray?, builder)
//    assertEquals("null", builder.toString())
    builder = JsonStringBuilder()
    Json.JsonArray.writeJson(IntArray(0), builder)
    assertEquals("[]", builder.toString())
    builder = JsonStringBuilder()
    Json.JsonArray.writeJson(intArrayOf(12), builder)
    assertEquals("[\n  12\n]", builder.toString())
    builder = JsonStringBuilder()
    Json.JsonArray.writeJson(intArrayOf(-7, 22, 86, -99), builder)
    assertEquals("[\n  -7,\n  22,\n  86,\n  -99\n]", builder.toString())
  }
  @Test
  fun testLongArrayToString() {
    var builder:JsonStringBuilder
//    builder = JsonStringBuilder()
//    Json.JsonArray.writeJson(null as LongArray?, builder)
//    assertEquals("null", builder.toString())
    builder = JsonStringBuilder()
    Json.JsonArray.writeJson(LongArray(0), builder)
    assertEquals("[]", builder.toString())
    builder = JsonStringBuilder()
    Json.JsonArray.writeJson(longArrayOf(12), builder)
    assertEquals("[\n  12\n]", builder.toString())
    builder = JsonStringBuilder()
    Json.JsonArray.writeJson(longArrayOf(-7, 22, 86, -99), builder)
    assertEquals("[\n  -7,\n  22,\n  86,\n  -99\n]", builder.toString())
  }
  @Test
  fun testFloatArrayToString() {
    var builder:JsonStringBuilder
//    builder = JsonStringBuilder()
//    Json.JsonArray.writeJson(null as FloatArray?, builder)
//    assertEquals("null", builder.toString())
    builder = JsonStringBuilder()
    Json.JsonArray.writeJson(FloatArray(0), builder)
    assertEquals("[]", builder.toString())
    builder = JsonStringBuilder()
    Json.JsonArray.writeJson(floatArrayOf(12.8f), builder)
    assertEquals("[\n  12.8\n]", builder.toString())
    builder = JsonStringBuilder()
    Json.JsonArray.writeJson(floatArrayOf(-7.1f, 22.234f, 86.7f, -99.02f), builder)
    assertEquals("[\n  -7.1,\n  22.234,\n  86.7,\n  -99.02\n]", builder.toString())
  }
  @Test
  fun testDoubleArrayToString() {
    var builder:JsonStringBuilder
//    builder = JsonStringBuilder()
//    Json.JsonArray.writeJson(null as DoubleArray?, builder)
//    assertEquals("null", builder.toString())
    builder = JsonStringBuilder()
    Json.JsonArray.writeJson(DoubleArray(0), builder)
    assertEquals("[]", builder.toString())
    builder = JsonStringBuilder()
    Json.JsonArray.writeJson(doubleArrayOf(12.8), builder)
    assertEquals("[\n  12.8\n]", builder.toString())
    builder = JsonStringBuilder()
    Json.JsonArray.writeJson(doubleArrayOf(-7.1, 22.234, 86.7, -99.02), builder)
    assertEquals("[\n  -7.1,\n  22.234,\n  86.7,\n  -99.02\n]", builder.toString())
  }
  @Test
  fun testBooleanArrayToString() {
    var builder:JsonStringBuilder
//    builder = JsonStringBuilder()
//    Json.JsonArray.writeJson(null as BooleanArray?, builder)
//    assertEquals("null", builder.toString())
    builder = JsonStringBuilder()
    Json.JsonArray.writeJson(BooleanArray(0), builder)
    assertEquals("[]", builder.toString())
    builder = JsonStringBuilder()
    Json.JsonArray.writeJson(booleanArrayOf(true), builder)
    assertEquals("[\n  true\n]", builder.toString())
    builder = JsonStringBuilder()
    Json.JsonArray.writeJson(booleanArrayOf(true, false, true), builder)
    assertEquals("[\n  true,\n  false,\n  true\n]", builder.toString())
  }
  @Test
  fun testCharArrayToString() {
    var builder:JsonStringBuilder
//    builder = JsonStringBuilder()
//    Json.JsonArray.writeJson(null as CharArray?, builder)
//    assertEquals("null", builder.toString())
    builder = JsonStringBuilder()
    Json.JsonArray.writeJson(CharArray(0), builder)
    assertEquals("[]", builder.toString())
    builder = JsonStringBuilder()
    Json.JsonArray.writeJson(charArrayOf('a'), builder)
    assertEquals("[\n  \"a\"\n]", builder.toString())
    builder = JsonStringBuilder()
    Json.JsonArray.writeJson(charArrayOf('a', 'b', 'c'), builder)
    assertEquals("[\n  \"a\",\n  \"b\",\n  \"c\"\n]", builder.toString())
  }
  @Test
  fun testObjectArrayToString() {
    var builder:JsonStringBuilder
//    builder = JsonStringBuilder()
//    Json.JsonArray.writeJson(null as Array<Any>?, builder)
//    assertEquals("null", builder.toString())
//    builder = JsonStringBuilder()
//    Json.JsonArray.writeJson(arrayOfNulls<Any>(0), builder)
//    assertEquals("[]", builder.toString())
    builder = JsonStringBuilder()
    Json.JsonArray.writeJson(arrayOf<Any>("Hello"), builder)
    assertEquals("[\n  \"Hello\"\n]", builder.toString())
    builder = JsonStringBuilder()
    Json.JsonArray.writeJson(arrayOf<Any>("Hello", Integer.valueOf(12), intArrayOf(1, 2, 3)), builder)
    assertEquals("[\n  \"Hello\",\n  12,\n  [\n    1,\n    2,\n    3\n  ]\n]", builder.toString())
  }
  @Test
  fun toJsonFromList() {
    val testList = ArrayList<String>()
    testList.add("First item")
    testList.add("Second item")
    assertEquals("[\n  \"First item\",\n  \"Second item\"\n]", Json.toJson(testList))
    assertEquals("[\n  null\n]", Json.toJson(Arrays.asList<Any>(java.lang.Double.NaN)))
    assertEquals("[\n  null\n]", Json.toJson(Arrays.asList<Any>(java.lang.Double.POSITIVE_INFINITY)))
    assertEquals("[\n  null\n]", Json.toJson(Arrays.asList<Any>(java.lang.Float.NaN)))
    assertEquals("[\n  null\n]", Json.toJson(Arrays.asList<Any>(java.lang.Float.POSITIVE_INFINITY)))
  }
  @Test
  fun toJsonFromMap() {
    val testMap = LinkedHashMap<String, String>()
    testMap.put("First item", "1")
    testMap.put("Second item", "2")
    val testMap2 = LinkedHashMap<String, String>()
    testMap2.put("", "1")
    val testMap3 = LinkedHashMap<String, String>()
    testMap3.put("__FA", "1")
    assertEquals("{\n  \"First item\": \"1\",\n  \"Second item\": \"2\"\n}", Json.toJson(testMap))
//    assertEquals("null", Json.toJson(null as Map<*, *>?))
    assertEquals("{\n  \"\": \"1\"\n}", Json.toJson(testMap2))
    assertEquals("{\n  \"__FA\": \"1\"\n}", Json.toJson(testMap3))
  }
  @Test
  fun toJsonFromMapFormatted() {
    val string = ("{\n"
      + "  \"glossary\": {\n"
      + "    \"title\": \"example glossary\",\n"
      + "    \"GlossDiv\": {\n"
      + "      \"title\": \"S\",\n"
      + "      \"GlossList\": {\n"
      + "        \"GlossEntry\": {\n"
      + "          \"ID\": \"SGML\",\n"
      + "          \"SortAs\": \"SGML\",\n"
      + "          \"GlossTerm\": \"Standard Generalized Markup Language\",\n"
      + "          \"Acronym\": \"SGML\",\n"
      + "          \"Abbrev\": \"ISO 8879:1986\",\n"
      + "          \"GlossDef\": {\n"
      + "            \"para\": \"A meta-markup language, used to create markup languages such as DocBook.\",\n"
      + "            \"GlossSeeAlso\": [\n"
      + "              \"GML\",\n"
      + "              \"XML\"\n"
      + "            ]\n"
      + "          },\n"
      + "          \"GlossSee\": \"markup\"\n"
      + "        }\n"
      + "      }\n"
      + "    }\n"
      + "  }\n"
      + "}")
    assertEquals(string, Json.toJson(Json.fromJson(string) as Map<String, Any?>))
  }
/*
  @Test
  fun testDecode() {
    val string = "[0,{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}]"
    assertEquals(("[\n 0,\n {\n \"1\": {\n \"2\": {\n \"3\": {\n \"4\": [\n"
                  + " 5,\n {\n \"6\": 7\n }\n ]\n"
                  + " }\n }\n }\n }\n]"),
                 Json.toJson(Json.fromJson(string) as List<Any>))
    assertEquals(("[\n 0,\n {\n \"1\": {\n \"2\": {\n \"3\": {\n \"4\": [\n"
                  + " 5,\n {\n \"6\": 7\n }\n ]\n"
                  + " }\n }\n }\n }\n]"),
                 Json.toJson(U(string).fromJson() as List<Any>))
    assertEquals(("[\n 0,\n {\n \"1\": {\n \"2\": {\n \"3\": {\n \"4\": [\n"
                  + " 5,\n {\n \"6\": 7\n }\n ]\n"
                  + " }\n }\n }\n }\n]"),
                 Json.toJson(Json.chain(string).fromJson().item() as List<Any>))
  }
  @Test
  fun testDecode2() {
    val string = "[\"hello\\bworld\\\"abc\\tdef\\\\ghi\\rjkl\\n123\\f356\"]"
    assertEquals("[\n \"hello\\bworld\\\"abc\\tdef\\\\ghi\\rjkl\\n123\\f356\"\n]",
                 Json.toJson(Json.fromJson(string) as List<Any>))
  }
  @Test
  fun testDecode3() {
    assertEquals("[\n]", Json.toJson(Json.fromJson("[]") as List<Any>))
  }
  @Test
  fun testDecodeMap() {
    val string = ("{\"first\": 123e+10,\"second\":[{\"k1\":{\"id\":\"id1\"}}," + "4,5,6,{\"id\":-123E-1}],\t\n\r \"third\":789,\"id\":null}")
    assertEquals(("{\n \"first\": 1.23E12,\n \"second\": [\n {\n \"k1\": {\n \"id\": \"id1\"\n"
                  + " }\n },\n 4,\n 5,\n 6,\n {\n \"id\": -12.3\n }\n ],\n \"third\": 789,\n"
                  + " \"id\": null\n}"), Json.toJson(Json.fromJson(string) as Map<String, Any>))
  }
  @Test
  fun testDecodeMap2() {
    assertEquals("{\n}", Json.toJson(Json.fromJson("{}") as Map<String, Any>))
  }
  @Test
  fun testDecodeMap3() {
    // http://stackoverflow.com/questions/12155800/how-to-convert-hashmap-to-json-object-in-java
    val map = LinkedHashMap<String, Any>()
    map.put("1", "a")
    map.put("2", "b")
    assertEquals("{\n \"1\": \"a\",\n \"2\": \"b\"\n}", Json.toJson(map))
    val newMap = Json.fromJson(Json.toJson(map)) as Map<String, Any>
    assertEquals("a", newMap.get("1"))
  }
  @Test
  fun testDecodeTrueFalse() {
    val array1 = ArrayList<Any>()
    array1.add("abc\u0010a/")
    array1.add(Integer.valueOf(123))
    array1.add(java.lang.Double.valueOf(222.123))
    array1.add(java.lang.Boolean.TRUE)
    array1.add(java.lang.Boolean.FALSE)
    assertEquals("[\n \"abc\\u0010a/\",\n 123,\n 222.123,\n true,\n false\n]", Json.toJson(array1))
    val string = "[\n \"abc\\u0010a/\",\n 123,\n 222.123,\n true,\n false\n]"
    assertEquals("[\n \"abc\\u0010a/\",\n 123,\n 222.123,\n true,\n false\n]",
                 Json.toJson(Json.fromJson(string) as List<Any>))
  }
  @Test
  fun testDecodeUnicode() {
    assertEquals("[\n \"abc\u0A00\"\n]", Json.toJson(Json.fromJson("[\"abc\\u0a00\"]") as List<Any>))
    assertEquals("[\n \"abc\u0A00\"\n]", Json.toJson(Json.fromJson("[\"abc\\u0A00\"]") as List<Any>))
  }
  @Test
  fun testJsonDecodeCyrillic() {
    assertEquals("[\n \"Текст на русском\"\n]", Json.toJson(Json.fromJson("[\"Текст на русском\"]") as List<Any>))
  }
*/
  @Test
  fun testDecodeSpecialCharacter() {
    assertEquals("{\n  \"description\": \"c:\\\\userDescription.txt\"\n}", Json.toJson(
      Json.fromJson("{\"description\":\"c:\\userDescription.txt\"}") as Map<String, Any?>))
    assertEquals("{description=c:\\userDescription.txt}", Json.fromJson(
      Json.toJson(object:LinkedHashMap<String, String>() {
        init{
          put("description", "c:\\userDescription.txt")
        }
      })).toString())
  }
  @Test
  fun testDecodeUnicode1() {
    assertEquals("[abc\\u0$00]", Json.fromJson("[\"abc\\u0$00\"]").toString())
  }
  @Test
  fun testDecodeUnicode2() {
    assertEquals("[abc\\u001g/]", Json.fromJson("[\"abc\\u001g\\/\"]").toString())
  }
  @Test
  fun testDecodeUnicode3() {
    assertEquals("[abc\\u001G/]", Json.fromJson("[\"abc\\u001G\\/\"]").toString())
  }
  @Test
  fun formatJson() {
    assertEquals("{\n   \"a\": {\n   }\n}", Json.formatJson("{\n \"a\": {\n }\n}"))
    assertEquals("[\n]", Json.formatJson("[]"))
    assertEquals("{\n    \"a\": {\n    }\n}",
                 Json.formatJson("{\n  \"a\": {\n  }\n}", Json.JsonStringBuilder.Step.FOUR_SPACES))
    assertEquals("{\"a\":{}}",
                 Json.formatJson("{\n  \"a\": {\n  }\n}", Json.JsonStringBuilder.Step.COMPACT))
    assertEquals("{\n\t\"a\": {\n\t}\n}",
                 Json.formatJson("{\n  \"a\": {\n  }\n}", Json.JsonStringBuilder.Step.TABS))
  }
  @Test
  fun toJsonJavaFromMap() {
    val testMap = LinkedHashMap<String, String>()
    testMap.put("First item", "1")
    testMap.put("Second item", "2")
    assertEquals("\"{\\n\"\n"
                + " + \"  \\\"First item\\\": \\\"1\\\",\\n\"\n"
                + " + \"  \\\"Second item\\\": \\\"2\\\"\\n\"\n"
                + " + \"}\";", Json.toJsonJavaString(testMap))
    assertEquals("\"[\\n\"\n"
                + " + \"  \\\"First item\\\",\\n\"\n"
                + " + \"  \\\"Second item\\\"\\n\"\n"
                + " + \"]\";", Json.toJsonJavaString(Arrays.asList("First item", "Second item")))
//    assertEquals("\"null\";", U.toJsonJavaString(null as Map?))
  }
  @Test(expected = Json.ParseException::class)
  fun testDecodeParseErr1() {
    Json.fromJson("$")
  }
  @Test(expected = Json.ParseException::class)
  fun testDecodeParseErr2() {
    Json.fromJson("[\"value\"")
  }
  @Test(expected = Json.ParseException::class)
  fun testDecodeParseErr3() {
    Json.fromJson("{\"value\":123")
  }
  @Test(expected = Json.ParseException::class)
  fun testDecodeParseErr4() {
    Json.fromJson("{\"value\"123")
  }
  @Test(expected = Json.ParseException::class)
  fun testDecodeParseErr5() {
    Json.fromJson("{value")
  }
  @Test(expected = Json.ParseException::class)
  fun testDecodeParseErr6() {
    Json.fromJson("[ture]")
  }
  @Test(expected = Json.ParseException::class)
  fun testDecodeParseErr8() {
    Json.fromJson("[\"\\abc\"]")
  }
  @Test(expected = Json.ParseException::class)
  fun testDecodeParseErr9() {
    Json.fromJson("[123ea]")
  }
  @Test(expected = Json.ParseException::class)
  fun testDecodeParseErr10() {
    try
    {
      Json.fromJson("[123.a]")
      fail("Expected ParseException")
    }
    catch (ex:Json.ParseException) {
      ex.offset
      ex.line
      ex.column
      throw ex
    }
  }
  @Test(expected = Json.ParseException::class)
  fun testDecodeParseErr11() {
    Json.fromJson("[1g]")
  }
  @Test(expected = Json.ParseException::class)
  fun testDecodeParseErr12() {
    Json.fromJson("[--1")
  }
  @Test(expected = Json.ParseException::class)
  fun testDecodeParseErr13() {
    Json.fromJson("[\"abc\u0010\"]")
  }
  @Test(expected = Json.ParseException::class)
  fun testDecodeParseErr14() {
    Json.fromJson("[\"abc\"][]")
  }
}