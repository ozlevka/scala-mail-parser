import com.auxilii.msgparser.MsgParser
import com.auxilii.msgparser.attachment.FileAttachment
import org.apache.commons.io.filefilter.WildcardFileFilter
import scala.collection.JavaConversions._


import java.io.{File}
val path = "/home/ozlevka/data"
val file = new File(path)
val filter  = new WildcardFileFilter("*.msg")
file.list(filter) foreach( x => {
    val message = (new MsgParser()).parseMsg(path + "/" + x)

    val attachments = message.getAttachments() filter( f => {
        f.asInstanceOf[FileAttachment].getFilename contains "body.7z"
    })


})

