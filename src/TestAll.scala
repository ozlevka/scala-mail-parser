/**
 * Created by ozlevka on 10/30/15.
 */

import com.ankor.asup.text.{TextParser, FileParser}
import com.auxilii.msgparser.MsgParser
import com.auxilii.msgparser.attachment.FileAttachment
import org.apache.commons.io.filefilter.WildcardFileFilter
import scala.collection.JavaConversions._
import scala.io.Source
import sys.process._


import java.io.{FileOutputStream, ByteArrayInputStream,  File}

object TestAll {
    def main(args: Array[String]) = {
       // val text = "Aggregate               kbytes       used      avail capacity  \naggr0               28119185512 27143289652  975895860      97%  \naggr0/.snapshot              0          0          0     ---%  "
        val pres = FileParser("/home/ozlevka/data/DF-A.txt").process()


        println(pres)
    }
}
