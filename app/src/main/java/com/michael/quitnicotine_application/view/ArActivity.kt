package com.michael.quitnicotine_application.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.core.Anchor
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import com.michael.quitnicotine_application.R

class ArActivity : AppCompatActivity() {
    private lateinit var arCam: ArFragment //object of ArFragment Class
    private var clickNo = 0 //helps to render the 3d model only once when we tap the screen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ar)
        arCam = (supportFragmentManager.findFragmentById(R.id.arCameraArea) as ArFragment?)!!
        //ArFragment is linked up with its respective id used in the activity_main.xml
        arCam.setOnTapArPlaneListener {
                hitResult: HitResult, plane: Plane?, motionEvent: MotionEvent? ->
            clickNo++
            //the 3d model comes to the scene only when clickNo is one that means once
            if (clickNo == 1) {
                val anchor = hitResult.createAnchor()
                ModelRenderable.builder()
                    .setSource(this, R.raw.sgrt)
                    .setIsFilamentGltf(true)
                    .build()
                    .thenAccept { modelRenderable: ModelRenderable? ->
                        addModel(
                            anchor,
                            modelRenderable
                        )
                    }
                    .exceptionally { throwable: Throwable ->
                        val builder = AlertDialog.Builder(this)
                        builder.setMessage("Something is not right" + throwable.message).show()
                        null
                    }
            }
        }
    }
    private fun addModel(anchor: Anchor, modelRenderable: ModelRenderable?) {
        val anchorNode = AnchorNode(anchor)
        // Creating a AnchorNode with a specific anchor
        anchorNode.setParent(arCam.arSceneView.scene)
        //attaching the anchorNode with the ArFragment
        val model = TransformableNode(arCam.transformationSystem)
        model.setParent(anchorNode)
        //attaching the anchorNode with the TransformableNode
        model.renderable = modelRenderable
        //attaching the 3d model with the TransformableNode that is already attached with the node
        model.select()
    }
}