val plasmoVoiceVersion: String by rootProject

dependencies {
    compileOnly(project(":common"))
    compileOnly("dev.simplix:protocolize-api:2.4.1")
    compileOnly("su.plo.voice.api:proxy:$plasmoVoiceVersion")
}
