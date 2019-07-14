package hello.micronaut.service
import com.mongodb.reactivestreams.client.*;
import com.mongodb.client.model.Aggregates
import java.util.Collections.singletonList
import com.sun.xml.internal.ws.api.client.ServiceInterceptor.aggregate
import io.micronaut.configuration.hystrix.annotation.HystrixCommand
import io.micronaut.context.annotation.Value
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton
import javax.validation.Valid




@Singleton
open class MongoAccess(@Inject val mongoClient: MongoClient) {
    @Value("\${mongodb.database-name}")
    lateinit var databaseName: String

    @Value("\${mongodb.collection-name}")
    lateinit var collectionName: String

    @HystrixCommand
    open fun randomDocument(): Maybe<MessageEntity> {
        return Flowable.fromPublisher(
                getCollection()
                        .aggregate(singletonList(Aggregates.sample(1)), MessageEntity::class.java)
        ).firstElement()
    }

    open fun addMessage(message: MessageEntity): Single<MessageEntity> {
        return Single.fromPublisher(getCollection().insertOne(message))
                .map({ success -> message })

    }

    private fun getCollection(): MongoCollection<MessageEntity> {
        return mongoClient
                .getDatabase(databaseName)
                .getCollection(collectionName, MessageEntity::class.java)
    }
}

class MessageEntity(var message: String = "hello emptiness")
