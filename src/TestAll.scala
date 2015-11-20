/**
 * Created by ozlevka on 10/30/15.
 */

import com.ankor.asup.RunFlow
import com.ankor.asup.elastic.JestWrapper
import com.ankor.asup.text.{AsupMailData, TextParser, FileParser}
import com.auxilii.msgparser.MsgParser
import com.auxilii.msgparser.attachment.FileAttachment
import org.apache.commons.io.filefilter.WildcardFileFilter
import scala.collection.JavaConversions._
import scala.io.Source
import sys.process._


import java.io.{FileOutputStream, ByteArrayInputStream,  File}

object TestAll {
    def main(args: Array[String]) = {
        val buffer = RunFlow("/home/ozlevka/newdisk/data/asup", "/home/ozlevka/newdisk/data/asup/tmp").openMessages

        println (JestWrapper("http://localhost:9200").save(buffer.toList, "asup"))


        //FileParser("/home/ozlevka/newdisk/data/tmp/DF-A.txt") process

        //func
    }


    def func = {

    }
}
