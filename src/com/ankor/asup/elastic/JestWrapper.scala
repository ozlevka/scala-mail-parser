package com.ankor.asup.elastic

import com.ankor.asup.text.AsupData
import com.google.gson.Gson
import io.searchbox.client.JestClientFactory
import io.searchbox.client.config.HttpClientConfig
import io.searchbox.core.{Index, Bulk}

/**
 * Created by ozlevka on 11/1/15.
 */



class JestWrapper(host: String) {
  val factory = new JestClientFactory()
  factory.setHttpClientConfig(new HttpClientConfig.Builder(host).build)
  val gson = new Gson();

  def save(data: List[Any], index: String) = {
    val bb = new Bulk.Builder()

    data foreach(d => {
      val json = gson.toJson(d)
      val action = new Index.Builder(json)
                   .index(index)
                   .`type`("asup_entry")
                   .build()
      bb.addAction(action)
    })

    val res = factory.getObject.execute(bb.build)
  }
}

object JestWrapper {
   def apply(host: String) = {
      new JestWrapper(host)
   }
}