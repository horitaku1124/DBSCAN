package org.christopherfrantz.dbscan

import com.github.horitaku1124.util.CSVReader
import org.junit.Test
import java.io.IOException
import java.util.ArrayList
import kotlin.math.pow
import kotlin.math.sqrt

class ClusteringIris {

  internal class IrisData {
    var a: Float = 0f
    var b: Float = 0f
    var c: Float = 0f
    var d: Float = 0f
    var name: String? = null
  }

  @Test
  fun test1() {
    val reader = CSVReader("./data/iris.csv", 1)
    val data2 = reader.readAll { s ->
      val rows = s.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
      val data = IrisData()
      data.a = java.lang.Float.parseFloat(rows[0])
      data.b = java.lang.Float.parseFloat(rows[1])
      data.c = java.lang.Float.parseFloat(rows[2])
      data.d = java.lang.Float.parseFloat(rows[3])
      data.name = rows[4]
      data
    }
    for (minCluster in 2 until 10) {
      for (maxDistance in 1 until 20) {
        var distance = maxDistance.toDouble() / 10
        val clustering = DBSCANClustering(data2, minCluster, distance) { v1, v2 ->
          sqrt((v1.a - v2.a).toDouble().pow(2.0)
              + (v1.b - v2.b).toDouble().pow(2.0)
              + (v1.c - v2.c).toDouble().pow(2.0)
              + (v1.d - v2.d).toDouble().pow(2.0))
        }
        val arrayLists: ArrayList<ArrayList<IrisData>> = clustering.performClustering()
        println(minCluster.toString() + "," + distance + " -> " + arrayLists.size)
      }
    }
  }
}