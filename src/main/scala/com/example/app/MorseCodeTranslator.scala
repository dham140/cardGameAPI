package com.example.app

/**
  * Created by christopher.odell on 1/26/16.
  */
object MorseCodeTranslator extends Translator{
  override def translate(input: String): String = {

    val sounds = Seq("beeeep", "beep")

    val beeps = Map('A' -> Seq(1,0),
      'B' -> Seq(0, 1, 1, 1),
      'C' -> Seq(0, 1, 0, 1),
      'D' -> Seq(0, 1, 1),
      'E' -> Seq(1),
      'F' -> Seq(1, 1, 0, 1),
      'G' -> Seq(0, 0, 1),
      'H' -> Seq(1, 1, 1, 1),
      'I' -> Seq(1, 1),
      'J' -> Seq(1, 0, 0, 0),
      'K' -> Seq(0, 1, 0),
      'L' -> Seq(1, 0, 1, 1),
      'M' -> Seq(0, 0),
      'N' -> Seq(0, 1),
      'O' -> Seq(0, 0, 0),
      'P' -> Seq(1, 0, 0, 1),
      'Q' -> Seq(0, 0, 1, 0),
      'R' -> Seq(1, 0, 1),
      'S' -> Seq(1, 1, 1),
      'T' -> Seq(0),
      'U' -> Seq(1, 1, 0),
      'V' -> Seq(1, 1, 1, 0),
      'W' -> Seq(1, 0, 0),
      'X' -> Seq(0, 1, 1, 0),
      'Y' -> Seq(0, 1, 0, 0),
      'Z' -> Seq(0, 0, 1, 1),
      '0' -> Seq(0, 0, 0, 0, 0),
      '1' -> Seq(1, 0, 0, 0, 0),
      '2' -> Seq(1, 1, 0, 0, 0),
      '3' -> Seq(1, 1, 1, 0, 0),
      '4' -> Seq(1, 1, 1, 1, 0),
      '5' -> Seq(1, 1, 1, 1, 1),
      '6' -> Seq(0, 1, 1, 1, 1),
      '7' -> Seq(0, 0, 1, 1, 1),
      '8' -> Seq(0, 0, 0, 1, 1),
      '9' -> Seq(0, 0, 0, 0, 1),
      '.' -> Seq(1, 0, 1, 0, 1, 0),
      ',' -> Seq(0, 0, 1, 1, 0, 0),
      ':' -> Seq(0, 0, 0, 1, 1, 1),
      '?' -> Seq(1, 1, 0, 0, 1, 1),
      '\\' -> Seq(1, 0, 0, 0, 0, 1),
    '-' -> Seq(0, 1, 1, 1, 1, 0),
    '/' -> Seq(0, 1, 1, 0, 1),
    '(' -> Seq(0, 1, 0, 0, 1, 0),
    ')' -> Seq(0, 1, 0, 0, 1, 0),
    '"' -> Seq(1, 0, 1, 1, 0, 1),
    '@' -> Seq(1, 0, 0, 1, 0, 1),
    '=' -> Seq(0, 1, 1, 1, 0),
    ' ' -> Seq())

    var temp:Seq[Seq[Int]] = null
    try{
      temp = input.toUpperCase.toCharArray.map(x =>
        beeps.contains(x) match {
          case true => beeps(x):Seq[Int]
          case false => throw new NoSuchElementException("Unsupported character: '" + x + "'");
        })
    }
    catch{
      case e: Exception => return input.toUpperCase + "\n\n" + e.toString

    }


        val result = temp.flatMap(x => x.map(y => sounds(y)))

        result.mkString(" ")


  }
}
