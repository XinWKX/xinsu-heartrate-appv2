package com.xinsu.heartrate.settings.core

object SettingsManager {

    /**
     * Pulse 音效
     */
    var pulseAudioEnabled =
        true

    /**
     * Haptic
     */
    var hapticEnabled =
        false

    /**
     * 粒子数量
     */
    var particleCount =
        140

    /**
     * Glow 强度
     */
    var glowEnabled =
        true

    /**
     * ECG Glow
     */
    var ecgGlowEnabled =
        true

    /**
     * 低功耗模式
     */
    var lowPowerMode =
        false

    /**
     * FPS 限制
     */
    var targetFps =
        60

    /**
     * 动态背景
     */
    var animatedBackground =
        true

    /**
     * 粒子速度
     */
    var particleSpeed =
        1f

    /**
     * HUD 亮度
     */
    var hudBrightness =
        1f

    /**
     * 雷达启用
     */
    var radarEnabled =
        true

    /**
     * 粒子启用
     */
    var particlesEnabled =
        true

    /**
     * 连接特效
     */
    var connectionFxEnabled =
        true

    /**
     * 音效总开关
     */
    var soundEnabled =
        true

    /**
     * 背景模式
     */
    var backgroundMode =
        BackgroundMode.BLACK

    enum class BackgroundMode {

        BLACK,

        GRID,

        PARTICLE,

        GRADIENT
    }
}
