package com.akkademy

import akka.actor.ActorSystem
import akka.testkit.TestActorRef
import akka.util.Timeout
import com.akkademy.messages.SetRequest
import org.scalatest.{FunSpecLike, Matchers}

/**
  * Created by wangfengyang on 2017/9/1.
  */
class AkkademyDbSpec extends  FunSpecLike with Matchers{

  // 创建一个actionSystem
  implicit val system = ActorSystem()
  implicit val timeout = Timeout(5)

  describe("akkademyDb") {
    describe("given SetRequest") {
      it("should place key/value into map") {

        // 创建action引用
        val actorRef = TestActorRef(new AkkademyDb)
        // 使用！(tell)通过actor引用，将消息放入ActionSystem中的Actor中

        // 请求
        // 1. 由于使用的是TestActorRef，所以是一个同步操作，就是说只有SetRequest发送到actor之后请求处理完成，才会执行下面的语句
        // 2. 正常情况下actor的tell是异步操作，调用后会立即返回，执行下面的代码，不会等到处理结束
        actorRef ! SetRequest("key123", "value123")

        // 验证发送和处理成功
        // 1. 获取指向背后Actor实例的引用
        // 2. 从map里检查是否actor处理成功
        val akkademyDb = actorRef.underlyingActor
        akkademyDb.map.get("key123") should equal(Some("value123"))
        println(akkademyDb.map.get("key123"))
      }
    }
  }
}
