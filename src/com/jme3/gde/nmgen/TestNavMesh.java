package com.jme3.gde.nmgen;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;


/**
 *
 * @author Rickard
 */
public class TestNavMesh extends SimpleApplication{

    public static void main(String[] args){ 
        TestNavMesh test = new TestNavMesh();
        AppSettings settings = new AppSettings(false);//vrAppState.getSettings();
        settings.setSwapBuffers(true);
        settings.setFrameRate(60);
        test.setSettings(settings);
        test.start();
    }
    
    protected FilterPostProcessor fpp;
    DirectionalLight light;
    
    @Override
    public void simpleInitApp() {
        
        
        flyCam.setMoveSpeed(10);
        cam.setLocation(new Vector3f(20, 100, 20));
//        viewPort.setBackgroundColor(ColorRGBA.White);
        light = new DirectionalLight();
        light.setDirection(new Vector3f(-0.3f, -0.5f, -0.2f));
        getRootNode().addLight(light);
        
        AmbientLight amb = new AmbientLight();
        amb.setColor(ColorRGBA.DarkGray);
        getRootNode().addLight(amb);
//        addFilter();
        
        Node world = (Node) assetManager.loadModel("Models/navtest.j3o");
        
        NavMeshController controller = new NavMeshController(this, (Node) world);
        
        Mesh navMesh = controller.generateNavMesh(0.15f, 0.5f, 1.5f, 0.25f, 48f, true, 0.1f, 2f, true, 3, 1, 0f, 0.25f, 6, 100, 0.1f); 
        
        Geometry navGeom = new Geometry("NavMesh", navMesh);
        Material m = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        m.setColor("Color", ColorRGBA.Red);
        
        navGeom.setMaterial(m);
        world.attachChild(navGeom);
        
        rootNode.attachChild(world);
        
    }
    
}
