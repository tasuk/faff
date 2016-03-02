package controllers

import play.api.mvc._

object WordPair extends Controller {
  def index = Action {
    Ok("Display word pairs. One day. Maybe.")
  }
}
