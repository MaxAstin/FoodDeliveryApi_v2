import com.bunbeauty.fooddelivery.auth.IJwtService
import com.bunbeauty.fooddelivery.data.Constants.FB_ADMIN_KEY
import com.bunbeauty.fooddelivery.data.Constants.PORT
import com.bunbeauty.fooddelivery.data.DatabaseFactory
import com.bunbeauty.fooddelivery.di.*
import com.bunbeauty.fooddelivery.plugins.configureSerialization
import com.bunbeauty.fooddelivery.plugins.configureSockets
import com.bunbeauty.fooddelivery.routing.configureRouting
import com.bunbeauty.fooddelivery.task.startUpdateHitsTask
import com.bunbeauty.fooddelivery.task.startUpdateStatisticTask
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import java.io.InputStream

fun main() {
    DatabaseFactory.init()
    embeddedServer(Netty, port = System.getenv(PORT).toInt()) {
        val inputStream: InputStream = System.getenv(FB_ADMIN_KEY).byteInputStream()
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(inputStream))
            .build()
        FirebaseApp.initializeApp(options)
        configureSockets()
        configureSerialization()
        install(Koin) {
            modules(
                dataModule,
                authModule,
                userModule,
                clientUserModule,
                companyModule,
                orderModule,
                cityModule,
                cafeModule,
                streetModule,
                categoryModule,
                menuProductModule,
                hitModule,
                deliveryModule,
                addressModule,
                statisticModule,
                dateTimeModule,
                versionModule,
                firebaseModule,
                paymentModule,
                requestModule
            )
        }
        val jwtService: IJwtService by inject()
        install(Authentication) {
            jwt {
                jwtService.configureAuth(this)
            }
        }
        configureRouting()
        startUpdateHitsTask()
        startUpdateStatisticTask()
    }.start(wait = true)
}