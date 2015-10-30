import org.apache.commons.io.filefilter.WildcardFileFilter
import org.apache.poi.hsmf.MAPIMessage
import org.apache.poi.hsmf.extractor.OutlookTextExtactor
import java.io.{File}



val path = "/home/ozlevka/data"
val file = new File(path)
val filter  = new WildcardFileFilter("*.msg")
file.list(filter) foreach( x => {
    val message = new MAPIMessage(path + "/" + x)
    val extractor = new OutlookTextExtactor(message)
    val attchments = message.getAttachmentFiles filter (f => {
        f.getPOIFSName contains("body.7z")
    })
    if( attchments.size > 0 ) println(attchments)
})

