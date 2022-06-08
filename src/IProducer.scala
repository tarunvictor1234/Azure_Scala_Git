import scala.beans.BeanProperty


trait IProducer {
   @BeanProperty var timedetails: String = _;
}