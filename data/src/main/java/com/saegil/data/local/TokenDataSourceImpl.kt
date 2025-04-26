package com.saegil.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.app.data.proto.TokenProto
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

private val Context.tokenProtoDataStore: DataStore<TokenProto> by dataStore(
    fileName = "token_proto.pb",
    serializer = TokenProtoSerializer
)

class TokenDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : TokenDataSource {

    override suspend fun saveToken(tokenProto: TokenProto) {
        context.tokenProtoDataStore.updateData { protoToken ->
            protoToken.toBuilder()
                .setAccessToken(tokenProto.accessToken)
                .setRefreshToken(tokenProto.refreshToken)
                .build()
        }
    }

    override suspend fun getToken(): TokenProto {
        return context.tokenProtoDataStore.data.first()
    }

    override suspend fun clearToken() {
        context.tokenProtoDataStore.updateData {
            TokenProto.getDefaultInstance()
        }
    }
}