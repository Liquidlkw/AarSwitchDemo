pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "AarSwitchDemo"
include(":app")
//一旦引入一个新的module此处就会include新的module，但是我们要做到源码和远程的切换则需要动态include
//include(":lib_sample")


//一般情况下远程sdk也会是一个仓库
//源码引入后sdk代码会被实际改动，切换回sdk工程后还可以提交回sdk仓库
val sdkSource = providers.gradleProperty("sdkSource").get().toBoolean()
if (sdkSource) {
    //todo 这里取决于你想把aar源码放在什么位置
    val parentFile = File(settingsDir.parent, "AarSwitchDemo")
    //todo 这里取决于你的模块名
    val moduleList = listOf(
        "lib_sample",
    )
    moduleList.forEach { module ->
        println("useSource: $module -> ${File(parentFile.absolutePath, module).absolutePath}")
        include(":$module")
        project(":$module").projectDir = File(parentFile.absolutePath, module)
    }
}
