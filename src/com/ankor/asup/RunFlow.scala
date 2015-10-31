package com.ankor.asup

import java.io.{FileOutputStream, File}

import scala.collection.JavaConversions._

import com.auxilii.msgparser.MsgParser
import com.auxilii.msgparser.attachment.FileAttachment
import org.apache.commons.io.filefilter.WildcardFileFilter

import scala.io.Source

import sys.process._


class RunFlow {

}



object RunFlow extends RunFlow {
   def main(args: Array[String]) = {
     val path = "/home/ozlevka/data"
     val file = new File(path)
     val filter  = new WildcardFileFilter("*.msg")
     file.list(filter) foreach( x => {
       val message = (new MsgParser()).parseMsg(path + "/" + x)
       val attachments = message.getAttachments() filter( f => {
         f.asInstanceOf[FileAttachment].getFilename contains "body.7z"
       })
       if (attachments.size > 0) {
         val file_name = "/home/ozlevka/data/tmp/body.7z"
         println(file_name)
         val stream = new FileOutputStream(file_name)
         stream.write(attachments(0).asInstanceOf[FileAttachment].getData)
         stream.close()

         "7z e /home/ozlevka/data/tmp/body.7z -oc:/home/ozlevka/data/tmp".!
         val lines = Source.fromFile("/home/ozlevka/data/tmp/DF-A.txt").getLines filter ( l => {
           !l.contains("snapshot")
         })

         new File("/home/ozlevka/data/tmp").listFiles foreach( f => {
           f.delete()
         })
       } else {
       }
     })
   }
}
