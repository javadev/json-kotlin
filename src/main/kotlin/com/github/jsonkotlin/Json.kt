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

object Json {
  private const val NULL = "null"
  class JsonStringBuilder {
    private val builder:StringBuilder
    var identStep:Step
    val type:Type
    private var ident:Int = 0
    enum class Step(ident:Int) {
      TWO_SPACES(2), THREE_SPACES(3), FOUR_SPACES(4), COMPACT(0), TABS(1);
      var ident:Int = 0
      init{
        this.ident = ident
      }
    }
    enum class Type(initial:String, newLine:String, tailLine:String, wrapLine:String) {
      PURE("", "\n", "", "\""), JAVA("\"", "\\n\"\n + \"", "\";", "\\\"");
      val initial:String
      val newLine:String
      val tailLine:String
      val wrapLine:String
      init{
        this.initial = initial
        this.newLine = newLine
        this.tailLine = tailLine
        this.wrapLine = wrapLine
      }
    }
    constructor(identStep:Step) {
      builder = StringBuilder(Type.PURE.initial)
      this.identStep = identStep
      this.type = Type.PURE
    }
    constructor(type:Type) {
      builder = StringBuilder(type.initial)
      this.identStep = Step.TWO_SPACES
      this.type = type
    }
    constructor() {
      builder = StringBuilder()
      this.identStep = Step.TWO_SPACES
      this.type = Type.PURE
    }
    fun append(character:Char):JsonStringBuilder {
      builder.append(character)
      return this
    }
    fun append(string:String):JsonStringBuilder {
      builder.append(string)
      return this
    }
    fun fillSpaces():JsonStringBuilder {
      var index = 0
      while (index < ident)
      {
        builder.append(if (identStep == Step.TABS) '\t' else ' ')
        index += 1
      }
      return this
    }
    fun incIdent():JsonStringBuilder {
      ident += identStep.ident
      return this
    }
    fun decIdent():JsonStringBuilder {
      ident -= identStep.ident
      return this
    }
    fun newLine():JsonStringBuilder {
      if (identStep != Step.COMPACT)
      {
        builder.append(type.newLine)
      }
      return this
    }
    override fun toString():String {
      return builder.toString() + type.tailLine
    }
  }
  object JsonArray {
    fun writeJson(collection:Collection<*>, builder:JsonStringBuilder) {
      val iter = collection.iterator()
      builder.append('[').incIdent()
      if (!collection.isEmpty())
      {
        builder.newLine()
      }
      while (iter.hasNext())
      {
        val value = iter.next()
        builder.fillSpaces()
        JsonValue.writeJson(value, builder)
        if (iter.hasNext())
        {
          builder.append(',').newLine()
        }
      }
      builder.newLine().decIdent().fillSpaces().append(']')
    }
    fun writeJson(array:ByteArray, builder:JsonStringBuilder) {
      if (array.isEmpty())
      {
        builder.append("[]")
      }
      else
      {
        builder.append('[').incIdent().newLine()
        builder.fillSpaces().append((array[0]).toString())
        for (i in 1 until array.size)
        {
          builder.append(',').newLine().fillSpaces()
          builder.append((array[i]).toString())
        }
        builder.newLine().decIdent().fillSpaces().append(']')
      }
    }
    fun writeJson(array:ShortArray, builder:JsonStringBuilder) {
      if (array.isEmpty())
      {
        builder.append("[]")
      }
      else
      {
        builder.append('[').incIdent().newLine()
        builder.fillSpaces().append((array[0]).toString())
        for (i in 1 until array.size)
        {
          builder.append(',').newLine().fillSpaces()
          builder.append((array[i]).toString())
        }
        builder.newLine().decIdent().fillSpaces().append(']')
      }
    }
    fun writeJson(array:IntArray, builder:JsonStringBuilder) {
      if (array.size == 0)
      {
        builder.append("[]")
      }
      else
      {
        builder.append('[').incIdent().newLine()
        builder.fillSpaces().append((array[0]).toString())
        for (i in 1 until array.size)
        {
          builder.append(',').newLine().fillSpaces()
          builder.append((array[i]).toString())
        }
        builder.newLine().decIdent().fillSpaces().append(']')
      }
    }
    fun writeJson(array:LongArray, builder:JsonStringBuilder) {
      if (array.size == 0)
      {
        builder.append("[]")
      }
      else
      {
        builder.append('[').incIdent().newLine()
        builder.fillSpaces().append((array[0]).toString())
        for (i in 1 until array.size)
        {
          builder.append(',').newLine().fillSpaces()
          builder.append((array[i]).toString())
        }
        builder.newLine().decIdent().fillSpaces().append(']')
      }
    }
    fun writeJson(array:FloatArray, builder:JsonStringBuilder) {
      if (array.size == 0)
      {
        builder.append("[]")
      }
      else
      {
        builder.append('[').incIdent().newLine()
        builder.fillSpaces().append((array[0]).toString())
        for (i in 1 until array.size)
        {
          builder.append(',').newLine().fillSpaces()
          builder.append((array[i]).toString())
        }
        builder.newLine().decIdent().fillSpaces().append(']')
      }
    }
    fun writeJson(array:DoubleArray, builder:JsonStringBuilder) {
      if (array.size == 0)
      {
        builder.append("[]")
      }
      else
      {
        builder.append('[').incIdent().newLine()
        builder.fillSpaces().append((array[0]).toString())
        for (i in 1 until array.size)
        {
          builder.append(',').newLine().fillSpaces()
          builder.append((array[i]).toString())
        }
        builder.newLine().decIdent().fillSpaces().append(']')
      }
    }
    fun writeJson(array:BooleanArray, builder:JsonStringBuilder) {
      if (array.size == 0)
      {
        builder.append("[]")
      }
      else
      {
        builder.append('[').incIdent().newLine()
        builder.fillSpaces().append((array[0]).toString())
        for (i in 1 until array.size)
        {
          builder.append(',').newLine().fillSpaces()
          builder.append((array[i]).toString())
        }
        builder.newLine().decIdent().fillSpaces().append(']')
      }
    }
    fun writeJson(array:CharArray, builder:JsonStringBuilder) {
      if (array.size == 0)
      {
        builder.append("[]")
      }
      else
      {
        builder.append('[').incIdent().newLine()
        builder.fillSpaces().append('\"').append((array[0]).toString()).append('\"')
        for (i in 1 until array.size)
        {
          builder.append(',').newLine().fillSpaces()
          builder.append('\"').append((array[i]).toString()).append('\"')
        }
        builder.newLine().decIdent().fillSpaces().append(']')
      }
    }
    fun writeJson(array:Array<Any>, builder:JsonStringBuilder) {
      if (array.size == 0)
      {
        builder.append("[]")
      }
      else
      {
        builder.append('[').newLine().incIdent().fillSpaces()
        JsonValue.writeJson(array[0], builder)
        for (i in 1 until array.size)
        {
          builder.append(',').newLine().fillSpaces()
          JsonValue.writeJson(array[i], builder)
        }
        builder.newLine().decIdent().fillSpaces().append(']')
      }
    }
  }
  object JsonObject {
    fun writeJson(map:Map<*, *>, builder:JsonStringBuilder) {
      val iter = map.entries.iterator()
      builder.append('{').incIdent()
      if (!map.isEmpty())
      {
        builder.newLine()
      }
      while (iter.hasNext())
      {
        val entry = iter.next() as Map.Entry<*, *>
        builder.fillSpaces().append(builder.type.wrapLine)
        builder.append(JsonValue.escape((entry.key).toString()))
        builder.append(builder.type.wrapLine)
        builder.append(':')
        if (builder.identStep != JsonStringBuilder.Step.COMPACT)
        {
          builder.append(' ')
        }
        JsonValue.writeJson(entry.value, builder)
        if (iter.hasNext())
        {
          builder.append(',').newLine()
        }
      }
      builder.newLine().decIdent().fillSpaces().append('}')
    }
  }
  object JsonValue {
    @Suppress("UNCHECKED_CAST")
    fun writeJson(value:Any?, builder:JsonStringBuilder) {
      if (value == null)
      {
        builder.append(NULL)
      }
      else if (value is String)
      {
        builder.append(builder.type.wrapLine)
        .append(escape(value)).append(builder.type.wrapLine)
      }
      else if (value is Double)
      {
        if (value.isInfinite() || value.isNaN())
        {
          builder.append(NULL)
        }
        else
        {
          builder.append(value.toString())
        }
      }
      else if (value is Float)
      {
        if (value.isInfinite() || value.isNaN())
        {
          builder.append(NULL)
        }
        else
        {
          builder.append(value.toString())
        }
      }
      else if (value is Number)
      {
        builder.append(value.toString())
      }
      else if (value is Boolean)
      {
        builder.append(value.toString())
      }
      else if (value is Map<*, *>)
      {
        JsonObject.writeJson(value, builder)
      }
      else if (value is Collection<*>)
      {
        JsonArray.writeJson(value, builder)
      }
      else if (value is ByteArray)
      {
        JsonArray.writeJson(value, builder)
      }
      else if (value is ShortArray)
      {
        JsonArray.writeJson(value, builder)
      }
      else if (value is IntArray)
      {
        JsonArray.writeJson(value, builder)
      }
      else if (value is LongArray)
      {
        JsonArray.writeJson(value, builder)
      }
      else if (value is FloatArray)
      {
        JsonArray.writeJson(value, builder)
      }
      else if (value is DoubleArray)
      {
        JsonArray.writeJson(value, builder)
      }
      else if (value is BooleanArray)
      {
        JsonArray.writeJson(value, builder)
      }
      else if (value is CharArray)
      {
        JsonArray.writeJson(value, builder)
      }
      else if (value is Array<*>)
      {
        JsonArray.writeJson(value as Array<Any>, builder)
      }
      else
      {
        builder.append(value.toString())
      }
    }
    fun escape(s:String):String {
      val sb = StringBuilder()
      escape(s, sb)
      return sb.toString()
    }
    private fun escape(s:String, sb:StringBuilder) {
      val len = s.length
      for (i in 0 until len)
      {
        val ch = s.get(i)
        when (ch) {
          '"' -> sb.append("\\\"")
          '\\' -> sb.append("\\\\")
          '\b' -> sb.append("\\b")
          '\n' -> sb.append("\\n")
          '\r' -> sb.append("\\r")
          '\t' -> sb.append("\\t")
          '€' -> sb.append("€")
          else -> if ((ch <= '\u001F' || ch >= '\u007F' && ch <= '\u009F'
                       || ch >= '\u2000' && ch <= '\u20FF'))
          {
            val ss = Integer.toHexString(ch.code)
            sb.append("\\u")
            for (k in 0 until 4 - ss.length)
            {
              sb.append('0')
            }
            sb.append(ss.toUpperCase())
          }
          else
          {
            sb.append(ch)
          }
        }
      }
    }
  }
  class ParseException(message:String, offset:Int, line:Int, column:Int):RuntimeException(message + " at " + line + ":" + column) {
    var offset:Int = 0
    var line:Int = 0
    var column:Int = 0
    init{
      this.offset = offset
      this.line = line
      this.column = column
    }
  }
  class JsonParser(string:String) {
    private var json:String
    private var index:Int = 0
    private var line:Int = 0
    private var lineOffset:Int = 0
    private var current:Int = 0
    private var captureBuffer:StringBuilder = StringBuilder()
    private var captureStart:Int = 0
    private var isWhiteSpace:Boolean = false
    get() {
      return current == ' '.code || current == '\t'.code || current == '\n'.code || current == '\r'.code
    }
    private val isDigit:Boolean
    get() {
      return current >= '0'.code && current <= '9'.code
    }
    private val isHexDigit:Boolean
    get() {
      return (isDigit || current >= 'a'.code && current <= 'f'.code || (current >= 'A'.code && current <= 'F'.code))
    }
    private val isEndOfText:Boolean
    get() {
      return current == -1
    }
    init{
      this.json = string
      line = 1
      captureStart = -1
    }
    fun parse():Any? {
      read()
      skipWhiteSpace()
      val result = readValue()
      skipWhiteSpace()
      if (!isEndOfText)
      {
        throw error("Unexpected character")
      }
      return result
    }
    private fun readValue():Any? {
      when (current) {
        'n'.code -> return readNull()
        't'.code -> return readTrue()
        'f'.code -> return readFalse()
        '"'.code -> return readString()
        '['.code -> return readArray()
        '{'.code -> return readObject()
        '-'.code, '0'.code, '1'.code, '2'.code, '3'.code, '4'.code,
        '5'.code, '6'.code, '7'.code, '8'.code, '9'.code -> return readNumber()
        else -> throw expected("value")
      }
    }
    private fun readArray():List<Any?> {
      read()
      val array = ArrayList<Any?>()
      skipWhiteSpace()
      if (readChar(']'))
      {
        return array
      }
      do
      {
        skipWhiteSpace()
        array.add(readValue())
        skipWhiteSpace()
      }
      while (readChar(','))
      if (!readChar(']'))
      {
        throw expected("',' or ']'")
      }
      return array
    }
    private fun readObject():MutableMap<String, Any?> {
      read()
      val `object` = LinkedHashMap<String, Any?>()
      skipWhiteSpace()
      if (readChar('}'))
      {
        return `object`
      }
      do
      {
        skipWhiteSpace()
        val name = readName()
        skipWhiteSpace()
        if (!readChar(':'))
        {
          throw expected("':'")
        }
        skipWhiteSpace()
        `object`.put(name, readValue())
        skipWhiteSpace()
      }
      while (readChar(','))
      if (!readChar('}'))
      {
        throw expected("',' or '}'")
      }
      return `object`
    }
    private fun readName():String {
      if (current != '"'.code)
      {
        throw expected("name")
      }
      return readString()
    }
    private fun readNull():String? {
      read()
      readRequiredChar('u')
      readRequiredChar('l')
      readRequiredChar('l')
      return null
    }
    private fun readTrue():Boolean {
      read()
      readRequiredChar('r')
      readRequiredChar('u')
      readRequiredChar('e')
      return java.lang.Boolean.TRUE
    }
    private fun readFalse():Boolean {
      read()
      readRequiredChar('a')
      readRequiredChar('l')
      readRequiredChar('s')
      readRequiredChar('e')
      return java.lang.Boolean.FALSE
    }
    private fun readRequiredChar(ch:Char) {
      if (!readChar(ch))
      {
        throw expected("'" + ch + "'")
      }
    }
    private fun readString():String {
      read()
      startCapture()
      while (current != '"'.code)
      {
        if (current == '\\'.code)
        {
          pauseCapture()
          readEscape()
          startCapture()
        }
        else if (current < 0x20)
        {
          throw expected("valid string character")
        }
        else
        {
          read()
        }
      }
      val string = endCapture()
      read()
      return string
    }
    private fun readEscape() {
      read()
      when (current) {
        '"'.code, '/'.code, '\\'.code -> captureBuffer.append(current.toChar())
        'b'.code -> captureBuffer.append('\b')
        'n'.code -> captureBuffer.append('\n')
        'r'.code -> captureBuffer.append('\r')
        't'.code -> captureBuffer.append('\t')
        'u'.code -> {
          val hexChars = CharArray(4)
          var isHexCharsDigits = true
          for (i in 0..3)
          {
            read()
            if (!isHexDigit)
            {
              isHexCharsDigits = false
            }
            hexChars[i] = current.toChar()
          }
          if (isHexCharsDigits)
          {
            captureBuffer.append(Integer.parseInt(String(hexChars), 16).toChar())
          }
          else
          {
            captureBuffer.append("\\u").append(hexChars[0]).append(hexChars[1]).append(hexChars[2])
            .append(hexChars[3])
          }
        }
        else -> throw expected("valid escape sequence")
      }
      read()
    }
    private fun readNumber():Number {
      startCapture()
      readChar('-')
      val firstDigit = current
      if (!readDigit())
      {
        throw expected("digit")
      }
      if (firstDigit != '0'.code)
      {
        while (readDigit())
        {}
      }
      readFraction()
      readExponent()
      val number = endCapture()
      val result:Number
      if (number.contains(".") || number.contains("e") || number.contains("E"))
      {
        if (number.length > 9)
        {
          result = java.math.BigDecimal(number)
        }
        else
        {
          result = java.lang.Double.valueOf(number)
        }
      }
      else
      {
        if (number.length > 20)
        {
          result = java.math.BigInteger(number)
        }
        else
        {
          result = java.lang.Long.valueOf(number)
        }
      }
      return result
    }
    private fun readFraction():Boolean {
      if (!readChar('.'))
      {
        return false
      }
      if (!readDigit())
      {
        throw expected("digit")
      }
      while (readDigit())
      {}
      return true
    }
    private fun readExponent():Boolean {
      if (!readChar('e') && !readChar('E'))
      {
        return false
      }
      if (!readChar('+'))
      {
        readChar('-')
      }
      if (!readDigit())
      {
        throw expected("digit")
      }
      while (readDigit())
      {}
      return true
    }
    private fun readChar(ch:Char):Boolean {
      if (current != ch.code)
      {
        return false
      }
      read()
      return true
    }
    private fun readDigit():Boolean {
      if (!isDigit)
      {
        return false
      }
      read()
      return true
    }
    private fun skipWhiteSpace() {
      while (isWhiteSpace)
      {
        read()
      }
    }
    private fun read() {
      if (index == json.length)
      {
        current = -1
        return
      }
      if (current == '\n'.code)
      {
        line++
        lineOffset = index
      }
      current = json.get(index++).code
    }
    private fun startCapture() {
      captureStart = index - 1
    }
    private fun pauseCapture() {
      captureBuffer.append(json.substring(captureStart, index - 1))
      captureStart = -1
    }
    private fun endCapture():String {
      val end = if (current == -1) index else index - 1
      val captured:String
      if (captureBuffer.length > 0)
      {
        captureBuffer.append(json.substring(captureStart, end))
        captured = captureBuffer.toString()
        captureBuffer.setLength(0)
      }
      else
      {
        captured = json.substring(captureStart, end)
      }
      captureStart = -1
      return captured
    }
    private fun expected(expected:String):ParseException {
      if (isEndOfText)
      {
        return error("Unexpected end of input")
      }
      return error("Expected " + expected)
    }
    private fun error(message:String):ParseException {
      val absIndex = index
      val column = absIndex - lineOffset
      val offset = if (isEndOfText) absIndex else absIndex - 1
      return ParseException(message, offset, line, column - 1)
    }
  }
  @JvmOverloads fun toJson(collection:Collection<*>, identStep:JsonStringBuilder.Step = JsonStringBuilder.Step.TWO_SPACES):String {
    val builder = JsonStringBuilder(identStep)
    JsonArray.writeJson(collection, builder)
    return builder.toString()
  }
  @JvmOverloads fun toJson(map:Map<*, *>, identStep:JsonStringBuilder.Step = JsonStringBuilder.Step.TWO_SPACES):String {
    val builder = JsonStringBuilder(identStep)
    JsonObject.writeJson(map, builder)
    return builder.toString()
  }
  fun toJsonJavaString(collection:Collection<*>):String {
    val builder = JsonStringBuilder(JsonStringBuilder.Type.JAVA)
    JsonArray.writeJson(collection, builder)
    return builder.toString()
  }
  fun toJsonJavaString(map:Map<*, *>):String {
    val builder = JsonStringBuilder(JsonStringBuilder.Type.JAVA)
    JsonObject.writeJson(map, builder)
    return builder.toString()
  }
  fun fromJson(string:String):Any? {
    return JsonParser(string).parse()
  }
  fun formatJson(json:String, identStep:JsonStringBuilder.Step):String {
    val result = fromJson(json)
    if (result is Map<*, *>)
    {
      return toJson(result, identStep)
    }
    return toJson(result as List<*>, identStep)
  }
  fun formatJson(json:String):String {
    return formatJson(json, JsonStringBuilder.Step.THREE_SPACES)
  }
}
