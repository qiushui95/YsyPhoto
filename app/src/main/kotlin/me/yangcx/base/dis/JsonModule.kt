package me.yangcx.base.dis

import com.squareup.moshi.Moshi
import me.yangcx.base.json.DefaultJsonConfig
import org.koin.dsl.module

class JsonModule(jsonConfig: DefaultJsonConfig) {
    val instance = module {
        single {
            Moshi.Builder()
                .apply {
                    jsonConfig.getJsonAdapters()
                        .forEach {
                            add(it)
                        }
                }
                .build()
        }
    }
}