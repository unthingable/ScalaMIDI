package de.sciss.midi

import java.io.File

object WriteSequence extends App {
  lazy val sq = {
    val ms  = (64 to 72).flatMap { pch => NoteOn(0, pch, 80) :: NoteOff(0, pch, 0) :: Nil }
    implicit val rate = TickRate.tempo(120, 1024)
    val ev  = ms.zipWithIndex.map { case (m, i) => Event((i * 0.25 * rate.value).toLong, m) }
    val mx  = ev.map(_.tick).max
    val t   = Track(ev, mx + rate.value.toLong) // pad with extra second
    Sequence(Vector(t))
  }

  val path  = args.headOption.getOrElse(sys.props("user.home") + File.separator + "scalamidi_test.mid")
  print(s"Writing to $path...")
  sq.write(path)
  println(" Ok.")
}