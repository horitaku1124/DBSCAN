package org.christopherfrantz.dbscan;

import org.christopherfrantz.dbscan.DBSCANClusteringTest.Vec2;
import org.junit.Test;

import java.util.Arrays;

public class DBSCANTestJava {
    static class Vec2 {
        public float x;
        public float y;
        public Vec2(float x, float y) {
            this.x = x;
            this.y = y;
        }
        @Override
        public String toString() {
            return x + "," + y;
        }
    }
    @Test
    public void testBase() throws DBSCANClusteringException {

        var inputValues = Arrays.asList(
                new Vec2(0, 1),
                new Vec2(0, 2),
                new Vec2(1, 2),
                new Vec2(10, 1),
                new Vec2(10, 2),
                new Vec2(10, 2)
        );
        var cluster = new DBSCANClusterer<Vec2>(inputValues, 2, 1, (v1, v2) -> {
            return Math.sqrt(Math.pow(v1.x - v2.x, 2) + Math.pow((v1.y - v2.y), 2));
        });
        var cl = cluster.performClustering();
        for (var list: cl) {
            for (var vec: list) {
                System.out.println(vec);
            }
        }
    }
}
