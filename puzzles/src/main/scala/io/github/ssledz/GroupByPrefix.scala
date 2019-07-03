package io.github.ssledz

import scala.annotation.tailrec

object GroupByPrefix extends App {

  case class Result(root: String, xs: List[String])

  def solution(elems: List[String]): List[Result] = {

    val empty = (None, List.empty)

    @tailrec
    def go(elems: List[String], acc: (Option[Result], List[Result])): List[Result] = elems match {

      case h :: t => acc match {

        case (Some(r@Result(root, xs)), rs) => {

          if (h.startsWith(root)) {

            go(t, (Some(Result(root, h :: xs)), rs))

          } else {

            go(t, (Some(Result(h, List.empty)), r :: rs))

          }

        }
        case (None, result) => go(t, (Some(Result(h, List.empty)), result))

      }

      case Nil => acc match {
        case (Some(r), rs) => r :: rs
        case (_, rs) => rs
      }

    }

    go(elems.sorted, empty)

  }

  val elems = List(
    "AA",
    "A",
    "B",
    "C",
    "AABC",
    "AB"
  )

  println(solution(elems)) // res1: List[Result] = List(Result(C,List()), Result(B,List()), Result(A,List(AB, AABC, AA)))

}
