//package Utility//import android.content.res.AssetManager
//import com.google.android.filament.*
//import com.google.android.filament.gltfio.*
//
//val assetLoader = AssetLoader()
//
//// Load the 3D model data from the first GLB file.
//val model1 = assetLoader.load(this.assets, "model1.glb")
//val mesh1 = model1.meshes[0]
//val material1 = model1.materials[0]
//val animations1 = model1.animations
//
//// Load the 3D model data from the second GLB file.
//val model2 = assetLoader.loadModel(this.assets, "model2.glb")
//val mesh2 = model2.meshes[0]
//val material2 = model2.materials[0]
//val animations2 = model2.animations
//
//// Merge the 3D model data from both files.
//val mergedMesh = Mesh.Builder()
//    .addVertexBuffer(mesh1.getVertexBuffer(0))
//    .addVertexBuffer(mesh2.getVertexBuffer(0))
//    .addIndexBuffer(mesh1.getIndexBuffer())
//    .addIndexBuffer(mesh2.getIndexBuffer())
//    .build(engine)
//val mergedMaterial = Material.Builder()
//    .packageName(this.packageName)
//    .build(engine)
//mergedMaterial.setMaterialInstance(
//MaterialInstance.Builder()
//.material(material1)
//.build(engine))
//mergedMaterial.setMaterialInstance(
//MaterialInstance.Builder()
//.material(material2)
//.build(engine))
//val entity = EntityManager.get().create()
//val model = Model.Builder()
//    .engine(engine)
//    .build()
//ModelData(model).add(mergedMesh, mergedMaterial)
