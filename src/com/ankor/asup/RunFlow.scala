package com.ankor.asup

import java.io.{FileOutputStream, File}

import com.ankor.asup.text.{BodyParser, TextParser, AsupData, FileParser}

import scala.collection.JavaConversions._

import com.auxilii.msgparser.{Message, MsgParser}
import com.auxilii.msgparser.attachment.FileAttachment
import org.apache.commons.io.filefilter.WildcardFileFilter

import scala.io.Source

import scala.sys.process._

trait RunFlowFuncs {
    val srcDir: String
    val tmpDir: String
    val commandTemplate: String
    val fileExt = "*.msg"


    def openMessages = {
      val file = new File(srcDir)
      val filter  = new WildcardFileFilter("*.msg")

      file.list(filter) flatMap (x => parseMessage(x))
    }

    def parseMessage(filePath: String) = {
      val message = (new MsgParser()).parseMsg(srcDir + "/" + filePath);
      val attachments = message.getAttachments filter(x => {
        x.asInstanceOf[FileAttachment].getFilename contains("body.7z")
      })

      var res: List[Any] = Nil
      if(attachments.size > 0) {
        val file_name = tmpDir + "/body.7z"
        val stream = new FileOutputStream(file_name)
        stream.write(attachments(0).asInstanceOf[FileAttachment].getData)
        stream.close()
        val command = commandTemplate.format(tmpDir, tmpDir)
        (command) !
        val parser = FileParser(tmpDir + "/DF-A.txt")

        res = parser.process

        new File(tmpDir).listFiles foreach( f => {
          f.delete()
        })

      }
      else {
         val  parser = TextParser(message.getBodyText)
         res = parser.process
      }

      enrichData(res, message)
      res
    }

    def enrichData(objects: List[Any], message: Message) = {
        val enrishment: Map[String,Any] = BodyParser(message.getBodyText).parse

        objects foreach(x => {
             try {
               x match {
                 case t: AsupData => {
                   if (enrishment contains ("serial_number")) t.serialNumber = enrishment("serial_number").toString
                   if (enrishment contains ("snmp_location")) t.snmpLocation = enrishment("snmp_location").toString
                   if (enrishment contains ("system_id")) t.systemId = enrishment("system_id").toString
                   t.from = message.getFromEmail.split("@")(1)
                   t.subject = message.getSubject
                   t.sysDate = message.getDate.getTime
                 }
               }
             }
             catch {
               case e:Exception => e.printStackTrace(); println(enrishment); println(message.getSubject)
             }
        })
    }
}




class RunFlow(_srcDir: String, _tmpDir: String, _command: String) extends RunFlowFuncs  {
    val srcDir = _srcDir
    val tmpDir = _tmpDir
    val commandTemplate = _command
}



object RunFlow {
    def apply(srcDir: String, tmpDir: String, command:String) = {
      new RunFlow(srcDir, tmpDir, command) with RunFlowFuncs
   }
}
