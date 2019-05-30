package org.christopherfrantz.dbscan

import java.util.ArrayList
import java.util.HashSet

class DBSCANClustering<V>
/**
 * Creates a DBSCAN clusterer instance.
 * Upon instantiation, call [.performClustering]
 * to perform the actual clustering.
 *
 * @param inputValues Input values to be clustered
 * @param minNumElements Minimum number of elements to constitute cluster
 * @param maxDistance Maximum distance of elements to consider clustered
 * @param metric Metric implementation to determine distance
 * @throws DBSCANClusteringException
 */
(inputValues: Collection<V>, minNumElements: Int, maxDistance: Double, metric: (V, V) -> Double) {

  /** maximum distance of values to be considered as cluster  */
  private var epsilon = 1.0

  /** minimum number of members to consider cluster  */
  private var minimumNumberOfClusterMembers = 2

  /** distance metric applied for clustering  */
  private var metric: (V, V) -> Double

  /** internal list of input values to be clustered  */
  private var inputValues: ArrayList<V> = ArrayList(inputValues)

  /** index maintaining visited points  */
  private val visitedPoints = HashSet<V>()

  init {
    if (inputValues.isEmpty()) {
      throw RuntimeException("DBSCAN: List of input values is empty.")
    }

    if (inputValues.size < 2) {
      throw RuntimeException("DBSCAN: Less than two input values cannot be clustered. Number of input values: " + inputValues!!.size)
    }
    this.minimumNumberOfClusterMembers = minNumElements
    this.epsilon = maxDistance

    if (epsilon < 0) {
      throw RuntimeException("DBSCAN: Maximum distance of input values cannot be negative. Current value: $epsilon")
    }
    if (minimumNumberOfClusterMembers < 2) {
      throw RuntimeException("DBSCAN: Clusters with less than 2 members don't make sense. Current value: $minimumNumberOfClusterMembers")
    }

    this.metric = metric
  }

  /**
   * Determines the neighbours of a given input value.
   *
   * @param inputValue Input value for which neighbours are to be determined
   * @return list of neighbours
   * @throws DBSCANClusteringException
   */
  private fun getNeighbours(inputValue: V): ArrayList<V> {
    val neighbours = ArrayList<V>()
    for (i in inputValues.indices) {
      val candidate = inputValues[i]
      if (metric(inputValue, candidate) <= epsilon) {
        neighbours.add(candidate)
      }
    }
    return neighbours
  }

  /**
   * Merges the elements of the right collection to the left one and returns
   * the combination.
   *
   * @param neighbours1 left collection
   * @param neighbours2 right collection
   * @return Modified left collection
   */
  private fun mergeRightToLeftCollection(neighbours1: ArrayList<V>,
                                         neighbours2: ArrayList<V>): ArrayList<V> {
    for (i in neighbours2.indices) {
      val tempPt = neighbours2[i]
      if (!neighbours1.contains(tempPt)) {
        neighbours1.add(tempPt)
      }
    }
    return neighbours1
  }

  /**
   * Applies the clustering and returns a collection of clusters (i.e. a list
   * of lists of the respective cluster members).
   *
   * @return
   * @throws DBSCANClusteringException
   */
  fun performClustering(): ArrayList<ArrayList<V>> {
    val resultList = ArrayList<ArrayList<V>>()
    visitedPoints.clear()

    var neighbours: ArrayList<V>
    var index = 0

    while (inputValues.size > index) {
      val p = inputValues[index]
      if (!visitedPoints.contains(p)) {
        visitedPoints.add(p)
        neighbours = getNeighbours(p)

        if (neighbours.size >= minimumNumberOfClusterMembers) {
          var ind = 0
          while (neighbours.size > ind) {
            val r = neighbours[ind]
            if (!visitedPoints.contains(r)) {
              visitedPoints.add(r)
              val individualNeighbours = getNeighbours(r)
              if (individualNeighbours.size >= minimumNumberOfClusterMembers) {
                neighbours = mergeRightToLeftCollection(
                    neighbours,
                    individualNeighbours)
              }
            }
            ind++
          }
          resultList.add(neighbours)
        }
      }
      index++
    }
    return resultList
  }

}