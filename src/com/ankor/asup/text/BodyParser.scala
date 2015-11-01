package com.ankor.asup.text

import scala.util.parsing.combinator.RegexParsers

/**
 * Created by ozlevka on 11/1/15.
 */

class AsupBodyGrammar extends RegexParsers   {
    val number = "[0-9]+" r
    val any = ".+" r
    val endLine = "$" r
    def system_id: Parser[String] = "SYSTEM_ID="  ~> number ^^ {_.toString}
    def serial_number: Parser[String] = "SERIAL_NUM=" ~> number ^^ {_.toString}
    def snmp_location:  Parser[String]  = "SNMP_LOCATION=" ~> any <~ endLine ^^ {_.toString}

    def allParsers = Map[String, Parser[String]]("serial_number" -> this.serial_number, "snmp_location" -> this.snmp_location, "system_id" -> this.system_id)
}

trait  SimpleBodyParser {
    val grammar: AsupBodyGrammar
    val lines: Array[String]

    def parse:  Map[String, Any] = {
      lines flatMap (l =>  {
        grammar.allParsers.flatMap (g => {
          val res = grammar.parseAll(g._2, l)
          if(res.successful) {
            Map[String,  String](g._1 -> res.get)
          } else {
            None
          }
        })
      }) toMap
    }
}

class BodyParser(text: String) extends SimpleBodyParser {
    val grammar = new AsupBodyGrammar
    val lines = text.split("\\r?\\n")
}


object BodyParser {
   def apply(text: String) = {
      new BodyParser(text) with SimpleBodyParser
   }
}
