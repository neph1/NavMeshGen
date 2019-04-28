package com.jme3.gde.nmgen;

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.scene.mesh.IndexBuffer;
import com.jme3.terrain.Terrain;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.critterai.nmgen.IntermediateData;
import org.critterai.nmgen.NavmeshGenerator;
import org.critterai.nmgen.TriangleMesh;

/**
 * Generates the navigation mesh using the org.critterai.nmgen.NavmeshGenerator class.
 * 
 */ 
public class NavMeshGenerator {

    private org.critterai.nmgen.NavmeshGenerator nmgen;
    private IntermediateData intermediateData;
    private int timeout = 30000;
    private NavMeshProperties properties;

    public NavMeshGenerator(NavMeshProperties properties) {
        this.properties = properties;
    }

    public void setIntermediateData(IntermediateData data) {
        this.intermediateData = data;
    }

    public Mesh optimize(Mesh mesh) {
        nmgen = new NavmeshGenerator(properties.getCellSize(), properties.getCellHeight(), properties.getMinTraversableHeight(),
                properties.getMaxTraversableStep(), properties.getMaxTraversableSlope(),
                properties.isClipLedges(), properties.getTraversableAreaBorderSize(),
                properties.getSmoothingThreshold(), properties.isUseConservativeExpansion(),
                properties.getMinUnconnectedRegionSize(), properties.getMergeRegionSize(),
                properties.getMaxEdgeLength(), properties.getEdgeMaxDeviation(), properties.getMaxVertsPerPoly(),
                properties.getContourSampleDistance(), properties.getContourMaxDeviation());

        FloatBuffer pb = mesh.getFloatBuffer(Type.Position);
        IndexBuffer ib = mesh.getIndexBuffer();

        // copy positions to float array
        float[] positions = new float[pb.capacity()];
        pb.clear();
        pb.get(positions);

        // generate int array of indices
        int[] indices = new int[ib.size()];
        for (int i = 0; i < indices.length; i++) {
            indices[i] = ib.get(i);
        }


        TriangleMesh triMesh = buildNavMesh(positions, indices, intermediateData);
        if (triMesh == null) {
            Logger.getGlobal().log(Level.WARNING, "Trimesh is null, returning null");
            return null;
        }

        int[] indices2 = triMesh.indices;
        float[] positions2 = triMesh.vertices;

        Mesh mesh2 = new Mesh();
        mesh2.setBuffer(Type.Position, 3, positions2);
        mesh2.setBuffer(Type.Index, 3, indices2);
        mesh2.updateBound();
        mesh2.updateCounts();

        return mesh2;
    }

    private TriangleMesh buildNavMesh(float[] positions, int[] indices, IntermediateData intermediateData) {
        MeshBuildRunnable runnable = new MeshBuildRunnable(positions, indices, intermediateData);
        try {
            execute(runnable, timeout);
        } catch (TimeoutException ex) {
            Logger.getGlobal().log(Level.WARNING, "NavMesh Generation timed out. " + ex.getMessage());
        }
        return runnable.getTriMesh();
    }

    private static void execute(Thread task, long timeout) throws TimeoutException {
        task.start();
        try {
            task.join(timeout);
        } catch (InterruptedException e) {
        }
        if (task.isAlive()) {
//            task.interrupt();
            task.stop();
            throw new TimeoutException();
        }
    }

    private static void execute(Runnable task, long timeout) throws TimeoutException {
        Thread t = new Thread(task, "Timeout guard");
        t.setDaemon(true);
        execute(t, timeout);
    }

    public Mesh terrain2mesh(Terrain terr) {
        float[] heights = terr.getHeightMap();
        int length = heights.length;
        int side = (int) FastMath.sqrt(heights.length);
        float[] vertices = new float[length * 3];
        int[] indices = new int[(side - 1) * (side - 1) * 6];

        //Vector3f trans = ((Node) terr).getWorldTranslation().clone();
        Vector3f trans = new Vector3f(0,0,0);
        trans.x -= terr.getTerrainSize() / 2f;
        trans.z -= terr.getTerrainSize() / 2f;
        float offsetX = trans.x;
        float offsetZ = trans.z;

        // do vertices
        int i = 0;
        for (int z = 0; z < side; z++) {
            for (int x = 0; x < side; x++) {
                vertices[i++] = x + offsetX;
                vertices[i++] = heights[z * side + x];
                vertices[i++] = z + offsetZ;
            }
        }

        // do indexes
        i = 0;
        for (int z = 0; z < side - 1; z++) {
            for (int x = 0; x < side - 1; x++) {
                // triangle 1
                indices[i++] = z * side + x;
                indices[i++] = (z + 1) * side + x;
                indices[i++] = (z + 1) * side + x + 1;
                // triangle 2
                indices[i++] = z * side + x;
                indices[i++] = (z + 1) * side + x + 1;
                indices[i++] = z * side + x + 1;
            }
        }

        Mesh mesh2 = new Mesh();
        mesh2.setBuffer(Type.Position, 3, vertices);
        mesh2.setBuffer(Type.Index, 3, indices);
        mesh2.updateBound();
        mesh2.updateCounts();

        return mesh2;
    }

   
    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
   
    private class MeshBuildRunnable implements Runnable {

        private float[] positions;
        private int[] indices;
        private IntermediateData intermediateData;
        private TriangleMesh triMesh;

        public MeshBuildRunnable(float[] positions, int[] indices, IntermediateData intermediateData) {
            this.positions = positions;
            this.indices = indices;
            this.intermediateData = intermediateData;
        }

        @Override
        public void run() {
            triMesh = nmgen.build(positions, indices, intermediateData);
        }

        public TriangleMesh getTriMesh() {
            return triMesh;
        }
    }

    public static class TimeoutException extends Exception {

        /** Create an instance */
        public TimeoutException() {
        }
    }
}
