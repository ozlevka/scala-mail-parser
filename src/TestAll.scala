/**
 * Created by ozlevka on 10/30/15.
 */

import com.ankor.asup.RunFlow
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
        val buffer = RunFlow("/home/ozlevka/data", "/home/ozlevka/data/tmp").openMessages

        println(buffer.size)
    }
}
