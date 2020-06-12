package son.ysy.photo.dis

import org.koin.dsl.module

object DatabaseModule {
    val instance = module {
//        single {
//            Room.databaseBuilder(
//                get(),
//                AreaDatabase::class.java,
//                "AreaInfo.db"
//            ).createFromAsset("AreaInfo.db")
//                .build()
//        }
//
//        single {
//            get<AreaDatabase>().getAreaDao()
//        }
    }
}