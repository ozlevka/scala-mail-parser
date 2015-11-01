import com.ankor.asup._
import com.ankor.asup.text.{BodyParser, AsupBodyGrammar, AsupMailData}




val text: String = "GENERATED_ON=Sun Oct 25 01:05:02 GMT 2015\nVERSION=NetApp Release 8.0.2 7-Mode: Mon Jun 13 14:14:26 PDT 2011\nSYSTEM_ID=0142233235\nSERIAL_NUM=200000347272\nHOSTNAME=cam1netapp01\nSEQUENCE=783\nSNMP_LOCATION=\"Camberley-England\"\nPARTNER_SYSTEM_ID=0142233247\nPARTNER_SERIAL_NUM=<unknown>\nPARTNER_HOSTNAME=cam1netapp02\nBOOT_CLUSTERED=\nCONTENTS_TRUNCATED=false"
val parser = new BodyParser(text)
parser.parse
