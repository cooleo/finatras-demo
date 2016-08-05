package coole.co.finatra

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.http.{Controller, HttpServer}


/**
  * Created by Rafael Souza on 19/07/16.
  */
class Server extends HttpServer {
  override protected def configureHttp(router: HttpRouter) {
    router.add[MyController]
  }
}

object App extends Server

class MyController extends Controller {

  var coutries:List[Country] = List(Country(1, "Brasil", "BR"), Country(2, "Bermuda", "BM"), Country(3, "Chile", "CL"))

  get("/ping") { request: Request =>
    "pong"
  }

  get("/countries") { request: Request =>
    coutries
  }

  post("/countries") { request: Country =>
    coutries =  request :: coutries
    response.created(request.id)
  }

  get("/countries/:id"){ request: Request =>
    println(request.params)
    println(request.getParam("id"))
    coutries.filter(country => country.id == request.getIntParam("id")).head
  }
}

case class Country (id: Int, name: String, code: String)