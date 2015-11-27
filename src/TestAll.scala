/**
 * Created by ozlevka on 10/30/15.
 */

import com.ankor.asup.RunFlow
import com.ankor.asup.elastic.JestWrapper
import com.ankor.asup.text.{AsupMailData, TextParser, FileParser}
import java.util.Properties


import java.io.{FileInputStream, FileOutputStream, ByteArrayInputStream, File}

object TestAll {
    def main(args: Array[String]) = {

        val props = new Properties()

        props.load(new FileInputStream(System.getProperty("config.file")))

        val buffer = RunFlow(props.getProperty("messages.source.directory"),
                     props.getProperty("messages.temporary.directory"),
                     props.getProperty("unarchive.command.template")).openMessages

        println (JestWrapper(props.getProperty("elastic.host")).save(buffer.toList, props.getProperty("elastic.working.index.template")))
    }
}
