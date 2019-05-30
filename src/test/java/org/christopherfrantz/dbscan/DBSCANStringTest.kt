package org.christopherfrantz.dbscan

import org.junit.Test
import java.util.ArrayList

class DBSCANStringTest {

  @Test
  fun test1() {
    val vecs = ArrayList<String>()
    vecs.add("abcdefg")
    vecs.add("abcdffg")
    vecs.add("aacdeeg")


    vecs.add("poiuygh")
    vecs.add("poiaygh")
    vecs.add("voiaygh")

    for (minCluster in 2 until 10) {
      for (maxDistance in 1 until 20) {
        val clustering = DBSCANClustering(vecs, minCluster, maxDistance.toDouble()) { v1, v2 ->
          var sum = 0.0
          for (i in 0 until v1.length) {
            sum += Math.pow((v1[i] - v2[i]).toDouble(), 2.0)
          }
          Math.sqrt(sum)
        }
        val arrayLists: ArrayList<ArrayList<String>> = clustering.performClustering()
        println(minCluster.toString() + "," + maxDistance + " -> " + arrayLists.size)
      }
    }
  }
}