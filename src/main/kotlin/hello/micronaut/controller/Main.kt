package hello.micronaut.controller

import hello.micronaut.service.MessageEntity
import hello.micronaut.service.MongoAccess
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject
import javax.validation.Valid

@Controller("/random")
class HelloController(@Inject val mongoAccess: MongoAccess) {

    @Get(produces = [MediaType.APPLICATION_JSON])
    fun getRandomDocument(): Maybe<MessageEntity> {
        return this.mongoAccess.randomDocument()
    }

    @Post(produces = [MediaType.APPLICATION_JSON])
    fun addDocument(messageEntity: MessageEntity): Single<MessageEntity> {
        return this.mongoAccess.addMessage(messageEntity)
    }
}
