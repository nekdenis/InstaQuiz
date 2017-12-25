package com.github.nekdenis.model

import com.ironz.binaryprefs.serialization.serializer.persistable.Persistable
import com.ironz.binaryprefs.serialization.serializer.persistable.io.DataInput
import com.ironz.binaryprefs.serialization.serializer.persistable.io.DataOutput


class AnswersModel(predefinedAnswers: Map<Int, Int> = mapOf()) : Persistable {

    var answers: Map<Int, Int> = predefinedAnswers
        private set(value) {
            field = value
        }

    override fun writeExternal(out: DataOutput) {
        val size = answers.size
        out.writeInt(size)
        for (answer in answers) {
            out.writeInt(answer.key)
            out.writeInt(answer.value)
        }
    }

    override fun readExternal(inp: DataInput) {
        val size = inp.readInt()
        val result = mutableMapOf<Int, Int>()
        for (i in 0 until size) {
            result.put(inp.readInt(), inp.readInt())
        }
        answers = HashMap(result)
    }

    override fun deepClone(): Persistable =
            AnswersModel().apply {
                answers = this@AnswersModel.answers
            }

    companion object {
        val KEY: String = "AnswersModel"
    }
}