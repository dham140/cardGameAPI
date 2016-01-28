import _root_.akka.actor.{Props, ActorSystem}
import com.example.app._
import org.scalatra._
import javax.servlet.ServletContext

class ScalatraBootstrap extends LifeCycle {
//  override def init(context: ServletContext) {
//    context.mount(new TestServlet, "/*")
//  }

  // Get a handle to an ActorSystem and a reference to one of your actors
  val system = ActorSystem()
//  val myActor = system.actorOf(Props[/*DO STUFF*/])

  // In the init method, mount your servlets with references to the system
  // and/or ActorRefs, as necessary.
  override def init(context: ServletContext) {
    context.mount(new TestServlet(system), "/*")
//    context.mount(new MyActorApp(system, myActor), "/actors/*")
  }

  // Make sure you shut down
  override def destroy(context:ServletContext) {
    system.terminate()
  }
}
