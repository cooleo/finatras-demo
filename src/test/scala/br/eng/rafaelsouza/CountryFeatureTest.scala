package br.eng.rafaelsouza

import com.google.common.net.MediaType
import com.twitter.finatra.http.{EmbeddedHttpServer, request}
import com.twitter.inject.server.FeatureTest
import com.twitter.finagle.http.{Response, Status}

/**
  * Created by Rafael Souza on 20/07/16.
  */

class CountryFeatureTest extends FeatureTest{

  override val server = new EmbeddedHttpServer(new Server())

  "Country Test" should {

    "list countries" in {
      val jsonResponse = """[{"id":1,"name":"Brasil","code":"BR"},{"id":2,"name":"Bermuda","code":"BM"},{"id":3,"name":"Chile","code":"CL"}]"""
      server.httpGet("/countries", MediaType.JSON_UTF_8, andExpect = Status.Ok, withJsonBody = jsonResponse)
    }

    "lookup country" in {
      val jsonResponse = """{"id":1,"name":"Brasil","code":"BR"}"""
      server.httpGet("/countries/1", MediaType.JSON_UTF_8, andExpect = Status.Ok, withJsonBody = jsonResponse)
    }

  }


}
