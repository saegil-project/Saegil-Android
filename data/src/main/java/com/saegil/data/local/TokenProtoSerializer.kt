package com.saegil.data.local

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.app.data.proto.TokenProto
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object TokenProtoSerializer : Serializer<TokenProto> {
    override val defaultValue: TokenProto = TokenProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): TokenProto {
        try {
            return TokenProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: TokenProto, output: OutputStream) = t.writeTo(output)
}