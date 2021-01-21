allprojects {
    repositories {
        jcenter()
    }
}

subprojects {
    tasks.register<Copy>("packageDistribution") {
        dependsOn("jar")
        from("${project.rootDir}/scripts/ach-cli")
        from("${project.projectDir}/build/libs/${project.name}.jar") {
            into("lib")
        }
        into("${project.rootDir}/dist")
    }
}
