package com.ankor.asup.text

/**
 * Created by ozlevka on 10/31/15.
 */

import scala.io.Source
import java.io.File
import scala.reflect.macros.Parsers
import scala.util.parsing.combinator.RegexParsers





class AsupGrammar extends RegexParsers {
  val number = "[0-9]+".r
  val space = "[\\s]?".r
  val aggregation = "aggr[0-9]?".r
  def space_number: Parser[Long] = opt(space) ~> number ^^ {
    _.toLong
  }

  def line: Parser[AsupData] = aggregation ~ rep(space_number) <~ "%" ^^ {
    case x ~ y => {
      new AsupData(x, y(0), y(1), y(2), y(3))
    }
  }
}


class AsupMailData {
   private[this] var _from:String = ""
   private[this] var _subject: String = ""
   private[this] var _serialNumber: String = ""
   private[this] var _systemId: String = ""
   private[this] var _snmpLocation: String = ""

  def from = _from
  def from_= (f: String): Unit = {
       _from = f
   }

  def subject = _subject
   def subject_= (v: String): Unit =  {
      _subject = v
   }

  def serialNumber = _serialNumber
   def serialNumber_= (v: String): Unit = {
      _serialNumber = v
   }
  def systemId = _systemId
   def systemId_= (v: String): Unit = {
     _systemId = v
   }

  def snmpLocation = _snmpLocation
   def snmpLocation_= (v: String): Unit = {
     _snmpLocation = v
   }
}


class AsupData(_name: String, _total: Long, _used: Long, _avaliable: Long, _capacity: Long) extends AsupMailData {
   val name = _name
   val total = _total
   val used = _used
   val avaliable = _avaliable
   val capacity = _capacity
}





trait SimpleParser extends RegexParsers {
  val grammar = new AsupGrammar
  def parse(text: String) = {
      val pres = grammar.parseAll(grammar.line,text)
      if (pres.successful) {
         pres.get
      }
      else {
         None
      }
  }

  def linesIterate(lines: List[String]) = {
    val r = for(line <- lines) yield parse(line)
    r filter(x => { x != None})
  }
}



class TextParser(text: String) extends SimpleParser {
  val newline = "\\r?\\n"

  def process = {
      linesIterate(text.split(newline).toList)
  }
}


class FileParser(f: File) extends SimpleParser {
    val file = f

    def this(path: String) = {
        this(new File(path))
    }

    def process = {
        val src = Source.fromFile(file)
        val lines = src.getLines.toList
        linesIterate(lines)
    }
}


object FileParser {
   def apply(path: String) = {
      new FileParser(path) with SimpleParser
   }
}



object TextParser {
   def apply(text: String) = {
      new TextParser(text) with SimpleParser
   }
}
