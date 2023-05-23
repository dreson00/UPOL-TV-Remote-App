package com.example.myapplication.Utilities

import android.hardware.ConsumerIrManager
import android.util.Log
import com.example.myapplication.data.ProtocolInfo

class RemoteManager(val irManager: ConsumerIrManager, val protocolInfo: ProtocolInfo) {

    // zde se podle formátu rozhodne, zda se musí signál převést
    // a nakonec ho odvysílá pomocí metody transmit v ConsumerIrManager
    // 38000 je frekvence, tahle je tak běžně nejpoužívanější
    fun transmit(command: String, format: String, bits: Int?): Boolean {
        if (irManager.hasIrEmitter()) {

            // signál je buď pole hodnot pulzů a doby vysílání
            // to je výchozí formát pro ConsumerIrManager
            // nebo je signál v kompresovaném NEC formátu - převede se na pole pulzů
            when (format) {
                "pulses" -> {
                    irManager.transmit(38000, toPulseArray(command))
                }
                "NEC" -> {

                    when (bits) {
                        32 -> {
                            irManager.transmit(
                                38000,
                                convertNECtoPattern(command.toLong(16))
                            )
                        }
                        64 -> {
                            irManager.transmit(
                                38000,
                                convertNECtoPattern(
                                    command
                                        .substring(0, command.length / 2)
                                        .toLong(16)
                                )
                            )
                            irManager.transmit(
                                38000,
                                convertNECtoPattern(
                                    command
                                        .substring(command.length / 2, command.length)
                                        .toLong(16)
                                )
                            )
                        }
                    }
                }
            }
            Log.w("warn", "odvysíláno")
            return true
        } else {
            return false
        }
    }

    // metoda pro převod stringu na pole
    private fun toPulseArray(signalString: String): IntArray {
        return signalString.split(", ").map { it.toInt() }.toIntArray()
    }

    // generováno ChatGPT - nevěděl jsem jak ty formáty převést
    private fun convertNECtoPattern(necCode: Long): IntArray {
        val hdrMark = protocolInfo.hdr_mark
        val hdrSpace = protocolInfo.hdr_space
        val bitMark = protocolInfo.bit_mark
        val oneSpace = protocolInfo.one_space
        val zeroSpace = protocolInfo.zero_space
        val pattern = mutableListOf<Int>()

        pattern.add(hdrMark)
        pattern.add(hdrSpace)

        for (i in 0 until 32) {
            val bit = ((necCode shr i) and 1).toInt()
            if (bit == 1) {
                pattern.add(bitMark)
                pattern.add(oneSpace)
            } else {
                pattern.add(bitMark)
                pattern.add(zeroSpace)
            }
        }

        pattern.add(bitMark)

        return pattern.toIntArray()
    }
}