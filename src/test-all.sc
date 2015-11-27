import java.io.FileInputStream

import scala.collection.JavaConversions._

import com.ankor.asup._
import com.ankor.asup.text.{BodyParser, AsupBodyGrammar, AsupMailData}
import java.util.Properties


val props = new Properties()

props.load(new FileInputStream("/home/ozlevka/projects/asup-scala-disk-parser/src/running.properties"))


val src = props.getProperty("messages.source.directory")
val tmp = props.getProperty("messages.temporary.directory")

val cmd = props.getProperty("unarchive.command.template").format(src,tmp)



