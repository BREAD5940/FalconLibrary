package org.ghrobotics.lib.commands

import edu.wpi.first.wpilibj.command.Subsystem
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicLong

object SubsystemHandler {

    private val subsystems = CopyOnWriteArrayList<FalconSubsystem>()

    private var alreadyStarted = false

    fun addSubsystem(subsystem: FalconSubsystem) {
        if (alreadyStarted) throw IllegalStateException("You cannot add a subsystem after the initialize stage")
        subsystems.add(subsystem)
        println("[FalconSubsystem Handler] Added ${subsystem.javaClass.simpleName}")
    }

    fun autoReset() = subsystems.forEach { it.autoReset() }

    fun teleopReset() = subsystems.forEach { it.teleopReset() }

    // https://www.chiefdelphi.com/forums/showthread.php?t=166814
    fun zeroOutputs() = subsystems.forEach { it.zeroOutputs() }
}

abstract class FalconSubsystem(val name: String = "FalconSubsystem ${subsystemId.incrementAndGet()}") {
    companion object {
        private val subsystemId = AtomicLong()
    }

    private val _wpiSubsystem = FalconWpiSubsystem()
    val wpiSubsystem: Subsystem = _wpiSubsystem

    private inner class FalconWpiSubsystem : Subsystem(name) {
        override fun initDefaultCommand() {
            defaultCommand = (this@FalconSubsystem.defaultCommand ?: EmptyFalconCommand).wpiCommand
        }
    }

    var defaultCommand: FalconCommand? = null
        protected set(value) {
            _wpiSubsystem.defaultCommand = (value ?: EmptyFalconCommand).wpiCommand
            field = value
        }

    open fun autoReset() {}
    open fun teleopReset() {}
    open fun zeroOutputs() {}
}