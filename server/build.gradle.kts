val plasmoVoiceVersion: String by rootProject

plugins {
    id("su.plo.voice.plugin") version("1.0.1")
}

dependencies {
    compileOnly(project(":common"))
    compileOnly("com.comphenix.protocol:ProtocolLib:5.1.0")
    compileOnly("su.plo.voice.api:server:$plasmoVoiceVersion")
}
