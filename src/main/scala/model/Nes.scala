package model

class Nes(val string: Option[String]) extends AnyVal {
    def foo: Nes = string.isEmpty match {
        case true => new Nes(None)
        case false => new Nes(string)
    }
}

