package org.christopherfrantz.dbscan


import org.junit.Test
import java.util.ArrayList

class DBSCANClusteringTest {
  class Vec2(var x: Float, var y: Float)

  @Test
  fun test1() {

    val numbers = ArrayList<Vec2>()

    numbers.add(Vec2(0f, 1f))
    numbers.add(Vec2(0f, 2f))
    numbers.add(Vec2(1f, 2f))

    numbers.add(Vec2(10f, 11f))
    numbers.add(Vec2(10f, 12f))
    numbers.add(Vec2(11f, 12f))

    numbers.add(Vec2(20f, 11f))
    numbers.add(Vec2(20f, 12f))
    numbers.add(Vec2(21f, 12f))

    numbers.add(Vec2(30f, 21f))
    numbers.add(Vec2(30f, 22f))
    numbers.add(Vec2(31f, 22f))

    numbers.add(Vec2(40f, 31f))
    numbers.add(Vec2(40f, 32f))
    numbers.add(Vec2(41f, 32f))

    numbers.add(Vec2(50f, 31f))
    numbers.add(Vec2(50f, 32f))
    numbers.add(Vec2(51f, 32f))

    for (minCluster in 2 until 10) {
      for (maxDistance in 1 until 20) {
        val clustering = DBSCANClustering(numbers, minCluster, maxDistance.toDouble()) { v1, v2 ->
          Math.sqrt(Math.pow((v1.x - v2.x).toDouble(), 2.0) + Math.pow((v1.y - v2.y).toDouble(), 2.0))
        }
        val arrayLists: ArrayList<ArrayList<Vec2>> = clustering.performClustering()
        println(minCluster.toString() + "," + maxDistance + " -> " + arrayLists.size)
      }
    }
  }
}